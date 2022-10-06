package com.example.phils.Admin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
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
import com.example.phils.R;
import com.example.phils.Shareprefered.AppConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RequisitionChangeStatusActivity extends AppCompatActivity {

    TextView category,type,size,quantity,reqQuantity,changestats;
    EditText comment;
    Button btn;

    ArrayList<String> arrayList1;
    RequestQueue requestQueue;
    Dialog dialog;
    AppConfig appConfig;

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), RequisitionListActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requisition_change_status);
        appConfig = new AppConfig(this);

        category = findViewById(R.id.category);
        type = findViewById(R.id.type);
        size = findViewById(R.id.size);
        quantity = findViewById(R.id.quantity);
        reqQuantity = findViewById(R.id.reqQuantity);
        changestats = findViewById(R.id.changestats);
        String id = getIntent().getStringExtra("id");
        String changecategory = getIntent().getStringExtra("stockcategory");
        String changetype = getIntent().getStringExtra("stocktype");
        String changesize = getIntent().getStringExtra("stocksize");
        String changequantity = getIntent().getStringExtra("quantityAssign");
        String changereqquantity = getIntent().getStringExtra("quantityreq");
        String changesatusprint = getIntent().getStringExtra("managerstatus");

        category.setText(changecategory);
        type.setText(changetype);
        size.setText(changesize);
        quantity.setText(changequantity);
        reqQuantity.setText(changereqquantity);
        changestats.setText(changesatusprint);

        comment = findViewById(R.id.comment);

        btn = findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String token = getIntent().getStringExtra("token");
                String userId = getIntent().getStringExtra("userId");
                String location = getIntent().getStringExtra("location");
                String user_employee_type = appConfig.getuser_employee_type();

//        Log.d("check",consumable);
//        Log.d("check",weldergrinder);

                ProgressDialog progressDialog = new ProgressDialog(RequisitionChangeStatusActivity.this);
                progressDialog.setMessage("Loading... Please Wait!");
                progressDialog.show();

                String stat = changestats.getText().toString();
                if (stat.equals("Requested")) {
                    stat = "0";
                } else if(stat.equals("Accepted"))
                {
                    stat = "1";

                }  else {
                    stat = "2";
                }
                String statusvale = stat;

                StringRequest request = new StringRequest(Request.Method.POST, "https://mployis.com/staging/api/requisition/requistion_change_status",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                JSONObject jsonObject = null;
                                try {
                                    jsonObject = new JSONObject(response);
                                    String message = jsonObject.getString("message");

                                    if(message.equals("Requisition status changed successfully")) {
                                        Toast.makeText(RequisitionChangeStatusActivity.this, message, Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getApplicationContext(), RequisitionListActivity.class));
                                    }
                                    else
                                    {
                                        Toast.makeText(RequisitionChangeStatusActivity.this, message, Toast.LENGTH_SHORT).show();

                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                //Toast.makeText(Add_Stock_Category_Activity.this, response, Toast.LENGTH_SHORT).show();
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RequisitionChangeStatusActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

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
                        params.put("req_manager_status", statusvale);
                        params.put("req_manager_comment", comment.getText().toString());

                        return params;
                    }

                };
                RequestQueue requestQueue = Volley.newRequestQueue(RequisitionChangeStatusActivity.this);
                requestQueue.add(request);
            }
        });

        arrayList1=new ArrayList<>();
        arrayList1.add("Requested");
        arrayList1.add("Accepted");
        arrayList1.add("Declined");

        changestats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Initialize dialog
                dialog=new Dialog(RequisitionChangeStatusActivity.this);

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

                ArrayAdapter<String> adapter=new ArrayAdapter<>(RequisitionChangeStatusActivity.this, android.R.layout.simple_list_item_1,arrayList1);
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
                        changestats.setText(adapter.getItem(i));

                        // Dismiss dialog
                        dialog.dismiss();
                    }
                });

            }
        });


    }
}