package com.example.phils.Admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import android.util.Log;
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
import com.example.phils.R;
import com.example.phils.Shareprefered.AppConfig;
import com.example.phils.Spinner.CategorySpinner;
import com.example.phils.Spinner.RequisitionJobNumberSpinner;
import com.example.phils.Spinner.StockSizeSpinner;
import com.example.phils.Spinner.StockTypeSpinner;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class UpdateRequisitionListActivity extends AppCompatActivity {

    ArrayList<RequisitionJobNumberSpinner> requisitionJobNumberSpinnersList = new ArrayList<>();
    ArrayAdapter<RequisitionJobNumberSpinner> requisitionJobNumberSpinnerArrayAdapter;

    ArrayList<CategorySpinner> categoryList = new ArrayList<>();
    ArrayAdapter<CategorySpinner> categoryAdapter;

    ArrayList<StockTypeSpinner> typeList = new ArrayList<StockTypeSpinner>();
    ArrayAdapter<StockTypeSpinner> typeAdapter;

    ArrayList<StockSizeSpinner> sizeList = new ArrayList<StockSizeSpinner>();
    ArrayAdapter<StockSizeSpinner> sizeAdapter;
    //Woker Grinder and Welder
    boolean[] wokerlenght;
    ArrayList<String> wokerList = new ArrayList<>();
    ArrayList<String> wokerListvalue   = new ArrayList<>();

    static String wokerValue[];
    static String wokerId[];


    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), RequisitionListActivity.class));
    }

    TextView location_save;
    AppConfig appConfig;

    Button reqadd;
    Button btnnotification;
    ImageView img,profile;
    TextView locationtext;


    TextView setjobnumberid,setReqUserid,setcategoryid,setypeid,setsizeid;
    TextView job_nuber,seamnumber,req_user,categoryreq,typereq,sizereq;
    EditText quantityreq,remark;

    ArrayList<String> seamList = new ArrayList<>();
    ArrayList<String> jobnumberList = new ArrayList<>();
    ArrayList<String> reqUserList = new ArrayList<>();
    //ArrayList<String> categoryList = new ArrayList<>();

    ArrayAdapter<String> jobnumberAdapter;
    ArrayAdapter<String> seamAdapter;
    ArrayAdapter<String> reqUserAdapter;
    //ArrayAdapter<String> categoryAdapter;

    Dialog dialog;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_requisition_list);

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
                dialog=new Dialog(UpdateRequisitionListActivity.this);

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
                        startActivity(new Intent(UpdateRequisitionListActivity.this, LoginActivity.class));
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
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        break;

                    case R.id.user:
                        startActivity(new Intent(getApplicationContext(), UserActivity.class));

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

        String token = getIntent().getStringExtra("token");
        String userId = getIntent().getStringExtra("userId");
        String location = getIntent().getStringExtra("location");
        String id = getIntent().getStringExtra("id");
        String user_employee_type = appConfig.getuser_employee_type();

        String job_number = getIntent().getStringExtra("job_number");
        String jobid = getIntent().getStringExtra("jobid");
        String req_user_id = getIntent().getStringExtra("req_user_id");
        String stockid = getIntent().getStringExtra("stockid");
        String typeid = getIntent().getStringExtra("typeid");
        String sizeid = getIntent().getStringExtra("sizeid");
        String stockcategory = getIntent().getStringExtra("stockcategory");
        String stocktype = getIntent().getStringExtra("stocktype");
        String stocksize = getIntent().getStringExtra("stocksize");
        String seam = getIntent().getStringExtra("seam");
        String quantityreqdata = getIntent().getStringExtra("quantityreq");


        setjobnumberid =  findViewById(R.id.setjobnumberid);
        setReqUserid   = findViewById(R.id.setReqUserid);
        setcategoryid = findViewById(R.id.setcategoryid);
        setypeid = findViewById(R.id.settypeid);
        setsizeid = findViewById(R.id.setsizeid);
        job_nuber = findViewById(R.id.job_number);
        seamnumber= findViewById(R.id.seamnumber);
        req_user= findViewById(R.id.req_user);
        categoryreq = findViewById(R.id.categoryreq);
        typereq   = findViewById(R.id.typereq);
        sizereq   = findViewById(R.id.sizereq);
        quantityreq = findViewById(R.id.quantityreq);
        remark = findViewById(R.id.remark);
        reqadd = findViewById(R.id.reqadd);
        reqadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataInsert();
            }
        });

        categoryreq.setText("stockid");
        typereq.setText("typeid");
        sizereq.setText("sizeid");
        seamnumber.setText("seam");



        fatchCategoryId(jobid);
        CategoryIdPass(stockid,jobid);
        TypeIdPass(typeid);
        PassJobNumber(jobid);

        setjobnumberid.setText(jobid);
        setcategoryid.setText(stockid);
        setypeid.setText(typeid);
        setsizeid.setText(sizeid);
        job_nuber.setText(job_number);
        seamnumber.setText(seam);
       // req_user.setText(req_user_id);
        categoryreq.setText(stockcategory);
        typereq.setText(stocktype);
        sizereq.setText(stocksize);
        quantityreq.setText(quantityreqdata);

        JobNumber();
        RequsistiionUser();

    }

    private void fatchCategoryId(String jobid) {

        categoryreq.setText("");
        categoryList.clear();
        String user_employee_type = appConfig.getuser_employee_type();

        String token = getIntent().getStringExtra("token");
        String userId = getIntent().getStringExtra("userId");
        String location = getIntent().getStringExtra("location");

        StringRequest request1 = new StringRequest(Request.Method.POST, "https://mployis.com/staging/api/requisition/get_stock_category_by_job_id",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            //String message = jsonObject.getString("message");

                            JSONArray jsonArray = jsonObject.getJSONArray("data");

                            for(int i=0; i<jsonArray.length();i++)
                            {
                                JSONObject object = jsonArray.getJSONObject(i);

                                String stock_category_id = object.getString("stock_category_id");
                                String  stock_category_name = object.getString("stock_category_name");
                                categoryList.add(new CategorySpinner(stock_category_id,stock_category_name));
                                categoryAdapter = new ArrayAdapter<CategorySpinner>(UpdateRequisitionListActivity.this,
                                        android.R.layout.simple_spinner_dropdown_item,categoryList);
                                categoryAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);

                            }

                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(UpdateRequisitionListActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("job_id", jobid);


                return params;
            }
        };

        RequestQueue requestQueue1 = Volley.newRequestQueue(UpdateRequisitionListActivity.this);
        requestQueue1.add(request1);

        categoryreq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Initialize dialog
                dialog = new Dialog(UpdateRequisitionListActivity.this);

                // set custom dialog
                dialog.setContentView(R.layout.dialog_searchable_spinner_stock_type);

                // set custom height and width
                dialog.getWindow().setLayout(750, 1100);

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
                        //categoryreq.setText(categoryAdapter.getItem(position));

                        CategorySpinner sp = (CategorySpinner)parent.getItemAtPosition(position);
                        categoryreq.setText(sp.stock_category_name);
                        setcategoryid.setText(sp.stock_category_id);
                        CategoryIdPass(sp.stock_category_id,jobid);
                        // Dismiss dialog
                        dialog.dismiss();


