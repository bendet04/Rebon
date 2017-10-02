package id.bhinneka.rebon.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import id.bhinneka.rebon.R;
import id.bhinneka.rebon.adpter.LokasiKeberangkatanAdapter;
import id.bhinneka.rebon.model.LokasiKeberangkatanModel;

/**
 * Created by farhad on 1/13/17.
 */

public class FragmentKeberangkatanBottomSheet extends BottomSheetDialogFragment {

    private RecyclerView mRecyclerViewKeberangkatan;
    private LokasiKeberangkatanAdapter lokasiKeberangkatanAdapter;
    private LinearLayoutManager linearLayoutManager;

    @Override
    public void setupDialog(final Dialog dialog, int style) {

        super.setupDialog(dialog, style);

        View contentView = View.inflate(getContext(), R.layout.fragment_keberangkatan_bottomsheet, null);

        List<LokasiKeberangkatanModel> models = new ArrayList<>();
        models.add(new LokasiKeberangkatanModel(
                "Pool Bhinneka Cirebon", "Jl. Pilang Raya Cirebon", 10000, 10000));
        models.add(new LokasiKeberangkatanModel(
                "Pool Bhinneka Bandung", "Jl. Pilang Raya Cirebon", 10000, 10000));
        models.add(new LokasiKeberangkatanModel(
                "Pool Bhinneka Karawang", "Jl. Pilang Raya Cirebon", 10000, 10000));

        lokasiKeberangkatanAdapter = new LokasiKeberangkatanAdapter(models, new LokasiKeberangkatanAdapter.ItemListener() {
            @Override
            public void onItemClick(String lokasiKeberangkatan) {
                Intent intent = new Intent("keberangkatan");
                intent.putExtra("lokasiKeberangaktan", lokasiKeberangkatan);
                LocalBroadcastManager.getInstance(getActivity().getApplicationContext())
                        .sendBroadcast(intent);
                dialog.dismiss();
            }
        });

        mRecyclerViewKeberangkatan =  contentView.findViewById(R.id.recyclerview_keberangkatan);
        linearLayoutManager = new LinearLayoutManager(contentView.getContext());
        mRecyclerViewKeberangkatan.setLayoutManager(linearLayoutManager);
        mRecyclerViewKeberangkatan.setAdapter(lokasiKeberangkatanAdapter);

        dialog.setContentView(contentView);
        dialog.setCanceledOnTouchOutside(false);
/*
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams)
                ((View) contentView.getParent()).getLayoutParams();

        CoordinatorLayout.Behavior behavior = params.getBehavior();

        if (behavior != null && behavior instanceof BottomSheetBehavior) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(mBottomSheetBehaviorCallback);
        }
        */
    }


    private BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback =
            new BottomSheetBehavior.BottomSheetCallback() {

                @Override
                public void onStateChanged(@NonNull View bottomSheet, int newState) {
                    switch (newState) {
                        case BottomSheetBehavior.STATE_HIDDEN: {
                            Log.d("BSB", "hidden");
                            dismiss();
                        }
                    }
                }

                @Override
                public void onSlide(@NonNull View bottomSheet, float slideOffset) {

                    Log.d("BSB", "sliding " + slideOffset);
                }
            };

    // passed back nama lokasi ke activty CariTiket
    public interface KebrangkatanListener{
        void onFinishClickItem(String keberangkatan);
    }
}
