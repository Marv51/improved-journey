#!/bin/bash

echo "First round of queries... (should all fail)"
echo ""
echo "PRODUCTS:"
curl localhost:8080/search/products
echo ""
echo "CATEGORIES:"
curl localhost:8080/search/categories
echo ""
echo "USERS:"
curl localhost:8080/search/users
echo ""

echo ""
echo "Waiting for a while... (10s)"
sleep 10

echo ""
echo "Second round of queries... (Could fail, but should not)"
echo ""
echo "PRODUCTS:"
curl localhost:8080/search/products
echo ""
echo "CATEGORIES:"
curl localhost:8080/search/categories
echo ""
echo "USERS:"
curl localhost:8080/search/users
echo ""

echo ""
echo "If queries failed in the 2nd round, the next will work..."
