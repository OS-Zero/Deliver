# 使用基于Java 8的官方镜像作为基础
FROM openjdk:8

# 设置工作目录
WORKDIR /app

# 复制.jar文件到镜像中
COPY ../../deliver-backend-admin/target/deliver-backend-admin.jar /app/deliver-backend-admin.jar

# 定义容器启动时执行的命令
CMD ["java", "-jar", "deliver-backend-admin.jar"]
