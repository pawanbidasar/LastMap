package jaipur.youstart.in.lastmap;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements LocationListener,OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleMap mMap;
    LocationRequest locationRequest;
    GoogleApiClient googleApiClient;
    Location newLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        locationRequest = LocationRequest.create();

//Set the update interval
        locationRequest.setInterval(3000);

// Use high accuracy
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);


        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
    }

    public void onStart() {
        super.onStart();
        googleApiClient.connect();
        Toast.makeText(this, "Connect", Toast.LENGTH_SHORT).show();
    }

    public void onStop() {
        super.onStop();
        googleApiClient.disconnect();
        Toast.makeText(this, "Disconnect", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mMap = googleMap;
//        mMap.setMyLocationEnabled(true);
        mMap.setBuildingsEnabled(true);
        mMap.getUiSettings().setAllGesturesEnabled(true);
        mMap.getUiSettings().setRotateGesturesEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        mMap.getUiSettings().setCompassEnabled(true);

    }


    @Override
    public void onConnected(Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        Location locations = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
//        Toast.makeText(this,"Connected",Toast.LENGTH_SHORT).show();
        String msg = "Current Location: " +  Double.toString(locations.getLatitude()) + "," +
                Double.toString(locations.getLongitude());
         Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();




        MylocationlistenerInterface mylocationlistenerInterface=new MylocationlistenerInterface();
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, mylocationlistenerInterface);

        float distance[]=new float[1];
        locations.distanceBetween(locations.getLatitude(), locations.getLongitude(), 26.854701, 75.813538, distance);

        if(distance[0] < 100.0)
        {
//            NotificationManager nm=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
//            Notification notifications=new Notification(R.drawable.overlay5,"Hey buddy,You are near Kardhani Shoping Center,Malviya Nagar,Jaipur",System.currentTimeMillis());
//            CharSequence title="Near Kardhani Shoping Center";
//            CharSequence details="Attention! Pause your work.Visit Kardhani Shoping Center.";
//            Intent intent=new Intent(TaskService.this,MyService.class);
//            PendingIntent pendingIntent=PendingIntent.getActivity(TaskService.this,1,intent,0);
//            CharSequence msg="Hello buddy you are near kardhani shoping center";
//            NotificationCompat.Builder builder = new NotificationCompat.Builder(TaskService.this);
//            builder.setAutoCancel(true);
//            builder.setTicker(title);
//            builder.setContentTitle(title);
//            builder.setContentText(details);
//            builder.setSmallIcon(R.drawable.overlay5);
//            builder.setStyle(new NotificationCompat.BigTextStyle().bigText(details));
//            builder.setStyle(new NotificationCompat.BigTextStyle().bigText(msg));
//            builder.setContentIntent(pendingIntent);
//            builder.setOngoing(false);
//            builder.setDefaults(Notification.DEFAULT_SOUND);
//            builder.setSubText(msg);
//            builder.setNumber(1);
//            builder.build();
//            notifications = builder.getNotification();
//            nm.notify(999,notifications);
        }
    }

    @Override
    public void onConnectionSuspended(int i)
    {
        Toast.makeText(this,"Disconnected",Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onConnectionFailed(ConnectionResult connectionResult)
    {
    Toast.makeText(this, "Connection Failed", Toast.LENGTH_SHORT).show();
    }

    LocationListener listener =  new LocationListener() {

        @Override
        public void onLocationChanged(Location location) {
            // Report to the UI that the location was updated
            String msg = "Updated Location: " +
                    Double.toString(location.getLatitude()) + "," +
                    Double.toString(location.getLongitude());
            Toast.makeText(MapsActivity.this, msg, Toast.LENGTH_SHORT).show();

        }
    };



    public void displayCurrentLocation() {
        // Get the current location's latitude & longitude
//        Location currentLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
//        String msg = "Current Location: " +  Double.toString(currentLocation.getLatitude()) + "," +
//                Double.toString(currentLocation.getLongitude());
//        // Display the current location in the UI
//         Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLocationChanged(Location location)
    {
     newLocation=location;
        Toast.makeText(this,"location changed"+location,Toast.LENGTH_SHORT).show();
    }
}
