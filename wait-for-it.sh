#!/usr/bin/env bash

TIMEOUT=15
WAIT_HOSTS="$1"
shift
COMMAND="$@"

if [ -z "$WAIT_HOSTS" ] || [ -z "$COMMAND" ]; then
    echo "Usage: $0 host:port [cmd...]"
    exit 1
fi

# Extract host and port
HOST=$(echo $WAIT_HOSTS | cut -d':' -f1)
PORT=$(echo $WAIT_HOSTS | cut -d':' -f2)

# Wait for the host and port to be ready
echo "Waiting for $HOST:$PORT..."

for i in $(seq 1 $TIMEOUT); do
    nc -z "$HOST" "$PORT" > /dev/null 2>&1
    result=$?
    if [ $result -eq 0 ]; then
        break
    fi
    echo "Waiting for $HOST:$PORT... retry $i"
    sleep 1
done

if [ $result -ne 0 ]; then
    echo "Timeout occurred after waiting for $HOST:$PORT for $TIMEOUT seconds."
    exit 1
fi

echo "$HOST:$PORT is available. Running command: $COMMAND"
exec $COMMAND
