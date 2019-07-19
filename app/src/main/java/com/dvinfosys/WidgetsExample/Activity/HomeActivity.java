package com.dvinfosys.WidgetsExample.Activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.dvinfosys.WidgetsExample.Fragments.ButtonFragment;
import com.dvinfosys.WidgetsExample.Fragments.CheckBoxFragment;
import com.dvinfosys.WidgetsExample.Fragments.EditTextFragment;
import com.dvinfosys.WidgetsExample.Fragments.ProgressViewFragment;
import com.dvinfosys.WidgetsExample.Fragments.RadioButtonFragment;
import com.dvinfosys.WidgetsExample.Fragments.SeekbarFragment;
import com.dvinfosys.WidgetsExample.Fragments.TextViewFragment;
import com.dvinfosys.WidgetsExample.Fragments.ToastViewFragment;
import com.dvinfosys.WidgetsExample.Fragments.VPVideoPlayerFragment;
import com.dvinfosys.WidgetsExample.R;
import com.dvinfosys.widgets.VideoPlayer.VPVideoPlayer;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        if (VPVideoPlayer.backPress()) {
            return;
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        Fragment fragment = null;

        if (id == R.id.nav_home) {

        } else if (id == R.id.nav_textview) {
            fragment = new TextViewFragment();
        } else if (id == R.id.nav_edittext) {
            fragment = new EditTextFragment();
        } else if (id == R.id.nav_checkbox) {
            fragment = new CheckBoxFragment();
        } else if (id == R.id.nav_button) {
            fragment = new ButtonFragment();
        } else if (id == R.id.nav_radiobutton) {
            fragment = new RadioButtonFragment();
        } else if (id == R.id.nav_progressview) {
            fragment = new ProgressViewFragment();
        } else if (id == R.id.nav_video_player) {
            fragment = new VPVideoPlayerFragment();
        } else if (id == R.id.nav_seekbar) {
            fragment = new SeekbarFragment();
        } else if (id == R.id.nav_toastview) {
            fragment = new ToastViewFragment();
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