//                            String s = select_category.getText().toString();
//                            Toast.makeText(Add_Stock_Make_Activity.this, s, Toast.LENGTH_SHORT).show();

//                        JsonObjectRequest jsonObjectRequest1 = new JsonObjectRequest(Request.Method.POST, categoryurl
//                                , null, new Response.Listener<JSONObject>() {
//                            @Override
//                            public void onResponse(JSONObject response) {
//                                try {
//                                    String ss = categoryreq.getText().toString();
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
//                                            Toast.makeText(UpdateRequisitionListActivity.this, setcategoryid.getText().toString(), Toast.LENGTH_SHORT).show();
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



    private void CategoryIdPass(String categoryid,String jobid) {
        typereq.setText("");
        typeList.clear();
        sizereq.setText("");
        sizeList.clear();

        String user_employee_type = appConfig.getuser_employee_type();

        String token = getIntent().getStringExtra("token");
        String userId = getIntent().getStringExtra("userId");
        String location = getIntent().getStringExtra("location");

        StringRequest request1 = new StringRequest(Request.Method.POST, "https://mployis.com/staging/api/requisition/get_stock_category_type_by_job_id",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            //String message = jsonObject.getString("message");

                            JSONArray jsonArray = jsonObject.getJSONArray("data");

                            for(int i=0; i<jsonArray.length();i++)
                            {
                                JSONObject object = jsonArray.getJSONObject(i);

                                String stock_type_id = object.getString("stock_type_id");
                                String  stock_type_name = object.getString("stock_type_name");
                                typeList.add(new StockTypeSpinner(stock_type_id,stock_type_name));
                                typeAdapter = new ArrayAdapter<StockTypeSpinner>(UpdateRequisitionListActivity.this,
                                        android.R.layout.simple_spinner_dropdown_item,typeList);
                                typeAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);

                            }

                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(UpdateRequisitionListActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("job_id", jobid);
                params.put("stock_category_id", categoryid);


                return params;
            }
        };

        RequestQueue requestQueue1 = Volley.newRequestQueue(UpdateRequisitionListActivity.this);
        requestQueue1.add(request1);

