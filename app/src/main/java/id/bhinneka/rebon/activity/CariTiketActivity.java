package id.bhinneka.rebon.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import id.bhinneka.rebon.R;
import id.bhinneka.rebon.adpter.LokasiKeberangkatanAdapter;
import id.bhinneka.rebon.fragment.FragmentKalenderBottomSheet;
import id.bhinneka.rebon.fragment.FragmentKeberangkatanBottomSheet;
import id.bhinneka.rebon.model.LokasiKeberangkatanModel;

public class CariTiketActivity extends AppCompatActivity {
    private static final String TAG = CariTiketActivity.class.getSimpleName();

    private EditText etKeberangkatan, etTanggal;
    private BottomSheetBehavior behaviorKeberangkatan;
    private BottomSheetBehavior behaviorTanggal;

    private View bottomSheetKeberangkatan, bottomSheetTanggal, bg;

    private TextView btnCariTiket;

    private RecyclerView keberangkatanRecyclerVeiw;
    private LokasiKeberangkatanAdapter adapter;
    private BroadcastReceiver broadcastReceiver;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cari_tiket);

        etKeberangkatan = (EditText) findViewById(R.id.et_keberangkatan);
        etTanggal = (EditText) findViewById(R.id.et_tgl_keberangkatan);
        keberangkatanRecyclerVeiw = (RecyclerView) findViewById(R.id.recyclerview_keberangkatan);
        btnCariTiket = (TextView) findViewById(R.id.btn_cari_tiket);

        broadcastReceiver = new LocalBroadcastReceiver();

        bottomSheetKeberangkatan = findViewById(R.id.bottom_sheet_keberangkatan);
        bottomSheetTanggal = findViewById(R.id.bottom_sheet_tanggal);

        behaviorKeberangkatan = BottomSheetBehavior.from(bottomSheetKeberangkatan);
        behaviorTanggal = BottomSheetBehavior.from(bottomSheetTanggal);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String currDate =  sdf.format(new Date());
        etTanggal.setText(currDate);
        bg = findViewById(R.id.bg);

        btnCariTiket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CariTiketActivity.this, JadwalActivity.class);
                startActivity(intent);
            }
        });
        behaviorKeberangkatan.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_COLLAPSED)
                    bg.setVisibility(View.GONE);
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                bg.setVisibility(View.VISIBLE);
            }
        });
        behaviorTanggal.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        keberangkatanRecyclerVeiw.setHasFixedSize(true);
        keberangkatanRecyclerVeiw.setLayoutManager(new LinearLayoutManager(this));

        List<LokasiKeberangkatanModel> models = new ArrayList<>();
        models.add(new LokasiKeberangkatanModel(
                "Pool Bhinneka Cirebon", "Jl. Pilang Raya Cirebon", 10000, 10000));
        models.add(new LokasiKeberangkatanModel(
                "Pool Bhinneka Bandung", "Jl. Pilang Raya Cirebon", 10000, 10000));
        models.add(new LokasiKeberangkatanModel(
                "Pool Bhinneka Karawang", "Jl. Pilang Raya Cirebon", 10000, 10000));

        etKeberangkatan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //behaviorKeberangkatan.setState(BottomSheetBehavior.STATE_EXPANDED);
                 showBottomSheetKeberangkatanFragment();
            }
        });

        etTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomKalenderFragment();
            }
        });

        adapter = new LokasiKeberangkatanAdapter(models, new LokasiKeberangkatanAdapter.ItemListener() {
            @Override
            public void onItemClick(String lokasiKeberangkatan) {
                etKeberangkatan.setText(lokasiKeberangkatan);
                behaviorKeberangkatan.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });

        keberangkatanRecyclerVeiw.setAdapter(adapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(
                broadcastReceiver, new IntentFilter("keberangkatan"));
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
    }

    /*
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (behaviorKeberangkatan.getState() == BottomSheetBehavior.STATE_EXPANDED) {

                Rect outRect = new Rect();
                bottomSheetKeberangkatan.getGlobalVisibleRect(outRect);

                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY()))
                    behaviorKeberangkatan.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        }

        return super.dispatchTouchEvent(event);
    }
*/

    private void showBottomSheetKeberangkatanFragment() {
        FragmentKeberangkatanBottomSheet fragmentModalBottomSheet = new FragmentKeberangkatanBottomSheet();
        fragmentModalBottomSheet.show(getSupportFragmentManager(), "Keberangkatan");

    }

    private void showBottomKalenderFragment(){
        FragmentKalenderBottomSheet kalender = new FragmentKalenderBottomSheet();
        kalender.show(getSupportFragmentManager(),"Kalender");
    }

    @Override
    public void onBackPressed() {
        if (behaviorKeberangkatan.getState() == 3) {
            behaviorKeberangkatan.setState(BottomSheetBehavior.STATE_COLLAPSED);
        } else {
            super.onBackPressed();
        }
    }

    private class LocalBroadcastReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e(TAG, "ada broadcast");
            // cek dulu biar safety :D
            if (intent == null || intent.getAction().equals("")){
                return;
            }

            if (intent.getAction().equals("keberangkatan")){
                etKeberangkatan.setText(intent.getStringExtra("lokasiKeberangaktan"));
            }
        }
    }
}
