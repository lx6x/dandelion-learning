# dandelion-bigdata-hadoop

## hadoop 安装 （单节点） 

### 环境

* Centos7
* java8
* hadoop-3.3.6

### Hadoop Java 版本

支持的 Java 版本

* Apache Hadoop 3.3 及更高版本支持 Java 8 和 Java 11（仅运行时）
    * 请使用Java 8编译Hadoop。不支持使用Java 11编译Hadoop： HADOOP-16795 - Java 11 编译支持
* Apache Hadoop 从 3.0.x 到 3.2.x 现在仅支持 Java 8
* Apache Hadoop 从 2.7.x 到 2.10.x 同时支持 Java 7 和 8

官方文档：[Hadoop 支持 Java 版本](https://cwiki.apache.org/confluence/display/HADOOP/Hadoop+Java+Versions)

### 下载 Hadoop

```shell
# 下载 不同版本对应修改
$ wget https://dlcdn.apache.org/hadoop/common/hadoop-3.3.6/hadoop-3.3.6.tar.gz
# 解压
$ tar -zxvf hadoop-3.3.6.tar.gz /软件目录下
```

### 下载 Jdk

```shell
# 安装包手动安装
# https://www.oracle.com/java/technologies/downloads/#java8
$ tar -zxvf jdk-8u391-linux-x64.tar.gz /软件目录下

# 配置环境变量 /etc/profile 写入
export JAVA_HOME=/usr/local/jdk1.8.0_391
export PATH=$JAVA_HOME/bin:$PATH
export CLASSPATH=.:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar

# 刷新配置
$ source /etc/profile

--------------------------------------------------------------------------

# yum 安装
# 安装 openjdk
# 默认安装路径一般在 /usr/lib/jvm
$ yum install java-1.8.0-openjdk-devel

# 验证
$ java -version
```

### Linux 系统设置

```shell
# vm centos7 修改静态 ip 需要的话 /etc/sysconfig/network-scripts/ifcfg-ens33
BOOTPROTO="static" # 静态ip
IPADDR=192.168.1.10 # 设置ip地址
NETMASK=255.255.255.0 # 子网掩码
GATEWAY=192.168.1.2 # 网关
DNS1=8.8.8.8 # DNS

# 创建 hadoop 用户
$ useradd hadoop
$ passwd hadoop

# 修改 /etc/hosts 文件，单机/集群 写入
192.0.0.1 master
192.0.0.2 node1
192.0.0.3 node2

# 修改 /etc/sysconfig/network 写入 
NETWORKING=yes
HOSTNAME=<master/主机名>
# 或者
$ hostnamectl set-hostname <主机名> 

# 关闭防火墙
$ systemctl stop firewalld.service
$ systemctl disable firewalld.service

--------------------------------------------------------------------------

# 查看是否安装 ssh
$ rpm -qa | grep ssh

# 安装
$ yum install openssh-clients
$ yum install openssh-server

# 安装完后测试
$ ssh localhost

# 设置免密登录，集群、单节点模式都需要用到 SSH 登陆
# 切换用户到 hadoop
$ su hadoop

# 在master主机上生成RSA公私钥对
$ ssh-keygen -t rsa

# 到hadoop用户的用户目录下的.ssh目录
# ~ 代表的是用户的主文件夹，即 “/home/用户名” 这个目录，如你的用户名为 hadoop，则 ~ 就代表 “/home/hadoop/”
$ cd ~/.ssh

# 生成authorized_keys文件 加入授权
$ cat id_rsa.pub >> authorized_keys

# 对authorized_keys文件进行权限修复
$ chmod 644 ./authorized_keys

# 测试
$ ssh localhost

# 分发authorized_keys到 node1 node2 集群需要做 三台虚拟机相互发送密钥
$ ssh-copy-id hadoop@master
$ ssh-copy-id hadoop@node1
$ ssh-copy-id hadoop@node2

# 在主机上对免密登录进行测试，除第一外后面再登录都不需要输入登录密码 集群需要做
$ ssh node1 # master主机上免密登录到 node1
$ ssh node2 # master主机上免密登录到 node2

```

### 配置 Hadoop

#### 配置环境变量
```shell
$ su hadoop
# 编辑 ~/.bashrc
export HADOOP_HOME=/usr/local/hadoop-3.3.6
export HADOOP_INSTALL=$HADOOP_HOME
export HADOOP_MAPRED_HOME=$HADOOP_HOME
export HADOOP_COMMON_HOME=$HADOOP_HOME
export HADOOP_HDFS_HOME=$HADOOP_HOME
export YARN_HOME=$HADOOP_HOME
export HADOOP_COMMON_LIB_NATIVE_DIR=$HADOOP_HOME/lib/native
export PATH=$PATH:$HADOOP_HOME/sbin:$HADOOP_HOME/bin
export HADOOP_OPTS="-Djava.library.path=$HADOOP_HOME/lib/native"
```

#### 设置目录权限

```shell
$ su root  
$ chown -R hadoop:hadoop /usr/local/hadoop-3.3.6  

!!! 注意文件权限问题 !!!
```

#### 修改 etc/hadoop/hadoop-env.sh

```shell
export JAVA_HOME=/usr/local/jdk1.8.0_391 # 对应安装目录
export HADOOP_HOME=/usr/local/hadoop-3.3.6 # 对应安装目录
```

#### 修改 etc/hadoop/mapred-site.xml

```xml
<configuration>
    <property>
        <name>mapreduce.framework.name</name>
        <value>yarn</value>
        <final>true</final>
        <description>The runtime framework for executing MapReduce jobs</description>
    </property>
    <property>
        <name>yarn.app.mapreduce.am.env</name>
        <value>HADOOP_MAPRED_HOME=/usr/local/hadoop-3.3.6</value>
    </property>
    <property>
        <name>mapreduce.map.env</name>
        <value>HADOOP_MAPRED_HOME=/usr/local/hadoop-3.3.6</value>
    </property>
    <property>
        <name>mapreduce.reduce.env</name>
        <value>HADOOP_MAPRED_HOME=/usr/local/hadoop-3.3.6</value>
    </property>
</configuration>
```

#### 修改 etc/hadoop/core-site.xml

```xml
<configuration>
    <property>
        <name>fs.default.name</name>
        <value>hdfs://master:9000</value>
    </property>
    <property>
        <name>hadoop.tmp.dir</name>
        <value>/home/hadoop/dfs</value>
    </property>
</configuration>
```

#### 修改 etc/hadoop/hdfs-site.xml

```xml
<configuration>
    <property>
        <name>dfs.replication</name>
        <value>1</value>
    </property>
<!--    <property>-->
<!--        <name>dfs.namenode.name.dir</name>-->
<!--        <value>/home/hadoop/dfs/namenode</value>-->
<!--    </property>-->
<!--    <property>-->
<!--        <name>dfs.datanode.data.dir</name>-->
<!--        <value>/home/hadoop/dfs/datanode</value>-->
<!--    </property>-->
<!--    <property>-->
<!--        <name>dfs.http.address</name>-->
<!--        <value>master:50070</value>-->
<!--    </property>-->
</configuration>
```

#### 修改 etc/hadoop/yarn-site.xml

```xml
<configuration>
    <!-- Site specific YARN configuration properties -->
    <property>
        <name>yarn.nodemanager.aux-services</name>
        <value>mapreduce_shuffle</value>
        <final>true</final>
    </property>
</configuration>
```

#### 初始化 Hadoop

```shell
# !!! 使用 hadoop 用户操作 !!!
# 对namenode进行格式化（必须在主节点上进行） bin 下 
$ hdfs namenode -format
```

#### 启动

```shell
# sbin 下
$ start-all.sh
# 或者
$ start-dfs.sh
$ start-yarn.sh
```
#### 验证
```shell
$ hdfs version
$ hadoop version
# 创建目录
$ hdfs dfs -mkdir /test
# 查询
$ hdfs dfs -ls / 
```
#### 访问
```
ip:9870
ip:9864
ip:8088
```
#### 遗留问题
1. 页面HDFS文件上传后出现
    1. Permission denied: user=dr.who, access=WRITE, inode="/test":hadoop:supergroup:drwxr-xr-x.  
       解决：执行 ‘hdfs dfs -chmod’ 命令为目录添加写权限，例如目录 ‘/test’ 执行 ‘hdfs dfs -chmod +w /test’
    2. Couldn't upload the file 1.txt.  
       解决：检查hosts是否配置对应ip映射
2. windows hosts 设置域名指向后无法访问.  
   解决：检查代理/hosts/DNS解析/Hadoop主机名 等是否配置正确

### 集群

以上只是单机版

集群需要节点分发 slave

然后再执行初始化 Hadoop 操作 （在主节点上操作）


