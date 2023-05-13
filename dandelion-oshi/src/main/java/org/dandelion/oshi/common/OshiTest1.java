package org.dandelion.oshi.common;

import com.sun.management.OperatingSystemMXBean;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.util.GlobalConfig;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class OshiTest1 {

    public static void main(String[] args) {
        initSystemInfo();
    }

    /***
     * @author Benjamin
     * @date 2022/12/8 10:02:50
     * @version 1.0.0
     * @description 获取系统详细参数信息
     * @param
     * @return void
     */
    public static void initSystemInfo() {
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            try {
                SystemInfo systemInfo = new SystemInfo();
                OperatingSystemMXBean osmxb = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
                MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
                // 椎内存使用情况
                MemoryUsage memoryUsage = memoryMXBean.getHeapMemoryUsage();

                // 初始的总内存
                long initTotalMemorySize = memoryUsage.getInit();
                // 最大可用内存
                long maxMemorySize = memoryUsage.getMax();
                // 已使用的内存
                long usedMemorySize = memoryUsage.getUsed();

                // 操作系统
                String osName = System.getProperty("os.name");
                // 总的物理内存
                String totalMemorySize = new DecimalFormat("#.##").format(osmxb.getTotalPhysicalMemorySize() / 1024.0 / 1024 / 1024) + "G";
                // 剩余的物理内存
                String freePhysicalMemorySize = new DecimalFormat("#.##").format(osmxb.getFreePhysicalMemorySize() / 1024.0 / 1024 / 1024) + "G";
                // 已使用的物理内存
                String usedMemory = new DecimalFormat("#.##").format((osmxb.getTotalPhysicalMemorySize() - osmxb.getFreePhysicalMemorySize()) / 1024.0 / 1024 / 1024) + "G";
                // 获得线程总数
                ThreadGroup parentThread;
                for (parentThread = Thread.currentThread().getThreadGroup(); parentThread
                        .getParent() != null; parentThread = parentThread.getParent()) {

                }

                int totalThread = parentThread.activeCount();

                // 磁盘使用情况
                File[] files = File.listRoots();
                for (File file : files) {
                    String total = new DecimalFormat("#.#").format(file.getTotalSpace() * 1.0 / 1024 / 1024 / 1024) + "G";
                    String free = new DecimalFormat("#.#").format(file.getFreeSpace() * 1.0 / 1024 / 1024 / 1024) + "G";
                    String un = new DecimalFormat("#.#").format(file.getUsableSpace() * 1.0 / 1024 / 1024 / 1024) + "G";
                    String path = file.getPath();
                    System.err.println(path + "总:" + total + ",可用空间:" + un + ",空闲空间:" + free);
                    System.err.println("=============================================");
                }

                System.err.println("操作系统:" + osName);
                System.err.println("程序启动时间:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(ManagementFactory.getRuntimeMXBean().getStartTime())));
                /// System.err.println("pid:" + System.getProperty("PID"));
                System.err.println("pid:" + getPID());
                System.err.println("cpu核数:" + Runtime.getRuntime().availableProcessors());
                printlnCpuInfo(systemInfo);
                System.err.println("JAVA_HOME:" + System.getProperty("java.home"));
                System.err.println("JAVA_VERSION:" + System.getProperty("java.version"));
                System.err.println("USER_HOME:" + System.getProperty("user.home"));
                System.err.println("USER_NAME:" + System.getProperty("user.name"));
                System.err.println("初始的总内存(JVM):" + new DecimalFormat("#.#").format(initTotalMemorySize * 1.0 / 1024 / 1024) + "M");
                System.err.println("最大可用内存(JVM):" + new DecimalFormat("#.#").format(maxMemorySize * 1.0 / 1024 / 1024) + "M");
                System.err.println("已使用的内存(JVM):" + new DecimalFormat("#.#").format(usedMemorySize * 1.0 / 1024 / 1024) + "M");
                System.err.println("总的物理内存:" + totalMemorySize);
                System.err.println("总的物理内存:" + new DecimalFormat("#.##").format(systemInfo.getHardware().getMemory().getTotal() * 1.0 / 1024 / 1024 / 1024) + "M");
                System.err.println("剩余的物理内存:" + freePhysicalMemorySize);
                System.err.println("剩余的物理内存:" + new DecimalFormat("#.##").format(systemInfo.getHardware().getMemory().getAvailable() * 1.0 / 1024 / 1024 / 1024) + "M");
                System.err.println("已使用的物理内存:" + usedMemory);
                System.err.println("已使用的物理内存:" + new DecimalFormat("#.##").format((systemInfo.getHardware().getMemory().getTotal() - systemInfo.getHardware().getMemory().getAvailable()) * 1.0 / 1024 / 1024 / 1024) + "M");
                System.err.println("总线程数:" + totalThread);
                System.err.println("===========================");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 0, 60, TimeUnit.SECONDS);


    }

    /**
     * 打印 CPU 信息
     *
     * @param systemInfo
     */
    private static void printlnCpuInfo(SystemInfo systemInfo) throws InterruptedException {

        // todo 解决 Why does OSHI's System and Processor CPU usage differ from the Windows Task Manager?
        GlobalConfig.set(GlobalConfig.OSHI_OS_WINDOWS_CPU_UTILITY, true);

        CentralProcessor processor = systemInfo.getHardware().getProcessor();
        long[] prevTicks = processor.getSystemCpuLoadTicks();
        // 睡眠1s
        TimeUnit.SECONDS.sleep(1);
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
        System.err.println("cpu系统使用率:" + new DecimalFormat("#.##%").format(cSys * 1.0 / totalCpu));
        System.err.println("cpu用户使用率:" + new DecimalFormat("#.##%").format(user * 1.0 / totalCpu));
        System.err.println("cpu当前等待率:" + new DecimalFormat("#.##%").format(iowait * 1.0 / totalCpu));
        System.err.println("cpu当前空闲率:" + new DecimalFormat("#.##%").format(idle * 1.0 / totalCpu));

        // todo cup利用
        //  user + system + nice + iowait + irq + softirq + steal
        long cpuUtilization = user + nice + cSys + iowait + irq + softirq + steal;
        System.err.println("cpu利用率：" + new DecimalFormat("#.##%").format(cpuUtilization * 1.0 / totalCpu));

        // hutool工具，底层用的也是oshi
		/*
		GlobalConfig.set(GlobalConfig.OSHI_OS_WINDOWS_CPU_UTILITY, true);
		cn.hutool.system.oshi.CpuInfo cpuInfo = OshiUtil.getCpuInfo();
		double free = cpuInfo.getFree();
		DecimalFormat format = new DecimalFormat("#.00");
		System.out.println("cpu利用率：" + Double.parseDouble(format.format(100.0D - free)));
		*/

        /// 低版本这两个方法是无参方法,高版本中是需要有参数的
        //System.err.format("CPU load: %.1f%% (counting ticks)%n", processor.getSystemCpuLoadBetweenTicks(ticksArray) * 100);
        //System.err.format("CPU load: %.1f%% (OS MXBean)%n", processor.getSystemCpuLoad(1L) * 100);
        long[] ticksArray = {1, 2, 3, 4, 5, 6, 7, 8};
        System.err.format("CPU load: %.1f%% (counting ticks)%n", processor.getSystemCpuLoadBetweenTicks(ticksArray) * 100);
        System.err.format("CPU load: %.1f%% (OS MXBean)%n", processor.getSystemCpuLoad(1L) * 100);

        ProcessorIdentifierInfo(processor);
    }


    /**
     * 获取进程id
     *
     * @return
     */
    public static String getPID() {
        String name = ManagementFactory.getRuntimeMXBean().getName();
        return name.split("@")[0];
    }

    public static void ProcessorIdentifierInfo(CentralProcessor processor) {
        CentralProcessor.ProcessorIdentifier processorIdentifier = processor.getProcessorIdentifier();
        String vendor = processorIdentifier.getVendor();
        String name = processorIdentifier.getName();
        String family = processorIdentifier.getFamily();
        String model = processorIdentifier.getModel();
        String stepping = processorIdentifier.getStepping();
        String processorID = processorIdentifier.getProcessorID();
        // cpu64bit ? "Intel64" : "x86"
        // Intel64 Family 6 Model 142 Stepping 12
        String identifier = processorIdentifier.getIdentifier();
        long vendorFreq = processorIdentifier.getVendorFreq();
        System.out.println("vendor: " + vendor);
        // 处理器名字
        System.out.println("name: " + name);
        System.out.println("family: " + family);
        System.out.println("model: " + model);
        System.out.println("stepping: " + stepping);
        System.out.println("processorID: " + processorID);
        System.out.println("identifier: " + identifier);
        System.out.println("vendorFreq: " + vendorFreq);
    }
}
