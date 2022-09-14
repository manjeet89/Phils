package com.example.phils.Admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
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
import com.example.phils.Demo;
import com.example.phils.LoginActivity;
import com.example.phils.R;
import com.example.phils.ResponseModels.ResponseModelStockCategory;
import com.example.phils.Shareprefered.AppConfig;
import com.example.phils.TwoStepVerification;
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

public class StockCategoryActivity extends AppCompatActivity {
    ProgressDialog progressDialog;
    RecyclerView recview;
    SearchView searchView;
    Button btn;
    StockCategoryAdapterClass stockCategoryAdapterClass;
    List<ResponseModelStockCategory> data;
    ResponseModelStockCategory responseModelStockCategory;
    LinearLayoutManager linearLayoutManager;
    SwipeRefreshLayout swipe;
    TextView location_save;
    AppConfig appConfig;
    private StockCategoryAdapterClass.RecycleViewClickListener listener;

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_category);

        appConfig = new AppConfig(this);
        location_save = findViewById(R.id.location_save);
        String location_save1 = appConfig.getLocation();
        location_save.setText(location_save1);


        recview = findViewById(R.id.recview);
        btn = findViewById(R.id.add_category);

        swipe = findViewById(R.id.swipeLayout);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
               startActivity(new Intent(getApplicationContext(),StockCategoryActivity.class));
                swipe.setRefreshing(false);
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
                    default:
                        return true;
                }
                return true;
            }
        });


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Add_category();
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
        stockCategoryAdapterClass = new StockCategoryAdapterClass(listener,data);
        recview.setAdapter(stockCategoryAdapterClass);

    }


    private void Add_category() {
        Intent intent = new Intent(getApplicationContext(), Add_Stock_Category_Activity.class);
        startActivity(intent);
        finish();
    }

    private void fileList(String newText) {

        List<ResponseModelStockCategory> modelStockCategories = new ArrayList<>();
        for(ResponseModelStockCategory responseModelStockCategory : data)
        {
            if(responseModelStockCategory.getStock_category_name().toLowerCase(Locale.ROOT).contains(newText.toLowerCase(Locale.ROOT))){
                modelStockCategories.add(responseModelStockCategory);
            }
        }

        if (modelStockCategories.isEmpty()){
            Toast.makeText(this, "No Data found", Toast.LENGTH_SHORT).show();
        }
        else
        {
            stockCategoryAdapterClass.setFilteredList(modelStockCategories);
        }
    }


    private void fatchdata() {
        progressDialog = new ProgressDialog(StockCategoryActivity.this);
        progressDialog.setTitle("Stock Category");
        progressDialog.setMessage("Loading... Please Wait!");
        progressDialog.show();
        String token = appConfig.getuser_token();
        String userId = appConfig.getuser_id();
        //Toast.makeText(this, token+"/"+userId, Toast.LENGTH_SHORT).show();

        StringRequest request = new StringRequest(Request.Method.POST, "https://mployis.com/staging/api/stock/stock_category",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            int j=0;
                            String stock_category_status;
                            String emp_type_name;

                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("message");

                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            for(int i=0;i<jsonArray.length();i++)
                            {
                                j++;
                                JSONObject object = jsonArray.getJSONObject(i);
                                String sn = String.valueOf(j);
                                String stock_category_id = object.getString("stock_category_id");
                                String stock_category_name = object.getString("stock_category_name");
                                String stock_emp_category = object.getString("stock_emp_category");

                                 stock_category_status = object.getString("stock_category_status");
                                if(stock_category_status.equals(String.valueOf(0)))
                                    {
                                        stock_category_status = "Disable";
                                    }
                                    else
                                    {
                                        stock_category_status = "Enable";
                                    }


                                String stock_category_updated_on = object.getString("stock_category_updated_on");
                                String stock_category_created_on = object.getString("stock_category_created_on");
                                 emp_type_name = object.getString("emp_type_name");
                                if(emp_type_name.equals("null"))
                                    {
                                        emp_type_name = "Other";
                                    }
                                    else
                                    {
                                        emp_type_name = object.getString("emp_type_name");
                                    }

                                    responseModelStockCategory = new ResponseModelStockCategory(sn,stock_category_id,stock_category_name,stock_emp_category,stock_category_status,stock_category_updated_on,stock_category_created_on,emp_type_name);
                                    data.add(responseModelStockCategory);
                                    stockCategoryAdapterClass.notifyDataSetChanged();
                                    progressDialog.dismiss();

                            }

                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(StockCategoryActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap headers = new HashMap();
                headers.put("user_token",token);
                headers.put("user_id", userId);
                return headers;
                //return super.getHeaders();
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(StockCategoryActivity.this);
        requestQueue.add(request);
    }

//    private void fatchdata() {
//
//        progressDialog = new ProgressDialog(StockCategoryActivity.this);
//        progressDialog.setTitle("Stock Category");
//        progressDialog.setMessage("Loading... Please Wait!");
//        progressDialog.show();
//        StringRequest request = new StringRequest(Request.Method.GET, "https://investment-wizards.com/manjeet/Phils_Stock/tbl_stock_category.php",
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            String status;
//                            int stat = 0;
//                            int j=0;
//                            String cat_group;
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
//                                    String stock_category_id = object.getString("stock_category_id");
//                                    String sn = String.valueOf(j);
//                                    String category = object.getString("stock_category_name");
//
//                                     cat_group = object.getString("emp_type_name");
//                                    if(cat_group.equals("null"))
//                                    {
//                                        cat_group = "Other";
//                                    }
//                                    else
//                                    {
//                                        cat_group = object.getString("emp_type_name");
//                                    }
//                                     status = object.getString("stock_category_status");
//                                    if(status.equals(String.valueOf(0)))
//                                    {
//                                        status = "Disable";
//                                    }
//                                    else
//                                    {
//                                        status = "Enable";
//                                    }
//
//                                    responseModelStockCategory = new ResponseModelStockCategory(sn,category,cat_group,status,stock_category_id);
//                                    data.add(responseModelStockCategory);
//                                    stockCategoryAdapterClass.notifyDataSetChanged();
//                                progressDialog.dismiss();
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
//                Toast.makeText(StockCategoryActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
//            startActivity(new Intent(getApplicationContext(), Demo.class));
//            }
//        });
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        requestQueue.add(request);
//    }

    private void recycleClickLister() {
        listener = new StockCategoryAdapterClass.RecycleViewClickListener() {

            @Override
            public void onClick(View v, int position) {
               // String id = data.get(position).getStock_category_id();
                String catGroup = data.get(position).getEmp_type_name();
                String category = data.get(position).getStock_category_name();
                String status = data.get(position).getStock_category_status();

                Intent intent = new Intent(getApplicationContext(), Update_StockCategory_Activity.class);
               // intent.putExtra("id",id);
                intent.putExtra("catGroup",catGroup);
                intent.putExtra("category",category);
                intent.putExtra("status",status);
                startActivity(intent);
                //Toast.makeText(UserActivity.this, kk, Toast.LENGTH_SHORT).show();
            }
        };
    }
}