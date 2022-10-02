package com.example.phils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.phils.Adapter.StockCategoryAdapterClass;
import com.example.phils.Admin.Assign_user_Job_Activity;
import com.example.phils.Admin.LoginActivity;
import com.example.phils.Admin.MainActivity;
import com.example.phils.Admin.Notification_Activity;
import com.example.phils.Admin.StockCategoryActivity;
import com.example.phils.Admin.Update_StockCategory_Activity;
import com.example.phils.ResponseModels.ResponseModelStockCategory;
import com.example.phils.Shareprefered.AppConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class RequisitionListActivity extends AppCompatActivity {

    ArrayList<String> wokerList = new ArrayList<>();

    Button add_reqlist;
    AppConfig appConfig;
    SearchView searchView;
    ProgressDialog progressDialog;
    TextView location_save,checklist;
    LinearLayoutManager linearLayoutManager;
    RecyclerView recview;
    Button other_reqlist;


    List<ResponseModelRequisitionList> data;
    RequisitionAdapterClass requisitionAdapterClass;
    ResponseModelRequisitionList responseModelRequisitionList;

    private RequisitionAdapterClass.RecycleViewClickListener listener;

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requisition_list);
        appConfig = new AppConfig(this);

        location_save = findViewById(R.id.location_save);
        recview = findViewById(R.id.recview);

        checklist = findViewById(R.id.checklist);

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

                Intent intent = new Intent(getApplicationContext(),OtherRequisitionAddActivity.class);
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

        linearLayoutManager = new LinearLayoutManager(this);
        recview.setLayoutManager(linearLayoutManager);
        data = new ArrayList<>();
        requisitionAdapterClass = new RequisitionAdapterClass(listener,data );
        recview.setAdapter(requisitionAdapterClass);

    }

    private void fileList(String newText) {

        List<ResponseModelRequisitionList> responseModelRequisitionLists = new ArrayList<>();
        for(ResponseModelRequisitionList responseModelStockCategory : data)
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
            requisitionAdapterClass.setFilteredList(responseModelRequisitionLists);
        }
    }

    private void recycleClickLister(){
        listener = new RequisitionAdapterClass.RecycleViewClickListener() {
            @Override
            public void onClick(View v, int position) {

              Dialog  dialog=new Dialog(RequisitionListActivity.this);

                // set custom dialog
                dialog.setContentView(R.layout.custom_requisition_list_button);

                // set custom height and width
                dialog.getWindow().setLayout(650,750);

                // set transparent background
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                // show dialog
                dialog.show();
                // Toast.makeText(Notification_Activity.this, "dfn", Toast.LENGTH_SHORT).show();

                Button changestatus = dialog.findViewById(R.id.changestatus);
                Button assignstock = dialog.findViewById(R.id.assignstock);
                Button delete = dialog.findViewById(R.id.delete);

                String id = data.get(position).getReq_id();
                String catGroup = data.get(position).getStock_category_name();
                String category = data.get(position).getStock_type_name();
                String status = data.get(position).getStock_size_name();
                String changestatu = data.get(position).getReq_manager_status();
                String stockcategory = data.get(position).getStock_category_name();
                String stocktype = data.get(position).getStock_type_name();
                String stocksize = data.get(position).getStock_size_name();
                String quantityAssign = data.get(position).getAssign_quantity();
                String quantityreq = data.get(position).getReq_quantity();
                String managerstatus = data.get(position).getReq_manager_status();

                String token = appConfig.getuser_token();
                String userId = appConfig.getuser_id();
                String location = appConfig.getLocationId();
//
                // Toast.makeText(RequisitionListActivity.this, changestatu, Toast.LENGTH_SHORT).show();
                if(changestatu.equals("Declined"))
                {
                    assignstock.setVisibility(View.GONE);
                }
                changestatus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(getApplicationContext(),RequisitionChangeStatusActivity.class);

                        intent.putExtra("stockcategory",stockcategory);
                        intent.putExtra("stocktype",stocktype);
                        intent.putExtra("stocksize",stocksize);
                        intent.putExtra("quantityAssign",quantityAssign);
                        intent.putExtra("quantityreq",quantityreq);
                        intent.putExtra("managerstatus",managerstatus);

                        intent.putExtra("id",id);
                        intent.putExtra("token",token);
                        intent.putExtra("userId",userId);
                        intent.putExtra("location",location);
                        startActivity(intent);

                    }
                });
