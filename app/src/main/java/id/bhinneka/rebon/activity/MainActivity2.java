package id.bhinneka.rebon.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import id.bhinneka.rebon.R;
import id.bhinneka.rebon.fragment.FragmentAbout;
import id.bhinneka.rebon.fragment.FragmentHome;
import id.bhinneka.rebon.fragment.FragmentPesanan;
import id.bhinneka.rebon.utils.Utils;

public class MainActivity2 extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        toolbar =  findViewById(R.id.main_toolbar);
        toolbar.setTitle("Tiket");
        setSupportActionBar(toolbar);

        FragmentHome homeFragment = new FragmentHome();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content, homeFragment);
        fragmentTransaction.commit();

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Utils.systemBarLolipop(this);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    changeFragment(0);
                    break;
                case R.id.navigation_pesanan:
                    changeFragment(1);
                    break;
                case R.id.navigation_about:
                    changeFragment(2);
                    break;
            }
            return true;
        }

    };

    private void changeFragment(int position) {

        Fragment newFragment = null;

        switch (position) {
            case 0:
                newFragment = new FragmentHome();
                break;
            case 1:
                newFragment = new FragmentPesanan();
                break;
            case 2:
               newFragment = new FragmentAbout();
                break;
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.content, newFragment)
                .commit();
    }
}
