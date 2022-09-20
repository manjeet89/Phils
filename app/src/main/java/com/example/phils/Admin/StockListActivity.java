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
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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
import com.example.phils.Adapter.StockListAdapterClass;
import com.example.phils.Demo;
import com.example.phils.LoginActivity;
import com.example.phils.ProfileActivity;
import com.example.phils.R;
import com.example.phils.ResponseModels.ResponseModelStockCategory;
import com.example.phils.ResponseModels.ResponseModelStockList;
import com.example.phils.Shareprefered.AppConfig;
import com.example.phils.Update_StockList_Activity;
import com.example.phils.UserActivity;
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

public class StockListActivity extends AppCompatActivity {
    ProgressDialog progressDialog;

    RecyclerView recview;
    SearchView searchView;
    ImageView img,profile;

    StockListAdapterClass stockListAdapterClass;
    List<ResponseModelStockList> data;
    ResponseModelStockList responseModelStockList;
    LinearLayoutManager linearLayoutManager;
    Button stock_add_button;
    TextView location_save;
    AppConfig appConfig;
    private StockListAdapterClass.RecycleViewClickListener listener;

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_list);

        appConfig = new AppConfig(this);
        location_save = findViewById(R.id.location_save);
        String location_save1 = appConfig.getLocation();
        location_save.setText(location_save1);


        recview = findViewById(R.id.recview);
        searchView = findViewById(R.id.search);
        searchView.clearFocus();



        stock_add_button = findViewById(R.id.stock_add);
        stock_add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String token = appConfig.getuser_token();
                String userId = appConfig.getuser_id();
                String location = appConfig.getLocation();

                Intent intent = new Intent(getApplicationContext(), Add_Stock_List_Activity.class);
                intent.putExtra("token",token);
                intent.putExtra("userId",userId);
                intent.putExtra("location",location);

                startActivity(intent);
                finish();
            }
        });


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
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        break;

                    case R.id.user:
                        startActivity(new Intent(getApplicationContext(), UserActivity.class));
                        break;

                    case R.id.category_stock:
                        startActivity(new Intent(getApplicationContext(),StockCategoryActivity.class));
                        break;

                    case R.id.type_stock:
                        startActivity(new Intent(getApplicationContext(),StockTypeActivity.class));
                        break;

                    case R.id.size_stock:
                        startActivity(new Intent(getApplicationContext(), StockSizeActivity.class));
                        break;

                    case R.id.make_stock:
                        startActivity(new Intent(getApplicationContext(),StockMakeActivity.class));
                        break;

                    case R.id.umo_stock:
                        startActivity(new Intent(getApplicationContext(),StockUomActivity.class));
                        break;

                    case R.id.list_stock:
                        startActivity(new Intent(getApplicationContext(),StockListActivity.class));
                        break;

                    case R.id.category_job:
                        startActivity(new Intent(getApplicationContext(), Job_Category_Activity.class));
                        break;

                    case R.id.Size_job:
                        startActivity(new Intent(getApplicationContext(), Job_Size_Activity.class));
                        break;

                    default:
                        return true;
                }
                return true;
            }
        });

        profile = findViewById(R.id.profile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
                //Toast.makeText(MainActivity.this, "desh", Toast.LENGTH_SHORT).show();
                Dialog dialog=new Dialog(StockListActivity.this);

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




                Button logout = dialog.findViewById(R.id.logout);
                logout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        appConfig.updateUserLoginStatus(false);
                        startActivity(new Intent(StockListActivity.this, LoginActivity.class));
                        finish();
                    }
                });
                TextView textView = dialog.findViewById(R.id.my_profile);
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

        stockListAdapterClass = new StockListAdapterClass(listener,data);
        recview.setAdapter(stockListAdapterClass);


    }


    private void fileList(String newText) {

        List<ResponseModelStockList> modelStockCategories = new ArrayList<>();
        for(ResponseModelStockList responseModelStockList : data)
        {
            if(responseModelStockList.getMake_name().toLowerCase(Locale.ROOT).contains(newText.toLowerCase(Locale.ROOT))
            || responseModelStockList.getStock_category_name().toLowerCase(Locale.ROOT).contains(newText.toLowerCase(Locale.ROOT))
            || responseModelStockList.getStock_type_name().toLowerCase(Locale.ROOT).contains(newText.toLowerCase(Locale.ROOT))){
                modelStockCategories.add(responseModelStockList);
            }
        }

        if (modelStockCategories.isEmpty()){
            Toast.makeText(this, "No Data found", Toast.LENGTH_SHORT).show();
        }
        else
        {
            stockListAdapterClass.setFilteredList(modelStockCategories);
        }
    }

    private void fatchdata() {

        progressDialog = new ProgressDialog(StockListActivity.this);
        progressDialog.setTitle("Stock List");
        progressDialog.setMessage("Loading... Please Wait!");
        progressDialog.show();

        String token = appConfig.getuser_token();
        String userId = appConfig.getuser_id();
        String location = appConfig.getLocation();

        StringRequest request = new StringRequest(Request.Method.POST, "https://mployis.com/staging/api/stock/stock_list",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            int j=0;
//                            String stock_category_status;
//                            String emp_type_name;

                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("message");
//                            Toast.makeText(StockListActivity.this, message, Toast.LENGTH_SHORT).show();
//                            progressDialog.dismiss();

                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            for(int i=0;i<jsonArray.length();i++)
                            {
                                j++;
                                JSONObject object = jsonArray.getJSONObject(i);

                                String sn = String.valueOf(j);
                                String stock_id  = object.getString("stock_id");
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

                                responseModelStockList = new ResponseModelStockList(sn,stock_id,stock_location_id,stock_category_id,stock_type_id,
                                        stock_size_id,stock_batch_number,stock_invoice_number,stock_distributor_name,stock_make_id,
                                        stock_uom_id,safety_stock,stock_quantity,stock_lot,stock_price,is_stock_transfer,stock_status,stock_type_name,
                                        stock_size_name,make_name,uom_name,stock_category_name,assign_quantity);
                                data.add(responseModelStockList);
                                stockListAdapterClass.notifyDataSetChanged();
                                progressDialog.dismiss();

//                                String stock_category_id = object.getString("stock_category_id");
//                                String stock_category_name = object.getString("stock_category_name");
//                                String stock_emp_category = object.getString("stock_emp_category");
//
//                                stock_category_status = object.getString("stock_category_status");
//                                if(stock_category_status.equals(String.valueOf(0)))
//                                {
//                                    stock_category_status = "Disable";
//                                }
//                                else
//                                {
//                                    stock_category_status = "Enable";
//                                }
//
//
//                                String stock_category_updated_on = object.getString("stock_category_updated_on");
//                                String stock_category_created_on = object.getString("stock_category_created_on");
//                                emp_type_name = object.getString("emp_type_name");
//                                if(emp_type_name.equals("null"))
//                                {
//                                    emp_type_name = "Others";
//                                }
//                                else
//                                {
//                                    emp_type_name = object.getString("emp_type_name");
//                                }
//
//                                responseModelStockCategory = new ResponseModelStockCategory(sn,stock_category_id,stock_category_name,stock_emp_category,stock_category_status,stock_category_updated_on,stock_category_created_on,emp_type_name);
//                                data.add(responseModelStockCategory);
//                                stockCategoryAdapterClass.notifyDataSetChanged();
//                                progressDialog.dismiss();

                            }

                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(StockListActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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

        RequestQueue requestQueue = Volley.newRequestQueue(StockListActivity.this);
        requestQueue.add(request);

    }


    //    private void fatchdata() {
//        progressDialog = new ProgressDialog(StockListActivity.this);
//        progressDialog.setTitle("Stock List");
//        progressDialog.setMessage("Loading... Please Wait!");
//        progressDialog.setIcon(R.drawable.ic_baseline_autorenew_24);
//        progressDialog.show();
//        StringRequest request = new StringRequest(Request.Method.GET, "https://investment-wizards.com/manjeet/Phils_Stock/tbl_stock_list.php",
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            String stock_status;
//                            int stat = 0;
//                            int j=0;
//                            JSONObject jsonObject = new JSONObject(response);
//                            String success = jsonObject.getString("success");
//
//                            JSONArray jsonArray = jsonObject.getJSONArray("data");
//                            if(success.equals("1"))
//                            {
//                                for(int i=0;i<jsonArray.length();i++)
//                                {
//                                    j++;
//                                    JSONObject object = jsonArray.getJSONObject(i);
//                                    String stock_id = object.getString("stock_id");
//                                    String sn = String.valueOf(j);
//                                    String stock_category_name = object.getString("stock_category_name");
//                                    String stock_type_name = object.getString("stock_type_name");
//                                    String stock_size_name = object.getString("stock_size_name");
//                                    String stock_batch_number = object.getString("stock_batch_number");
//                                    String make_name = object.getString("make_name");
//                                    String uom_name = object.getString("uom_name");
//                                    String safety_stock = object.getString("safety_stock");
//                                    String stock_quantity = object.getString("stock_quantity");
//                                    String stock_price = object.getString("stock_price");
//                                    stock_status = object.getString("stock_status");
//                                    if(stock_status.equals(String.valueOf(0)))
//                                    {
//                                        stock_status = "Disable";
//                                    }
//                                    else
//                                    {
//                                        stock_status = "Enable";
//                                    }
//
//                                   responseModelStockList = new ResponseModelStockList(sn,stock_id,stock_category_name,stock_type_name,stock_size_name,stock_batch_number,make_name,uom_name,safety_stock,stock_quantity,stock_price,stock_status);
//                                    data.add(responseModelStockList);
//                                    stockListAdapterClass.notifyDataSetChanged();
//                                    progressDialog.dismiss();
//
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
//                Toast.makeText(StockListActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        requestQueue.add(request);
//    }
    private void recycleClickLister() {
        listener = new StockListAdapterClass.RecycleViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                String kk = data.get(position).getStock_id();

                String stock_category_id = data.get(position).getStock_category_id();
                String stock_type_id = data.get(position).getStock_type_id();
                String stock_size_id = data.get(position).getStock_size_id();

                String Stock_category_name = data.get(position).getStock_category_name();
                String Stock_type_name = data.get(position).getStock_type_name();
                String Stock_size_name = data.get(position).getStock_size_name();

                String stock_batch_number = data.get(position).getStock_batch_number();

                String stock_make_id = data.get(position).getStock_make_id();
                String stock_uom_id = data.get(position).getStock_uom_id();

                String Make_name = data.get(position).getMake_name();
                String Uom_name = data.get(position).getUom_name();

                String safety_stock = data.get(position).getSafety_stock();
                String stock_quantity = data.get(position).getStock_quantity();
                String stock_lot = data.get(position).getStock_lot();
                String stock_price = data.get(position).getStock_price();
                String stock_invoice_number = data.get(position).getStock_invoice_number();
                String stock_distributor_name = data.get(position).getStock_distributor_name();


                String token = appConfig.getuser_token();
                String userId = appConfig.getuser_id();
                String location = appConfig.getLocation();

                Intent intent = new Intent(getApplicationContext(), Update_StockList_Activity.class);
                intent.putExtra("id", kk);
                intent.putExtra("stock_category_id", stock_category_id);
                intent.putExtra("stock_type_id", stock_type_id);
                intent.putExtra("stock_size_id", stock_size_id);

                intent.putExtra("Stock_category_name", Stock_category_name);
                intent.putExtra("Stock_type_name",Stock_type_name );
                intent.putExtra("Stock_size_name", Stock_size_name);

                intent.putExtra("stock_batch_number", stock_batch_number);

                intent.putExtra("stock_make_id", stock_make_id);
                intent.putExtra("stock_uom_id", stock_uom_id);

                intent.putExtra("Make_name", Make_name);
                intent.putExtra("Uom_name", Uom_name);

                intent.putExtra("safety_stock", safety_stock);
                intent.putExtra("stock_quantity", stock_quantity);
                intent.putExtra("stock_lot", stock_lot);
                intent.putExtra("stock_price", stock_price);
                intent.putExtra("stock_invoice_number", stock_invoice_number);
                intent.putExtra("stock_distributor_name", stock_distributor_name);

                intent.putExtra("token",token);
                intent.putExtra("userId",userId);
                intent.putExtra("location",location);



                startActivity(intent);
            }
        };
    }
}