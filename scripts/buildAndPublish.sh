#!/bin/sh

current_dir=$(dirname `realpath "$0"`)
parent_dir=$(dirname $current_dir)

cd $parent_dir

GIT_COMMIT=`git rev-parse --short HEAD`

./gradlew clean bootJar

docker build -t ghcr.io/vaynerakawalo/albion-rmt-backend:$GIT_COMMIT .

docker push ghcr.io/vaynerakawalo/albion-rmt-backend:$GIT_COMMIT

cd $current_dir

echo "Created and published image with tag: $GIT_COMMIT"