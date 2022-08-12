package com.example.phils;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

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
    Spinner Country , city;
    ArrayList<String> category = new ArrayList<>();
    ArrayList<String> id = new ArrayList<>();
    ArrayList<String> citylist = new ArrayList<>();

    ArrayAdapter<String> categoryAdapter,cityAdaptor;
    RequestQueue requestQueue;
    String url = "https://investment-wizards.com/manjeet/Phils_Stock/insert_category/fatch_spinner_stock/fatch_spinner_category_data_in_tbl_type.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stock_size);
        requestQueue = Volley.newRequestQueue(this);
        Country = findViewById(R.id.country);
        city = findViewById(R.id.city);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url
                , null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("data");

                    for(int i=0;i<jsonArray.length();i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String category_name = jsonObject.optString("stock_category_name");
                        String category_id = jsonObject.optString("stock_category_id");
                        category.add(category_name);
                                id.add(category_id);

                        categoryAdapter = new ArrayAdapter<>(Add_Stock_Size_Activity.this,
                                android.R.layout.simple_list_item_1, category);
                        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        Country.setAdapter(categoryAdapter);

//                        listView.setAdapter(categoryAdapter);
//                        editText.addTextChangedListener(new TextWatcher() {
//                            @Override
//                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//                            }
//
//                            @Override
//                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                                categoryAdapter.getFilter().filter(charSequence);
//                            }
//
//                            @Override
//                            public void afterTextChanged(Editable editable) {
//
//                            }
//
//                        });


//                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                            @Override
//                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                                // when item selected from list
//                                // set selected item on textView
//                                select_type.setText(categoryAdapter.getItem(position));
//
//                                // Dismiss dialog
//                                dialog.dismiss();
//                            }
//                        });
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
        Country.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if(adapterView.getId() == R.id.country){
            citylist.clear();
            String selectCategory = adapterView.getSelectedItem().toString();
            String url = "https://investment-wizards.com/manjeet/Phils_Stock/insert_category/fatch_spinner_stock/fatch_spinner_category_and_type_data_in_tbl_size.php?stock_category_id="+selectCategory;
            requestQueue = Volley.newRequestQueue(this);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray jsonArray = response.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String type_name = jsonObject.optString("stock_type_name");
//                            String category_id = jsonObject.optString("stock_category_id");
                            citylist.add(type_name);
//                            category.add(category_id);

                            cityAdaptor = new ArrayAdapter<>(Add_Stock_Size_Activity.this,
                                    android.R.layout.simple_list_item_1, citylist);
                            cityAdaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                          if(cityAdaptor.isEmpty()) {
                              city.setAdapter(cityAdaptor);
                          }
                            {
                                citylist.add("Null");
                                ArrayAdapter<String> add = new ArrayAdapter<>(Add_Stock_Size_Activity.this,
                                        android.R.layout.simple_list_item_1,citylist);
                                city.setAdapter(add);
                            }
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