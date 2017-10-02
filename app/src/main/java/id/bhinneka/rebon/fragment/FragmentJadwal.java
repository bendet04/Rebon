package id.bhinneka.rebon.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import id.bhinneka.rebon.R;
import id.bhinneka.rebon.activity.ReviewPesananActivity;
import id.bhinneka.rebon.adpter.JadwalAdapter;
import id.bhinneka.rebon.app.AppController;
import id.bhinneka.rebon.fragment.loading.ProgressFrameLayout;
import id.bhinneka.rebon.model.JadwalModel;
import id.bhinneka.rebon.utils.Constant;
import id.bhinneka.rebon.utils.DividerItemDecoration;
import id.bhinneka.rebon.utils.RecycelerTouchListener;
import id.bhinneka.rebon.utils.Utils;

/**
 * Created by bendet on 03/08/17.
 */

public class FragmentJadwal extends Fragment {
    private static final String TAG = FragmentJadwal.class.getSimpleName();

    private static final String KEY_DATE = "date";
    private String tanggal;
    private RecyclerView jadwalRecyclerVeiw;
    private JadwalAdapter adapter;
    private final List<JadwalModel> data = new ArrayList<>();
    private ProgressFrameLayout progressFrameLayout;
    private ArrayList<String> listKursi = new ArrayList<>();

    public static FragmentJadwal newInstance(long date) {
        FragmentJadwal fragmentFirst = new FragmentJadwal();
        Bundle args = new Bundle();
        args.putLong(KEY_DATE, date);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    @Override
    public void onCreate(Bundle savedStateInstance) {
        super.onCreate(savedStateInstance);
        final long millis = getArguments().getLong(KEY_DATE);
        if (millis > 0) {
            final Context context = getActivity();
            if (context != null) {
                tanggal = Utils.getFormattedDate(context, millis);
                return;
            }
        }
        tanggal = "";
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_jadwal, container, false);
        jadwalRecyclerVeiw = view.findViewById(R.id.recylerview_jadwal);
        progressFrameLayout = view.findViewById(R.id.progress);

        progressFrameLayout.showLoading();
        getData(tanggal);

        adapter = new JadwalAdapter(data);

        jadwalRecyclerVeiw.setLayoutManager(new LinearLayoutManager(getContext()));
        jadwalRecyclerVeiw.setHasFixedSize(true);
        jadwalRecyclerVeiw.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        jadwalRecyclerVeiw.setAdapter(adapter);
        jadwalRecyclerVeiw.addOnItemTouchListener(new RecycelerTouchListener(getContext(),
                jadwalRecyclerVeiw, new RecycelerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                if (data.get(position).getSisaKursi() > 0){
                    JadwalModel model = data.get(position);
                    Intent intent = new Intent(getContext(), ReviewPesananActivity.class);
                    intent.putExtra("keberangkatan", model.getLokasiKeberangkatan());
                    intent.putExtra("alamat", model.getAlamat());
                    intent.putExtra("harga", model.getHarga());
                    intent.putExtra("hargaApps", model.getHargaApps());
                    intent.putExtra("tgl", model.getTanggal());
                    intent.putExtra("jam", model.getJam());
                    intent.putExtra("kode", model.getKode());
                    intent.putExtra("sisaKursi", model.getSisaKursi());
                    intent.putExtra("kursi", listKursi);
                    startActivity(intent);
                }

            }
        }));

        return view;
    }

    private void getData(String tgl) {
        String url = Constant.GET_JADWAL_BY_TGL.replace("$tgl", tgl);
        JsonArrayRequest request = new JsonArrayRequest
                ( url, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.v(TAG, response.toString());
                        if(response.length() > 0 ){
                            progressFrameLayout.showContent();
                            for (int i = 0; i < response.length(); i++){
                                try{
                                    JSONObject jsonObject = response.getJSONObject(i);

                                    JadwalModel model = new JadwalModel();
                                    model.setAlamat(jsonObject.getString("alamat"));
                                    model.setHarga(jsonObject.getString("harga"));
                                    model.setHargaApps(jsonObject.getString("harga_apps"));
                                    model.setJam(jsonObject.getString("jam"));
                                    model.setKode(jsonObject.getString("kode"));
                                    model.setLokasiKeberangkatan(jsonObject.getString("keberangkatan"));
                                    model.setTanggal(jsonObject.getString("tanggal"));
                                    model.setSisaKursi(jsonObject.getInt("sisa_kursi"));

                                    data.add(model);
                                }catch (JSONException e){
                                    Log.e(TAG, "JSONException: "+ e.getMessage());
                                }
                            }
                            adapter.notifyDataSetChanged();
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
                                errorClickListener);
                    }
                });
        AppController.getInstance(getContext()).addToRequestQueue(request);
    }

    private View.OnClickListener errorClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            progressFrameLayout.showLoading();
            getData(tanggal);
        }
    };
}
