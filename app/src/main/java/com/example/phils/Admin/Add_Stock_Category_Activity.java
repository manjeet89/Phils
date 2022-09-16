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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.phils.R;
import com.example.phils.ResponseModels.ResponseModelStockCategory;
import com.example.phils.Shareprefered.AppConfig;
import com.example.phils.UserActivity;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Add_Stock_Category_Activity extends AppCompatActivity {
    TextView textview,check_status,setcategoryid;
    ArrayList<String> arrayList , arrayList1;
    Dialog dialog;
    Button button;
    EditText category_name;
    TextView location_save;
    AppConfig appConfig;
    ProgressDialog progressDialog;
    ArrayAdapter<String> EmpCategory;


    @Override
    public void onBackPressed() {
    startActivity(new Intent(getApplicationContext(), StockCategoryActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stock_category);

        appConfig = new AppConfig(this);
        location_save = findViewById(R.id.location_save);
        String location_save1 = appConfig.getLocation();
        location_save.setText(location_save1);


            textview=findViewById(R.id.testView);
            check_status = findViewById(R.id.status_check);
            button = findViewById(R.id.insert_cat);
            category_name = findViewById(R.id.category_insert);
            setcategoryid = findViewById(R.id.setcategoryid);

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
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        break;

                    case R.id.user:
                        startActivity(new Intent(getApplicationContext(), UserActivity.class));
                        break;

                    case R.id.category_stock:
                        startActivity(new Intent(getApplicationContext(),StockCategoryActivity.class));
                        break;

                    case R.id.type_stock:
                        startActivity(new Intent(getApplicationContext(), StockTypeActivity.class));
                        break;

                    case R.id.size_stock:
                        startActivity(new Intent(getApplicationContext(), StockSizeActivity.class));
                        break;

                    case R.id.make_stock:
                        startActivity(new Intent(getApplicationContext(), StockMakeActivity.class));
                        break;

                    case R.id.umo_stock:
                        startActivity(new Intent(getApplicationContext(), StockUomActivity.class));
                        break;

                    case R.id.list_stock:
                        startActivity(new Intent(getApplicationContext(), StockListActivity.class));
                        break;
                    default:
                        return true;
                }
                return true;
            }
        });







            // initialize array list
            arrayList=new ArrayList<>();

            arrayList1=new ArrayList<>();


            arrayList1.add("Enable");
            arrayList1.add("Disable");

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Insert();
                }
            });

        String token = getIntent().getStringExtra("token");
        String userId = getIntent().getStringExtra("userId");
        String location = getIntent().getStringExtra("location");

        StringRequest request = new StringRequest(Request.Method.POST, "https://mployis.com/staging/api/stock/stock_employee_category",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            //String message = jsonObject.getString("message");

                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            for(int i=0;i<jsonArray.length();i++)
                            {
                                JSONObject object = jsonArray.getJSONObject(i);
                                String emp_type_id = object.getString("emp_type_id");
                                String emp_type_name = object.getString("emp_type_name");

                                arrayList.add(emp_type_name);
                                EmpCategory = new ArrayAdapter<>(Add_Stock_Category_Activity.this,
                                        android.R.layout.simple_list_item_1, arrayList);
                                EmpCategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                            }
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Add_Stock_Category_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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

        RequestQueue requestQueue = Volley.newRequestQueue(Add_Stock_Category_Activity.this);
        requestQueue.add(request);



            textview.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // Initialize dialog
                    dialog=new Dialog(Add_Stock_Category_Activity.this);

                    // set custom dialog
                    dialog.setContentView(R.layout.dialog_searchable_spinner);

                    // set custom height and width
                    dialog.getWindow().setLayout(650,800);

                    // set transparent background
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                    // show dialog
                    dialog.show();

          // Initialize and assign variable
                    EditText editText=dialog.findViewById(R.id.edit_text);
                    ListView listView=dialog.findViewById(R.id.list_view);

                    // Initialize array adapter
                    //ArrayAdapter<String> adapter=new ArrayAdapter<>(Add_Stock_Category_Activity.this, android.R.layout.simple_list_item_1,arrayList);

                    // set adapter
                    listView.setAdapter(EmpCategory);
                    editText.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            EmpCategory.getFilter().filter(s);
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            // when item selected from list
                            // set selected item on textView
                            textview.setText(EmpCategory.getItem(position));
                            // Dismiss dialog

                            String token = getIntent().getStringExtra("token");
                            String userId = getIntent().getStringExtra("userId");
                            String location = getIntent().getStringExtra("location");

                            StringRequest request = new StringRequest(Request.Method.POST, "https://mployis.com/staging/api/stock/stock_employee_category",
                                    new com.android.volley.Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {

                                            try {
                                                String ss = textview.getText().toString();

                                                JSONObject jsonObject = new JSONObject(response);
                                                //String message = jsonObject.getString("message");

                                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                                for(int i=0;i<jsonArray.length();i++)
                                                {
                                                    JSONObject object = jsonArray.getJSONObject(i);
                                                    String emp_type_id = object.getString("emp_type_id");
                                                    String emp_type_name = object.getString("emp_type_name");
                                                    if(ss.equals(emp_type_name)){
                                                        String idea = emp_type_id;
                                                        setcategoryid.setText(idea);

                                                        //Toast.makeText(Add_Stock_Category_Activity.this, idea, Toast.LENGTH_SHORT).show();
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
                                    Toast.makeText(Add_Stock_Category_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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

                            RequestQueue requestQueue = Volley.newRequestQueue(Add_Stock_Category_Activity.this);
                            requestQueue.add(request);
                            dialog.dismiss();
                        }
                    });
                }
            });

            check_status.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Initialize dialog
                    dialog=new Dialog(Add_Stock_Category_Activity.this);

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

                    ArrayAdapter<String> adapter=new ArrayAdapter<>(Add_Stock_Category_Activity.this, android.R.layout.simple_list_item_1,arrayList1);
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
        }

    private void Insert() {
        progressDialog = new ProgressDialog(Add_Stock_Category_Activity.this);
        progressDialog.setTitle("Stock Category");
        progressDialog.setMessage("Loading... Please Wait!");
        progressDialog.show();

        String e1 =  setcategoryid.getText().toString().trim();
        String e2 = category_name.getText().toString().toUpperCase(Locale.ROOT).trim();
        String e3 = check_status.getText().toString().trim();
        if(TextUtils.isEmpty(e1))
        {
            textview.setError("Please Select Category Group");
            Toast.makeText(Add_Stock_Category_Activity.this, "Please Select Category Group", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(TextUtils.isEmpty(e2))
        {
            category_name.setError("Please Enter your Category Name");
            Toast.makeText(Add_Stock_Category_Activity.this, "Please Enter your Category Name", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(TextUtils.isEmpty(e3))
        {
            check_status.setError("Please Select your Status");
            Toast.makeText(Add_Stock_Category_Activity.this, "Please Select your Status", Toast.LENGTH_SHORT).show();
            return;
        }
        else
        {


            if(e3.equals("Enable"))
            {
                e3 = "1";
            }
            else
            {
                e3 = "0";
            }

            String e4 =  e1;
            String e5 = e2;
            String e6 = e3;
//            Toast.makeText(this, e4+"  /  "+e5+"  /  "+e6, Toast.LENGTH_SHORT).show();

            String token = getIntent().getStringExtra("token");
            String userId = getIntent().getStringExtra("userId");
            String location = getIntent().getStringExtra("location");

            StringRequest request = new StringRequest(Request.Method.POST, "https://mployis.com/staging/api/stock/add_stock_category",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(response);
                                String message = jsonObject.getString("message");
                                Toast.makeText(Add_Stock_Category_Activity.this, message, Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(),StockCategoryActivity.class));
                                progressDialog.dismiss();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            //Toast.makeText(Add_Stock_Category_Activity.this, response, Toast.LENGTH_SHORT).show();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Add_Stock_Category_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

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
                    params.put("stock_category_name",e5);
                    params.put("stock_emp_category",e4);
                    params.put("stock_category_status",e6);
                    return  params;
                }


            };
            RequestQueue  requestQueue = Volley.newRequestQueue(Add_Stock_Category_Activity.this);
            requestQueue.add(request);

        }
    }

}