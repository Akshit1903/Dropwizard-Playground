#!/bin/bash

# Replace placeholders in config.template.yml and write to config.template.yml
envsubst < /app/config.template.yml > /app/config.yml
echo "✅ Config file generated: config.yml"
