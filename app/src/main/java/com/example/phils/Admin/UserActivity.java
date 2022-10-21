package com.example.phils.Admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
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
import com.example.phils.Adapter.UserAdapterClass;
import com.example.phils.R;
import com.example.phils.ResponseModels.ResponseModelUser;
import com.example.phils.Shareprefered.AppConfig;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.switchmaterial.SwitchMaterial;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class UserActivity extends AppCompatActivity {
    ProgressDialog progressDialog;
    RecyclerView recview;
    SearchView searchView;

//    UserAdapterClass userAdapterClass;
    UserAdapterClass adapter;
    List<ResponseModelUser> data;
    ResponseModelUser responseModelUser;
    LinearLayoutManager linearLayoutManager;
    TextView location_save;
    AppConfig appConfig;
    Button button;

     private UserAdapterClass.RecycleViewClickListener listener;

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }


    Button logout,location;
    Button btnnotification;
    ImageView img,profile;
    TextView locationtext;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

//        appConfig = new AppConfig(this);
//
//
//        String ko = appConfig.getRequisition();
//        Log.d("kooooo",ko);
//
//        if(appConfig.getRequisition().equals("true")){
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//            setTheme(R.style.Theme_Dark);
//
//        }
//        else{
//            setTheme(R.style.Theme_Day);
//        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
      //  AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//        if(AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_YES){
//            setTheme(R.style.Theme_Dark);
//        }
//        else{
//            setTheme(R.style.Theme_Day);
//        }

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
                dialog=new Dialog(UserActivity.this);

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
                        startActivity(new Intent(UserActivity.this,LoginActivity.class));
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

//                SwitchMaterial switchMaterial = dialog.findViewById(R.id.switchmaterial);
//
//                switchMaterial = dialog.findViewById(R.id.switchmaterial);
//
//
//                if(appConfig.getRequisition().equals("true")){
//
//                    switchMaterial.setChecked(true);
//
//                }
//                else{
//                    switchMaterial.setChecked(false);
//
//                }
//                switchMaterial.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                    @Override
//                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                        if(b)
//                        {
//                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//
//                            appConfig.SaveRequisition("true");
//
//                        }
//                        else
//                        {
//                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//
//                            appConfig.SaveRequisition("false");
//
//                        }
//                    }
//                });


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


        recview = findViewById(R.id.recview);
        searchView = findViewById(R.id.search);
        searchView.clearFocus();
        button = findViewById(R.id.adduser);

//        String access_module = appConfig.getaccess_module().trim();
//        String text = access_module.toString().replace("[", "").replace("]", "");
//        String withoutQuotes_line1 = text.replace("\"", "");
//        String [] items = withoutQuotes_line1.split("\\s*,\\s*");

        String adduser = "";

        for (int i =0;i<items.length;i++) {

            if (items[i].equals("add-user")) { adduser = "add-user"; }
        }
        if(adduser.equals("add-user")){
            button.setVisibility(View.VISIBLE);
        }
        else
        {
            button.setVisibility(View.GONE);
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AddUserActivity.class));
            }
        });




        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                fileList(newText);
                return true;
            }
        });
        fatchdata();
        recycleClickLister();
//
        linearLayoutManager = new LinearLayoutManager(this);
        recview.setLayoutManager(linearLayoutManager);

        data = new ArrayList<>();
        adapter = new UserAdapterClass(listener,data);
        recview.setAdapter(adapter);

