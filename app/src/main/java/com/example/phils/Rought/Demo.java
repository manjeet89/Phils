package com.example.phils.Rought;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.phils.MultipleItemSelectInSpinner.Employee;
import com.example.phils.Adapter.MultiAdapter;
import com.example.phils.R;
import com.example.phils.Shareprefered.AppConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Demo extends AppCompatActivity {

    private ArrayList<Employee> employees = new ArrayList<>();
    private MultiAdapter adapter ;
    private ArrayAdapter arrayAdapter;
    RequestQueue requestQueue;
    String url = "https://erp.philsengg.com/api/stock/stock_category";

    Dialog dialog;
     Button classme;
     TextView name;

    androidx.appcompat.widget.AppCompatButton btnGetSelected,btnAdd;
    androidx.recyclerview.widget.RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        requestQueue = Volley.newRequestQueue(this);

        btnGetSelected = findViewById(R.id.btnGetSelected);
        recyclerView = findViewById(R.id.recyclerView);
        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setVisibility(View.GONE);

        EditText editText =findViewById(R.id.edit_text);

        name = findViewById(R.id.name);
        classme = findViewById(R.id.classme);
        classme.setVisibility(View.GONE);
        classme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                dialog = new Dialog(Demo.this);

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
                androidx.recyclerview.widget.RecyclerView recyclerView = dialog.findViewById(R.id.recyclerView);
                EditText editText =dialog.findViewById(R.id.edit_text);


                recyclerView.setLayoutManager(new LinearLayoutManager(Demo.this));
                recyclerView.addItemDecoration(new DividerItemDecoration(Demo.this, LinearLayoutManager.VERTICAL));
                adapter = new MultiAdapter(Demo.this,employees);
                recyclerView.setAdapter(adapter);


                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                     arrayAdapter.getFilter().filter(charSequence);
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                    fileList(editable.toString());
                    }
                });


                btnGetSelected.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (adapter.getSelected().size() > 0) {
                            StringBuilder stringBuilder = new StringBuilder();
                            StringBuilder stringBuildername = new StringBuilder();

                            for (int i = 0; i < adapter.getSelected().size(); i++) {
                                stringBuilder.append(adapter.getSelected().get(i).getId());
                                stringBuildername.append(adapter.getSelected().get(i).getName());

                                stringBuilder.append("\n");
                                stringBuildername.append(" , ");
                            }
                            Toast.makeText(Demo.this, stringBuilder.toString().trim(), Toast.LENGTH_SHORT).show();
                            name.setText(stringBuildername.toString().trim());
                            dialog.dismiss();

                        } else {
                            Toast.makeText(Demo.this, "No Selection", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

        AppConfig appConfig = new AppConfig(this);
        employees = new ArrayList<>();


        String token = appConfig.getuser_token();
        String userId = appConfig.getuser_id();
        String location = appConfig.getLocationId();
        String user_employee_type = appConfig.getuser_employee_type();


        StringRequest request = new StringRequest(Request.Method.POST, url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            int j=0;

                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("message");
                            Toast.makeText(Demo.this, message, Toast.LENGTH_SHORT).show();
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            for(int i=0;i<jsonArray.length();i++)
                            {
                                j++;
                                JSONObject object = jsonArray.getJSONObject(i);
                                String stock_category_id = object.getString("stock_category_id");
                                String stock_category_name = object.getString("stock_category_name");
                                Employee employee = new Employee();

                                Log.d("butter",stock_category_id);
                                employee.setId(stock_category_id);
                                employee.setName(stock_category_name);
                                if (i == 0) {
                                    employee.setChecked(true);
                                }
                                employees.add(employee);

                            }
                            adapter.setEmployees(employees);
//                            arrayAdapter = new ArrayAdapter(Demo.this,
//                                    android.R.layout.simple_spinner_dropdown_item,employees);
//                            arrayAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);



                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Demo.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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

        RequestQueue requestQueue = Volley.newRequestQueue(Demo.this);
        requestQueue.add(request);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        adapter = new MultiAdapter(this, employees);


        recyclerView.setAdapter(adapter);


        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
               // fileList(charSequence.toString());
               // btnGetSelected.setVisibility(View.VISIBLE);

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
               // arrayAdapter.getFilter().filter(charSequence);
                //fileList(charSequence.toString());
                if(charSequence.toString().equals("")) {
                    btnGetSelected.setVisibility(View.VISIBLE);
                    fileList(charSequence.toString());
                    btnAdd.setVisibility(View.GONE);
                }
                else {
                    fileList(charSequence.toString());
                    btnAdd.setVisibility(View.VISIBLE);
                    btnGetSelected.setVisibility(View.GONE);


                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
              //  btnGetSelected.setVisibility(View.VISIBLE);



            }
        });

//        createList();
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnGetSelected.setVisibility(View.VISIBLE);
                btnAdd.setVisibility(View.GONE);
                editText.setText("");


            }
        });


        btnGetSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (adapter.getSelected().size() > 0) {
                    int s = adapter.getItemCount();
                    Log.d("ddddd", String.valueOf(s));
                    StringBuilder stringBuilder = new StringBuilder();
                    for (int i = 0; i < adapter.getSelected().size(); i++) {
                        stringBuilder.append(adapter.getSelected().get(i).getId());
                        stringBuilder.append("\n");
                    }
                    Toast.makeText(Demo.this, stringBuilder.toString().trim(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Demo.this, "No Selection", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void fileList(String toString) {

        ArrayList<Employee> filEmployees= new ArrayList<>();
        for (Employee item: employees){
            if(item.getName().toLowerCase(Locale.ROOT).contains(toString)){
                filEmployees.add(item);
            }
            else{
              //  filEmployees.add(item);
            }
        }
        adapter.filterList(filEmployees);
    }

    private void createList() {

//        employees = new ArrayList<>();

//        for (int i = 0; i < 20; i++) {
//            Employee employee = new Employee();
//            employee.setName("Employee " + (i + 1));
//            employee.setId("100"+(i+1));
//            // for example to show at least one selection
//            if (i == 0) {
//                employee.setChecked(true);
//            }
//            //
//            employees.add(employee);
//        }
//        adapter.setEmployees(employees);
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}