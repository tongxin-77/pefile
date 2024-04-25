package com.xin.pefile.util;

import com.xin.pefile.io.ByteArray;
import com.xin.pefile.io.IPEFileReader;

import java.nio.charset.StandardCharsets;

/**
 * @author tongxin
 * @date 2024/4/25 10:00
 */
public class PeUtil {
    /**
     * 计算给定偏移量所需的填充字节数量。
     * 该方法主要用于确保数据在存储或处理时按照4字节边界对齐。
     *
     * @param offset 原始偏移量，必须为非负数。
     * @return 返回所需的填充字节数量。如果偏移量已经4字节对齐，则返回0；
     * 否则返回最小的字节数，使得添加该数量的字节后偏移量成为4的倍数。
     * @throws IllegalArgumentException 如果偏移量为负数，抛出此异常。
     */
    public static int calculatePadding(long offset) {
        // 检查偏移量是否为负数
        if (offset < 0) {
            throw new IllegalArgumentException("Offset cannot be negative.");
        }
        int remainder = Math.toIntExact(offset % 4); // 计算偏移量对4取余，以确定是否需要填充
        // 返回所需的填充字节数量
        return (remainder == 0) ? 4 : (4 - remainder) % 4;
    }

    /**
     * 从给定的数据读取器中读取字符串。
     *
     * @param dataReader 用于读取数据的IDataReader接口实例，提供读取和获取当前位置的能力。
     * @return 从数据读取器中解析出的字符串。如果遇到字符串结束标志，则返回空字符串。
     */
    public static String readString(IPEFileReader dataReader) {
        // 记录当前读取位置
        long offset = dataReader.getOffset();
        // 读取接下来的2个字节，用于确定字符串长度
        ByteArray byteArray = dataReader.read(2);
        // 根据读取的长度，以UTF_16LE编码读取字符串
        String str = byteArray.readString(StandardCharsets.UTF_16LE);
        // 如果字符串为空或者以空字符开头，则认为此字符串为空，回退到原始位置
        if (str==null||str.charAt(0) == '\u0000') {
            dataReader.offset(offset);
            return "";
        }
        // 递归读取字符串，直到遇到字符串结束标志
        str = str + readString(dataReader);
        return str;
    }

}
