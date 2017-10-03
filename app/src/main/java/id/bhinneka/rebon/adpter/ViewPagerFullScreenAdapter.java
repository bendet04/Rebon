package id.bhinneka.rebon.adpter;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.ArrayList;

/**
 * Created by bendet on 9/13/17.
 */

public class ViewPagerFullScreenAdapter extends PagerAdapter{
    private Activity activity;
    private ArrayList<String> imagePaths;
    private LayoutInflater inflater;

    // constructor
    public ViewPagerFullScreenAdapter(Activity activity,
                                  ArrayList<String> imagePaths) {
        this.activity = activity;
        this.imagePaths = imagePaths;
    }

    @Override
    public int getCount() {
        return this.imagePaths.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ( container).removeView((RelativeLayout) object);

    }
}
