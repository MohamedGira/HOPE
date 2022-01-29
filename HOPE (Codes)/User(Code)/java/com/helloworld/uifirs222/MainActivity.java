package com.helloworld.uifirs222;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.helloworld.uifirs222.Adapers.SectionsPagerAdapter;
import com.helloworld.uifirs222.ui.main.Hospitalfrag;
import com.helloworld.uifirs222.ui.main.toshowinalarm;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {


    public static final String TAG = Hospitalfrag.class.getSimpleName();
    private static int PLAY_SERVICE_RESOLUTION_REQUEST = 1000;
    private Location mLastLocation;
    private GoogleApiClient mGoogleApiCleint;
    DatabaseReference dref;


    private LocationRequest mLocationReq;

    public static final String PREFS_NAME = "30";


    public final static int REQUEST_CODE = 10101;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        dref = FirebaseDatabase.getInstance().getReference();


        DisplayMetrics metrics = getResources().getDisplayMetrics();

        final int width = metrics.widthPixels;
        final int height = metrics.heightPixels;


        //Initializing shared prefrence
        final SharedPreferences storage = this.getSharedPreferences(MainActivity.PREFS_NAME, 0); // 0 - for private mode
        final SharedPreferences.Editor editor = storage.edit();


        Button but = findViewById(R.id.fab);
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialograte = new Dialog(MainActivity.this);
                dialograte.setContentView(R.layout.dialog_rate);
                dialograte.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                final EditText feedback = dialograte.findViewById(R.id.feedback);
                dialograte.setCanceledOnTouchOutside(false);
                dialograte.show();

                final Spinner spinner = (Spinner) dialograte.findViewById(R.id.ratingspinner);
// Create an ArrayAdapter using the string array and a default spinner layout
                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(),
                        R.array.Rate, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
// Apply the adapter to the spinner
                spinner.setAdapter(adapter);


                TextView Rate, Remindlater;
                Rate = dialograte.findViewById(R.id.rate);
                Remindlater = dialograte.findViewById(R.id.remindlater2);
                Rate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRefuserate = database.getReference("Users/" + storage.getString("User number", "00") + "/Rate");
                        myRefuserate.setValue(spinner.getSelectedItem().toString());
                        DatabaseReference myRefusEdit = database.getReference("Users/" + storage.getString("User number", "00") + "/Opinion");
                        myRefusEdit.setValue(feedback.getText().toString());

                    }
                });
                Remindlater.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editor.remove("launch_times");
                        editor.remove("date_firstlaunch");
                    }
                });

            }
        });


//How many times the app were opend
        long launch_count = storage.getLong("launch_times", 0) + 1;
        editor.putLong("launch_times", launch_count);
        editor.commit();


//Get first date when app
        long date_firstLaunch = storage.getLong("date_firstlaunch", 0);
        if (date_firstLaunch == 0) {
            date_firstLaunch = System.currentTimeMillis();
            editor.putLong("date_firstlaunch", date_firstLaunch);
        }

        if (launch_count >= 3) {
//Compare if days are more than 3
            if (System.currentTimeMillis() >= date_firstLaunch + (3 * 24 * 60 * 60 * 1000)) {

            }
        }


//Refreshing user state( in order to stop the appointments notifications on opening the app)
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRefuser = database.getReference("Users/" + storage.getString("User number", "00") + "/Notified to Attend");
        myRefuser.setValue("False");
        DatabaseReference myRefus = database.getReference("Users/" + storage.getString("User number", "00") + "/Notified to Cancel");
        myRefus.setValue("False");
        boolean boolog = storage.getBoolean("signed up", true);

        if (boolog) {
            // checkDrawOverlayPermission();
            /* never make toasts here "overlay detection" will ruin everything*/

            //this boolean checks if the user has google play services
            CheckPlayService();


            //Check user permessions if he signed up
            if (ContextCompat.checkSelfPermission(MainActivity.this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, String.valueOf(ContextCompat.checkSelfPermission(MainActivity.this,
                        android.Manifest.permission.ACCESS_FINE_LOCATION)), Toast.LENGTH_SHORT).show();
                requestPermissions(new String[]{
                        android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET
                }, 10);
            }


            //Builds the Api Client & the request for location in case the user has google play services
            if (CheckPlayService()) {
                buildGoogleApiClient();
                createLocationRequest();
            }


//Checks if the client (whom was built from the previous step) exists. if so, it connects him to google api
            if (mGoogleApiCleint != null) ;
            {
                mGoogleApiCleint.connect();
            }


        }


    }


    @Override
    public void onResume() {
        super.onResume();
        final SharedPreferences storage = this.getSharedPreferences(MainActivity.PREFS_NAME, 0); // 0 - for private mode
        final SharedPreferences.Editor editor = storage.edit();

        boolean boolog = storage.getBoolean("signed up", false);
        if (boolog) {
            mGoogleApiCleint.connect();
            CheckPlayService();

        }
    }

    @Override
    public void onStop() {
        super.onStop();
        final SharedPreferences storage = this.getSharedPreferences(MainActivity.PREFS_NAME, 0); // 0 - for private mode
        final SharedPreferences.Editor editor = storage.edit();

        boolean boolog = storage.getBoolean("signed up", false);
        if (boolog) {
            if (mGoogleApiCleint.isConnected()) {
                mGoogleApiCleint.disconnect();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == 10) {
            if (ContextCompat.checkSelfPermission(
                    this, Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                mGoogleApiCleint.connect();
                CheckPlayService();
            } else {
                Toast.makeText(MainActivity.this,
                        "for better experience, please accept the permission",
                        Toast.LENGTH_LONG);
            }
        }
    }


    public boolean checkDrawOverlayPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (!Settings.canDrawOverlays(this)) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, REQUEST_CODE);
            return false;
        } else {

            return true;
        }
    }


    private boolean CheckPlayService() {
        int resutlcode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resutlcode != ConnectionResult.SUCCESS) {
            Toast.makeText(MainActivity.this, "success", Toast.LENGTH_SHORT).show();
            if (GooglePlayServicesUtil.isUserRecoverableError(resutlcode)) {
                GooglePlayServicesUtil.getErrorDialog(resutlcode, this, PLAY_SERVICE_RESOLUTION_REQUEST).show();
            } else {
                Toast.makeText(this, "This device is not supported", Toast.LENGTH_LONG).show();

            }
            return false;
        }
        return true;
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiCleint = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)

                .addApi(LocationServices.API).build();

    }

    protected void createLocationRequest() {
        mLocationReq = new LocationRequest();
        mLocationReq.setInterval(5000);
        mLocationReq.setFastestInterval(1000);
        mLocationReq.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationReq.setSmallestDisplacement(1);
        mLocationReq.setNumUpdates(1);

    }

    //Start updating the location of the Built Client using the Created Request
    protected void StartLocationUpdate() {
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiCleint, mLocationReq, this);
        }
    }


    @Override
    public void onConnected(Bundle bundle) {
        StartLocationUpdate();
    }


    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(this, "Suspended", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

        Toast.makeText(this, "failed to connect", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        final SharedPreferences storage = this.getSharedPreferences(MainActivity.PREFS_NAME, 0); // 0 - for private mode
        final SharedPreferences.Editor editor = storage.edit();
        String stlat = String.valueOf(mLastLocation.getLatitude());
        String stlong = String.valueOf(mLastLocation.getLongitude());
        editor.putString("LastLat", stlat);
        editor.putString("LastLong", stlong);

        editor.apply();
        editor.commit();


    }


}
