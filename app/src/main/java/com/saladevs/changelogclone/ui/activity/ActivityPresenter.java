package com.saladevs.changelogclone.ui.activity;

import android.content.pm.PackageInfo;

import com.saladevs.changelogclone.model.PackageUpdate;
import com.saladevs.changelogclone.ui.BasePresenter;

import io.realm.Realm;
import io.realm.Sort;
import rx.Subscription;

class ActivityPresenter extends BasePresenter<ActivityFragment> {

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
                .subscribe(updates -> {
                    if (updates.size() > 0) {
                        getMvpView().showEmptyState(false);
                        getMvpView().showUpdates(updates);
                    } else {
                        getMvpView().showEmptyState(true);
                    }
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
