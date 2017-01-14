#!/bin/bash
javac -classpath .:filters.jar CountRices.java
java -classpath .:filters.jar CountRices
