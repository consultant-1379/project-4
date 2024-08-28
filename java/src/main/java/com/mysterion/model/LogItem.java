package com.mysterion.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "LogsCollection")
public class LogItem {
    @Id
    private ObjectId id;

    @Field("Severity")
    private String severity;
    @Field("Message")
    private String message;
    @Field("Version")
    private String version;
    @Field("Date")
    private String  date;
    @Field("Time")
    private String time;

    public LogItem(){}

    public LogItem(ObjectId id, String severity, String message, String version, String date, String time){
        this.id = id;
        this.severity = severity;
        this.message = message;
        this.version = version;
        this.date = date;
        this.time = time;
    }

    public String getId() {
        return id.toHexString();
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return getId() + getSeverity() + getVersion() + getDate() + getMessage() + getTime();
    }
}
