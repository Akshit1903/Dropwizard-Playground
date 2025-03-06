docker rm -f $(docker ps -aq)  # Remove all containers
docker rmi -f $(docker images -aq)  # Remove all images
