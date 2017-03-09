FROM java:8-jdk-alpine
ENV TZ="Asia/Shanghai"
ENV LANG C.UTF-8
ADD build/libs/yugong.jar /root/yugong.jar
ADD bin /root/
RUN chmod +x /root/*.sh;mkdir /root/logs
ENV JAVA_OPTS ""
ENV PINPINT_AGENT_VERSION "1.6.1-SNAPSHOT"
WORKDIR /root
RUN /agent.sh
CMD ["./start.sh"]