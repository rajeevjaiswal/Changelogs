package com.saladevs.changelogclone;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.saladevs.changelogclone.utils.PackageUtils;

import timber.log.Timber;

public class PackageReceiver extends BroadcastReceiver {

    private static final String TAG = ".PackageReceiver";

    public PackageReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        String packageName = intent.getData().getEncodedSchemeSpecificPart();

        if (intent.getAction().equals(Intent.ACTION_PACKAGE_ADDED)) {
            // Package installed

        } else if (intent.getAction().equals(Intent.ACTION_PACKAGE_REPLACED)) {
            // Package updated
            Timber.d("Package updated - %s", packageName);
            if (PackageUtils.isPackageFromGooglePlay(packageName)) {
                PackageService.startActionFetchUpdate(context, packageName);
            }

        } else if (intent.getAction().equals(Intent.ACTION_PACKAGE_FULLY_REMOVED)) {
            // Package uninstalled
            Timber.d("Package uninstalled - %s", packageName);
            PackageService.startActionRemovePackage(context, packageName);
        }
    }
}
