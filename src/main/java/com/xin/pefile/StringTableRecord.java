package com.xin.pefile;

import com.xin.pefile.io.IByteArray;
import com.xin.pefile.io.IPEFileReader;
import com.xin.pefile.util.PeUtil;
import lombok.Getter;import lombok.ToString;

/**
 * @author tongxin
 * @date 2024/4/23 11:23
 */
@Getter
@ToString
public class StringTableRecord {
    private final static int LENGTH = 6;
    private final int length;
    private final int valueLength;
    private final int type;
    private final String key;
    private final String value;
    private final long offset;
    private final int padding;

    StringTableRecord(IPEFileReader dataReader, long offset) {
        this.offset = offset;
        IByteArray byteArray = dataReader.read(offset, LENGTH);
        this.length = byteArray.readWord();
        this.valueLength = byteArray.readWord();
        this.type = byteArray.readWord();
        this.key = PeUtil.readString(dataReader);
        offset = dataReader.getOffset();
        offset = offset + PeUtil.calculatePadding(offset);
        dataReader.offset(offset);
        this.value = PeUtil.readString(dataReader).trim();
        this.padding = PeUtil.calculatePadding(this.offset + this.length - 2);
    }

    public long getEndOffset() {
        return offset + length - 2 + padding;
    }

}