//
//                String token = appConfig.getuser_token();
//                String userId = appConfig.getuser_id();
//                String location = appConfig.getLocationId();
//
//                Intent intent = new Intent(getApplicationContext(), Update_StockCategory_Activity.class);
//                intent.putExtra("id",id);
//                intent.putExtra("token",token);
//                intent.putExtra("userId",userId);
//                intent.putExtra("location",location);
            }
        };
    }


    private void fatchdata() {
        progressDialog = new ProgressDialog(RequisitionListActivity.this);
        progressDialog.setMessage("Loading... Please Wait!");
        progressDialog.show();

        String token = appConfig.getuser_token();
        String userId = appConfig.getuser_id();
        String location = appConfig.getLocationId();
        String user_employee_type = appConfig.getuser_employee_type();

        //Toast.makeText(this, token+"/"+userId, Toast.LENGTH_SHORT).show();

        StringRequest request = new StringRequest(Request.Method.POST, "https://mployis.com/staging/api/requisition/requisition_open",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            int j=0;
                            String req_manager_status;
                            String req_status;
                            String assign_quantity;
                            String req_manager_id;
                            String stringBuilder;
                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("message");

                            if(message.equals("Invalid user request")){
                                Toast.makeText(RequisitionListActivity.this, message, Toast.LENGTH_SHORT).show();
                                appConfig.updateUserLoginStatus(false);
                                startActivity(new Intent(RequisitionListActivity.this, LoginActivity.class));
                                finish();
                            }
                            else {

                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    j++;
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String sn = String.valueOf(j);

                                    String req_id = object.getString("req_id");
                                    String req_user_id = object.getString("req_user_id");
                                    String req_user_id_details = object.getString("req_user_id_details");

                                    wokerList.clear();
                                    String[] strSplit = req_user_id.split(",");
                                    for(String name : strSplit){
                                            JSONObject jsonObject1 = new JSONObject(req_user_id_details);
                                            String idnumber =  jsonObject1.getString(name);
                                           // Log.d("please",idnumber);
                                            wokerList.add(idnumber);
                                        }
                                    String stringbuilder = String.join(",",wokerList);

                                    String req_by_user_id =object.getString("req_by_user_id");
                                    String req_job_id = object.getString("req_job_id");
                                    String seam_number = object.getString("seam_number");
                                    String req_category_id = object.getString("req_category_id");
                                    String req_type_id = object.getString("req_type_id");
                                    String req_size_id = object.getString("req_size_id");
                                    String req_quantity = object.getString("req_quantity");
                                    String req_remark = object.getString("req_remark");
                                    String req_location_id = object.getString("req_location_id");
                                    req_manager_id = object.getString("req_manager_id");




                                    if(req_manager_id.equals("null"))
                                    {
                                        req_manager_id = "Default";
                                    }

                                    String req_manager_comment = object.getString("req_manager_comment");
                                     req_manager_status = object.getString("req_manager_status");

                                    if (req_manager_status.equals(String.valueOf(0))) {
                                        req_manager_status = "Initial";
                                    }
                                    else if(req_manager_status.equals(String.valueOf(1))){
                                        req_manager_status = "Accepted";
                                    }
                                    else {
                                        req_manager_status = "Declined";
                                    }

                                     req_status = object.getString("req_status");

                                    if(req_status.equals(String.valueOf(0))){
                                        req_status = "In Progress";
                                    }
                                    else {
                                        req_status = "Completed";
                                    }
                                    String req_updated_on = object.getString("req_updated_on");
                                    String req_created_on = object.getString("req_created_on");
                                    String stock_type_name = object.getString("stock_type_name");
                                    String stock_size_name = object.getString("stock_size_name");
                                    if(stock_size_name.equals("null"))
                                    {
                                        stock_size_name = " ";
                                    }
                                    String stock_category_name = object.getString("stock_category_name");
                                    String job_number = object.getString("job_number");
                                    String user_full_name = object.getString("user_full_name");
                                    String user_employee_id = object.getString("user_employee_id");
                                     assign_quantity = object.getString("assign_quantity");
                                     if(assign_quantity.equals("null"))
                                     {
                                         assign_quantity = "0";
                                     }

                                    //Toast.makeText(RequisitionListActivity.this, sn, Toast.LENGTH_SHORT).show();
                                    responseModelRequisitionList = new ResponseModelRequisitionList(sn,req_id,stringbuilder,req_by_user_id,req_job_id,
                                            seam_number,req_category_id,req_type_id,req_size_id,req_quantity,req_remark,req_location_id,req_manager_id,req_manager_comment,
                                            req_manager_status,req_status,req_updated_on,req_created_on,stock_type_name,stock_size_name,stock_category_name,
                                            job_number,user_full_name,user_employee_id,assign_quantity);
                                    data.add(responseModelRequisitionList);
                                    requisitionAdapterClass.notifyDataSetChanged();

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
                Toast.makeText(RequisitionListActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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

        RequestQueue requestQueue = Volley.newRequestQueue(RequisitionListActivity.this);
        requestQueue.add(request);

    }


}