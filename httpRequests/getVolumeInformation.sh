#!/usr/bin/zsh

curl -v -H "Content-Type: application/json" \
  -H "X-Auth-PSK: $1" \
  -X POST \
  -d '{"id": 20, "method": "getVolumeInformation", "version": "1.0", "params": [] }' \
  http://192.168.1.241:3000/sony/audio
