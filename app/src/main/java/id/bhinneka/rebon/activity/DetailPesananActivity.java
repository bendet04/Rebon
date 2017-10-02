package id.bhinneka.rebon.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import id.bhinneka.rebon.R;
import id.bhinneka.rebon.app.AppController;
import id.bhinneka.rebon.fragment.loading.ProgressFrameLayout;
import id.bhinneka.rebon.utils.Constant;

public class DetailPesananActivity extends AppCompatActivity {
    private static final String TAG = DetailPesananActivity.class.getSimpleName();

    private ProgressFrameLayout progressFrameLayout;
    private TextView tvKeberangkatan, tvAlamat, tvStatus, tvHargaApps, tvJam, tvTanggal,
            tvJumlahKursi, tvTotalHarga, tvInputKursi, tvInputPemesan, tvInputBayar;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pesanan);

        tvAlamat =  (TextView) findViewById(R.id.tv_alamat);
        tvStatus =  (TextView) findViewById(R.id.tv_status);
        tvKeberangkatan =  (TextView) findViewById(R.id.tv_keberangkatan);
        tvTanggal =  (TextView) findViewById(R.id.tv_tanggal);
        tvJam =  (TextView) findViewById(R.id.tv_jam);
        tvJumlahKursi =  (TextView) findViewById(R.id.tv_jumlah_kursi);
        tvTotalHarga =  (TextView) findViewById(R.id.tv_total_harga);
        tvInputBayar =  (TextView) findViewById(R.id.tv_input_pembayaran);
        tvInputKursi =  (TextView) findViewById(R.id.tv_input_kursi);
        tvInputPemesan =  (TextView) findViewById(R.id.tv_input_pemesan);

    }

    private void getData(String bookingKode) {
        String url = Constant.GET_ORDER.replace("$kode", bookingKode);
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e(TAG, "response : " + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    progressFrameLayout.showContent();
                    tvKeberangkatan.setText(jsonObject.getString("keberangkatan"));
                    tvStatus.setText(jsonObject.getString("status"));
                    tvTanggal.setText(jsonObject.getString("tanggal"));
                    tvJam.setText(jsonObject.getString("jam"));
                    tvAlamat.setText(jsonObject.getString("alamat"));
                    tvTotalHarga.setText(jsonObject.getString("total_harga"));
                    tvJumlahKursi.setText(jsonObject.getString("jumlah_kursi"));
                    tvInputBayar.setText(jsonObject.getString("payment_type"));
                    tvInputKursi.setText(jsonObject.getString("kursi"));
                    tvInputPemesan.setText(jsonObject.getString("pemesan"));
                    tvStatus.setText(jsonObject.getString("status"));

                } catch (JSONException e) {
                    Log.e(TAG, "JSON parsing error : " + e.getMessage());
                    progressFrameLayout.showError(R.drawable.ic_dialog_close_light,
                            "Terjadi Kesalahan",
                            "Koneksi Error",
                            "Coba Lagi",
                            errorClickListener);

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Volley Error : " + error.getMessage());
                progressFrameLayout.showError(R.drawable.ic_dialog_close_light,
                        "Terjadi Kesalahan",
                        "Koneksi Error",
                        "Coba Lagi",
                        errorClickListener);
            }
        });

        AppController.getInstance(this).addToRequestQueue(request);
    }

    private View.OnClickListener errorClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            progressFrameLayout.showLoading();
            //getData();
        }
    };
}
