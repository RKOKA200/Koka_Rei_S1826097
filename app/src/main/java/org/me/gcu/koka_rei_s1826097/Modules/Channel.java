package org.me.gcu.koka_rei_s1826097.Modules;

// Koka_Rei_S1826097

import java.util.List;

public class Channel {
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public List<Item> getItem() {
        return item;
    }

    public void setItem(List<Item> item) {
        this.item = item;
    }

    public String title;
    public String description;
    public String link;
    public List<Item> item;
}

