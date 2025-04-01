#!/bin/bash

# SSH를 통해 원격 서버에 접속하여 MySQL 상태 확인
ssh -i /path/to/nhn_key.pem rocky@192.168.0.13 "mysqladmin ping -h localhost -u root -p'1234'" > /dev/null 2>&1

# MySQL이 정상적으로 응답하면 exit 0, 그렇지 않으면 exit 1
if [ $? -eq 0 ]; then
  exit 0
else
  exit 1
fi
