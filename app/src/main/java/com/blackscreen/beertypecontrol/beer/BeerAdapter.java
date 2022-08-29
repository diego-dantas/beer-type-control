package com.blackscreen.beertypecontrol.beer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.blackscreen.beertypecontrol.R;

import java.util.List;
import java.util.Objects;

public class BeerAdapter extends BaseAdapter {

    private Context context;
    private List<BeerDTO> beers;

    public BeerAdapter(Context context, List<BeerDTO> beers){
        this.context = context;
        this.beers = beers;
    }

    private static class BeerHolder{
        public TextView textViewId;
        public TextView textViewName;
        public TextView textViewType;
        public TextView textViewAbv;
        public TextView textViewIbu;
        public TextView textViewNote;
    }



    @Override
    public int getCount() {
        return beers.size();
    }

    @Override
    public Object getItem(int i) {
        return beers.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        BeerHolder beerHolder;

        if(Objects.isNull(view)){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.activity_beer_layout, viewGroup, false);

            beerHolder = new BeerHolder();

            beerHolder.textViewId = view.findViewById(R.id.textViewIdBeer);
            beerHolder.textViewName = view.findViewById(R.id.textViewNameBeer);
            beerHolder.textViewType = view.findViewById(R.id.textViewTypeBeer);
            beerHolder.textViewAbv = view.findViewById(R.id.textViewAbvBeer);
            beerHolder.textViewIbu = view.findViewById(R.id.textViewIbuBeer);
            beerHolder.textViewNote = view.findViewById(R.id.textViewNoteBeer);

            view.setTag(beerHolder);

        }else{
            beerHolder = (BeerHolder) view.getTag();
        }

        beerHolder.textViewId.setText(String.valueOf(beers.get(i).getId()));
        beerHolder.textViewName.setText(beers.get(i).getName());
        beerHolder.textViewType.setText(beers.get(i).getType());
        beerHolder.textViewAbv.setText(beers.get(i).getAbv());
        beerHolder.textViewIbu.setText(beers.get(i).getIbu());
        beerHolder.textViewNote.setText(beers.get(i).getSpNote());

        return view;
    }
}
