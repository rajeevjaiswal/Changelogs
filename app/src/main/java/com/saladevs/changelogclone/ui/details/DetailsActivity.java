package com.saladevs.changelogclone.ui.details;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.saladevs.changelogclone.R;
import com.saladevs.changelogclone.utils.PackageUtils;

public class DetailsActivity extends AppCompatActivity {

    private static final String PARAM_PACKAGE = "package_info";

    private PackageInfo mPackageInfo;

    private Toolbar mToolbar;

    public static void startWith(Context context, PackageInfo packageInfo) {
        Intent intent = new Intent(context, DetailsActivity.class);
        intent.putExtra(PARAM_PACKAGE, packageInfo);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Get packageInfo from Intent bundle
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mPackageInfo = bundle.getParcelable(PARAM_PACKAGE);
        }

        // Put fragment with the same bundle that the Activity received
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        DetailsFragment fragment = DetailsFragment.newInstance(mPackageInfo);
        transaction.replace(R.id.container, fragment).commit();

        // Get View references
        ImageView icon = (ImageView) findViewById(R.id.icon);
        TextView label = (TextView) findViewById(R.id.textPrimary);
        TextView subtitle = (TextView) findViewById(R.id.textSecondary);

        // Setup UI
        if (mPackageInfo != null) {
            icon.setImageDrawable(PackageUtils.getAppIconDrawable(mPackageInfo));
            label.setText(PackageUtils.getAppLabel(mPackageInfo));
            subtitle.setText(mPackageInfo.packageName);
        }
    }
}
