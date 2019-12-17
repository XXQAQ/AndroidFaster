package com.xq.androidfaster.util.tools;

import android.text.TextUtils;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public final class ByteUtils {

    //注意：下4列方法被排序的字段必须使用@FieldOrder进行注解使用

    private static Map<Class, Field[]> classMap = new HashMap<>();

    public static byte[] object2Bytes(Object o) throws Exception {
        if (o == null) return new byte[0];

        Class mClass = o.getClass();

        ByteBuffer byteBuffer = ByteBuffer.allocate(64*1000);
        if (!classMap.containsKey(mClass)){
            classMap.put(mClass,orderedFields(getAllDeclaredFields(mClass)));
        }

        Field[] fields = classMap.get(mClass);

        for (int i=0;i<fields.length;i++){

            Field field = fields[i];

            // 静态字段 等情况不受理
            if (Modifier.isStatic(field.getModifiers())){
                continue;
            }
            //设置强制访问
            if (!field.isAccessible()){
                field.setAccessible(true);
            }

            Object value = null;

            DataPointer<byte[]> pointer = new DataPointer<>();

            if ( o instanceof ToBytesConverter && ((ToBytesConverter) o).interceptToBytes(fields,i,byteBuffer,pointer) )
            {
                if (pointer.get() != null){
                    byteBuffer.put(pointer.get());
                }
            }
            else
            {
                value = field.get(o);

                if (field.getType().isArray() || List.class.isAssignableFrom(field.getType()))
                {
                    if (value == null){
                        continue;
                    }

                    Field lengthField = getLengthField(field,fields,i);
                    int length = 0;

                    if (lengthField == null){
                        length = getArrayFieldLength(o,field,fields,i);
                    } else {
                        if (field.getType().isArray()){
                            length = ArrayUtils.getLength(value);
                        } else  if (List.class.isAssignableFrom(field.getType())){
                            length = ((List)value).size();
                        }
                        lengthField.set(o,length);
                    }

                    if (field.getType().isArray())
                    {
                        for (int j=0;j<length;j++){
                            DataPointer<byte[]> temp = new DataPointer<>();
                            toBytes(field.getType().getComponentType(),ArrayUtils.get(value,j),temp);
                            if (temp.get() != null){
                                byteBuffer.put(temp.get());
                            }
                        }
                    }
                    else      if(List.class.isAssignableFrom(field.getType()))
                    {
                        for (int j=0;j<length;j++){
                            DataPointer<byte[]> temp = new DataPointer<>();
                            toBytes(field.getType().getComponentType(),((List)value).get(j),temp);
                            if (temp.get() != null){
                                byteBuffer.put(temp.get());
                            }
                        }
                    }
                }
                else
                {
                    toBytes(field.getType(),value,pointer);

                    if (pointer.get() != null){
                        byteBuffer.put(pointer.get());
                    }
                }
            }
        }

        if (o instanceof ToBytesConverter){
            ((ToBytesConverter) o).finishToBytes(fields,byteBuffer);
        }

        return byteBuffer2Bytes(byteBuffer);

    }

    public static <T>T bytes2Object(Class<T> mClass,byte[] bytes) throws Exception {
        return bytes2Object(mClass,ByteBuffer.wrap(bytes));
    }

    public static <T>T bytes2Object(Class<T> mClass,ByteBuffer byteBuffer) throws Exception {
        if (mClass == null)  return null;

        Constructor<T> constructor = mClass.getDeclaredConstructor();
        constructor.setAccessible(true);
        T o = constructor.newInstance();

        if (!classMap.containsKey(mClass)){
            classMap.put(mClass,orderedFields(getAllDeclaredFields(mClass)));
        }

        Field[] fields = classMap.get(mClass);

        for (int i=0;i<fields.length;i++){

            Field field = fields[i];

            // 静态字段 等情况不受理
            if (Modifier.isStatic(field.getModifiers())){
                continue;
            }

            //设置强制访问
            if (!field.isAccessible()){
                field.setAccessible(true);
            }

            Object value = null;

            DataPointer pointer = new DataPointer();

            if (o instanceof FromBytesConverter && !((FromBytesConverter) o).interceptFromBytes(fields,i,byteBuffer,pointer))
            {
                if (pointer.get() != null){
                    field.set(o,pointer.get());
                }
            }
            else
            {
                if (field.getType().isArray() || List.class.isAssignableFrom(field.getType()))
                {
                    int length = getArrayFieldLength(o,field,fields,i);

                    if (field.getType().isArray())
                    {
                        value = Array.newInstance(field.getType().getComponentType(),length);

                        for (int j=0;j<length;j++){
                            DataPointer temp = new DataPointer();
                            toObj(field.getType().getComponentType(),byteBuffer,temp);
                            ArrayUtils.set(value,j,temp.get());
                        }
                    }
                    else  if (List.class.isAssignableFrom(field.getType()))
                    {
                        value = new LinkedList<>();

                        for (int j=0;j<length;j++){
                            DataPointer temp = new DataPointer();
                            toObj(field.getType().getComponentType(),byteBuffer,temp);
                            ((List)value).set(j,temp.get());
                        }
                    }

                    field.set(o,value);
                }
                else
                {
                    toObj(field.getType(),byteBuffer,pointer);

                    field.set(o,value);
                }
            }
        }

        return o;
    }

    private static void toBytes(Class valueClass,Object value,DataPointer<byte[]> pointer) throws Exception{

        if (long.class.isAssignableFrom(valueClass) || Long.class.isAssignableFrom(valueClass)){
            pointer.set(long2Bytes((Long) value));
        }
        else    if (int.class.isAssignableFrom(valueClass) || Integer.class.isAssignableFrom(valueClass)){
            pointer.set(int2Bytes((Integer) value));
        }
        else    if (short.class.isAssignableFrom(valueClass) || Short.class.isAssignableFrom(valueClass)){
            pointer.set(short2Bytes((Short) value));
        }
        else    if (char.class.isAssignableFrom(valueClass) || Character.class.isAssignableFrom(valueClass)){
            pointer.set(char2Bytes((Character) value));
        }
        else    if (byte.class.isAssignableFrom(valueClass) || Byte.class.isAssignableFrom(valueClass)){
            pointer.set(new byte[]{(Byte) value});
        }
        else    if (boolean.class.isAssignableFrom(valueClass) || Boolean.class.isAssignableFrom(valueClass)){
            pointer.set(boolean2Bytes((Boolean) value));
        }
        else    if (double.class.isAssignableFrom(valueClass) || Double.class.isAssignableFrom(valueClass)){
            pointer.set(double2bytes((Double) value));
        }
        else    if (float.class.isAssignableFrom(valueClass) || Float.class.isAssignableFrom(valueClass)){
            pointer.set(float2bytes((Float) value));
        }
        else {
            pointer.set(object2Bytes(value));
        }
    }

    private static void toObj(Class valueClass,ByteBuffer byteBuffer,DataPointer<Object> pointer) throws Exception{

        if (long.class.isAssignableFrom(valueClass) || Long.class.isAssignableFrom(valueClass)){
            pointer.set(byteBuffer.getLong());
        }
        else    if (int.class.isAssignableFrom(valueClass) || Integer.class.isAssignableFrom(valueClass)){
            pointer.set(byteBuffer.getInt());
        }
        else    if (short.class.isAssignableFrom(valueClass) || Short.class.isAssignableFrom(valueClass)){
            pointer.set(byteBuffer.getShort());
        }
        else    if (char.class.isAssignableFrom(valueClass) || Character.class.isAssignableFrom(valueClass)){
            pointer.set(byteBuffer.getChar());
        }
        else    if (byte.class.isAssignableFrom(valueClass) || Byte.class.isAssignableFrom(valueClass)){
            pointer.set(byteBuffer.get());
        }
        else    if (boolean.class.isAssignableFrom(valueClass) || Boolean.class.isAssignableFrom(valueClass)){
            pointer.set(byteBuffer.get() == (byte)1);
        }
        else    if (double.class.isAssignableFrom(valueClass) || Double.class.isAssignableFrom(valueClass)){
            pointer.set(byteBuffer.getDouble());
        }
        else    if (float.class.isAssignableFrom(valueClass) || Float.class.isAssignableFrom(valueClass)){
            pointer.set(byteBuffer.getFloat());
        }
        else {
            pointer.set(bytes2Object(valueClass,byteBuffer));
        }
    }

    private static Field getLengthField(Field field,Field[] fields,int position) throws Exception{
        if (field.getAnnotation(ArrayFieldLength.class) != null)
        {
            String lengthFieldName = field.getAnnotation(ArrayFieldLength.class).lenthByField();
            if (!TextUtils.isEmpty(lengthFieldName))
            {
                for (Field lengthField : fields)
                {
                    if (lengthField.getName().equals(lengthFieldName))
                    {
                        if (lengthField.getType().isPrimitive()){
                            return  lengthField;
                        } else {
                            throw  new Exception(lengthField.getName() + " must be Number");
                        }
                    }
                }
            }
            else
            {
                return null;
            }
        }
        if (position == 0){
            throw  new Exception("Are you sure " + field.getName() + "is array???");
        }
        if (!(fields[position-1].getType().isPrimitive())){
            throw  new Exception(fields[position-1].getName() + " must be Number");
        }
        return fields[position-1];
    }

    private static int getArrayFieldLength(Object o,Field field,Field[] fields,int position) throws Exception{
        if (field.getAnnotation(ArrayFieldLength.class) != null)
        {
            String lengthFieldName = field.getAnnotation(ArrayFieldLength.class).lenthByField();
            if (TextUtils.isEmpty(lengthFieldName))
            {
                return field.getAnnotation(ArrayFieldLength.class).length();
            }
            else
            {
                for (Field lengthField : fields)
                {
                    if (lengthField.getName().equals(lengthFieldName))
                    {
                        if (lengthField.getType().isPrimitive()){
                            return  ((Number) lengthField.get(o)).intValue();
                        } else {
                            throw  new Exception(lengthField.getName() + " must be Number");
                        }
                    }
                }
            }
        }
        if (position == 0){
            throw  new Exception("Are you sure " + field.getName() + "is array???");
        }
        if (!(fields[position-1].getType().isPrimitive())){
            throw  new Exception(fields[position-1].getName() + " must be Number");
        }
        return ((Number) fields[position-1].get(o)).intValue();
    }

    private static Field[] getAllDeclaredFields(Class mClass){
        if (mClass == null || mClass.getName().equals(Object.class.getName())) return null;

        List<Field> list = new LinkedList<>();

        Field[] allParentField = getAllDeclaredFields(mClass.getSuperclass());
        if (allParentField != null && allParentField.length >0)
            list.addAll(Arrays.asList(allParentField));

        list.addAll(Arrays.asList(mClass.getDeclaredFields()));

        return list.toArray(new Field[0]);
    }

    private static Field[] orderedFields(Field[]fields){
        // 用来存放所有的属性域
        List<Field> list = new ArrayList<>();
        // 过滤带有注解的Field
        for(Field f:fields){
            if(f.getAnnotation(FieldOrder.class)!=null){
                list.add(f);
            }
        }
        // 排序
        Collections.sort(list, new Comparator<Field>() {
            @Override
            public int compare(Field o1, Field o2) {
                return o1.getAnnotation(FieldOrder.class).order() - o2.getAnnotation(FieldOrder.class).order();
            }
        });
        return list.toArray(new Field[0]);
    }

    public interface ToBytesConverter {

        public boolean interceptToBytes(Field[] fields,int fieldPosition,ByteBuffer byteBuffer,DataPointer<byte[]> pointer) throws RuntimeException;

        public void finishToBytes(Field[] fields,ByteBuffer byteBuffer) throws RuntimeException;
    }

    public interface FromBytesConverter{

        public boolean interceptFromBytes(Field[] fields,int fieldPosition,ByteBuffer byteBuffer,DataPointer pointer) throws RuntimeException;

    }

    public static class DataPointer<T>{

        private T t;

        public T get() {
            return t;
        }

        public void set(T t) {
            this.t = t;
        }

    }



    private static byte[] subBytes(byte[] bytes, int start, int end) {
        if (bytes == null) {
            return null;
        }
        if (start < 0) {
            start = 0;
        }
        if (end > bytes.length) {
            end = bytes.length;
        }
        int newSize = end - start;
        byte[] subArray = new byte[newSize];
        System.arraycopy(bytes, start, subArray, 0, newSize);
        return subArray;
    }

    public static byte[] concatByte(byte... bytess){
        ByteBuffer byteBuffer = ByteBuffer.allocate(64*1024);
        for (byte bytes : bytess)
            byteBuffer.put(bytes);
        return byteBuffer2Bytes(byteBuffer);
    }

    public static byte[] concatBytes(byte[]... bytess){
        ByteBuffer byteBuffer = ByteBuffer.allocate(64*1024);
        for (byte[] bytes : bytess)
            byteBuffer.put(bytes);
        return byteBuffer2Bytes(byteBuffer);
    }

    public static byte[] reverseBytes(byte[] a){
        byte[] b = new byte[a.length];
        for (int i = 0; i < b.length; i++) {
            b[i] = a[b.length - i - 1];
        }
        return b;
    }

    public static byte[] datagramPacket2Bytes(DatagramPacket datagramPacket){
        byte[] bytes = new byte[datagramPacket.getLength()];
        System.arraycopy(datagramPacket.getData(),0,bytes,0,bytes.length);
        return bytes;
    }

    public static byte[] byteBuffer2Bytes(ByteBuffer byteBuffer){
        int len = byteBuffer.position() > 0? byteBuffer.position() : byteBuffer.limit() == byteBuffer.capacity()?0 : byteBuffer.limit();
        byte[] bytes = new byte[len];
        System.arraycopy(byteBuffer.array(),0,bytes,0,len);
        return bytes;
    }

    public static byte[] byte2Bytes(byte b){
        return new byte[]{b};
    }

    public static byte[] char2Bytes(char c) {
        byte[] b = new byte[2];
        b[0] = (byte) ((c & 0xFF00) >> 8);
        b[1] = (byte) (c & 0xFF);
        return b;
    }

    public static char bytes2Char(byte[] b) {
        char c = (char) (((b[0] & 0xFF) << 8) | (b[1] & 0xFF));
        return c;
    }

    public static byte[] short2Bytes(short s) {
        byte[] b = new byte[2];
        b[0] = (byte) ((s & 0xFF00) >> 8);
        b[1] = (byte) (s & 0xFF);
        return b;
    }

    public static short bytes2Short(byte[] b) {
        short s = (short) (((b[0] & 0xFF) << 8) | (b[1] & 0xFF));
        return s;
    }

    public static int bytes2Int(byte[] b) {
        return   b[3] & 0xFF |
                (b[2] & 0xFF) << 8 |
                (b[1] & 0xFF) << 16 |
                (b[0] & 0xFF) << 24
                ;
    }

    public static byte[] int2Bytes(int a) {
        return new byte[] {
                (byte) ((a >> 24) & 0xFF),
                (byte) ((a >> 16) & 0xFF),
                (byte) ((a >> 8) & 0xFF),
                (byte) (a & 0xFF)
        };
    }

    public static byte[] long2Bytes(long x) {
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.putLong(x);
        return buffer.array();
    }

    public static long bytes2Long(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.put(bytes, 0, bytes.length);
        buffer.flip();//need flip
        return buffer.getLong();
    }

    public static boolean bytes2Boolean(byte[] bytes){
        return bytes[0] == (byte) 1;
    }

    public static byte[] boolean2Bytes(boolean b){
        return new byte[]{b?(byte) 1:(byte) 0};
    }

    public static double bytes2Double(byte[] bytes){
        return Double.longBitsToDouble(bytes2Long(bytes));
    }

    public static byte[] double2bytes(double d){
        return long2Bytes(Double.doubleToLongBits(d));
    }

    public static float bytes2Float(byte[] bytes){
        return Float.intBitsToFloat(bytes2Int(bytes));
    }

    public static byte[] float2bytes(float f){
        return int2Bytes(Float.floatToIntBits(f));
    }

    private static final char HEX_DIGITS[] =
            {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    public static String bytes2HexString(final byte[] bytes) {
        if (bytes == null) return "";
        int len = bytes.length;
        if (len <= 0) return "";
        char[] ret = new char[len << 1];
        for (int i = 0, j = 0; i < len; i++) {
            ret[j++] = HEX_DIGITS[bytes[i] >> 4 & 0x0f];
            ret[j++] = HEX_DIGITS[bytes[i] & 0x0f];
        }
        return new String(ret);
    }

    public static byte[] hexString2Bytes(String hexString) {
        if (isSpace(hexString)) return null;
        int len = hexString.length();
        if (len % 2 != 0) {
            hexString = "0" + hexString;
            len = len + 1;
        }
        char[] hexBytes = hexString.toUpperCase().toCharArray();
        byte[] ret = new byte[len >> 1];
        for (int i = 0; i < len; i += 2) {
            ret[i >> 1] = (byte) (hex2Dec(hexBytes[i]) << 4 | hex2Dec(hexBytes[i + 1]));
        }
        return ret;
    }

    public static int hex2Dec(final char hexChar) {
        if (hexChar >= '0' && hexChar <= '9') {
            return hexChar - '0';
        } else if (hexChar >= 'A' && hexChar <= 'F') {
            return hexChar - 'A' + 10;
        } else {
            throw new IllegalArgumentException();
        }
    }

    private static boolean isSpace(final String s) {
        if (s == null) return true;
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

}
