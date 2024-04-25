package com.xin.pefile;

import com.xin.pefile.io.IByteArray;
import com.xin.pefile.io.IPEFileReader;
import com.xin.pefile.util.PeUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;import lombok.ToString;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @author tongxin
 * @date 2024/4/21 15:56
 */
@Getter
@ToString
@NoArgsConstructor
public class VersionInfo {
    private final static int LENGTH = 6;
    private final static int VERSION_INFO_LENGTH = 30;
    private final static String VERSION_INFO_MAGIC = "VS_VERSION_INFO";
    private  int length;
    private  int valueLength;
    private  int type;
    private  int padding;
    private  long offset;
    private  FixedFileInfo fixedFileInfo;
    private  StringFileInfo stringFileInfo;
    private  StringTable stringTable;
    private  Map<String, StringTableRecord> records=new HashMap<>();

    VersionInfo(IPEFileReader dataReader, long offset) {
        this.offset = offset;
        IByteArray byteArray = dataReader.read(offset, LENGTH);
        this.length = byteArray.readWord();
        this.valueLength = byteArray.readWord();
        this.type = byteArray.readWord();
        byteArray = dataReader.read(VERSION_INFO_LENGTH);
        byte[] bytes = byteArray.readBytes();
        String str = new String(bytes, StandardCharsets.UTF_16LE);
        if (!VERSION_INFO_MAGIC.equals(str)) {
            throw new RuntimeException("not a version info");
        }
        this.padding = PeUtil.calculatePadding(dataReader.getOffset());
        this.fixedFileInfo = new FixedFileInfo(dataReader, getEndOffset());
        this.stringFileInfo = new StringFileInfo(dataReader, this.fixedFileInfo.getEndOffset());
        this.stringTable = new StringTable(dataReader, this.stringFileInfo.getEndOffset());
        offset = this.stringTable.getEndOffset();
        while (dataReader.getOffset() < this.stringTable.getOffset() + this.stringTable.getLength()) {
            StringTableRecord stringTableRecord = new StringTableRecord(dataReader, offset);
            records.put(stringTableRecord.getKey(), stringTableRecord);
            offset=stringTableRecord.getEndOffset();
        }
    }

    public long getEndOffset() {
        return offset + LENGTH +
                VERSION_INFO_LENGTH + padding;
    }

}
