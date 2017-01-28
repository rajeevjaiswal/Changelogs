package com.saladevs.changelogclone.ui.activity;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;

public class ActivityItemAnimator extends DefaultItemAnimator {

    @Override
    public boolean canReuseUpdatedViewHolder(RecyclerView.ViewHolder viewHolder) {
        return true;
    }

}