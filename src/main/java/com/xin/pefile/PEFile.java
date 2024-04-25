package com.xin.pefile;

import com.xin.pefile.constants.ResourceTypes;
import com.xin.pefile.io.PEFileReader;
import com.xin.pefile.io.IPEFileReader;
import lombok.Getter;
import lombok.SneakyThrows;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * PE文件解析类，用于解析并获取PE文件（可执行文件）的各种信息，如节头部、资源目录表等。
 * @author tongxin
 * @date 2024/4/17 22:34
 */

@Getter
public class PEFile implements AutoCloseable {

    /**
     * 用于读取文件数据的接口
     */
    private final IPEFileReader dataReader;
    /**
     * DOS头
     */
    private final DosHeader dosHeader;
    /**
     * NT头
     */
    private final NtHeader ntHeader;
    /**
     * 节头部集合
     */
    private final SectionHeaders sectionHeaders;
    /**
     * 资源目录表
     */
    private final ResourceDirectoryTable resourceDirectoryTable;
    /**
     * 构造函数，初始化PE文件的各种头部信息和资源目录表。
     *
     * @param file 要解析的PE文件
     * @throws IOException 当读取文件时发生IO异常
     */
    @SneakyThrows
    PEFile(File file) {
        this.dataReader = new PEFileReader(file);
        this.dosHeader = new DosHeader(this.dataReader.read(DosHeader.DOS_HEADER_SIZE));
        this.ntHeader = new NtHeader(this.dataReader,this.dosHeader.getAddressOfNewExeHeader());
        this.sectionHeaders = new SectionHeaders(this.dataReader,
                this.dosHeader.getAddressOfNewExeHeader() +
                        NtHeader.NT_HEADER_MAGIC_LENGTH + FileHeader.FILE_HEADER_SIZE +
                        this.ntHeader.getFileHeader().getSizeOfOptionalHeader(),
                this.ntHeader.getFileHeader().getNumberOfSections());
        this.resourceDirectoryTable = new ResourceDirectoryTable(this.dataReader,
                this.sectionHeaders.get(SectionHeaders.RSRC).getPointerToRawData());
    }

    /**
     * 根据资源ID获取资源数据入口列表。
     *
     * @param id 资源ID
     * @return 资源数据入口列表
     */
    public List<ResourceDataEntry> getResourceDataEntry(long id) {
        ResourceDirectoryEntry resourceDirectoryEntry = this.resourceDirectoryTable.getResourceDirectoryEntry(id);
        return getResourceDataEntry(resourceDirectoryEntry);
    }
    /**
     * 获取版本信息。
     *
     * @return 版本信息对象，如果找不到则返回空版本信息对象。
     */
    public VersionInfo getVersionInfo() {
        List<ResourceDataEntry> entries = getResourceDataEntry(ResourceTypes.RT_VERSION);
        return entries.stream().map(entry -> {
            SectionHeader header = this.sectionHeaders.get(SectionHeaders.RSRC);
            long offset = header.getPointerToRawData() + entry.getDataRva() - header.getVirtualAddress();
            return new VersionInfo(this.dataReader, offset);
        }).findFirst().orElse(new VersionInfo());
    }
    /**
     * 根据资源目录项获取资源数据入口列表。
     *
     * @param entry 资源目录项
     * @return 资源数据入口列表
     */
    private List<ResourceDataEntry> getResourceDataEntry(ResourceDirectoryEntry entry) {
        List<ResourceDataEntry> entries = new ArrayList<>();
        if (entry.isLeaf()) {
            return Collections.singletonList(entry.getResourceDataEntry());
        }
        entry.getResourceDirectoryTable().getEntries().forEach((k, v) -> {
            entries.addAll(getResourceDataEntry(v));
        });
        return entries;
    }
    /**
     * 关闭数据读取器，释放资源。
     *
     * @throws IOException 当关闭读取器时发生IO异常
     */
    @Override
    @SneakyThrows
    public void close() {
        this.dataReader.close();
    }
}
