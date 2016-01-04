package com.example.goncalomatos.spotifysdktest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RunActivity extends AppCompatActivity {

    private static final String CLIENT_ID = "68528c82f0a14b1da759976535533f48";
    private static final String ECHONEST_KEY = "BM5IMCRRSRYJMLZVK";
    private static final String REDIRECT_URI = "my-first-spotify-app://callback";
    private static final int REQUEST_CODE = 1337;

    private Intent intent;
    private SpotifyHelper spotifyHelper;
    private int lastPace = -50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        intent = new Intent(this, MockService.class);

        AuthenticationRequest.Builder builder = new AuthenticationRequest.Builder(CLIENT_ID,
                AuthenticationResponse.Type.TOKEN,
                REDIRECT_URI);
        builder.setScopes(new String[]{"user-read-private", "streaming"});
        AuthenticationRequest request = builder.build();

        AuthenticationClient.openLoginActivity(this, REQUEST_CODE, request);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        // Check if result comes from the correct activity
        if (requestCode == REQUEST_CODE) {
            final AuthenticationResponse response = AuthenticationClient.getResponse(resultCode, intent);

            if (response.getType() == AuthenticationResponse.Type.TOKEN) {
                spotifyHelper = new SpotifyHelper(RunActivity.this, response);
            }
        }
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            handleSensorData(intent);
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        // TODO: 04/01/16  this should be a foreground service
        startService(intent);
        registerReceiver(broadcastReceiver, new IntentFilter(MockService.BROADCAST_ACTION));
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
        stopService(intent);
    }

    public void updateTrackInfo(String currentTrack){
        TextView trackInfoUI = (TextView) findViewById(R.id.track_info);
        trackInfoUI.setText(currentTrack);
    }

    private void handleSensorData(Intent sensorDataIntent)
    {
        int pace = sensorDataIntent.getIntExtra("pace", 200);
        Log.d("RUN", "" + pace);

        //TODO: strategies and stuff
        if (pace > lastPace + 50 || pace < lastPace - 50 ) {
            Log.d("RunActivity", "changin pace");

            if(spotifyHelper != null) {
                spotifyHelper.queryAndPlay(pace);
            }
        }
        lastPace = pace;

    }


}
