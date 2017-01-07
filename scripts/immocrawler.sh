#!/bin/bash
RETR_NUM=$1
PROVIDER=$2
JSON_AGG=$(curl -s -XGET http://localhost:9200/immoscraper/publication/_search?q=provider:$PROVIDER -d '{
      "size" : 0,
    "aggs" : {
        "max_id" : { "max" : { "field" : "id" } }
    }
}')

MAX_ID=$(echo $JSON_AGG|jq -r '.aggregations.max_id.value'+1)
MAX_ID=`python -c "print int(float('$MAX_ID'))"`

echo "Retrieving $RETR_NUM for $PROVIDER starting from $MAX_ID"

java -jar /home/immoscraper/build/immoscraper-jar-with-dependencies.jar provider://$PROVIDER/$MAX_ID/$RETR_NUM elastic://localhost:9300/immoscraper/publication
