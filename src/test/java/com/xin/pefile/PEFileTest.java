package com.xin.pefile;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

/**
 * @author tongxin
 * @date 2024/4/17 23:48
 */
class PEFileTest {
    @Test
    public void testCreatePEFile() throws IOException {
        File file = new File("C:\\Users\\16132\\Downloads\\douyin-downloader-v3.5.1-win32-ia32-douyinDownload1-wid-8IagZoKvO9o.exe");
        try(PEFile peFile = new PEFile(file)) {
            VersionInfo versionInfo= peFile.getVersionInfo();
            System.out.println(versionInfo.toString());
        }

    }

}