package com.blackscreen.beertypecontrol;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.blackscreen.beertypecontrol.beer.BeerAdapter;
import com.blackscreen.beertypecontrol.beer.BeerEntity;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    private ListView listViewBeer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        listViewBeer = findViewById(R.id.listViewBeer);

        listViewBeer.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                BeerEntity beer = (BeerEntity) listViewBeer.getItemAtPosition(i);

                Toast.makeText(getApplicationContext(), beer.getName() + " Foi Clicado", Toast.LENGTH_SHORT).show();

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


    private void listFilling(){

        String[] beers = getResources().getStringArray(R.array.beers);
        String[] types = getResources().getStringArray(R.array.types);
        String[] abv = getResources().getStringArray(R.array.abvs);
        String[] ibu = getResources().getStringArray(R.array.ibus);
        String[] note = getResources().getStringArray(R.array.notes);

        ArrayList<BeerEntity> beerEntities = new ArrayList<>();

        for(int i = 0; i < beers.length; i++){
            beerEntities.add(new BeerEntity(
                    beers[i],
                    types[i],
                    abv[i],
                    ibu[i],
                    note[i]));
        }

        BeerAdapter beerAdapter = new BeerAdapter(this, beerEntities);

        listViewBeer.setAdapter(beerAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {

            Toast.makeText(getApplicationContext(), " Recuperou o valor", Toast.LENGTH_LONG).show();


        }
    }
}