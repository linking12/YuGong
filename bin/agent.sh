#!/usr/bin/env bash
SOURCE="$0"
while [ -h "$SOURCE"  ]; do # resolve $SOURCE until the file is no longer a symlink
    DIR="$( cd -P "$( dirname "$SOURCE"  )" && pwd  )"
    SOURCE="$(readlink "$SOURCE")"
    [[ $SOURCE != /*  ]] && SOURCE="$DIR/$SOURCE" # if $SOURCE was a relative symlink, we need to resolve it relative to the path where the symlink file was located
done
DIR="$( cd -P "$( dirname "$SOURCE"  )" && pwd  )"
apk add --update curl
rm -rf /var/cache/apk/*
mkdir $DIR/pinpoint-agent-$PINPINT_AGENT_VERSION
curl -o $DIR/pinpoint-agent-$PINPINT_AGENT_VERSION/pinpoint-agent-$PINPINT_AGENT_VERSION.zip --user 'liushiming:Hello899' http://repo.quancheng-ec.com/repository/documentation/pinpoint-agent-$PINPINT_AGENT_VERSION.zip
unzip -o -d $DIR/pinpoint-agent-$PINPINT_AGENT_VERSION $DIR/pinpoint-agent-$PINPINT_AGENT_VERSION/pinpoint-agent-$PINPINT_AGENT_VERSION.zip
rm -r $DIR/pinpoint-agent-$PINPINT_AGENT_VERSION/pinpoint-agent-$PINPINT_AGENT_VERSION.zip
