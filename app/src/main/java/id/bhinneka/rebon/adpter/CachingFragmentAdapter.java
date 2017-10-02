package id.bhinneka.rebon.adpter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.util.SparseArray;
import android.view.ViewGroup;

import java.util.Calendar;

import id.bhinneka.rebon.fragment.FragmentJadwal;
import id.bhinneka.rebon.utils.Utils;

/**
 * Created by bendet on 03/08/17.
 */

public class CachingFragmentAdapter extends FragmentStatePagerAdapter {
    private static final String TAG = CachingFragmentAdapter.class.getSimpleName();

    private SparseArray<Fragment> registeredFragment = new SparseArray<>();
    private Context context;

    public CachingFragmentAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public int getCount(){
        return Utils.DAY_OF_TIME;
    }

    @Override
    public Fragment getItem(int position){
        long timeForPosition = Utils.getDayForPosition(position).getTimeInMillis();
        Log.e(TAG, "position "+position+" long time :"+timeForPosition);
        return FragmentJadwal.newInstance(timeForPosition);
    }

    @Override
    public CharSequence getPageTitle(int position){
        Calendar cal = Utils.getDayForPosition(position);
        return Utils.getFormattedDateIndo(context, cal.getTimeInMillis());
    }

    //registrasi fragment ketika item di diinisialisai
    @Override
    public Object instantiateItem(ViewGroup container, int positon){
        Fragment fragment = (Fragment) super.instantiateItem(container, positon);
        registeredFragment.put(positon, fragment);
        return fragment;
    }

    //unregistered ketika item tidak aktif
    public void destroyItem(ViewGroup container, int position, Object object){
        registeredFragment.remove(position);
        super.destroyItem(container, position, object);
    }

    public Fragment getRegisteredFragment(int position){
        return registeredFragment.get(position);
    }

}
