#!/bin/bash
curl -XPUT "http://localhost:9200/$1" -d '{
  "mappings": {
    "publication": {
      "properties": {
        "location": {
          "type": "geo_point"
        }
      }
    }
  },
  "settings": {
    "index": {
      "number_of_shards": 3,
      "number_of_replicas": 1
    },
    "analysis": {
      "analyzer": {
        "default": {
          "tokenizer": "standard",
          "filter": [
            "standard",
            "lowercase",
            "asciifolding",
            "word_delimiter"
          ]
        }
      }
    }
  }
}'
