package com.xin.pefile;

import com.xin.pefile.io.ByteArray;
import lombok.Data;
import lombok.Getter;

/**
 * @author tongxin
 * @date 2024/4/20 17:28
 */
@Getter
public class Misc {
    /**
     * 长度
     */
    public final static int LENGTH = 4;
    /**
     * 物理地址
     */
    private final long physicalAddress;
    /**
     * 虚拟大小
     */
    private final long virtualSize;

    Misc(ByteArray byteArray) {
        long data = byteArray.readDWord();
        physicalAddress = data;
        virtualSize = data;
    }
}
