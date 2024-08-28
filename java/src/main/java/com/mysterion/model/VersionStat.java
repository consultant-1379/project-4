package com.mysterion.model;

public class VersionStat {
    private String versionName;
    private int info;
    private int warning;
    private int error;

    public VersionStat(String name) {
        this.versionName = name;
        info = 0;
        warning = 0;
        error = 0;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public int getInfo() {
        return info;
    }

    public int getWarning() {
        return warning;
    }

    public int getError() {
        return error;
    }

    public void addError() {
        error++;
    }

    public void addWarning() {
        warning++;
    }

    public void addInfo() {
        info++;
    }
}
