# dandelion-bigdata

## hadoop 安装

### 环境

* Centos7
* java8
* hadoop-3.3.6

#### Hadoop Java 版本

支持的 Java 版本

* Apache Hadoop 3.3 及更高版本支持 Java 8 和 Java 11（仅运行时）
    * 请使用Java 8编译Hadoop。不支持使用Java 11编译Hadoop： HADOOP-16795 - Java 11 编译支持
* Apache Hadoop 从 3.0.x 到 3.2.x 现在仅支持 Java 8
* Apache Hadoop 从 2.7.x 到 2.10.x 同时支持 Java 7 和 8

官方文档：[Hadoop 支持 Java 版本](https://cwiki.apache.org/confluence/display/HADOOP/Hadoop+Java+Versions)

### 下载 Hadoop Jdk

```shell
# 下载 不同版本对应修改
wget https://dlcdn.apache.org/hadoop/common/hadoop-3.3.6/hadoop-3.3.6.tar.gz
# 解压
tar -zxvf hadoop-3.3.6.tar.gz /软件目录下
```

### 下载 Jdk

```shell
# 安装包手动安装
# https://www.oracle.com/java/technologies/downloads/#java8
tar -zxvf tar -zxvf jdk-8u391-linux-x64.tar.gz

# 配置环境变量 /etc/profile 写入
export JAVA_HOME=/usr/local/jdk1.8.0_391
export PATH=$JAVA_HOME/bin:$PATH
export CLASSPATH=.:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar

# 刷新配置
source /etc/profile

--------------------------------------------------------------------------

# yum 安装
# 安装 openjdk
# 默认安装路径一般在 /usr/lib/jvm
yum install java-1.8.0-openjdk-devel
```

### Linux 系统设置

```shell
# 创建 hadoop 用户
useradd hadoop
passwd hadoop

# 修改 /etc/hosts 文件，单机/集群 写入
192.0.0.1 master
192.0.0.2 node1
192.0.0.3 node2

# 修改 /etc/sysconfig/network 写入 
NETWORKING=yes
HOSTNAME=你的主机名

# 关闭防火墙
systemctl stop firewalld.service
systemctl disable firewalld.service

--------------------------------------------------------------------------

# 查看是否安装 ssh
rpm -qa | grep ssh

# 安装
yum install openssh-clients
yum install openssh-server

# 安装完后测试
ssh localhost

# 设置免密登录，集群、单节点模式都需要用到 SSH 登陆
# 切换用户到 hadoop
su hadoop

# 在master主机上生成RSA公私钥对
ssh-keygen -t rsa

# 到hadoop用户的用户目录下的.ssh目录
# ~ 代表的是用户的主文件夹，即 “/home/用户名” 这个目录，如你的用户名为 hadoop，则 ~ 就代表 “/home/hadoop/”
cd ~/.ssh

# 生成authorized_keys文件 加入授权
cat id_rsa.pub >> authorized_keys

# 对authorized_keys文件进行权限修复
chmod 644 ./authorized_keys

# 测试
ssh localhost

# 分发authorized_keys到 node1 node2
ssh-copy-id hadoop@node1
ssh-copy-id hadoop@node2

# 在主机上对免密登录进行测试，除第一外后面再登录都不需要输入登录密码
ssh node1 # master主机上免密登录到 node1
ssh node2 # master主机上免密登录到 node2

```

### 配置 Hadoop

#### 设置目录权限

```shell
su root  
chown -R hadoop:hadoop /usr/local/hadoop-3.3.6  
cd /usr/local/hadoop-3.3.6
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
    <value>hdfs://master:9001</value>
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
  <property>
    <name>dfs.namenode.name.dir</name>
    <value>/home/hadoop/dfs/namenode</value>
  </property>
  <property>
    <name>dfs.datanode.data.dir</name>
    <value>/home/hadoop/dfs/datanode</value>
  </property>
  <property>
    <name>dfs.http.address</name>
    <value>0.0.0.0:50070</value>
  </property>
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

#### 初始化Hadoop
```shell
# 对namenode进行格式化（必须在主节点上进行）
hdfs namenode -format
```

