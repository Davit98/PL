package com.davitmartirosyan.pl.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.view.View;

import com.davitmartirosyan.pl.R;
import com.davitmartirosyan.pl.ui.fragment.AboutFragment;
import com.davitmartirosyan.pl.ui.fragment.ProductListFragment;
import com.davitmartirosyan.pl.util.FragmentTransactionManager;

public class MainActivity extends  BaseActivity  implements View.OnClickListener,
        NavigationView.OnNavigationItemSelectedListener {

    // ===========================================================
    // Constants
    // ===========================================================

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    // ===========================================================
    // Fields
    // ===========================================================

    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;

    // ===========================================================
    // Constructors
    // ===========================================================

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    // ===========================================================
    // Methods for/from SuperClass
    // ===========================================================

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViews();
        setListeners();
        customizeActionBar();
        initDrawer();
        openScreen(ProductListFragment.newInstance(),R.id.nav_product_list,false);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else if(!mNavigationView.getMenu().findItem(R.id.nav_product_list).isChecked()) {
            onNavigationItemSelected(mNavigationView.getMenu().findItem(R.id.nav_product_list));
        } else {
            finish();
        }
    }

    // ===========================================================
    // Observer callback
    // ===========================================================

    // ===========================================================
    // Observer methods
    // ===========================================================

    // ===========================================================
    // Other Listeners, methods for/from Interfaces
    // ===========================================================

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_product_list:
                openScreen(ProductListFragment.newInstance(),R.id.nav_product_list,false);
                break;

            case R.id.nav_about:
                openScreen(AboutFragment.newInstance(),R.id.nav_about,true);
                break;
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    // ===========================================================
    // Click Listeners
    // ===========================================================

    @Override
    public void onClick(View v) {
    }

    // ===========================================================
    // Methods
    // ===========================================================

    private void setListeners() {
        mNavigationView.setNavigationItemSelectedListener(this);
    }

    private void findViews() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.dl_main);
        mNavigationView = (NavigationView) findViewById(R.id.nav_main);
    }

    private void customizeActionBar() {
        setActionBarTitle(getString(R.string.app_name));
    }

    private void initDrawer() {
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                getToolBar(),
                R.string.msg_navigation_drawer_open,
                R.string.msg_navigation_drawer_close
        );
        mDrawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    private void openScreen(Fragment fragment, int item, boolean mustAddToBackStack) {
        mNavigationView.getMenu().findItem(item).setChecked(true);

        FragmentTransactionManager.displayFragment(
                getSupportFragmentManager(),
                fragment,
                R.id.fl_main_container,
                mustAddToBackStack
        );
    }

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================
}
