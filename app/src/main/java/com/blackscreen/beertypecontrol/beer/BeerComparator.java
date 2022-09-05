package com.blackscreen.beertypecontrol.beer;

import com.blackscreen.beertypecontrol.useful.ViewOrder;
import com.blackscreen.beertypecontrol.useful.ViewTypeOrder;

import java.util.Comparator;

public class BeerComparator implements Comparator<BeerDTO>  {

    private ViewOrder viewOrder;
    private ViewTypeOrder viewTypeOrder;

    public BeerComparator(ViewOrder viewOrder, ViewTypeOrder viewTypeOrder) {
        this.viewOrder = viewOrder;
        this.viewTypeOrder = viewTypeOrder;
    }

    @Override
    public int compare(BeerDTO beerDTO1, BeerDTO beerDTO2) {

        if(ViewTypeOrder.ID.equals(this.viewTypeOrder)){
            return ViewOrder.ASC.equals(this.viewOrder) ?
                    beerDTO1.getId() - beerDTO2.getId() :
                    beerDTO2.getId() - beerDTO1.getId();
        }

        if(ViewTypeOrder.NAME.equals(this.viewTypeOrder)){
            return ViewOrder.ASC.equals(this.viewOrder) ?
                    beerDTO1.getName().toUpperCase().compareTo(beerDTO2.getName().toUpperCase()) :
                    beerDTO2.getName().toUpperCase().compareTo(beerDTO1.getName().toUpperCase());
        }

        return -1;
    }

}
