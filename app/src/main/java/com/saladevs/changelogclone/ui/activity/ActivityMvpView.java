package com.saladevs.changelogclone.ui.activity;

import android.content.pm.PackageInfo;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;

import com.saladevs.changelogclone.model.PackageUpdate;
import com.saladevs.changelogclone.ui.MvpView;

import java.util.List;

public interface ActivityMvpView extends MvpView {

    void showPublications(List<PackageUpdate> updates);

    void startDetailsActivity(PackageInfo packageInfo);
}
