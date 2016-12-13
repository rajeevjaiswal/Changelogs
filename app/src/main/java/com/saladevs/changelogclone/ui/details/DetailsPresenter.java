package com.saladevs.changelogclone.ui.details;

import com.saladevs.changelogclone.model.PackageUpdate;
import com.saladevs.changelogclone.ui.BasePresenter;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import rx.Observable;
import rx.Subscription;

public class DetailsPresenter extends BasePresenter<DetailsMvpView> {

    private Realm mRealm;
    private Subscription mSubscription;
    private String mPackageName;

    public DetailsPresenter(String packageName) {
        mRealm = Realm.getDefaultInstance();
        mPackageName = packageName;
    }

    @Override
    protected void attachView(DetailsMvpView mvpView) {
        super.attachView(mvpView);

        mSubscription = mRealm.where(PackageUpdate.class)
                .equalTo("packageName", mPackageName)
                .findAllSorted("date", Sort.DESCENDING)
                .asObservable()
                .subscribe(updates -> getMvpView().showUpdates(updates));
    }

    @Override
    protected void detachView() {
        super.detachView();

        if (!mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
        mRealm.close();
    }
}
