package com.example.phils.Admin;

import androidx.annotation.NonNull;
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
import com.example.phils.Adapter.RequisitionReciverAdapterClass;
import com.example.phils.R;
import com.example.phils.ResponseModels.ResponseModelRequisitionReiverOnGoing;
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

public class RequisitionReciverList extends AppCompatActivity {

    ArrayList<String> wokerList = new ArrayList<>();

    Button add_reqlist;
    AppConfig appConfig;
    SearchView searchView;
    ProgressDialog progressDialog;
    TextView location_save,checklist;
    LinearLayoutManager linearLayoutManager;
    RecyclerView recview;
    Button other_reqlist,ongoing,open,completed;

    List<ResponseModelRequisitionReiverOnGoing> data = new ArrayList<>();
    ResponseModelRequisitionReiverOnGoing responseModelRequisitionReiverOnGoing;
    RequisitionReciverAdapterClass requisitionReciverAdapterClass;
    private RequisitionReciverAdapterClass.RecycleViewClickListener listener;

    Button logout,location;
    Button btnnotification;
    ImageView img,profile;
    TextView locationtext;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requisition_reciver_list);

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
                dialog=new Dialog(RequisitionReciverList.this);

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
                        startActivity(new Intent(RequisitionReciverList.this,LoginActivity.class));
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

        completed = findViewById(R.id.completed);
        completed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RequisitionReciverComplete.class));
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

        fatchdata();
        recycleClickLister();

        recview = findViewById(R.id.recview);
        linearLayoutManager = new LinearLayoutManager(this);
        recview.setLayoutManager(linearLayoutManager);
        data = new ArrayList<>();
        requisitionReciverAdapterClass = new RequisitionReciverAdapterClass(listener,data);
        recview.setAdapter(requisitionReciverAdapterClass);


    }
    private void fileList(String newText) {

        List<ResponseModelRequisitionReiverOnGoing> responseModelRequisitionLists = new ArrayList<>();
        for(ResponseModelRequisitionReiverOnGoing responseModelStockCategory : data)
        {
            if(responseModelStockCategory.getStock_category_name().toLowerCase(Locale.ROOT).contains(newText.toLowerCase(Locale.ROOT))){
                responseModelRequisitionLists.add(responseModelStockCategory);
            }
        }

        if (responseModelRequisitionLists.isEmpty()){
            Toast.makeText(this, "No Data found", Toast.LENGTH_SHORT).show();
        }
        else
        {
            requisitionReciverAdapterClass.setFilteredList(responseModelRequisitionLists);
        }
    }

    private void recycleClickLister(){
        listener = new RequisitionReciverAdapterClass.RecycleViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                String id = data.get(position).getAssign_id();
                String CATEGORY = data.get(position).getStock_category_name();
                String TYPE = data.get(position).getStock_type_name();
                String SIZE = data.get(position).getStock_size_name();
                String QUANTITY = data.get(position).getAssign_quantity();

                Intent intent = new Intent(getApplicationContext(), ReturnStock.class);
                intent.putExtra("id",id);
                intent.putExtra("CATEGORY",CATEGORY);
                intent.putExtra("TYPE",TYPE);
                intent.putExtra("SIZE",SIZE);
                intent.putExtra("QUANTITY",QUANTITY);
                startActivity(intent);



//                Toast.makeText(RequisitionReciverList.this, id, Toast.LENGTH_SHORT).show();
            }
        };
    }

    private void fatchdata() {
        progressDialog = new ProgressDialog(RequisitionReciverList.this);
        progressDialog.setMessage("Loading... Please Wait!");
        progressDialog.show();

        String token = appConfig.getuser_token();
        String userId = appConfig.getuser_id();
        String location = appConfig.getLocationId();
        String user_employee_type = appConfig.getuser_employee_type();

        //Toast.makeText(this, token+"/"+userId, Toast.LENGTH_SHORT).show();

        StringRequest request = new StringRequest(Request.Method.POST, "https://mployis.com/staging/api/requisition/receiver_on_going",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            int j=0;

                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("message");
                            Toast.makeText(RequisitionReciverList.this, message, Toast.LENGTH_SHORT).show();
                            String data1 = jsonObject.getString("data");
                            if(data1.equals("false")) {
                                Toast.makeText(RequisitionReciverList.this, "No Data Available", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }else {
                                if (message.equals("Invalid user request")) {
                                    Toast.makeText(RequisitionReciverList.this, message, Toast.LENGTH_SHORT).show();
                                    appConfig.updateUserLoginStatus(false);
                                    startActivity(new Intent(RequisitionReciverList.this, LoginActivity.class));
                                    finish();
                                } else {

                                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        j++;
                                        JSONObject object = jsonArray.getJSONObject(i);
                                        String sn = String.valueOf(j);

                                        String user_full_name = object.getString("user_full_name");
                                        String user_employee_id = object.getString("user_employee_id");
                                        String stock_type_name = object.getString("stock_type_name");

                                        String stock_size_name = object.getString("stock_size_name");
                                        String stock_category_name = object.getString("stock_category_name");
                                        String stock_emp_category = object.getString("stock_emp_category");
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
                                        String req_manager_comment = object.getString("req_manager_comment");
                                        String req_manager_status = object.getString("req_manager_status");

                                        String req_status = object.getString("req_status");
                                        String req_updated_on = object.getString("req_updated_on");
                                        String req_created_on = object.getString("req_created_on");
                                        String assign_id = object.getString("assign_id");

                                        String assign_req_id = object.getString("assign_req_id");
                                        String assign_location_id = object.getString("assign_location_id");
                                        String assign_stock_id = object.getString("assign_stock_id");
                                        String assign_quantity = object.getString("assign_quantity");

                                        String receiver_id = object.getString("receiver_id");
                                        String assign_manager_id = object.getString("assign_manager_id");
                                        String assign_manager_comment = object.getString("assign_manager_comment");
                                        String assign_manager_status = object.getString("assign_manager_status");

                                        String assign_updated_on = object.getString("assign_updated_on");
                                        String assign_created_on = object.getString("assign_created_on");
                                        String job_number = object.getString("job_number");

                                        // Toast.makeText(RequisitionReciverList.this, job_number, Toast.LENGTH_SHORT).show();
                                        responseModelRequisitionReiverOnGoing = new ResponseModelRequisitionReiverOnGoing(sn, user_full_name, user_employee_id, stock_type_name, stock_size_name,
                                                stock_category_name, stock_emp_category, req_id, req_user_id, req_by_user_id, req_job_id, seam_number, req_category_id,
                                                req_type_id, req_size_id, req_quantity, req_remark, req_location_id, req_manager_id,
                                                req_manager_comment, req_manager_status, req_status, req_updated_on,
                                                req_created_on, assign_id, assign_req_id, assign_location_id, assign_stock_id, assign_quantity, receiver_id,
                                                assign_manager_id, assign_manager_comment, assign_manager_status, assign_updated_on, assign_created_on, job_number);


                                        data.add(responseModelRequisitionReiverOnGoing);
                                        requisitionReciverAdapterClass.notifyDataSetChanged();

                                        progressDialog.dismiss();

                                    }
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
                Toast.makeText(RequisitionReciverList.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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

        RequestQueue requestQueue = Volley.newRequestQueue(RequisitionReciverList.this);
        requestQueue.add(request);

    }

}