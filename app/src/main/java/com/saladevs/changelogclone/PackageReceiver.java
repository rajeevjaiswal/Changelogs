package com.saladevs.changelogclone;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.saladevs.changelogclone.utils.PackageUtils;

public class PackageReceiver extends BroadcastReceiver {

    private static final String TAG = ".PackageReceiver";

    public PackageReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        String packageName = intent.getData().getEncodedSchemeSpecificPart();

        if (PackageUtils.isPackageFromGooglePlay(packageName)) {

            if (intent.getAction().equals(Intent.ACTION_PACKAGE_ADDED)) {
                // Package installed
                //PackageService.startActionFetchUpdate(context, packageName);

            } else if (intent.getAction().equals(Intent.ACTION_PACKAGE_REPLACED)) {
                // Package updated
                PackageService.startActionFetchUpdate(context, packageName);

            } else if (intent.getAction().equals(Intent.ACTION_PACKAGE_FULLY_REMOVED)) {
                // Package uninstalled
                PackageService.startActionRemovePackage(context, packageName);

            }
        }
    }
}
