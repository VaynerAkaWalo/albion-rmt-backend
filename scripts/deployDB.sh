#!/bin/zsh

docker network create albion-network

docker run \
  --name postgres \
  -e POSTGRES_DB=albionrmt \
  -e POSTGRES_USER=$DB_USER \
  -e POSTGRES_PASSWORD=$DB_PASSWORD \
  -p 5432:5432 \
  -v /data:/var/lib/postgresql/data \
  -d \
  postgres
