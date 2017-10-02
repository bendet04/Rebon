package id.bhinneka.rebon.adpter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import id.bhinneka.rebon.R;
import id.bhinneka.rebon.model.DestinasiWisataModel;

/**
 * Created by bendet on 9/9/17.
 */

public class WisataVerticalAdapter extends RecyclerView.Adapter<WisataVerticalAdapter.MyHolder> {
    private static final String TAG = WisataVerticalAdapter.class.getSimpleName();
    private List<DestinasiWisataModel> data;

    public WisataVerticalAdapter(List<DestinasiWisataModel> data) {
        this.data = data;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_destinasi_wisata, parent, false));
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        holder.tvKategoriWisata.setText(data.get(position).getKategori());
        holder.adapter.setData(data.get(position).getDestinasiWisata());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class MyHolder extends RecyclerView.ViewHolder {
        RecyclerView rvListWisata;
        WisataHorizontalAdapter adapter;
        TextView tvKategoriWisata;

        public MyHolder(final View itemView) {
            super(itemView);
            rvListWisata = itemView.findViewById(R.id.rvListWisata);
            tvKategoriWisata = itemView.findViewById(R.id.tv_kategori_wisata);
            adapter = new WisataHorizontalAdapter();
            rvListWisata.setAdapter(adapter);
            rvListWisata.setHasFixedSize(true);
            rvListWisata.setLayoutManager(new LinearLayoutManager(itemView.getContext(),
                    LinearLayoutManager.HORIZONTAL, false));
            rvListWisata.setNestedScrollingEnabled(false);
        }
    }
}
