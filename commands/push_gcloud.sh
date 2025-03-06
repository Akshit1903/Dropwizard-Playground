#!/bin/bash

# Set the image name
IMAGE_NAME="playground"
GCLOUD_PROJECT_ID="playground-452816"
VERSION=$(mvn help:evaluate -Dexpression=project.version -q -DforceStdout)

gcloud auth login

gcloud config set project "$GCLOUD_PROJECT_ID"

docker build -t "$IMAGE_NAME":"$VERSION" .

#docker tag "$IMAGE_NAME":"$VERSION" gcr.io/"$GCLOUD_PROJECT_ID"/"$IMAGE_NAME":"$VERSION"

#docker push gcr.io/"$GCLOUD_PROJECT_ID"/"$IMAGE_NAME":"$VERSION"

docker save "$IMAGE_NAME" -o "$IMAGE_NAME".tar
