package jaipur.youstart.in.lastmap;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by youstart on 2/8/2016.
 */
public class AlaramReceiver extends BroadcastReceiver   {
    public static String ACTION_ALARM = "com.alarammanager.alaram";

//    LocationManager locationmanager;
    @Override
    public void onReceive(Context context, Intent intent) {


//        Toast.makeText(context, "Entered", Toast.LENGTH_SHORT).show();

        Bundle bundle = intent.getExtras();
        String action = bundle.getString(ACTION_ALARM);
        if (action.equals(ACTION_ALARM)) {
            Log.i("Alarm Receiver", "If loop");


//            locationmanager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
//            LocationListener locationListener=new MylocationlistenerInterface();

            Intent inService = new Intent(context,NewService.class);
//            locationmanager.requestLocationUpdates(LocationManager.GPS_PROVIDER,10,10,locationListener);
//            PendingIntent pendingintent = PendingIntent.getService(context, 0, inService, 0);
//            locationmanager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000, 10,locationListener, Looper.myLooper());

            context.startService(inService);
        }
        else
        {
            Log.i("Alarm Receiver", "Else loop");
            Toast.makeText(context, "Else loop", Toast.LENGTH_SHORT).show();
        }
    }
}
