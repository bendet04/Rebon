package id.bhinneka.rebon.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import id.bhinneka.rebon.R;
import id.bhinneka.rebon.adpter.CachingFragmentAdapter;

public class JadwalActivity extends AppCompatActivity {

    private static Context mContext;
    private CachingFragmentAdapter adapterViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jadwal);

        mContext = this;
        ViewPager vpPager = (ViewPager) findViewById(R.id.viewpager);
        adapterViewPager = new CachingFragmentAdapter(getSupportFragmentManager(), mContext);
        vpPager.setAdapter(adapterViewPager);
        vpPager.setCurrentItem(0);
    }

    /*
    private RecyclerView jadwalRecyclerVeiw;
    private JadwalAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jadwal);

        final List<JadwalModel> data = new ArrayList<>();

        data.add(new JadwalModel("08:00", "Pool Bhinneka", "Jl. Pilang Raya No. 586", "Rp. 30.000","40.000","Senin, 07-07-2017","CTR-0001",20));
        data.add(new JadwalModel("09:00", "Pool Taxi", "Jl. Ahmad Yani No. 91, Kecapi, Harjamukti, Kecapi, Harjamukti, Kota Cirebon", "Rp. 30.000","40.000","Senin, 07-07-2017","CTR-0002",15));
        data.add(new JadwalModel("11:00", "Giant Rajawali", "di Jl. Rajawali Raya No.1, Kel. Kecapi, Kec. Harjamukti, Cirebon", "Rp. 30.000","40.000","Senin, 07-07-2017","CTR-0002",21));

        adapter = new JadwalAdapter(data);

        jadwalRecyclerVeiw = (RecyclerView) findViewById(R.id.recylerview_jadwal);
        jadwalRecyclerVeiw.setLayoutManager(new LinearLayoutManager(this));
        jadwalRecyclerVeiw.setHasFixedSize(true);
        jadwalRecyclerVeiw.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        jadwalRecyclerVeiw.setAdapter(adapter);
        jadwalRecyclerVeiw.addOnItemTouchListener(new RecycelerTouchListener(getApplicationContext(),
                jadwalRecyclerVeiw, new RecycelerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                JadwalModel model = data.get(position);
                Intent intent = new Intent(JadwalActivity.this, PilihKursiActivity.class);
                intent.putExtra("lokasiKeberangkatan", model.getLokasiKeberangkatan());
                intent.putExtra("harga", model.getHarga());
                intent.putExtra("hargaApps", model.getHargaApps());
                intent.putExtra("tgl", model.getTanggal());
                intent.putExtra("jam", model.getJam());
                intent.putExtra("kodeJadwal", model.getKode());
                startActivity(intent);
            }
        }));

    }

    */
}
