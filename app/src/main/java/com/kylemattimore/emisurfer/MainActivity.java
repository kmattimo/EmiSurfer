package com.kylemattimore.emisurfer;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

    BluetoothAdapter mBluetoothAdapter;

    TextView tv_bt_scan;
    Button button_bt_scan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        // Maybe check if dev supports bluetooth?

        // Get UI elements
        button_bt_scan = (Button) findViewById(R.id.button_bt_search);
        tv_bt_scan = (TextView) findViewById(R.id.tv_bt_scan);

        // Bluetooth Intent receiver
        IntentFilter bt_filter = new IntentFilter();

        bt_filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        bt_filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);

        registerReceiver(btIntentReciever, bt_filter);

        // Set button listeners
        button_bt_scan.setOnClickListener (new View.OnClickListener() {
            public void onClick(View v) {
                // Do stuff here
                mBluetoothAdapter.startDiscovery();
            }
        });
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

    private final BroadcastReceiver btIntentReciever = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if(BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
                tv_bt_scan.setText("YES");
            }
            else if(BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                tv_bt_scan.setText("NOPE");
            }
        }
    };
}
