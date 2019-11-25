使用 18 年版本 idea 无法编译通过，其内置 gradle 版本为 4.4，有一个 TaskProvider 类没有；也没有
办法使用自定义的 gradle 版本，虽然这个类有了，但是会报 Failed to notify build listener 的错误。
Failed to notify build listener 是因为 idea 和本地 gradle 版本不兼容的原因。

正确做法为下载最新版 idea，看其内置 gradle 版本，下载相应版本的 gradle 并进行安装、配置。设置项目
的 gradle 为本地下载的 gradle 即可。

Aspects 项目无法编译通过，注释掉项目的 .gradle 文件即可。