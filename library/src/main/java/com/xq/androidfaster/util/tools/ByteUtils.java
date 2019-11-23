package com.xq.androidfaster.util.tools;

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

    private static Map<Class, Field[]> classMap = new HashMap<>();

    public interface ByteConverter{

        public byte[] convertToBytes(Object o,Field[] fields,int fieldPosition,byte[] afterBytes);

        public Object convertFromBytes(Class mClass,Field[] fields,int fieldPosition,ByteBuffer byteBuffer);
    }

    //注意：下4列方法被排序的字段必须使用@FieldOrder进行注解使用

    public static byte[] object2Bytes(Object o) throws Exception{
        return object2Bytes(o,null);
    }

    public static byte[] object2Bytes(Object o,ByteConverter converter) throws Exception {
        if (o == null) return null;
        return object2Bytes(o,o.getClass(),converter);
    }

    private static byte[] object2Bytes(Object o, Class mClass, ByteConverter converter) throws Exception {

        ByteBuffer byteBuffer = ByteBuffer.allocate(64*1000);

        if (!classMap.containsKey(mClass)){
            classMap.put(mClass,getOrderedFields(getAllDeclaredFields(mClass)));
        }

        Field[] fields = classMap.get(mClass);

        for (int i=0;i<fields.length;i++){

            Field field = fields[i];

            //设置强制访问
            if (!field.isAccessible())
                field.setAccessible(true);

            // 静态字段 等情况不受理
            if (Modifier.isStatic(field.getModifiers())){
                continue;
            }

            Object value = null;

            if (converter != null && (value = converter.convertToBytes(o,fields,i,byteBuffer2Bytes(byteBuffer))) != null && ((byte[])value).length >0 )
            {
                byteBuffer.put((byte[]) value);
            }
            else
            {
                value = field.get(o);

                if (field.getType().isArray() || List.class.isAssignableFrom(field.getType()))
                {
                    if (i == 0){
                        throw  new Exception("what the fucking");
                    }

                    if (!(fields[i-1].getType().isPrimitive())){
                        throw  new Exception(fields[i-1].getName() + " must be Number");
                    }

                    if (value == null){
                        throw  new Exception(field.getName() + " is null");
                    }

                    if (field.getType().getComponentType().isPrimitive()){
                        for (int j=0;j<((Number) fields[i-1].get(o)).intValue();j++){
                            if (field.getType().isArray()){
                                putPrimitive(ArrayUtils.get(value,j),field.getType().getComponentType(),byteBuffer);
                            } else {
                                putPrimitive(((List)value).get(j),field.getType().getComponentType(),byteBuffer);
                            }
                        }
                    } else {
                        for (int j=0;j<((Number) fields[i-1].get(o)).intValue();j++){

                            byte[] bytes = null;

                            if (field.getType().isArray()){
                                bytes = object2Bytes(ArrayUtils.get(value,j),field.getType().getComponentType(),converter);
                            } else {
                                bytes = object2Bytes(((List)value).get(j),field.getType().getComponentType(),converter);
                            }

                            if (bytes != null && bytes.length > 0)
                                byteBuffer.put(bytes);
                        }
                    }
                }
                else
                {
                    if (!putPrimitive(value,field.getType(),byteBuffer)){

                        if (value != null)
                            byteBuffer.put(object2Bytes(value,field.getType(),converter));
                    }
                }
            }
        }

        return byteBuffer2Bytes(byteBuffer);

    }

    private static boolean putPrimitive(Object value,Class valueClass,ByteBuffer byteBuffer){
        if (long.class.isAssignableFrom(valueClass) || Long.class.isAssignableFrom(valueClass)){
            byteBuffer.putLong(value == null?0 : (Long) value);
            return true;
        }
        else    if (int.class.isAssignableFrom(valueClass) || Integer.class.isAssignableFrom(valueClass)){
            byteBuffer.putInt(value == null?0 : (Integer) value);
            return true;
        }
        else    if (short.class.isAssignableFrom(valueClass) || Short.class.isAssignableFrom(valueClass)){
            byteBuffer.putShort(value == null?0 : (Short) value);
            return true;
        }
        else    if (char.class.isAssignableFrom(valueClass) || Character.class.isAssignableFrom(valueClass)){
            byteBuffer.putChar(value == null?0 : (Character) value);
            return true;
        }
        else    if (byte.class.isAssignableFrom(valueClass) || Byte.class.isAssignableFrom(valueClass)){
            byteBuffer.put(value == null?0 : (Byte) value);
            return true;
        }
        else    if (boolean.class.isAssignableFrom(valueClass) || Boolean.class.isAssignableFrom(valueClass)){
            byteBuffer.put(value == null?0 : (Boolean)value?(byte) 1:(byte) 0);
            return true;
        }
        else    if (double.class.isAssignableFrom(valueClass) || Double.class.isAssignableFrom(valueClass)){
            byteBuffer.putDouble(value == null?0 : (Double) value);
            return true;
        }
        else    if (float.class.isAssignableFrom(valueClass) || Float.class.isAssignableFrom(valueClass)){
            byteBuffer.putFloat(value == null?0 : (Float) value);
            return true;
        }
        return false;
    }

    public static <T>T bytes2Object(byte[] bytes,Class<T> mClass) throws Exception {
        return bytes2Object(bytes,mClass,null);
    }

    public static <T>T bytes2Object(byte[] bytes,Class<T> mClass,ByteConverter converter) throws Exception{
        return bytes2Object(ByteBuffer.wrap(bytes),mClass,converter);
    }

    private static <T>T bytes2Object(ByteBuffer byteBuffer,Class<T> mClass,ByteConverter converter) throws Exception {
        if (mClass == null)  return null;

        Constructor<T> constructor = mClass.getDeclaredConstructor();
        constructor.setAccessible(true);
        T o = constructor.newInstance();

        if (!classMap.containsKey(mClass)){
            classMap.put(mClass,getOrderedFields(getAllDeclaredFields(mClass)));
        }

        Field[] fields = classMap.get(mClass);

        for (int i=0;i<fields.length;i++){

            Field field = fields[i];

            //设置强制访问
            if (!field.isAccessible())
                field.setAccessible(true);

            // 静态字段 等情况不受理
            if (Modifier.isStatic(field.getModifiers())){
                continue;
            }

            Object value = null;

            if (converter != null && (value = converter.convertFromBytes(mClass,fields,i,byteBuffer)) != null)
            {
                field.set(o,value);
            }
            else
            {
                if (field.getType().isArray() || List.class.isAssignableFrom(field.getType()))
                {
                    if (i == 0){
                        throw  new Exception("what the fucking");
                    }

                    if (!(fields[i-1].getType().isPrimitive())){
                        throw  new Exception(fields[i-1].getName() + " must be Number");
                    }

                    if (field.getType().isArray()){
                        value = Array.newInstance(field.getType().getComponentType(),((Number) fields[i-1].get(o)).intValue());

                        for (int j=0;j<((Number) fields[i-1].get(o)).intValue();j++){
                            if (field.getType().getComponentType().isPrimitive()){
                                ArrayUtils.set(value, j, getPrimitive(field.getType().getComponentType(),byteBuffer));
                            } else {
                                ArrayUtils.set(value, j, bytes2Object(byteBuffer, field.getType().getComponentType(), converter));
                            }
                        }
                    } else {
                        value = new LinkedList<>();

                        for (int j=0;j<((Number) fields[i-1].get(o)).intValue();j++){
                            if (field.getType().getComponentType().isPrimitive()){
                                ((List)value).set(j,getPrimitive(field.getType().getComponentType(),byteBuffer));
                            } else {
                                ((List)value).set(j,bytes2Object(byteBuffer,field.getType().getComponentType(),converter));
                            }
                        }
                    }

                    field.set(o,value);
                }
                else
                {
                    if ((value = getPrimitive(field.getType(),byteBuffer)) == null){
                        value = bytes2Object(byteBuffer,field.getType(),converter);
                    }

                    if (value != null){
                        field.set(o,value);
                    }
                }
            }
        }

        return o;
    }

    private static Object getPrimitive(Class valueClass,ByteBuffer byteBuffer){
        if (long.class.isAssignableFrom(valueClass) || Long.class.isAssignableFrom(valueClass)){
            return byteBuffer.getLong();
        }
        else    if (int.class.isAssignableFrom(valueClass) || Integer.class.isAssignableFrom(valueClass)){
            return byteBuffer.getInt();
        }
        else    if (short.class.isAssignableFrom(valueClass) || Short.class.isAssignableFrom(valueClass)){
            return byteBuffer.getShort();
        }
        else    if (char.class.isAssignableFrom(valueClass) || Character.class.isAssignableFrom(valueClass)){
            return byteBuffer.getChar();
        }
        else    if (byte.class.isAssignableFrom(valueClass) || Byte.class.isAssignableFrom(valueClass)){
            return byteBuffer.get();
        }
        else    if (boolean.class.isAssignableFrom(valueClass) || Boolean.class.isAssignableFrom(valueClass)){
            return byteBuffer.get() == (byte)1;
        }
        else    if (double.class.isAssignableFrom(valueClass) || Double.class.isAssignableFrom(valueClass)){
            return byteBuffer.getDouble();
        }
        else    if (float.class.isAssignableFrom(valueClass) || Float.class.isAssignableFrom(valueClass)){
            return byteBuffer.getFloat();
        }
        return null;
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

    private static Field[] getOrderedFields(Field[]fields){
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
        int len = byteBuffer.position() > 0? byteBuffer.position() : byteBuffer.limit();
        byte[] bytes = new byte[len];
        System.arraycopy(byteBuffer.array(),0,bytes,0,len);
        return bytes;
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
