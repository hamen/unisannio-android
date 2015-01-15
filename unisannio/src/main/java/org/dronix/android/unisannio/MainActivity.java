package org.dronix.android.unisannio;

import com.alterego.advancedandroidlogger.implementations.DetailedAndroidLogger;
import com.crashlytics.android.Crashlytics;

import org.dronix.android.unisannio.fragments.AteneoLandingFragment;
import org.dronix.android.unisannio.fragments.GiurisprudenzaLandingFragment;
import org.dronix.android.unisannio.fragments.ScienceLandingFragment;
import org.dronix.android.unisannio.fragments.SeaLandingFragment;
import org.dronix.android.unisannio.ingegneria.IngegneriaAvvisiFragment;
import org.dronix.android.unisannio.nagivation_drawer.NavigationDrawerFragment;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import dagger.ObjectGraph;
import eu.inmite.android.lib.dialogs.SimpleDialogFragment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Accessors(prefix = "m")
public class MainActivity extends ActionBarActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    @Inject
    DetailedAndroidLogger mLogger;

    @Getter
    private NavigationDrawerFragment mNavigationDrawerFragment;

    private CharSequence mTitle;

    private ObjectGraph activityGraph;

    private boolean mIsTablet;

    private UniApp mApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mApplication = (UniApp) getApplication();

        activityGraph = mApplication.getApplicationGraph().plus(getModules().toArray());
        activityGraph.inject(this);

        super.onCreate(savedInstanceState);

        Crashlytics.start(this);

        UniApp.setActivity(this);

        mIsTablet = getResources().getBoolean(R.bool.isTablet);
        if (mIsTablet) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    protected List<Object> getModules() {
        return Arrays.asList(new ActivityModule(this));
    }

    public void inject(Object object) {
        if (activityGraph == null) {
            activityGraph = mApplication.getApplicationGraph().plus(getModules().toArray());
        }
        activityGraph.inject(object);
    }

    @Override
    protected void onDestroy() {
        activityGraph = null;
        super.onDestroy();
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        switch (position) {
            case 0:
                mTitle = getString(R.string.title_section1);
                fragmentManager.beginTransaction()
                        .replace(R.id.container, AteneoLandingFragment.newInstance())
                        .commit();
                break;
            case 1:
                mTitle = getString(R.string.title_section2);
                fragmentManager.beginTransaction()
                        .replace(R.id.container, new IngegneriaAvvisiFragment())
                        .commit();
                break;
            case 2:
                mTitle = getString(R.string.title_section3);
                fragmentManager.beginTransaction()
                        .replace(R.id.container, GiurisprudenzaLandingFragment.newInstance())
                        .commit();
                break;
            case 3:
                mTitle = getString(R.string.title_section4);
                fragmentManager.beginTransaction()
                        .replace(R.id.container, SeaLandingFragment.newInstance())
                        .commit();
                break;
            case 4:
                mTitle = getString(R.string.title_section5);
                fragmentManager.beginTransaction()
                        .replace(R.id.container, ScienceLandingFragment.newInstance())
                        .commit();
                break;
            case 5:
                mTitle = getString(R.string.about);
                SimpleDialogFragment
                        .createBuilder(this, getSupportFragmentManager())
                        .setTitle(getString(R.string.about))
                        .setMessage(getString(R.string.author))
                        .show();
                break;
        }
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
            case 4:
                mTitle = getString(R.string.title_section4);
                break;
            case 5:
                mTitle = getString(R.string.title_section5);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            //getMenuInflater().inflate(R.menu.activity_main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (mNavigationDrawerFragment.isDrawerOpen()) {
            mNavigationDrawerFragment.close();
        } else if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
            resetActionBar(true, DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        } else {
            super.onBackPressed();
        }
    }

    public void resetActionBar(boolean childAction, int drawerMode) {
        if (childAction) {
            // [Undocumented?] trick to get up button icon to show
            mNavigationDrawerFragment.getDrawerToggle().setDrawerIndicatorEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } else {
            mNavigationDrawerFragment.getDrawerToggle().setDrawerIndicatorEnabled(true);
        }

        mNavigationDrawerFragment.getDrawerLayout().setDrawerLockMode(drawerMode);
    }
}
