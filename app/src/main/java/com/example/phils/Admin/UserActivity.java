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
import com.example.phils.Adapter.UserAdapterClass;
import com.example.phils.R;
import com.example.phils.ResponseModels.ResponseModelUser;
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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

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


        recview = findViewById(R.id.recview);
        searchView = findViewById(R.id.search);
        searchView.clearFocus();
        button = findViewById(R.id.adduser);
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
        progressDialog.setIcon(R.drawable.ic_baseline_autorenew_24);
        progressDialog.show();

        String token = appConfig.getuser_token();
        String userId = appConfig.getuser_id();
        String location = appConfig.getLocationId();

        StringRequest request = new StringRequest(Request.Method.POST, "https://mployis.com/staging/api/user/user_list",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            int j=0;

                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("message");

                            //Toast.makeText(UserActivity.this, message, Toast.LENGTH_SHORT).show();
                            if(message.equals("Invalid user request")){
                                Toast.makeText(UserActivity.this, message, Toast.LENGTH_SHORT).show();
                                appConfig.updateUserLoginStatus(false);
                                startActivity(new Intent(UserActivity.this, LoginActivity.class));
                                finish();
                            }
                            else {

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
                Toast.makeText(UserActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap headers = new HashMap();
                headers.put("user_token", token);
                headers.put("user_id", userId);
                headers.put("project_location_id", location);

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
                //Toast.makeText(UserActivity.this, kk, Toast.LENGTH_SHORT).show();
            }
        };
    }
}