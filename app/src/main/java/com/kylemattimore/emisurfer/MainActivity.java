package com.kylemattimore.emisurfer;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    KylesView view;
    SeekBar bar;
    TextView tv_factor;

    double barMultiplier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        view = (KylesView) findViewById(R.id.view_canvas);

        tv_factor = (TextView) findViewById(R.id.tv_factor);

        // make text label for progress value
        final TextView textProgress = (TextView)findViewById(R.id.textViewProgress);

        bar = (SeekBar)findViewById(R.id.seekBar1); // make seekbar object

        bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                // TODO Auto-generated method stub
                barMultiplier = (float) progress;
                view.setMultiplier((float) progress);
                textProgress.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }
        });

        for (int i = 0; i < 4; ++i) {
            new PrimeFactorsTask().execute(new BigInteger("32452843"));
        }
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

    public class PrimeFactorsTask extends AsyncTask<BigInteger, Void, BigInteger> {
        @Override
        protected BigInteger doInBackground(BigInteger... params) {
            List<BigInteger> list = primeFactors(params[0]);
            return list.get(list.size()-1);
        }

        public List<BigInteger> primeFactors(BigInteger number) {
            BigInteger n = number;
            List<BigInteger> factors = new ArrayList<BigInteger>();
            for (BigInteger i = BigInteger.valueOf(2); i.compareTo(n) <= 0; i = i.add(BigInteger.ONE)) {
                while (n.remainder(i).equals(BigInteger.ZERO)) {
                    factors.add(i);
                    n = n.divide(i);
                }
            }
            return factors;
        }

        @Override
        protected void onPostExecute(BigInteger integer) {
            tv_factor.setText(integer.toString());
            tv_factor.invalidate();
        }
    }
}

