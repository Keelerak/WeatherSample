package com.example.andrey.weather;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class TableManipulationActivity extends Activity {

    EditText etFirstname;
    EditText etCountryname;
    String temp = String.valueOf(-1111);

    Button btnDML;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.table_manipulation);
        getAllWidgets();
        bindWidgetsWithEvent();
        checkForRequest();
    }

    private void checkForRequest() {
        String request = getIntent().getExtras().get(Constants.DML_TYPE).toString();
        if (request.equals(Constants.UPDATE)) {
            btnDML.setText(Constants.UPDATE);
            etFirstname.setText(getIntent().getExtras().get(Constants.FIRST_NAME).toString());
            etCountryname.setText(getIntent().getExtras().get(Constants.COUNTRY_NAME).toString());
        } else {
            btnDML.setText(Constants.INSERT);
        }
    }

    private void bindWidgetsWithEvent() {
        btnDML.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClick();
            }
        });
    }

    private void getAllWidgets() {
        etFirstname = (EditText) findViewById(R.id.etFirstName);
        etCountryname = (EditText) findViewById(R.id.etCountryname);

        btnDML = (Button) findViewById(R.id.btnDML);
    }

    private void onButtonClick() {
        if (etFirstname.getText().toString().equals("") || etCountryname.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Add Both Fields", Toast.LENGTH_LONG).show();
        } else {
            Intent intent33 = new Intent();
            intent33.putExtra(Constants.FIRST_NAME, etFirstname.getText().toString());
            intent33.putExtra(Constants.COUNTRY_NAME, etCountryname.getText().toString());
            intent33.putExtra(Constants.TEMPERATURE, temp);
            setResult(RESULT_OK, intent33);
            finish();
        }
    }
}