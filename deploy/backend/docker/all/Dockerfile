# 使用基于Java 17的官方镜像作为基础
FROM openjdk:17

# 设置工作目录
WORKDIR /app

# 复制.jar文件到镜像中
COPY ../../deliver-backend-all/target/deliver-backend-all.jar /app/deliver-backend-all.jar

# 定义容器启动时执行的命令
CMD ["java", "-jar", "deliver-backend-all.jar"]
