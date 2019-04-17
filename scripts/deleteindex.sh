#!/bin/bash
BASE_URL=$1
curl -XDELETE $BASE_URL/$1
