package id.bhinneka.rebon.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import id.bhinneka.rebon.R;
import id.bhinneka.rebon.adpter.PaymentAdapter;
import id.bhinneka.rebon.app.AppController;
import id.bhinneka.rebon.fragment.loading.ProgressFrameLayout;
import id.bhinneka.rebon.model.PaymentModel;
import id.bhinneka.rebon.utils.Constant;
import id.bhinneka.rebon.utils.DividerItemDecoration;

public class PilihPaymentActivity extends AppCompatActivity {
    private static final String TAG = PilihPaymentActivity.class.getSimpleName();
    public RecyclerView paymentRecyclerView;
    private PaymentAdapter adapter;
    private ArrayList<PaymentModel> data = new ArrayList<>();
    private ProgressFrameLayout progressFrameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilih_payment);

        progressFrameLayout = (ProgressFrameLayout) findViewById(R.id.progress);
        progressFrameLayout.showLoading();
        getData();

        paymentRecyclerView = (RecyclerView) findViewById(R.id.recylerview_payment);
        adapter = new PaymentAdapter(data, this);

        paymentRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        paymentRecyclerView.setHasFixedSize(true);
        paymentRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        paymentRecyclerView.setAdapter(adapter);
    }

    private void getData(){
        JsonArrayRequest request = new JsonArrayRequest(
                Constant.GET_PAYMENT_MASTER, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.v(TAG, response.toString());
                progressFrameLayout.showContent();
                for (int i = 0; i < response.length(); i++){
                    try{
                        JSONObject jsonObject = response.getJSONObject(i);
                        PaymentModel model = new PaymentModel();
                        model.setHowToPayment(jsonObject.getString("how_to_payment"));
                        model.setId(jsonObject.getString("id"));
                        model.setImgUrl(jsonObject.getString("logo"));
                        model.setKet(jsonObject.getString("keterangan"));
                        model.setPaymentType(jsonObject.getString("payment_type"));
                        model.setRekVirtual(jsonObject.getString("rek_virtual"));
                        data.add(model);
                    }catch (JSONException e){
                        Log.e(TAG, "Json Parsing error : "+e.getMessage());
                        progressFrameLayout.showError(R.drawable.ic_dialog_close_light,
                                "Terjadi Kesalahan",
                                "Koneksi Error",
                                "Coba Lagi",
                                errorClickListener);
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Volley Error : "+error.getMessage());
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
            getData();
        }
    };

}
