package com.saladevs.changelogclone.network;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public interface PlayStoreService {

    @GET("changelogs/{packageName}/latest")
    Observable<PackageChangelog> getChangelog(@Path("packageName") String packageName);
}
