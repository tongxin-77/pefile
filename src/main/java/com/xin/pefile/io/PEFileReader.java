package com.xin.pefile.io;

import lombok.SneakyThrows;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * DataReader类实现了IDataReader接口，用于从文件中读取数据。
 * @author tongxin
 * @date 2024/4/17 22:46
 */
public class PEFileReader implements IPEFileReader {
    private final FileInputStream fis; // 文件输入流

    /**
     * 构造函数，创建一个DataReader实例。
     *
     * @param file 要读取数据的文件
     * @throws IOException 如果打开文件失败，则抛出IOException
     */
    @SneakyThrows
    public PEFileReader(File file) {
        this.fis = new FileInputStream(file);
    }

    /**
     * 从文件中读取指定长度的数据。
     *
     * @param length 要读取的数据长度
     * @return 读取到的数据，封装在ByteArray对象中
     * @throws IOException 如果读取数据失败，则抛出IOException
     */
    @Override
    @SneakyThrows
    public ByteArray read(int length) {
        byte[] data = new byte[length]; // 创建一个指定长度的字节数组
        fis.read(data); // 从文件中读取数据到字节数组
        return new ByteArray(data); // 返回包含读取数据的ByteArray对象
    }

    /**
     * 从文件的指定偏移位置开始读取指定长度的数据。
     *
     * @param offset 数据的起始读取位置
     * @param length 要读取的数据长度
     * @return 读取到的数据，封装在ByteArray对象中
     * @throws IOException 如果设置偏移位置或读取数据失败，则抛出IOException
     */
    @Override
    public ByteArray read(long offset, int length) {
        this.offset(offset); // 设置读取的起始偏移位置
        return this.read(length); // 读取指定长度的数据
    }

    /**
     * 设置文件读取的偏移位置。
     *
     * @param offset 要设置的偏移量
     * @throws IOException 如果设置偏移位置失败，则抛出IOException
     */
    @Override
    @SneakyThrows
    public void offset(long offset) {
        this.fis.getChannel().position(offset); // 设置文件通道的当前位置为指定的偏移量
    }

    /**
     * 获取当前文件读取的偏移位置。
     *
     * @return 当前的偏移量
     * @throws IOException 如果获取偏移位置失败，则抛出IOException
     */
    @Override
    @SneakyThrows
    public long getOffset() {
        return this.fis.getChannel().position(); // 获取文件通道的当前位置
    }

    /**
     * 关闭文件输入流。
     *
     * @throws IOException 如果关闭文件输入流失败，则抛出IOException
     */
    @Override
    @SneakyThrows
    public void close() {
        this.fis.close(); // 关闭文件输入流
    }
}
