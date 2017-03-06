FROM java:8-jdk-alpine
ENV TZ="Asia/Shanghai"
ENV LANG C.UTF-8
RUN echo https://dl-cdn.alpinelinux.org/alpine/edge/testing >>/etc/apk/repositories 
ENV CURL_VERSION 7.50.1
RUN apk add --update --no-cache openssl openssl-dev nghttp2-dev ca-certificates
RUN apk add --update --no-cache --virtual curldeps g++ make perl && \
    wget https://curl.haxx.se/download/curl-$CURL_VERSION.tar.bz2 && \
    tar xjvf curl-$CURL_VERSION.tar.bz2 && \
    rm curl-$CURL_VERSION.tar.bz2 && \
    cd curl-$CURL_VERSION && \
    ./configure \
	    --with-nghttp2=/usr \
	    --prefix=/usr \
	    --with-ssl \
	    --enable-ipv6 \
	    --enable-unix-sockets \
	    --without-libidn \
	    --disable-static \
	    --disable-ldap \
	    --with-pic && \
	make && \
	make install && \
	cd / && \
	rm -r curl-$CURL_VERSION && \
	rm -r /var/cache/apk && \
	rm -r /usr/share/man && \
	apk del curldeps
ADD build/libs/yugong.jar /root/yugong.jar
ADD bin /root/
RUN chmod +x /root/*.sh;mkdir /root/logs
RUN curl -O --user 'liushiming:Hello899' http://repo.quancheng-ec.com/repository/documentation/pinpoint-agent-1.6.1-SNAPSHOT.zip \
	&& unzip ./pinpoint-agent-1.6.1-SNAPSHOT.zip -d . \
    && rm -r ./pinpoint-agent-1.6.1-SNAPSHOT.zip
ENV JAVA_OPTS ""
WORKDIR /root
CMD ["./start.sh"]