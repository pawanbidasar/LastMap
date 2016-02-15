package jaipur.youstart.in.lastmap;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;

import android.widget.Toast;


import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Firebase.setAndroidContext(this);
        Firebase myFirebaseRef = new Firebase("https://lastmap.firebaseio.com/");

//        myFirebaseRef.child("Location1/Longitude").setValue("2.123");
//        myFirebaseRef.child("Location1/Latitude").setValue("3.221");
//        myFirebaseRef.child("Location1/PlaceName").setValue("Kardhani Shoping Center");
//        myFirebaseRef.child("Location1/Description").setValue("This is shopping center");
//        myFirebaseRef.child("Location1/ID").setValue(1);
//
//        myFirebaseRef.child("Location2/Longitude").setValue("1.123");
//        myFirebaseRef.child("Location2/Latitude").setValue("2.221");
//        myFirebaseRef.child("Location2/PlaceName").setValue("Hawa Mahal");
//        myFirebaseRef.child("Location2/Description").setValue("This is a Tourist Place.In this mahal there is no door. So do you wanna this mahal.");
//        myFirebaseRef.child("Location2/ID").setValue(1);
//
//        myFirebaseRef.child("Location3/Longitude").setValue("4.123");
//        myFirebaseRef.child("Location3/Latitude").setValue("5.221");
//        myFirebaseRef.child("Location3/PlaceName").setValue("Amber Mahal");
//        myFirebaseRef.child("Location3/Description").setValue("This is a Tourist Place.Shaan of Jaipur is Amber. So you have to visit this place, when will you come.");
//        myFirebaseRef.child("Location3/ID").setValue(1);


        myFirebaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                Toast.makeText(MainActivity.this,"Changed",Toast.LENGTH_SHORT).show();
//                Toast.makeText(MainActivity.this,"Valus is :"+dataSnapshot.getValue(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {}
        });
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        AlarmManager alarms = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getApplicationContext(), AlaramReceiver.class);
        intent.putExtra(AlaramReceiver.ACTION_ALARM, AlaramReceiver.ACTION_ALARM);
        final PendingIntent pIntent = PendingIntent.getBroadcast(this, 1234567, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarms.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 10000, pIntent);
        Toast.makeText(this, "Started...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        GoogleMap mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
        mMap.setBuildingsEnabled(true);
        mMap.getUiSettings().setAllGesturesEnabled(true);
        mMap.getUiSettings().setRotateGesturesEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        mMap.getUiSettings().setCompassEnabled(true);
    }

}
