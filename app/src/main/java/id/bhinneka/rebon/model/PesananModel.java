package id.bhinneka.rebon.model;

/**
 * Created by bendet on 29/08/17.
 */

public class PesananModel {
    private String kode, nama, nohp, kursi, totalHarga, keberangkatan, jumlah_kursi, 
            lunas, jam, alamat, idPayment, tanggal, statusPesanan, payment, paymentExpired, statusPayment;

    public PesananModel() {
    }

    public PesananModel(String kode, String nama, String nohp, String kursi, String totalHarga, 
                        String keberangkatan, String jumlah_kursi, String lunas, String jam, 
                        String alamat, String idPayment, String tanggal, String statusPesanan, String payment,
                        String paymentExpired, String statusPayment) {
        this.kode = kode;
        this.nama = nama;
        this.nohp = nohp;
        this.kursi = kursi;
        this.totalHarga = totalHarga;
        this.keberangkatan = keberangkatan;
        this.jumlah_kursi = jumlah_kursi;
        this.lunas = lunas;
        this.jam = jam;
        this.alamat = alamat;
        this.idPayment = idPayment;
        this.tanggal = tanggal;
        this.statusPesanan = statusPesanan;
        this.payment = payment;
        this.paymentExpired = paymentExpired;
        this.statusPayment = statusPayment;
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNohp() {
        return nohp;
    }

    public void setNohp(String nohp) {
        this.nohp = nohp;
    }

    public String getKursi() {
        return kursi;
    }

    public void setKursi(String kursi) {
        this.kursi = kursi;
    }

    public String getTotalHarga() {
        return totalHarga;
    }

    public void setTotalHarga(String totalHarga) {
        this.totalHarga = totalHarga;
    }

    public String getKeberangkatan() {
        return keberangkatan;
    }

    public void setKeberangkatan(String keberangkatan) {
        this.keberangkatan = keberangkatan;
    }

    public String getJumlah_kursi() {
        return jumlah_kursi;
    }

    public void setJumlah_kursi(String jumlah_kursi) {
        this.jumlah_kursi = jumlah_kursi;
    }

    public String getLunas() {
        return lunas;
    }

    public void setLunas(String lunas) {
        this.lunas = lunas;
    }

    public String getJam() {
        return jam;
    }

    public void setJam(String jam) {
        this.jam = jam;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getIdPayment() {
        return idPayment;
    }

    public void setIdPayment(String idPayment) {
        this.idPayment = idPayment;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getStatusPesanan() {
        return statusPesanan;
    }

    public void setStatusPesanan(String statusPesanan) {
        this.statusPesanan = statusPesanan;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getPaymentExpired() {
        return paymentExpired;
    }

    public void setPaymentExpired(String paymentExpired) {
        this.paymentExpired = paymentExpired;
    }

    public String getStatusPayment() {
        return statusPayment;
    }

    public void setStatusPayment(String statusPayment) {
        this.statusPayment = statusPayment;
    }
}
