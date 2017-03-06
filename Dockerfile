FROM java:8-jdk-alpine
ENV TZ="Asia/Shanghai"
ENV LANG C.UTF-8
ADD build/libs/yugong.jar /root/yugong.jar
ADD bin /root/
RUN chmod +x /root/*.sh;mkdir /root/logs
RUN set -x \
	&& wget -P ./ "http://repo.quancheng-ec.com/repository/documentation/pinpoint-agent-1.6.1-SNAPSHOT.zip" \
	&& unzip ./pinpoint-agent-1.6.1-SNAPSHOT.zip -d . \
    && rm -r ./pinpoint-agent-1.6.1-SNAPSHOT.zip
ENV JAVA_OPTS ""
WORKDIR /root
CMD ["./start.sh"]