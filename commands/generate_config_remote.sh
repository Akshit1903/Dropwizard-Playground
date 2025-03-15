#!/bin/bash

# Define input and output files
CONFIG_FILE="/app/config.yml"

# Ensure the template file exists
if [[ ! -f "$CONFIG_FILE" ]]; then
    echo "❌ ERROR: Template file '$CONFIG_FILE' not found!"
    exit 1
fi

# Read all environment variables and replace placeholders in the config file
while IFS='=' read -r key value; do
    # Skip empty lines and comments
    [[ -z "$key" || "$key" =~ ^#.*$ ]] && continue
    # Replace ${KEY} with its actual value
    sed -i "s|\${$key}|$value|g" "$CONFIG_FILE"
done < <(env)

cat "$CONFIG_FILE"

echo "✅ Config file generated: $CONFIG_FILE"
