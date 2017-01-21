package com.saladevs.changelogclone.ui;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.saladevs.changelogclone.R;
import com.saladevs.changelogclone.ui.details.DetailsActivity;
import com.saladevs.changelogclone.utils.PackageUtils;


public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_Base_TranslucentSystemBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setUpToolbar(mToolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mNavigationView = (NavigationView) findViewById(R.id.navigation);
        setUpNavigationView(mNavigationView);

    }

    private void setUpToolbar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    
    private void setUpNavigationView(NavigationView navigationView) {
        mNavigationView.setItemIconTintList(null);
        Menu mNavigationMenu = mNavigationView.getMenu();
        PackageUtils.getPackageList()
                .subscribe(pi -> addNavigationItem(mNavigationMenu, pi));
    }

    private void addNavigationItem(Menu menu, PackageInfo pi) {
        menu.add(PackageUtils.getAppLabel(pi))
                .setIcon(PackageUtils.getAppIconDrawable(pi)) // TODO -  onCreate Bottleneck
                .setOnMenuItemClickListener(menuItem -> {
                    Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                    intent.putExtra(DetailsActivity.PARAM_PACKAGE, pi);
                    startActivity(intent);
                    return true;
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(mNavigationView);
                return true;
            case R.id.action_about:
                return true;
        }

        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(mNavigationView)) {
            mDrawerLayout.closeDrawers();
        } else {
            super.onBackPressed();
        }
    }
}
