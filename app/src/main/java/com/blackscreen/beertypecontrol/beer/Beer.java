package com.blackscreen.beertypecontrol.beer;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Beer {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private String name;
    private String type;
    private String brewery;
    private String abv;
    private String ibu;
    private String note;
    private String spNote;
    private boolean origin;
    private boolean wouldBuyAgain;


    public Beer(long id, String name, String type, String brewery, String abv, String ibu, String note, String spNote, boolean wouldBuyAgain, boolean origin) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.brewery = brewery;
        this.abv = abv;
        this.ibu = ibu;
        this.note = note;
        this.spNote = spNote;
        this.wouldBuyAgain = wouldBuyAgain;
        this.origin = origin;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getSpNote() {
        return spNote;
    }

    public void setSpNote(String spNote) {
        this.spNote = spNote;
    }

    public String getBrewery() {
        return brewery;
    }

    public void setBrewery(String brewery) {
        this.brewery = brewery;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public boolean isOrigin() {
        return origin;
    }

    public void setOrigin(boolean origin) {
        this.origin = origin;
    }

    public boolean isWouldBuyAgain() {
        return wouldBuyAgain;
    }

    public void setWouldBuyAgain(boolean wouldBuyAgain) {
        this.wouldBuyAgain = wouldBuyAgain;
    }

}
