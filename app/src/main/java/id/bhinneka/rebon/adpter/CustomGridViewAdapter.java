package id.bhinneka.rebon.adpter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import id.bhinneka.rebon.R;
import id.bhinneka.rebon.model.KursiModel;

public class CustomGridViewAdapter extends ArrayAdapter<KursiModel> {
    private static final String TAG = CustomGridViewAdapter.class.getSimpleName();

    private Context context;
    private int layoutResourceId;
    private ArrayList<KursiModel> data = new ArrayList<>();

    public CustomGridViewAdapter(@NonNull Context context, @LayoutRes int resource,
                                 ArrayList<KursiModel> data) {
        super(context, resource, data);
        this.layoutResourceId = resource;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertVeiw, ViewGroup parent) {
        View row = convertVeiw;
        RecordHolder holder;

        try {
            if (row == null) {
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                row = inflater.inflate(layoutResourceId, parent, false);

                holder = new RecordHolder();
                holder.tvTitle = row.findViewById(R.id.item_text);
                holder.imageView = row.findViewById(R.id.item_image);

                row.setTag(holder);
            } else {
                holder = (RecordHolder) row.getTag();
            }

            KursiModel model = data.get(position);
            holder.tvTitle.setText(model.getTitle());
            holder.imageView.setImageBitmap(model.getImage());

        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        return row;
    }

    private static class RecordHolder {
        public TextView tvTitle;
        public ImageView imageView;
    }
}