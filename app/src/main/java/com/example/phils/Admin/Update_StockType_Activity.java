package com.example.phils.Admin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.phils.R;
import com.example.phils.Shareprefered.AppConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Update_StockType_Activity extends AppCompatActivity {
    TextView check_status,select_type,id12;
    ArrayList<String> arrayList1;
    Dialog dialog;
    Button insert_type;

    EditText type_insert;


    ArrayList<String> category = new ArrayList<>();

    ArrayAdapter<String> categoryAdapter;

    RequestQueue requestQueue;
    String url = "https://mployis.com/staging/api/stock/stock_category";

    TextView location_save;
    AppConfig appConfig;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_stock_type);

        requestQueue = Volley.newRequestQueue(this);

        String categoryselect = getIntent().getStringExtra("category");
        String name = getIntent().getStringExtra("name");
        String status = getIntent().getStringExtra("status");

        appConfig = new AppConfig(this);
        location_save = findViewById(R.id.location_save);
        String location_save1 = appConfig.getLocation();
        location_save.setText(location_save1);


        id12 = findViewById(R.id.set);

        //Button begging
        insert_type = findViewById(R.id.insert_type);
        insert_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Insert();
            }
        });

        //end button


        //begging category Spinner
        select_type = findViewById(R.id.select_category);
        type_insert = findViewById(R.id.type_insert);
        check_status = findViewById(R.id.type_status);

        select_type.setText(categoryselect);
        type_insert.setText(name);
        check_status.setText(status);

        String token = getIntent().getStringExtra("token");
        String userId = getIntent().getStringExtra("userId");
        String location = getIntent().getStringExtra("location");

        StringRequest request = new StringRequest(Request.Method.POST, url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            int j=0;

                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("message");

                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            for(int i=0;i<jsonArray.length();i++)
                            {
                                j++;
                                JSONObject object = jsonArray.getJSONObject(i);
                                String stock_category_id = object.getString("stock_category_id");
                                String stock_category_name = object.getString("stock_category_name");
                                category.add(stock_category_name);

                                categoryAdapter = new ArrayAdapter<>(Update_StockType_Activity.this,
                                        android.R.layout.simple_list_item_1, category);
                                categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                            }

                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Update_StockType_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap headers = new HashMap();
                headers.put("user_token",token);
                headers.put("user_id", userId);
                headers.put("project_location_id", location);

                return headers;
                //return super.getHeaders();
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(Update_StockType_Activity.this);
        requestQueue.add(request);



//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url
//                , null, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                try {
//                    JSONArray jsonArray = response.getJSONArray("data");
//
//                    for (int i = 0; i < jsonArray.length(); i++) {
//                        JSONObject jsonObject = jsonArray.getJSONObject(i);
//                        String category_name = jsonObject.optString("stock_category_name");
//                        String category_id = jsonObject.optString("stock_category_id");
//                        category.add(category_name);
//
//                        categoryAdapter = new ArrayAdapter<>(Update_StockType_Activity.this,
//                                android.R.layout.simple_list_item_1, category);
//                        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//                    }
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//
//            }
//
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        });
//
//        requestQueue.add(jsonObjectRequest);




        select_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Initialize dialog
                dialog = new Dialog(Update_StockType_Activity.this);

                // set custom dialog
                dialog.setContentView(R.layout.dialog_searchable_spinner_stock_type);

                // set custom height and width
                dialog.getWindow().setLayout(650, 800);

                // set transparent background
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                // show dialog
                dialog.show();

                // Initialize and assign variable
                EditText editText = dialog.findViewById(R.id.edit_text);
                ListView listView = dialog.findViewById(R.id.list_view);

                listView.setAdapter(categoryAdapter);
                editText.addTextChangedListener(new TextWatcher() {
                                                    @Override
                                                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                                    }

                                                    @Override
                                                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                                        categoryAdapter.getFilter().filter(charSequence);
                                                    }

                                                    @Override
                                                    public void afterTextChanged(Editable editable) {

                                                    }

                                                }

                );


                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        // when item selected from list
                        // set selected item on textView
                        select_type.setText(categoryAdapter.getItem(position));

                        // Dismiss dialog
                        dialog.dismiss();

                        String token = getIntent().getStringExtra("token");
                        String userId = getIntent().getStringExtra("userId");
                        String location = getIntent().getStringExtra("location");

                        StringRequest request = new StringRequest(Request.Method.POST, url,
                                new com.android.volley.Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {

                                        try {
                                            int j=0;

                                            JSONObject jsonObject = new JSONObject(response);
                                            String message = jsonObject.getString("message");

                                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                                            for(int i=0;i<jsonArray.length();i++)
                                            {
                                                j++;
                                                String ss = select_type.getText().toString();

                                                JSONObject object = jsonArray.getJSONObject(i);
                                                String stock_category_id = object.getString("stock_category_id");
                                                String stock_category_name = object.getString("stock_category_name");
                                                if(ss.equals(stock_category_name)){
                                                    String idea = stock_category_id;
                                                    id12.setText(idea);
                                                   // Toast.makeText(Update_StockType_Activity.this, idea, Toast.LENGTH_SHORT).show();

                                                }
                                            }
                                        }
                                        catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(Update_StockType_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        })
                        {
                            @Override
                            public Map<String, String> getHeaders() throws AuthFailureError {
                                HashMap headers = new HashMap();
                                headers.put("user_token",token);
                                headers.put("user_id", userId);
                                headers.put("project_location_id", location);

                                return headers;
                                //return super.getHeaders();
                            }
                        };

                        RequestQueue requestQueue = Volley.newRequestQueue(Update_StockType_Activity.this);
                        requestQueue.add(request);

                    }

                });



            }
        });



        //end catgory spinner





        // begging Status spinner

        arrayList1=new ArrayList<>();
        arrayList1.add("Enable");
        arrayList1.add("Disable");

        check_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Initialize dialog
                dialog=new Dialog(Update_StockType_Activity.this);

                // set custom dialog
                dialog.setContentView(R.layout.dialog_searchable_spinner_status);

                // set custom height and width
                dialog.getWindow().setLayout(650,800);

                // set transparent background
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                // show dialog
                dialog.show();

                // Initialize and assign variable
                EditText editText1=dialog.findViewById(R.id.edit_text1);
                ListView listViewstatus=dialog.findViewById(R.id.list_view_status);

                ArrayAdapter<String> adapter=new ArrayAdapter<>(Update_StockType_Activity.this, android.R.layout.simple_list_item_1,arrayList1);
                listViewstatus.setAdapter(adapter);
                editText1.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        adapter.getFilter().filter(charSequence);

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });
                listViewstatus.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        check_status.setText(adapter.getItem(i));

                        // Dismiss dialog
                        dialog.dismiss();
                    }
                });

            }
        });
        // end Status spinner
    }

    private void Insert() {

//        JsonObjectRequest jsonObjectRequest1 = new JsonObjectRequest(Request.Method.POST, url
//                , null, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                try {
//                    String ss = select_type.getText().toString();
//                    JSONArray jsonArray = response.getJSONArray("data");
//
//                    for (int i = 0; i < jsonArray.length(); i++) {
//                        JSONObject jsonObject = jsonArray.getJSONObject(i);
//                        String category_name = jsonObject.optString("stock_category_name");
//                        String category_id = jsonObject.optString("stock_category_id");
//                        if(ss.equals(category_name)){
//                            String idea = category_id;
//                            id12.setText(idea);
//                            //Toast.makeText(Add_Stock_Type_Activity.this, idea, Toast.LENGTH_SHORT).show();
//
//                        }
//                    }
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        });
//
//        requestQueue.add(jsonObjectRequest1);




        String e1 = id12.getText().toString().trim();
        String e2 = type_insert.getText().toString().trim();
        String e3 = check_status.getText().toString().trim();

        String token = getIntent().getStringExtra("token");
        String userId = getIntent().getStringExtra("userId");

        if (TextUtils.isEmpty(e1)) {
            id12.setError("Please Select Category Group");
            Toast.makeText(Update_StockType_Activity.this, "Please Select Category Group", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(e2)) {
            type_insert.setError("Please Enter your type Name");
            Toast.makeText(Update_StockType_Activity.this, "Please Enter your Category Name", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(e3)) {
            Toast.makeText(Update_StockType_Activity.this, "Please Select your Status", Toast.LENGTH_SHORT).show();
            return;
        } else {

            if (e3.equals("Enable")) {
                e3 = "1";
            } else {
                e3 = "0";
            }
            String e4 =  e1;
            String e5 = e2.toUpperCase(Locale.ROOT);
            String e6 = e3;
            String id = getIntent().getStringExtra("id");




            StringRequest request1 = new StringRequest(Request.Method.POST, "https://mployis.com/staging/api/stock/update_stock_type",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(response);
                                String message = jsonObject.getString("message");
                                Toast.makeText(Update_StockType_Activity.this, message, Toast.LENGTH_SHORT).show();

                                startActivity(new Intent(getApplicationContext(),StockTypeActivity.class));
                                //progressDialog.dismiss();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            // Toast.makeText(Update_StockCategory_Activity.this, response, Toast.LENGTH_SHORT).show();

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Update_StockType_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();


                }
            })
            {


                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap headers = new HashMap();
                    headers.put("user_token",token);
                    headers.put("user_id", userId);
                    return headers;
                }

                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<String,String>();
                    params.put("stock_type_id",id);
                    params.put("stock_category_id",e4);
                    params.put("stock_type_name",e5);
                    params.put("stock_type_status",e6);
                    return  params;
                }
            };
            RequestQueue requestQueue1 = Volley.newRequestQueue(this);
            requestQueue1.add(request1);


//            StringRequest request = new StringRequest(Request.Method.POST, "https://investment-wizards.com/manjeet/Phils_Stock/update_category/update_stock_type.php",
//                    new Response.Listener<String>() {
//                        @Override
//                        public void onResponse(String response) {
//                            Toast.makeText(Update_StockType_Activity.this, response, Toast.LENGTH_SHORT).show();
//                        }
//                    }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    Toast.makeText(Update_StockType_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
//
//                }
//            }) {
//                @Nullable
//                @Override
//                protected Map<String, String> getParams() throws AuthFailureError {
//                    Map<String, String> params = new HashMap<String, String>();
//                    params.put("stock_type_id", id);
//                    params.put("stock_category_id", e4);
//                    params.put("stock_type_name", e5);
//                    params.put("stock_type_status", e6);
//                    return params;
//                }
//            };
//            RequestQueue requestQueue = Volley.newRequestQueue(Update_StockType_Activity.this);
//            requestQueue.add(request);
//            Toast.makeText(this,id+"/"+e4+" / "+e5+" / "+e6 , Toast.LENGTH_SHORT).show();
        }
    }
}