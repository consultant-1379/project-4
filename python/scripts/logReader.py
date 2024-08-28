#!/usr/bin/python

from pymongo import MongoClient, errors
import os
import time
import re

FILE_PATH = "tempLogs"


def processLogs():
    while True:
        if os.path.exists(FILE_PATH):
            try:
                logFile = open(FILE_PATH)
                logLines = follow(logFile)
                for line in logLines:
                    record = createRecordFromRealLog(line)
                    logsCollection = getCollection()
                    if logsCollection:
                        logsCollection.insert_one(record)
                        print("Item added to DB")
            except IOError as e:
                print(f"An error has occurred. \n {e}")


def follow(theFile):
    theFile.seek(0, os.SEEK_END)
    while True:
        line = theFile.readline()
        if not line:
            time.sleep(0.05)
            continue
        yield line


def setUpDB():
    try:
        myClient = MongoClient(
            host="project-4_mongo_1" + ":" + "27017",
            serverSelectionTimeoutMS=3000,
            username="root",
            password="example", )
    except errors.ServerSelectionTimeoutError as err:
        print(f"pymongo ERROR {err}")
        myClient = None
    return myClient


def getCollection():
    client = setUpDB()
    logsDB = client["Logs"]
    if client:
        logsCollection = logsDB["LogsCollection"]
    else:
        logs = None
    return logsCollection


def createRecord(line):
    splitLine = line.split(" ")
    severity = re.search(r"(?<=\[).+?(?=\])", line).group()
    version = re.search(r"(v[0-9](\.[0-9])+)", line).group()
    date = f"{splitLine[4]}/{splitLine[3]}/{splitLine[6]}"
    # time = re.search(r"([0-24]+:[0-60]+:[0-60]+)", line).group()
    time = splitLine[5]
    message = splitLine[7:].join(" ")
    record = {"Severity": str(severity), "Version": str(version), "Date": str(date),
              "Time": str(time), "Message": str(message)}
    return record


def createRecordFromRealLog(line):
    # 2020-07-17 10:20:41,650 [INFO ] [LoggingScenarioListener] VUser: 1 Starting flow : Close Tool
    # Date
    # Time
    # Severity
    # Message
    splitLine = line.split(" ")
    date = splitLine[0]
    time = splitLine[1]
    severity = re.search(r"(?<=\[).+?(?=\])", line).group()
    message = " ".join(splitLine[4:])
    version = re.search(r"(v[0-9](\.[0-9])+)", line)
    if not version:
        version = "v1.0.0"
    else:
        version = version.group()
    record = {"Severity": str(severity), "Version": str(version), "Date": str(date),
              "Time": str(time), "Message": str(message.strip())}
    print(record)
    return record


processLogs()
