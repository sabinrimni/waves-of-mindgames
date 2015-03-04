package com.apps.group11.wavesofmindgames;

import android.app.AlertDialog;
import android.app.ProgressDialog;
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


public class MainActivity extends ActionBarActivity {
    TGDevice tgDevice;
    BluetoothAdapter btAdapter;


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
            switch (msg.what) {
                case TGDevice.MSG_STATE_CHANGE:
                    switch (msg.arg1) {
                        case TGDevice.STATE_IDLE:
                            break;
                        case TGDevice.STATE_CONNECTING:
                            ProgressDialog pd = ProgressDialog.show(MainActivity.this, "Connectiong", "Please Wait...", true);
                            break;
                        case TGDevice.STATE_CONNECTED:
                            tgDevice.start();
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