//        JsonObjectRequest jsonObjectRequest1 = new JsonObjectRequest(Request.Method.POST, typeurl+idea, null,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        try {
//
//
//                            JSONArray jsonArray = response.getJSONArray("data");
//                            for(int i=0;i<jsonArray.length();i++){
//                                JSONObject jsonObject = jsonArray.getJSONObject(i);
//                                String stock_type_id= jsonObject.optString("stock_type_id");
//                                String stock_type_name1= jsonObject.optString("stock_type_name");
//                                typeList.add(stock_type_name1);
//                                typeAdapter = new ArrayAdapter<>(UpdateRequisitionListActivity.this,
//                                        android.R.layout.simple_list_item_1,typeList);
//                                typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
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
//        requestQueue.add(jsonObjectRequest1);


        typereq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Initialize dialog
                dialog = new Dialog(UpdateRequisitionListActivity.this);

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
                        //typereq.setText(typeAdapter.getItem(position));

                        StockTypeSpinner sp =(StockTypeSpinner)parent.getItemAtPosition(position);
                        typereq.setText(sp.stock_type_name);
                        setypeid.setText(sp.stock_type_id);
                        TypeIdPass(sp.stock_type_id);
                        // Dismiss dialog
                        dialog.dismiss();


//                            String s = select_category.getText().toString();
//                            Toast.makeText(Add_Stock_Make_Activity.this, s, Toast.LENGTH_SHORT).show();

//                        JsonObjectRequest jsonObjectRequest2 = new JsonObjectRequest(Request.Method.POST, typeurl+idea
//                                , null, new Response.Listener<JSONObject>() {
//                            @Override
//                            public void onResponse(JSONObject response) {
//                                try {
//                                    String ss = typereq.getText().toString();
//                                    JSONArray jsonArray = response.getJSONArray("data");
//
//                                    for (int i = 0; i < jsonArray.length(); i++) {
//                                        JSONObject jsonObject = jsonArray.getJSONObject(i);
//                                        String stock_type_id= jsonObject.optString("stock_type_id");
//                                        String stock_type_name= jsonObject.optString("stock_type_name");
//                                        if(ss.equals(stock_type_name)){
//                                            String idea = stock_type_id;
//                                            setypeid.setText(idea);
//                                            Toast.makeText(UpdateRequisitionListActivity.this, setypeid.getText().toString(), Toast.LENGTH_SHORT).show();
//                                            TypeIdPass(idea);
//                                        }
//                                    }
//
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//
//
//                            }
//
//                        }, new Response.ErrorListener() {
//                            @Override
//                            public void onErrorResponse(VolleyError error) {
//
//                            }
//                        });
//
//                        requestQueue.add(jsonObjectRequest2);
                    }
                });
            }
        });
    }

    private void TypeIdPass(String idea) {
//        Toast.makeText(this, idea, Toast.LENGTH_SHORT).show();
        sizereq.setText("");
        sizeList.clear();

        String user_employee_type = appConfig.getuser_employee_type();

        String token = getIntent().getStringExtra("token");
        String userId = getIntent().getStringExtra("userId");
        String location = getIntent().getStringExtra("location");

        StringRequest request1 = new StringRequest(Request.Method.POST, "https://mployis.com/staging/api/requisition/get_stock_size_by_type_id",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            //String message = jsonObject.getString("message");

                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            for(int i=0; i<jsonArray.length();i++)
                            {
                                JSONObject object = jsonArray.getJSONObject(i);

                                String stock_size_id = object.getString("stock_size_id");
                                String  stock_size_name = object.getString("stock_size_name");

                                sizeList.add(new StockSizeSpinner(stock_size_id,stock_size_name));
                                sizeAdapter = new ArrayAdapter<StockSizeSpinner>(UpdateRequisitionListActivity.this,
                                        android.R.layout.simple_spinner_dropdown_item,sizeList);

                                sizeAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);

                            }

                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(UpdateRequisitionListActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("stock_type_id", idea);


                return params;
            }
        };

        RequestQueue requestQueue1 = Volley.newRequestQueue(UpdateRequisitionListActivity.this);
        requestQueue1.add(request1);

