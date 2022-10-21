package com.example.phils.Admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

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
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.phils.MultipleItemSelectInSpinner.AssignUserJobWelderGrienderData;
import com.example.phils.Adapter.AssignUserMultiWelderGrienderAdapter;
import com.example.phils.MultipleItemSelectInSpinner.JobIncompleteData;
import com.example.phils.Adapter.MultiJobInCompleteAdapter;
import com.example.phils.R;
import com.example.phils.Shareprefered.AppConfig;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Assign_user_Job_Activity extends AppCompatActivity {

    TextView select_job,assign_welder;
    TextView setjob,setwg;
    RequestQueue requestQueue;

    Button btn;
    TextView location_save;
    AppConfig appConfig;

    //Woker Grinder and Welder
    boolean[] wokerlenght;
    ArrayList<String> wokerList = new ArrayList<>();
    ArrayList<String> wokerListvalue   = new ArrayList<>();

    static String wokerValue[];
    static String wokerId[];


    //Job
    boolean[] joblength;
    ArrayList<String> jobList = new ArrayList<>();
    ArrayList<String> jobListvalue = new ArrayList<>();

    static String jobValue[];
    static String jobId[];

    Button logout,location;
    Button btnnotification;
    ImageView img,profile;
    TextView locationtext;
    Dialog dialog;

    ProgressDialog progressDialog;




    private ArrayList<JobIncompleteData> jobIncompleteData = new ArrayList<>();
    private MultiJobInCompleteAdapter multiJobInCompleteAdapter;
    private ArrayAdapter arrayAdapterjobincomolete;


    private ArrayList<AssignUserJobWelderGrienderData> assignUserJobWelderGrienderDataList = new ArrayList<>();
    private AssignUserMultiWelderGrienderAdapter assignUserMultiWelderGrienderAdapter;
    private ArrayAdapter arrayAdapterJjobweldergrinder;


    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(),Job_List_Activity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_user_job);
        appConfig = new AppConfig(this);
        progressDialog = new ProgressDialog(this);


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
                dialog=new Dialog(Assign_user_Job_Activity.this);

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
                        startActivity(new Intent(Assign_user_Job_Activity.this,LoginActivity.class));
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



        String access_module = appConfig.getaccess_module().trim();
//        String access_module = getIntent().getStringExtra("access_module");
//
        String text = access_module.toString().replace("[", "").replace("]", "");
        String withoutQuotes_line1 = text.replace("\"", "");
        // Log.d("mekya",withoutQuotes_line1);
        String [] items = withoutQuotes_line1.split("\\s*,\\s*");

        String userlist="",stock="",stockcategorylist="",stocktypelist="",stocksizelist="",stockmakelist="",stockuomlist="",stocklist="";
        String job="",jobcategorylist="",jobsizelist="",joblist="";
        String requisition="",requisitionlist="",receiverlist="";
        for (int i =0;i<items.length;i++) {

            Log.d("itemshar",items[i]);

            //Stock
            if (items[i].equals("user-list")) { userlist = "user-list"; }
            if (items[i].equals("stock")) { stock = "stock"; }
            if (items[i].equals("stock-category-list")) { stockcategorylist = "stock-category-list"; }
            if (items[i].equals("stock-type-list")) { stocktypelist = "stock-type-list"; }
            if (items[i].equals("stock-size-list")) { stocksizelist = "stock-size-list"; }
            if (items[i].equals("stock-make-list")) { stockmakelist = "stock-make-list"; }
            if (items[i].equals("stock-uom-list")) { stockuomlist = "stock-uom-list"; }
            if (items[i].equals("stock-list")) { stocklist = "stock-list"; }

            //job
            if (items[i].equals("job")) { job = "job"; }
            if (items[i].equals("job-category-list")) { jobcategorylist = "job-category-list"; }
            if (items[i].equals("job-size-list")) { jobsizelist = "job-size-list"; }
            if (items[i].equals("job-list")) { joblist = "job-list"; }

            //Requisition
            if (items[i].equals("requisition")) { requisition = "requisition"; }
            if (items[i].equals("requisition-list")) { requisitionlist = "requisition-list"; }
            if (items[i].equals("receiver-list")) { receiverlist = "receiver-list"; }
        }
        Menu menu = navigationView.getMenu();

        //Requisition
        if(requisition.equals("requisition")){
            MenuItem menuItem = menu.findItem(R.id.requisitionnav);
            menuItem.setVisible(true);
        }
        else
        {
            MenuItem menuItem = menu.findItem(R.id.requisitionnav);
            menuItem.setVisible(false);
        }
