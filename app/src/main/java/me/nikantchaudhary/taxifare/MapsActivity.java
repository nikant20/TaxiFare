package me.nikantchaudhary.taxifare;

import android.*;
import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import static me.nikantchaudhary.taxifare.R.id.map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    GoogleMap mMap;
    LocationManager locationManager;
    TextView tvStartRide, tvEndRide;
    FloatingActionButton fabRate, fabLocation;
    double start_latitude, start_longitude,end_latitude, end_longitude;;
    long start_time, end_time;
    float baseRate, rate,distance,timeRate;
    private SQLiteDatabase db;
    private Cursor c;
    private static final String SELECT_SQL = "SELECT * FROM ratecalculator";
    DBHandler dbhandler;
    String br,r,tr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext());
         br= (settings.getString("BaseRate",""));
         r=(settings.getString("Rate",""));
        tr = (settings.getString("TimeRate",""));
       // Toast.makeText(getApplicationContext(),"baserate is: "+br+"length is: "+br.length(),Toast.LENGTH_LONG);



        //openDatabase();
        // dbhandler = new DBHandler(this);
       /** c = db.rawQuery(SELECT_SQL,null);
        c.moveToFirst();
        baseRate = Float.parseFloat(c.getString(0));
        rate = Float.parseFloat(c.getString(1));

        try{

            SQLiteOpenHelper sqLiteOpenHelper = new DBHandler(this);
            SQLiteDatabase db = sqLiteOpenHelper.getReadableDatabase();
            Cursor cursor = db.query("Select * from ratecalculator",null,null,null,null,null,null);
            if (cursor.moveToFirst()){
                baseRate = Float.parseFloat(cursor.getString(0));
                rate = Float.parseFloat(cursor.getString(1));
            }

        }catch(SQLiteException e){
            Toast.makeText(getApplicationContext(),"Database unavailable",Toast.LENGTH_SHORT);
        }
        **/
        //rate floating action button

        fabRate = (FloatingActionButton) findViewById(R.id.fabRate);
        fabRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),SetFareRate.class);
                startActivity(i);

            }
        });


        //Getting BaseRate and Rate from SetFareActivity
       /** Bundle bundle = this.getIntent().getExtras();
        if(bundle!=null) {
            BaseRate = bundle.getString("baseRate");
            Rate =bundle.getString("rate");

            baseRate=Float.parseFloat(BaseRate);
            rate=Float.parseFloat(Rate);
        }
        else
            Toast.makeText(getApplicationContext(),"Nothing Recieved",Toast.LENGTH_SHORT).show(); **/

        //start ride Methods


        tvStartRide = (TextView) findViewById(R.id.tvStartRide);
        tvStartRide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocationManager locationManager = (LocationManager)
                        getSystemService(LOCATION_SERVICE);
                Criteria criteria = new Criteria();
                String bestProvider = locationManager.getBestProvider(criteria, true);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    Activity#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for Activity#requestPermissions for more details.
                        return;
                    }
                }


                Location location = locationManager.getLastKnownLocation(bestProvider);
                start_latitude = location.getLatitude();
                start_longitude = location.getLongitude();
                start_time = System.currentTimeMillis();

                LatLng latLng = new LatLng(start_latitude, start_longitude);
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET));
                markerOptions.position(latLng);
                mMap.addMarker(new MarkerOptions().position(latLng).title("Start Point").draggable(true));
                tvStartRide.setVisibility(View.GONE);
                tvEndRide.setVisibility(View.VISIBLE);


            }
        });


        //End Ride Methods

        tvEndRide = (TextView) findViewById(R.id.tvEndRide);
        tvEndRide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocationManager locationManager = (LocationManager)
                        getSystemService(LOCATION_SERVICE);
                Criteria criteria = new Criteria();
                String bestProvider = locationManager.getBestProvider(criteria, true);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    Activity#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for Activity#requestPermissions for more details.
                        return;
                    }
                }
                Location location = locationManager.getLastKnownLocation(bestProvider);
                end_latitude = location.getLatitude();
                end_longitude = location.getLongitude();

                LatLng latLng = new LatLng(end_latitude, end_longitude);
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
                markerOptions.position(latLng);
                mMap.addMarker(markerOptions);
                end_time = System.currentTimeMillis();
                float travel_time = (end_time - start_time) / 60000;

                Location startLocation = new Location("Start Location");
                startLocation.setLatitude(start_latitude);
                startLocation.setLongitude(start_longitude);

                Location endLocation = new Location("End Location");
                endLocation.setLatitude(end_latitude);
                endLocation.setLongitude(end_longitude);

                baseRate= Float.parseFloat(br);
                rate = Float.parseFloat(r);
                timeRate = Float.parseFloat(tr);

                distance = startLocation.distanceTo(endLocation);
                float fare = (baseRate+((distance/1000)*rate)+(travel_time*timeRate));


                tvEndRide.setVisibility(View.GONE);

                AlertDialog.Builder alertdialogue = new AlertDialog.Builder(MapsActivity.this);
                alertdialogue.setTitle("Fare");
                alertdialogue.setIcon(R.mipmap.ic_launcher);
                alertdialogue.setMessage("Distance travelled is: "+distance + " metres \n Time travelled is: "+travel_time+" minutes \n fare is: "+fare+" INR");
                alertdialogue.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                @Override
                 public void onClick(DialogInterface dialogInterface, int i) {
                 }
                });alertdialogue.show();
                mMap.clear();
                tvStartRide.setVisibility(View.VISIBLE);
            }
        });

        /**
        //Rate Methods

        fabRate = (FloatingActionButton) findViewById(R.id.fabRate);
        fabRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), SetFareRate.class);
                startActivityForResult(i,2);
            }
        });
        **/

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(map);
        mapFragment.getMapAsync(this);

        //Get current Location Methods

        fabLocation = (FloatingActionButton) findViewById(R.id.fabLocation);
        fabLocation.setImageResource(R.drawable.ic_gps_fixed_black_24dp);
        fabLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
                Criteria criteria = new Criteria();
                String bestProvider = locationManager.getBestProvider(criteria, true);
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                mMap.setMyLocationEnabled(true);
                mMap.animateCamera(CameraUpdateFactory.zoomTo(16));
            }
        });


    }

 /*  private void openDatabase() {

        db = openOrCreateDatabase("RateDetails", Context.MODE_PRIVATE,null);

    }
*/

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String bestProvider = locationManager.getBestProvider(criteria, true);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        mMap.animateCamera(CameraUpdateFactory.zoomTo(16));

        Location location = locationManager.getLastKnownLocation(bestProvider);
        if (location != null) {
            onLocationChanged(location);
        }
        locationManager.requestLocationUpdates(bestProvider, 20000, 0, (LocationListener) this);

    }

    @Override
    public void onLocationChanged(Location location) {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        LatLng latLng = new LatLng(latitude, longitude);


        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
       // mMap.setBuildingsEnabled(true);
       // mMap.setIndoorEnabled(true);
       // mMap.setTrafficEnabled(true);
        mMap.getUiSettings().isMyLocationButtonEnabled();
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(16));
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    /**

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==2){
           String BaseRate = data.getStringExtra("baseRate");
           String  Rate = data.getStringExtra("rate");
           Toast.makeText(getApplicationContext(),"Base rate is: "+BaseRate,Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(),BaseRate.length(),Toast.LENGTH_SHORT).show();

            Toast.makeText(getApplicationContext(),"rate is: "+Rate,Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(),Rate.length(),Toast.LENGTH_SHORT).show();


            if(BaseRate != null && !BaseRate.isEmpty()){
                baseRate = Float.valueOf(BaseRate);
            }
            if(Rate != null && !BaseRate.isEmpty()){
                rate = Float.valueOf(Rate);
            }

        }
    }

    **/

}
