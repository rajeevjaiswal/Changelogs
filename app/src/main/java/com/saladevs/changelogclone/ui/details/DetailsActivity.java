package com.saladevs.changelogclone.ui.details;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.saladevs.changelogclone.R;
import com.saladevs.changelogclone.utils.PackageUtils;

public class DetailsActivity extends AppCompatActivity {

    public static final String PARAM_PACKAGE = "package_info";
    public static final String PLAY_STORE_URL = "https://play.google.com/store/apps/details?id=";

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
        DetailsFragment fragment = new DetailsFragment();
        fragment.setArguments(bundle);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_open_app:
                startPackageActivity(mPackageInfo.packageName);
                return true;
            case R.id.action_open_store:
                startPlayStoreActivity(mPackageInfo.packageName);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void startPlayStoreActivity(String packageName) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(PLAY_STORE_URL + packageName)));
    }

    private void startPackageActivity(String packageName) {
        Intent i = getPackageManager().getLaunchIntentForPackage(packageName);
        if (i == null) {
            Snackbar.make(mToolbar, R.string.cant_open_app, Snackbar.LENGTH_SHORT).show();
        } else {
            i.addCategory(Intent.CATEGORY_LAUNCHER);
            startActivity(i);
        }
    }


}
