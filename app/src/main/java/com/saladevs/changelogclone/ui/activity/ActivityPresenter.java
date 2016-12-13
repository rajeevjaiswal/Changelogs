package com.saladevs.changelogclone.ui.activity;

import android.content.pm.PackageInfo;

import com.saladevs.changelogclone.model.PackageUpdate;
import com.saladevs.changelogclone.ui.BasePresenter;

import io.realm.Realm;
import io.realm.Sort;
import rx.Subscription;

class ActivityPresenter extends BasePresenter<ActivityFragment> {

    private static final String PREFS_FILE = "activity";
    private static final boolean enabledDescriptins = true;

    private Realm mRealm;
    private Subscription mSubscription;

    ActivityPresenter() {
        mRealm = Realm.getDefaultInstance();
    }

    @Override
    public void attachView(ActivityFragment mvpView) {
        super.attachView(mvpView);

        mSubscription = mRealm.where(PackageUpdate.class)
                .findAllSortedAsync("date", Sort.DESCENDING)
                .asObservable()
                .subscribe(u -> {
                    getMvpView().showPublications(u);
                });
    }

    @Override
    public void detachView() {
        super.detachView();
        if (!mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
        mRealm.close();
    }

    void onItemClicked(PackageInfo packageInfo) {
        getMvpView().startDetailsActivity(packageInfo);
    }
}
