package com.apps.group11.wavesofmindgames;

import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.neurosky.thinkgear.TGDevice;


public class BallGame extends MainActivity {

    Handler customHandler = new Handler();
    ImageView ball=(ImageView)findViewById(R.id.imageView);
    int att;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ball_game, menu);
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
            ball.setBottom((25-att)/10);
            if (ball.getBottom() < 1)
            {
                TextView text=(TextView)findViewById(R.id.textView3);
                text.setText("You lose!");
                text.setVisibility(View.VISIBLE);
            }
            if (ball.getBottom() > 300)
            {
                TextView text=(TextView)findViewById(R.id.textView3);
                text.setText("You win!");
                text.setVisibility(View.VISIBLE);
            }

            customHandler.postDelayed(this, 1000);
        }
    };
}
