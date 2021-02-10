#!/bin/sh

uri=`curl http://localhost:8088/product/uri?id=1`
echo $uri

order_url="http://localhost:8088/order/${uri}"
echo $order_url

for i in {1..50}
do
    curl -d '{"customer": 16, "product": "1"}' -H 'Content-Type: application/json' $order_url &
done
