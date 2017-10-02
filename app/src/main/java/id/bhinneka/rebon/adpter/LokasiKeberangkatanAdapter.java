package id.bhinneka.rebon.adpter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import id.bhinneka.rebon.R;
import id.bhinneka.rebon.model.LokasiKeberangkatanModel;

/**
 * Created by bendet on 13/07/17.
 */

public class LokasiKeberangkatanAdapter extends RecyclerView.Adapter<LokasiKeberangkatanAdapter.ViewHolder> {

    private List<LokasiKeberangkatanModel> mDataSet = new ArrayList<>();
    private ItemListener mItemListener;

    public LokasiKeberangkatanAdapter() {
    }

    public LokasiKeberangkatanAdapter(List<LokasiKeberangkatanModel> models, ItemListener item) {
        mDataSet = models;
        mItemListener = item;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private LinearLayout layoutKeberangkatan;
        private TextView tvLokasiKeberangkatan, tvAlamatKeberangkatan;
        private ImageView imgMapsLokasiKeberangkatan;
        private Context mContext;
        private String lokasiKeberangkatan;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            layoutKeberangkatan = itemView.findViewById(R.id.layout_keberangakatan);
            tvLokasiKeberangkatan = itemView.findViewById(R.id.tv_lokasi_keberangkatan);
            tvAlamatKeberangkatan = itemView.findViewById(R.id.tv_alamat_keberangkatan);
            imgMapsLokasiKeberangkatan = itemView.findViewById(R.id.img_maps_keberangkatan);

            mContext = itemView.getContext();
        }

        public void setData(LokasiKeberangkatanModel model) {

            final double lat = model.getLat();
            final double lng = model.getLng();

            tvLokasiKeberangkatan.setText(model.getNamaLokasi());
            tvAlamatKeberangkatan.setText(model.getAlamatLokasi());
            imgMapsLokasiKeberangkatan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Uri gmmIntentUri = Uri.parse("geo:" + lat + "," + lng);
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    if (mapIntent.resolveActivity(mContext.getPackageManager()) != null) {
                        mContext.startActivity(mapIntent);
                    }
                }
            });

            lokasiKeberangkatan = model.getNamaLokasi();
        }

        @Override
        public void onClick(View view) {
            if (mItemListener != null) {
                mItemListener.onItemClick(lokasiKeberangkatan);
            }
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_keberangkatan, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(mDataSet.get(position));
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public void update(List<LokasiKeberangkatanModel> list) {
        this.mDataSet = list;
        notifyDataSetChanged();
    }

    public interface ItemListener {
        void onItemClick(String lokasiKeberangkatan);
    }
}
