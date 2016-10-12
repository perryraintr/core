#!/bin/sh
HOME=/opt/job
LANG=zh_CN
LC_ALL=zh_CN.GBK
JAVA_HOME=/opt/jdk1.6.0_20
PATH=$JAVA_HOME/bin:$PATH
NLS_LANG=AMERICAN_AMERICA.ZHS16GBK
CLASSPATH=.:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar:$HOME/lib/aopalliance.jar:$HOME/lib/aspectjweaver-1.6.12.jar:$HOME/lib/c3p0-0.9.1.1.jar:$HOME/lib/commons-beanutils-1.7.0.jar:$HOME/lib/commons-codec-1.3.jar:$HOME/lib/commons-collections-3.1.jar:$HOME/lib/commons-configuration-1.1.jar:$HOME/lib/commons-dbcp-1.2.2.jar:$HOME/lib/commons-fileupload-1.2.2.jar:$HOME/lib/commons-httpclient-3.0-rc4.jar:$HOME/lib/commons-io-2.0.1.jar:$HOME/lib/commons-lang-2.5.jar:$HOME/lib/commons-logging-1.0.4.jar:$HOME/lib/commons-pool-1.5.4.jar:$HOME/lib/dom4j-1.6.1.jar:$HOME/lib/fastjson-1.1.43.jar:$HOME/lib/fastjson-1.2.9.jar:$HOME/lib/httpclient-4.3.3.jar:$HOME/lib/httpcore-4.3.2.jar:$HOME/lib/httpmime-4.2.jar:$HOME/lib/ibatis-2.3.4.726.jar:$HOME/lib/java_memcached-1.6.jar:$HOME/lib/javax.servlet.jar:$HOME/lib/javax.servlet.jsp.jstl.jar:$HOME/lib/jdom-2.0.6.jar:$HOME/lib/json.jar:$HOME/lib/jtds-1.2.8.jar:$HOME/lib/log4j-1.2.16.jar:$HOME/lib/mysql-connector-java-5.1.39-bin.jar:$HOME/lib/slf4j-api-1.6.1.jar:$HOME/lib/slf4j-log4j12-1.6.1.jar:$HOME/lib/spring-aop-3.2.2.RELEASE.jar:$HOME/lib/spring-aspects-3.2.2.RELEASE.jar:$HOME/lib/spring-beans-3.2.2.RELEASE.jar:$HOME/lib/spring-context-3.2.2.RELEASE.jar:$HOME/lib/spring-core-3.2.2.RELEASE.jar:$HOME/lib/spring-expression-3.2.2.RELEASE.jar:$HOME/lib/spring-jdbc-3.2.2.RELEASE.jar:$HOME/lib/spring-orm-3.2.2.RELEASE.jar:$HOME/lib/spring-tx-3.2.2.RELEASE.jar:$HOME/lib/spring-web-3.2.2.RELEASE.jar:$HOME/lib/spring-webmvc-3.2.2.RELEASE.jar:$HOME/lib/standard-1.1.2.jar:$HOME/lib/xstream-1.2.2.jar:$HOME/lib/javase-2.3.0.jar:$HOME/lib/core-2.3.0.jar
export LANG LC_ALL NLS_LANG JAVA_HOME PATH CLASSPATH

cd $HOME
java -Xms1024m -Xmx10240m com.raintr.pinshe.job.SubscriptionJob >> subscription.log 2>&1 &