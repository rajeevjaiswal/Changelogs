package com.saladevs.changelogclone.ui.details;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.saladevs.changelogclone.R;
import com.saladevs.changelogclone.model.PackageUpdate;

import java.util.List;

public class DetailsFragment extends Fragment implements DetailsMvpView {

    private DetailsPresenter mPresenter;
    private PackageInfo mPackageInfo;

    private RecyclerView mRecyclerView;
    private DetailsAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public DetailsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mPackageInfo = getArguments().getParcelable(DetailsActivity.PARAM_PACKAGE);
            mPresenter = new DetailsPresenter(mPackageInfo.packageName);
        } else {
            throw new IllegalArgumentException("Must provide PackageInfo to DetailsFragment");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new DetailsAdapter();
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mPresenter.attachView(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        mPresenter.detachView();
    }

    @Override
    public void showUpdates(List<PackageUpdate> updates) {
        mAdapter.setData(updates);
    }

}
