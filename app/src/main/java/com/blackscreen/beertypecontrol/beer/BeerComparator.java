package com.blackscreen.beertypecontrol.beer;

import com.blackscreen.beertypecontrol.useful.ViewDirection;
import com.blackscreen.beertypecontrol.useful.ViewTypeOrder;

import java.util.Comparator;

public class BeerComparator implements Comparator<Beer>  {

    private ViewDirection viewDirection;
    private ViewTypeOrder viewTypeOrder;

    public BeerComparator(ViewDirection viewDirection, ViewTypeOrder viewTypeOrder) {
        this.viewDirection = viewDirection;
        this.viewTypeOrder = viewTypeOrder;
    }

    @Override
    public int compare(Beer beer1, Beer beer2) {
        long compareLong;
        if(ViewTypeOrder.ID.equals(this.viewTypeOrder)){
            compareLong =  ViewDirection.ASC.equals(this.viewDirection) ?
                    beer1.getId() - beer2.getId() :
                    beer2.getId() - beer1.getId();

            return (int) compareLong;
        }

        if(ViewTypeOrder.NAME.equals(this.viewTypeOrder)){
            return ViewDirection.ASC.equals(this.viewDirection) ?
                    beer1.getName().toUpperCase().compareTo(beer2.getName().toUpperCase()) :
                    beer2.getName().toUpperCase().compareTo(beer1.getName().toUpperCase());
        }

        return -1;
    }

}
