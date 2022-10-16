#!/usr/bin/zsh

curl -v -H "Content-Type: application/json" \
  -H "X-Auth-PSK: $1" \
  -X POST \
  -d '{
          "method": "getPowerStatus",
          "id": 50,
          "params": [],
          "version": "1.0"
      }' \
  http://192.168.1.111/sony/system
