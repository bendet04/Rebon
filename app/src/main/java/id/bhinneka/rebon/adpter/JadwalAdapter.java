package id.bhinneka.rebon.adpter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import id.bhinneka.rebon.R;
import id.bhinneka.rebon.model.JadwalModel;
import id.bhinneka.rebon.utils.Utils;

/**
 * Created by bendet on 17/07/17.
 */

public class JadwalAdapter extends RecyclerView.Adapter<JadwalAdapter.ViewHolder> {

    private List<JadwalModel> mData = new ArrayList<>();

    public JadwalAdapter(List<JadwalModel> data) {
        mData = data;
    }

    @Override
    public JadwalAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_jadwal, parent, false));
    }

    @Override
    public void onBindViewHolder(JadwalAdapter.ViewHolder holder, int position) {
        holder.setData(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvKeberangkatan, tvAlamat, tvHarga, tvHargaApps, tvJam, tvTanggal,
                tvSisaKursi;


        public ViewHolder(View itemView) {
            super(itemView);

            tvAlamat = itemView.findViewById(R.id.tv_alamat);
            tvHarga = itemView.findViewById(R.id.tv_harga);
            tvKeberangkatan = itemView.findViewById(R.id.tv_keberangkatan);
            tvTanggal = itemView.findViewById(R.id.tv_tanggal);
            tvJam = itemView.findViewById(R.id.tv_jam);
            tvSisaKursi = itemView.findViewById(R.id.tv_sisa_kursi);
        }

        public void setData(JadwalModel model) {
            tvJam.setText(model.getJam());
            tvAlamat.setText(model.getAlamat());
            tvHarga.setText("Rp. " + Utils.rupiah(Integer.parseInt(model.getHarga())));
            tvTanggal.setText(model.getTanggal());
            tvKeberangkatan.setText(model.getLokasiKeberangkatan());

            if (model.getSisaKursi() > 0)
                tvSisaKursi.setText("TERSEDIA");
            else
                tvSisaKursi.setText("PENUH");
        }
    }
}
