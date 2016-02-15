package jaipur.youstart.in.lastmap;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.nio.Buffer;
import java.util.Timer;


/**
 * Created by youstart on 2/10/2016.
 */
public class NewService extends Service implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    LocationManager loc;
    LocationRequest locationRequest;
    GoogleApiClient googleApiClient;
    static  int ch=0;
    Location loca;

    Firebase myFirebaseReference;
    private static final long ONE_MIN = 500;
    private static final long TWO_MIN = 50;
    private static final long FIVE_MIN = 50;
    private static final long POLLING_FREQ = 1000 * 20;
    private static final long FASTEST_UPDATE_FREQ = 1000 * 5;
    private static final float MIN_ACCURACY = 1.0f;
    private static final float MIN_LAST_READ_ACCURACY = 1;

    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
        myFirebaseReference = new Firebase("https://lastmap.firebaseio.com/");
        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        googleApiClient.connect();
        locationRequest = LocationRequest.create();
//Set the update interval
        locationRequest.setInterval(6000);
// Use high accuracy
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setPowerRequirement(Criteria.NO_REQUIREMENT);
//        String bestProvider = lm.getBestProvider(criteria, true);

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.checkSelfPermission(NewService.this, Manifest.permission.ACCESS_FINE_LOCATION);
            return;
        }


//        loca = bestLastKnownLocation(MIN_LAST_READ_ACCURACY, FIVE_MIN);
//
//        if (null== loca || loca.getAccuracy()> MIN_LAST_READ_ACCURACY || loca.getTime()<System.currentTimeMillis()-TWO_MIN)
//        {
//        LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, new CustomLocationListener());
//
//        }
//


//        Location locations = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);

//        Toast.makeText(this,"Connected",Toast.LENGTH_SHORT).show();
//        String msg="Current Location: "+locations.getLatitude()+","+ locations.getLongitude();
//        Toast.makeText(this,""+locations.getLatitude(),Toast.LENGTH_SHORT).show();
//        float distance[]=new float[1];
//        locations.distanceBetween(locations.getLatitude(),locations.getLongitude(), 26.854701, 75.813538, distance);
//        Toast.makeText(NewService.this,"Distance is : "+distance,Toast.LENGTH_SHORT).show();

//        MylocationlistenerInterface mylocationlistenerInterface = new MylocationlistenerInterface();
//        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, new CustomLocationListener());

    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
    }

    private class CustomLocationListener implements LocationListener {

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onProviderDisabled(String provider) {
        }

        public void onLocationChanged(Location location) {


            writeLocationData(location);
        }
    }

    public int onStartCommand(Intent intent, int id, int lp) {

//        Toast.makeText(this, "Location Update service has been started", Toast.LENGTH_SHORT).show();
        loc = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return 0;
        }
