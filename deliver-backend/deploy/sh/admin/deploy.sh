#!/bin/bash

# 定义变量
APP_NAME="your-app"
JAR_NAME="your-app.jar"
LOG_DIR="/logs"

# 切换到jar包所在的目录
cd /path/to/your/jar/directory

# 启动应用并将日志输出到指定目录
java -jar $JAR_NAME >> $LOG_DIR/$APP_NAME.log 2>&1 &
