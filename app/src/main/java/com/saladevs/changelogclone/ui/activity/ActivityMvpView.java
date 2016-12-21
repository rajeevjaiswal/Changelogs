package com.saladevs.changelogclone.ui.activity;

import android.content.pm.PackageInfo;

import com.saladevs.changelogclone.model.PackageUpdate;
import com.saladevs.changelogclone.ui.MvpView;

import java.util.List;

public interface ActivityMvpView extends MvpView {

    void showEmptyState(boolean b);

    void showUpdates(List<PackageUpdate> updates);

    void startDetailsActivity(PackageInfo packageInfo);
}
