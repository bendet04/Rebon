package id.bhinneka.rebon.adpter;

import android.content.Intent;
import android.net.http.SslError;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import id.bhinneka.rebon.R;
import id.bhinneka.rebon.activity.PilihPaymentActivity;
import id.bhinneka.rebon.model.PaymentModel;
import id.bhinneka.rebon.utils.ExpandableLayout;


/**
 * Created by bendet on 02/08/17.
 */
public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.ViewHolder> {
    private static final String TAG = PaymentAdapter.class.getSimpleName();

    private List<PaymentModel> data = new ArrayList<>();
    private PilihPaymentActivity activity;

    public PaymentAdapter(List<PaymentModel> data, PilihPaymentActivity activity) {
        this.data = data;
        this.activity = activity;
    }

    @Override
    public PaymentAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_payment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PaymentAdapter.ViewHolder holder, int position) {
        holder.setData(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvPayment;
        private ImageView imgPayment;
        private ExpandableLayout expandableLayout;
        private RelativeLayout expand;
        private WebView webView;
        private Button btnPilih;

        public ViewHolder(View itemView) {
            super(itemView);
            expandableLayout = itemView.findViewById(R.id.expandable_layout);
            tvPayment = itemView.findViewById(R.id.tv_payment);
            imgPayment = itemView.findViewById(R.id.img_payment);
            expand = itemView.findViewById(R.id.expandable);
            webView = itemView.findViewById(R.id.webview_how_to_payment);
            btnPilih = itemView.findViewById(R.id.btn_piiih_payment);
        }

        public void setData(final PaymentModel model) {
            tvPayment.setText(model.getPaymentType());
            expandableLayout.setExpanded(model.isExpanded, false);
            webView.loadData(model.getHowToPayment(), "text/html; charset=utf-8", "UTF-8");
            webView.getSettings().setJavaScriptEnabled(false);
            webView.setWebViewClient(new WebViewClient(){
                @Override
                public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                    handler.proceed();
                }
            });
            btnPilih.setText("pilih " + model.getPaymentType());
            btnPilih.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("id", model.getId());
                    resultIntent.putExtra("pembayaran", model.getPaymentType());
                    activity.setResult(activity.RESULT_OK, resultIntent);
                    activity.finish();
                }
            });
            expand.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean result = expandableLayout.toggleExpansion();
                    PaymentModel model = data.get(getAdapterPosition());
                    model.isExpanded = result ? !model.isExpanded : model.isExpanded;
                }
            });
            expand.setTag(this);
            expandableLayout.setTag(this);
            expandableLayout.setOnExpandListener(mOnExpandListener);
        }
    }

    public ExpandableLayout.OnExpandListener mOnExpandListener = new ExpandableLayout.OnExpandListener() {

        private boolean isScrollingToBottom = false;

        @Deprecated
        @Override
        public void onToggle(ExpandableLayout view, View child, boolean isExpanded) {
        }

        @Override
        public void onExpandOffset(ExpandableLayout view, View child, float offset,
                                   boolean isExpanding) {
            if (view.getTag() instanceof PaymentAdapter.ViewHolder) {
                final PaymentAdapter.ViewHolder holder = (PaymentAdapter.ViewHolder) view.getTag();
                if (holder.getAdapterPosition() == data.size() - 1) {
                    if (!isScrollingToBottom) {
                        isScrollingToBottom = true;
                        activity.paymentRecyclerView.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                isScrollingToBottom = false;
                                activity.paymentRecyclerView.scrollToPosition(holder.getAdapterPosition());
                            }
                        }, 100);
                    }
                }
            }
        }
    };

}
