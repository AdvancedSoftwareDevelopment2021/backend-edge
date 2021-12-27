package cn.edu.sjtu.ist.ecssbackendedge.utils.convert;

import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.enumeration.DataType;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Objects;

import cn.hutool.core.convert.Convert;

/**
 * @author dyanjun
 * @date 2021/12/26 17:44
 */
public class DataUtil {
    /**
     * 通过类型转换数据
     *
     * @param type  String Type, short/int/long/float/double/boolean/string
     * @param value String Value
     * @param <T>   T
     * @return T
     */
    public static <T> T value(String type, String value) {
        return Convert.convertByClassName(getTypeClassName(type), value);
    }

    /**
     * Base 64 解码
     *
     * @param content string
     * @return string
     */
    public static String base64Encode(String content) {
        byte[] bytes = content.getBytes(StandardCharsets.UTF_8);
        return Base64.getEncoder().encodeToString(bytes);
    }

    /**
     * Base 64 编码
     *
     * @param content string
     * @return string
     */
    public static String base64Decode(String content) {
        byte[] bytes = content.getBytes(StandardCharsets.UTF_8);
        return new String(Base64.getDecoder().decode(bytes));
    }

    /**
     * 将byte[]转成十六进制字符串
     *
     * @param bytes Byte Array
     * @return String
     */
    public static String bytesToHex(byte[] bytes) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() < 2) {
                sb.append(0);
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    /**
     * byte数组到int的转换(大端)
     *
     * @param bytes Byte Array
     * @return Integer
     */
    public static int bytesToInt(byte[] bytes) {
        byte[] temp = new byte[4];
        int length = bytes.length;
        System.arraycopy(bytes, 0, temp, 0, length);
        for (int i = length; i < 4; i++) {
            temp[i] = 0x00;
        }
        int int1 = temp[3] & 0xff;
        int int2 = (temp[2] & 0xff) << 8;
        int int3 = (temp[1] & 0xff) << 16;
        int int4 = (temp[0] & 0xff) << 24;

        return int1 | int2 | int3 | int4;
    }

    /**
     * byte数组到int的转换(小端)
     *
     * @param bytes Byte Array
     * @return Integer
     */
    public static int bytesToIntLE(byte[] bytes) {
        byte[] temp = new byte[4];
        int length = bytes.length;
        System.arraycopy(bytes, 0, temp, 0, length);
        for (int i = length; i < 4; i++) {
            temp[i] = 0x00;
        }
        int int1 = temp[0] & 0xff;
        int int2 = (temp[1] & 0xff) << 8;
        int int3 = (temp[2] & 0xff) << 16;
        int int4 = (temp[3] & 0xff) << 24;
        return int1 | int2 | int3 | int4;
    }

    /**
     * 将byte[]转成Ascii码
     *
     * @param bytes Byte Array
     * @return String
     */
    public static String bytesToAscii(byte[] bytes) {
        String asciiStr = null;
        try {
            asciiStr = new String(bytes, "ISO8859-1");
        } catch (UnsupportedEncodingException ignored) {
        }
        return asciiStr;
    }

    /**
     * 将byte[]颠倒
     *
     * @param bytes Byte Array
     * @return Byte Array
     */
    public static byte[] byteReverse(byte[] bytes) {
        int length = bytes.length;
        byte[] reverse = new byte[length];
        for (int i = 0; i < length; i++) {
            reverse[length - 1 - i] = bytes[i];
        }
        return reverse;
    }

    /**
     * 合并byte[]
     *
     * @param bytes Byte Array
     * @return Byte Array
     */
    public static byte[] mergerBytes(byte[]... bytes) {
        int lengthByte = 0;
        for (byte[] value : bytes) {
            lengthByte += value.length;
        }
        byte[] allByte = new byte[lengthByte];
        int countLength = 0;
        for (byte[] b : bytes) {
            System.arraycopy(b, 0, allByte, countLength, b.length);
            countLength += b.length;
        }
        return allByte;
    }

    /**
     * 获取字节间的异或值
     *
     * @param bytes Byte Array
     * @return Byte
     */
    public static byte xorBytes(byte[]... bytes) {
        byte xor = 0x00;
        for (byte[] value : bytes) {
            for (byte b : value) {
                xor ^= b;
            }
        }
        return xor;
    }

    /**
     * 获取基本类型 Class Name
     *
     * @param type String Type, short/int/long/float/double/boolean/string
     * @return Class Name
     */
    public static String getTypeClassName(String type) {
        String className = "java.lang.String";
        switch (Objects.requireNonNull(DataType.fromString(type))) {
            case INTEGER:
                className = "java.lang.Integer";
                break;
            case DOUBLE:
                className = "java.lang.Double";
                break;
            case BOOLEAN:
                className = "java.lang.Boolean";
                break;
            case OBJECT:
                className = "java.lang.Object";
                break;
            default:
                break;
        }
        return className;
    }
}
