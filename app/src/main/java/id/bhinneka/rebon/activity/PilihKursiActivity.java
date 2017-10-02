package id.bhinneka.rebon.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import id.bhinneka.rebon.R;
import id.bhinneka.rebon.adpter.CustomGridViewAdapter;
import id.bhinneka.rebon.app.AppController;
import id.bhinneka.rebon.fragment.loading.ProgressFrameLayout;
import id.bhinneka.rebon.model.KursiModel;
import id.bhinneka.rebon.utils.Constant;
import id.bhinneka.rebon.utils.Utils;

public class PilihKursiActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private static final String TAG = PilihKursiActivity.class.getSimpleName();

    private GridView gridView;
    private ArrayList<KursiModel> data = new ArrayList<>(15);
    private CustomGridViewAdapter gridAdapter;
    private Bitmap seatDriver, seatBooked, seatIcon, seatSelected, seatNone;
    private TextView tvJumlahKursi, tvTotalHarga;
    private int tempJumlahKursi = 0, jumlahHarga, tempJumlahHarga = 30000;
    private List<String> listKursi = new ArrayList<>();
    private String x = "", kodeJadwal;
    private ProgressFrameLayout progressFrameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilih_kursi);

        tvJumlahKursi = findViewById(R.id.tv_jumlah_kursi);
        tvTotalHarga = findViewById(R.id.tv_total_harga);

        progressFrameLayout = findViewById(R.id.progres_frame_layout);
        progressFrameLayout.showLoading();
        if (getIntent() != null) {
            kodeJadwal = getIntent().getStringExtra("kodeJadwal");
            Log.e(TAG, "kode jadwal :" + kodeJadwal);
            getData(kodeJadwal);
        }

        seatBooked = BitmapFactory.decodeResource(this.getResources(), R.drawable.seat_booked);
        seatDriver = BitmapFactory.decodeResource(this.getResources(), R.drawable.steering_icon);
        seatIcon = BitmapFactory.decodeResource(this.getResources(), R.drawable.seat);
        seatSelected = BitmapFactory.decodeResource(this.getResources(), R.drawable.seat_selected);
        seatNone = BitmapFactory.decodeResource(this.getResources(), R.drawable.seat_none);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_next, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        } else if (item.getItemId() == R.id.action_next) {
            String kursi, temp_kursi = "";
            int i, len = data.size();
            for (i = 0; i < len; i++) {
                if (data.get(i).getImage() == seatSelected) {
                    temp_kursi += data.get(i).getTitle() + " ";
                }
            }
            kursi = temp_kursi.trim().replace(" ", ",");

            Bundle data = new Bundle();
            data.putInt("jumlah_kursi", Integer.parseInt(tvJumlahKursi.getText().toString()));
            data.putString("kursi", kursi);
            data.putInt("total_bayar", jumlahHarga);
            Intent resultIntent = new Intent();
            resultIntent.putExtras(data);
            setResult(this.RESULT_OK, resultIntent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void setSeatSelected(int pos) {
        KursiModel model = data.get(pos);
        data.remove(pos);
        data.add(pos, new KursiModel(seatSelected, model.getTitle()));
        gridAdapter.notifyDataSetChanged();
    }

    private void setSeatDeselected(int pos) {
        KursiModel model = data.get(pos);
        data.remove(pos);
        data.add(pos, new KursiModel(seatIcon, model.getTitle()));
        gridAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        tempJumlahKursi = 0;
        KursiModel model = data.get(position);
        Bitmap seatCompare = model.getImage();

        if (seatCompare == seatIcon) {
            setSeatSelected(position);
        } else if (seatCompare == seatSelected) {
            setSeatDeselected(position);
        }

        int i, len = data.size();
        for (i = 0; i < len; i++) {
            if (data.get(i).getImage() == seatSelected) {
                tempJumlahKursi++;
            }
        }
        Log.e(TAG, "jumlah kursi :" + tempJumlahKursi + " No : " + x);
        if (tempJumlahKursi > 0) {
            jumlahHarga = tempJumlahHarga * tempJumlahKursi;
            tvJumlahKursi.setText(String.valueOf(tempJumlahKursi));
            tvTotalHarga.setText("Rp." + Utils.rupiah(jumlahHarga));
            Log.e(TAG, "total harga :" + jumlahHarga);
        } else {
            tvJumlahKursi.setText(String.valueOf(0));
            tvTotalHarga.setText(String.valueOf("Rp.0"));
        }
    }

    private void dataKursi() {
        // i < 32 (jumlah seluruh array yg digunakan untuk membuat tampilan pilih kursi)
        for (int i = 0; i < 32; i++) {
            switch (i) {
                // di listkursi array ke 2 tambahin kursi kosong dan kursi pengemudi
                case 1: {
                    listKursi.add(2, "kosong");
                    listKursi.add(3, "kosong");
                    listKursi.add(4, "pengemudi");
                } break;
                case 8:
                    listKursi.add(7, "kosong");
                    break;
                case 12:
                    listKursi.add(12, "kosong");
                    break;
                case 17:
                    listKursi.add(17, "kosong");
                    break;
                case 22:
                    listKursi.add(22, "kosong");
                    break;
                case 27:
                    listKursi.add(27, "kosong");
                    break;
            }
        }
        Log.e(TAG, "List : " + listKursi);

        for (int a = 0; a < listKursi.size(); a++) {
            if (listKursi.get(a).length() <= 2) {
                data.add(new KursiModel(seatIcon, String.valueOf(listKursi.get(a))));
            } else if (listKursi.get(a).toString().equals("kosong")) {
                data.add(new KursiModel(seatNone, ""));
            } else if (listKursi.get(a).toString().equals("pengemudi")) {
                data.add(new KursiModel(seatDriver, ""));
            } else {
                try {
                    JSONObject object = new JSONObject(listKursi.get(a).toString());
                    String pemesan = object.getString("pemesan");
                    String statusKursi = object.getString("status_kursi");
                    String kursi = object.getString("kursi");
                    if (statusKursi.equals("1"))
                        data.add(new KursiModel(seatBooked, String.valueOf(kursi)));
                } catch (JSONException e) {
                    Log.e(TAG, "JSON Parse Error : " + e.getMessage());
                }
            }
        }
    }

    private void getData(String kodeJadwal) {
        String url = Constant.GET_KURSI.replace("$kode_jadwal", kodeJadwal);
        JsonArrayRequest request = new JsonArrayRequest
                ( url, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.v(TAG, response.toString());
                        if(response.length() > 0 ){
                            progressFrameLayout.showContent();
                            for (int a = 1; a< response.length(); a++){
                                try {
                                    Log.e(TAG, "Kursi array :" + response.getString(a));
                                    listKursi.add(response.get(a).toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            dataKursi();
                            gridAdapter = new CustomGridViewAdapter(PilihKursiActivity.this, R.layout.item_list_kursi, data);
                            gridView =  findViewById(R.id.grid_view);
                            gridView.setNumColumns(5);
                            gridView.setAdapter(gridAdapter);
                            gridView.setOnItemClickListener(PilihKursiActivity.this);
                        }else{
                            progressFrameLayout.showEmpty(R.drawable.icon_logo,"Tidak ada Jadwal","Tidak ada jadwal keberangkatan ");
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Volley Error : "+error.getMessage());
                        progressFrameLayout.showError(R.drawable.ic_dialog_close_light,
                                "Terjadi Kesalahan", "Koneksi Error", "Coba Lagi",
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        progressFrameLayout.showLoading();
                                    }
                                });
                    }
                });
        AppController.getInstance(this).addToRequestQueue(request);
    }
}
