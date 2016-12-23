package com.saladevs.changelogclone.network;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PackageChangelog {
    @SerializedName("packageName")
    private String packageName;
    @SerializedName("changes")
    private List<String> changes;

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public List<String> getChanges() {
        return changes;
    }

    public void setChanges(List<String> changes) {
        this.changes = changes;
    }
}
