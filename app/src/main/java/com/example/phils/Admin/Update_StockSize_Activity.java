package com.example.phils.Admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.phils.Spinner.CategorySpinner;
import com.example.phils.R;
import com.example.phils.Shareprefered.AppConfig;
import com.example.phils.Spinner.StockTypeSpinner;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Update_StockSize_Activity extends AppCompatActivity {
    TextView select_category,select_type,stock_size_status,setcategoryid,setypeid;
    EditText stock_size_name;
    Button insert_size;
    ArrayList<String> arrayList1;
    Dialog dialog;
    ImageView img,profile;

    String categoryurl = "https://mployis.com/staging/api/stock/stock_category";
    String categoryIdurl="https://mployis.com/staging/api/stock/get_stock_type_from_category_id";
    String sizeurl = "https://mployis.com/staging/api/stock/update_stock_size";


    ArrayList<StockTypeSpinner> stockTypeSpinners = new ArrayList<StockTypeSpinner>();
    ArrayAdapter<StockTypeSpinner> StockTypeSpinnerAdpter;

    ArrayList<CategorySpinner> categorySpinners = new ArrayList<CategorySpinner>();
    ArrayAdapter<CategorySpinner> spinnerArrayAdapter;

    RequestQueue requestQueue;
    ProgressDialog progressDialog;

    TextView location_save,locationtext;
    AppConfig appConfig;

    Button logout,location;
    Button btnnotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_stock_size);
        requestQueue = Volley.newRequestQueue(this);

        appConfig = new AppConfig(this);
        location_save = findViewById(R.id.location_save);
        String location_save1 = appConfig.getLocation();
        location_save.setText(location_save1);

        locationtext = findViewById(R.id.locationtext);
        locationtext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ProjectLocationActivity.class));
            }
        });
        location_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ProjectLocationActivity.class));

            }
        });

        img = findViewById(R.id.img);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Notification_Activity.class));
            }
        });

//        logout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                appConfig.updateUserLoginStatus(false);
//                startActivity(new Intent(MainActivity.this, LoginActivity.class));
//                finish();
//            }
//        });

        profile = findViewById(R.id.profile);

        //ImageView profile=(ImageView) findViewById(R.id.profile);
        Bitmap mbitmap=((BitmapDrawable) getResources().getDrawable(R.drawable.admin)).getBitmap();
        Bitmap imageRounded=Bitmap.createBitmap(mbitmap.getWidth(), mbitmap.getHeight(), mbitmap.getConfig());
        Canvas canvas=new Canvas(imageRounded);
        Paint mpaint=new Paint();
        mpaint.setAntiAlias(true);
        mpaint.setShader(new BitmapShader(mbitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
        canvas.drawRoundRect((new RectF(0, 0, mbitmap.getWidth(), mbitmap.getHeight())), 100, 100, mpaint); // Round Image Corner 100 100 100 100
        profile.setImageBitmap(imageRounded);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
                //Toast.makeText(MainActivity.this, "desh", Toast.LENGTH_SHORT).show();
                dialog=new Dialog(Update_StockSize_Activity.this);

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

                ImageView profile  = dialog.findViewById(R.id.profile);
                Bitmap mbitmap=((BitmapDrawable) getResources().getDrawable(R.drawable.admin)).getBitmap();
                Bitmap imageRounded=Bitmap.createBitmap(mbitmap.getWidth(), mbitmap.getHeight(), mbitmap.getConfig());
                Canvas canvas=new Canvas(imageRounded);
                Paint mpaint=new Paint();
                mpaint.setAntiAlias(true);
                mpaint.setShader(new BitmapShader(mbitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
                canvas.drawRoundRect((new RectF(0, 0, mbitmap.getWidth(), mbitmap.getHeight())), 100, 100, mpaint); // Round Image Corner 100 100 100 100
                profile.setImageBitmap(imageRounded);




                Button logout = dialog.findViewById(R.id.logout);
                logout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        appConfig.updateUserLoginStatus(false);
                        startActivity(new Intent(Update_StockSize_Activity.this,LoginActivity.class));
                        finish();
                    }
                });
                TextView textView = dialog.findViewById(R.id.my_profile);
//                if(1==1)
//                {
//                    textView.setVisibility(View.GONE);
//                }
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


        MaterialToolbar toolbar = findViewById(R.id.topAppbar);
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigation_view);




        if(1==2) {
            Menu menu = navigationView.getMenu();
            MenuItem menuItem = menu.findItem(R.id.ghar);
            menuItem.setVisible(false);
        }





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
                        startActivity(new Intent(getApplicationContext(),UserActivity.class));

                        break;

                    case R.id.category_stock:
                        startActivity(new Intent(getApplicationContext(), StockCategoryActivity.class));
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


                    case R.id.category_job:
                        startActivity(new Intent(getApplicationContext(), Job_Category_Activity.class));
                        break;

                    case R.id.Size_job:
                        startActivity(new Intent(getApplicationContext(), Job_Size_Activity.class));
                        break;

                    case R.id.List_job:
                        startActivity(new Intent(getApplicationContext(), Job_List_Activity.class));
                        break;

                    case R.id.Report_reports:
                        startActivity(new Intent(getApplicationContext(), ReportsActivity.class));
                        break;

                    case R.id.Report_consumption:
                        startActivity(new Intent(getApplicationContext(), ConsumptionActivity.class));
                        break;

                    case R.id.Report_consumption_details:
                        startActivity(new Intent(getApplicationContext(), ConsumptionDetailActivity.class));
                        break;

