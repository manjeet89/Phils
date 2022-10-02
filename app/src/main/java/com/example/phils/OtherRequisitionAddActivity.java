package com.example.phils;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.phils.Shareprefered.AppConfig;
import com.example.phils.Spinner.CategorySpinner;
import com.example.phils.Spinner.StockSizeSpinner;
import com.example.phils.Spinner.StockTypeSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class OtherRequisitionAddActivity extends AppCompatActivity {


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



    TextView setjobnumberid,setReqUserid,setcategoryid,setypeid,setsizeid;
    TextView job_nuber,seamnumber,req_user,categoryreq,typereq,sizereq;
    EditText quantityreq,remark;
    Button reqadd;

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

    String seamurlurl = "https://investment-wizards.com/manjeet/Phils_Stock/job_seam.php?job_id=";
    String jobnumberurl = "https://investment-wizards.com/manjeet/Phils_Stock/insert_category/fatch_spinner_stock/fatch_spinner_job_number.php";
    String req_userurl = "https://investment-wizards.com/manjeet/Phils_Stock/insert_category/fatch_spinner_stock/tbl_user_fatch_spinner.php";
    String categoryurl = "https://investment-wizards.com/manjeet/Phils_Stock/insert_category/fatch_spinner_stock/fatch_spinner_category_data_in_tbl_type.php";
    String typeurl = "https://investment-wizards.com/manjeet/Phils_Stock/insert_category/fatch_spinner_stock/fatch_spinner_category_and_type_data_in_tbl_size.php?stock_category_id=";
    String sizeurl = "https://investment-wizards.com/manjeet/Phils_Stock/insert_category/fatch_spinner_stock/fatch_spinner_size.php?stock_type_id=";

    TextView location_save;
    AppConfig appConfig;

    private static final String CHANNEL_ID = "My Channel";
    private static final int NOTIFICATION_ID = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_requisition_add);
        requestQueue = Volley.newRequestQueue(this);
        appConfig = new AppConfig(this);

        String token = getIntent().getStringExtra("token");
        String userId = getIntent().getStringExtra("userId");
        String location = getIntent().getStringExtra("location");

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
                AddRequisition();
            }
        });


        JobNumber();
        RequsistiionUser();
        // fatchCategoryId();


    }

    private void AddRequisition() {


        String jobnumber = setjobnumberid.getText().toString();
        String userreq   = setReqUserid.getText().toString();
        String category  = setcategoryid.getText().toString();
        String type      = setypeid.getText().toString();
        String size      = setsizeid.getText().toString();
        String seamList  = seamnumber.getText().toString();
        String quantity  = quantityreq.getText().toString();
        String remarks    = remark.getText().toString();

        appConfig = new AppConfig(this);
        // String req_by_user_id = appConfig.getIdOfUser();
        //String location_save1 = appConfig.getLocation();




        // Toast.makeText(this,jobnumber+"/"+ userreq +"/"+category +"/" + type  +"/"+ size +"/"+seamList+"/"+ quantity, Toast.LENGTH_SHORT).show();

        if(TextUtils.isEmpty(jobnumber))
        {
            Toast.makeText(OtherRequisitionAddActivity.this, "Please Select job Number ", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(TextUtils.isEmpty(userreq))
        {
            Toast.makeText(OtherRequisitionAddActivity.this, "Please Select User ", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(TextUtils.isEmpty(category))
        {
            Toast.makeText(OtherRequisitionAddActivity.this, "Please Select category", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(TextUtils.isEmpty(type))
        {
            Toast.makeText(OtherRequisitionAddActivity.this, "Please Select type", Toast.LENGTH_SHORT).show();
            return;
        }

        else if(TextUtils.isEmpty(seamList))
        {
            Toast.makeText(OtherRequisitionAddActivity.this, "Please Select Seam", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(TextUtils.isEmpty(quantity))
        {
            Toast.makeText(OtherRequisitionAddActivity.this, "Please Enter Quantity", Toast.LENGTH_SHORT).show();
            return;
        }

        else {

            String token = getIntent().getStringExtra("token");
            String userId = getIntent().getStringExtra("userId");
            String location = getIntent().getStringExtra("location");
            String user_employee_type = appConfig.getuser_employee_type();
//        Log.d("check",consumable);
//        Log.d("check",weldergrinder);

            StringRequest request = new StringRequest(Request.Method.POST, "https://mployis.com/staging/api/requisition/add_requisition",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(response);
                                String message = jsonObject.getString("message");

//                                if(message.equals("Job size created successfully")) {
//                                    Toast.makeText(OtherRequisitionAddActivity.this, message, Toast.LENGTH_SHORT).show();
//                                    startActivity(new Intent(getApplicationContext(), Job_List_Activity.class));
//                                }
//                                else
//                                {
                                Toast.makeText(OtherRequisitionAddActivity.this, message, Toast.LENGTH_SHORT).show();

                                // }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            //Toast.makeText(Add_Stock_Category_Activity.this, response, Toast.LENGTH_SHORT).show();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(OtherRequisitionAddActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

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
                    params.put("req_user_id[]", userreq);
                    params.put("req_category_id", category);
                    params.put("req_type_id", type);
                    params.put("req_size_id", size);
                    params.put("seam_number", seamList);
                    params.put("req_quantity", quantity);
                    params.put("req_remark", remarks);

                    return params;
                }

            };
            RequestQueue requestQueue = Volley.newRequestQueue(OtherRequisitionAddActivity.this);
            requestQueue.add(request);
//            StringRequest request = new StringRequest(Request.Method.POST, "https://investment-wizards.com/manjeet/Phils_Stock/insert_category/add_requisition_list.php",
//                    new Response.Listener<String>() {
//                        @Override
//                        public void onResponse(String response) {
//                          // Notification();
////                            Toast.makeText(OtherRequisitionAddActivity.this, response, Toast.LENGTH_SHORT).show();
//                        }
//                    }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    Toast.makeText(OtherRequisitionAddActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
//
//                }
//            }) {
//                @Nullable
//                @Override
//                protected Map<String, String> getParams() throws AuthFailureError {
//                    Map<String, String> params = new HashMap<String, String>();
//                    params.put("req_user_id", userreq);
//                   // params.put("req_by_user_id", req_by_user_id);
//                    params.put("req_job_id", jobnumber);
//                    params.put("seam_number", seamList);
//                    params.put("req_category_id", category);
//                    params.put("req_type_id", type);
//                    params.put("req_size_id", size);
//                    params.put("req_quantity", quantity);
//                    params.put("req_remark", remarks);
//                   // params.put("req_location_id", location_save1);
//
//                    return params;
//                }
//            };
//            //        Toast.makeText(this, category+"/"+type+"/"+size+"/"+batchno+"/"+invoiceinsert
////                +"/"+distributorinsert+"/"+makeinsert+"/"+uom+"/"+safetyinsert+"/"+quantityinsert
////                +"/"+max_all+"/"+priceinsert, Toast.LENGTH_SHORT).show();
//
//            RequestQueue requestQueue = Volley.newRequestQueue(OtherRequisitionAddActivity.this);
//            requestQueue.add(request);

        }
    }

    private void Notification() {

        String userreq   = setReqUserid.getText().toString();
        // String nameofuser = appConfig.getNameOfUser();
        String quantity  = quantityreq.getText().toString();
        String category  = categoryreq.getText().toString();
        String type      = typereq.getText().toString();
        String size      = sizereq.getText().toString();
        String req   = "requisition";
        // String notification = nameofuser+" has request "+quantity+" for "+category+" -> "+type+" -> "+size+" please approved the same";
        // Toast.makeText(this, notification, Toast.LENGTH_SHORT).show();
        StringRequest request = new StringRequest(Request.Method.POST, "https://investment-wizards.com/manjeet/Phils_Stock/insert_category/add_notification.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //SentNotification();
                        Toast.makeText(OtherRequisitionAddActivity.this, response, Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(OtherRequisitionAddActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("noti_user_id", userreq);
                params.put("req_by_user_id", req);
                //   params.put("noti_message", notification);


                return params;
            }
        };


        RequestQueue requestQueue = Volley.newRequestQueue(OtherRequisitionAddActivity.this);
        requestQueue.add(request);
    }

    private void SentNotification() {
//        String userreq   = setReqUserid.getText().toString();
//        String nameofuser = appConfig.getNameOfUser();
//        String iduser   = appConfig.getIdOfUser();
//        String quantity  = quantityreq.getText().toString();
//        String category  = categoryreq.getText().toString();
//        String type      = typereq.getText().toString();
//        String size      = sizereq.getText().toString();
//        String req   = "requisition";
//        String notification = nameofuser+" has request "+quantity+" for "+category+" -> "+type+" -> "+size+" please approved the same";
//
//
//
//        JsonObjectRequest jsonObjectRequest1 = new JsonObjectRequest(Request.Method.POST, "https://investment-wizards.com/manjeet/Phils_Stock/fatch_token.php?user_id="+userId, null,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        try {
//
//
//                            JSONArray jsonArray = response.getJSONArray("data");
//                            for(int i=0;i<jsonArray.length();i++){
//                                JSONObject jsonObject = jsonArray.getJSONObject(i);
//                                String user_token= jsonObject.optString("user_token");
//                                FcmNotificationsSender notificationsSender = new FcmNotificationsSender(user_token,
//                                        nameofuser,
//                                        notification.,getApplicationContext(),OtherRequisitionAddActivity.this);
//                                notificationsSender.SendNotifications();
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

//                Intent intent = new Intent(getApplicationContext(), Notification_Activity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                intent.putExtra("Message",notification);
//                PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),1,intent,PendingIntent.FLAG_UPDATE_CURRENT);
//
//                NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(),CHANNEL_ID);
//                builder.setSmallIcon(R.drawable.ic_baseline_message_24);
//                builder.setContentTitle("Notification");
//                builder.setContentText(notification);
//                builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
//                builder.setContentIntent(pendingIntent);
//                builder.setAutoCancel(true);
//
//                NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
//                notificationManagerCompat.notify(NOTIFICATION_ID,builder.build());



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
                                categoryAdapter = new ArrayAdapter<CategorySpinner>(OtherRequisitionAddActivity.this,
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
                Toast.makeText(OtherRequisitionAddActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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

        RequestQueue requestQueue1 = Volley.newRequestQueue(OtherRequisitionAddActivity.this);
        requestQueue1.add(request1);

        categoryreq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Initialize dialog
                dialog = new Dialog(OtherRequisitionAddActivity.this);

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
//                                            Toast.makeText(OtherRequisitionAddActivity.this, setcategoryid.getText().toString(), Toast.LENGTH_SHORT).show();
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
                                typeAdapter = new ArrayAdapter<StockTypeSpinner>(OtherRequisitionAddActivity.this,
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
                Toast.makeText(OtherRequisitionAddActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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

        RequestQueue requestQueue1 = Volley.newRequestQueue(OtherRequisitionAddActivity.this);
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
//                                typeAdapter = new ArrayAdapter<>(OtherRequisitionAddActivity.this,
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
                dialog = new Dialog(OtherRequisitionAddActivity.this);

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
//                                            Toast.makeText(OtherRequisitionAddActivity.this, setypeid.getText().toString(), Toast.LENGTH_SHORT).show();
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
                                sizeAdapter = new ArrayAdapter<StockSizeSpinner>(OtherRequisitionAddActivity.this,
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
                Toast.makeText(OtherRequisitionAddActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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

        RequestQueue requestQueue1 = Volley.newRequestQueue(OtherRequisitionAddActivity.this);
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
//                                sizeAdapter = new ArrayAdapter<>(OtherRequisitionAddActivity.this,
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
                dialog = new Dialog(OtherRequisitionAddActivity.this);

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

                        JsonObjectRequest jsonObjectRequest1 = new JsonObjectRequest(Request.Method.POST, sizeurl+idea
                                , null, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    String ss = sizereq.getText().toString();
                                    JSONArray jsonArray = response.getJSONArray("data");

                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                                        String stock_size_id= jsonObject.optString("stock_size_id");
                                        String stock_size_name= jsonObject.optString("stock_size_name");
                                        if(ss.equals(stock_size_name)){
                                            String idea = stock_size_id;
                                            setsizeid.setText(idea);
                                            //TypeIdPass(idea);
                                            Toast.makeText(OtherRequisitionAddActivity.this, setsizeid.getText().toString(), Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                            }

                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });

                        requestQueue.add(jsonObjectRequest1);
                    }
                });
            }
        });
    }


    private void RequsistiionUser() {

        String token = getIntent().getStringExtra("token");
        String userId = getIntent().getStringExtra("userId");
        String location = getIntent().getStringExtra("location");

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

                            for(int i=0; i<jsonArray.length();i++)
                            {
                                JSONObject object = jsonArray.getJSONObject(i);
                                String user_id = object.getString("user_id");
                                String user_name = object.getString("user_full_name");
                                String user_employee_id = object.getString("user_employee_id");

                                wokerValue[i]=user_name+" - "+user_employee_id;
                                wokerId[i]=user_id;

                            }

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
                Toast.makeText(OtherRequisitionAddActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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

        RequestQueue requestQueue4 = Volley.newRequestQueue(OtherRequisitionAddActivity.this);
        requestQueue4.add(request4);

        req_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Initialize alert dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(OtherRequisitionAddActivity.this);

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

//        JsonObjectRequest jsonObjectRequest1 = new JsonObjectRequest(Request.Method.GET, req_userurl, null,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        try {
//
//                            JSONArray jsonArray = response.getJSONArray("data");
//                            for(int i=0;i<jsonArray.length();i++){
//                                JSONObject jsonObject = jsonArray.getJSONObject(i);
//
//                                String user_id= jsonObject.optString("user_id");
//                                String user_full_name= jsonObject.optString("user_full_name");
//                                String user_employee_id= jsonObject.optString("user_employee_id");
//
//                                if(user_full_name.equals("Phils ERP"))
//                                {
//
//                                }
//                                else {
//
//                                    reqUserList.add(user_full_name+" - "+user_employee_id);
//                                    reqUserAdapter = new ArrayAdapter<>(OtherRequisitionAddActivity.this,
//                                            android.R.layout.simple_list_item_1, reqUserList);
//                                    reqUserAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                                }
//                            }
//
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
//
//        req_user.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // Initialize dialog
//                dialog = new Dialog(OtherRequisitionAddActivity.this);
//
//                // set custom dialog
//                dialog.setContentView(R.layout.dialog_searchable_spinner);
//
//                // set custom height and width
//                dialog.getWindow().setLayout(950, 1200);
//
//                // set transparent background
//                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//
//                // show dialog
//                dialog.show();
//
//                // Initialize and assign variable
//                EditText editText = dialog.findViewById(R.id.edit_text);
//                ListView listView = dialog.findViewById(R.id.list_view);
//
//
//
//                listView.setAdapter(reqUserAdapter);
//                editText.addTextChangedListener(new TextWatcher() {
//                                                    @Override
//                                                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//                                                    }
//
//                                                    @Override
//                                                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                                                        reqUserAdapter.getFilter().filter(charSequence);
//                                                    }
//
//                                                    @Override
//                                                    public void afterTextChanged(Editable editable) {
//
//                                                    }
//
//                                                }
//
//                );
//                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        // when item selected from list
//                        // set selected item on textView
//                        req_user.setText(reqUserAdapter.getItem(position));
//                        // Dismiss dialog
//                        dialog.dismiss();
//
//                        JsonObjectRequest jsonObjectRequest2 = new JsonObjectRequest(Request.Method.GET,req_userurl
//                                , null, new Response.Listener<JSONObject>() {
//                            @Override
//                            public void onResponse(JSONObject response) {
//                                try {
//                                    String ss = req_user.getText().toString();
//                                    JSONArray jsonArray = response.getJSONArray("data");
//
//                                    for (int i = 0; i < jsonArray.length(); i++) {
//                                        JSONObject jsonObject = jsonArray.getJSONObject(i);
//
//                                        String user_id= jsonObject.optString("user_id");
//                                        String user_full_name= jsonObject.optString("user_full_name");
//                                        String user_employee_id= jsonObject.optString("user_employee_id");
//
//                                        if(ss.equals(user_full_name+" - "+user_employee_id)){
//                                            String idea = user_id;
//                                            setReqUserid.setText(idea);
//                                             Toast.makeText(OtherRequisitionAddActivity.this, setReqUserid.getText().toString(), Toast.LENGTH_SHORT).show();
//
//                                            //PassJobNumber(idea);
//                                            //TypeIdPass(idea);
//                                        }
//
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
//
//                    }
//                });
//            }
//        });

    }


    private void JobNumber() {

        String token = getIntent().getStringExtra("token");
        String userId = getIntent().getStringExtra("userId");
        String location = getIntent().getStringExtra("location");

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
                                requisitionJobNumberSpinnerArrayAdapter = new ArrayAdapter<RequisitionJobNumberSpinner>(OtherRequisitionAddActivity.this,
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
                Toast.makeText(OtherRequisitionAddActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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

        RequestQueue requestQueue1 = Volley.newRequestQueue(OtherRequisitionAddActivity.this);
        requestQueue1.add(request1);

        job_nuber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Initialize dialog
                dialog = new Dialog(OtherRequisitionAddActivity.this);

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

                        Toast.makeText(OtherRequisitionAddActivity.this, sp.job_id, Toast.LENGTH_SHORT).show();

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
                                    seamAdapter = new ArrayAdapter<>(OtherRequisitionAddActivity.this,
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
                Toast.makeText(OtherRequisitionAddActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("job_id", idea);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(OtherRequisitionAddActivity.this);
        requestQueue.add(request1);

//        JsonObjectRequest jsonObjectRequest1 = new JsonObjectRequest(Request.Method.POST, seamurlurl+idea, null,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        try {
//
//                            JSONArray jsonArray = response.getJSONArray("data");
//                            for(int i=0;i<jsonArray.length();i++){
//                                JSONObject jsonObject = jsonArray.getJSONObject(i);
//
//                                String job_id= jsonObject.optString("job_id");
//                                String seam_number= jsonObject.optString("seam_number");
//
//                                String[] strSplit = seam_number.split(",");
//                                ArrayList<String> strList = new ArrayList<String>(
//                                        Arrays.asList(strSplit));
//
//                                for (String s : strList) {
//                                    seamList.add(s);
//                                    seamAdapter = new ArrayAdapter<>(RequisitionAddActivity.this,
//                                            android.R.layout.simple_list_item_1, seamList);
//                                    seamAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                                }
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

        seamnumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Initialize dialog
                dialog = new Dialog(OtherRequisitionAddActivity.this);

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

                        Toast.makeText(OtherRequisitionAddActivity.this, seamnumber.getText().toString(), Toast.LENGTH_SHORT).show();


                    }
                });
            }
        });
    }



}


