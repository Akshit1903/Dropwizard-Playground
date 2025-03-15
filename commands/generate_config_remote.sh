#!/bin/sh

set -e  # Exit immediately if a command exits with a non-zero status

# Check if envsubst is available
if ! command -v envsubst >/dev/null 2>&1; then
    echo "❌ ERROR: envsubst command not found. Install gettext package."
    exit 1
fi

# Replace placeholders in config.template.yml and save to config.yml
envsubst < /app/config.template.yml > /app/config.yml

echo "✅ Config file generated: config.yml"
