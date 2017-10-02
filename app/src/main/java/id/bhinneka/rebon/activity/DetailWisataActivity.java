package id.bhinneka.rebon.activity;

import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import id.bhinneka.rebon.R;
import id.bhinneka.rebon.adpter.SlideImageAdapter;
import id.bhinneka.rebon.app.AppController;
import id.bhinneka.rebon.helper.CirclePageIndicator;
import id.bhinneka.rebon.utils.Constant;
import id.bhinneka.rebon.utils.Utils;

public class DetailWisataActivity extends AppCompatActivity {
    private static final String TAG = DetailWisataActivity.class.getSimpleName();

    private CollapsingToolbarLayout collapsingToolbarLayout;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private static ViewPager mPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActivityTransitions();
        setContentView(R.layout.activity_detail_wisata);

        setSupportActionBar((Toolbar) findViewById(R.id.wisata_toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ViewCompat.setTransitionName(findViewById(R.id.app_bar_layout), "extraImage");
        collapsingToolbarLayout = findViewById(R.id.colapsing_toolbar);
        collapsingToolbarLayout.setTitle("Adi");
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));
        initImageSlide();
        Utils.systemBarLolipop(this);
    }

    private void initActivityTransitions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Slide transition = new Slide();
            transition.excludeTarget(android.R.id.statusBarBackground, true);
            getWindow().setEnterTransition(transition);
            getWindow().setReturnTransition(transition);
        }
    }

    private void applyPalette(Palette palette) {
        int primaryDark = getResources().getColor(R.color.colorPrimaryDark);
        int primary = getResources().getColor(R.color.colorPrimary);
        collapsingToolbarLayout.setContentScrimColor(palette.getMutedColor(primary));
        collapsingToolbarLayout.setStatusBarScrimColor(palette.getDarkMutedColor(primaryDark));
        supportStartPostponedEnterTransition();
    }

    private void updateBackground(FloatingActionButton fab, Palette palette) {
        int lightVibrantColor = palette.getLightVibrantColor(getResources().getColor(android.R.color.white));
        int vibrantColor = palette.getVibrantColor(getResources().getColor(R.color.colorAccent));

        fab.setRippleColor(lightVibrantColor);
        fab.setBackgroundTintList(ColorStateList.valueOf(vibrantColor));
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

                            mPager = findViewById(R.id.viewPageAndroid);
                            mPager.setAdapter(new SlideImageAdapter(DetailWisataActivity.this, ls));
                            CirclePageIndicator indicator = findViewById(R.id.indicator);
                            indicator.setViewPager(mPager);

                            final float density = getResources().getDisplayMetrics().density;

                            //Set circle indicator radius
                            indicator.setRadius(3 * density);
                            NUM_PAGES = ls.size();

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
        AppController.getInstance(this).addToRequestQueue(request);
    }
}
