# 使用一个基础的 Java 8 镜像
FROM openjdk:8-jdk-alpine

# 设置工作目录
WORKDIR /app

# 将打包好的 JAR 包复制到容器的工作目录
COPY target/TradeManagement.jar app.jar

# 暴露应用程序使用的端口
EXPOSE 8080

# 定义容器启动时执行的命令
CMD ["java", "-jar", "app.jar"]