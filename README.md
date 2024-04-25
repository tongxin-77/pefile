PEFile Package

## 简介

`PEFile`包提供了一套完整的工具集，用于解析 Microsoft Windows 平台上的 Portable Executable (PE)
文件格式。此包包含了多个精心设计的类和接口,兼容大文件解析，旨在帮助开发者高效、准确地处理 PE 文件的各个组成部分，包括头信息、节区、导入/导出表、重定位信息、调试符号等。

## 主要功能与组件

### [PEFileReader.java](src%2Fmain%2Fjava%2Fcom%2Fxin%2Fpefile%2Fio%2FPEFileReader.java)

功能：实现基本的 PE 文件读取功能，提供对文件数据的低级访问接口。
使用：作为其他解析类的基础，负责打开文件、读取字节流及管理文件访问状态。

### [PEFile.java](src%2Fmain%2Fjava%2Fcom%2Fxin%2Fpefile%2FPEFile.java) （核心解析器）

功能：作为包的核心类，整合上述各类组件，提供统一的 API 用于解析整个 PE 文件。
使用：传入PE文件，即可通过调用相关方法获取所需解析结果。  
目前实现:
> 根据资源ID获取资源数据入口列表 getResourceDataEntry

> 获取版本信息 getVersionInfo

> 根据资源目录项获取资源数据入口列表 getResourceDataEntry

### [ByteArray.java](src%2Fmain%2Fjava%2Fcom%2Fxin%2Fpefile%2Fio%2FByteArray.java)

功能：提供对字节数组进行读取操作的实现类，支持多种数据类型的读取，如字符串、字节、单词和双字。
使用：作为其他解析类的基础，负责读取字节流中的数据，并提供相应的方法用于获取数据。

## 示例

````java
  class PEFileTest {
    @Test
    public void testCreatePEFile() throws IOException {
        File file = new File("./yourexe.exe");
        try (PEFile peFile = new PEFile(file)) {
            VersionInfo versionInfo = peFile.getVersionInfo();
            System.out.println(versionInfo.toString());
        }
    }

}
````

output:  
````
VersionInfo(length=724, valueLength=52, type=0, padding=4, offset=66184, fixedFileInfo=com.xin.pefile.FixedFileInfo@69a3d1d, stringFileInfo=StringFileInfo(length=564, valueLength=0, type=0, signature=StringFileInfo, padding=2, offset=66276), stringTable=StringTable(length=528, valueLength=0, type=0, signature=040904e4, offset=66312, padding=2), records={CompanyName=StringTableRecord(length=124, valueLength=46, type=1, key=CompanyName, value=Beijing Microlive Vision Technology Co., Ltd., offset=66336, padding=2), FileDescription=StringTableRecord(length=62, valueLength=11, type=1, key=FileDescription, value=【抖音】记录美好生活, offset=66460, padding=4), LegalCopyright=StringTableRecord(length=162, valueLength=63, type=1, key=LegalCopyright, value=Copyright © 2024 Beijing Microlive Vision Technology Co., Ltd., offset=66580, padding=4), ProductName=StringTableRecord(length=46, valueLength=7, type=1, key=ProductName, value=douyin, offset=66744, padding=4), FileVersion=StringTableRecord(length=56, valueLength=12, type=1, key=FileVersion, value=3.5.1.13657, offset=66524, padding=2), ProductVersion=StringTableRecord(length=48, valueLength=6, type=1, key=ProductVersion, value=3.5.1, offset=66792, padding=2), VarFileInfo=StringTableRecord(length=68, valueLength=0, type=0, key=VarFileInfo, value=$, offset=66840, padding=2)})
````
## 后续优化计划

1. 完善版本信息解析
2. exe图标导出
3. ...