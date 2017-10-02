package id.bhinneka.rebon.adpter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import id.bhinneka.rebon.R;
import id.bhinneka.rebon.activity.DetailWisataActivity;
import id.bhinneka.rebon.model.ListDestinasiWisataModel;

/**
 * Created by bendet on 9/9/17.
 */

public class WisataHorizontalAdapter extends RecyclerView.Adapter<WisataHorizontalAdapter.MyHolder> {

    private List<ListDestinasiWisataModel> data;

    public void setData (List<ListDestinasiWisataModel> data){
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_destinasi_wisata_detail, parent, false));
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        ListDestinasiWisataModel model = data.get(position);
        holder.setData(model);
        holder.itemView.setTag(model);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        private ImageView ivWisata;
        private TextView tvWisata;
        private  ListDestinasiWisataModel model;

        public MyHolder(final View itemView) {
            super(itemView);
            ivWisata = itemView.findViewById(R.id.iv_lokasi_wisata);
            tvWisata = itemView.findViewById(R.id.tv_nama_wisata);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), DetailWisataActivity.class);
                    intent.putExtra("id", model.getId());
                    intent.putExtra("namaDestinasi",model.getNamaDestinasi());
                    view.getContext().startActivity(intent);
                }
            });
        }

        public void setData(ListDestinasiWisataModel model){
            this.model = model;
            tvWisata.setText(model.getNamaDestinasi());
        }
    }
}
