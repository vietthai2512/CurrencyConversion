package com.example.currencyconversion;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements OnItemSelectedListener
{
    public String baseCurrency = "VND";
    public String convertedToCurrency = "USD";
    public double conversionRate1 = 1.0;
    public double conversionRate2 = 1.0;
    EditText input1;
    EditText input2;
    Map<String, Double> currencies = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        input1 = (EditText) findViewById(R.id.input1);
        input2 = (EditText) findViewById(R.id.input2);
        currencies.put("EUR", 1.0); currencies.put("VND", 27412.401313); currencies.put("USD", 1.182767); currencies.put("SGD", 1.60712);
        currencies.put("CAD", 1.555628); currencies.put("AUD", 1.655998); currencies.put("HKD", 9.166622);
        currencies.put("JPY", 123.515217); currencies.put("GBP", 0.905308); currencies.put("RUB", 91.143561);
        currencies.put("BTC", 8.6981889e-5); currencies.put("THB", 36.914115);
        spinnerSetup();
        textChangedStuff();
    }

    private void spinnerSetup()
    {
        Spinner spinner1 = (Spinner) findViewById(R.id.spinner1);
        Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.currencies1, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.currencies2, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);
        spinner2.setAdapter(adapter2);
        spinner1.setOnItemSelectedListener(this);
        spinner2.setOnItemSelectedListener(this);
        disableEditText(input2);
    }

    private void textChangedStuff()
    {
        input1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
                Log.d("Main", "Before Text Changed");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                Log.d("Main", "On Text Changed");
            }

            @Override
            public void afterTextChanged(Editable s)
            {
                try
                {
                    getAPIResult();
                }
                catch (Exception e)
                {
                    Toast.makeText(getApplicationContext(), "Type a value", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getAPIResult()
    {
        //if (input1 != null && !input1.getText().toString().equals(""))
        //{

            /*String API_KEY = "bcf89b30f7078d7de8a3be1333d0bbab";
            String API_URL = String.format("https://data.fixer.io/api/latest?access_key=%s&base=%s&symbols=%s", API_KEY, "EUR", convertedToCurrency);*/

            if (baseCurrency.equals(convertedToCurrency))
            {
                input2.setText(input1.getText().toString());
                Toast.makeText(getApplicationContext(), "Please pick a currency to convert.", Toast.LENGTH_SHORT).show();
            }
            else
            {
                try
                {
                    /*BufferedReader in = new BufferedReader(new InputStreamReader(new URL(API_URL).openStream()));
                    String readLine;
                    StringBuilder input = new StringBuilder();

                    while ((readLine = in.readLine()) != null)
                    {
                        input.append(readLine);
                    }

                    JSONObject jsonObject = new JSONObject((input.toString()));
                    conversionRate = Double.parseDouble(jsonObject.getJSONObject("rates").getString(convertedToCurrency));*/
                    conversionRate1 = currencies.get(baseCurrency);
                    conversionRate2 = currencies.get(convertedToCurrency);
                    double result = Double.parseDouble(input1.getText().toString()) / conversionRate1 * conversionRate2;
                    input2.setText(String.valueOf(result));

                    Log.d("Main", baseCurrency);
                    Log.d("Main", convertedToCurrency);
                    Log.d("Main", String.valueOf(conversionRate1));
                    Log.d("Main", String.valueOf(conversionRate2));
                    //Log.d("Main", input.toString());
                }
                catch (Exception e)
                {
                    Log.e("Main", e.toString());
                }
            }
        //}
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        if (parent.getId() == R.id.spinner1)
        {
            baseCurrency = parent.getItemAtPosition(position).toString();
            getAPIResult();
        }
        else if (parent.getId() == R.id.spinner2)
        {
            convertedToCurrency = parent.getItemAtPosition(position).toString();
            getAPIResult();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void disableEditText(EditText editText) {
        editText.setFocusable(false);
        editText.setCursorVisible(false);
        editText.setKeyListener(null);
        editText.setBackgroundColor(Color.TRANSPARENT);
    }
}