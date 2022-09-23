package com.example.phils.Admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.example.phils.Adapter.StockUOMAdapterClass;
import com.example.phils.CategorySpinner;
import com.example.phils.LoginActivity;
import com.example.phils.ProfileActivity;
import com.example.phils.R;
import com.example.phils.Shareprefered.AppConfig;
import com.example.phils.StockMakeSpinner;
import com.example.phils.StockSizeSpinner;
import com.example.phils.StockTypeSpinner;
import com.example.phils.StockUOMSpinner;
import com.example.phils.Update_StockList_Activity;
import com.example.phils.UserActivity;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

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

    ArrayList<StockTypeSpinner> stockTypeSpinners = new ArrayList<StockTypeSpinner>();
    ArrayAdapter<StockTypeSpinner> StockTypeSpinnerAdpter;

    ArrayList<CategorySpinner> categorySpinners = new ArrayList<CategorySpinner>();
    ArrayAdapter<CategorySpinner> spinnerArrayAdapter;

    ArrayList<StockSizeSpinner> stockSizeSpinners = new ArrayList<StockSizeSpinner>();
    ArrayAdapter<StockSizeSpinner> sizeSpinnerArrayAdapter;

    ArrayList<StockUOMSpinner> stockUOMSpinners = new ArrayList<StockUOMSpinner>();
    ArrayAdapter<StockUOMSpinner> uomSpinnerArrayAdapter;

    ArrayList<StockMakeSpinner> stockMakeSpinners = new ArrayList<StockMakeSpinner>();
    ArrayAdapter<StockMakeSpinner> makeSpinnerArrayAdapter;

    Dialog dialog;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;

    String categoryurl = "https://mployis.com/staging/api/stock/stock_category";
    String categoryIdurl="https://mployis.com/staging/api/stock/get_stock_type_from_category_id";
    String typeIdurl="https://mployis.com/staging/api/stock/get_stock_size_from_type_id";

