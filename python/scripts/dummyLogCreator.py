#!/usr/bin/env python

import time, random

warn = ["WARNING", "Some huge problem has occurred"]
info = ["INFO", "Here is some info"]
error = ["ERROR", "Some error has occurred"]

choiceList = [warn, info, error]
versionList = ["v1.0.0", "v1.0.1", "v1.0.2"]

file = open("dummyLog.txt", "w")
while True:
    randNum = random.randint(0, 2)
    versionNum = random.randint(0, 2)
    string = f"[{choiceList[randNum][0]}] {versionList[versionNum]} {time.asctime(time.localtime(time.time()))} " \
             f"{choiceList[randNum][1]}\n"
    file.write(string)
    file.flush()
    time.sleep(5)
