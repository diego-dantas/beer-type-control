package com.blackscreen.beertypecontrol;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.blackscreen.beertypecontrol.beer.Beer;
import com.blackscreen.beertypecontrol.beer.BeerDatabase;
import com.blackscreen.beertypecontrol.useful.CustomAlert;

import java.util.ArrayList;
import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    public static final int NEW = 1;
    public static final int UPDATE = 2;
    public static final String OPTION = "OPTION";
    public static final String ID = "ID";
    public static final String NAME = "NAME";
    public static final String TYPE = "TYPE";
    public static final String BREWERY = "BREWERY";
    public static final String ABV = "ABV";
    public static final String IBU = "IBU";
    public static final String NOTE = "NOTE";
    public static final String SP_NOTE = "SP_NOTE";
    public static final String BUY_AGAIN = "BUYAGAIN";
    public static final String ORIGIN = "ORIGIN";


    private int option;

    private EditText
                editTextId,
                editTextName,
                editTextType,
                editTextBrewery,
                editTextAbv,
                editTextIbu,
                editTextNote;

    private CheckBox checkBoxOrigin;

    private RadioGroup radioGroupBuy;

    private Spinner spinnerNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);

        ActionBar actionBar = getSupportActionBar();
        if(Objects.nonNull(actionBar)){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        editTextId      = findViewById(R.id.editTextId);
        editTextName    = findViewById(R.id.editTextName);
        editTextType    = findViewById(R.id.editType);
        editTextBrewery = findViewById(R.id.editTextBrewery);
        editTextAbv     = findViewById(R.id.editTextAbv);
        editTextIbu     = findViewById(R.id.editTextIbu);
        editTextNote    = findViewById(R.id.editTextNote);
        checkBoxOrigin  = findViewById(R.id.checkBoxOrigin);
        radioGroupBuy   = findViewById(R.id.radioGroupBuy);
        spinnerNote     = findViewById(R.id.spinnerNote);

        setUpSpinner();

        if(Objects.nonNull(bundle)){

            option = bundle.getInt(OPTION, NEW);

            if(option == NEW){
                editTextId.setText("0");
                setTitle(getString(R.string.newBeer));
            }else{

                BeerDatabase beerDatabase = BeerDatabase.getDatabase(this);

                long id = bundle.getLong(RegisterActivity.ID);
                Beer beer = beerDatabase.beerDAO().findById(id);

                editTextId.setText(String.valueOf(beer.getId()));
                editTextName.setText(beer.getName());
                editTextType.setText(beer.getType());
                editTextBrewery.setText(beer.getBrewery());
                editTextAbv.setText(beer.getAbv());
                editTextIbu.setText(beer.getIbu());
                editTextNote.setText(beer.getNote());

                RadioButton rbBuyAgain;
                if(beer.isWouldBuyAgain()){
                    rbBuyAgain = findViewById(R.id.radioButtonYes);
                    rbBuyAgain.setChecked(true);
                }else{
                    rbBuyAgain = findViewById(R.id.radioButtonNo);
                    rbBuyAgain.setChecked(true);
                }

                checkBoxOrigin.setChecked(beer.isOrigin());

                int position = Integer.valueOf(beer.getSpNote()) + 1;

                spinnerNote.setSelection(position);

                setTitle(getString(R.string.updateBeer));
            }

        }

        editTextName.requestFocus();

    }


    public static void newBeer(AppCompatActivity activity){
        Intent intent = new Intent(activity, RegisterActivity.class);

        intent.putExtra(OPTION, NEW);

        activity.startActivityForResult(intent, NEW);
    }

    public static void updateBeer(AppCompatActivity activity, Beer beerListView){
        Intent intent = new Intent(activity, RegisterActivity.class);

        intent.putExtra(OPTION, UPDATE);
        intent.putExtra(ID, beerListView.getId());
//        intent.putExtra(NAME, beerListView.getName());
//        intent.putExtra(TYPE, beerListView.getType());
//        intent.putExtra(BREWERY, beerListView.getBrewery());
//        intent.putExtra(ABV, beerListView.getAbv());
//        intent.putExtra(IBU, beerListView.getIbu());
//        intent.putExtra(NOTE, beerListView.getNote());
//        intent.putExtra(SP_NOTE, beerListView.getSpNote());
//        intent.putExtra(BUY_AGAIN, beerListView.isWouldBuyAgain());
//        intent.putExtra(ORIGIN, beerListView.isOrigin());

        activity.startActivityForResult(intent, UPDATE);
    }

    private void clean(){
        editTextName.setText(null);
        editTextType.setText(null);
        editTextBrewery.setText(null);
        editTextAbv.setText(null);
        editTextIbu.setText(null);
        editTextNote.setText(null);

        checkBoxOrigin.setChecked(false);

        radioGroupBuy.clearCheck();

        setUpSpinner();

        editTextName.requestFocus();
        Toast.makeText(this, R.string.campos_limpos, Toast.LENGTH_LONG).show();
    }

    private void toSave(){
        Beer beer = validateAllFields();

        if(Objects.nonNull(beer)){

            BeerDatabase beerDatabase = BeerDatabase.getDatabase(this);

            if(option == NEW){
                long id = beerDatabase.beerDAO().insert(beer);
                beer.setId(id);
            }else{
                beerDatabase.beerDAO().update(beer);
            }

            Intent intent = new Intent();

            intent.putExtra(ID, beer.getId());
            intent.putExtra(NAME, beer.getName());
            intent.putExtra(TYPE, beer.getType());
            intent.putExtra(BREWERY, beer.getBrewery());
            intent.putExtra(ABV, beer.getAbv());
            intent.putExtra(IBU, beer.getIbu());
            intent.putExtra(NOTE, beer.getNote());
            intent.putExtra(SP_NOTE, beer.getSpNote());
            intent.putExtra(BUY_AGAIN, beer.isWouldBuyAgain());
            intent.putExtra(ORIGIN, beer.isOrigin());

            setResult(Activity.RESULT_OK, intent);

            finish();
        }
    }



    private Beer validateAllFields(){

        int id          = Integer.valueOf(editTextId.getText().toString());
        String name     = editTextName.getText().toString();
        String type     = editTextType.getText().toString();
        String brewery  = editTextBrewery.getText().toString();
        String abv      = editTextAbv.getText().toString();
        String ibu      = editTextIbu.getText().toString();
        String note     = editTextNote.getText().toString();


        if(validateField(name)){
            msgValidateField(R.string.errorName, editTextName);
            return null;
        }else if(validateField(type)){
            msgValidateField(R.string.errorType, editTextType);
            return null;
        }else if(validateField(brewery)){
            msgValidateField(R.string.errorbrewery, editTextBrewery);
            return null;
        }else if(validateField(abv)){
            msgValidateField(R.string.errorAbv, editTextAbv);
            return null;
        }else if(validateField(ibu)){
            msgValidateField(R.string.errorIbu, editTextIbu);
            return null;
        }else if(validateField(note)){
            msgValidateField(R.string.errorNote, editTextNote);
            return null;
        }

        String spNote   = validateSpinner();
        Boolean wouldBuyAgain = validateRadioButton();

        if(Objects.isNull(wouldBuyAgain) || Objects.isNull(spNote)){
            return null;
        }

        boolean origin = checkBoxOrigin.isChecked();

        Toast.makeText(this, R.string.msg_success, Toast.LENGTH_LONG).show();
        Beer beer = new Beer(id, name, type, brewery, abv, ibu, note, spNote, wouldBuyAgain, origin);

        return beer;

    }

    public boolean validateField(String field){
        return field == null || field.trim().isEmpty();
    }


    public void showToastEditText(Integer errorMsg, EditText editText){
        Toast.makeText(this, errorMsg, Toast.LENGTH_LONG).show();
        editText.requestFocus();
    }

    private void msgValidateField(Integer errorMsg, EditText editText){
        CustomAlert.errorWarning(this, errorMsg);

        editText.requestFocus();
    }

    public Boolean validateRadioButton(){

        Boolean wouldBuyAgain = null;
        final int yes = R.id.radioButtonYes;
        final int no = R.id.radioButtonNo;
        switch (radioGroupBuy.getCheckedRadioButtonId()){
            case yes:
                wouldBuyAgain = true;
                break;
            case no:
                wouldBuyAgain = false;
                break;
        }

        if (Objects.isNull(wouldBuyAgain)){
            Toast.makeText(this, R.string.errorRadioGroupBuy, Toast.LENGTH_LONG).show();
            radioGroupBuy.requestFocusFromTouch();
        }

        return wouldBuyAgain;
    }

    public void setUpSpinner(){
        ArrayList<String> list = new ArrayList<String>();
        list.add(getString(R.string.spinnerLabel));
        for (int i = 0; i <= 10; i++ ){
            list.add(String.valueOf(i));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                list);

        spinnerNote.setAdapter(adapter);
    }

    public String validateSpinner(){
        String note = spinnerNote.getSelectedItem().toString();

        if(Objects.isNull(note)){
            toastSpinner(getString(R.string.errorSpinner));
        }else{
            if (note.equals(getString(R.string.spinnerLabel))){
                toastSpinner(getString(R.string.errorSpinnerSelect));
                note = null;
            }
        }
        return note;
    }


    public void toastSpinner(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        spinnerNote.requestFocusFromTouch();
    }

    @Override
    public void onBackPressed() {
       cancel();
    }

    private void cancel(){
        setResult(Activity.RESULT_CANCELED);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_option_register, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuItemSave:
                toSave();
                return true;
            case R.id.menuItemClean:
                clean();
                return true;
            case android.R.id.home:
                cancel();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}