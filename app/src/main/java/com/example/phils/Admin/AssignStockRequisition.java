package com.example.phils.Admin;

import androidx.annotation.Nullable;
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
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
import com.example.phils.Adapter.AssignRequisitionAdapterClass;
import com.example.phils.Spinner.GrinderWelderListSpinner;
import com.example.phils.R;
import com.example.phils.ResponseModels.ResponseModelAssignRequisition;
import com.example.phils.Shareprefered.AppConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AssignStockRequisition extends AppCompatActivity {

    TextView category,type,size,quantity,reqQuantity,changestats;

    TextView setreq_id,setreciverid,assignrecivedby,assignquantity,setmaxlot;
    AppConfig appConfig;

    ProgressDialog progressDialog;
    RecyclerView recview;
    SearchView searchView;
    LinearLayoutManager linearLayoutManager;
    List<ResponseModelAssignRequisition> data;
    ResponseModelAssignRequisition responseModelAssignRequisition;
    AssignRequisitionAdapterClass assignRequisitionAdapterClass;
    private AssignRequisitionAdapterClass.RecycleViewClickListener listener;
    private AssignRequisitionAdapterClass.ItemClickListener  itemClickListener;

    ArrayList<GrinderWelderListSpinner> grinderWelderListSpinners = new ArrayList<>();
    ArrayAdapter<GrinderWelderListSpinner> grinderWelderListSpinnerArrayAdapter;

    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_stock_requisition);
        String id = getIntent().getStringExtra("id");
        String changecategory = getIntent().getStringExtra("stockcategory");
        String changetype = getIntent().getStringExtra("stocktype");
        String changesize = getIntent().getStringExtra("stocksize");
        String changequantity = getIntent().getStringExtra("quantityAssign");
        String changereqquantity = getIntent().getStringExtra("quantityreq");
        String changesatusprint = getIntent().getStringExtra("managerstatus");

        appConfig = new AppConfig(this);

        category = findViewById(R.id.category);
        type = findViewById(R.id.type);
        size = findViewById(R.id.size);
        quantity = findViewById(R.id.quantity);
        reqQuantity = findViewById(R.id.reqQuantity);
        changestats = findViewById(R.id.changestats);

        btn = findViewById( R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressDialog.setMessage("Please Wait");
                progressDialog.show();

               String s =  setreq_id.getText().toString();
               String p = setreciverid.getText().toString();
               String ass = assignquantity.getText().toString();
               String max = setmaxlot.getText().toString();
               int Lot = Integer.parseInt(max) - Integer.parseInt(changequantity);
                //Toast.makeText(AssignStockRequisition.this, String.valueOf(Lot), Toast.LENGTH_SHORT).show();

                if (TextUtils.isEmpty(s)) {
                    setreq_id.setError("Please Select Available Stock");
                    Toast.makeText(AssignStockRequisition.this, "Please Select Available Stock", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(p)) {
                    setreciverid.setError("Please Select User");
                    Toast.makeText(AssignStockRequisition.this, "Please Select User", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(TextUtils.isEmpty(ass)){
                    setreciverid.setError("Please Enter Quantity");
                    Toast.makeText(AssignStockRequisition.this, "Please Enter Quantity", Toast.LENGTH_SHORT).show();

                }
                    else if(Integer.parseInt(max) >=Integer.parseInt(ass) )
                {
                    String token = appConfig.getuser_token();
                    String userId = appConfig.getuser_id();
                    String location = appConfig.getLocationId();
                    String user_employee_type = appConfig.getuser_employee_type();

                    //Toast.makeText(AssignStockRequisition.this, s+"/"+id+"/"+p+"/"+ass, Toast.LENGTH_SHORT).show();
                    StringRequest request = new StringRequest(Request.Method.POST, "https://mployis.com/staging/api/requisition/requistion_assign_stock",
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    JSONObject jsonObject = null;
                                    try {
                                        jsonObject = new JSONObject(response);
                                        String message = jsonObject.getString("message");

                                        Toast.makeText(AssignStockRequisition.this, message, Toast.LENGTH_SHORT).show();

                                        startActivity(new Intent(getApplicationContext(), RequisitionListActivity.class));
                                        progressDialog.dismiss();

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    //Toast.makeText(Add_Stock_Category_Activity.this, response, Toast.LENGTH_SHORT).show();
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(AssignStockRequisition.this, error.getMessage(), Toast.LENGTH_SHORT).show();

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
                            params.put("req_id", id);
                            params.put("assign_stock_id", s);
                            params.put("req_quantity", ass);
                            params.put("receiver_id", p);

                            return params;
                        }


                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(AssignStockRequisition.this);
                    requestQueue.add(request);
                }
                    else
                {
                    Toast.makeText(AssignStockRequisition.this,"The quantity field must contain a number less than or equal to"+ max, Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }

            }
        });
        setreq_id = findViewById(R.id.setreq_id);
        setreciverid = findViewById(R.id.setreciverid);
        setmaxlot = findViewById(R.id.setmaxlot);

        category.setText(changecategory);
        type.setText(changetype);
        size.setText(changesize);
        quantity.setText(changequantity);
        reqQuantity.setText(changereqquantity);

        recview = findViewById(R.id.recview);

        assignrecivedby = findViewById(R.id.assignrecivedby);
        assignquantity = findViewById(R.id.assignquantity);

//        searchView = findViewById(R.id.search);
//        searchView.clearFocus();
//
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//               // fileList(newText);
//                return true;
//            }
//        });

        fatchdata();
        recycleClickLister();
        linearLayoutManager = new LinearLayoutManager(this);
        data = new ArrayList<>();

        itemClickListener = new AssignRequisitionAdapterClass.ItemClickListener() {
            @Override
            public void onClick(String s) {
                recview.post(new Runnable() {
                    @Override
                    public void run() {
                        assignRequisitionAdapterClass.notifyDataSetChanged();;
                    }
                });
//                Toast
//                        .makeText(getApplicationContext(),
//                                "Selected : " + s,
//                                Toast.LENGTH_SHORT)
//                        .show();
            }
        };
        recview.setLayoutManager(linearLayoutManager);

        assignRequisitionAdapterClass = new AssignRequisitionAdapterClass(listener,itemClickListener,data);
        recview.setAdapter(assignRequisitionAdapterClass);
    }

    private void recycleClickLister() {
        setreq_id.setText("");
        listener  = new AssignRequisitionAdapterClass.RecycleViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                String  s = data.get(position).getStock_id();
                String Max  = data.get(position).getStock_lot();
                setmaxlot.setText(Max);
                setreq_id.setText(s);

                //Toast.makeText(AssignStockRequisition.this, s+Max, Toast.LENGTH_SHORT).show();

            }
        };
    }

    private void fatchdata() {
        progressDialog = new ProgressDialog(AssignStockRequisition.this);
        progressDialog.setMessage("Loading... Please Wait!");
        progressDialog.show();

        String token = appConfig.getuser_token();
        String userId = appConfig.getuser_id();
        String location = appConfig.getLocationId();
        String useremptype = appConfig.getuser_employee_type();

        String req_id = getIntent().getStringExtra("id");
        //Toast.makeText(this, token+"/"+userId+"/"+location+"/"+useremptype+"/"+req_id, Toast.LENGTH_SHORT).show();

        StringRequest request = new StringRequest(Request.Method.POST, "https://mployis.com/staging/api/requisition/requistion_avaiable_stock",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            int j=0;
                            String stock_category_status;
                            String emp_type_name;

                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("message");

                            if(message.equals("Invalid user request")){
                                Toast.makeText(AssignStockRequisition.this, message, Toast.LENGTH_SHORT).show();
                                appConfig.updateUserLoginStatus(false);
                                startActivity(new Intent(AssignStockRequisition.this, LoginActivity.class));
                                finish();
                            }
                            else {

                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    j++;
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String sn = String.valueOf(j);
                                    String stock_id = object.getString("stock_id");
                                    String stock_location_id = object.getString("stock_location_id");
                                    String stock_category_id = object.getString("stock_category_id");
                                    String stock_type_id = object.getString("stock_type_id");
                                    String stock_size_id = object.getString("stock_size_id");

                                    String stock_batch_number = object.getString("stock_batch_number");
                                    String stock_invoice_number = object.getString("stock_invoice_number");
                                    String stock_distributor_name = object.getString("stock_distributor_name");
                                    String stock_make_id = object.getString("stock_make_id");
                                    String stock_uom_id = object.getString("stock_uom_id");

                                    String safety_stock = object.getString("safety_stock");
                                    String stock_quantity = object.getString("stock_quantity");
                                    String stock_lot = object.getString("stock_lot");
                                    String stock_price = object.getString("stock_price");
                                    String is_stock_transfer = object.getString("is_stock_transfer");

                                    String stock_status = object.getString("stock_status");
                                    String stock_type_name = object.getString("stock_type_name");
                                    String stock_size_name = object.getString("stock_size_name");
                                    String make_name = object.getString("make_name");
                                    String uom_name = object.getString("uom_name");

                                    String stock_category_name = object.getString("stock_category_name");
                                    String assign_quantity = object.getString("assign_quantity");

                                    if(stock_quantity.equals("0")) {
                                    }else{
                                        int sum=0;
                                        if(assign_quantity.equals("null")) {
                                            int k = 0;
                                            int p = 0;
                                             sum = Integer.valueOf(stock_quantity) - 0;
                                        }
                                        else
                                        {
                                            sum = Integer.valueOf(stock_quantity) - Integer.valueOf(assign_quantity);
                                        }
//
                                        int q = sum;
//
//                                        Toast.makeText(AssignStockRequisition.this, String.valueOf(p), Toast.LENGTH_SHORT).show();
                                        String AvailableStock = String.valueOf(q);
//
                                        responseModelAssignRequisition = new ResponseModelAssignRequisition(AvailableStock,stock_id, stock_location_id, stock_category_id, stock_type_id, stock_size_id,
                                                stock_batch_number, stock_invoice_number, stock_distributor_name, stock_make_id, stock_uom_id, safety_stock,
                                                stock_quantity, stock_lot, stock_price, is_stock_transfer, stock_status, stock_type_name, stock_size_name, make_name,
                                                uom_name, stock_category_name, assign_quantity);
                                        data.add(responseModelAssignRequisition);
                                        assignRequisitionAdapterClass.notifyDataSetChanged();
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
                Toast.makeText(AssignStockRequisition.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap headers = new HashMap();
                headers.put("user_token",token);
                headers.put("user_id", userId);
                headers.put("project_location_id", location);
                headers.put("user_employee_type", useremptype);
                return headers;
                //return super.getHeaders();
            }

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                params.put("req_id", req_id);
                return  params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(AssignStockRequisition.this);
        requestQueue.add(request);


        StringRequest request12 = new StringRequest(Request.Method.POST, "https://mployis.com/staging/api/job/job_welder_grinder_list",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            //String message = jsonObject.getString("message");

                            JSONArray jsonArray = jsonObject.getJSONArray("data");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                String user_id = object.getString("user_id");
                                String user_full_name = object.getString("user_full_name");
                                String user_employee_id = object.getString("user_employee_id");

                                String name = user_full_name +" - "+user_employee_id;

                                grinderWelderListSpinners.add(new GrinderWelderListSpinner(user_id,name));
                                grinderWelderListSpinnerArrayAdapter = new ArrayAdapter<GrinderWelderListSpinner>(AssignStockRequisition.this,
                                        android.R.layout.simple_spinner_dropdown_item,grinderWelderListSpinners);
                                grinderWelderListSpinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                            }
                            }
                        catch(JSONException e){
                                e.printStackTrace();
                            }
                        }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AssignStockRequisition.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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

        RequestQueue requestQueue12 = Volley.newRequestQueue(AssignStockRequisition.this);
        requestQueue12.add(request12);

        assignrecivedby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Initialize dialog
             Dialog   dialog = new Dialog(AssignStockRequisition.this);

                // set custom dialog
                dialog.setContentView(R.layout.dialog_searchable_spinner_stock_type);

                // set custom height and width
                dialog.getWindow().setLayout(950, 1100);

                // set transparent background
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                // show dialog
                dialog.show();

                // Initialize and assign variable
                EditText editText = dialog.findViewById(R.id.edit_text);
                ListView listView = dialog.findViewById(R.id.list_view);

                listView.setAdapter(grinderWelderListSpinnerArrayAdapter);
                editText.addTextChangedListener(new TextWatcher() {
                                                    @Override
                                                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                                    }

                                                    @Override
                                                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                                        grinderWelderListSpinnerArrayAdapter.getFilter().filter(charSequence);
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

                        GrinderWelderListSpinner sp =(GrinderWelderListSpinner)parent.getItemAtPosition(position);
                        assignrecivedby.setText(sp.user_full_name);
                        setreciverid.setText(sp.user_id);
                        dialog.dismiss();
                    }
                });
            }
        });

    }
}