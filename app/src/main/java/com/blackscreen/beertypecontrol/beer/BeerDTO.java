package com.blackscreen.beertypecontrol.beer;

import android.os.Bundle;

import com.blackscreen.beertypecontrol.RegisterActivity;

public class BeerDTO {

    private int id;
    private String name;
    private String type;
    private String brewery;
    private String abv;
    private String ibu;
    private String note;
    private String spNote;
    private boolean origin;
    private boolean wouldBuyAgain;


    public BeerDTO(int id, String name, String type, String brewery, String abv, String ibu, String note, String spNote, boolean wouldBuyAgain, boolean origin) {
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

    public BeerDTO() {
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public static BeerDTO bundleToBeerDTO(Bundle bundle){

        return new BeerDTO(
                bundle.getInt(RegisterActivity.ID),
                bundle.getString(RegisterActivity.NAME),
                bundle.getString(RegisterActivity.TYPE),
                bundle.getString(RegisterActivity.BREWERY),
                bundle.getString(RegisterActivity.ABV),
                bundle.getString(RegisterActivity.IBU),
                bundle.getString(RegisterActivity.NOTE),
                bundle.getString(RegisterActivity.SP_NOTE),
                bundle.getBoolean(RegisterActivity.BUY_AGAIN),
                bundle.getBoolean(RegisterActivity.ORIGIN));
    }
}
