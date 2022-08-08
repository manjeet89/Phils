package com.example.phils;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Add_Stock_Category_Activity extends AppCompatActivity {
    TextView textview,check_status;
    ArrayList<String> arrayList , arrayList1;
    Dialog dialog;
    Button button;
    EditText category_name;
        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stock_category);
            textview=findViewById(R.id.testView);
            check_status = findViewById(R.id.status_check);
            button = findViewById(R.id.insert_cat);
            category_name = findViewById(R.id.category_insert);

            // initialize array list
            arrayList=new ArrayList<>();
            arrayList1=new ArrayList<>();

            // set value in array list
            arrayList.add("Others");
            arrayList.add("Welding");
            arrayList.add("Grinding");

            arrayList1.add("Enable");
            arrayList1.add("Disable");

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Insert();
                }
            });

            textview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Initialize dialog
                    dialog=new Dialog(Add_Stock_Category_Activity.this);

                    // set custom dialog
                    dialog.setContentView(R.layout.dialog_searchable_spinner);

                    // set custom height and width
                    dialog.getWindow().setLayout(650,800);

                    // set transparent background
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                    // show dialog
                    dialog.show();

                    // Initialize and assign variable
                    EditText editText=dialog.findViewById(R.id.edit_text);
                    ListView listView=dialog.findViewById(R.id.list_view);

                    // Initialize array adapter
                    ArrayAdapter<String> adapter=new ArrayAdapter<>(Add_Stock_Category_Activity.this, android.R.layout.simple_list_item_1,arrayList);

                    // set adapter
                    listView.setAdapter(adapter);
                    editText.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            adapter.getFilter().filter(s);
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            // when item selected from list
                            // set selected item on textView
                            textview.setText(adapter.getItem(position));

                            // Dismiss dialog
                            dialog.dismiss();
                        }
                    });
                }
            });

            check_status.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Initialize dialog
                    dialog=new Dialog(Add_Stock_Category_Activity.this);

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

                    ArrayAdapter<String> adapter=new ArrayAdapter<>(Add_Stock_Category_Activity.this, android.R.layout.simple_list_item_1,arrayList1);
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
        }

    private void Insert() {
        String e1 =  textview.getText().toString().trim();
        String e2 = category_name.getText().toString().trim();
        String e3 = check_status.getText().toString().trim();
        if(TextUtils.isEmpty(e1))
        {
            textview.setError("Please Select Category Group");
            Toast.makeText(Add_Stock_Category_Activity.this, "Please Select Category Group", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(TextUtils.isEmpty(e2))
        {
            category_name.setError("Please Enter your Category Name");
            Toast.makeText(Add_Stock_Category_Activity.this, "Please Enter your Category Name", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(TextUtils.isEmpty(e3))
        {
            check_status.setError("Please Select your Status");
            Toast.makeText(Add_Stock_Category_Activity.this, "Please Select your Status", Toast.LENGTH_SHORT).show();
            return;
        }
        else
        {
            if(e1.equals("Welding"))
            {
                e1 = "5";
            }
            else if(e1.equals("Grinding"))
            {
                e1 = "4";
            }
            else
            {
                e1 = "0";
            }


            if(e3.equals("Enable"))
            {
                e3 = "1";
            }
            else
            {
                e3 = "0";
            }

            String e4 =  e1;
            String e5 = e2;
            String e6 = e3;
//            Toast.makeText(this, e4+"  /  "+e5+"  /  "+e6, Toast.LENGTH_SHORT).show();


            StringRequest request = new StringRequest(Request.Method.POST, "https://investment-wizards.com/manjeet/Phils_Stock/insert_category/add_category.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(Add_Stock_Category_Activity.this, response, Toast.LENGTH_SHORT).show();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Add_Stock_Category_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

                }
            })
            {
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<String,String>();
                    params.put("stock_category_name",e5);
                    params.put("stock_emp_category",e4);
                    params.put("stock_category_status",e6);
                    return  params;
                }
            };
            RequestQueue  requestQueue = Volley.newRequestQueue(Add_Stock_Category_Activity.this);
            requestQueue.add(request);

        }
    }

}