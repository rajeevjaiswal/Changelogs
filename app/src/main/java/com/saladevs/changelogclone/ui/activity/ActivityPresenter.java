package com.saladevs.changelogclone.ui.activity;

import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.preference.PreferenceManager;

import com.f2prateek.rx.preferences.Preference;
import com.f2prateek.rx.preferences.RxSharedPreferences;
import com.saladevs.changelogclone.App;
import com.saladevs.changelogclone.model.PackageUpdate;
import com.saladevs.changelogclone.ui.BasePresenter;

import io.realm.Realm;
import io.realm.Sort;
import rx.subscriptions.CompositeSubscription;

class ActivityPresenter extends BasePresenter<ActivityFragment> {

    private static final String PREF_CHANGELOG_STYLE = "prefChangelogStyle";

    private Realm mRealm;
    private CompositeSubscription mSubscriptions = new CompositeSubscription();
    private Preference<Integer> mChangelogStylePref;

    ActivityPresenter() {
        mRealm = Realm.getDefaultInstance();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(App.getContext());
        RxSharedPreferences rxPrefs = RxSharedPreferences.create(prefs);
        mChangelogStylePref = rxPrefs.getInteger(PREF_CHANGELOG_STYLE);
    }

    @Override
    public void attachView(ActivityFragment mvpView) {
        super.attachView(mvpView);

        // Subscribe to Style SharedPreferences changes
        mSubscriptions.add(mChangelogStylePref.asObservable()
                .subscribe(style -> getMvpView().changeChangelogStyle(style)));

        // Subscribe to Realm changes
        mSubscriptions.add(mRealm.where(PackageUpdate.class)
                .findAllSortedAsync("date", Sort.DESCENDING)
                .asObservable()
                .subscribe(updates -> {
                    if (updates.size() > 0) {
                        getMvpView().showEmptyState(false);
                        getMvpView().showUpdates(updates);
                    } else {
                        getMvpView().showEmptyState(true);
                    }
                }));
    }

    @Override
    public void detachView() {
        super.detachView();
        mSubscriptions.unsubscribe();
        mRealm.close();
    }

    void onItemClicked(PackageInfo packageInfo) {
        getMvpView().startDetailsActivity(packageInfo);
    }

    void onChangelogStyleSelected(int style) {
        mChangelogStylePref.set(style);
    }
}
