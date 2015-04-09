package com.apps.group11.wavesofmindgames;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.neurosky.thinkgear.TGDevice;
import com.neurosky.thinkgear.TGEegPower;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {
    TGDevice tgDevice;
    BluetoothAdapter btAdapter;
    TGEegPower tgePower;
    int deltaValue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btAdapter = BluetoothAdapter.getDefaultAdapter();
        if(btAdapter != null) {
            tgDevice = new TGDevice(btAdapter, handler);
        }

        Button button = (Button)findViewById(R.id.button);
        button.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        tgDevice.connect(true);
                    }
                }
        );
    }

    public void showTexts()
    {
        TextView signal = (TextView)findViewById(R.id.textSignal);
        TextView attention = (TextView)findViewById(R.id.textAttention);
        TextView delta = (TextView)findViewById(R.id.textDelta);
        TextView theta = (TextView)findViewById(R.id.textTheta);
        TextView lowAlpha = (TextView)findViewById(R.id.textLowA);
        TextView highAlpha = (TextView)findViewById(R.id.textHighA);
        TextView lowBeta = (TextView)findViewById(R.id.textLowB);
        TextView highBeta = (TextView)findViewById(R.id.textHighB);
        TextView lowGamma = (TextView)findViewById(R.id.textLowG);
        TextView highGamma = (TextView)findViewById(R.id.textHighG);

        signal.setVisibility(View.VISIBLE);
        attention.setVisibility(View.VISIBLE);
        delta.setVisibility(View.VISIBLE);
        theta.setVisibility(View.VISIBLE);
        lowAlpha.setVisibility(View.VISIBLE);
        highAlpha.setVisibility(View.VISIBLE);
        lowBeta.setVisibility(View.VISIBLE);
        highBeta.setVisibility(View.VISIBLE);
        lowGamma.setVisibility(View.VISIBLE);
        highGamma.setVisibility(View.VISIBLE);
    }

    public void getAttention(Message msg)
    {
        TGEegPower ep = (TGEegPower)msg.obj;

        int attention;

        attention = ep.delta;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            ProgressDialog pd = new ProgressDialog(MainActivity.this);
            switch (msg.what) {
                case TGDevice.MSG_STATE_CHANGE:
                    switch (msg.arg1) {
                        case TGDevice.STATE_IDLE:

                            break;
                        case TGDevice.STATE_CONNECTING:
                            //pd.show(MainActivity.this, "Connecting", "Please Wait...", true, true);
                            break;
                        case TGDevice.STATE_CONNECTED:
                            tgDevice.start();
                            showTexts();
                            break;
                        case TGDevice.STATE_DISCONNECTED:

                            break;
                        case TGDevice.STATE_NOT_FOUND:
                        case TGDevice.STATE_NOT_PAIRED:
                        default:
                            break;
                    }
                    break;
                case TGDevice.MSG_POOR_SIGNAL:
                    Log.v("HelloEEG", "PoorSignal: " + msg.arg1);
                case TGDevice.MSG_ATTENTION:
                    Log.v("HelloEEG", "Attention: " + msg.arg1);
                    break;
                case TGDevice.MSG_RAW_DATA:
                    int rawValue = msg.arg1;
                    break;
                case TGDevice.MSG_EEG_POWER:
                    TGEegPower ep = (TGEegPower)msg.obj;
                    Log.v("HelloEEG", "Delta: " + ep.delta);
                    Log.v("HelloEEG", "Theta: " + ep.theta);
                    Log.v("HelloEEG", "Low Alpha: " + ep.lowAlpha);
                    Log.v("HelloEEG", "High Alpha: " + ep.highAlpha);
                    Log.v("HelloEEG", "Low Beta: " + ep.lowBeta);
                    Log.v("HelloEEG", "High Beta: " + ep.highBeta);
                    Log.v("HelloEEG", "Low Gamma: " + ep.lowGamma);
                    Log.v("HelloEEG", "Mid Gamma: " + ep.midGamma);
                default:
                    break;
            }
        }
    };

}
