package com.xin.pefile;

import com.xin.pefile.io.IByteArray;
import com.xin.pefile.io.IPEFileReader;
import com.xin.pefile.util.PeUtil;
import lombok.Getter;import lombok.ToString;

import java.nio.charset.StandardCharsets;

/**
 * @author tongxin
 * @date 2024/4/22 11:03
 */
@Getter
@ToString
public class StringFileInfo {
    private final static int SIGNATURE_LENGTH = 28;
    public final static int LENGTH = SIGNATURE_LENGTH + 6;
    private final static String STRING_FILE_INFO_MAGIC = "StringFileInfo";

    private final int length;
    private final int valueLength;
    private final int type;
    private final String signature;
    private final int padding;
    private final long offset;

    public StringFileInfo(IPEFileReader dataReader, long offset) {
        this.offset = offset;
        IByteArray byteArray = dataReader.read(offset, LENGTH);
        this.length = byteArray.readWord();
        this.valueLength = byteArray.readWord();
        this.type = byteArray.readWord();
        this.signature = byteArray.readString(SIGNATURE_LENGTH, StandardCharsets.UTF_16LE);
        if (!STRING_FILE_INFO_MAGIC.equals(this.signature)) {
            throw new RuntimeException("StringFileInfo signature error");
        }
        this.padding = PeUtil.calculatePadding(dataReader.getOffset());
    }

    public long getEndOffset() {
        return offset + LENGTH + padding;
    }
}
