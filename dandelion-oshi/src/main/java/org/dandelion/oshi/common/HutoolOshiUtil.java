package org.dandelion.oshi.common;

import cn.hutool.system.oshi.CpuInfo;
import cn.hutool.system.oshi.OshiUtil;
import oshi.hardware.*;
import oshi.software.os.FileSystem;
import oshi.software.os.OSFileStore;
import oshi.software.os.OperatingSystem;
import oshi.util.FormatUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class HutoolOshiUtil {

    public static void main(String[] args) {
        huTool();
    }

    public static void huTool() {
        OperatingSystem os = OshiUtil.getOs();
        HardwareAbstractionLayer hal = OshiUtil.getHardware();


        System.out.println("操作系统：" + os);
        System.out.println("获取操作系统：" + os.getFamily());
        System.out.println("操作系统制造商：" + os.getManufacturer());
        System.out.println("操作系统版本：" + os.getVersionInfo());
        System.out.println("系统启动时间: " + Instant.ofEpochSecond(os.getSystemBootTime()));
        System.out.println("获取系统启动时间(启动后的时间): " + FormatUtil.formatElapsedSecs(os.getSystemUptime()));
        System.out.println("Running with" + (os.isElevated() ? "" : "out") + " elevated permissions.");
        System.out.println("指定pid获取对应进程信息：" + os.getProcess(0));
        System.out.println("获取正在运行的进程数：" + os.getProcessCount());
        System.out.println("获取正在运行的线程数：" + os.getThreadCount());
        System.out.println("获取操作系统位（32/64）数：" + os.getBitness());

        System.out.println();

        ComputerSystem computerSystem = hal.getComputerSystem();
        System.out.println("系统: " + computerSystem.toString());
        System.out.println("系统制造商: " + computerSystem.getManufacturer());
        System.out.println("获取计算机系统模型: " + computerSystem.getModel());
        System.out.println("获取计算机序列号: " + computerSystem.getSerialNumber());
        System.out.println("获取计算机系统硬件uuid: " + computerSystem.getHardwareUUID());
        System.out.println("获取计算机系统固件/BIOS: " + computerSystem.getFirmware().toString());
        System.out.println("系统固件 - 制造商：" + computerSystem.getFirmware().getManufacturer());
        System.out.println("系统固件 - 名称：" + computerSystem.getFirmware().getName());
        System.out.println("系统固件 - 描述：" + computerSystem.getFirmware().getDescription());
        System.out.println("系统固件 - 版本：" + computerSystem.getFirmware().getVersion());
        System.out.println("系统固件 -发布时间：" + computerSystem.getFirmware().getReleaseDate());
        System.out.println("获取计算机系统基板/主板: " + computerSystem.getBaseboard().toString());
        System.out.println("主板 - 制造商：" + computerSystem.getBaseboard().getManufacturer());
        System.out.println("主板 - 模型：" + computerSystem.getBaseboard().getModel());
        System.out.println("主板 - 版本：" + computerSystem.getBaseboard().getVersion());
        System.out.println("主板 - 序列号：" + computerSystem.getBaseboard().getSerialNumber());

        System.out.println();

        GlobalMemory memory = hal.getMemory();
        System.out.println("内存：" + memory);
        long total = memory.getTotal();
        long available = memory.getAvailable();
        long useMemory = total - available;


        BigDecimal divide = BigDecimal.valueOf(useMemory).divide(BigDecimal.valueOf(total), 4, RoundingMode.FLOOR).multiply(BigDecimal.valueOf(100));
        System.out.println(" 物理内存量 bytes：" + total);
        System.out.println(" 当前可用物理内存量：" + available);
        System.out.println(" 已使用物理内存量：" + useMemory);
        System.out.println(" 内存使用率：" + divide);

        System.out.println(" ---------：" + formatByte(total));

        //空闲率
        System.out.println(" 空闲率：" + new DecimalFormat("#.##%").format((available) * 1.0 / total));
        //使用率
        System.out.println(" 使用率：" + new DecimalFormat("#.##%").format((useMemory) * 1.0 / total));

        System.out.println();

        System.out.println("磁盘");
        FileSystem fileSystem = os.getFileSystem();
        List<OSFileStore> fileStores = fileSystem.getFileStores();
        // 总容量
        long totalSystemFile = 0L;
        // 总空闲容量
        long freeSystemFile = 0L;
        // 共使用大小
        long usedSystemFile = 0L;
        for (OSFileStore f : fileStores) {
            StringBuilder sb = new StringBuilder();
            sb
                    .append(" 文件系统名称：").append(f.getName())
                    .append(" 卷名：").append(f.getVolume())
                    .append(" 标签：").append(f.getLabel())
                    .append(" 类型：").append(f.getType())
                    .append(" 容量：").append(f.getTotalSpace())
                    .append(" 空闲容量：").append(f.getFreeSpace())
                    .append(" 已使用容量：").append(f.getTotalSpace() - f.getFreeSpace())
                    .append(" 已使用容量：").append(f.getTotalSpace() - f.getFreeSpace())

            ;
            System.out.println(sb);
            totalSystemFile = totalSystemFile + f.getTotalSpace();
            freeSystemFile = freeSystemFile + f.getFreeSpace();
        }
        System.out.println(" 总容量：" + totalSystemFile);
        System.out.println(" 空闲容量：" + freeSystemFile);
        usedSystemFile = totalSystemFile - freeSystemFile;
        System.out.println(" 共使用容量：" + usedSystemFile);
        BigDecimal divide1 = BigDecimal.valueOf(usedSystemFile).divide(BigDecimal.valueOf(totalSystemFile), 4, RoundingMode.FLOOR);
        System.out.println(" 共使用占比：" + new DecimalFormat("#.##%").format(divide1));

        System.out.println();

        System.out.println("cpu");
        CpuInfo cpuInfo = OshiUtil.getCpuInfo();
        System.out.println(" " + cpuInfo);
        double used = cpuInfo.getUsed();
        System.out.println(" 利用率：" + used);

        System.out.println();

        System.out.println("获取正在运行的进程");
        /*List<OSProcess> processes = OshiUtil.getOs().getProcesses(null, OperatingSystem.ProcessSorting.CPU_DESC, 10);
        for (OSProcess process : processes) {
            System.out.println(" "+process.getName() + "  " + process.getProcessID() + "  " + process.getState() + "  " +process.getResidentSetSize());

            List<OSThread> threadDetails = process.getThreadDetails();
            for (OSThread threadDetail : threadDetails) {
                System.out.println("  "+threadDetail.getName());
            }
        }*/

        System.out.println();

        System.out.println("网络信息");
        List<NetworkIF> networkIFs = hal.getNetworkIFs();
        System.out.println("networkIFs：" + networkIFs);
        for (NetworkIF networkIF : networkIFs) {
            System.out.println(" 名称：" + networkIF.getName());
            System.out.println(" mac：" + networkIF.getMacaddr());
            System.out.println(" ip：" + networkIF.getIPv4addr()[0]);
        }


    }


    /**
     * 获取cpu使用率，等待率，空闲率
     *
     * @return
     * @throws Exception
     */
    public static void cpu() {
        HardwareAbstractionLayer hal = OshiUtil.getHardware();

        CentralProcessor processor = hal.getProcessor();
        long[] prevTicks = processor.getSystemCpuLoadTicks();
        // 睡眠1s
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        long[] ticks = processor.getSystemCpuLoadTicks();
        long nice = ticks[CentralProcessor.TickType.NICE.getIndex()] - prevTicks[CentralProcessor.TickType.NICE.getIndex()];
        long irq = ticks[CentralProcessor.TickType.IRQ.getIndex()] - prevTicks[CentralProcessor.TickType.IRQ.getIndex()];
        long softirq = ticks[CentralProcessor.TickType.SOFTIRQ.getIndex()] - prevTicks[CentralProcessor.TickType.SOFTIRQ.getIndex()];
        long steal = ticks[CentralProcessor.TickType.STEAL.getIndex()] - prevTicks[CentralProcessor.TickType.STEAL.getIndex()];
        long cSys = ticks[CentralProcessor.TickType.SYSTEM.getIndex()] - prevTicks[CentralProcessor.TickType.SYSTEM.getIndex()];
        long user = ticks[CentralProcessor.TickType.USER.getIndex()] - prevTicks[CentralProcessor.TickType.USER.getIndex()];
        long iowait = ticks[CentralProcessor.TickType.IOWAIT.getIndex()] - prevTicks[CentralProcessor.TickType.IOWAIT.getIndex()];
        long idle = ticks[CentralProcessor.TickType.IDLE.getIndex()] - prevTicks[CentralProcessor.TickType.IDLE.getIndex()];
        long totalCpu = user + nice + cSys + idle + iowait + irq + softirq + steal;
        System.err.println("cpu核数:" + processor.getLogicalProcessorCount());
        System.err.println("cpu利用率：" + new DecimalFormat("#.##").format(nice * 1.0 / totalCpu));
        System.err.println("cpu系统使用率:" + new DecimalFormat("#.##%").format(cSys * 1.0 / totalCpu));
        System.err.println("cpu用户使用率:" + new DecimalFormat("#.##%").format(user * 1.0 / totalCpu));
        System.err.println("cpu当前等待率:" + new DecimalFormat("#.##%").format(iowait * 1.0 / totalCpu));
        System.err.println("cpu当前空闲率:" + new DecimalFormat("#.##%").format(idle * 1.0 / totalCpu));
        CpuInfo cpuInfo = OshiUtil.getCpuInfo();
        System.err.println(cpuInfo.getUsed());
    }

    /**
     * 单位转换
     */
    private static String formatByte(long byteNumber) {
        //换算单位
        double FORMAT = 1024.0;
        double kbNumber = byteNumber / FORMAT;
        if (kbNumber < FORMAT) {
            return new DecimalFormat("#.##KB").format(kbNumber);
        }
        double mbNumber = kbNumber / FORMAT;
        if (mbNumber < FORMAT) {
            return new DecimalFormat("#.##MB").format(mbNumber);
        }
        double gbNumber = mbNumber / FORMAT;
        if (gbNumber < FORMAT) {
            return new DecimalFormat("#.##GB").format(gbNumber);
        }
        double tbNumber = gbNumber / FORMAT;
        return new DecimalFormat("#.##TB").format(tbNumber);
    }
}
