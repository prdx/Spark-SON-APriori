Spark SON A-Priori
Final Project
Spring 2019

Code author
-----------
Anak Agung Ngurah Bagus Trihatmaja, Jay Turakhia

Installation
------------
These components are installed:
- JDK 1.8
- Scala 2.11.12
- Hadoop 2.9.1
- Spark 2.3.1 (without bundled Hadoop)
- Maven
- AWS CLI (for EMR execution)


Environment
-----------
1) Example ~/.bash_aliases:
export JAVA_HOME=/usr/lib/jvm/java-8-oracle
export HADOOP_HOME=/home/joe/tools/hadoop/hadoop-2.9.1
export SCALA_HOME=/home/joe/tools/scala/scala-2.11.12
export SPARK_HOME=/home/joe/tools/spark/spark-2.3.1-bin-without-hadoop
export YARN_CONF_DIR=$HADOOP_HOME/etc/hadoop
export PATH=$PATH:$HADOOP_HOME/bin:$HADOOP_HOME/sbin:$SCALA_HOME/bin:$SPARK_HOME/bin
export SPARK_DIST_CLASSPATH=$(hadoop classpath)

2) Explicitly set JAVA_HOME in $HADOOP_HOME/etc/hadoop/hadoop-env.sh:
export JAVA_HOME=/usr/lib/jvm/java-8-oracle

Execution
---------
All of the build & execution commands are organized in the Makefile.

Update parameters on the Makefile:
aws.min.support=<floating number>

1) Standalone Hadoop:
	make switch-standalone		-- set standalone Hadoop environment (execute once)
	make local
