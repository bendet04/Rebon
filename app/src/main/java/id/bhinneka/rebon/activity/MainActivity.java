package id.bhinneka.rebon.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import id.bhinneka.rebon.R;
import id.bhinneka.rebon.adpter.ViewPagerAdapter;
import id.bhinneka.rebon.fragment.FragmentPesanan;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        toolbar.setTitle("Tiket");
        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0 : toolbar.setTitle("Tiket");
                        break;
                    case 1 : toolbar.setTitle("Pesanan");
                        break;
                    case 2 : toolbar.setTitle("Galery");
                        break;
                    case 3 : toolbar.setTitle("User");
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        setupViewPager(viewPager);

        tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        setupTab(tabLayout);

        if (getIntent() != null){
            if (getIntent().getAction().equals("openTabPesanan")){
                viewPager.setCurrentItem(1, true);
            }
        }
    }

    private void setupViewPager(ViewPager viewPager){
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        //adapter.addFragment(new TrackingActivity(), "Tiket");
        adapter.addFragment(new FragmentPesanan(), "Pesanan");
       // adapter.addFragment(new FragmentGaleri(),"Galeri");
        //adapter.addFragment(new SewaActivity(), "User");
        viewPager.setAdapter(adapter);
    }

    private void setupTab(TabLayout tabLayout){
        tabLayout.getTabAt(0).setIcon(R.drawable.tab_tiket);
        tabLayout.getTabAt(1).setIcon(R.drawable.tab_pesanan);
        tabLayout.getTabAt(2).setIcon(R.drawable.tab_galeri);
        tabLayout.getTabAt(3).setIcon(R.drawable.tab_user);
        tabLayout.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),
                R.color.tab_background));
        tabLayout.setTabTextColors(ContextCompat.getColor(getApplicationContext(), R.color.divider),
                ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
    }
}
