package com.xin.pefile;

import com.xin.pefile.io.IByteArray;
import com.xin.pefile.io.IPEFileReader;
import com.xin.pefile.util.PeUtil;
import lombok.Data;import lombok.ToString;

import java.nio.charset.StandardCharsets;

/**
 * @author tongxin
 * @date 2024/4/22 11:03
 */
@Data
@ToString
public class StringTable {
    private final static String STRING_TABLE_MAGIC = "040904B0";
    private final static int LENGTH = 22;
    private int length;
    private int valueLength;
    private int type;
    private String signature;
    private long offset;
    private int padding;

    StringTable(IPEFileReader dataReader, long offset) {
        this.offset = offset;
        IByteArray byteArray = dataReader.read(offset, LENGTH);
        this.length = byteArray.readWord();
        this.valueLength = byteArray.readWord();
        this.type = byteArray.readWord();
        this.signature = byteArray.readString(STRING_TABLE_MAGIC.length() * 2, StandardCharsets.UTF_16LE);
        this.padding = PeUtil.calculatePadding(dataReader.getOffset());
    }

    public long getEndOffset() {
        return offset + LENGTH + valueLength + padding;
    }
}
