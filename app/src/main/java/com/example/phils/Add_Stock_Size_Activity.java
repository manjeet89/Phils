package com.example.phils;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Add_Stock_Size_Activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    Spinner city,country;
    TextView id_type;
    ArrayList<String> countryList = new ArrayList<>();
    ArrayList<String> cityList = new ArrayList<>();
    ArrayAdapter<String> countryAdapter ;
    ArrayAdapter<String> cityAdapter;
    RequestQueue requestQueue;
    String url = "https://investment-wizards.com/manjeet/Phils_Stock/insert_category/fatch_spinner_stock/fatch_spinner_category_data_in_tbl_type.php";
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stock_size);
        requestQueue = Volley.newRequestQueue(this);

        country = findViewById(R.id.country);
        city = findViewById(R.id.city);
        id_type = findViewById(R.id.id_type);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {


                            JSONArray jsonArray = response.getJSONArray("data");
                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String category_id= jsonObject.optString("stock_category_id");
                                String category_name= jsonObject.optString("stock_category_name");
                                countryList.add(category_name);
                                countryAdapter = new ArrayAdapter<>(Add_Stock_Size_Activity.this,
                                        android.R.layout.simple_list_item_1,countryList);
                                countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                country.setAdapter(countryAdapter);

//                                country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                                    @Override
//                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                                        Object obj =   country.getAdapter().getItem(i);
//                                       //country.getId();
//
//                                        String value = obj.toString();
//
//                                        //Toast.makeText(Add_Stock_Size_Activity.this, value, Toast.LENGTH_SHORT).show();
//                                        try {
//                                            JSONArray jsonArray = response.getJSONArray("data");
//                                            for(int j=0;j<jsonArray.length();j++) {
//                                                JSONObject jsonObject = jsonArray.getJSONObject(i);
//                                                String category_id = jsonObject.optString("stock_category_id");
//                                                String category_name = jsonObject.optString("stock_category_name");
//                                                if(value.equals(category_name)){
//                                                  id = category_id;
//                                                     // Toast.makeText(Add_Stock_Size_Activity.this, id, Toast.LENGTH_SHORT).show();
//                                                    String d = id;
//                                                    id_type.setText(d);
//
//                                                }
//                                            }
//                                        } catch (JSONException e) {
//                                            e.printStackTrace();
//                                        }
//
//
//
//
//
//
//
//                                    }
//
//
//                                    @Override
//                                    public void onNothingSelected(AdapterView<?> adapterView) {
//
//                                    }
//                                });

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        String sp = id_type.getText().toString();

        requestQueue.add(jsonObjectRequest);
        country.setOnItemSelectedListener(this);


    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if(adapterView.getId() == R.id.country){
            cityList.clear();
            String selectCountry = adapterView.getSelectedItem().toString();
            String url1 = "https://investment-wizards.com/manjeet/Phils_Stock/insert_category/fatch_spinner_stock/fatch_spinner_category_and_type_data_in_tbl_size.php?stock_category_id="+selectCountry;
            requestQueue = Volley.newRequestQueue(this);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                    url1, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray jsonArray = response.getJSONArray("data");

                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String category_id= jsonObject.optString("stock_type_id");
                            String type_name= jsonObject.optString("stock_type_name");
                            cityList.add(type_name);
                            cityAdapter = new ArrayAdapter<>(Add_Stock_Size_Activity.this,
                                    android.R.layout.simple_list_item_1,cityList);
                            cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            country.setAdapter(cityAdapter);

                            city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    Object obj1 =   city.getAdapter().getItem(i);
                                    //country.getId();

                                    String value1 = obj1.toString();

                                    Toast.makeText(Add_Stock_Size_Activity.this, value1, Toast.LENGTH_SHORT).show();
//                                    try {
//                                        JSONArray jsonArray = response.getJSONArray("data");
//                                        for(int j=0;j<jsonArray.length();j++) {
//                                            JSONObject jsonObject = jsonArray.getJSONObject(i);
//                                            String category_id = jsonObject.optString("stock_category_id");
//                                            String category_name = jsonObject.optString("stock_category_name");
//                                            if(value.equals(category_name)){
//                                                id = category_id;
//                                                // Toast.makeText(Add_Stock_Size_Activity.this, id, Toast.LENGTH_SHORT).show();
//                                                String d = id;
//                                                id_type.setText(d);
//
//                                            }
//                                        }
//                                    } catch (JSONException e) {
//                                        e.printStackTrace();
//                                    }

                                }


                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            });

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            requestQueue.add(jsonObjectRequest);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}