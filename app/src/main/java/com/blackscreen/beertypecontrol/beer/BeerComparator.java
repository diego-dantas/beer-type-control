package com.blackscreen.beertypecontrol.beer;

import com.blackscreen.beertypecontrol.useful.ViewDirection;
import com.blackscreen.beertypecontrol.useful.ViewTypeOrder;

import java.util.Comparator;

public class BeerComparator implements Comparator<BeerDTO>  {

    private ViewDirection viewDirection;
    private ViewTypeOrder viewTypeOrder;

    public BeerComparator(ViewDirection viewDirection, ViewTypeOrder viewTypeOrder) {
        this.viewDirection = viewDirection;
        this.viewTypeOrder = viewTypeOrder;
    }

    @Override
    public int compare(BeerDTO beerDTO1, BeerDTO beerDTO2) {

        if(ViewTypeOrder.ID.equals(this.viewTypeOrder)){
            return ViewDirection.ASC.equals(this.viewDirection) ?
                    beerDTO1.getId() - beerDTO2.getId() :
                    beerDTO2.getId() - beerDTO1.getId();
        }

        if(ViewTypeOrder.NAME.equals(this.viewTypeOrder)){
            return ViewDirection.ASC.equals(this.viewDirection) ?
                    beerDTO1.getName().toUpperCase().compareTo(beerDTO2.getName().toUpperCase()) :
                    beerDTO2.getName().toUpperCase().compareTo(beerDTO1.getName().toUpperCase());
        }

        return -1;
    }

}
