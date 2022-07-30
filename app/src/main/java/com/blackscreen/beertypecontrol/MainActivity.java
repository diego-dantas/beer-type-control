package com.blackscreen.beertypecontrol;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private EditText
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

        setContentView(R.layout.activity_main);

        editTextName    = findViewById(R.id.editTextName);
        editTextType    = findViewById(R.id.editType);
        editTextBrewery = findViewById(R.id.editTextBrewery);
        editTextAbv     = findViewById(R.id.editTextAbv);
        editTextIbu     = findViewById(R.id.editTextIbu);
        editTextNote    = findViewById(R.id.editTextNote);

        checkBoxOrigin = findViewById(R.id.checkBoxOrigin);

        radioGroupBuy = findViewById(R.id.radioGroupBuy);

        spinnerNote = findViewById(R.id.spinnerNote);

        editTextName.requestFocus();

        setUpSpinner();

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
        validateAllFields();
    }



    public void validateAllFields(){
        String name     = editTextName.getText().toString();
        String type     = editTextType.getText().toString();
        String brewery  = editTextBrewery.getText().toString();
        String abv      = editTextAbv.getText().toString();
        String ibu      = editTextIbu.getText().toString();
        String note     = editTextNote.getText().toString();

        if(validateField(name)){
            showToastEditText(R.string.errorName, editTextName);
            return;
        }else if(validateField(type)){
            showToastEditText(R.string.errorType, editTextType);
            return;
        }else if(validateField(brewery)){
            showToastEditText(R.string.errorbrewery, editTextBrewery);
            return;
        }else if(validateField(abv)){
            showToastEditText(R.string.errorAbv, editTextAbv);
            return;
        }else if(validateField(ibu)){
            showToastEditText(R.string.errorIbu, editTextIbu);
            return;
        }else if(validateField(note)){
            showToastEditText(R.string.errorNote, editTextNote);
            return;
        }

        if(Objects.isNull(validateRadioButton()))
            return;

        if(Objects.isNull(validateSpinner()))
            return;


        Toast.makeText(this, R.string.msg_success, Toast.LENGTH_LONG).show();

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

}