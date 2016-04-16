package com.woop.tryreverseengineerthis;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.woop.tryreverseengineerthis.helper.StringHelper;

import com.woop.tryreverseengineerthis.items.ItemContent;
import com.woop.tryreverseengineerthis.listener.CurrentLocationListener;
import com.woop.tryreverseengineerthis.storage.LocationStorage;

import java.security.NoSuchAlgorithmException;

import javax.crypto.NoSuchPaddingException;

public class LandingActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        UniversityClassFragment.OnListFragmentInteractionListener {

    private static final String TAG = "LandingActvity";
    private CurrentLocationListener mLocationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        try {
            StringHelper.initialise(getApplicationContext());
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Hello", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        LocationManager locationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);

        boolean gps_enabled = false;
        boolean network_enabled = false;

        if(locationManager==null)
            locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        try{
            gps_enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        }catch(Exception ex){}
        try{
            network_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        }catch(Exception ex){}

        if(!gps_enabled && !network_enabled){
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setMessage("Please turn on Location Service for the full experience" );
            dialog.setPositiveButton("Setting", new DialogInterface.OnClickListener() {


                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub
                    startActivityForResult(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS), 100);
                }
            });
            dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub

                }
            });
            dialog.show();

        }
    }

    @Override
    public void onResume(){
        super.onResume();

        LocationManager locationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);
        mLocationListener = new CurrentLocationListener();
        try {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                    5000, 0, mLocationListener);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    5000, 0, mLocationListener);
            Log.d(TAG, "Hi");
        }catch(SecurityException e)
        {
            Log.d(TAG, "Permission not granted");
        }
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
        getMenuInflater().inflate(R.menu.landing, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = null;
        if (id == R.id.nav_classes) {
            fragment = new UniversityClassFragment();
        } else if (id == R.id.nav_assignments) {
            fragment = new Fragment();
        } else if (id == R.id.nav_checkin) {
            fragment = new Fragment();
        } else if (id == R.id.nav_lectures) {
            fragment = new Fragment();
        } else if (id == R.id.nav_share) {
            fragment = new Fragment();
        } else if (id == R.id.nav_send) {
            fragment = new Fragment();
        }

        fragmentManager.beginTransaction()
                .replace(R.id.main_fragment_container, fragment)
                .commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onListFragmentInteraction(ItemContent.ClassItem item) {
        Log.d(TAG, item.id);
        Location currentLocation = LocationStorage.getLocation();

        if(currentLocation == null)
            return;

        double longitude = currentLocation.getLongitude();
        double latitude = currentLocation.getLatitude();

        if(latitude > -35.0 || latitude < -37.0)
        {
            Log.d(TAG, "Latitude: " + latitude);
            return;
        }

        if(longitude < 174.0 || longitude > 175.0){
            Log.d(TAG, "Longitude: " + longitude);
            return;
        }

        Toast.makeText(getApplicationContext(), "Checked in", Toast.LENGTH_SHORT )
        .show();
    }
}
