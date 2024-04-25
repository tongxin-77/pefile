package com.xin.pefile;

import com.xin.pefile.io.IPEFileReader;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author tongxin
 * @date 2024/4/20 17:25
 */
public class SectionHeaders {
    public final static String TEXT = ".text";
    public final static String DATA = ".data";
    public final static String BSS = ".bss";
    public final static String RDATA = ".rdata";
    public final static String IDATA = ".idata";
    public final static String TLS = ".tls";
    public final static String PDATA = ".pdata";
    public final static String EXTRA = ".extra";
    public final static String RSRC = ".rsrc";
    private final Map<String, SectionHeader> sectionHeaders = new HashMap<>();

    SectionHeaders(IPEFileReader dataReader, long offset, int count) {
        dataReader.offset(offset);
        Arrays.stream(new int[count], 0, count)
                .mapToObj(i -> new SectionHeader(dataReader.read(0x28)))
                .forEach(header -> sectionHeaders.put(header.getName().trim(), header));
    }

    public SectionHeader get(String name) {
        return sectionHeaders.get(name);
    }

}
