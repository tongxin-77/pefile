package com.xin.pefile;

import com.xin.pefile.io.IPEFileReader;
import lombok.Getter;
import lombok.SneakyThrows;

import java.io.IOException;

/**
 * 解析文件头部信息，包含文件签名、文件头和可选头。
 * @author tongxin
 * @date 2024/4/18 21:49
 */
@Getter
public class NtHeader {
    /**
     * NT头的魔数，用于标识PE格式的文件。PE是Windows可执行文件的格式。
     */
    private final static long NT_HEADER_MAGIC = 0x4550;

    /**
     * NT_HEADER_MAGIC的字节长度，用于确保在读取文件时对齐到正确的字节位置。
     */
    public final static int NT_HEADER_MAGIC_LENGTH = 4;

    /**
     * 文件的签名，用于验证文件的格式和完整性。
     */
    private final long signature;

    /**
     * 文件头，包含有关文件的基本信息，如文件大小、入口点等。
     */
    private final FileHeader fileHeader;

    /**
     * 可选头，包含关于可执行文件的更详细的信息，如DLL标志、基地址等。
     */
    private final OptionalHeader optionalHeader;

    @SneakyThrows
    NtHeader(IPEFileReader dataReader, long offset) {
        // 读取并验证MZ签名
        this.signature = dataReader.read(offset,NT_HEADER_MAGIC_LENGTH).readDWord();
        if (NT_HEADER_MAGIC != signature) {
            throw new IOException("Invalid PE file: Missing PE signature");
        }
        this.fileHeader = new FileHeader(dataReader.read(FileHeader.FILE_HEADER_SIZE));
        this.optionalHeader = new OptionalHeader(dataReader.read(this.fileHeader.getSizeOfOptionalHeader()));
    }
}
