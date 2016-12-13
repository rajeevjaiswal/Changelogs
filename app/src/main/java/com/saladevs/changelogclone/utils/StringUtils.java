package com.saladevs.changelogclone.utils;

import com.saladevs.changelogclone.PlayStoreParser;

public class StringUtils {
    public static String getCleanDescription(String description) {
        if (description != null) {
            return description.replace(PlayStoreParser.CHANGE_SEPARATOR, "\n");
        } else {
            return null;
        }
    }
}
