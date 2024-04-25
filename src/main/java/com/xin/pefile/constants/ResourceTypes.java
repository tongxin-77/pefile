package com.xin.pefile.constants;

/**
 * @author tongxin
 * @date 2024/4/20 23:54
 */
public interface ResourceTypes {
    // 常见的资源类型及其ID
    /**
     * 光标资源
     */
    long RT_CURSOR = 1;
    /**
     * 位图资源
     */
    long RT_BITMAP = 2;
    /**
     * 图标资源
     */
    long RT_ICON = 3;
    /**
     * 菜单资源
     */
    long RT_MENU = 4;
    /**
     * 对话框资源
     */
    long RT_DIALOG = 5;
    /**
     * 字符串表资源
     */
    long RT_STRING = 6;
    /**
     * 字体目录资源
     */
    long RT_FONTDIR = 7;
    /**
     * 字体资源
     */
    long RT_FONT = 8;
    /**
     * 快捷键表资源
     */
    long RT_ACCELERATOR = 9;
    /**
     * 自定义数据资源
     */
    long RT_RCDATA = 10;
    /**
     * 消息表资源
     */
    long RT_MESSAGETABLE = 11;
    /**
     * 组合光标资源（多个光标集合）
     */
    long RT_GROUP_CURSOR = 12;
    /**
     * 组合图标资源（多个图标集合）
     */
    long RT_GROUP_ICON = 14;
    /**
     * 版本信息资源
     */
    long RT_VERSION = 16;
    /**
     * 包含对话框模板的资源
     */
    long RT_DLGINCLUDE = 17;
    /**
     * 插件设备相关的资源
     */
    long RT_PLUGPLAY = 19;
    /**
     * 虚拟设备驱动程序资源
     */
    long RT_VXD = 20;
    /**
     * 动画光标资源
     */
    long RT_ANICURSOR = 21;
    /**
     * 动画图标资源
     */
    long RT_ANIICON = 22;
    /**
     * HTML资源（HTML帮助文件）
     */
    long RT_HTML = 23;
    /**
     * 应用程序清单资源（Windows XP SP2及更高版本）
     */
    long RT_MANIFEST = 24;
    // 其他可能存在的非标准或不常用的资源类型
    // ...
}
