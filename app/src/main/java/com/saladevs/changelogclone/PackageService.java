package com.saladevs.changelogclone;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;

import com.saladevs.changelogclone.model.PackageUpdate;
import com.saladevs.changelogclone.utils.PackageUtils;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import io.realm.Realm;

public class PackageService extends IntentService {
    private static final String TAG = ".PackageService";

    private static final String ACTION_REMOVE_PACKAGE = "com.saladevs.changelogclone.action.REMOVE_PACKAGE";
    private static final String ACTION_FETCH_UPDATE = "com.saladevs.changelogclone.action.FETCH_UPDATE";

    private static final String EXTRA_PACKAGE_NAME = "com.saladevs.changelogclone.extra.PACKAGE_NAME";

    public PackageService() {
        super("PackageService");
    }


    public static void startActionRemovePackage(Context context, String packageName) {
        Intent intent = new Intent(context, PackageService.class);
        intent.setAction(ACTION_REMOVE_PACKAGE);
        intent.putExtra(EXTRA_PACKAGE_NAME, packageName);
        context.startService(intent);
    }

    public static void startActionFetchUpdate(Context context, String packageName) {
        Intent intent = new Intent(context, PackageService.class);
        intent.setAction(ACTION_FETCH_UPDATE);
        intent.putExtra(EXTRA_PACKAGE_NAME, packageName);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            final String packageName = intent.getStringExtra(EXTRA_PACKAGE_NAME);

            if (ACTION_REMOVE_PACKAGE.equals(action)) {
                handleActionRemovePackage(packageName);
            } else if (ACTION_FETCH_UPDATE.equals(action)) {
                handleActionFetchUpdate(packageName);
            }
        }
    }

    private void handleActionRemovePackage(String packageName) {
        Realm mRealm = Realm.getDefaultInstance();
        mRealm.executeTransaction(realm -> realm
                .where(PackageUpdate.class)
                .equalTo("packageName", packageName)
                .findAll()
                .deleteAllFromRealm()
        );
        mRealm.close();
    }

    private void handleActionFetchUpdate(String packageName) {
        PackageInfo packageInfo = PackageUtils.getPackageInfo(packageName);
        PackageUpdate packageUpdate = new PackageUpdate(
                packageInfo.packageName,
                packageInfo.versionName,
                new Date(),
                getPackageDescription(packageInfo));
        saveUpdate(packageUpdate);
    }

    private String getPackageDescription(PackageInfo pInfo) {
        if (PackageUtils.isPackageFromGooglePlay(pInfo.packageName)) {
            try {
                return new PlayStoreParser().fetchPackageUpdate(pInfo.packageName);
            } catch (IOException | ParseException e) {
                return getString(R.string.error_fetching_description);
            }
        } else {
            return getString(R.string.source_not_play_store);
        }
    }

    private void saveUpdate(PackageUpdate update) {
        if (update != null) {
            Realm mRealm = Realm.getDefaultInstance();
            mRealm.executeTransaction(realm -> realm.copyToRealmOrUpdate(update));
            mRealm.close();
        }
    }

}