//    String typeurl = "https://investment-wizards.com/manjeet/Phils_Stock/insert_category/fatch_spinner_stock/fatch_spinner_category_and_type_data_in_tbl_size.php?stock_category_id=";
//    String sizeurl = "https://investment-wizards.com/manjeet/Phils_Stock/insert_category/fatch_spinner_stock/fatch_spinner_size.php?stock_type_id=";
    String uomurl = "https://mployis.com/staging/api/stock/stock_uom";
    String makeurl = "https://mployis.com/staging/api/stock/stock_make";
    ImageView img,profile;

    TextView location_save;
    AppConfig appConfig;



    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), StockListActivity.class));
    }

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

        fatchMakeId();
        fatchUOMId();

        MaterialToolbar toolbar = findViewById(R.id.topAppbar);
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigation_view);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                drawerLayout.closeDrawer(GravityCompat.START);
                switch (id)
                {
                    case R.id.ghar:
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        break;

                    case R.id.user:
                        startActivity(new Intent(getApplicationContext(), UserActivity.class));
                        break;

                    case R.id.category_stock:
                        startActivity(new Intent(getApplicationContext(),StockCategoryActivity.class));
                        break;

                    case R.id.type_stock:
                        startActivity(new Intent(getApplicationContext(),StockTypeActivity.class));
                        break;

                    case R.id.size_stock:
                        startActivity(new Intent(getApplicationContext(), StockSizeActivity.class));
                        break;

                    case R.id.make_stock:
                        startActivity(new Intent(getApplicationContext(),StockMakeActivity.class));
                        break;

                    case R.id.umo_stock:
                        startActivity(new Intent(getApplicationContext(),StockUomActivity.class));
                        break;

                    case R.id.list_stock:
                        startActivity(new Intent(getApplicationContext(),StockListActivity.class));
                        break;

                    case R.id.category_job:
                        startActivity(new Intent(getApplicationContext(), Job_Category_Activity.class));
                        break;

                    case R.id.Size_job:
                        startActivity(new Intent(getApplicationContext(), Job_Size_Activity.class));
                        break;

                    default:
                        return true;
                }
                return true;
            }
        });

        profile = findViewById(R.id.profile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
                //Toast.makeText(MainActivity.this, "desh", Toast.LENGTH_SHORT).show();
                Dialog dialog=new Dialog(Add_Stock_List_Activity.this);

                // set custom dialog
                dialog.setContentView(R.layout.custom_profile_dialog);

                // set custom height and width
                dialog.getWindow().setLayout(750,1050);

                // set transparent background
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                // show dialog
                dialog.show();

                String emp_name = appConfig.getemp_type_name();
                String fullName = appConfig.getuser_full_name();

                TextView nameAdmin = dialog.findViewById(R.id.nameAdmin);
                TextView post = dialog.findViewById(R.id.postAdmin);
                nameAdmin.setText(fullName);
                post.setText(emp_name);




                Button logout = dialog.findViewById(R.id.logout);
                logout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        appConfig.updateUserLoginStatus(false);
                        startActivity(new Intent(Add_Stock_List_Activity.this, LoginActivity.class));
                        finish();
                    }
                });
                TextView textView = dialog.findViewById(R.id.my_profile);
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                    }
                });
                TextView ChangePassword = dialog.findViewById(R.id.change_pas);
                ChangePassword.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(getApplicationContext(), ChangePasswordActivity.class));
                    }
                });

            }
        });
        String token = getIntent().getStringExtra("token");
        String userId = getIntent().getStringExtra("userId");
        String location = getIntent().getStringExtra("location");


        StringRequest request = new StringRequest(Request.Method.POST, categoryurl,
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

                                categorySpinners.add(new CategorySpinner(stock_category_id,stock_category_name));
                                spinnerArrayAdapter = new ArrayAdapter<CategorySpinner>(Add_Stock_List_Activity.this,
                                        android.R.layout.simple_spinner_dropdown_item,categorySpinners);
                                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


//                                categoryList.add(stock_category_name);
//                                categoryAdapter = new ArrayAdapter<>(Add_Stock_List_Activity.this,
//                                        android.R.layout.simple_list_item_1, categoryList);
//                                categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                            }

                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Add_Stock_List_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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

        RequestQueue requestQueue = Volley.newRequestQueue(Add_Stock_List_Activity.this);
        requestQueue.add(request);


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

                listView.setAdapter(spinnerArrayAdapter);
                editText.addTextChangedListener(new TextWatcher() {
                                                    @Override
                                                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                                    }

                                                    @Override
                                                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                                        spinnerArrayAdapter.getFilter().filter(charSequence);
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

                        CategorySpinner spn = (CategorySpinner) parent.getItemAtPosition(position);
                        select_category.setText(spn.stock_category_name);
                        setcategoryid.setText(spn.stock_category_id);
                        CategoryIdPass(spn.stock_category_id);

//                        select_category.setText(categoryAdapter.getItem(position));
//
//                        StringRequest request = new StringRequest(Request.Method.POST, categoryurl,
//                                new com.android.volley.Response.Listener<String>() {
//                                    @Override
//                                    public void onResponse(String response) {
//
//                                        try {
//                                            int j=0;
//
//                                            JSONObject jsonObject = new JSONObject(response);
//                                            String message = jsonObject.getString("message");
//
//                                            JSONArray jsonArray = jsonObject.getJSONArray("data");
//                                            for(int i=0;i<jsonArray.length();i++)
//                                            {
//                                                j++;
//                                                String ss = select_category.getText().toString();
//
//                                                JSONObject object = jsonArray.getJSONObject(i);
//                                                String stock_category_id = object.getString("stock_category_id");
//                                                String stock_category_name = object.getString("stock_category_name");
//                                                if(ss.equals(stock_category_name)){
//                                                    String idea = stock_category_id;
//                                                    setcategoryid.setText(idea);
//                                                    CategoryIdPass(idea);
//                                                    Toast.makeText(Add_Stock_List_Activity.this, idea, Toast.LENGTH_SHORT).show();
//
//                                                }
//                                            }
//                                        }
//                                        catch (JSONException e) {
//                                            e.printStackTrace();
//                                        }
//                                    }
//                                }, new Response.ErrorListener() {
//                            @Override
//                            public void onErrorResponse(VolleyError error) {
//                                Toast.makeText(Add_Stock_List_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
//                            }
//                        })
//                        {
//                            @Override
//                            public Map<String, String> getHeaders() throws AuthFailureError {
//                                HashMap headers = new HashMap();
//                                headers.put("user_token",token);
//                                headers.put("user_id", userId);
//                                headers.put("project_location_id", location);
//
//                                return headers;
//                                //return super.getHeaders();
//                            }
//                        };
//
//                        RequestQueue requestQueue = Volley.newRequestQueue(Add_Stock_List_Activity.this);
//                        requestQueue.add(request);

                        // Dismiss dialog
                        dialog.dismiss();


//                        JsonObjectRequest jsonObjectRequest1 = new JsonObjectRequest(Request.Method.POST, categoryurl
//                                , null, new Response.Listener<JSONObject>() {
//                            @Override
//                            public void onResponse(JSONObject response) {
//                                try {
//                                    String ss = select_category.getText().toString();
//                                    JSONArray jsonArray = response.getJSONArray("data");
//
//                                    for (int i = 0; i < jsonArray.length(); i++) {
//                                        JSONObject jsonObject = jsonArray.getJSONObject(i);
//                                        String category_name = jsonObject.optString("stock_category_name");
//                                        String category_id = jsonObject.optString("stock_category_id");
//                                        if(ss.equals(category_name)){
//                                            String idea = category_id;
//                                            setcategoryid.setText(idea);
//                                            CategoryIdPass(idea);
//
//                                        }
//                                    }
//
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//
//                        }, new Response.ErrorListener() {
//                            @Override
//                            public void onErrorResponse(VolleyError error) {
//
//                            }
//                        });
//
//                        requestQueue.add(jsonObjectRequest1);
                    }
                });
            }
        });

    }

    private void fatchUOMId() {
        String token = getIntent().getStringExtra("token");
        String userId = getIntent().getStringExtra("userId");
        String location = getIntent().getStringExtra("location");


        StringRequest request = new StringRequest(Request.Method.POST, uomurl,
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
                                String uom_id = object.getString("uom_id");
                                String uom_name = object.getString("uom_name");

                                stockUOMSpinners.add(new StockUOMSpinner(uom_id,uom_name));
                                uomSpinnerArrayAdapter = new ArrayAdapter<>(Add_Stock_List_Activity.this,
                                        android.R.layout.simple_list_item_1,stockUOMSpinners);
                                uomSpinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                                uomList.add(uom_name);
//
//                                uomAdapter = new ArrayAdapter<>(Add_Stock_List_Activity.this,
//                                        android.R.layout.simple_list_item_1,uomList);
//                                uomAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                            }

                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Add_Stock_List_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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

        RequestQueue requestQueue = Volley.newRequestQueue(Add_Stock_List_Activity.this);
        requestQueue.add(request);
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, uomurl, null,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        try {
//
//                            JSONArray jsonArray = response.getJSONArray("data");
//                            for(int i=0;i<jsonArray.length();i++){
//                                JSONObject jsonObject = jsonArray.getJSONObject(i);
//                                String uom_id= jsonObject.optString("uom_id");
//                                String uom_name= jsonObject.optString("uom_name");
//                                uomList.add(uom_name);
//                                uomAdapter = new ArrayAdapter<>(Add_Stock_List_Activity.this,
//                                        android.R.layout.simple_list_item_1,uomList);
//                                uomAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        });
//
//        requestQueue.add(jsonObjectRequest);

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

                listView.setAdapter(uomSpinnerArrayAdapter);
                editText.addTextChangedListener(new TextWatcher() {
                                                    @Override
                                                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                                    }

                                                    @Override
                                                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                                        uomSpinnerArrayAdapter.getFilter().filter(charSequence);
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

                        StockUOMSpinner stockUOMSpinner = (StockUOMSpinner) parent.getItemAtPosition(position);
                        select_uom.setText(stockUOMSpinner.uom_name);
                        setuomid.setText(stockUOMSpinner.uom_id);

//                        select_uom.setText(uomAdapter.getItem(position));
//
//                        String token = getIntent().getStringExtra("token");
//                        String userId = getIntent().getStringExtra("userId");
//                        String location = getIntent().getStringExtra("location");
//
//
//                        StringRequest request = new StringRequest(Request.Method.POST, uomurl,
//                                new com.android.volley.Response.Listener<String>() {
//                                    @Override
//                                    public void onResponse(String response) {
//
//                                        try {
//                                         String ss = select_uom.getText().toString();
//
//                                            JSONObject jsonObject = new JSONObject(response);
//                                            String message = jsonObject.getString("message");
//
//                                            JSONArray jsonArray = jsonObject.getJSONArray("data");
//                                            for(int i=0;i<jsonArray.length();i++)
//                                            {
//
//                                                JSONObject object = jsonArray.getJSONObject(i);
//                                                String uom_id = object.getString("uom_id");
//                                                String uom_name = object.getString("uom_name");
//
//                                                if(ss.equals(uom_name)){
//                                                    String idea = uom_id;
//                                                    setuomid.setText(idea);
//                                                    Toast.makeText(Add_Stock_List_Activity.this, idea, Toast.LENGTH_SHORT).show();
//                                        }
//                                            }
//
//                                        }
//                                        catch (JSONException e) {
//                                            e.printStackTrace();
//                                        }
//                                    }
//                                }, new Response.ErrorListener() {
//                            @Override
//                            public void onErrorResponse(VolleyError error) {
//                                Toast.makeText(Add_Stock_List_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
//                            }
//                        })
//                        {
//                            @Override
//                            public Map<String, String> getHeaders() throws AuthFailureError {
//                                HashMap headers = new HashMap();
//                                headers.put("user_token",token);
//                                headers.put("user_id", userId);
//                                headers.put("project_location_id", location);
//
//                                return headers;
//                                //return super.getHeaders();
//                            }
//                        };
//
//                        RequestQueue requestQueue = Volley.newRequestQueue(Add_Stock_List_Activity.this);
//                        requestQueue.add(request);

                        // Dismiss dialog
                        dialog.dismiss();

//                        JsonObjectRequest jsonObjectRequest1 = new JsonObjectRequest(Request.Method.POST, uomurl
//                                , null, new Response.Listener<JSONObject>() {
//                            @Override
//                            public void onResponse(JSONObject response) {
//                                try {
//                                    String ss = select_uom.getText().toString();
//                                    JSONArray jsonArray = response.getJSONArray("data");
//
//                                    for (int i = 0; i < jsonArray.length(); i++) {
//                                        JSONObject jsonObject = jsonArray.getJSONObject(i);
//                                        String uom_name = jsonObject.optString("uom_name");
//                                        String uom_id = jsonObject.optString("uom_id");
//                                        if(ss.equals(uom_name)){
//                                            String idea = uom_id;
//                                            setuomid.setText(idea);
//                                            //Toast.makeText(Add_Stock_List_Activity.this, idea, Toast.LENGTH_SHORT).show();
//                                        }
//                                    }
//
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//
//                        }, new Response.ErrorListener() {
//                            @Override
//                            public void onErrorResponse(VolleyError error) {
//
//                            }
//                        });
//
//                        requestQueue.add(jsonObjectRequest1);
                    }
                });
            }
        });
    }

    private void fatchMakeId() {

        String token = getIntent().getStringExtra("token");
        String userId = getIntent().getStringExtra("userId");
        String location = getIntent().getStringExtra("location");


        StringRequest request = new StringRequest(Request.Method.POST, makeurl,
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
                                String make_id = object.getString("make_id");
                                String make_name = object.getString("make_name");

                                stockMakeSpinners.add(new StockMakeSpinner(make_id,make_name));
                                makeSpinnerArrayAdapter = new ArrayAdapter<>(Add_Stock_List_Activity.this,
                                        android.R.layout.simple_list_item_1, stockMakeSpinners);
                                makeSpinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                                makeList.add(make_name);
//                                makeAdapter = new ArrayAdapter<>(Add_Stock_List_Activity.this,
//                                        android.R.layout.simple_list_item_1,makeList);
//                                makeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
                            }

                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Add_Stock_List_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(Add_Stock_List_Activity.this);
        requestQueue.add(request);

//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, makeurl, null,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        try {
//
//                            JSONArray jsonArray = response.getJSONArray("data");
//                            for(int i=0;i<jsonArray.length();i++){
//                                JSONObject jsonObject = jsonArray.getJSONObject(i);
//                                String make_id= jsonObject.optString("make_id");
//                                String make_name= jsonObject.optString("make_name");
//                                makeList.add(make_name);
//                                makeAdapter = new ArrayAdapter<>(Add_Stock_List_Activity.this,
//                                        android.R.layout.simple_list_item_1,makeList);
//                                categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        });
//
//        requestQueue.add(jsonObjectRequest);

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

                listView.setAdapter(makeSpinnerArrayAdapter);
                editText.addTextChangedListener(new TextWatcher() {
                                                    @Override
                                                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                                    }

                                                    @Override
                                                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                                        makeSpinnerArrayAdapter.getFilter().filter(charSequence);
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

                        StockMakeSpinner stockMakeSpinner = (StockMakeSpinner) parent.getItemAtPosition(position);
                        select_make.setText(stockMakeSpinner.make_name);
                        setmakeid.setText(stockMakeSpinner.make_id);
//                        select_make.setText(makeAdapter.getItem(position));
//                        String token = getIntent().getStringExtra("token");
//                        String userId = getIntent().getStringExtra("userId");
//                        String location = getIntent().getStringExtra("location");
//
//
//                        StringRequest request = new StringRequest(Request.Method.POST, makeurl,
//                                new com.android.volley.Response.Listener<String>() {
//                                    @Override
//                                    public void onResponse(String response) {
//
//                                        try {
//                                            String ss = select_make.getText().toString();
//
//                                            JSONObject jsonObject = new JSONObject(response);
//                                            String message = jsonObject.getString("message");
//
//                                            JSONArray jsonArray = jsonObject.getJSONArray("data");
//                                            for(int i=0;i<jsonArray.length();i++)
//                                            {
//
//                                                JSONObject object = jsonArray.getJSONObject(i);
//                                                String make_id = object.getString("make_id");
//                                                String make_name = object.getString("make_name");
//
//                                                if(ss.equals(make_name)){
//                                                    String idea = make_id;
//                                                    setmakeid.setText(idea);
//                                                    Toast.makeText(Add_Stock_List_Activity.this, idea, Toast.LENGTH_SHORT).show();
//                                                }
//                                            }
//
//                                        }
//                                        catch (JSONException e) {
//                                            e.printStackTrace();
//                                        }
//                                    }
//                                }, new Response.ErrorListener() {
//                            @Override
//                            public void onErrorResponse(VolleyError error) {
//                                Toast.makeText(Add_Stock_List_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
//                            }
//                        })
//                        {
//                            @Override
//                            public Map<String, String> getHeaders() throws AuthFailureError {
//                                HashMap headers = new HashMap();
//                                headers.put("user_token",token);
//                                headers.put("user_id", userId);
//                                headers.put("project_location_id", location);
//
//                                return headers;
//                                //return super.getHeaders();
//                            }
//                        };
//
//                        RequestQueue requestQueue = Volley.newRequestQueue(Add_Stock_List_Activity.this);
//                        requestQueue.add(request);
                        // Dismiss dialog
                        dialog.dismiss();

//                        JsonObjectRequest jsonObjectRequest1 = new JsonObjectRequest(Request.Method.POST, makeurl
//                                , null, new Response.Listener<JSONObject>() {
//                            @Override
//                            public void onResponse(JSONObject response) {
//                                try {
//                                    String ss = select_make.getText().toString();
//                                    JSONArray jsonArray = response.getJSONArray("data");
//
//                                    for (int i = 0; i < jsonArray.length(); i++) {
//                                        JSONObject jsonObject = jsonArray.getJSONObject(i);
//                                        String make_name = jsonObject.optString("make_name");
//                                        String make_id = jsonObject.optString("make_id");
//                                        if(ss.equals(make_name)){
//                                            String idea = make_id;
//                                            setmakeid.setText(idea);
//                                        }
//                                    }
//
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//
//                        }, new Response.ErrorListener() {
//                            @Override
//                            public void onErrorResponse(VolleyError error) {
//
//                            }
//                        });
//
//                        requestQueue.add(jsonObjectRequest1);
                    }
                });
            }
        });
    }



    private void CategoryIdPass(String idea) {
        select_type.setText("");
        stockTypeSpinners.clear();

        select_size.setText("");
        sizeList.clear();

        String token = getIntent().getStringExtra("token");
        String userId = getIntent().getStringExtra("userId");
        String location = getIntent().getStringExtra("location");

        StringRequest request = new StringRequest(Request.Method.POST, categoryIdurl,
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
                                String stock_type_id = object.getString("stock_type_id");
                                String stock_type_name = object.getString("stock_type_name");

                                stockTypeSpinners.add(new StockTypeSpinner(stock_type_id,stock_type_name));
                                StockTypeSpinnerAdpter = new ArrayAdapter<StockTypeSpinner>(Add_Stock_List_Activity.this,
                                        android.R.layout.simple_list_item_1,stockTypeSpinners );
                                StockTypeSpinnerAdpter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


//                                typeList.add(stock_type_name);
//                                typeAdapter = new ArrayAdapter<>(Add_Stock_List_Activity.this,
//                                        android.R.layout.simple_list_item_1,typeList);
//                                typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
                            }
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Add_Stock_List_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        })
        {

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                params.put("stock_category_id",idea);
                return params;
            }

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

        RequestQueue requestQueue = Volley.newRequestQueue(Add_Stock_List_Activity.this);
        requestQueue.add(request);


        select_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Initialize dialog
                dialog = new Dialog(Add_Stock_List_Activity.this);

                // set custom dialog
                dialog.setContentView(R.layout.dialog_searchable_spinner);

                // set custom height and width
                dialog.getWindow().setLayout(850, 1000);

                // set transparent background
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                // show dialog
                dialog.show();

                // Initialize and assign variable
                EditText editText = dialog.findViewById(R.id.edit_text);
                ListView listView = dialog.findViewById(R.id.list_view);



                listView.setAdapter(StockTypeSpinnerAdpter);
                editText.addTextChangedListener(new TextWatcher() {
                                                    @Override
                                                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                                    }

                                                    @Override
                                                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                                        StockTypeSpinnerAdpter.getFilter().filter(charSequence);
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

                        StockTypeSpinner stockTypeSpinner = (StockTypeSpinner) parent.getItemAtPosition(position);
                        select_type.setText(stockTypeSpinner.stock_type_name);
                        setypeid.setText(stockTypeSpinner.stock_type_id);
                        TypeIdPass(stockTypeSpinner.stock_type_id);

//                        select_type.setText(typeAdapter.getItem(position));
//
//                        String token = getIntent().getStringExtra("token");
//                        String userId = getIntent().getStringExtra("userId");
//                        String location = getIntent().getStringExtra("location");
//
//                        StringRequest request = new StringRequest(Request.Method.POST, categoryIdurl,
//                                new com.android.volley.Response.Listener<String>() {
//                                    @Override
//                                    public void onResponse(String response) {
//
//                                        try {
//                                            int j=0;
//                                            String ss = select_type.getText().toString();
//                                            JSONObject jsonObject = new JSONObject(response);
//                                            String message = jsonObject.getString("message");
//
//                                            JSONArray jsonArray = jsonObject.getJSONArray("data");
//                                            for(int i=0;i<jsonArray.length();i++)
//                                            {
//                                                j++;
//                                                JSONObject object = jsonArray.getJSONObject(i);
//                                                String stock_type_id = object.getString("stock_type_id");
//                                                String stock_type_name = object.getString("stock_type_name");
//                                                if(ss.equals(stock_type_name)){
//                                                    String idea = stock_type_id;
//                                                    setypeid.setText(idea);
//                                                    TypeIdPass(idea);
//
//                                                    Toast.makeText(Add_Stock_List_Activity.this, idea, Toast.LENGTH_SHORT).show();
//
//                                                }
//                                            }
//                                        }
//                                        catch (JSONException e) {
//                                            e.printStackTrace();
//                                        }
//                                    }
//                                }, new Response.ErrorListener() {
//                            @Override
//                            public void onErrorResponse(VolleyError error) {
//                                Toast.makeText(Add_Stock_List_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
//                            }
//                        })
//                        {
//
//                            @Nullable
//                            @Override
//                            protected Map<String, String> getParams() throws AuthFailureError {
//                                Map<String,String> params = new HashMap<String,String>();
//                                params.put("stock_category_id",idea);
//                                return params;
//                            }
//
//                            @Override
//                            public Map<String, String> getHeaders() throws AuthFailureError {
//                                HashMap headers = new HashMap();
//                                headers.put("user_token",token);
//                                headers.put("user_id", userId);
//                                headers.put("project_location_id", location);
//
//                                return headers;
//
//
//
//
//                                //return super.getHeaders();
//                            }
//                        };
//
//                        RequestQueue requestQueue = Volley.newRequestQueue(Add_Stock_List_Activity.this);
//                        requestQueue.add(request);

                        // Dismiss dialog
                        dialog.dismiss();


                    }
                });
            }
        });



    }

    private void TypeIdPass(String idea) {
        Toast.makeText(this, idea, Toast.LENGTH_SHORT).show();
        select_size.setText("");
        stockSizeSpinners.clear();

        String token = getIntent().getStringExtra("token");
        String userId = getIntent().getStringExtra("userId");
        String location = getIntent().getStringExtra("location");

        StringRequest request = new StringRequest(Request.Method.POST, typeIdurl,
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
                                String stock_size_id = object.getString("stock_size_id");
                                String stock_size_name = object.getString("stock_size_name");

                                stockSizeSpinners.add(new StockSizeSpinner(stock_size_id,stock_size_name));
                                sizeSpinnerArrayAdapter = new ArrayAdapter<StockSizeSpinner>(Add_Stock_List_Activity.this,
                                        android.R.layout.simple_list_item_1,stockSizeSpinners);
                                sizeSpinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

//                                sizeList.add(stock_size_name);
//                                sizeAdapter = new ArrayAdapter<>(Add_Stock_List_Activity.this,
//                                        android.R.layout.simple_list_item_1,sizeList);
//                                sizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                            }
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Add_Stock_List_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        })
        {

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                params.put("stock_type_id",idea);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap headers = new HashMap();
                headers.put("user_token",token);
                headers.put("user_id", userId);
                headers.put("project_location_id", location);

                return headers;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(Add_Stock_List_Activity.this);
        requestQueue.add(request);

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



                listView.setAdapter(sizeSpinnerArrayAdapter);
                editText.addTextChangedListener(new TextWatcher() {
                                                    @Override
                                                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                                    }

                                                    @Override
                                                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                                        sizeSpinnerArrayAdapter.getFilter().filter(charSequence);
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

                        StockSizeSpinner stockSizeSpinner = (StockSizeSpinner) parent.getItemAtPosition(position);
                        select_size.setText(stockSizeSpinner.stock_size_name);
                        setsizeid.setText(stockSizeSpinner.stock_size_id);

//                        select_size.setText(sizeAdapter.getItem(position));
//
//                        String token = getIntent().getStringExtra("token");
//                        String userId = getIntent().getStringExtra("userId");
//                        String location = getIntent().getStringExtra("location");
//
//                        StringRequest request = new StringRequest(Request.Method.POST, typeIdurl,
//                                new com.android.volley.Response.Listener<String>() {
//                                    @Override
//                                    public void onResponse(String response) {
//
//                                        try {
//                                            int j=0;
//                                            String ss = select_size.getText().toString();
//                                            JSONObject jsonObject = new JSONObject(response);
//                                            String message = jsonObject.getString("message");
//
//                                            JSONArray jsonArray = jsonObject.getJSONArray("data");
//                                            for(int i=0;i<jsonArray.length();i++)
//                                            {
//                                                j++;
//                                                JSONObject object = jsonArray.getJSONObject(i);
//                                                String stock_size_id = object.getString("stock_size_id");
//                                                String stock_size_name = object.getString("stock_size_name");
//                                                if(ss.equals(stock_size_name)){
//                                                    String idea = stock_size_id;
//                                                    setsizeid.setText(idea);
//                                                    Toast.makeText(Add_Stock_List_Activity.this, idea, Toast.LENGTH_SHORT).show();
//
//                                                }
//                                            }
//                                        }
//                                        catch (JSONException e) {
//                                            e.printStackTrace();
//                                        }
//                                    }
//                                }, new Response.ErrorListener() {
//                            @Override
//                            public void onErrorResponse(VolleyError error) {
//                                Toast.makeText(Add_Stock_List_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
//                            }
//                        })
//                        {
//
//                            @Nullable
//                            @Override
//                            protected Map<String, String> getParams() throws AuthFailureError {
//                                Map<String,String> params = new HashMap<String,String>();
//                                params.put("stock_type_id",idea);
//                                return params;
//                            }
//
//                            @Override
//                            public Map<String, String> getHeaders() throws AuthFailureError {
//                                HashMap headers = new HashMap();
//                                headers.put("user_token",token);
//                                headers.put("user_id", userId);
//                                headers.put("project_location_id", location);
//
//                                return headers;
//
//
//
//
//                                //return super.getHeaders();
//                            }
//                        };
//
//                        RequestQueue requestQueue = Volley.newRequestQueue(Add_Stock_List_Activity.this);
//                        requestQueue.add(request);
                        // Dismiss dialog
                        dialog.dismiss();


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

        else {
            progressDialog = new ProgressDialog(Add_Stock_List_Activity.this);
            progressDialog.setTitle("Stock Category");
            progressDialog.setMessage("Loading... Please Wait!");
            progressDialog.show();

            String token = getIntent().getStringExtra("token");
            String userId = getIntent().getStringExtra("userId");
            String location = getIntent().getStringExtra("location");


            StringRequest request = new StringRequest(Request.Method.POST, "https://mployis.com/staging/api/stock/add_stock",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(response);
                                String message = jsonObject.getString("message");

                                Toast.makeText(Add_Stock_List_Activity.this, message, Toast.LENGTH_SHORT).show();

                                startActivity(new Intent(getApplicationContext(),StockListActivity.class));
                                progressDialog.dismiss();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            //Toast.makeText(Add_Stock_Category_Activity.this, response, Toast.LENGTH_SHORT).show();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Add_Stock_List_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

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
                }

                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<String,String>();
                    params.put("stock_category_id", category);
                    params.put("stock_type_id", type);
                    params.put("stock_size_id", size);
                    params.put("stock_batch_number", batchno);
                    params.put("stock_make_id", makeinsert);
                    params.put("stock_uom_id", uom);
                    params.put("safety_stock", safetyinsert);
                    params.put("stock_quantity", quantityinsert);
                    params.put("stock_lot", max_all);
                    params.put("stock_price", priceinsert);
                    params.put("stock_invoice_number", invoiceinsert);
                    params.put("stock_distributor_name", distributorinsert);
                    return params;
                }


            };
            RequestQueue  requestQueue = Volley.newRequestQueue(Add_Stock_List_Activity.this);
            requestQueue.add(request);

//            StringRequest request = new StringRequest(Request.Method.POST, "https://investment-wizards.com/manjeet/Phils_Stock/insert_category/add_category_list.php",
//                    new Response.Listener<String>() {
//                        @Override
//                        public void onResponse(String response) {
//                            Toast.makeText(Add_Stock_List_Activity.this, response, Toast.LENGTH_SHORT).show();
//                        }
//                    }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    Toast.makeText(Add_Stock_List_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
//
//                }
//            }) {
//                @Nullable
//                @Override
//                protected Map<String, String> getParams() throws AuthFailureError {
//                    Map<String, String> params = new HashMap<String, String>();
//                    params.put("stock_category_id", category);
//                    params.put("stock_type_id", type);
//                    params.put("stock_size_id", size);
//                    params.put("stock_batch_number", batchno);
//                    params.put("stock_invoice_number", invoiceinsert);
//                    params.put("stock_distributor_name", distributorinsert);
//                    params.put("stock_make_id", makeinsert);
//                    params.put("stock_uom_id", uom);
//                    params.put("safety_stock", safetyinsert);
//                    params.put("stock_quantity", quantityinsert);
//                    params.put("stock_lot", max_all);
//                    params.put("stock_price", priceinsert);
//                    return params;
//                }
//            };
//            //        Toast.makeText(this, category+"/"+type+"/"+size+"/"+batchno+"/"+invoiceinsert
////                +"/"+distributorinsert+"/"+makeinsert+"/"+uom+"/"+safetyinsert+"/"+quantityinsert
////                +"/"+max_all+"/"+priceinsert, Toast.LENGTH_SHORT).show();
//
//            RequestQueue requestQueue = Volley.newRequestQueue(Add_Stock_List_Activity.this);
//            requestQueue.add(request);
        }

    }
}