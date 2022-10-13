#!/usr/bin/zsh

curl -v -H "Content-Type: application/json" \
  -H "X-Auth-PSK: Superteam1" \
  -X POST \
  -d '{
          "method": "getCurrentExternalInputsStatus",
          "id": 105,
          "params": [],
          "version": "1.1"
      }' \
  http://192.168.1.111/sony/avContent
