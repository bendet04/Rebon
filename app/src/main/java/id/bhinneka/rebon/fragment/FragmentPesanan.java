package id.bhinneka.rebon.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import id.bhinneka.rebon.R;
import id.bhinneka.rebon.adpter.PesananAdapter;
import id.bhinneka.rebon.app.AppController;
import id.bhinneka.rebon.fragment.loading.ProgressFrameLayout;
import id.bhinneka.rebon.model.PesananModel;
import id.bhinneka.rebon.utils.Constant;
import id.bhinneka.rebon.utils.DBHelper;

/**
 * Created by bendet on 05/07/17.
 */

public class FragmentPesanan extends Fragment {
    private static final String TAG = FragmentPesanan.class.getSimpleName();
    private View parentView;
    private ProgressFrameLayout progressFrameLayout;
    private RecyclerView recyclerView;
    private DBHelper db;
    private PesananAdapter adapter;
    private ArrayList<String> dataKode;
    private ArrayList<PesananModel> dataPesanan = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DBHelper(getActivity());
        dataKode = db.getAllPesanan();
       // for (int i = 0; i < dataKode.size(); i++)
         //   db.deleteContact(dataKode.get(i));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.fragment_pesanan, container, false);

        adapter = new PesananAdapter(dataPesanan);

        recyclerView = parentView.findViewById(R.id.recycler_pesanan);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        progressFrameLayout = parentView.findViewById(R.id.progress);

        if (dataKode.size() > 0) {
            String allKode = TextUtils.join(",", dataKode);
            Log.e(TAG, "All PesananModel : " + allKode);
            getData(allKode);
        } else {
            progressFrameLayout.showEmpty(R.drawable.icon_logo, "Belum Ada Pesanan", "Ada Belum Memesan");
        }

        return parentView;
    }

    private void getData(String bookingKode) {
        progressFrameLayout.showLoading();
        dataPesanan.clear();
        String url = Constant.GET_ORDER.replace("$kode", bookingKode);
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e(TAG, "response : " + response);
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        PesananModel model = new PesananModel();
                        model.setKursi(jsonObject.getString("kursi"));
                        model.setKeberangkatan(jsonObject.getString("keberangkatan"));
                        model.setNama(jsonObject.getString("pemesan"));
                        model.setNohp(jsonObject.getString("telepon"));
                        model.setJumlah_kursi(jsonObject.getString("jumlah_kursi"));
                        model.setTotalHarga(jsonObject.getString("total_harga"));
                        model.setLunas(jsonObject.getString("lunas"));
                        model.setStatusPesanan(jsonObject.getString("status"));
                        model.setStatusPayment(jsonObject.getString("status_payment"));
                        model.setAlamat(jsonObject.getString("alamat"));
                        model.setIdPayment(jsonObject.getString("id_payment"));
                        model.setPayment(jsonObject.getString("payment_type"));
                        model.setTanggal(jsonObject.getString("tanggal"));
                        model.setKode(jsonObject.getString("kode"));
                        model.setPaymentExpired(jsonObject.getString("payment_expired"));
                        model.setJam(jsonObject.getString("jam"));

                        dataPesanan.add(model);
                    }

                    progressFrameLayout.showContent();

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

        AppController.getInstance(getContext()).addToRequestQueue(request);
    }

    private View.OnClickListener errorClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            //getData();
        }
    };
}