//         adapter  = new UserAdapterClass(listener,data);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
//        recview.setLayoutManager(layoutManager);
//        recview.setItemAnimator(new DefaultItemAnimator());
//        recview.setAdapter(adapter);


       //

    }

    private void fileList(String newText) {

        List<ResponseModelUser> modelStockCategories = new ArrayList<>();
        for(ResponseModelUser responseModelUser : data)
        {
            if(responseModelUser.getUser_name().toLowerCase(Locale.ROOT).contains(newText.toLowerCase(Locale.ROOT))
            ||responseModelUser.getEmployee_type().toLowerCase(Locale.ROOT).contains(newText.toLowerCase(Locale.ROOT))
            ||responseModelUser.getUser_full_name().toLowerCase(Locale.ROOT).contains(newText.toLowerCase(Locale.ROOT))){
                modelStockCategories.add(responseModelUser);
            }
        }

        if (modelStockCategories.isEmpty()){
            Toast.makeText(this, "No Data found", Toast.LENGTH_SHORT).show();
        }
        else
        {
            adapter.setFilteredList(modelStockCategories);
        }
    }

    private void fatchdata() {
        progressDialog = new ProgressDialog(UserActivity.this);
        progressDialog.setMessage("Loading... Please Wait!");
        progressDialog.show();

        appConfig = new AppConfig(this);

        String token = appConfig.getuser_token();
        String userId = appConfig.getuser_id();
        String location = appConfig.getLocationId();
        String user_employee_type = appConfig.getuser_employee_type();

        StringRequest request = new StringRequest(Request.Method.POST, "https://erp.philsengg.com/api/user/user_list",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            int j=0;

                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("message");

                            //Toast.makeText(UserActivity.this, message, Toast.LENGTH_SHORT).show();
//                            if(message.equals("Invalid user request")){
//                                Toast.makeText(UserActivity.this, message, Toast.LENGTH_SHORT).show();
//                                appConfig.updateUserLoginStatus(false);
//                                startActivity(new Intent(UserActivity.this, LoginActivity.class));
//                                finish();
//                            }
//                            else {

                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    j++;
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String sn = String.valueOf(j);
                                    String user_id = object.getString("user_id");
                                    String user_name = object.getString("user_name");
                                    String user_employee_id = object.getString("user_employee_id");
                                    String user_employee_type = object.getString("user_employee_type");
                                    String employee_type = object.getString("employee_type");
                                    if(employee_type.equals(String.valueOf(0)))
                                    {
                                        employee_type = "Temporary";
                                    }
                                    else
                                    {
                                        employee_type = "Permanent";
                                    }
                                    String user_full_name = object.getString("user_full_name");
                                    String gender = object.getString("gender");
                                    String user_email_id = object.getString("user_email_id");
                                    String user_phone_number = object.getString("user_phone_number");
                                    String user_password = object.getString("user_password");
                                    String project_location_id = object.getString("project_location_id");
                                    String reporting_manager = object.getString("reporting_manager");
                                    String user_otp = object.getString("user_otp");
                                    if(user_otp.equals("null"))
                                    {
                                        user_otp = " ";
                                    }
                                    String user_otp_tried = object.getString("user_otp_tried");
                                    String user_token = object.getString("user_token");

                                    String user_status = object.getString("user_status");
                                    if(user_status.equals(String.valueOf(0)))
                                    {
                                        user_status = "Disable";
                                    }
                                    else
                                    {
                                        user_status = "Enable";
                                    }
                                    String user_updated_on = object.getString("user_updated_on");
                                    String user_created_on = object.getString("user_created_on");
                                    String emp_type_name = object.getString("emp_type_name");


                                    responseModelUser = new ResponseModelUser(sn,user_id,user_name,user_employee_id,user_employee_type,
                                            employee_type,user_full_name,gender,user_email_id,user_phone_number,user_password,project_location_id,
                                            reporting_manager,user_otp,user_otp_tried,user_token,user_status,user_updated_on,user_created_on,emp_type_name);
                                    data.add(responseModelUser);
                                    adapter.notifyDataSetChanged();
                                    progressDialog.dismiss();

                               // }
                            }

                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(UserActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }) {
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
                Map<String, String> params = new HashMap<String, String>();
                params.put("project_location_id", location);
                params.put("user_id", userId);

                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(UserActivity.this);
        requestQueue.add(request);

    }

    private void recycleClickLister() {
        listener = new UserAdapterClass.RecycleViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                String id = data.get(position).getUser_id();
                String employee_type = data.get(position).getEmployee_type();
                String user_full_name = data.get(position).getUser_full_name();
                String user_password = data.get(position).getUser_password();
                String user_name = data.get(position).getUser_name();
                String gender = data.get(position).getGender();
                String user_employee_id = data.get(position).getUser_employee_id();
                String user_mobile_number = data.get(position).getUser_phone_number();
                String user_email_id = data.get(position).getUser_email_id();
                String user_employee_type = data.get(position).getUser_employee_type();
                String reporting_manager = data.get(position).getReporting_manager();
                String user_status = data.get(position).getUser_status();

                Dialog  dialog=new Dialog(UserActivity.this);

                // set custom dialog
                dialog.setContentView(R.layout.custom_stock_category_button);

                // set custom height and width
                dialog.getWindow().setLayout(650,750);

                // set transparent background
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                // show dialog
                dialog.show();

                Button add = dialog.findViewById(R.id.add);

                String access_module = appConfig.getaccess_module().trim();
        String text = access_module.toString().replace("[", "").replace("]", "");
        String withoutQuotes_line1 = text.replace("\"", "");
        String [] items = withoutQuotes_line1.split("\\s*,\\s*");

                String updateuser = "";

                for (int i =0;i<items.length;i++) {

                    if (items[i].equals("update-user")) { updateuser = "update-user"; }
                }
                if(updateuser.equals("update-user")){
                    add.setVisibility(View.VISIBLE);
                }
                else
                {
                    add.setVisibility(View.GONE);
                    Toast.makeText(UserActivity.this, "No Access Available", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }

                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(getApplicationContext(), UpdateUserActivity.class);
                        intent.putExtra("id",id);
                        intent.putExtra("employee_type",employee_type);
                        intent.putExtra("user_full_name",user_full_name);
                        intent.putExtra("user_password",user_password);
                        intent.putExtra("user_name",user_name);
                        intent.putExtra("gender",gender);
                        intent.putExtra("user_employee_id",user_employee_id);
                        intent.putExtra("user_mobile_number",user_mobile_number);
                        intent.putExtra("user_email_id",user_email_id);
                        intent.putExtra("user_employee_type",user_employee_type);
                        intent.putExtra("reporting_manager",reporting_manager);
                        intent.putExtra("user_status",user_status);

                        startActivity(intent);

                    }
                });



                //Toast.makeText(UserActivity.this, kk, Toast.LENGTH_SHORT).show();
            }
        };
    }
}