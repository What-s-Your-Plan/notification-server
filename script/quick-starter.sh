#!/bin/bash

echo "1. 💬 Start Kafka Server"
cd docker \
  && docker-compose -f docker-compose.local.yml up -d

echo "2. ⚡ bootJar"
cd .. \
  && ./gradlew clean bootRun