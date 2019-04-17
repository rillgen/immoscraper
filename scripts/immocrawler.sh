#!/bin/bash
BASE_URL=$1
RETR_NUM=$2
PROVIDER=$3
JSON_AGG=$(curl -s -XGET -H "Content-Type: application/json" $BASE_URL/immoscraper/publication/_search?q=provider:$PROVIDER -d '{
      "size" : 0,
    "aggs" : {
        "max_id" : { "max" : { "field" : "id" } }
    }
}')

MAX_ID=$(echo $JSON_AGG|jq -r '.aggregations.max_id.value'+1)
MAX_ID=`python -c "print int(float('$MAX_ID'))"`

echo "Retrieving $RETR_NUM for $PROVIDER starting from $MAX_ID"

java -jar /home/ubuntu/immoscraper/target/immoscraper.jar provider://$PROVIDER/$MAX_ID/$RETR_NUM ${BASE_URL/http/elastic}/immoscraper/publication
