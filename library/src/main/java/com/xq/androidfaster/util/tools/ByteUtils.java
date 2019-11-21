package com.xq.androidfaster.util.tools;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

public final class ByteUtils {

    private static Map<Class, Field[]> classMap = new HashMap<>();
    public static byte[] objectField2Bytes(Object o) throws Exception {
        if (o == null) return null;
        return objectField2Bytes(o,o.getClass());
    }

    private static byte[] objectField2Bytes(Object o,Class mClass) throws Exception {

        ByteBuffer byteBuffer = ByteBuffer.allocate(64*1000);

        if (!classMap.containsKey(mClass)){
            classMap.put(mClass,mClass.getDeclaredFields());
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

                    byte[] bytes = objectField2Bytes(value[j],field.getType().getComponentType());
                    if (bytes != null && bytes.length > 0)
                        byteBuffer.put(bytes);
                }
            } else {

                Object value = field.get(o);

                if (field.getType().isAssignableFrom(long.class) || field.getType().isAssignableFrom(Long.class)){
                    byteBuffer.putLong(value == null?0 : (Long) value);
                }
                else    if (field.getType().isAssignableFrom(int.class) || field.getType().isAssignableFrom(Integer.class)){
                    byteBuffer.putInt(value == null?0 : (Integer) value);
                }
                else    if (field.getType().isAssignableFrom(short.class) || field.getType().isAssignableFrom(Short.class)){
                    byteBuffer.putShort(value == null?0 : (Short) value);
                }
                else    if (field.getType().isAssignableFrom(char.class) || field.getType().isAssignableFrom(Character.class)){
                    byteBuffer.putChar(value == null?0 : (Character) value);
                }
                else    if (field.getType().isAssignableFrom(byte.class) || field.getType().isAssignableFrom(Byte.class)){
                    byteBuffer.put(value == null?0 : (Byte) value);
                }
                else    if (field.getType().isAssignableFrom(boolean.class) || field.getType().isAssignableFrom(Boolean.class)){
                    byteBuffer.put(value == null?0 : (Boolean)value?(byte) 1:(byte) 0);
                }
                else    if (field.getType().isAssignableFrom(double.class) || field.getType().isAssignableFrom(Double.class)){
                    byteBuffer.putDouble(value == null?0 : (Double) value);
                }
                else    if (field.getType().isAssignableFrom(float.class) || field.getType().isAssignableFrom(Float.class)){
                    byteBuffer.putFloat(value == null?0 : (Float) value);
                }
                else    if (value != null){
                    byteBuffer.put(objectField2Bytes(value,field.getType()));
                }
            }
        }

        return byteBuffer2Bytes(byteBuffer);

    }

    public static Object bytes2ObjectField(byte[] bytes,Class mClass) throws Exception{
        return bytes2ObjectField(ByteBuffer.wrap(bytes),mClass);
    }

    private static Object bytes2ObjectField(ByteBuffer byteBuffer,Class mClass) throws Exception {
        if (mClass == null)  return null;

        Constructor constructor = mClass.getDeclaredConstructor();
        constructor.setAccessible(true);
        Object o = constructor.newInstance();

        if (!classMap.containsKey(mClass)){
            classMap.put(mClass,mClass.getDeclaredFields());
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
                    value[j] = bytes2ObjectField(byteBuffer,field.getType().getComponentType());
                }
            } else {

                if (field.getType().isAssignableFrom(long.class) || field.getType().isAssignableFrom(Long.class)){
                    field.set(o,byteBuffer.getLong());
                }
                else    if (field.getType().isAssignableFrom(int.class) || field.getType().isAssignableFrom(Integer.class)){
                    field.set(o,byteBuffer.getInt());
                }
                else    if (field.getType().isAssignableFrom(short.class) || field.getType().isAssignableFrom(Short.class)){
                    field.set(o,byteBuffer.getShort());
                }
                else    if (field.getType().isAssignableFrom(char.class) || field.getType().isAssignableFrom(Character.class)){
                    field.set(o,byteBuffer.getChar());
                }
                else    if (field.getType().isAssignableFrom(byte.class) || field.getType().isAssignableFrom(Byte.class)){
                    field.set(o,byteBuffer.get());
                }
                else    if (field.getType().isAssignableFrom(boolean.class) || field.getType().isAssignableFrom(Boolean.class)){
                    field.set(o,byteBuffer.get() == (byte)1);
                }
                else    if (field.getType().isAssignableFrom(double.class) || field.getType().isAssignableFrom(Double.class)){
                    field.set(o,byteBuffer.getDouble());
                }
                else    if (field.getType().isAssignableFrom(float.class) || field.getType().isAssignableFrom(Float.class)){
                    field.set(o,byteBuffer.getFloat());
                }
                else {
                    field.set(o,bytes2ObjectField(byteBuffer,field.getType()));
                }
            }
        }

        return o;

    }

    public static byte[] concatBytes(byte[]... bytess){
        ByteBuffer byteBuffer = ByteBuffer.allocate(64*1024);
        for (byte[] bytes : bytess)
            byteBuffer.put(bytes);
        byteBuffer.flip();
        byte[] bytes = new byte[byteBuffer.limit()];
        byteBuffer.get(bytes);
        return bytes;
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
        byte[] bytes = new byte[byteBuffer.position()];
        System.arraycopy(byteBuffer.array(),0,bytes,0,byteBuffer.position());
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

    public static byte[] short2Bytes(short c) {
        byte[] b = new byte[2];
        b[0] = (byte) ((c & 0xFF00) >> 8);
        b[1] = (byte) (c & 0xFF);
        return b;
    }

    public static short bytes2Short(byte[] b) {
        short c = (short) (((b[0] & 0xFF) << 8) | (b[1] & 0xFF));
        return c;
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
        buffer.putLong(0, x);
        return buffer.array();
    }

    public static long bytes2Long(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.put(bytes, 0, bytes.length);
        buffer.flip();//need flip
        return buffer.getLong();
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
