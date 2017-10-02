package id.bhinneka.rebon.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import id.bhinneka.rebon.R;
import id.bhinneka.rebon.app.AppController;
import id.bhinneka.rebon.utils.Constant;
import id.bhinneka.rebon.utils.DBHelper;
import id.bhinneka.rebon.utils.Utils;

public class ReviewPesananActivity extends AppCompatActivity {

    private static final String TAG = ReviewPesananActivity.class.getSimpleName();

    private String kode, jam, tgl, keberangkatan, harga, hargaApps, alamat, kursi, nama, noHp, idPayment;
    private TextView tvKeberangkatan, tvAlamat, tvHarga, tvHargaApps, tvJam, tvTanggal,
            tvSisaKursi, tvJumlahKursi, tvTotalHarga, tvInputKursi, tvInputPemesan, tvInputBayar,
            tvHelperKursi, tvHelperPemesan, tvHelperPembayaran, tvHelperNama, tvHelperHp;
    private EditText etnama, etNoHp;
    private int sisaKursi, jumlahKursi = 0, totalHarga = 30000;
    private DBHelper db;
    private CheckBox cbSimpanNama;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_pesanan);
        db = new DBHelper(this);

        tvAlamat = findViewById(R.id.tv_alamat);
        tvHarga = findViewById(R.id.tv_harga);
        tvKeberangkatan = findViewById(R.id.tv_keberangkatan);
        tvTanggal = findViewById(R.id.tv_tanggal);
        tvJam = findViewById(R.id.tv_jam);
        tvSisaKursi = findViewById(R.id.tv_sisa_kursi);
        tvJumlahKursi = findViewById(R.id.tv_jumlah_kursi);
        tvTotalHarga = findViewById(R.id.tv_total_harga);
        tvInputBayar = findViewById(R.id.tv_input_pembayaran);
        tvInputKursi = findViewById(R.id.tv_input_kursi);
        tvInputPemesan = findViewById(R.id.tv_input_pemesan);
        tvHelperKursi = findViewById(R.id.tv_helper_kursi);
        tvHelperPembayaran = findViewById(R.id.tv_helper_pembayaran);
        tvHelperPemesan = findViewById(R.id.tv_helper_pemesan);

        if (getIntent() != null) {
            kode = getIntent().getStringExtra("kode");
            jam = getIntent().getStringExtra("jam");
            tgl = getIntent().getStringExtra("tgl");
            keberangkatan = getIntent().getStringExtra("keberangkatan");
            harga = getIntent().getStringExtra("harga");
            alamat = getIntent().getStringExtra("alamat");
            sisaKursi = getIntent().getIntExtra("sisaKursi", 0);
            hargaApps = getIntent().getStringExtra("hargaApps");

            tvKeberangkatan.setText(keberangkatan);
            tvHarga.setText("Rp. " + Utils.rupiah(Integer.parseInt(harga)));
            tvTanggal.setText(Utils.getFormattedDateIndo(getApplicationContext(), tgl));
            tvJam.setText(jam);
            tvAlamat.setText(alamat);
            tvSisaKursi.setText(String.valueOf(sisaKursi));
        }

        tvInputKursi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReviewPesananActivity.this, PilihKursiActivity.class);
                intent.putExtra("kodeJadwal", kode);
                startActivityForResult(intent, 1);
            }
        });

        tvInputPemesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflate = LayoutInflater.from(ReviewPesananActivity.this);
                View dialogView = inflate.inflate(R.layout.dialog_input_pemesan, null);

                final AlertDialog.Builder dialog = new AlertDialog.Builder(ReviewPesananActivity.this);
                dialog.setView(dialogView);
                final SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

                etnama = dialogView.findViewById(R.id.et_input_nama);
                etNoHp = dialogView.findViewById(R.id.et_input_handphone);
                tvHelperNama = dialogView.findViewById(R.id.tv_helper_nama);
                tvHelperHp = dialogView.findViewById(R.id.tv_helper_hp);
                cbSimpanNama = dialogView.findViewById(R.id.cb_simpan_nama);

                TextView btnOk = dialogView.findViewById(R.id.btn_ok);
                TextView btnBatal = dialogView.findViewById(R.id.btn_batal);

                final AlertDialog alert = dialog.create();
                if (sp.getBoolean("simpanNama", true)){
                    cbSimpanNama.setChecked(true);
                    etnama.setText(sp.getString("nama", null));
                    etNoHp.setText(sp.getString("telepon",null));
                }
                btnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (validateNama() && validateHandphone()) {
                            String hp = etNoHp.getText().toString().trim();
                            if (hp.substring(0, 2).equals("62")) {
                                hp = 0 + hp.substring(2);
                            }
                            noHp = hp;
                            nama = etnama.getText().toString().trim();

                            tvInputPemesan.setText(nama + " (" + noHp + ")");
                            tvInputPemesan.setTextColor(Color.BLACK);
                            alert.dismiss();
                            validatePemesan();
                            if (cbSimpanNama.isChecked()){
                                SharedPreferences.Editor edit = sp.edit();
                                edit.putBoolean("simpanNama", true);
                                edit.putString("nama", nama);
                                edit.putString("telepon", noHp);
                                edit.apply();
                            }else{
                                SharedPreferences.Editor edit = sp.edit();
                                edit.putBoolean("simpanNama", false);
                                edit.putString("nama", null);
                                edit.putString("telepon", null);
                                edit.apply();
                            }
                        }
                    }
                });

                btnBatal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alert.dismiss();
                    }
                });

                alert.show();
            }
        });

        tvInputBayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(ReviewPesananActivity.this, PilihPaymentActivity.class), 2);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int input = requestCode;
        if (resultCode == RESULT_OK) {
            if (input == 1) {
                jumlahKursi = data.getExtras().getInt("jumlah_kursi");
                kursi = data.getExtras().getString("kursi");
                totalHarga = data.getExtras().getInt("total_bayar");
                tvInputKursi.setText(kursi);
                tvInputKursi.setTextColor(Color.BLACK);
                tvTotalHarga.setText("Rp. " + Utils.rupiah(totalHarga));
                tvJumlahKursi.setText(String.valueOf(jumlahKursi));
                validateKursi();
            } else if (input == 2) {
                String pembayaran = data.getStringExtra("pembayaran");
                idPayment = data.getStringExtra("id");
                tvInputBayar.setText(pembayaran);
                tvInputBayar.setTextColor(Color.BLACK);
                validatePembayaran();
            }
        }
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
            if (validateKursi() && validatePemesan() && validatePembayaran())
                kirimData();
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean validateHandphone() {
        String handphone = etNoHp.getText().toString().trim();

        if (handphone.isEmpty()) {
            tvHelperHp.setText("No Hp tidak boleh kosong");
            tvHelperHp.setVisibility(View.VISIBLE);
            return false;
        } else if (handphone.length() < 10) {
            tvHelperHp.setText("No Hp kurang dari 10");
            tvHelperHp.setVisibility(View.VISIBLE);
            return false;
        } else {
            tvHelperHp.setVisibility(View.INVISIBLE);
            return true;
        }
    }

    private boolean validateNama() {
        String nama = etnama.getText().toString().trim();
        if (nama.isEmpty()) {
            tvHelperNama.setText("Nama tidak boleh kosong");
            tvHelperNama.setVisibility(View.VISIBLE);
            return false;
        } else {
            tvHelperNama.setVisibility(View.INVISIBLE);
            return true;
        }
    }

    private boolean validateKursi() {
        String kursi = tvInputKursi.getText().toString();

        int bottom = tvInputKursi.getPaddingBottom();
        int left = tvInputKursi.getPaddingLeft();
        int right = tvInputKursi.getPaddingRight();
        int top = tvInputKursi.getPaddingTop();

        if (kursi.equalsIgnoreCase("pilih kursi")) {
            tvInputKursi.setBackgroundResource(R.drawable.rounded_border_error);
            tvInputKursi.setPadding(left, top, right, bottom);
            tvHelperKursi.setVisibility(View.VISIBLE);
            return false;
        } else {
            tvInputKursi.setBackgroundResource(R.drawable.rounded_border);
            tvInputKursi.setPadding(left, top, right, bottom);
            tvHelperKursi.setVisibility(View.INVISIBLE);
            return true;
        }
    }

    private boolean validatePembayaran() {
        String bayar = tvInputBayar.getText().toString();

        int bottom = tvInputBayar.getPaddingBottom();
        int left = tvInputBayar.getPaddingLeft();
        int right = tvInputBayar.getPaddingRight();
        int top = tvInputBayar.getPaddingTop();

        if (bayar.equalsIgnoreCase("pembayaran")) {
            tvInputBayar.setBackgroundResource(R.drawable.rounded_border_error);
            tvInputBayar.setPadding(left, top, right, bottom);
            tvHelperPembayaran.setVisibility(View.VISIBLE);
            return false;
        } else {
            tvInputBayar.setBackgroundResource(R.drawable.rounded_border);
            tvInputBayar.setPadding(left, top, right, bottom);
            tvHelperPembayaran.setVisibility(View.INVISIBLE);
            return true;
        }
    }

    private boolean validatePemesan() {
        String pemesan = tvInputPemesan.getText().toString();
        int bottom = tvInputPemesan.getPaddingBottom();
        int left = tvInputPemesan.getPaddingLeft();
        int right = tvInputPemesan.getPaddingRight();
        int top = tvInputPemesan.getPaddingTop();

        if (pemesan.equalsIgnoreCase("pemesan")) {
            tvInputPemesan.setBackgroundResource(R.drawable.rounded_border_error);
            tvInputPemesan.setPadding(left, top, right, bottom);
            tvHelperPemesan.setVisibility(View.VISIBLE);
            return false;
        } else {
            tvInputPemesan.setBackgroundResource(R.drawable.rounded_border);
            tvInputPemesan.setPadding(left, top, right, bottom);
            tvHelperPemesan.setVisibility(View.INVISIBLE);
            return true;
        }
    }

    private void kirimData() {
        StringRequest request = new StringRequest(Request.Method.POST,
                Constant.POST_ORDER, new Response.Listener<String>() {
            @Override
            public void onResponse(String res) {
                Log.v(TAG, "response : " + res);
                try {
                    JSONObject response = new JSONObject(res);
                    String error = response.getString("error");
                    if (error.equals("false")) {
                        if (db.insertPesanan(response.getString("booking_kode"))) {

                            Intent intent = new Intent(ReviewPesananActivity.this, MainActivity.class);
                            intent.setAction("openTabPesanan");
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                    } else {
                        Toast.makeText(ReviewPesananActivity.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "JSON parsing error : " + e.getMessage());
                    Toast.makeText(ReviewPesananActivity.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Volley Error : " + error.getMessage());
                Toast.makeText(ReviewPesananActivity.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> param = new HashMap<>();
                param.put("jadwal_kode", kode);
                param.put("keberangkatan", keberangkatan);
                param.put("pemesan", nama);
                param.put("telepon", noHp);
                param.put("jumlah_kursi", String.valueOf(jumlahKursi));
                param.put("harga", String.valueOf(harga));
                param.put("total_harga", String.valueOf(totalHarga));
                param.put("diskon", "");
                param.put("kursi", kursi);
                param.put("id_payment", idPayment);
                Log.e(TAG, "params kirim data : " + param);
                return param;
            }
        };
        AppController.getInstance(this).addToRequestQueue(request);
    }
}
