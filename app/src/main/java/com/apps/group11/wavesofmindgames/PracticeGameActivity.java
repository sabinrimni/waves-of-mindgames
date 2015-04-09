package com.apps.group11.wavesofmindgames;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.os.Handler;

import com.neurosky.thinkgear.TGDevice;


public class PracticeGameActivity extends MainActivity {

    Handler customHandler = new Handler();
    int att;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice_game);

        customHandler.postDelayed(updateAttention, 0);
    }

    private Runnable updateAttention = new Runnable()
    {
        public void run()
        {

            att = TGDevice.MSG_ATTENTION;

            TextView a = (TextView)findViewById(R.id.textView1);
            a.setText(String.valueOf(att));

            TextView b = (TextView)findViewById(R.id.textView2);

            if(att <= 10)
            {
                if(att <= 20 && att > 10)
                {
                    if(att <= 30 && att > 20)
                    {
                        if(att <= 40 && att > 30)
                        {
                            if(att <= 50 && att > 40 )
                            {
                                if(att <= 60 && att > 50)
                                {
                                    if(att <= 70 && att > 60)
                                    {
                                        if(att <= 80 && att > 70 )
                                        {
                                            if(att <= 90 && att > 80)
                                            {
                                                if(att <= 99 && att > 90)
                                                {
                                                    if(att == 100)
                                                    {
                                                        b.setText("YOU DID IT, Congratulation");
                                                    }
                                                    b.setText("Work that mind, you can do it");
                                                }
                                                b.setText("You are so close");
                                            }
                                            b.setText("Keep your mind on one thing");
                                        }
                                        b.setText("You have come pretty far focus");
                                    }
                                    b.setText("Now it's getting harder concentrate");
                                }
                                b.setText("Half way there champ");
                            }
                            b.setText("I knew you could do it");
                        }
                        b.setText("Concentrate you can do better");
                    }
                    b.setText("Hold that attention");
                }
                b.setText("Good Start");
            }
            customHandler.postDelayed(this, 1000);
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_practice_game, menu);
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
}
