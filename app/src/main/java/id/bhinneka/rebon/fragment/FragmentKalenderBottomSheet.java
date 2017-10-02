package id.bhinneka.rebon.fragment;

import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;

import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.Calendar;

import id.bhinneka.rebon.R;

/**
 * Created by bendet on 16/07/17.
 */

public class FragmentKalenderBottomSheet extends BottomSheetDialogFragment {

    BottomSheetBehavior mBehavior;
    MaterialCalendarView mKalender;

    @Override
    public void setupDialog(final Dialog dialog, int style) {
        super.setupDialog(dialog, style);

        final View contentView = View.inflate(getContext(), R.layout.fragment_kalender_bottomsheet, null);
        dialog.setContentView(contentView);
        dialog.setCanceledOnTouchOutside(false);

        mKalender = contentView.findViewById(R.id.calendarView);
        Calendar cal = Calendar.getInstance();
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams)
                ((View) contentView.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();

        if (behavior != null && behavior instanceof BottomSheetBehavior) {
            mBehavior = (BottomSheetBehavior) behavior;
            mBehavior.setBottomSheetCallback(mBottomSheetBehaviorCallback);

            // biar max screen nya
            contentView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    contentView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    int height = contentView.getMeasuredHeight();
                    mBehavior.setPeekHeight(height);
                }
            });
        }
    }

    private BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback =
            new BottomSheetBehavior.BottomSheetCallback() {

                @Override
                public void onStateChanged(@NonNull View bottomSheet, int newState) {
                    switch (newState) {
                        case BottomSheetBehavior.STATE_HIDDEN: {
                            dismiss();
                        }
                    }
                }

                @Override
                public void onSlide(@NonNull View bottomSheet, float slideOffset) {

                    Log.d("BSB", "sliding " + slideOffset);
                }
            };
}