//
        if(requisitionlist.equals("requisition-list")){
            MenuItem menuItem = menu.findItem(R.id.resqu_list);
            menuItem.setVisible(true);
        }else
        {
            MenuItem menuItem = menu.findItem(R.id.resqu_list);
            menuItem.setVisible(false);
        }

        if(receiverlist.equals("receiver-list")){
            MenuItem menuItem = menu.findItem(R.id.resqu_reviever);
            menuItem.setVisible(true);
        }else
        {
            MenuItem menuItem = menu.findItem(R.id.resqu_reviever);
            menuItem.setVisible(false);
        }



        //Job Portion

        if(job.equals("job")){
            MenuItem menuItem = menu.findItem(R.id.jobnav);
            menuItem.setVisible(true);
        }
        else
        {
            MenuItem menuItem = menu.findItem(R.id.jobnav);
            menuItem.setVisible(false);
        }
//
        if(jobcategorylist.equals("job-category-list")){
            MenuItem menuItem = menu.findItem(R.id.category_job);
            menuItem.setVisible(true);
        }else
        {
            MenuItem menuItem = menu.findItem(R.id.category_job);
            menuItem.setVisible(false);
        }

        if(jobsizelist.equals("job-size-list")){
            MenuItem menuItem = menu.findItem(R.id.Size_job);
            menuItem.setVisible(true);
        }else
        {
            MenuItem menuItem = menu.findItem(R.id.Size_job);
            menuItem.setVisible(false);
        }

        if(joblist.equals("job-list")){
            MenuItem menuItem = menu.findItem(R.id.List_job);
            menuItem.setVisible(true);
        }else
        {
            MenuItem menuItem = menu.findItem(R.id.List_job);
            menuItem.setVisible(false);
        }


        //Stock portion
        if (userlist.equals("user-list")) {
            MenuItem menuItem = menu.findItem(R.id.user);
            menuItem.setVisible(true);
        }
        else
        {
            MenuItem menuItem = menu.findItem(R.id.user);
            menuItem.setVisible(false);
        }

        if (stock.equals("stock")) {
            MenuItem menuItem = menu.findItem(R.id.stockidnav);
            menuItem.setVisible(true);
        }
        else
        {
            MenuItem menuItem = menu.findItem(R.id.stockidnav);
            menuItem.setVisible(false);
        }

        if(stockcategorylist.equals("stock-category-list")){
            MenuItem menuItem = menu.findItem(R.id.category_stock);
            menuItem.setVisible(true);
        }else
        {
            MenuItem menuItem = menu.findItem(R.id.category_stock);
            menuItem.setVisible(false);
        }

        if(stocktypelist.equals("stock-type-list")){
            MenuItem menuItem = menu.findItem(R.id.type_stock);
            menuItem.setVisible(true);
        }else
        {
            MenuItem menuItem = menu.findItem(R.id.type_stock);
            menuItem.setVisible(false);
        }

        if(stocksizelist.equals("stock-size-list")){
            MenuItem menuItem = menu.findItem(R.id.size_stock);
            menuItem.setVisible(true);
        }else
        {
            MenuItem menuItem = menu.findItem(R.id.size_stock);
            menuItem.setVisible(false);
        }

        if(stockmakelist.equals("stock-make-list")){
            MenuItem menuItem = menu.findItem(R.id.make_stock);
            menuItem.setVisible(true);
        }else
        {
            MenuItem menuItem = menu.findItem(R.id.make_stock);
            menuItem.setVisible(false);
        }

        if(stockuomlist.equals("stock-uom-list")){
            MenuItem menuItem = menu.findItem(R.id.umo_stock);
            menuItem.setVisible(true);
        }else
        {
            MenuItem menuItem = menu.findItem(R.id.umo_stock);
            menuItem.setVisible(false);
        }

        if(stocklist.equals("stock-list")){
            MenuItem menuItem = menu.findItem(R.id.list_stock);
            menuItem.setVisible(true);
        }else
        {
            MenuItem menuItem = menu.findItem(R.id.list_stock);
            menuItem.setVisible(false);
        }






        if(1==2) {
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


        select_job = findViewById(R.id.select_job);
        assign_welder = findViewById(R.id.assign_welder);
        btn = findViewById(R.id.insert_type);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.setMessage("Please Wait");
                progressDialog.show();

                String token = getIntent().getStringExtra("token");
                String userId = getIntent().getStringExtra("userId");
                String location = getIntent().getStringExtra("location");
                String user_employee_type = appConfig.getuser_employee_type();

                //Toast.makeText(Assign_user_Job_Activity.this, setjob.getText().toString()+"/"+setwg.getText().toString(), Toast.LENGTH_SHORT).show();

               // Log.d("matchts",token+"/"+setjob.getText().toString()+"/"+setwg.getText().toString());
                StringRequest request = new StringRequest(Request.Method.POST, "https://erp.philsengg.com/api/job/job_assign_user",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                JSONObject jsonObject = null;
                                try {
                                    jsonObject = new JSONObject(response);
                                    String message = jsonObject.getString("message");

                                    if(message.equals("Invalid user request")){
                                        Toast.makeText(Assign_user_Job_Activity.this, message, Toast.LENGTH_SHORT).show();
                                        appConfig.updateUserLoginStatus(false);
                                        startActivity(new Intent(Assign_user_Job_Activity.this, LoginActivity.class));
                                        finish();
                                    }
                                    else {

                                        Toast.makeText(Assign_user_Job_Activity.this, message, Toast.LENGTH_SHORT).show();
                                        progressDialog.dismiss();
                                        startActivity(new Intent(getApplicationContext(), Assign_user_Job_Activity.class));
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                //Toast.makeText(Add_Stock_Category_Activity.this, response, Toast.LENGTH_SHORT).show();
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Assign_user_Job_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                })
                {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        HashMap headers = new HashMap();
                        headers.put("Usertoken",token);
                        headers.put("Userid", userId);
                        headers.put("Projectlocationid", location);
                        headers.put("Useremployeetype", user_employee_type);

                        return headers;
                    }

                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> params = new HashMap<String,String>();
                        params.put("job_id[]",setjob.getText().toString());
                        params.put("job_emp_user[]",setwg.getText().toString());
                        return  params;
                    }


                };
                RequestQueue  requestQueue = Volley.newRequestQueue(Assign_user_Job_Activity.this);
                requestQueue.add(request);
            }
        });



        setjob = findViewById(R.id.setjob);
        setwg = findViewById(R.id.setwg);


        requestQueue = Volley.newRequestQueue(this);

        String token = getIntent().getStringExtra("token");
        String userId = getIntent().getStringExtra("userId");
        String location = getIntent().getStringExtra("location");
        String user_employee_type = appConfig.getuser_employee_type();

        jobIncompleteData = new ArrayList<>();

        StringRequest request = new StringRequest(Request.Method.POST, "https://erp.philsengg.com/api/job/job_incomplete",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            int j=0;

                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("message");

                            //Toast.makeText(Assign_user_Job_Activity.this, message, Toast.LENGTH_SHORT).show();
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            for(int i=0;i<jsonArray.length();i++)
                            {
                                j++;
                                JSONObject object = jsonArray.getJSONObject(i);
                                String stock_category_id = object.getString("job_id");
                                String stock_category_name = object.getString("job_number");
                                JobIncompleteData jobIncompleteDatas = new JobIncompleteData();

                                Log.d("butter",stock_category_id);
                                jobIncompleteDatas.setId(stock_category_id);
                                jobIncompleteDatas.setName(stock_category_name);
//                                if (i == 0) {
//                                    jobIncompleteDatas.setChecked(true);
//                                }
                                jobIncompleteData.add(jobIncompleteDatas);

                            }
                            multiJobInCompleteAdapter.setJobIncompleteData(jobIncompleteData);
                            arrayAdapterjobincomolete = new ArrayAdapter(Assign_user_Job_Activity.this,
                                    android.R.layout.simple_spinner_dropdown_item,jobIncompleteData);
                            arrayAdapterjobincomolete.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);



                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Assign_user_Job_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap headers = new HashMap();
                headers.put("Usertoken",token);
                headers.put("Userid", userId);
                headers.put("Projectlocationid", location);
                headers.put("Useremployeetype", user_employee_type);

                return headers;
                //return super.getHeaders();
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(Assign_user_Job_Activity.this);
        requestQueue.add(request);

        multiJobInCompleteAdapter = new MultiJobInCompleteAdapter(this,jobIncompleteData);


        select_job.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog = new Dialog(Assign_user_Job_Activity.this);

                // set custom dialog
                dialog.setContentView(R.layout.demospinner1);

                // set custom height and width
                dialog.getWindow().setLayout(950, 1500);

                // set transparent background
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffffff")));

                // show dialog
                dialog.show();

                // Initialize and assign variable
                androidx.appcompat.widget.AppCompatButton btnGetSelected = dialog.findViewById(R.id.btnGetSelected);
                androidx.appcompat.widget.AppCompatButton btnAdd = dialog.findViewById(R.id.btnAdd);
                btnAdd.setVisibility(View.GONE);

                androidx.recyclerview.widget.RecyclerView recyclerView = dialog.findViewById(R.id.recyclerView);
                EditText editText =dialog.findViewById(R.id.edit_text);


                recyclerView.setLayoutManager(new LinearLayoutManager(Assign_user_Job_Activity.this));
                recyclerView.addItemDecoration(new DividerItemDecoration(Assign_user_Job_Activity.this, LinearLayoutManager.VERTICAL));
                multiJobInCompleteAdapter = new MultiJobInCompleteAdapter(Assign_user_Job_Activity.this,jobIncompleteData);
                recyclerView.setAdapter(multiJobInCompleteAdapter);

                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        btnGetSelected.setVisibility(View.VISIBLE);
                        btnAdd.setVisibility(View.GONE);
                        editText.setText("");


                    }
                });

                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                       // arrayAdapterjobincomolete.getFilter().filter(charSequence);

                        if(charSequence.toString().equals("")) {
                            btnGetSelected.setVisibility(View.VISIBLE);

                            ArrayList<JobIncompleteData> filEmployees= new ArrayList<>();
                            for (JobIncompleteData item: jobIncompleteData){
                                if(item.getName().toLowerCase(Locale.ROOT).contains(charSequence)
                                        || item.getName().toUpperCase(Locale.ROOT).contains(charSequence)){
                                    filEmployees.add(item);
                                }
                            }
                            multiJobInCompleteAdapter.filterList(filEmployees);
                            btnAdd.setVisibility(View.GONE);
                        }
                        else {
                            ArrayList<JobIncompleteData> filEmployees= new ArrayList<>();
                            for (JobIncompleteData item: jobIncompleteData){
                                if(item.getName().toLowerCase(Locale.ROOT).contains(charSequence)
                                        || item.getName().toUpperCase(Locale.ROOT).contains(charSequence)){
                                    filEmployees.add(item);
                                }
                            }
                            multiJobInCompleteAdapter.filterList(filEmployees);

                            btnAdd.setVisibility(View.VISIBLE);
                            btnGetSelected.setVisibility(View.GONE);


                        }


                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

