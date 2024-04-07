#!/bin/zsh

CONTAINER_NAME=albion-rmt-backend
CONTAINER_KEY_STORE_LOCATION=/certs/albion-rmt.p12

docker pull ghcr.io/vaynerakawalo/albion-rmt-backend:$1

docker kill $CONTAINER_NAME

docker system prune -f

docker run -dp 8050:8050 \
 -e KEY_STORE_LOCATION=$CONTAINER_KEY_STORE_LOCATION \
 -e KEY_STORE_PASSWORD=$KEY_STORE_PASSWORD \
 -v $KEY_STORE_LOCATION:$CONTAINER_KEY_STORE_LOCATION \
 --name $CONTAINER_NAME \
 ghcr.io/vaynerakawalo/albion-rmt-backend:$1