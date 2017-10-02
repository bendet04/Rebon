package id.bhinneka.rebon.model;

import android.graphics.Bitmap;

/**
 * Created by bendet on 26/07/17.
 */

public class KursiModel {

    private Bitmap image;
    private String title;
    private boolean isSelected;

    public KursiModel(Bitmap image, String title) {
        this.image = image;
        this.title = title;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
