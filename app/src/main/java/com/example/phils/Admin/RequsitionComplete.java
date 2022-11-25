package com.example.phils.Admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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
import com.example.phils.Adapter.RequisitionCompleteAdapterClass;
import com.example.phils.R;
import com.example.phils.ResponseModels.ResponseModelRequisitionComplete;
import com.example.phils.Shareprefered.AppConfig;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class RequsitionComplete extends AppCompatActivity {

    ArrayList<String> wokerList = new ArrayList<>();
    Button next,pre;
    static  int store = 0;
    static int sendvalue = 5;
    static int nagetivevalue = -5;

    Button add_reqlist;
    AppConfig appConfig;
    SearchView searchView;
    ProgressDialog progressDialog;
    TextView location_save,checklist;
    LinearLayoutManager linearLayoutManager;
    RecyclerView recview;
    Button other_reqlist,ongoing,open;


    Button logout,location;
    Button btnnotification;
    ImageView img,profile;
    TextView locationtext;
    Dialog dialog;

    List<ResponseModelRequisitionComplete> data = new ArrayList<>();
    RequisitionCompleteAdapterClass requisitionCompleteAdapterClass;
    ResponseModelRequisitionComplete responseModelRequisitionComplete;
    private RequisitionCompleteAdapterClass.RecycleViewClickListener listener;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requsition_complete);

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
                dialog=new Dialog(RequsitionComplete.this);

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
                        startActivity(new Intent(RequsitionComplete.this, LoginActivity.class));
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

        ongoing = findViewById(R.id.ongoing);
        ongoing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),RequisitionReciverList.class));
            }
        });
        add_reqlist = findViewById(R.id.add_reqlist);
        add_reqlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String token = appConfig.getuser_token();
                String userId = appConfig.getuser_id();
                String location = appConfig.getLocationId();

                Intent intent = new Intent(getApplicationContext(), RequisitionAddActivity.class);
                intent.putExtra("token",token);
                intent.putExtra("userId",userId);
                intent.putExtra("location",location);

                startActivity(intent);
                finish();
            }
        });

        other_reqlist = findViewById(R.id.other_reqlist);
        other_reqlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String token = appConfig.getuser_token();
                String userId = appConfig.getuser_id();
                String location = appConfig.getLocationId();

                Intent intent = new Intent(getApplicationContext(), OtherRequisitionAddActivity.class);
                intent.putExtra("token",token);
                intent.putExtra("userId",userId);
                intent.putExtra("location",location);

                startActivity(intent);
                finish();
            }
        });

        next = findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fatchdata(sendvalue);
            }
        });
        pre = findViewById(R.id.pre);
        pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fatchdata(nagetivevalue);

            }
        });
        ongoing = findViewById(R.id.ongoing);
        ongoing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RequisitionOnGoingActivity.class));
            }
        });
        open = findViewById(R.id.open);
        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),RequisitionListActivity.class));
            }
        });

        searchView = findViewById(R.id.search);
        searchView.clearFocus();

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

        fatchdata(0);
       // recycleClickLister();

        recview = findViewById(R.id.recview);
        linearLayoutManager = new LinearLayoutManager(this);
        recview.setLayoutManager(linearLayoutManager);

        requisitionCompleteAdapterClass = new RequisitionCompleteAdapterClass(listener,data);
        recview.setAdapter(requisitionCompleteAdapterClass);
    }

    private void fileList(String newText) {

        List<ResponseModelRequisitionComplete> responseModelRequisitionLists = new ArrayList<>();
        for(ResponseModelRequisitionComplete responseModelReciverComplete : data)
        {
            if(responseModelReciverComplete.getStock_category_name().toLowerCase(Locale.ROOT).contains(newText.toLowerCase(Locale.ROOT))){
                responseModelRequisitionLists.add(responseModelReciverComplete);
            }
        }

        if (responseModelRequisitionLists.isEmpty()){
            Toast.makeText(this, "No Data found", Toast.LENGTH_SHORT).show();
        }
        else
        {
            requisitionCompleteAdapterClass.setFilteredList(responseModelRequisitionLists);
        }
    }

    private void fatchdata(int value) {

        appConfig = new AppConfig(this);
        String spi = appConfig.getRequisition();
        String spname = appConfig.getManagerName();

        Log.d("ssssss",spi);
        Log.d("ssssss",spname);


        progressDialog = new ProgressDialog(RequsitionComplete.this);
        progressDialog.setMessage("Loading... Please Wait!");
        progressDialog.show();

        String token = appConfig.getuser_token();
        String userId = appConfig.getuser_id();
        String location = appConfig.getLocationId();
        String user_employee_type = appConfig.getuser_employee_type();

//        Toast.makeText(this, token+"/"+userId+"/"+location+"/"+user_employee_type, Toast.LENGTH_SHORT).show();
        store = store + value;
        data.clear();
        // String last_record_count =

//        Toast.makeText(this, String.valueOf(store), Toast.LENGTH_SHORT).show();
        //progressDialog.dismiss();



            StringRequest request = new StringRequest(Request.Method.POST, "https://erp.philsengg.com/api/requisition/requisition_completed",
                    new com.android.volley.Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                int j = 0;

                                JSONObject jsonObject = new JSONObject(response);
                                String message = jsonObject.getString("message");

                               // Toast.makeText(RequsitionComplete.this, message, Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();

                                if (message.equals("Invalid user request")) {
                                    Toast.makeText(RequsitionComplete.this, message, Toast.LENGTH_SHORT).show();
                                    appConfig.updateUserLoginStatus(false);
                                    startActivity(new Intent(RequsitionComplete.this, LoginActivity.class));
                                    finish();
                                } else {

                                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        j++;
                                        JSONObject object = jsonArray.getJSONObject(i);
                                        String sn = String.valueOf(j);

                                        String req_id = object.getString("req_id");
                                        String req_user_id = object.getString("req_user_id");
                                        String req_by_user_id = object.getString("req_by_user_id");

                                        String req_job_id = object.getString("req_job_id");
                                        String seam_number = object.getString("seam_number");
                                        String req_category_id = object.getString("req_category_id");

                                        String req_type_id = object.getString("req_type_id");
                                        String req_size_id = object.getString("req_size_id");
                                        String req_quantity = object.getString("req_quantity");
                                        String req_remark = object.getString("req_remark");


                                        String req_location_id = object.getString("req_location_id");
                                        String req_manager_id = object.getString("req_manager_id");

                                        if (req_manager_id.equals("null")) {
                                            req_manager_id = "Default";
                                        }
                                        String req_manager_comment = object.getString("req_manager_comment");
                                        if (req_manager_comment.equals("null")) {
                                            req_manager_comment = "Default ";
                                        }
                                        else
                                        {
                                            int value = Integer.parseInt(req_manager_id);
                                            String[] arrayid = spi.split(",");
                                            String[] arrayname = spname.split(",");

                                            String printname ="";
                                            for(int k=0;k<arrayid.length;k++){
                                                // Log.d("apppp",arrayname[k]+"/"+arrayid[k]);
                                                String ok = arrayid[k].trim();
                                                int che = 0;
                                                //    che = Integer.parseInt(ok);
                                                if(value==Integer.parseInt(ok)){

                                                    printname = arrayname[k];
                                                    String printid = arrayid[k];

                                                    Log.d("apppp",printname +"/"+printid);
                                                }
                                            }
                                            req_manager_id = printname;
                                        }

                                        String req_manager_status = object.getString("req_manager_status");
                                        if (req_manager_status.equals(String.valueOf(0))) {
                                            req_manager_status = "Requested";
                                        } else if (req_manager_status.equals(String.valueOf(1))) {
                                            req_manager_status = "Accepted";
                                        } else {
                                            req_manager_status = "Declined";
                                        }
                                        String req_status = object.getString("req_status");
                                        String req_updated_on = object.getString("req_updated_on");
                                        String req_created_on = object.getString("req_created_on");

                                        String stock_type_name = object.getString("stock_type_name");
                                        String stock_size_name = object.getString("stock_size_name");
                                        if (stock_size_name.equals("null"))
                                            stock_size_name = " ";
                                        String stock_category_name = object.getString("stock_category_name");
                                        String job_number = object.getString("job_number");

                                        String user_full_name = object.getString("user_full_name");
                                        String user_employee_id = object.getString("user_employee_id");
                                        String assign_quantity = object.getString("assign_quantity");
                                        if (assign_quantity.equals("null")) {
                                            assign_quantity = "0";
                                        }
                                        String req_user_id_details = object.getString("req_user_id_details");

                                        wokerList.clear();
                                        String[] strSplit = req_user_id.split(",");
                                        for (String name : strSplit) {
                                            JSONObject jsonObject1 = new JSONObject(req_user_id_details);
                                            String idnumber = jsonObject1.getString(name);
                                            // Log.d("please",idnumber);
                                            wokerList.add(idnumber);
                                        }
                                        String stringbuilder = String.join(",", wokerList);


                                        // Toast.makeText(RequsitionComplete.this, assign_quantity, Toast.LENGTH_SHORT).show();
                                        // Toast.makeText(RequisitionReciverList.this, job_number, Toast.LENGTH_SHORT).show();
                                        responseModelRequisitionComplete = new ResponseModelRequisitionComplete(sn, req_id, req_user_id, req_by_user_id, req_job_id,
                                                seam_number, req_category_id,
                                                req_type_id, req_size_id, req_quantity, req_remark, req_location_id, req_manager_id,
                                                req_manager_comment, req_manager_status, req_status, req_updated_on,
                                                req_created_on, stock_type_name, stock_size_name, stock_category_name, job_number, user_full_name,
                                                user_employee_id, assign_quantity, stringbuilder);

                                        data.add(responseModelRequisitionComplete);
                                        requisitionCompleteAdapterClass.notifyDataSetChanged();
                                        progressDialog.dismiss();

                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(RequsitionComplete.this, "Error", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(RequsitionComplete.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(RequsitionComplete.this, " Server Error", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap headers = new HashMap();
                    headers.put("Usertoken", token);
                    headers.put("Userid", userId);
                    headers.put("Projectlocationid", location);
                    headers.put("Useremployeetype", user_employee_type);

                    return headers;
                    //return super.getHeaders();
                }

                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("last_record_count", String.valueOf(store));
                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(RequsitionComplete.this);
            requestQueue.add(request);


    }
}