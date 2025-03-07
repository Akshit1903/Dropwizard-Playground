#!/bin/bash

REPO_DIR="/home/akshit/Dropwizard-Playground"
JAR_PATH="$REPO_DIR/target/playground.jar"
CONFIG_PATH="$REPO_DIR/config/local.yml"
BRANCH="main"  # Change this if needed

cd "$REPO_DIR" || exit

# Fetch latest changes
git fetch origin "$BRANCH"

# Check if there are updates
if ! git diff --quiet HEAD origin/"$BRANCH"; then
    echo "Changes detected. Updating repository..."

    # Pull latest changes
    git pull origin "$BRANCH"

    # Build the project using local mvn
    mvn clean install -Djavax.net.ssl.trustStore=/etc/ssl/certs/java/cacerts -Djavax.net.ssl.trustStorePassword=changeit

    # Find and kill the running Java process
    PID=$(pgrep -f "playground.jar")
    if [ -n "$PID" ]; then
        echo "Stopping existing instance (PID: $PID)..."
        kill "$PID"
        sleep 5  # Allow time for shutdown
    fi

    # Start the new instance
    echo "Starting new instance..."
    nohup java -jar "$JAR_PATH" server "$CONFIG_PATH" > /dev/null 2>&1 &
else
    echo "No updates found."
fi
