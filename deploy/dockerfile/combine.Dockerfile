# 使用基于 JDK 17 的 OpenJDK 基础镜像
FROM openjdk:17-jdk-slim

# 设置项目路径变量
ARG PROJECT_PATH=deliver-backend/deliver-application/deliver-application-combine

# 设置工作目录
WORKDIR /combine

# 复制项目的 JAR 文件到容器中
COPY ${PROJECT_PATH}/target/*.jar deliver-combine.jar

# 复制 application.yml 到容器中
COPY ${PROJECT_PATH}/src/main/resources/application.yml application.yml

# 暴露应用程序的端口，根据你的实际情况修改
EXPOSE 9090

# 定义启动命令，使用外部的 application.yml 配置
CMD ["java", "-jar", "-Dspring.config.location=application.yml", "deliver-combine.jar"]