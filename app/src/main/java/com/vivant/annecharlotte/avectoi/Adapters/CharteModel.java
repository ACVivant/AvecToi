package com.vivant.annecharlotte.avectoi.Adapters;

/**
 * Created by Anne-Charlotte Vivant on 09/08/2019.
 */
public class CharteModel {

    private int image;
    private String title;
    private String desc;

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public CharteModel(int image, String title, String desc) {
        this.image = image;
        this.title = title;
        this.desc = desc;
    }
}
