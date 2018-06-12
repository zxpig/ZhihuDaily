package com.xtlog.android.zhihudaily.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.xtlog.android.zhihudaily.R;
import com.xtlog.android.zhihudaily.adapter.MyPagerAdapter;
import com.xtlog.android.zhihudaily.service.PushService;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    public static MainActivity sMainActivity;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sMainActivity = this;

//        推送设置
        SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
        //默认关闭推送
        boolean isOpen = pref.getBoolean("push", false);
        if(isOpen) {
            Intent startIntent = new Intent(this, PushService.class);
            startService(startIntent);
        }

        mViewPager = (ViewPager)findViewById(R.id.main_view_pager);
        mTabLayout = (TabLayout)findViewById(R.id.tabs);

        MyPagerAdapter pagerAdapter = new MyPagerAdapter(getSupportFragmentManager());

        mViewPager.setAdapter(pagerAdapter);


        mTabLayout.setupWithViewPager(mViewPager);






        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            SharedPreferences.Editor editor = getSharedPreferences("data", Context.MODE_PRIVATE).edit();
            editor.putString("json", "");
            editor.apply();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        SharedPreferences.Editor editor = getSharedPreferences("data", Context.MODE_PRIVATE).edit();
        SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_star) {
            // Handle the camera action
        } else if (id == R.id.nav_good) {

        } else if (id == R.id.nav_night_mode) {


        } else if (id == R.id.nav_share) {

        } else if(id == R.id.nav_push_switch){
            if(!pref.getBoolean("push", false)){//默认关闭
                Toast.makeText(this, "新文章推送开启", Toast.LENGTH_SHORT).show();
                editor.putBoolean("push", true);
                editor.apply();

                //Start Push Service
                Intent startIntent = new Intent(this, PushService.class);
                startService(startIntent);
            }
            else{
                Toast.makeText(this, "新文章推送关闭", Toast.LENGTH_SHORT).show();
                editor.putBoolean("push", false);
                editor.apply();

                //Stop Push Service
                Intent stopIntent = new Intent(this, PushService.class);
                stopService(stopIntent);

            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }


}
