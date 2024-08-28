#!/usr/bin/env python
import time

logFile = open("log")
dummyLogFile = open("tempLogs", "w")

while True:
    if logFile:
        line = logFile.readline()
        if "[INFO ]" in line or "[WARN ]" in line or "[ERROR]" in line:
            dummyLogFile.write(line)
            dummyLogFile.flush()
            time.sleep(0.4)
