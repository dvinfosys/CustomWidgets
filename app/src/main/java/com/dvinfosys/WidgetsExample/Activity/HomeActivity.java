package com.dvinfosys.WidgetsExample.Activity;

import android.content.Intent;
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

import com.dvinfosys.WidgetsExample.Fragments.AutoSelectFragment;
import com.dvinfosys.WidgetsExample.Fragments.ButtonFragment;
import com.dvinfosys.WidgetsExample.Fragments.CheckBoxFragment;
import com.dvinfosys.WidgetsExample.Fragments.ColorPickerFragment;
import com.dvinfosys.WidgetsExample.Fragments.CountdownViewFragment;
import com.dvinfosys.WidgetsExample.Fragments.DAlertFragment;
import com.dvinfosys.WidgetsExample.Fragments.DToastFragment;
import com.dvinfosys.WidgetsExample.Fragments.EditTextFragment;
import com.dvinfosys.WidgetsExample.Fragments.ExpandingCollectionFragment;
import com.dvinfosys.WidgetsExample.Fragments.FoldingCellFragment;
import com.dvinfosys.WidgetsExample.Fragments.ImageviewFragment;
import com.dvinfosys.WidgetsExample.Fragments.InputCodeFragment;
import com.dvinfosys.WidgetsExample.Fragments.MSearchbarFragment;
import com.dvinfosys.WidgetsExample.Fragments.NumberCounterFragment;
import com.dvinfosys.WidgetsExample.Fragments.PaperOnboardingFragment;
import com.dvinfosys.WidgetsExample.Fragments.ProgressViewFragment;
import com.dvinfosys.WidgetsExample.Fragments.RadioButtonFragment;
import com.dvinfosys.WidgetsExample.Fragments.RangeSeekbarFragment;
import com.dvinfosys.WidgetsExample.Fragments.RecyclerResizeFragment;
import com.dvinfosys.WidgetsExample.Fragments.SearchableSpinnerFragment;
import com.dvinfosys.WidgetsExample.Fragments.SeekbarFragment;
import com.dvinfosys.WidgetsExample.Fragments.SpinWheelFragment;
import com.dvinfosys.WidgetsExample.Fragments.SpotlightFragment;
import com.dvinfosys.WidgetsExample.Fragments.StickerViewFragment;
import com.dvinfosys.WidgetsExample.Fragments.SwitchFragment;
import com.dvinfosys.WidgetsExample.Fragments.TextViewFragment;
import com.dvinfosys.WidgetsExample.Fragments.ToastViewFragment;
import com.dvinfosys.WidgetsExample.Fragments.ToggleButtonFragment;
import com.dvinfosys.WidgetsExample.MainActivity;
import com.dvinfosys.WidgetsExample.R;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

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
            startActivity(new Intent(HomeActivity.this, MainActivity.class));
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
        } else if (id == R.id.nav_seekbar) {
            fragment = new SeekbarFragment();
        } else if (id == R.id.nav_toastview) {
            fragment = new ToastViewFragment();
        } else if (id == R.id.nav_imageview) {
            fragment = new ImageviewFragment();
        } else if (id == R.id.nav_countdownview) {
            fragment = new CountdownViewFragment();
        } else if (id == R.id.nav_number_counter) {
            fragment = new NumberCounterFragment();
        } else if (id == R.id.nav_color_picker) {
            fragment = new ColorPickerFragment();
        } else if (id == R.id.nav_switch) {
            fragment = new SwitchFragment();
        } else if (id == R.id.nav_togglebutton) {
            fragment = new ToggleButtonFragment();
        } else if (id == R.id.nav_folding_cell) {
            fragment = new FoldingCellFragment();
        } else if (id == R.id.nav_searchable_spinner) {
            fragment = new SearchableSpinnerFragment();
        } else if (id == R.id.nav_spotlight) {
            fragment = new SpotlightFragment();
        } else if (id == R.id.nav_paper_onboarding) {
            fragment = new PaperOnboardingFragment();
        } else if (id == R.id.nav_expanding_collection) {
            fragment = new ExpandingCollectionFragment();
        } else if (id == R.id.nav_auto_select) {
            fragment = new AutoSelectFragment();
        } else if (id == R.id.nav_alert_dialog) {
            fragment = new DAlertFragment();
        } else if (id == R.id.nav_spin_wheel) {
            fragment = new SpinWheelFragment();
        } else if (id == R.id.nav_resize_recycler) {
            fragment = new RecyclerResizeFragment();
        } else if (id == R.id.nav_input_code) {
            fragment = new InputCodeFragment();
        } else if (id == R.id.nav_sticker) {
            fragment = new StickerViewFragment();
        }else if (id==R.id.nav_msearchbar){
            fragment=new MSearchbarFragment();
        }else if (id==R.id.nav_d_toast){
            fragment=new DToastFragment();
        }else if (id==R.id.nav_range_seekbar){
            fragment=new RangeSeekbarFragment();
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
