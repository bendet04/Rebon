package id.bhinneka.rebon.model;

/**
 * Created by bendet on 9/9/17.
 */

public class ListDestinasiWisataModel {
    private String url;
    private String namaDestinasi;
    private int id;

    public ListDestinasiWisataModel() {
    }

    public ListDestinasiWisataModel(int id, String url, String namaDestinasi) {
        this.id = id;
        this.url = url;
        this.namaDestinasi = namaDestinasi;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNamaDestinasi() {
        return namaDestinasi;
    }

    public void setNamaDestinasi(String namaDestinasi) {
        this.namaDestinasi = namaDestinasi;
    }
}
