package com.blackscreen.beertypecontrol;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.blackscreen.beertypecontrol.beer.BeerAdapter;
import com.blackscreen.beertypecontrol.beer.BeerDTO;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    private ListView listViewBeer;
    private BeerAdapter beerAdapter;
    private List<BeerDTO> beerList;
    private int positionSelected = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        listViewBeer = findViewById(R.id.listViewBeer);

        listViewBeer.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                positionSelected = i;
                updateRegister();

            }
        });

        listFilling();
    }

    public void openAbout(View view){
        AboutActivity.about(this);
    }

    public void openRegister(View view){
        RegisterActivity.newBeer(this);
    }

    public void updateRegister() {

        BeerDTO beerListView = beerList.get(positionSelected);

        RegisterActivity.updateBeer(this, beerListView);
    }


    private void listFilling(){

        beerList = new ArrayList<>();

        beerAdapter = new BeerAdapter(this, beerList);

        listViewBeer.setAdapter(beerAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {

            Bundle bundle = data.getExtras();

            if(requestCode == RegisterActivity.UPDATE){

                BeerDTO beerListView = beerList.get(positionSelected);
                beerListView.setId(bundle.getInt(RegisterActivity.ID));
                beerListView.setName(bundle.getString(RegisterActivity.NAME));
                beerListView.setType(bundle.getString(RegisterActivity.TYPE));
                beerListView.setBrewery(bundle.getString(RegisterActivity.BREWERY));
                beerListView.setAbv(bundle.getString(RegisterActivity.ABV));
                beerListView.setIbu(bundle.getString(RegisterActivity.IBU));
                beerListView.setNote(bundle.getString(RegisterActivity.NOTE));
                beerListView.setSpNote(bundle.getString(RegisterActivity.SP_NOTE));
                beerListView.setWouldBuyAgain(bundle.getBoolean(RegisterActivity.BUY_AGAIN));
                beerListView.setOrigin(bundle.getBoolean(RegisterActivity.ORIGIN));

                positionSelected = -1;

            }else{

                Integer id = idGenerator();
                bundle.putInt(RegisterActivity.ID, id);

                beerList.add(BeerDTO.bundleToBeerDTO(bundle));
            }
            beerAdapter.notifyDataSetChanged();
        }
    }

    public int idGenerator(){
        return beerList.size() + 1;
    }

}