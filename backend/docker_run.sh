#!/bin/bash
docker rm -f crypto_learn_cont
docker build -t crypto_learn .
docker run --name=crypto_learn_cont -p8080:8080 -i crypto_learn
