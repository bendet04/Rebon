package id.bhinneka.rebon.adpter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;

import id.bhinneka.rebon.R;
import id.bhinneka.rebon.app.AppController;
import id.bhinneka.rebon.fragment.SlideShowDialogFragement;

/**
 * Created by adi on 18/12/16.
 */

public class SlideImageAdapter extends PagerAdapter {
    public static final String TAG = SlideImageAdapter.class.getSimpleName();

    private Context mContext;
    private ArrayList<String> IMAGES;
    private LayoutInflater inflater;

    public SlideImageAdapter(Context context, ArrayList<String> IMAGES) {
        this.mContext = context;
        this.IMAGES = IMAGES;
        if (context != null)
            inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public int getCount() {
        if (IMAGES != null) {
            return IMAGES.size();
        }
        return 0;
    }

    @Override
    public View instantiateItem(ViewGroup view, final int position) {
        View imageLayout = inflater.inflate(R.layout.sliding_images_layout, view, false);
        NetworkImageView imageView = imageLayout.findViewById(R.id.image);

        ImageLoader imageLoader = AppController.getInstance(mContext).getImageLoader();
        imageLoader.get(IMAGES.get(position), ImageLoader.getImageListener(imageView,
                R.drawable.ic_list_gray, R.drawable.ic_list_green));
        imageView.setImageUrl(IMAGES.get(position), imageLoader);

        view.addView(imageLayout, 0);
        imageLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(TAG, "pos " + IMAGES.get(position));
                Intent intent = new Intent(mContext, SlideShowDialogFragement.class);
                intent.putStringArrayListExtra("images", IMAGES);
                mContext.startActivity(intent);
            }
        });
        // Log.e(TAG, "banner :"+IMAGES.get(position));
        return imageLayout;
    }
}
