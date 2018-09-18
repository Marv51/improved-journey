#!/bin/bash

echo "make sure all containers are properly set up and hit a key..."
read

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
echo "Waiting for a little while... usually couple of seconds and hit a key..."
read

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
