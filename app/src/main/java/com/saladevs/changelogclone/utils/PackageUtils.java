package com.saladevs.changelogclone.utils;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import com.saladevs.changelogclone.App;

import rx.Observable;

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

    public static Observable<PackageInfo> getPackageList() {
        return Observable.from(mPackageManager.getInstalledPackages(0))
                .filter(pi -> isPackageFromGooglePlay(pi.packageName))
                .sorted((lhs, rhs) -> mPackageManager.getApplicationLabel(lhs.applicationInfo).toString().
                        compareToIgnoreCase(mPackageManager.getApplicationLabel(rhs.applicationInfo).toString()));
    }

    public static CharSequence getAppLabel(PackageInfo pi) {
        return mPackageManager.getApplicationLabel(pi.applicationInfo);
    }

    public static Drawable getAppIconDrawable(PackageInfo pi) {
        return mPackageManager.getApplicationIcon(pi.applicationInfo);
    }
}
