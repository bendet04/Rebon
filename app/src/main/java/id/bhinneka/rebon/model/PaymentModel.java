package id.bhinneka.rebon.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by bendet on 02/08/17.
 */

public class PaymentModel{

    public boolean isExpanded;
    private String pembayaran, rekVirtual, paymentUrl, paymentType, ket,  imgUrl, id, howToPayment;

    public PaymentModel(){

    }

    public PaymentModel(String pembayaran, String rekVirtual, String imgUrl, String paymentType, String paymentUrl, String howToPayment) {
        this.pembayaran = pembayaran;
        this.imgUrl = imgUrl;
        this.paymentUrl = paymentUrl;
        this.paymentType = paymentType;
        this.howToPayment = howToPayment;
        this.rekVirtual = rekVirtual;
    }

    public String getPembayaran() {
        return pembayaran;
    }

    public void setPembayaran(String pembayaran) {
        this.pembayaran = pembayaran;
    }

    public String getRekVirtual() {
        return rekVirtual;
    }

    public void setRekVirtual(String rekVirtual) {
        this.rekVirtual = rekVirtual;
    }

    public String getPaymentUrl() {
        return paymentUrl;
    }

    public void setPaymentUrl(String paymentUrl) {
        this.paymentUrl = paymentUrl;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getKet() {
        return ket;
    }

    public void setKet(String ket) {
        this.ket = ket;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHowToPayment() {
        return howToPayment;
    }

    public void setHowToPayment(String howToPayment) {
        this.howToPayment = howToPayment;
    }
}
