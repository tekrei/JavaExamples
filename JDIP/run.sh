#!/bin/bash
javac -classpath .:filters.jar *.java
java -classpath .:filters.jar MainWindow laol.jpg
