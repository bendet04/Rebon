package id.bhinneka.rebon.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

import id.bhinneka.rebon.R;

/**
 * Created by bendet on 05/07/17.
 */

public class TrackingActivity extends AppCompatActivity{
    private final static String TAG = TrackingActivity.class.getSimpleName();

    private GoogleMap mMap;
    private MapView mMapVeiw;
    private TextView btnPesanTiket;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_tiket);
        setupMap(savedInstanceState);

        btnPesanTiket = findViewById(R.id.btn_pesan_tiket);
        btnPesanTiket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TrackingActivity.this, JadwalActivity.class));
            }
        });
    }

    private void setupMap(Bundle savedInstanceState){
        mMapVeiw = findViewById(R.id.map);
        mMapVeiw.onCreate(savedInstanceState);
        mMapVeiw.onResume();

        try{
            MapsInitializer.initialize(getApplicationContext());
        }catch (Exception e){
            Log.e(TAG, e.getMessage());
        }

        mMapVeiw.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;

                // koordinat cirebon
                LatLng cirebon = new LatLng(-6.70795570, 108.53058230);

                mMap.moveCamera(CameraUpdateFactory.newLatLng(cirebon));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(13));
            }
        });
    }

}
