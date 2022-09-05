package com.blackscreen.beertypecontrol;

import static com.blackscreen.beertypecontrol.useful.ViewTypeOrder.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.blackscreen.beertypecontrol.beer.BeerAdapter;
import com.blackscreen.beertypecontrol.beer.BeerComparator;
import com.blackscreen.beertypecontrol.beer.BeerDTO;
import com.blackscreen.beertypecontrol.useful.ViewOrder;
import com.blackscreen.beertypecontrol.useful.ViewTypeOrder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ListActivity extends AppCompatActivity {

    private static final String ARQUIVO = "com.blackscreen.beertypecontrol.ORDER_PREFERENCE";

    private static final String PREFERENCE = "PREFERENCE";

    private ViewTypeOrder viewTypeOrderOption = ID;

    private ListView listViewBeer;
    private BeerAdapter beerAdapter;
    private List<BeerDTO> beerList;
    private int positionSelected = -1;

    private ActionMode actionMode;
    private View viewSelected;

    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.main_option_action, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem menuItem) {

            switch (menuItem.getItemId()){
                case R.id.menuItemEdit:
                    updateRegister();
                    mode.finish();
                    return true;
                case R.id.menuItemDelete:
                    deleteRegister();
                    mode.finish();
                    return true;
                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            if(viewSelected != null){
                viewSelected.setBackgroundColor(Color.TRANSPARENT);
            }

            actionMode = null;
            viewSelected = null;
            listViewBeer.setEnabled(true);
        }
    };


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

        listViewBeer.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        listViewBeer.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                if(actionMode != null){
                    return false;
                }

                positionSelected = i;

                view.setBackgroundColor(Color.LTGRAY);

                viewSelected = view;

                listViewBeer.setEnabled(false);

                actionMode = startSupportActionMode(mActionModeCallback);

                return true;
            }
        });

        listFilling();
        readOrderPreference();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_option, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        item.setChecked(true);

        switch (item.getItemId()){
            case R.id.menuItemAdd:
                RegisterActivity.newBeer(this);
                break;
            case R.id.menuItemAbout:
                AboutActivity.about(this);
                break;
            case R.id.gMenuItemOrderId:
                saveOrderPreference(ID);
                return true;

            case R.id.gMenuItemOrderName:
                saveOrderPreference(NAME);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }

        return super.onOptionsItemSelected(item);
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

    private void deleteRegister(){
        beerList.remove(positionSelected);
        beerAdapter.notifyDataSetChanged();
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

            updateOrderList();
        }
    }


    private int idGenerator(){
        return beerList.size() + 1;
    }

    private void readOrderPreference(){

        SharedPreferences shared = getSharedPreferences(ARQUIVO,
                Context.MODE_PRIVATE);

        viewTypeOrderOption = ViewTypeOrder.valueOf(shared.getString(PREFERENCE, viewTypeOrderOption.toString()));

        updateOrderList();
    }

    private void saveOrderPreference(ViewTypeOrder viewTypeOrder){

        SharedPreferences shared = getSharedPreferences(ARQUIVO,
                Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = shared.edit();

        editor.putString(PREFERENCE, viewTypeOrder.toString());

        editor.commit();

        viewTypeOrderOption = viewTypeOrder;

        updateOrderList();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        MenuItem item;

        switch(viewTypeOrderOption){

            case ID:
                item = menu.findItem(R.id.gMenuItemOrderId);
                break;
            case NAME:
                item = menu.findItem(R.id.gMenuItemOrderName);
                break;
            default:
                return false;
        }

        item.setChecked(true);
        return true;
    }

    private void updateOrderList(){
        Collections.sort(beerList, new BeerComparator(ViewOrder.ASC, viewTypeOrderOption));
        beerAdapter.notifyDataSetChanged();
    }

}