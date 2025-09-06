#!/usr/bin/env bash
set -e

cd storefront

chmod +x ./gradlew

./gradlew --no-daemon bootRun
