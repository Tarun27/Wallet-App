#!/bin/bash

KAFKA_CONTAINER_NAME=resources-kafka-1

echo "Creating Kafka topics inside container: $KAFKA_CONTAINER_NAME"

docker exec -it $KAFKA_CONTAINER_NAME kafka-topics --create --bootstrap-server localhost:9092 \
  --topic wallet.transaction.debit.request --partitions 1 --replication-factor 1

docker exec -it $KAFKA_CONTAINER_NAME kafka-topics --create --bootstrap-server localhost:9092 \
  --topic wallet.transaction.debit.success --partitions 1 --replication-factor 1

docker exec -it $KAFKA_CONTAINER_NAME kafka-topics --create --bootstrap-server localhost:9092 \
  --topic wallet.transaction.debit.failed --partitions 1 --replication-factor 1

docker exec -it $KAFKA_CONTAINER_NAME kafka-topics --create --bootstrap-server localhost:9092 \
  --topic wallet.transaction.credit.request --partitions 1 --replication-factor 1

docker exec -it $KAFKA_CONTAINER_NAME kafka-topics --create --bootstrap-server localhost:9092 \
  --topic wallet.transaction.credit.success --partitions 1 --replication-factor 1

docker exec -it $KAFKA_CONTAINER_NAME kafka-topics --create --bootstrap-server localhost:9092 \
  --topic wallet.transaction.credit.failed --partitions 1 --replication-factor 1

echo "✅ All topics created"