//                    case R.id.roles:
//                        startActivity(new Intent(getApplicationContext(), RolesAndPrivilegesActivity.class));
//                        break;
//
                    case R.id.resqu_list:
                        startActivity(new Intent(getApplicationContext(), RequisitionListActivity.class));
                        break;
//
                    case R.id.resqu_reviever:
                        startActivity(new Intent(getApplicationContext(), RequisitionReciverList.class));
                        break;



                    default:
                        return true;
                }
                return true;
            }
        });

        select_category = findViewById(R.id.select_category);
        select_type = findViewById(R.id.select_type);
        stock_size_name = findViewById(R.id.stock_size_name);
        stock_size_status = findViewById(R.id.stock_size_status);
        insert_size = findViewById(R.id.insert_size);
        setcategoryid = findViewById(R.id.setcategoryid);
        setypeid = findViewById(R.id.settypeid);

        String setcate = getIntent().getStringExtra("category");
        String settype = getIntent().getStringExtra("type");
        String setsize = getIntent().getStringExtra("size");
        String setstatus = getIntent().getStringExtra("status");

        String categoryid = getIntent().getStringExtra("categoryid");
        String typeid = getIntent().getStringExtra("typeid");

        CategoryIdPass(typeid);

        select_category.setText(setcate);
        select_type.setText(settype);
        stock_size_name.setText(setsize);
        stock_size_status.setText(setstatus);
        setcategoryid.setText(categoryid);
        setypeid.setText(typeid);

        insert_size.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InsertData();
            }
        });



        String token = getIntent().getStringExtra("token");
        String userId = getIntent().getStringExtra("userId");
        String location = getIntent().getStringExtra("location");
        String user_employee_type = appConfig.getuser_employee_type();

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
                                spinnerArrayAdapter = new ArrayAdapter<CategorySpinner>(Update_StockSize_Activity.this,
                                        android.R.layout.simple_spinner_dropdown_item,categorySpinners);
                                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
 }

                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Update_StockSize_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap headers = new HashMap();
                headers.put("user_token",token);
                headers.put("user_id", userId);
                headers.put("project_location_id", location);
                headers.put("user_employee_type", user_employee_type);

                return headers;
                //return super.getHeaders();
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(Update_StockSize_Activity.this);
        requestQueue.add(request);

        select_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Initialize dialog
                dialog = new Dialog(Update_StockSize_Activity.this);

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

                        dialog.dismiss();


                    }
                });
            }
        });




        // begging Status spinner
        arrayList1=new ArrayList<>();
        arrayList1.add("Enable");
        arrayList1.add("Disable");

        stock_size_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Initialize dialog
                dialog=new Dialog(Update_StockSize_Activity.this);

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

                ArrayAdapter<String> adapter=new ArrayAdapter<>(Update_StockSize_Activity.this, android.R.layout.simple_list_item_1,arrayList1);
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
                        stock_size_status.setText(adapter.getItem(i));

                        // Dismiss dialog
                        dialog.dismiss();
                    }
                });

            }
        });
        // end Status spinner
    }

    private void InsertData() {
        String categoryadd = setcategoryid.getText().toString();
        String typeadd  = setypeid.getText().toString();
        String sizenameadd = stock_size_name.getText().toString();
        String statusadd = stock_size_status.getText().toString();
        String id = getIntent().getStringExtra("id");

        if (TextUtils.isEmpty(categoryadd)) {
            setcategoryid.setError("Please Select Category Group");
            Toast.makeText(Update_StockSize_Activity.this, "Please Select Category Group", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(typeadd)) {
            setypeid.setError("Please Enter your type Name");
            Toast.makeText(Update_StockSize_Activity.this, "Please Select Type ", Toast.LENGTH_SHORT).show();
            return;
        }else if (TextUtils.isEmpty(sizenameadd)) {
            select_type.setError("Please Enter Size");
            Toast.makeText(Update_StockSize_Activity.this, "Please Enter Size", Toast.LENGTH_SHORT).show();
            return;
        }else if (TextUtils.isEmpty(statusadd)) {
            stock_size_status.setError("Please Select your Status");
            Toast.makeText(Update_StockSize_Activity.this, "Please Select your Status", Toast.LENGTH_SHORT).show();
            return;
        } else {

            if (statusadd.equals("Enable")) {
                statusadd = "1";
            } else {
                statusadd = "0";
            }

            String status = statusadd;
            progressDialog = new ProgressDialog(Update_StockSize_Activity.this);
            progressDialog.setTitle("Stock Size");
            progressDialog.setMessage("Loading... Please Wait!");
            progressDialog.show();

            String token = getIntent().getStringExtra("token");
            String userId = getIntent().getStringExtra("userId");
            String location = getIntent().getStringExtra("location");
            String user_employee_type = appConfig.getuser_employee_type();

            StringRequest request = new StringRequest(Request.Method.POST, sizeurl,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(response);
                                String message = jsonObject.getString("message");
                                Toast.makeText(Update_StockSize_Activity.this, message, Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(),StockSizeActivity.class));
                                progressDialog.dismiss();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            //Toast.makeText(Add_Stock_Category_Activity.this, response, Toast.LENGTH_SHORT).show();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Update_StockSize_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

                }
            })
            {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap headers = new HashMap();
                    headers.put("user_token",token);
                    headers.put("user_id", userId);
                    headers.put("project_location_id", location);
                    headers.put("user_employee_type", user_employee_type);

                    return headers;
                }

                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<String,String>();
                    params.put("stock_category_id", categoryadd);
                    params.put("stock_type_id",typeadd);
                    params.put("stock_size_name", sizenameadd);
                    params.put("stock_size_status", status);
                    params.put("stock_size_id", id);

                    return  params;
                }


            };
            RequestQueue  requestQueue = Volley.newRequestQueue(Update_StockSize_Activity.this);
            requestQueue.add(request);

        }
    }


    private void CategoryIdPass(String idea) {

//        select_type.getText().clear();
        select_type.setText("");
        stockTypeSpinners.clear();


        String token = getIntent().getStringExtra("token");
        String userId = getIntent().getStringExtra("userId");
        String location = getIntent().getStringExtra("location");
        String user_employee_type = appConfig.getuser_employee_type();

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
                                StockTypeSpinnerAdpter = new ArrayAdapter<StockTypeSpinner>(Update_StockSize_Activity.this,
                                        android.R.layout.simple_list_item_1,stockTypeSpinners );
                                StockTypeSpinnerAdpter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                            }
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Update_StockSize_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
                headers.put("user_employee_type", user_employee_type);

                return headers;


            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(Update_StockSize_Activity.this);
        requestQueue.add(request);


        select_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Initialize dialog
                dialog = new Dialog(Update_StockSize_Activity.this);

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

                        dialog.dismiss();

                    }
                });
            }
        });


    }
}