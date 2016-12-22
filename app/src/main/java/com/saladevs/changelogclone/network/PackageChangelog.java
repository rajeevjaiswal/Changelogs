package com.saladevs.changelogclone.network;

import java.util.List;

public class PackageChangelog {
    private String packageName;
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
