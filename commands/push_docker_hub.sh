#!/bin/bash

# Set the image name
IMAGE_NAME="playground"
DOCKER_USERNAME="akshit1903"

VERSION=$(mvn help:evaluate -Dexpression=project.version -q -DforceStdout)
docker build -t "$IMAGE_NAME":"$VERSION" .

docker tag "$IMAGE_NAME":"$VERSION" "$DOCKER_USERNAME"/"$IMAGE_NAME":"$VERSION"

docker login

docker push "$DOCKER_USERNAME"/"$IMAGE_NAME":"$VERSION"
