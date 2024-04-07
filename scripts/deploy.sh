#!/bin/zsh

CONTAINER_NAME=albion-rmt-backend
CONTAINER_KEYSTORE_LOCATION=/certs/keystore.p12

docker pull ghcr.io/vaynerakawalo/albion-rmt-backend:$1

docker kill $CONTAINER_NAME

docker system prune -f

docker run -dp 8443:8050 \
 -e KEYSTORE_LOCATION=$CONTAINER_KEYSTORE_LOCATION \
 -e KEYSTORE_PASSWORD=$KEYSTORE_PASSWORD \
 -v `dirname $KEYSTORE_LOCATION`:`dirname $CONTAINER_KEYSTORE_LOCATION` \
 --name $CONTAINER_NAME \
 ghcr.io/vaynerakawalo/albion-rmt-backend:$1