package com.xin.pefile.io;

/**
 * IDataReader接口定义了数据读取器的基本操作，允许按照指定长度或偏移量读取数据，并支持自动关闭资源。
 * 继承自AutoCloseable以支持使用try-with-resources语句自动管理资源。
 * @author tongxin
 * @date 2024/4/17 22:53
 */
public interface IPEFileReader extends AutoCloseable{

    /**
     * 读取指定长度的数据。
     *
     * @param length 要读取的数据长度。
     * @return ByteArray对象，包含读取到的数据。
     */
    ByteArray read(int length);

    /**
     * 从指定偏移量开始读取指定长度的数据。
     *
     * @param offset 数据读取的起始偏移量。
     * @param length 要读取的数据长度。
     * @return ByteArray对象，包含从指定偏移量开始读取到的数据。
     */
    ByteArray read(long offset,int length);

    /**
     * 设置数据读取的偏移量。
     *
     * @param offset 要设置的偏移量。
     */
    void offset(long offset);

    /**
     * 获取当前数据读取的偏移量。
     *
     * @return 当前偏移量。
     */
    long getOffset();
}
