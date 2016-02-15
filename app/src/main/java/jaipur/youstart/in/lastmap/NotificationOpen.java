package jaipur.youstart.in.lastmap;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

public class NotificationOpen extends AppCompatActivity
{
    ImageSwitcher imageSwitcher;
    TextView textView;
    String id;
    int ids=0;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_open);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle extras=getIntent().getExtras();
        if(extras!=null)
        {
            Toast.makeText(this, "Id of location is " + getIntent().getStringExtra("ID"), Toast.LENGTH_SHORT).show();
            id=getIntent().getStringExtra("ID");
            Log.e("id",""+id);
//            ids=Integer.parseInt(getIntent().getStringExtra("ID"));
        }





        textView=(TextView)findViewById(R.id.textView);
        imageSwitcher=(ImageSwitcher)findViewById(R.id.imageSwitcher);


            imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
                @Override
                public View makeView() {
                    ImageView imageView = new ImageView(getApplicationContext());
                    imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
//                imageView.setImageResource(R.drawable.mapmachine);
                    imageView.setLayoutParams(new ImageSwitcher.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                    if(id.equals("MN01"))
                    {
                        imageView.setImageResource(R.drawable.overlay5);
                        textView.setText("Hello Tourist ! this is success kid and this will kill you now .....");
                    }

                    if(id.equals("MN02"))
                    {
                            imageView.setImageResource(R.drawable.mapmachine);
                            textView.setText("Hi Tourist ! This is Hawk and flying in the sky .");
                    }
                    return imageView;
                }
            });









        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
