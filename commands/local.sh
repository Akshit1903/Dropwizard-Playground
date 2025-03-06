#!/bin/bash

# Set the image name
IMAGE_NAME="playground"

VERSION=$(mvn help:evaluate -Dexpression=project.version -q -DforceStdout)
docker build -t "$IMAGE_NAME":"$VERSION" .

# Run the container in detached mode and capture its ID
CONTAINER_ID=$(docker run --rm -p 8080:8080 -d "$IMAGE_NAME":"$VERSION")
docker logs -f "$CONTAINER_ID"

# Wait for the container to stop
docker wait "$CONTAINER_ID"

# Remove the stopped container
docker rm "$CONTAINER_ID"

# Remove the image
docker rmi "$IMAGE_NAME":"$VERSION"

echo "✅ Container and image removed successfully!"



