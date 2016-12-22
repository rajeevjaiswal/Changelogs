package com.saladevs.changelogclone.network;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface PlayStoreService {

    @GET("changelogs/")
    Observable<PackageChangelog> getChangelog(@Query("package") String packageName);
}
