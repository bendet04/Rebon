package id.bhinneka.rebon.adpter;

import android.os.CountDownTimer;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import id.bhinneka.rebon.R;
import id.bhinneka.rebon.model.PesananModel;
import id.bhinneka.rebon.utils.DBHelper;
import id.bhinneka.rebon.utils.Utils;

/**
 * Created by bendet on 17/07/17.
 */

public class PesananAdapter extends RecyclerView.Adapter<PesananAdapter.ViewHolder> {

    private List<PesananModel> mData = new ArrayList<>();
    private String kodePesanan;

    public PesananAdapter(List<PesananModel> data) {
        mData = data;
    }

    @Override
    public PesananAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_pesanan, parent, false));
    }

    @Override
    public void onBindViewHolder(PesananAdapter.ViewHolder holder, int position) {
        holder.setData(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvKeberangkatan, tvAlamat, tvJam, tvTanggal, tvPemesan, tvKursi,
                tvPembayaran, tvSisaMenit, tvSisaJam, tvStatus;
        private Button btnBayar, btnCancel;
        private CountDownTimer countDownTimer;
        private DBHelper db;
        private LinearLayout layoutButton, layoutSisaWaktu;

        public ViewHolder(View itemView) {
            super(itemView);

            tvAlamat = itemView.findViewById(R.id.tv_alamat);
            tvKeberangkatan = itemView.findViewById(R.id.tv_keberangkatan);
            tvTanggal = itemView.findViewById(R.id.tv_tanggal);
            tvJam = itemView.findViewById(R.id.tv_jam);
            tvPemesan = itemView.findViewById(R.id.tv_pemesan);
            tvKursi = itemView.findViewById(R.id.tv_kursi);
            tvPembayaran = itemView.findViewById(R.id.tv_pembayaran);
            tvSisaJam = itemView.findViewById(R.id.tv_sisa_jam);
            tvSisaMenit = itemView.findViewById(R.id.tv_sisa_menit);
            tvStatus = itemView.findViewById(R.id.tv_status);

            btnBayar = itemView.findViewById(R.id.btn_bayar);
            btnCancel = itemView.findViewById(R.id.btn_cancel);

            layoutButton = itemView.findViewById(R.id.layout_button);
            layoutSisaWaktu = itemView.findViewById(R.id.layout_sisa_waktu);

            db = new DBHelper(itemView.getContext());
        }

        public void setData(PesananModel model) {

            tvJam.setText(model.getJam());
            tvAlamat.setText(model.getAlamat());
            tvTanggal.setText(model.getTanggal());
            tvKeberangkatan.setText(model.getKeberangkatan());
            tvPemesan.setText(model.getNama());
            tvKursi.setText(model.getKursi());
            tvPembayaran.setText(model.getPayment());
            tvStatus.setText(model.getStatusPayment());

            kodePesanan = model.getKode();

            if (!model.getStatusPesanan().equalsIgnoreCase("cancel")){
                long exp = Utils.selisihJam(model.getPaymentExpired());
                if (exp > 0){
                    layoutSisaWaktu.setVisibility(View.VISIBLE);
                    layoutButton.setVisibility(View.VISIBLE);
                    countDownTimer = new SisaWaktu(exp, 1 * 1000);
                    countDownTimer.start();
                }else {
                    db.deleteContact(kodePesanan);
                    tvSisaJam.setText("Expired");
                    layoutSisaWaktu.setVisibility(View.INVISIBLE);
                    layoutButton.setVisibility(View.INVISIBLE);
                }
            }
        }

        class SisaWaktu extends CountDownTimer {

            public SisaWaktu(long millisInFuture, long countDownInterval) {
                super(millisInFuture, countDownInterval);
            }

            @Override
            public void onTick(long l) {
                tvSisaJam.setText(String.valueOf(l / (60 * 60 * 1000) % 24));
                tvSisaMenit.setText(String.valueOf(l/ (60 * 1000) % 60));
            }

            @Override
            public void onFinish() {

            }
        }
    }
}
