package com.example.phils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
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
import com.example.phils.Admin.Add_Job_Category_Activity;
import com.example.phils.Admin.Add_Stock_Category_Activity;
import com.example.phils.Admin.MainActivity;
import com.example.phils.Admin.StockCategoryActivity;
import com.example.phils.Admin.StockListActivity;
import com.example.phils.Admin.StockMakeActivity;
import com.example.phils.Admin.StockSizeActivity;
import com.example.phils.Admin.StockTypeActivity;
import com.example.phils.Admin.StockUomActivity;
import com.example.phils.Shareprefered.AppConfig;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Update_StockCategory_Activity extends AppCompatActivity {
    TextView textview,check_status;
    ArrayList<String> arrayList , arrayList1;
    Dialog dialog;
    Button button;
    EditText category_name;
    TextView location_save;
    AppConfig appConfig;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_stock_category);

        String catGroup = getIntent().getStringExtra("catGroup");
        String category = getIntent().getStringExtra("category");
        String status = getIntent().getStringExtra("status");



        appConfig = new AppConfig(this);
        location_save = findViewById(R.id.location_save);
        String location_save1 = appConfig.getLocation();
        location_save.setText(location_save1);


        textview=findViewById(R.id.testView);
        check_status = findViewById(R.id.status_check);
        button = findViewById(R.id.insert_cat);
        category_name = findViewById(R.id.category_insert);


        textview.setText(catGroup);
        category_name.setText(category);
        check_status.setText(status);

        MaterialToolbar toolbar = findViewById(R.id.topAppbar);
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigation_view);
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
                    default:
                        return true;
                }
                return true;
            }
        });




        // initialize array list
        arrayList=new ArrayList<>();
        arrayList1=new ArrayList<>();

        // set value in array list
        arrayList.add("Others");
        arrayList.add("WELDER");
        arrayList.add("GRINDER");

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
                dialog=new Dialog(Update_StockCategory_Activity.this);

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
                ArrayAdapter<String> adapter=new ArrayAdapter<>(Update_StockCategory_Activity.this, android.R.layout.simple_list_item_1,arrayList);

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
                dialog=new Dialog(Update_StockCategory_Activity.this);

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

                ArrayAdapter<String> adapter=new ArrayAdapter<>(Update_StockCategory_Activity.this, android.R.layout.simple_list_item_1,arrayList1);
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
        String e2 = category_name.getText().toString().toUpperCase(Locale.ROOT).trim();
        String e3 = check_status.getText().toString().trim();
        if(TextUtils.isEmpty(e1))
        {
            textview.setError("Please Select Category Group");
            Toast.makeText(Update_StockCategory_Activity.this, "Please Select Category Group", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(TextUtils.isEmpty(e2))
        {
            category_name.setError("Please Enter your Category Name");
            Toast.makeText(Update_StockCategory_Activity.this, "Please Enter your Category Name", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(TextUtils.isEmpty(e3))
        {
            check_status.setError("Please Select your Status");
            Toast.makeText(Update_StockCategory_Activity.this, "Please Select your Status", Toast.LENGTH_SHORT).show();
            return;
        }
        else
        {
            if(e1.equals("WELDER"))
            {
                e1 = "5";
            }
            else if(e1.equals("GRINDER"))
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

            String id = getIntent().getStringExtra("id");
            String e4 =  e1;
            String e5 = e2;
            String e6 = e3;
//            Toast.makeText(this, e4+"  /  "+e5+"  /  "+e6, Toast.LENGTH_SHORT).show();


            StringRequest request = new StringRequest(Request.Method.POST, "https://investment-wizards.com/manjeet/Phils_Stock/update_category/update_stock_category.php",
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            Toast.makeText(Update_StockCategory_Activity.this, response, Toast.LENGTH_SHORT).show();

                                        }
                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(Update_StockCategory_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();


                                }
                            })
                            {
                                @Nullable
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    Map<String,String> params = new HashMap<String,String>();
                                    params.put("stock_category_id",id);
                                    params.put("stock_category_name",e5);
                                    params.put("stock_emp_category",e4);
                                    params.put("stock_category_status",e6);
                                    return  params;
                                }
                            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(request);

        }
    }

}