//                        ArrayList<JobIncompleteData> filEmployees= new ArrayList<>();
//                        for (JobIncompleteData item: jobIncompleteData){
//                            if(item.getName().toLowerCase(Locale.ROOT).contains(editable)
//                                    || item.getName().toUpperCase(Locale.ROOT).contains(editable)){
//                                filEmployees.add(item);
//                            }
//                        }
//                        multiJobInCompleteAdapter.filterList(filEmployees);
                    }
                });


                btnGetSelected.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (multiJobInCompleteAdapter.getSelected().size() > 0) {
                            StringBuilder stringBuilder = new StringBuilder();
                            StringBuilder stringBuildername = new StringBuilder();

                            for (int i = 0; i < multiJobInCompleteAdapter.getSelected().size(); i++) {
                                stringBuilder.append(multiJobInCompleteAdapter.getSelected().get(i).getId());
                                stringBuildername.append(multiJobInCompleteAdapter.getSelected().get(i).getName());

                                stringBuilder.append(",");
                                stringBuildername.append(", ");
                            }
                            select_job.setText(stringBuildername.toString().trim());
                            setjob.setText(stringBuilder.toString().trim());
                         //   Toast.makeText(Assign_user_Job_Activity.this, stringBuilder.toString().trim(), Toast.LENGTH_SHORT).show();
                            dialog.dismiss();

                        } else {
                            Toast.makeText(Assign_user_Job_Activity.this, "No Selection", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

//        StringRequest request1 = new StringRequest(Request.Method.POST, "https://erp.philsengg.com/api/job/job_incomplete",
//                new com.android.volley.Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//
//                        try {
//
//                            JSONObject jsonObject = new JSONObject(response);
//                            String message = jsonObject.getString("message");
//                            if(message.equals("Invalid user request")){
//                                Toast.makeText(Assign_user_Job_Activity.this, message, Toast.LENGTH_SHORT).show();
//                                appConfig.updateUserLoginStatus(false);
//                                startActivity(new Intent(Assign_user_Job_Activity.this, LoginActivity.class));
//                                finish();
//                            }
//                            else {
//                                JSONArray jsonArray = jsonObject.getJSONArray("data");
//
//                                jobValue = new String[jsonArray.length()];
//                                jobId = new String[jsonArray.length()];
//                                joblength = new boolean[jobValue.length];
//
//                                for (int i = 0; i < jsonArray.length(); i++) {
//                                    JSONObject object = jsonArray.getJSONObject(i);
//
//                                    String job_id = object.getString("job_id");
//                                    String job_number = object.getString("job_number");
//
//                                    jobValue[i] = job_number;
//                                    jobId[i] = job_id;
//
//                                }
//
//                                Collections.reverse(Arrays.asList(jobValue));
//                                Collections.reverse(Arrays.asList(jobId));
//
//                            }
//                        }
//                        catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(Assign_user_Job_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        })
//        {
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                HashMap headers = new HashMap();
//                headers.put("Usertoken",token);
//                headers.put("Userid", userId);
//                headers.put("Projectlocationid", location);
//                headers.put("Useremployeetype", user_employee_type);
//
//                return headers;
//                //return super.getHeaders();
//            }
//        };
//
//        RequestQueue requestQueue1 = Volley.newRequestQueue(Assign_user_Job_Activity.this);
//        requestQueue1.add(request1);

//        select_job.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // Initialize alert dialog
//                AlertDialog.Builder builder = new AlertDialog.Builder(Assign_user_Job_Activity.this);
//
//                // set title
//                builder.setTitle("Select Language");
//
//
//
//                // set dialog non cancelable
//                builder.setCancelable(false);
//
//                builder.setMultiChoiceItems(jobValue, joblength, new DialogInterface.OnMultiChoiceClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
//                        // check condition
//                        if (b) {
//                            // when checkbox selected
//                            // Add position  in lang list
//                            jobList.add(jobId[i]);
//                            jobListvalue.add(jobValue[i]);
//
//                           // Log.d("Nilesh",joblistId[i]);
//
//
//                            // Sort array list
//                            Collections.sort(jobList);
//
//                        } else {
//                            // when checkbox unselected
//                            // Remove position from langList
//                            jobList.remove(jobId[i]);
//                            jobListvalue.remove(jobValue[i]);
//
//                        }
//                        Log.d("Nil", String.valueOf(jobList));
//                    }
//                });
//                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        // Initialize string builder
////                        StringBuilder stringBuilder = new StringBuilder();
//                        String stringBuilder= String.join(",", jobListvalue);
//                        String stringbuilder = String.join(",",jobList);
////                        Log.d("nil",stringBuilder);
////                        Log.d("nil",stringbuilder);
//
//                        select_job.setText(stringBuilder);
//                        setjob.setText(stringbuilder);
//                        //Toast.makeText(Assign_user_Job_Activity.this, setjob.getText().toString(), Toast.LENGTH_SHORT).show();
//
//                    }
//                });
//
//                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        // dismiss dialog
//                        dialogInterface.dismiss();
//                    }
//                });
//                builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        // use for loop
//                        for (int j = 0; j < joblength.length; j++) {
//                            // remove all selection
//                            joblength[j] = false;
//                            // clear language list
//                            jobListvalue.clear();
//                            jobList.clear();
//                            // clear text view value
//                            select_job.setText("");
//                        }
//                    }
//                });
//                // show dialog
//                builder.show();
//
//            }
//        });

        assignUserJobWelderGrienderDataList = new ArrayList<>();

        StringRequest request4 = new StringRequest(Request.Method.POST, "https://erp.philsengg.com/api/job/job_welder_grinder_list",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            int j=0;

                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("message");

                            //Toast.makeText(Assign_user_Job_Activity.this, message, Toast.LENGTH_SHORT).show();
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            for(int i=0;i<jsonArray.length();i++)
                            {
                                j++;
                                JSONObject object = jsonArray.getJSONObject(i);
                                String user_id = object.getString("user_id");

                                String user_name = object.getString("user_full_name");
                                String user_employee_id = object.getString("user_employee_id");

                                AssignUserJobWelderGrienderData assignUserJobWelderGrienderData = new AssignUserJobWelderGrienderData();

                                Log.d("butter",user_id);
                                assignUserJobWelderGrienderData.setId(user_id);
                                assignUserJobWelderGrienderData.setName(user_name+"-"+user_employee_id);
//                                if (i == 0) {
//                                    assignUserJobWelderGrienderData.setChecked(true);
//                                }
                                assignUserJobWelderGrienderDataList.add(assignUserJobWelderGrienderData);

                            }
                            assignUserMultiWelderGrienderAdapter.setAssignUserJobWelderGrienderData(assignUserJobWelderGrienderDataList);

                             arrayAdapterJjobweldergrinder = new ArrayAdapter(Assign_user_Job_Activity.this,
                                    android.R.layout.simple_spinner_dropdown_item,assignUserJobWelderGrienderDataList);
                            arrayAdapterJjobweldergrinder.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);



                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Assign_user_Job_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap headers = new HashMap();
                headers.put("Usertoken",token);
                headers.put("Userid", userId);
                headers.put("Projectlocationid", location);
                headers.put("Useremployeetype", user_employee_type);

                return headers;
                //return super.getHeaders();
            }
        };

        RequestQueue requestQueue4 = Volley.newRequestQueue(Assign_user_Job_Activity.this);
        requestQueue4.add(request4);

        assignUserMultiWelderGrienderAdapter = new AssignUserMultiWelderGrienderAdapter(this,assignUserJobWelderGrienderDataList);



        assign_welder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog = new Dialog(Assign_user_Job_Activity.this);

                // set custom dialog
                dialog.setContentView(R.layout.demospinner1);

                // set custom height and width
                dialog.getWindow().setLayout(950, 1500);

                // set transparent background
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffffff")));

                // show dialog
                dialog.show();

                // Initialize and assign variable
                androidx.appcompat.widget.AppCompatButton btnGetSelected = dialog.findViewById(R.id.btnGetSelected);
                androidx.appcompat.widget.AppCompatButton btnAdd = dialog.findViewById(R.id.btnAdd);
                btnAdd.setVisibility(View.GONE);

                androidx.recyclerview.widget.RecyclerView recyclerView = dialog.findViewById(R.id.recyclerView);
                EditText editText =dialog.findViewById(R.id.edit_text);


                recyclerView.setLayoutManager(new LinearLayoutManager(Assign_user_Job_Activity.this));
                recyclerView.addItemDecoration(new DividerItemDecoration(Assign_user_Job_Activity.this, LinearLayoutManager.VERTICAL));
                assignUserMultiWelderGrienderAdapter = new AssignUserMultiWelderGrienderAdapter(Assign_user_Job_Activity.this,assignUserJobWelderGrienderDataList);
                recyclerView.setAdapter(assignUserMultiWelderGrienderAdapter);

                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        btnGetSelected.setVisibility(View.VISIBLE);
                        btnAdd.setVisibility(View.GONE);
                        editText.setText("");


                    }
                });

                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                       // arrayAdapterJjobweldergrinder.getFilter().filter(charSequence);

                        if(charSequence.toString().equals("")) {
                            btnGetSelected.setVisibility(View.VISIBLE);
                            ArrayList<AssignUserJobWelderGrienderData> filEmployees= new ArrayList<>();
                            for (AssignUserJobWelderGrienderData item: assignUserJobWelderGrienderDataList){
                                if(item.getName().toLowerCase(Locale.ROOT).contains(charSequence)
                                        || item.getName().toUpperCase(Locale.ROOT).contains(charSequence)){
                                    filEmployees.add(item);
                                }
                            }
                            assignUserMultiWelderGrienderAdapter.filterList(filEmployees);
                            btnAdd.setVisibility(View.GONE);
                        }
                        else {
                            ArrayList<AssignUserJobWelderGrienderData> filEmployees= new ArrayList<>();
                            for (AssignUserJobWelderGrienderData item: assignUserJobWelderGrienderDataList){
                                if(item.getName().toLowerCase(Locale.ROOT).contains(charSequence)
                                        || item.getName().toUpperCase(Locale.ROOT).contains(charSequence)){
                                    filEmployees.add(item);
                                }
                            }
                            assignUserMultiWelderGrienderAdapter.filterList(filEmployees);

                            btnAdd.setVisibility(View.VISIBLE);
                            btnGetSelected.setVisibility(View.GONE);


                        }
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
//                        ArrayList<AssignUserJobWelderGrienderData> filEmployees= new ArrayList<>();
//                        for (AssignUserJobWelderGrienderData item: assignUserJobWelderGrienderDataList){
//                            if(item.getName().toLowerCase(Locale.ROOT).contains(editable)
//                                    || item.getName().toUpperCase(Locale.ROOT).contains(editable)){
//                                filEmployees.add(item);
//                            }
//                        }
//                        assignUserMultiWelderGrienderAdapter.filterList(filEmployees);
                    }
                });


                btnGetSelected.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (assignUserMultiWelderGrienderAdapter.getSelected().size() > 0) {
                            StringBuilder stringBuilder = new StringBuilder();
                            StringBuilder stringBuildername = new StringBuilder();

                            for (int i = 0; i < assignUserMultiWelderGrienderAdapter.getSelected().size(); i++) {
                                stringBuilder.append(assignUserMultiWelderGrienderAdapter.getSelected().get(i).getId());
                                stringBuildername.append(assignUserMultiWelderGrienderAdapter.getSelected().get(i).getName());

                                stringBuilder.append(",");
                                stringBuildername.append(" , ");
                            }
                            assign_welder.setText( stringBuildername.toString().trim());
                            setwg.setText( stringBuilder.toString().trim());
                            //Toast.makeText(Assign_user_Job_Activity.this, stringBuilder.toString().trim(), Toast.LENGTH_SHORT).show();

                            dialog.dismiss();

                        } else {
                            Toast.makeText(Assign_user_Job_Activity.this, "No Selection", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
//        StringRequest request4 = new StringRequest(Request.Method.POST, "https://erp.philsengg.com/api/job/job_welder_grinder_list",
//                new com.android.volley.Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//
//                        try {
//
//                            JSONObject jsonObject = new JSONObject(response);
//                            //String message = jsonObject.getString("message");
//
//                            JSONArray jsonArray = jsonObject.getJSONArray("data");
//
//                            wokerValue = new String[jsonArray.length()];
//                            wokerId = new String[jsonArray.length()];
//                            wokerlenght = new boolean[wokerValue.length];
//
//                            for(int i=0; i<jsonArray.length();i++)
//                            {
//                                JSONObject object = jsonArray.getJSONObject(i);
//                                String user_id = object.getString("user_id");
//                                String user_name = object.getString("user_full_name");
//                                String user_employee_id = object.getString("user_employee_id");
//
//                                wokerValue[i]=user_name+" - "+user_employee_id;
//                                wokerId[i]=user_id;
//
//                            }
//
////                            Collections.reverse(Arrays.asList(wokerValue));
////                            Collections.reverse(Arrays.asList(wokerId));
//
//                        }
//                        catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(Assign_user_Job_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        })
//        {
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                HashMap headers = new HashMap();
//                headers.put("Usertoken",token);
//                headers.put("Userid", userId);
//                headers.put("Projectlocationid", location);
//                headers.put("Useremployeetype", user_employee_type);
//
//                return headers;
//                //return super.getHeaders();
//            }
//        };
//
//        RequestQueue requestQueue4 = Volley.newRequestQueue(Assign_user_Job_Activity.this);
//        requestQueue4.add(request4);
//
//        assign_welder.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // Initialize alert dialog
//                AlertDialog.Builder builder = new AlertDialog.Builder(Assign_user_Job_Activity.this);
//
//                // set title
//                builder.setTitle("Select Language");
//
//                // set dialog non cancelable
//                builder.setCancelable(false);
//
//                builder.setMultiChoiceItems(wokerValue, wokerlenght, new DialogInterface.OnMultiChoiceClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
//                        // check condition
//                        if (b) {
//                            // when checkbox selected
//                            // Add position  in lang list
//                            wokerList.add(wokerId[i]);
//                            wokerListvalue.add(wokerValue[i]);
//
//                            // Sort array list
//                            Collections.sort(wokerList);
//
//                        } else {
//                            // when checkbox unselected
//                            // Remove position from langList
//                            wokerList.remove(wokerId[i]);
//                            wokerListvalue.remove(wokerValue[i]);
//
//                        }
//                        Log.d("Nil", String.valueOf(wokerList));
//                    }
//                });
//                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        // Initialize string builder
////                        StringBuilder stringBuilder = new StringBuilder();
//                        String stringBuilder= String.join(",", wokerListvalue);
//                        String stringbuilder= String.join(",", wokerList);
//
//                        Log.d("nil",stringBuilder);
//                        Log.d("nil",stringbuilder);
//
//                        assign_welder.setText(stringBuilder);
//                        setwg.setText(stringbuilder);
//
//                    }
//                });
//
//                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        // dismiss dialog
//                        dialogInterface.dismiss();
//                    }
//                });
//                builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        // use for loop
//                        for (int j = 0; j < wokerlenght.length; j++) {
//                            // remove all selection
//                            wokerlenght[j] = false;
//                            // clear language list
//                            wokerListvalue.clear();
//                            wokerList.clear();
//                            // clear text view value
//                            assign_welder.setText("");
//                        }
//                    }
//                });
//                // show dialog
//                builder.show();
//
//            }
//        });



    }
}