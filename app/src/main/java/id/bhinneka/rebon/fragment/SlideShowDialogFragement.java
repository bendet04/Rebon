package id.bhinneka.rebon.fragment;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.ArrayList;

import id.bhinneka.rebon.R;
import id.bhinneka.rebon.adpter.SlideImageAdapter;

/**
 * Created by bendet on 9/13/17.
 */

public class SlideShowDialogFragement extends AppCompatActivity{
    private static final String TAG = SlideShowDialogFragement.class.getSimpleName();
    private ArrayList<String> images = new ArrayList<>();
    private ViewPager viewPager;
    private TextView tvCount, tvTitle, tvDescription;
    private SlideImageAdapter adapeter;

    public void onCreate(Bundle savedStateInstance) {
        super.onCreate(savedStateInstance);
        setContentView(R.layout.dialog_fragment_slide_show);
        if (getIntent() != null)
            images = getIntent().getStringArrayListExtra("images");

        viewPager = findViewById(R.id.view_pager);
        tvCount = findViewById(R.id.tv_count);
        tvDescription = findViewById(R.id.tv_description);
        tvTitle = findViewById(R.id.tv_title);
        adapeter = new SlideImageAdapter(this, images);
        viewPager.setAdapter(adapeter);
    }

}
