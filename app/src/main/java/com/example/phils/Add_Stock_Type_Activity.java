package com.example.phils;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Add_Stock_Type_Activity extends AppCompatActivity {
    TextView check_status,select_type;
    ArrayList<String> arrayList1;
    Dialog dialog;

    Button insert_type;

    EditText type_insert;


    ArrayList<String> category = new ArrayList<>();
    ArrayAdapter<String> categoryAdapter;
    RequestQueue requestQueue;
    String url = "https://investment-wizards.com/manjeet/Phils_Stock/insert_category/fatch_spinner_stock/fatch_spinner_category_data_in_tbl_type.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stock_type);
        requestQueue = Volley.newRequestQueue(this);

        type_insert = findViewById(R.id.type_insert);

        //Button begging
        insert_type = findViewById(R.id.insert_type);
        insert_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Insert();
            }
        });

        //end button


        //begging category Spinner
        select_type = findViewById(R.id.select_category);
        select_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Initialize dialog
                dialog=new Dialog(Add_Stock_Type_Activity.this);

                // set custom dialog
                dialog.setContentView(R.layout.dialog_searchable_spinner_stock_type);

                // set custom height and width
                dialog.getWindow().setLayout(650,800);

                // set transparent background
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                // show dialog
                dialog.show();

                // Initialize and assign variable
                EditText editText=dialog.findViewById(R.id.edit_text);
                ListView listView=dialog.findViewById(R.id.list_view);
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url
                        , null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("data");

                            for(int i=0;i<jsonArray.length();i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String category_name = jsonObject.optString("stock_category_name");
                                String category_id = jsonObject.optString("stock_category_id");
                                category.add(category_name);
//                                category.add(category_id);

                                categoryAdapter = new ArrayAdapter<>(Add_Stock_Type_Activity.this,
                                        android.R.layout.simple_list_item_1, category);
                                categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
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

                                });


                                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        // when item selected from list
                                        // set selected item on textView
                                        select_type.setText(categoryAdapter.getItem(position));

                                        // Dismiss dialog
                                        dialog.dismiss();
                                    }
                                });
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

                requestQueue.add(jsonObjectRequest);

            }
        });

        //end catgory spinner


        // begging Status spinner
        check_status = findViewById(R.id.type_status);
        arrayList1=new ArrayList<>();
        arrayList1.add("Enable");
        arrayList1.add("Disable");

        check_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Initialize dialog
                dialog=new Dialog(Add_Stock_Type_Activity.this);

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

                ArrayAdapter<String> adapter=new ArrayAdapter<>(Add_Stock_Type_Activity.this, android.R.layout.simple_list_item_1,arrayList1);
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
                        check_status.setText(adapter.getItem(i));

                        // Dismiss dialog
                        dialog.dismiss();
                    }
                });

            }
        });
        // end Status spinner
    }

    private void Insert() {
        String e1 =  select_type.getText().toString().trim();
        String e2 = type_insert.getText().toString().trim();
        String e3 = check_status.getText().toString().trim();

        Toast.makeText(this,e1+" / "+e2+" / "+e3 , Toast.LENGTH_SHORT).show();
    }
}