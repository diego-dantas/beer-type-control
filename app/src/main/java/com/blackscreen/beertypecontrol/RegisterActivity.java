package com.blackscreen.beertypecontrol;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.blackscreen.beertypecontrol.beer.BeerDTO;

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

                BeerDTO beerDTO = BeerDTO.bundleToBeerDTO(bundle);

                editTextId.setText(String.valueOf(beerDTO.getId()));
                editTextName.setText(beerDTO.getName());
                editTextType.setText(beerDTO.getType());
                editTextBrewery.setText(beerDTO.getBrewery());
                editTextAbv.setText(beerDTO.getAbv());
                editTextIbu.setText(beerDTO.getIbu());
                editTextNote.setText(beerDTO.getNote());

                RadioButton rbBuyAgain;
                if(beerDTO.isWouldBuyAgain()){
                    rbBuyAgain = findViewById(R.id.radioButtonYes);
                    rbBuyAgain.setChecked(true);
                }else{
                    rbBuyAgain = findViewById(R.id.radioButtonNo);
                    rbBuyAgain.setChecked(true);
                }

                checkBoxOrigin.setChecked(beerDTO.isOrigin());

                int position = Integer.valueOf(beerDTO.getSpNote()) + 1;

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

    public static void updateBeer(AppCompatActivity activity, BeerDTO beerListView){
        Intent intent = new Intent(activity, RegisterActivity.class);

        intent.putExtra(OPTION, UPDATE);
        intent.putExtra(ID, beerListView.getId());
        intent.putExtra(NAME, beerListView.getName());
        intent.putExtra(TYPE, beerListView.getType());
        intent.putExtra(BREWERY, beerListView.getBrewery());
        intent.putExtra(ABV, beerListView.getAbv());
        intent.putExtra(IBU, beerListView.getIbu());
        intent.putExtra(NOTE, beerListView.getNote());
        intent.putExtra(SP_NOTE, beerListView.getSpNote());
        intent.putExtra(BUY_AGAIN, beerListView.isWouldBuyAgain());
        intent.putExtra(ORIGIN, beerListView.isOrigin());

        activity.startActivityForResult(intent, UPDATE);
    }

    public void clearFields(View view){

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

    public void saveFields(View view){
        BeerDTO beerDTO = validateAllFields();

        if(Objects.nonNull(beerDTO)){

            Intent intent = new Intent();

            intent.putExtra(ID, beerDTO.getId());
            intent.putExtra(NAME, beerDTO.getName());
            intent.putExtra(TYPE, beerDTO.getType());
            intent.putExtra(BREWERY, beerDTO.getBrewery());
            intent.putExtra(ABV, beerDTO.getAbv());
            intent.putExtra(IBU, beerDTO.getIbu());
            intent.putExtra(NOTE, beerDTO.getNote());
            intent.putExtra(SP_NOTE, beerDTO.getSpNote());
            intent.putExtra(BUY_AGAIN, beerDTO.isWouldBuyAgain());
            intent.putExtra(ORIGIN, beerDTO.isOrigin());

            setResult(Activity.RESULT_OK, intent);

            finish();
        }
    }

    public BeerDTO validateAllFields(){

        int id          = Integer.valueOf(editTextId.getText().toString());
        String name     = editTextName.getText().toString();
        String type     = editTextType.getText().toString();
        String brewery  = editTextBrewery.getText().toString();
        String abv      = editTextAbv.getText().toString();
        String ibu      = editTextIbu.getText().toString();
        String note     = editTextNote.getText().toString();
        String spNote   = validateSpinner();
        Boolean wouldBuyAgain = validateRadioButton();

        if(validateField(name)){
            showToastEditText(R.string.errorName, editTextName);
            return null;
        }else if(validateField(type)){
            showToastEditText(R.string.errorType, editTextType);
            return null;
        }else if(validateField(brewery)){
            showToastEditText(R.string.errorbrewery, editTextBrewery);
            return null;
        }else if(validateField(abv)){
            showToastEditText(R.string.errorAbv, editTextAbv);
            return null;
        }else if(validateField(ibu)){
            showToastEditText(R.string.errorIbu, editTextIbu);
            return null;
        }else if(validateField(note)){
            showToastEditText(R.string.errorNote, editTextNote);
            return null;
        }

        if(Objects.isNull(wouldBuyAgain) || Objects.isNull(spNote)){
            return null;
        }

        boolean origin = checkBoxOrigin.isChecked();

        Toast.makeText(this, R.string.msg_success, Toast.LENGTH_LONG).show();
        BeerDTO beerDTO = new BeerDTO(id, name, type, brewery, abv, ibu, note, spNote, wouldBuyAgain, origin);

        return beerDTO;

    }

    public boolean validateField(String field){
        return field == null || field.trim().isEmpty();
    }


    public void showToastEditText(Integer errorMsg, EditText editText){
        Toast.makeText(this, errorMsg, Toast.LENGTH_LONG).show();
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
        setResult(Activity.RESULT_CANCELED);
        finish();
    }

}