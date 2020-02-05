#!/bin/bash

source /etc/profile
input1=CqApiServer.jar
logDate="NIUREN-"$(date "+%Y年-%m月-%d日%H:%M:%S")
PID=$(ps -ef | grep $input1 | grep -v grep | awk '{print $2}')
if [[ ! "${PID}" ]]; then
  echo "no running project"
else
  if ps -p "${PID}" >/dev/null; then
    echo "running project: $PID"
    sudo kill "${PID}"
    echo "${PID}"
    echo "kill project end!"
  fi
fi
export JAVA_HOME=/usr/java/jdk8
export PATH=${JAVA_HOME}/bin:$PATH

#create logfile and run project
touch /data/javaproject/logs/"$logDate".txt
nohup /usr/java/jdk8/bin/java -jar /data/javaproject/$input1 >/data/javaproject/logs/"$logDate".txt 2>&1 &
echo "Build success!"