//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, sizeurl+idea, null,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        try {
//
//                            JSONArray jsonArray = response.getJSONArray("data");
//                            for(int i=0;i<jsonArray.length();i++){
//                                JSONObject jsonObject = jsonArray.getJSONObject(i);
//                                String stock_size_id= jsonObject.optString("stock_size_id");
//                                String stock_size_name= jsonObject.optString("stock_size_name");
//                                sizeList.add(stock_size_name);
//                                sizeAdapter = new ArrayAdapter<>(UpdateRequisitionListActivity.this,
//                                        android.R.layout.simple_list_item_1,sizeList);
//                                sizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
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


        sizereq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Initialize dialog
                dialog = new Dialog(UpdateRequisitionListActivity.this);

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
                        //sizereq.setText(sizeAdapter.getItem(position));

                        StockSizeSpinner sp = (StockSizeSpinner)parent.getItemAtPosition(position);
                        sizereq.setText(sp.stock_size_name);
                        setsizeid.setText(sp.stock_size_id);
                        // Dismiss dialog
                        dialog.dismiss();


//                            String s = select_category.getText().toString();
//                            Toast.makeText(Add_Stock_Make_Activity.this, s, Toast.LENGTH_SHORT).show();

//                        JsonObjectRequest jsonObjectRequest1 = new JsonObjectRequest(Request.Method.POST, sizeurl+idea
//                                , null, new Response.Listener<JSONObject>() {
//                            @Override
//                            public void onResponse(JSONObject response) {
//                                try {
//                                    String ss = sizereq.getText().toString();
//                                    JSONArray jsonArray = response.getJSONArray("data");
//
//                                    for (int i = 0; i < jsonArray.length(); i++) {
//                                        JSONObject jsonObject = jsonArray.getJSONObject(i);
//                                        String stock_size_id= jsonObject.optString("stock_size_id");
//                                        String stock_size_name= jsonObject.optString("stock_size_name");
//                                        if(ss.equals(stock_size_name)){
//                                            String idea = stock_size_id;
//                                            setsizeid.setText(idea);
//                                            //TypeIdPass(idea);
//                                            Toast.makeText(UpdateRequisitionListActivity.this, setsizeid.getText().toString(), Toast.LENGTH_SHORT).show();
//                                        }
//                                    }
//
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//
//
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


    private void RequsistiionUser() {

        String token = getIntent().getStringExtra("token");
        String userId = getIntent().getStringExtra("userId");
        String location = getIntent().getStringExtra("location");
        String req_user_id = getIntent().getStringExtra("req_user_id");
        String user_employee_type = appConfig.getuser_employee_type();

//        Toast.makeText(this, req_user_id, Toast.LENGTH_SHORT).show();

        StringRequest request4 = new StringRequest(Request.Method.POST, "https://mployis.com/staging/api/job/job_welder_grinder_list",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            //String message = jsonObject.getString("message");

                            JSONArray jsonArray = jsonObject.getJSONArray("data");

                            wokerValue = new String[jsonArray.length()];
                            wokerId = new String[jsonArray.length()];
                            wokerlenght = new boolean[wokerValue.length];

                            int p=0;
                            for(int i=0; i<jsonArray.length();i++)
                            {
                                JSONObject object = jsonArray.getJSONObject(i);
                                String user_id = object.getString("user_id");
                                String user_name = object.getString("user_full_name");
                                String user_employee_id = object.getString("user_employee_id");



                                String [] array = req_user_id.split(",");

                                for(int k=0;k<array.length;k++){
                                    if(user_id.equals(array[k])){
                                        p = Integer.parseInt(user_id);
                                    }
                                }
                                if(user_id.equals(String.valueOf(p)))
                                {
                                    wokerList.add(user_id);
                                    wokerListvalue.add(user_name+" - "+user_employee_id);

                                    wokerlenght[i]=true;
                                }
                                else
                                {
                                    wokerlenght[i]=false;
                                }

                                wokerValue[i]=user_name+" - "+user_employee_id;
                                wokerId[i]=user_id;


//                                String[] WG = req_user_id.split(",");
//                                for(String name : WG){
//                                    if(user_id.equals(name))
//                                    {
////                                        wokerList.add(user_id);
////                                        wokerListvalue.add(user_full_name+" - "+user_employee_id);
//
//                                        q=Integer.parseInt(user_id);
////                                        wokerlenght[i] = true;
////                                        Log.d("okccc",user_full_name);
//                                    }
//                                    //System.out.println(name);
//
//                                }
//                                if(user_id.equals(String.valueOf(q)))
//                                {
//                                    wokerList.add(user_id);
//                                    wokerListvalue.add(user_name+" - "+user_employee_id);
//
//                                    wokerlenght[i]=true;
//                                }
//                                else
//                                {
//                                    wokerlenght[i]=false;
//                                }

                            }
                            String stringBuilder= String.join(",", wokerListvalue);
                            String stringbuilder = String.join(",",wokerList);

                            req_user.setText(stringBuilder);
                            setReqUserid.setText(stringbuilder);

//                            Collections.reverse(Arrays.asList(wokerValue));
//                            Collections.reverse(Arrays.asList(wokerId));

                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(UpdateRequisitionListActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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

        RequestQueue requestQueue4 = Volley.newRequestQueue(UpdateRequisitionListActivity.this);
        requestQueue4.add(request4);

        req_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Initialize alert dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateRequisitionListActivity.this);

                // set title
                builder.setTitle("Select Language");

                // set dialog non cancelable
                builder.setCancelable(false);

                builder.setMultiChoiceItems(wokerValue, wokerlenght, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        // check condition
                        if (b) {
                            // when checkbox selected
                            // Add position  in lang list
                            wokerList.add(wokerId[i]);
                            wokerListvalue.add(wokerValue[i]);

                            // Sort array list
                            Collections.sort(wokerList);

                        } else {
                            // when checkbox unselected
                            // Remove position from langList
                            wokerList.remove(wokerId[i]);
                            wokerListvalue.remove(wokerValue[i]);

                        }
                        Log.d("Nil", String.valueOf(wokerList));
                    }
                });
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Initialize string builder
//                        StringBuilder stringBuilder = new StringBuilder();
                        String stringBuilder= String.join(",", wokerListvalue);
                        String stringbuilder= String.join(",", wokerList);

                        Log.d("nil",stringBuilder);
                        Log.d("nil",stringbuilder);

                        req_user.setText(stringBuilder);
                        setReqUserid.setText(stringbuilder);

                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // dismiss dialog
                        dialogInterface.dismiss();
                    }
                });
                builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // use for loop
                        for (int j = 0; j < wokerlenght.length; j++) {
                            // remove all selection
                            wokerlenght[j] = false;
                            // clear language list
                            wokerListvalue.clear();
                            wokerList.clear();
                            // clear text view value
                            req_user.setText("");
                        }
                    }
                });
                // show dialog
                builder.show();

            }
        });


    }

    private void JobNumber() {

        String token = getIntent().getStringExtra("token");
        String userId = getIntent().getStringExtra("userId");
        String location = getIntent().getStringExtra("location");
        String user_employee_type = appConfig.getuser_employee_type();

        StringRequest request1 = new StringRequest(Request.Method.POST, "https://mployis.com/staging/api/job/job_incomplete",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            //String message = jsonObject.getString("message");

                            JSONArray jsonArray = jsonObject.getJSONArray("data");

                            for(int i=0; i<jsonArray.length();i++)
                            {
                                JSONObject object = jsonArray.getJSONObject(i);

                                String job_id = object.getString("job_id");
                                String  job_number = object.getString("job_number");

                                requisitionJobNumberSpinnersList.add(new RequisitionJobNumberSpinner(job_id,job_number));
                                requisitionJobNumberSpinnerArrayAdapter = new ArrayAdapter<RequisitionJobNumberSpinner>(UpdateRequisitionListActivity.this,
                                        android.R.layout.simple_spinner_dropdown_item,requisitionJobNumberSpinnersList);
                                requisitionJobNumberSpinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            }



                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(UpdateRequisitionListActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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

        RequestQueue requestQueue1 = Volley.newRequestQueue(UpdateRequisitionListActivity.this);
        requestQueue1.add(request1);

        job_nuber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Initialize dialog
                dialog = new Dialog(UpdateRequisitionListActivity.this);

                // set custom dialog
                dialog.setContentView(R.layout.dialog_searchable_spinner);

                // set custom height and width
                dialog.getWindow().setLayout(750, 1100);

                // set transparent background
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                // show dialog
                dialog.show();

                // Initialize and assign variable
                EditText editText = dialog.findViewById(R.id.edit_text);
                ListView listView = dialog.findViewById(R.id.list_view);



                listView.setAdapter(requisitionJobNumberSpinnerArrayAdapter);
                editText.addTextChangedListener(new TextWatcher() {
                                                    @Override
                                                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                                    }

                                                    @Override
                                                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                                        requisitionJobNumberSpinnerArrayAdapter.getFilter().filter(charSequence);
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
                        //job_nuber.setText(jobnumberAdapter.getItem(position));

                        RequisitionJobNumberSpinner sp = (RequisitionJobNumberSpinner)parent.getItemAtPosition(position);
                        job_nuber.setText(sp.job_number);
                        setjobnumberid.setText(sp.job_id);
                        fatchCategoryId(sp.job_id);
                        PassJobNumber(sp.job_id);
                        // Dismiss dialog
                        dialog.dismiss();

                        // Toast.makeText(UpdateRequisitionListActivity.this, sp.job_id, Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });



    }

    private void PassJobNumber(String idea) {
        seamnumber.setText("");
        seamList.clear();


        String token = getIntent().getStringExtra("token");
        String userId = getIntent().getStringExtra("userId");
        String location = getIntent().getStringExtra("location");
        String user_employee_type = appConfig.getuser_employee_type();

        StringRequest request1 = new StringRequest(Request.Method.POST, "https://mployis.com/staging/api/job/job_incomplete",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            //String message = jsonObject.getString("message");

                            JSONArray jsonArray = jsonObject.getJSONArray("data");

                            for(int i=0; i<jsonArray.length();i++)
                            {
                                JSONObject object = jsonArray.getJSONObject(i);

                                String job_id = object.getString("job_id");
                                String  seam_number = object.getString("seam_number");

                                String[] strSplit = seam_number.split(",");
                                ArrayList<String> strList = new ArrayList<String>(
                                        Arrays.asList(strSplit));

                                for (String s : strList) {
                                    seamList.add(s);
                                    seamAdapter = new ArrayAdapter<>(UpdateRequisitionListActivity.this,
                                            android.R.layout.simple_list_item_1, seamList);
                                    seamAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
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
                Toast.makeText(UpdateRequisitionListActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("job_id", idea);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(UpdateRequisitionListActivity.this);
        requestQueue.add(request1);

        seamnumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Initialize dialog
                dialog = new Dialog(UpdateRequisitionListActivity.this);

                // set custom dialog
                dialog.setContentView(R.layout.dialog_searchable_spinner);

                // set custom height and width
                dialog.getWindow().setLayout(750, 1100);

                // set transparent background
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                // show dialog
                dialog.show();

                // Initialize and assign variable
                EditText editText = dialog.findViewById(R.id.edit_text);
                ListView listView = dialog.findViewById(R.id.list_view);



                listView.setAdapter(seamAdapter);
                editText.addTextChangedListener(new TextWatcher() {
                                                    @Override
                                                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                                    }

                                                    @Override
                                                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                                        seamAdapter.getFilter().filter(charSequence);
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
                        seamnumber.setText(seamAdapter.getItem(position));

                        // Dismiss dialog
                        dialog.dismiss();

                        // Toast.makeText(RequisitionAddActivity.this, seamnumber.getText().toString(), Toast.LENGTH_SHORT).show();


                    }
                });
            }
        });
    }
    private void DataInsert() {

        String jobnumber = setjobnumberid.getText().toString().trim();
        String requser = setReqUserid.getText().toString().trim();
        String categorydata = setcategoryid.getText().toString().trim();
        String typedata = setypeid.getText().toString().trim();
        String jobsizedata  = setsizeid.getText().toString().trim();
        String seam = seamnumber.getText().toString().trim();
        String quantity = quantityreq.getText().toString().trim();
        String remarkdata = remark.getText().toString().trim();




        if (TextUtils.isEmpty(jobnumber)) {
            Toast.makeText(UpdateRequisitionListActivity.this, "Please Select Job Number", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(requser)) {
            Toast.makeText(UpdateRequisitionListActivity.this, "Please Select Requisition User", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(categorydata)) {
            Toast.makeText(UpdateRequisitionListActivity.this, "Please Select Job Category", Toast.LENGTH_SHORT).show();
            return;
        }else if (TextUtils.isEmpty(typedata)) {
            Toast.makeText(UpdateRequisitionListActivity.this, "Please Select Job Type ", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(jobsizedata)) {
            Toast.makeText(UpdateRequisitionListActivity.this, "Please Select Job Size", Toast.LENGTH_SHORT).show();
            return;
        }else if (TextUtils.isEmpty(seam)) {
            Toast.makeText(UpdateRequisitionListActivity.this, "Please Select Seam Number", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(quantity)) {
            Toast.makeText(UpdateRequisitionListActivity.this, "Enter Quantity", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            ProgressDialog progressDialog = new ProgressDialog(UpdateRequisitionListActivity.this);
            progressDialog.setMessage("Loading... Please Wait!");
            progressDialog.show();


            String token = getIntent().getStringExtra("token");
            String userId = getIntent().getStringExtra("userId");
            String location = getIntent().getStringExtra("location");
            String user_employee_type = appConfig.getuser_employee_type();

            String Id = getIntent().getStringExtra("id");

            // Toast.makeText(this, Id, Toast.LENGTH_SHORT).show();
            StringRequest request = new StringRequest(Request.Method.POST, "https://mployis.com/staging/api/requisition/update_requisition",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(response);
                                String message = jsonObject.getString("message");
                                Toast.makeText(UpdateRequisitionListActivity.this, message, Toast.LENGTH_SHORT).show();

                                startActivity(new Intent(getApplicationContext(),RequisitionListActivity.class));
//                                if(message.equals("Job size created successfully")) {
//                                    startActivity(new Intent(getApplicationContext(), Job_List_Activity.class));
//                                }
//                                else
//                                {
//                                    Toast.makeText(Update_JobList_Activity.this, message, Toast.LENGTH_SHORT).show();
//
//                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            //Toast.makeText(Add_Stock_Category_Activity.this, response, Toast.LENGTH_SHORT).show();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(UpdateRequisitionListActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap headers = new HashMap();
                    headers.put("user_token", token);
                    headers.put("user_id", userId);
                    headers.put("project_location_id", location);
                    headers.put("user_employee_type", user_employee_type);

                    return headers;
                }

                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();

                    params.put("req_job_id", jobnumber);
                    params.put("req_user_id[]", requser);
                    params.put("req_category_id", categorydata);
                    params.put("req_type_id", typedata);
                    params.put("req_size_id", jobsizedata);
                    params.put("seam_number", seam);
                    params.put("req_quantity", quantity);
                    params.put("req_remark", remarkdata);
                    params.put("req_id", Id);

                    return params;
                }

            };
            RequestQueue requestQueue = Volley.newRequestQueue(UpdateRequisitionListActivity.this);
            requestQueue.add(request);
        }

    }


}