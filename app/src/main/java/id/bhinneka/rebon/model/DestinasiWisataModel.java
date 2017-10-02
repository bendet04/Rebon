package id.bhinneka.rebon.model;

import java.util.List;

/**
 * Created by bendet on 9/9/17.
 */

public class DestinasiWisataModel {
    String kategori;
    List<ListDestinasiWisataModel> destinasiWisata;

    public DestinasiWisataModel() {
    }

    public DestinasiWisataModel(String kategori, List<ListDestinasiWisataModel> destinasiWisata) {
        this.kategori = kategori;
        this.destinasiWisata = destinasiWisata;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public List<ListDestinasiWisataModel> getDestinasiWisata() {
        return destinasiWisata;
    }

    public void setDestinasiWisata(List<ListDestinasiWisataModel> destinasiWisata) {
        this.destinasiWisata = destinasiWisata;
    }
}
