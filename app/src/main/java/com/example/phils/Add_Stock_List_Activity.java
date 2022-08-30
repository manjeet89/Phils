package com.example.phils;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
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
import com.example.phils.Shareprefered.AppConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Add_Stock_List_Activity extends AppCompatActivity {

    TextView setcategoryid,setypeid,setsizeid,setmakeid,setuomid;
    TextView select_category,select_type,select_size,select_make,select_uom;
    EditText stock_batch_name,invoice,distributor,safety,quantity,max_size_alloted,price;
    Button list_insert;

    ArrayList<String> categoryList = new ArrayList<>();
    ArrayList<String> typeList = new ArrayList<>();
    ArrayList<String> sizeList = new ArrayList<>();
    ArrayList<String> makeList = new ArrayList<>();
    ArrayList<String> uomList= new ArrayList<>();

    ArrayAdapter<String> categoryAdapter;
    ArrayAdapter<String> typeAdapter;
    ArrayAdapter<String> sizeAdapter;
    ArrayAdapter<String> makeAdapter;
    ArrayAdapter<String> uomAdapter;

    Dialog dialog;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;

    String categoryurl = "https://investment-wizards.com/manjeet/Phils_Stock/insert_category/fatch_spinner_stock/fatch_spinner_category_data_in_tbl_type.php";
    String typeurl = "https://investment-wizards.com/manjeet/Phils_Stock/insert_category/fatch_spinner_stock/fatch_spinner_category_and_type_data_in_tbl_size.php?stock_category_id=";
    String sizeurl = "https://investment-wizards.com/manjeet/Phils_Stock/insert_category/fatch_spinner_stock/fatch_spinner_size.php?stock_type_id=";
    String uomurl = "https://investment-wizards.com/manjeet/Phils_Stock/insert_category/fatch_spinner_stock/fatch_spinner_uom.php";
    String makeurl = "https://investment-wizards.com/manjeet/Phils_Stock/insert_category/fatch_spinner_stock/fatch_spinner_make.php";

    TextView location_save;
    AppConfig appConfig;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stock_list);
        requestQueue = Volley.newRequestQueue(this);

        appConfig = new AppConfig(this);
        location_save = findViewById(R.id.location_save);
        String location_save1 = appConfig.getLocation();
        location_save.setText(location_save1);

        setcategoryid = findViewById(R.id.setcategoryid);
        setypeid = findViewById(R.id.settypeid);
        setsizeid = findViewById(R.id.setsizeid);
        setmakeid = findViewById(R.id.setmakeid);
        setuomid = findViewById(R.id.setuomid);

        select_category = findViewById(R.id.select_category);
        select_type = findViewById(R.id.select_type);
        select_size = findViewById(R.id.select_size);
        select_make = findViewById(R.id.select_make);
        select_uom = findViewById(R.id.select_uom);

        stock_batch_name = findViewById(R.id.stock_batch_name);
        invoice = findViewById(R.id.invoice);
        distributor = findViewById(R.id.distributor);
        safety = findViewById(R.id.safety);
        quantity = findViewById(R.id.quantity);
        max_size_alloted = findViewById(R.id.max_size_alloted);
        price = findViewById(R.id.price);

        list_insert = findViewById(R.id.list_insert);
        list_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InsertData();
            }
        });

        fatchCategoryId();
        fatchMakeId();
        fatchUOMId();


    }

    private void fatchUOMId() {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, uomurl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONArray jsonArray = response.getJSONArray("data");
                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String uom_id= jsonObject.optString("uom_id");
                                String uom_name= jsonObject.optString("uom_name");
                                uomList.add(uom_name);
                                uomAdapter = new ArrayAdapter<>(Add_Stock_List_Activity.this,
                                        android.R.layout.simple_list_item_1,uomList);
                                uomAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

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

        select_uom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Initialize dialog
                dialog = new Dialog(Add_Stock_List_Activity.this);

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

                listView.setAdapter(uomAdapter);
                editText.addTextChangedListener(new TextWatcher() {
                                                    @Override
                                                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                                    }

                                                    @Override
                                                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                                        uomAdapter.getFilter().filter(charSequence);
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
                        select_uom.setText(uomAdapter.getItem(position));

                        // Dismiss dialog
                        dialog.dismiss();


//                            String s = select_category.getText().toString();
//                            Toast.makeText(Add_Stock_Make_Activity.this, s, Toast.LENGTH_SHORT).show();

                        JsonObjectRequest jsonObjectRequest1 = new JsonObjectRequest(Request.Method.POST, uomurl
                                , null, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    String ss = select_uom.getText().toString();
                                    JSONArray jsonArray = response.getJSONArray("data");

                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                                        String uom_name = jsonObject.optString("uom_name");
                                        String uom_id = jsonObject.optString("uom_id");
                                        if(ss.equals(uom_name)){
                                            String idea = uom_id;
                                            setuomid.setText(idea);
                                            //Toast.makeText(Add_Stock_List_Activity.this, idea, Toast.LENGTH_SHORT).show();
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

                        requestQueue.add(jsonObjectRequest1);
                    }
                });
            }
        });
    }

    private void fatchMakeId() {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, makeurl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONArray jsonArray = response.getJSONArray("data");
                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String make_id= jsonObject.optString("make_id");
                                String make_name= jsonObject.optString("make_name");
                                makeList.add(make_name);
                                makeAdapter = new ArrayAdapter<>(Add_Stock_List_Activity.this,
                                        android.R.layout.simple_list_item_1,makeList);
                                categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

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

        select_make.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Initialize dialog
                dialog = new Dialog(Add_Stock_List_Activity.this);

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

                listView.setAdapter(makeAdapter);
                editText.addTextChangedListener(new TextWatcher() {
                                                    @Override
                                                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                                    }

                                                    @Override
                                                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                                        makeAdapter.getFilter().filter(charSequence);
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
                        select_make.setText(makeAdapter.getItem(position));

                        // Dismiss dialog
                        dialog.dismiss();


//                            String s = select_category.getText().toString();
//                            Toast.makeText(Add_Stock_Make_Activity.this, s, Toast.LENGTH_SHORT).show();

                        JsonObjectRequest jsonObjectRequest1 = new JsonObjectRequest(Request.Method.POST, makeurl
                                , null, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    String ss = select_make.getText().toString();
                                    JSONArray jsonArray = response.getJSONArray("data");

                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                                        String make_name = jsonObject.optString("make_name");
                                        String make_id = jsonObject.optString("make_id");
                                        if(ss.equals(make_name)){
                                            String idea = make_id;
                                            setmakeid.setText(idea);
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

                        requestQueue.add(jsonObjectRequest1);
                    }
                });
            }
        });
    }

    private void fatchCategoryId() {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, categoryurl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONArray jsonArray = response.getJSONArray("data");
                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String category_id= jsonObject.optString("stock_category_id");
                                String category_name= jsonObject.optString("stock_category_name");
                                categoryList.add(category_name);
                                categoryAdapter = new ArrayAdapter<>(Add_Stock_List_Activity.this,
                                        android.R.layout.simple_list_item_1,categoryList);
                                categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

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

        select_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Initialize dialog
                dialog = new Dialog(Add_Stock_List_Activity.this);

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
                        select_category.setText(categoryAdapter.getItem(position));

                        // Dismiss dialog
                        dialog.dismiss();


//                            String s = select_category.getText().toString();
//                            Toast.makeText(Add_Stock_Make_Activity.this, s, Toast.LENGTH_SHORT).show();

                        JsonObjectRequest jsonObjectRequest1 = new JsonObjectRequest(Request.Method.POST, categoryurl
                                , null, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    String ss = select_category.getText().toString();
                                    JSONArray jsonArray = response.getJSONArray("data");

                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                                        String category_name = jsonObject.optString("stock_category_name");
                                        String category_id = jsonObject.optString("stock_category_id");
                                        if(ss.equals(category_name)){
                                            String idea = category_id;
                                            setcategoryid.setText(idea);
                                            CategoryIdPass(idea);

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

                        requestQueue.add(jsonObjectRequest1);
                    }
                });
            }
        });

    }

    private void CategoryIdPass(String idea) {
        select_type.setText("");
        typeList.clear();
        select_size.setText("");
        sizeList.clear();

        JsonObjectRequest jsonObjectRequest1 = new JsonObjectRequest(Request.Method.POST, typeurl+idea, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {


                            JSONArray jsonArray = response.getJSONArray("data");
                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String stock_type_id= jsonObject.optString("stock_type_id");
                                String stock_type_name1= jsonObject.optString("stock_type_name");
                                typeList.add(stock_type_name1);
                                typeAdapter = new ArrayAdapter<>(Add_Stock_List_Activity.this,
                                        android.R.layout.simple_list_item_1,typeList);
                                typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

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

        requestQueue.add(jsonObjectRequest1);


        select_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Initialize dialog
                dialog = new Dialog(Add_Stock_List_Activity.this);

                // set custom dialog
                dialog.setContentView(R.layout.dialog_searchable_spinner);

                // set custom height and width
                dialog.getWindow().setLayout(650, 800);

                // set transparent background
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                // show dialog
                dialog.show();

                // Initialize and assign variable
                EditText editText = dialog.findViewById(R.id.edit_text);
                ListView listView = dialog.findViewById(R.id.list_view);



                listView.setAdapter(typeAdapter);
                editText.addTextChangedListener(new TextWatcher() {
                                                    @Override
                                                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                                    }

                                                    @Override
                                                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                                        typeAdapter.getFilter().filter(charSequence);
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
                        select_type.setText(typeAdapter.getItem(position));

                        // Dismiss dialog
                        dialog.dismiss();


//                            String s = select_category.getText().toString();
//                            Toast.makeText(Add_Stock_Make_Activity.this, s, Toast.LENGTH_SHORT).show();

                        JsonObjectRequest jsonObjectRequest2 = new JsonObjectRequest(Request.Method.POST, typeurl+idea
                                , null, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    String ss = select_type.getText().toString();
                                    JSONArray jsonArray = response.getJSONArray("data");

                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                                        String stock_type_id= jsonObject.optString("stock_type_id");
                                        String stock_type_name= jsonObject.optString("stock_type_name");
                                        if(ss.equals(stock_type_name)){
                                            String idea = stock_type_id;
                                            setypeid.setText(idea);
                                            TypeIdPass(idea);
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

                        requestQueue.add(jsonObjectRequest2);
                    }
                });
            }
        });
    }

    private void TypeIdPass(String idea) {
//        Toast.makeText(this, idea, Toast.LENGTH_SHORT).show();
        select_size.setText("");
        sizeList.clear();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, sizeurl+idea, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONArray jsonArray = response.getJSONArray("data");
                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String stock_size_id= jsonObject.optString("stock_size_id");
                                String stock_size_name= jsonObject.optString("stock_size_name");
                                sizeList.add(stock_size_name);
                                sizeAdapter = new ArrayAdapter<>(Add_Stock_List_Activity.this,
                                        android.R.layout.simple_list_item_1,sizeList);
                                sizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

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


        select_size.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Initialize dialog
                dialog = new Dialog(Add_Stock_List_Activity.this);

                // set custom dialog
                dialog.setContentView(R.layout.dialog_searchable_spinner);

                // set custom height and width
                dialog.getWindow().setLayout(650, 800);

                // set transparent background
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                // show dialog
                dialog.show();

                // Initialize and assign variable
                EditText editText = dialog.findViewById(R.id.edit_text);
                ListView listView = dialog.findViewById(R.id.list_view);



                listView.setAdapter(sizeAdapter);
                editText.addTextChangedListener(new TextWatcher() {
                                                    @Override
                                                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                                    }

                                                    @Override
                                                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                                        sizeAdapter.getFilter().filter(charSequence);
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
                        select_size.setText(sizeAdapter.getItem(position));

                        // Dismiss dialog
                        dialog.dismiss();


//                            String s = select_category.getText().toString();
//                            Toast.makeText(Add_Stock_Make_Activity.this, s, Toast.LENGTH_SHORT).show();

                        JsonObjectRequest jsonObjectRequest1 = new JsonObjectRequest(Request.Method.POST, sizeurl+idea
                                , null, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    String ss = select_size.getText().toString();
                                    JSONArray jsonArray = response.getJSONArray("data");

                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                                        String stock_size_id= jsonObject.optString("stock_size_id");
                                        String stock_size_name= jsonObject.optString("stock_size_name");
                                        if(ss.equals(stock_size_name)){
                                            String idea = stock_size_id;
                                            setsizeid.setText(idea);
                                            //TypeIdPass(idea);
//                                            Toast.makeText(Add_Stock_List_Activity.this, idea, Toast.LENGTH_SHORT).show();
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

                        requestQueue.add(jsonObjectRequest1);
                    }
                });
            }
        });
    }

    private void InsertData() {
        String category,type,size,batchno,invoiceinsert,distributorinsert,makeinsert,uom,safetyinsert,quantityinsert,max_all,priceinsert;

        category = setcategoryid.getText().toString().trim();
        type     = setypeid.getText().toString().trim();
        size     = setsizeid.getText().toString().trim();
        batchno  = stock_batch_name.getText().toString();
        invoiceinsert  = invoice.getText().toString();
        distributorinsert    = distributor.getText().toString();
        makeinsert      = setmakeid.getText().toString();
        uom       = setuomid.getText().toString().trim();
        safetyinsert    = safety.getText().toString().trim();
        quantityinsert =  quantity.getText().toString().trim();
        max_all  = max_size_alloted.getText().toString().trim();
        priceinsert     = price.getText().toString().trim();

        if(TextUtils.isEmpty(category))
        {
            Toast.makeText(Add_Stock_List_Activity.this, "Please Select Category ", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(TextUtils.isEmpty(type))
        {
            Toast.makeText(Add_Stock_List_Activity.this, "Please Select type", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(TextUtils.isEmpty(uom))
        {
            Toast.makeText(Add_Stock_List_Activity.this, "Please Select Uom", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(TextUtils.isEmpty(safetyinsert))
        {
            Toast.makeText(Add_Stock_List_Activity.this, "Please Enter safety stock", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(TextUtils.isEmpty(quantityinsert))
        {
            Toast.makeText(Add_Stock_List_Activity.this, "Please Enter Quantity", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(TextUtils.isEmpty(max_all))
        {
            Toast.makeText(Add_Stock_List_Activity.this, "Please Maximum Size Allotted", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(TextUtils.isEmpty(priceinsert))
        {
            Toast.makeText(Add_Stock_List_Activity.this, "Please Enter Price", Toast.LENGTH_SHORT).show();
            return;
        }
        else {

            StringRequest request = new StringRequest(Request.Method.POST, "https://investment-wizards.com/manjeet/Phils_Stock/insert_category/add_category_list.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(Add_Stock_List_Activity.this, response, Toast.LENGTH_SHORT).show();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Add_Stock_List_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

                }
            }) {
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("stock_category_id", category);
                    params.put("stock_type_id", type);
                    params.put("stock_size_id", size);
                    params.put("stock_batch_number", batchno);
                    params.put("stock_invoice_number", invoiceinsert);
                    params.put("stock_distributor_name", distributorinsert);
                    params.put("stock_make_id", makeinsert);
                    params.put("stock_uom_id", uom);
                    params.put("safety_stock", safetyinsert);
                    params.put("stock_quantity", quantityinsert);
                    params.put("stock_lot", max_all);
                    params.put("stock_price", priceinsert);
                    return params;
                }
            };
            //        Toast.makeText(this, category+"/"+type+"/"+size+"/"+batchno+"/"+invoiceinsert
//                +"/"+distributorinsert+"/"+makeinsert+"/"+uom+"/"+safetyinsert+"/"+quantityinsert
//                +"/"+max_all+"/"+priceinsert, Toast.LENGTH_SHORT).show();

            RequestQueue requestQueue = Volley.newRequestQueue(Add_Stock_List_Activity.this);
            requestQueue.add(request);
        }

    }
}