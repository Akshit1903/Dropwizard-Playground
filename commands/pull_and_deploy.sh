#!/bin/bash

REPO_DIR="/home/akshit/Dropwizard-Playground"
TARGET_DIR="$REPO_DIR/target"
JAR_NAME="playground.jar"
CONFIG_PATH="$REPO_DIR/config/local.yml"
BRANCH="main"

NEW_JAR="$TARGET_DIR/$JAR_NAME"
BACKUP_JAR="$TARGET_DIR/playground_backup.jar"

cd "$REPO_DIR" || exit

# Fetch latest changes
pwd
git fetch origin "$BRANCH"

# Check if there are updates
if ! git diff --quiet HEAD origin/"$BRANCH"; then
    echo "Changes detected. Updating repository..."

    # Pull latest changes
    git pull origin "$BRANCH"

    # Build the project using local mvn
    /opt/maven/bin/mvn clean install -Djavax.net.ssl.trustStore=/etc/ssl/certs/java/cacerts -Djavax.net.ssl.trustStorePassword=changeit

    # Check if the new jar was built successfully
    if [ -f "$NEW_JAR" ]; then
        echo "New JAR built successfully. Preparing backup..."

        # Stop existing instance
        PID=$(pgrep -f "$BACKUP_JAR")
        if [ -n "$PID" ]; then
            echo "Stopping existing instance (PID: $PID)..."
            kill "$PID"
            sleep 5  # Allow time for shutdown
        fi

        # Backup old JAR before replacing
        if [ -f "$BACKUP_JAR" ]; then
            rm "$BACKUP_JAR"  # Remove old backup
        fi
        if [ -f "$NEW_JAR" ]; then
            mv "$NEW_JAR" "$BACKUP_JAR"
        fi

        # Start new instance
        echo "Starting new instance..."
        nohup java -jar "$BACKUP_JAR" server "$CONFIG_PATH" > update.log 2>&1 &

        # Wait briefly to check if the new instance runs
        sleep 10
        if pgrep -f "$BACKUP_JAR" > /dev/null; then
            echo "New instance is running successfully. Deleting old JAR..."
            rm -f "$NEW_JAR"
        else
            echo "New instance failed. Rolling back..."
            mv "$BACKUP_JAR" "$NEW_JAR"
            nohup java -jar "$NEW_JAR" server "$CONFIG_PATH" > update.log 2>&1 &
        fi
    else
        echo "Build failed, keeping the old version running."
    fi
else
    echo "No updates found."
fi
