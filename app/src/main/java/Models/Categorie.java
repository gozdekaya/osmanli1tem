package Models;

import java.util.List;

public class Categorie {
    int id;
    String title,media;
    List<Categorie> subCategories;

    public Categorie(int id, String title, String media, List<Categorie> subCategories) {
        this.id = id;
        this.title = title;
        this.media = media;
        this.subCategories = subCategories;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    public List<Categorie> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(List<Categorie> subCategories) {
        this.subCategories = subCategories;
    }
}
