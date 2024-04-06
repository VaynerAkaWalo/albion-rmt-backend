#!/bin/zsh

CONTAINER_NAME = "albion-rmt-backend"

docker pull ghcr.io/vaynerakawalo/albion-rmt-backend:$1

docker kill CURRENT_CONTAINER

docker system prune -f

docker run -dp 8050:8050 --name albion-rmt-backend  ghcr.io/vaynerakawalo/albion-rmt-backend:$1