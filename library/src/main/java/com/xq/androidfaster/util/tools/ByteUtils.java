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
            classMap.put(mClass,getAllDeclaredFields(mClass));
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

            if (field.getType().isArray()){

                if (i == 0){
                    throw  new Exception("what the fucking");
                }

                if (!(fields[i-1].getType().isPrimitive())){
                    throw  new Exception(fields[i-1].getName() + " must be Number");
                }

                for (int j=0;j<((Number) fields[i-1].get(o)).intValue();j++){

                    Object[] value = (Object[]) field.get(o);

                    if (value == null){
                        throw  new Exception(field.getName() + " is null");
                    }

                    byte[] bytes = object2Bytes(value[j],field.getType().getComponentType(),converter);
                    if (bytes != null && bytes.length > 0)
                        byteBuffer.put(bytes);
                }
            } else {

                Object value = null;

                if (converter != null && (value = converter.convertToBytes(o,fields,i,byteBuffer2Bytes(byteBuffer))) != null && ((byte[])value).length >0 ){

                    byteBuffer.put((byte[]) value);

                } else {
                    value = field.get(o);

                    if (long.class.isAssignableFrom(field.getType()) || Long.class.isAssignableFrom(field.getType())){
                        byteBuffer.putLong(value == null?0 : (Long) value);
                    }
                    else    if (int.class.isAssignableFrom(field.getType()) || Integer.class.isAssignableFrom(field.getType())){
                        byteBuffer.putInt(value == null?0 : (Integer) value);
                    }
                    else    if (short.class.isAssignableFrom(field.getType()) || Short.class.isAssignableFrom(field.getType())){
                        byteBuffer.putShort(value == null?0 : (Short) value);
                    }
                    else    if (char.class.isAssignableFrom(field.getType()) || Character.class.isAssignableFrom(field.getType())){
                        byteBuffer.putChar(value == null?0 : (Character) value);
                    }
                    else    if (byte.class.isAssignableFrom(field.getType()) || Byte.class.isAssignableFrom(field.getType())){
                        byteBuffer.put(value == null?0 : (Byte) value);
                    }
                    else    if (boolean.class.isAssignableFrom(field.getType()) || Boolean.class.isAssignableFrom(field.getType())){
                        byteBuffer.put(value == null?0 : (Boolean)value?(byte) 1:(byte) 0);
                    }
                    else    if (double.class.isAssignableFrom(field.getType()) || Double.class.isAssignableFrom(field.getType())){
                        byteBuffer.putDouble(value == null?0 : (Double) value);
                    }
                    else    if (float.class.isAssignableFrom(field.getType()) || Float.class.isAssignableFrom(field.getType())){
                        byteBuffer.putFloat(value == null?0 : (Float) value);
                    }
                    else    {
                        if (value != null) byteBuffer.put(object2Bytes(value,field.getType(),converter));
                    }
                }
            }
        }

        return byteBuffer2Bytes(byteBuffer);

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
            classMap.put(mClass,getAllDeclaredFields(mClass));
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

            if (field.getType().isArray()){

                if (i == 0){
                    throw  new Exception("what the fucking");
                }

                if (!(fields[i-1].getType().isPrimitive())){
                    throw  new Exception(fields[i-1].getName() + " must be Number");
                }

                Object[] value = (Object[]) Array.newInstance(field.getType().getComponentType(),((Number) fields[i-1].get(o)).intValue());

                field.set(o,value);

                for (int j=0;j<((Number) fields[i-1].get(o)).intValue();j++){
                    value[j] = bytes2Object(byteBuffer,field.getType().getComponentType(),converter);
                }
            } else {

                Object value = null;

                if (converter != null && (value = converter.convertFromBytes(mClass,fields,i,byteBuffer)) != null){

                    field.set(o,value);

                } else {
                    if (long.class.isAssignableFrom(field.getType()) || Long.class.isAssignableFrom(field.getType())){
                        value = byteBuffer.getLong();
                    }
                    else    if (int.class.isAssignableFrom(field.getType()) || Integer.class.isAssignableFrom(field.getType())){
                        value = byteBuffer.getInt();
                    }
                    else    if (short.class.isAssignableFrom(field.getType()) || Short.class.isAssignableFrom(field.getType())){
                        value = byteBuffer.getShort();
                    }
                    else    if (char.class.isAssignableFrom(field.getType()) || Character.class.isAssignableFrom(field.getType())){
                        value = byteBuffer.getChar();
                    }
                    else    if (byte.class.isAssignableFrom(field.getType()) || Byte.class.isAssignableFrom(field.getType())){
                        value = byteBuffer.get();
                    }
                    else    if (boolean.class.isAssignableFrom(field.getType()) || Boolean.class.isAssignableFrom(field.getType())){
                        value = byteBuffer.get() == (byte)1;
                    }
                    else    if (double.class.isAssignableFrom(field.getType()) || Double.class.isAssignableFrom(field.getType())){
                        value = byteBuffer.getDouble();
                    }
                    else    if (float.class.isAssignableFrom(field.getType()) || Float.class.isAssignableFrom(field.getType())){
                        value = byteBuffer.getFloat();
                    }
                    else {
                        value = bytes2Object(byteBuffer,field.getType(),converter);
                    }

                    if (value != null) field.set(o,value);
                }
            }
        }

        return o;

    }

    private static Field[] getAllDeclaredFields(Class mClass){
        if (mClass == null || mClass.getName().equals(Object.class.getName())) return null;

        List<Field> list = new LinkedList<>();

        Field[] allParentField = getAllDeclaredFields(mClass.getSuperclass());
        if (allParentField != null && allParentField.length >0)
            list.addAll(Arrays.asList(allParentField));

        list.addAll(getOrderedFieldList(mClass.getDeclaredFields()));

        return list.toArray(new Field[0]);
    }

    private static List<Field> getOrderedFieldList(Field[] fields){
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
        return list;
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
