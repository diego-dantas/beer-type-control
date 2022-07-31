package com.blackscreen.beertypecontrol.beer;

public class BeerEntity {

    private String name;
    private String type;
    private String abv;
    private String ibu;
    private String note;

    public BeerEntity(String name, String type, String abv, String ibu, String note) {
        this.name = name;
        this.type = type;
        this.abv = abv;
        this.ibu = ibu;
        this.note = note;
    }

    @Override
    public String toString() {
        return name + "-" + type ;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAbv() {
        return abv;
    }

    public void setAbv(String abv) {
        this.abv = abv;
    }

    public String getIbu() {
        return ibu;
    }

    public void setIbu(String ibu) {
        this.ibu = ibu;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
