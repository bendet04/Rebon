package id.bhinneka.rebon.model;

/**
 * Created by bendet on 13/07/17.
 */

public class LokasiKeberangkatanModel {

    private String namaLokasi, alamatLokasi;
    private double lat, lng;

    public LokasiKeberangkatanModel(String namaLokasi, String alamatLokasi, double lat, double lng) {
        this.namaLokasi = namaLokasi;
        this.alamatLokasi = alamatLokasi;
        this.lat = lat;
        this.lng = lng;
    }

    public String getNamaLokasi() {
        return namaLokasi;
    }

    public void setNamaLokasi(String namaLokasi) {
        this.namaLokasi = namaLokasi;
    }

    public String getAlamatLokasi() {
        return alamatLokasi;
    }

    public void setAlamatLokasi(String alamatLokasi) {
        this.alamatLokasi = alamatLokasi;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}
