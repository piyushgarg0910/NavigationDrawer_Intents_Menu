package com.example.android.leafpic;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    ActionBar actionBar;
    SearchView searchView;
    SearchManager searchManager;
    WebView webView;
    TextView textView;
    static int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);

        navigationView.setNavigationItemSelectedListener(this);

        textView = (TextView) findViewById(R.id.text_main);
        //textView.setText("https://www.google.com");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.action_settings:
                Toast.makeText(MainActivity.this,"Menu Settings",Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_search:
                searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
                //MenuItem searchViewItem = menu.findItem(R.id.action_search);
                searchView = (SearchView) item.getActionView();
                searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
                searchView.setIconifiedByDefault(false);
                searchView.setFocusable(true);
                searchView.requestFocusFromTouch();
                Toast.makeText(MainActivity.this,"Search", Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void navMenuItemSelected(int id)
    {
        Fragment fragment = null;
        switch (id) {
            case R.id.Home:
                Toast.makeText(MainActivity.this,"Home",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.hidden_folder:
                Toast.makeText(MainActivity.this,"hidden folder",Toast.LENGTH_SHORT).show();
                break;
            case R.id.local_folder:
                break;
            case R.id.about:
                break;
            case R.id.settings:
                fragment = new SettingsFragment();
                Toast.makeText(getApplicationContext(),"Nav Settings",Toast.LENGTH_SHORT).show();
                break;
            case R.id.donate:
                break;
            case R.id.wallpapers:
                break;
        }
        if (fragment != null)
        {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content_main,fragment);
            fragmentTransaction.commit();
        }
        drawerLayout.closeDrawers();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        int id = menuItem.getItemId();
        navMenuItemSelected(id);
       // menuItem.setChecked(true);
        return true;
    }

   @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (count == 0) {
            textView.setText("https://www.google.com");
        }
        else {
            textView.setText(getIntent().getStringExtra("link"));
        }
        count++;
        webView = (WebView) findViewById(R.id.wView);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(textView.getText().toString());
        webView.getSettings().setJavaScriptEnabled(true);
    }
}
