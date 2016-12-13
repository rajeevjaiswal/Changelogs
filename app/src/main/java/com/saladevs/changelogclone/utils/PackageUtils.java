package com.saladevs.changelogclone.utils;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import com.saladevs.changelogclone.App;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class PackageUtils {

    public static final String PACKAGE_ANDROID_VENDING = "com.android.vending";
    public static final PackageManager mPackageManager = App.getContext().getPackageManager();

    public static PackageInfo getPackageInfo(String packageName) { //throws PackageManager.NameNotFoundException {
        try {
            return mPackageManager.getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException nnfe) {
            throw new RuntimeException(nnfe);
        }
    }

    public static boolean isPackageInstalled(String packageName) {
        try {
            mPackageManager.getPackageInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException nnfe) {
            return false;
        }
    }

    public static boolean isPackageFromGooglePlay(String packageName) {
        try {
            String installerPackage = mPackageManager.getInstallerPackageName(packageName);
            return installerPackage != null && installerPackage.equals(PACKAGE_ANDROID_VENDING);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public static List<PackageInfo> getPackageList() {

        // Get list of installed apps in the phone
        List<PackageInfo> packages = mPackageManager.getInstalledPackages(0);


        // Filter out packages not coming from Play Store
        Iterator<PackageInfo> it = packages.iterator();
        while (it.hasNext()) {
            PackageInfo app = it.next();
            if (!isPackageFromGooglePlay(app.packageName)) {
                it.remove();
            }
        }

        // Sort the packages by their application labels
        Collections.sort(packages, new Comparator<PackageInfo>() {
            @Override
            public int compare(PackageInfo lhs, PackageInfo rhs) {
                return mPackageManager.getApplicationLabel(lhs.applicationInfo).toString().
                        compareToIgnoreCase(mPackageManager.getApplicationLabel(rhs.applicationInfo).toString());
            }
        });

        return packages;
    }

    public static List<String> getPackageNameList() {
        ArrayList<String> names = new ArrayList<>();
        for (PackageInfo pi : getPackageList()) {
            names.add(pi.packageName);
        }
        return names;
    }

    public static CharSequence getAppLabel(PackageInfo pi) {
        return mPackageManager.getApplicationLabel(pi.applicationInfo);
    }

    public static Drawable getAppIconDrawable(PackageInfo pi) {
        return mPackageManager.getApplicationIcon(pi.applicationInfo);
    }
}
