package org.dandelion.commons.utils;

import cn.hutool.core.lang.Console;
import cn.hutool.system.JavaRuntimeInfo;
import cn.hutool.system.OsInfo;
import cn.hutool.system.SystemUtil;
import oshi.SystemInfo;
import oshi.hardware.*;
import oshi.software.os.FileSystem;
import oshi.software.os.OSFileStore;
import oshi.software.os.OperatingSystem;

import java.util.List;

import static cn.hutool.core.io.unit.DataSizeUtil.format;

/**
 * TODO
 *
 * @author L
 * @version 1.0
 * @date 2021/12/16 10:57
 */
public class PcMessage {

    public static void read() {
        Console.log("======= 操作系统信息 ======");
        OsInfo osInfo = SystemUtil.getOsInfo();
        Console.log("操作系统：{}", osInfo.getName());
        Console.log("系统版本：{}", osInfo.getVersion());
        Console.log("系统架构：{}", osInfo.getArch());
        Console.log("JVM总内存：{}", format(SystemUtil.getTotalMemory()));
        Console.log("JVM剩余内存：{}", format(SystemUtil.getFreeMemory()));

        Console.log("======= java信息 ======");
        JavaRuntimeInfo runtimeInfo = SystemUtil.getJavaRuntimeInfo();
        Console.log("java版本：{}", runtimeInfo.getVersion());
        Console.log("java架构：{}", runtimeInfo.getSunArchDataModel());
        Console.log("java目录：{}", runtimeInfo.getHomeDir());

        SystemInfo systemInfo = new SystemInfo();
        HardwareAbstractionLayer hardware = systemInfo.getHardware();
        OperatingSystem operatingSystem = systemInfo.getOperatingSystem();

        Console.log("======= CPU信息 ======");
        CentralProcessor cpu = hardware.getProcessor();
        CentralProcessor.ProcessorIdentifier cpuInfo = cpu.getProcessorIdentifier();
        Console.log("cpu数量：{}", cpu.getPhysicalPackageCount());
        Console.log("cpu核心数：{}", cpu.getPhysicalProcessorCount());
        Console.log("cpu线程数：{}", cpu.getLogicalProcessorCount());
        Console.log("cpuID：{}", cpuInfo.getProcessorID());
        Console.log("cpu名称：{}", cpuInfo.getName());
        Console.log("cpu标识：{}", cpuInfo.getIdentifier());

        Console.log("======= 主板信息 ======");
        ComputerSystem computer = hardware.getComputerSystem();
        Console.log("主板型号：{}", computer.getModel());
        Console.log("主板序列号：{}", computer.getSerialNumber());

        Console.log("======= 内存信息 ======");
        GlobalMemory memory = hardware.getMemory();
        Console.log("内存大小：{}", format(memory.getTotal()));
        Console.log("可用内存：{}", format(memory.getAvailable()));
        Console.log("==========");
        List<PhysicalMemory> memoryList = memory.getPhysicalMemory();
        memoryList.forEach((item) -> {
            Console.log("内存型号：{}", item.getManufacturer());
            Console.log("内存规格：{}", item.getMemoryType());
            Console.log("内存主频：{}", format(item.getClockSpeed()));
            Console.log("内存大小：{}", format(item.getCapacity()));
            Console.log("==========");
        });

        Console.log("======= 物理磁盘信息 ======");
        List<HWDiskStore> diskList = hardware.getDiskStores();
        diskList.forEach((disk) -> {
            Console.log("名称：{}", disk.getName());
            Console.log("型号：{}", disk.getModel());
            Console.log("序号：{}", disk.getSerial());
            Console.log("大小：{}", format(disk.getSize()));
            Console.log("==========");
        });

        Console.log("======= 逻辑磁盘信息 ======");
        FileSystem fileSystem = operatingSystem.getFileSystem();
        List<OSFileStore> fileList = fileSystem.getFileStores(true);
        fileList.forEach((file) -> {
            Console.log("名称：{}", file.getName());
            Console.log("UUID：{}", file.getUUID());
            Console.log("盘符：{}", file.getMount());
            Console.log("文件类型：{}", file.getType());
            Console.log("总大小：{}", format(file.getTotalSpace()));
            Console.log("剩余大小：{}", format(file.getUsableSpace()));
            Console.log("==========");
        });
    }
}