//       loc.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1*15*1000, 10, new CustomLocationListener(), Looper.myLooper());
        loc.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1 * 15 * 1000, 10, new CustomLocationListener(), Looper.myLooper());



        return START_STICKY;
    }


    private void writeLocationData(Location location) {
        final double latitude = location.getLatitude();
        final double lon = location.getLongitude();


            double lat1,lat2,lat3,long1,long2,long3;

        myFirebaseReference.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {

                double ll1,ll2,ll3,lon1,lon2,lon3;
                dataSnapshot.child("Location1/ID").getValue();
                Log.e("val",""+dataSnapshot.child("Location1/ID").getValue());
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    ll1=(double)dataSnapshot.child("Location1/Latitude").getValue();
                    ll2=(double)dataSnapshot.child("Location2/Latitude").getValue();
                    ll3=(double)dataSnapshot.child("Location3/Latitude").getValue();

                   Log.e("Latitude 1 ",ll1+" : Latitude 2 :"+ll2+" Latitude 3 : "+ll3);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

//        Toast.makeText(NewService.this, "latitude is : " + latitude + "  longitude is : " + lon, Toast.LENGTH_LONG).show();
        float duri[] = new float[1];
        float duri1[]=new float[1];
//        location.distanceBetween(latitude, lon, 26.854701, 75.813538, duri);
//        location.distanceBetween(latitude, lon, 26.854459, 75.812660, duri1);
        Location location1 = new Location("locationA");
        location1.setLatitude(location.getLatitude());
        location1.setLongitude(location.getLongitude());
        Location location2 = new Location("locationB");
        location2.setLatitude(26.854649);
        location2.setLongitude(75.813058);
        Location location3=new Location("locationC");
        location3.setLatitude(26.8546442);
        location3.setLongitude(75.813156);
        double distance2=location1.distanceTo(location3);
        double distance = location1.distanceTo(location2);

        if(distance  < distance2)
        {

            Log.e("11distance",""+distance);
            Log.e("11distance2",""+distance2);
            if (distance < 50 )
            {
//                myFirebaseReference.child(""+ch++).setValue("Latitude"+latitude+"Longitude"+lon+" Nearest Place "+distance);
                Context context=getApplicationContext();
//            Toast.makeText(MylocationlistenerInterface.this, "j", Toast.LENGTH_LONG).show();
                NotificationManager nm = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
                Notification notifications = new Notification(R.drawable.overlay5, "Hey buddy,You are near Kardhani Shoping Center,Malviya Nagar,Jaipur", System.currentTimeMillis());
                CharSequence title = "Near Kardhani Shoping Center";
                CharSequence details = "Attention! Pause your work.Visit Kardhani Shoping Center.";
                Intent intentn = new Intent(context, NotificationOpen.class);
                intentn.putExtra("ID","MN01");
                intentn.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                PendingIntent pendingIntent = PendingIntent.getActivity(context, 1, intentn, PendingIntent.FLAG_UPDATE_CURRENT);
                CharSequence msg = "Hello buddy you are near kardhani shoping center";
                NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
                builder.setAutoCancel(true);
                builder.setTicker(title);
                builder.setContentTitle(title);
                builder.setContentText(details);
                builder.setSmallIcon(R.drawable.overlay5);
                builder.setStyle(new NotificationCompat.BigTextStyle().bigText(details));
                builder.setStyle(new NotificationCompat.BigTextStyle().bigText(msg));
                builder.setContentIntent(pendingIntent);
                builder.setOngoing(false);
                builder.setDefaults(Notification.DEFAULT_SOUND);
                builder.setSubText(msg);
                builder.setNumber(1);
                builder.build();
                notifications = builder.getNotification();
//                nm.notify(999, notifications);
            }

        }
        else
        {

            if (distance2 < 50 )
            {
//                myFirebaseReference.child(""+ch++).setValue("Latitude"+latitude+"Longitude"+lon+" Nearest Place "+distance2);
                    Log.e("distance",""+distance2);
                Context context=getApplicationContext();
//            Toast.makeText(MylocationlistenerInterface.this, "j", Toast.LENGTH_LONG).show();
                NotificationManager nm = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
                Notification notifications = new Notification(R.drawable.overlay5, "Hey buddy,You are near Kardhani Shoping Center,Malviya Nagar,Jaipur", System.currentTimeMillis());
                CharSequence title = "Near Kardhani Shoping Center";
                CharSequence details = "Attention! Pause your work.Visit Kardhani Shoping Center.";
                Intent intentn = new Intent(context, NotificationOpen.class);
                intentn.putExtra("ID","MN02");
                intentn.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                PendingIntent pendingIntent = PendingIntent.getActivity(context, 1, intentn, PendingIntent.FLAG_UPDATE_CURRENT);
                CharSequence msg = "Hello buddy you are near kardhani shoping center";
                NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
                builder.setAutoCancel(true);
                builder.setTicker(title);
                builder.setContentTitle(title);
                builder.setContentText(details);
                builder.setSmallIcon(R.drawable.overlay5);
                builder.setStyle(new NotificationCompat.BigTextStyle().bigText(details));
                builder.setStyle(new NotificationCompat.BigTextStyle().bigText(msg));
                builder.setContentIntent(pendingIntent);
                builder.setOngoing(false);
                builder.setDefaults(Notification.DEFAULT_SOUND);
                builder.setSubText(msg);
                builder.setNumber(1);
                builder.build();
                notifications = builder.getNotification();
//                nm.notify(999, notifications);
            }
        }
//        Toast.makeText(NewService.this, "Duriyaaaa  1 : " + distance +" Dis 2 : "+distance2, Toast.LENGTH_SHORT).show();


    }


    private Location bestLastKnownLocation(float minAccuracy, long minTime) {
        Location bestResult = null;
        float bestAccuracy = Float.MAX_VALUE;
        long bestTime = Long.MIN_VALUE;

        // Get the best most recent location currently available
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return null;
        }
        Location mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        //tv.setText(mCurrentLocation+"");
        if (mCurrentLocation != null)
        {
            float accuracy = mCurrentLocation.getAccuracy();
            long time = mCurrentLocation.getTime();
            if (accuracy < bestAccuracy)
            {
                bestResult = mCurrentLocation;
                bestAccuracy = accuracy;
                bestTime = time;
            }
        }
        // Return best reading or null
        if (bestAccuracy > minAccuracy || bestTime < minTime)
        {
            return null;
        }
        else
        {
            return bestResult;
        }
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }



}




//        new Thread(){
//            public void run() {
//                try
//                {
//                    FileOutputStream latitudeFile = new FileOutputStream(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/latitude.txt");
//                    latitudeFile.write(String.valueOf(latitude).getBytes());
//                    latitudeFile.close();
//                    FileOutputStream longitudeFile = new FileOutputStream(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/longitude.txt");
//                    longitudeFile.write(String.valueOf(lon).getBytes());
//                    longitudeFile.close();
//                }
//                catch (Exception e)
//                {
//                    e.printStackTrace();
//                }
//            };
//        }.start();