#!/bin/bash

# Define input and output files
TEMPLATE_FILE="/app/config.template.yml"
OUTPUT_FILE="/app/config.yml"

# Ensure the template file exists
if [[ ! -f "$TEMPLATE_FILE" ]]; then
    echo "❌ ERROR: Template file '$TEMPLATE_FILE' not found!"
    exit 1
fi

# Create the output file
cp "$TEMPLATE_FILE" "$OUTPUT_FILE"

# Read all environment variables and replace placeholders in the config file
while IFS='=' read -r key value; do
    # Skip empty lines and comments
    [[ -z "$key" || "$key" =~ ^#.*$ ]] && continue

    # Replace ${KEY} with its actual value
    sed -i "s|\${$key}|$value|g" "$OUTPUT_FILE"
done < <(env)

cat "$OUTPUT_FILE"

echo "✅ Config file generated: $OUTPUT_FILE"
