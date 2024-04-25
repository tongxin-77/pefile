package com.xin.pefile.io;

import java.nio.charset.Charset;

/**
 * IByteArray 接口定义了操作字节数组的一系列方法。
 * @author tongxin
 * @date 2024/4/18 0:11
 */
public interface IByteArray {

    /**
     * 读取一个字符串，使用平台默认字符集。
     *
     * @return 从字节数组中读取的字符串
     */
    String readString();

    /**
     * 读取一个指定字符集的字符串。
     *
     * @param charset 指定的字符集
     * @return 从字节数组中读取的字符串
     */
    String readString(Charset charset);

    /**
     * 读取指定长度的字符串，使用平台默认字符集。
     *
     * @param length 要读取的字符串长度
     * @return 从字节数组中读取的字符串
     */
    String readString(int length);

    /**
     * 读取指定长度、指定字符集的字符串。
     *
     * @param length  要读取的字符串长度
     * @param charset 指定的字符集
     * @return 从字节数组中读取的字符串
     */
    String readString(int length, Charset charset);

    /**
     * 从指定偏移开始读取指定长度、指定字符集的字符串。
     *
     * @param offset 读取的起始偏移位置
     * @param length 要读取的字符串长度
     * @param charset 指定的字符集
     * @return 从字节数组中读取的字符串
     */
    String readString(int offset, int length, Charset charset);

    /**
     * 读取所有剩余的字节。
     *
     * @return 从字节数组中读取的所有字节
     */
    byte[] readBytes();

    /**
     * 读取指定长度的字节。
     *
     * @param length 要读取的字节长度
     * @return 从字节数组中读取的字节
     */
    byte[] readBytes(int length);

    /**
     * 读取一个字节并转换为无符号整数。
     *
     * @return 读取的字节转换成的无符号整数
     */
    int readWord();

    /**
     * 读取两个字节并转换为无符号长整数。
     *
     * @return 读取的两个字节转换成的无符号长整数
     */
    long readDWord();

    /**
     * 从当前位置读取指定长度的数据到一个新的 ByteArray 对象中。
     *
     * @param length 要读取的数据长度
     * @return 包含了指定长度数据的新 ByteArray 对象
     */
    ByteArray read(int length);
}

