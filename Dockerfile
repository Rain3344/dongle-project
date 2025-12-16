# 第一阶段 构建
FROM maven:3.9.9 AS build
COPY ./ /app
WORKDIR /app
RUN mvn clean package

# 第二阶段 运行
FROM openjdk:17-jdk
RUN mkdir /app
WORKDIR /app
COPY --from=build /app/target/dt_chat_api-0.0.1-SNAPSHOT.jar /app/dt_chat_api-0.0.1-SNAPSHOT.jar

# 设置时区、暴露端口
ENV TZ=Asia/Shanghai
EXPOSE 9001

# 启动命令（通过外部挂载的配置文件覆盖默认配置）
CMD ["java", "-jar", "dt_chat_api-0.0.1-SNAPSHOT.jar"]

# java -agentpath:./JavaAgent.dll -jar ./enc_dongle-project-0.0.1-SNAPSHOT.jar