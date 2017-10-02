package id.bhinneka.rebon.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import id.bhinneka.rebon.R;
import id.bhinneka.rebon.activity.JadwalActivity;
import id.bhinneka.rebon.activity.SewaActivity;
import id.bhinneka.rebon.activity.TrackingActivity;
import id.bhinneka.rebon.adpter.WisataVerticalAdapter;
import id.bhinneka.rebon.adpter.SlideImageAdapter;
import id.bhinneka.rebon.app.AppController;
import id.bhinneka.rebon.helper.CirclePageIndicator;
import id.bhinneka.rebon.model.DestinasiWisataModel;
import id.bhinneka.rebon.model.ListDestinasiWisataModel;
import id.bhinneka.rebon.utils.Constant;

/**
 * Created by bendet on 26/08/17.
 */

public class FragmentHome extends Fragment implements View.OnClickListener {
    private static final String TAG = FragmentHome.class.getSimpleName();

    private View parent;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private static ViewPager mPager;
    private Timer swipeTimer = null;
    private RecyclerView rvWisata;
    private WisataVerticalAdapter adapter;
    private CardView cvTiket, cvSewa, cvTracking, cvCaraPesan;
    private List<DestinasiWisataModel> data = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        parent = inflater.inflate(R.layout.fragment_home, container, false);

        rvWisata = parent.findViewById(R.id.recycler_lokasi_wisata);
        cvCaraPesan = parent.findViewById(R.id.cv_cara_pesan);
        cvSewa = parent.findViewById(R.id.cv_sewa);
        cvTracking = parent.findViewById(R.id.cv_track);
        cvTiket = parent.findViewById(R.id.cv_tiket);

        getData();

        adapter = new WisataVerticalAdapter(data);
        rvWisata.setHasFixedSize(true);
        rvWisata.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rvWisata.setAdapter(adapter);
        rvWisata.setNestedScrollingEnabled(false);

        cvTiket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().startActivity(new Intent(getContext(), JadwalActivity.class));
            }
        });
        cvTracking.setOnClickListener(this);
        cvSewa.setOnClickListener(this);

        return parent;
    }

    @Override
    public void onResume() {
        super.onResume();
        initImageSlide();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (swipeTimer != null) {
            swipeTimer.cancel();
        }
    }

    private void initImageSlide() {
        //ambil data gambar duluu
        final ArrayList<String> ls = new ArrayList<>();
        final StringRequest request = new StringRequest(StringRequest.Method.GET, Constant.GET_BANNER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e(TAG, "response " + response);
                        try {

                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                final JSONObject jsonObject = jsonArray.getJSONObject(i);
                                //tambain ke list
                                ls.add(i, jsonObject.get("image_url").toString());
                            }

                            mPager = parent.findViewById(R.id.viewPageAndroid);
                            mPager.setAdapter(new SlideImageAdapter(getActivity(), ls));
                            CirclePageIndicator indicator = parent.findViewById(R.id.indicator);
                            indicator.setViewPager(mPager);

                            Activity activity = getActivity();
                            if (activity != null && isAdded()) {
                                final float density = getResources().getDisplayMetrics().density;

                                //Set circle indicator radius
                                indicator.setRadius(3 * density);

                                NUM_PAGES = ls.size();

                                // Auto start of viewpager
                                final Handler handler = new Handler();
                                final Runnable Update = new Runnable() {
                                    public void run() {
                                        if (currentPage == NUM_PAGES) {
                                            currentPage = 0;
                                        }
                                        mPager.setCurrentItem(currentPage++, true);
                                    }
                                };

                                swipeTimer = new Timer();
                                swipeTimer.schedule(new TimerTask() {
                                    @Override
                                    public void run() {
                                        handler.post(Update);
                                    }
                                }, 3000, 3000);

                                // Pager listener over indicator
                                indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

                                    @Override
                                    public void onPageSelected(int position) {
                                        currentPage = position;
                                    }

                                    @Override
                                    public void onPageScrolled(int pos, float arg1, int arg2) {

                                    }

                                    @Override
                                    public void onPageScrollStateChanged(int pos) {

                                    }
                                });
                            }
                        } catch (JSONException ex) {
                            Log.e(TAG, "Json Exception :" + ex.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Volley Error :" + error.getMessage());
            }
        });
        AppController.getInstance(getActivity()).addToRequestQueue(request);
    }

    private void getData() {
        JsonArrayRequest request = new JsonArrayRequest
                (Constant.GET_DESTINASI_WISATA, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e(TAG, response.toString());
                        if (response.length() > 0) {
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject jsonObject = response.getJSONObject(i);
                                    JSONArray jsonArray = jsonObject.getJSONArray("wisata");
                                    DestinasiWisataModel model = new DestinasiWisataModel();
                                    List<ListDestinasiWisataModel> modelData = new ArrayList<>();
                                    Log.e(TAG, "Jenis wisata " + jsonObject.getString("kategori"));
                                    for (int a = 0; a < jsonArray.length(); a++) {
                                        ListDestinasiWisataModel modelDetail = new ListDestinasiWisataModel();
                                        JSONObject obj = jsonArray.getJSONObject(a);
                                        Log.e(TAG, "nama Destinasi " + obj.getString("nama_destinasi"));
                                        modelDetail.setId(obj.getInt("id"));
                                        modelDetail.setNamaDestinasi(obj.getString("nama_destinasi"));
                                        modelDetail.setUrl(obj.getString("image_url"));
                                        modelData.add(modelDetail);
                                    }
                                    model.setKategori(jsonObject.getString("kategori"));
                                    model.setDestinasiWisata(modelData);
                                    data.add(model);
                                } catch (JSONException e) {
                                    Log.e(TAG, "JSONException: " + e.getMessage());
                                }
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Volley Error : " + error.getMessage());
                    }
                });
        AppController.getInstance(getContext()).addToRequestQueue(request);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cv_sewa: {
                getActivity().startActivity(new Intent(getContext(), SewaActivity.class));
            }
            break;
            case R.id.cv_track: {
                getActivity().startActivity(new Intent(getContext(), TrackingActivity.class));
            }
        }
    }
}
