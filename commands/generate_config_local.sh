#!/bin/bash

# Load .env file
set -a
source .env
set +a

# Replace placeholders in config.template.yml and write to config.template.yml
envsubst < config/config.template.yml > config/local.yml

echo "✅ Config file generated: local.yml"
