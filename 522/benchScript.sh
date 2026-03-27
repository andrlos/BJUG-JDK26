#!/bin/bash

# JDK26

javac G1RealisticBenchmark.java
OUTPUT_FILE="throughput26.log"
RUNS=100

# clear previous output
> "$OUTPUT_FILE"

for i in $(seq 1 $RUNS); do
    echo "Run $i..."

    java -Xms64m -Xmx64m -XX:+UseG1GC G1RealisticBenchmark \
        | grep "Throughput" >> "$OUTPUT_FILE"

done

echo "Done 26. Results stored in $OUTPUT_FILE"

# JDK25

javac G1RealisticBenchmark.java
OUTPUT_FILE="throughput25.log"
RUNS=100

# clear previous output
> "$OUTPUT_FILE"

for i in $(seq 1 $RUNS); do
    echo "Run $i..."

    java -Xms64m -Xmx64m -XX:+UseG1GC G1RealisticBenchmark \
        | grep "Throughput" >> "$OUTPUT_FILE"

done

echo "Done 25. Results stored in $OUTPUT_FILE"

