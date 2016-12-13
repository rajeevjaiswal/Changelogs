package com.saladevs.changelogclone.ui.details;

import com.saladevs.changelogclone.model.PackageUpdate;
import com.saladevs.changelogclone.ui.MvpView;

import java.util.List;

public interface DetailsMvpView extends MvpView {

    void showUpdates(List<PackageUpdate> updates);

}
