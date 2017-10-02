package id.bhinneka.rebon.model;

/**
 * Created by bendet on 17/07/17.
 */

public class JadwalModel {

    private String jam, lokasiKeberangkatan, alamat, harga, hargaApps, tanggal, kode;
    private int sisaKursi;

    public JadwalModel(){

    }
    public JadwalModel(String jam,  String lokasiKeberangkatan, String alamat, String harga,
                       String hargaApps, String tanggal, String kode, int sisaKursi) {
        this.jam = jam;
        this.lokasiKeberangkatan = lokasiKeberangkatan;
        this.harga = harga;
        this.hargaApps = hargaApps;
        this.tanggal = tanggal;
        this.kode = kode;
        this.sisaKursi = sisaKursi;
        this.alamat = alamat;
    }

    public String getJam() {
        return jam;
    }

    public void setJam(String jam) {
        this.jam = jam;
    }

    public String getLokasiKeberangkatan() {
        return lokasiKeberangkatan;
    }

    public void setLokasiKeberangkatan(String lokasiKeberangkatan) {
        this.lokasiKeberangkatan = lokasiKeberangkatan;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getHarga() {
        return harga;
    }

    public String getHargaApps() {
        return hargaApps;
    }

    public void setHargaApps(String hargaApps) {
        this.hargaApps = hargaApps;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public int getSisaKursi() {
        return sisaKursi;
    }

    public void setSisaKursi(int sisaKursi) {
        this.sisaKursi = sisaKursi;
    }
}
