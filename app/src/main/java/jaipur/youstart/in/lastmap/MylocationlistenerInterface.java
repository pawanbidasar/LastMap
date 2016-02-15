package jaipur.youstart.in.lastmap;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by youstart on 2/9/2016.
 */
public class MylocationlistenerInterface implements com.google.android.gms.location.LocationListener {

    @Override
   public void onLocationChanged(Location location){
        Log.d("hxxxxxxxxxxxxxxxxxxxxxx",location.getLatitude()+":"+location.getLongitude());

    };


}
