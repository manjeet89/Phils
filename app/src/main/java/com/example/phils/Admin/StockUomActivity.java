package com.example.phils.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.example.phils.Adapter.StockUOMAdapterClass;
import com.example.phils.R;
import com.example.phils.ResponseModels.ResponseModelStockMake;
import com.example.phils.ResponseModels.ResponseModelStockUOM;
import com.example.phils.Shareprefered.AppConfig;
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

public class StockUomActivity extends AppCompatActivity {
    ProgressDialog progressDialog;

    RecyclerView recview;
    SearchView searchView;

    Button uom;
    StockUOMAdapterClass stockUOMAdapterClass;
    List<ResponseModelStockUOM> data;
    ResponseModelStockUOM responseModelStockUOM;
    LinearLayoutManager linearLayoutManager;

    TextView location_save;
    AppConfig appConfig;
    private StockUOMAdapterClass.RecycleViewClickListener listener;

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_uom);

        appConfig = new AppConfig(this);
        location_save = findViewById(R.id.location_save);
        String location_save1 = appConfig.getLocation();
        location_save.setText(location_save1);

        recview = findViewById(R.id.recview);
        searchView = findViewById(R.id.search);
        searchView.clearFocus();

        uom = findViewById(R.id.uom);
        uom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String token = appConfig.getuser_token();
                String userId = appConfig.getuser_id();
                String location = appConfig.getLocation();

                Intent intent = new Intent(getApplicationContext(), Add_Stock_UOM_Activity.class);
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
                    default:
                        return true;
                }
                return true;
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
        stockUOMAdapterClass = new StockUOMAdapterClass(listener,data);
        recview.setAdapter(stockUOMAdapterClass);

       }



    private void fileList(String newText) {

        List<ResponseModelStockUOM> modelStockCategories = new ArrayList<>();
        for(ResponseModelStockUOM responseModelStockUOM : data)
        {
            if(responseModelStockUOM.getUom_name().toLowerCase(Locale.ROOT).contains(newText.toLowerCase(Locale.ROOT))){
                modelStockCategories.add(responseModelStockUOM);
            }
        }

        if (modelStockCategories.isEmpty()){
            Toast.makeText(this, "No Data found", Toast.LENGTH_SHORT).show();
        }
        else
        {
            stockUOMAdapterClass.setFilteredList(modelStockCategories);
        }
    }

    private void fatchdata() {

        progressDialog = new ProgressDialog(StockUomActivity.this);
        progressDialog.setTitle("Stock UOM");
        progressDialog.setMessage("Loading... Please Wait!");
        progressDialog.show();
        String token = appConfig.getuser_token();
        String userId = appConfig.getuser_id();
        String location = appConfig.getLocation();

        StringRequest request = new StringRequest(Request.Method.POST, "https://mployis.com/staging/api/stock/stock_uom",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            int j=0;
                            String uom_status;

                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("message");
                            // Toast.makeText(StockMakeActivity.this, message, Toast.LENGTH_SHORT).show();
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            for(int i=0;i<jsonArray.length();i++)
                            {

                                j++;
                                JSONObject object = jsonArray.getJSONObject(i);
                                String sn = String.valueOf(j);

                                String uom_id = object.getString("uom_id");
                                String uom_name = object.getString("uom_name");

                                uom_status = object.getString("uom_status");
                                if(uom_status.equals(String.valueOf(0)))
                                {
                                    uom_status = "Disable";
                                }
                                else
                                {
                                    uom_status = "Enable";
                                }


                                String uom_updated_on = object.getString("uom_updated_on");
                                String uom_created_on = object.getString("uom_created_on");


                                    responseModelStockUOM = new ResponseModelStockUOM(sn,uom_id,uom_name,uom_status,uom_updated_on,uom_created_on);
                                    data.add(responseModelStockUOM);
                                    stockUOMAdapterClass.notifyDataSetChanged();
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
                Toast.makeText(StockUomActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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

        RequestQueue requestQueue = Volley.newRequestQueue(StockUomActivity.this);
        requestQueue.add(request);

    }

//    private void fatchdata() {
//        progressDialog = new ProgressDialog(StockUomActivity.this);
//        progressDialog.setTitle("Stock UOM");
//        progressDialog.setMessage("Loading... Please Wait!");
//        progressDialog.setIcon(R.drawable.ic_baseline_autorenew_24);
//        progressDialog.show();
//        StringRequest request = new StringRequest(Request.Method.GET, "https://investment-wizards.com/manjeet/Phils_Stock/tbl_stock_uom.php",
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            String uom_status;
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
//                                    String uom_id = object.getString("uom_id");
//                                    String  sn = String.valueOf(j);
//                                    String uom_name = object.getString("uom_name");
//
//                                    uom_status = object.getString("uom_status");
//                                    if(uom_status.equals(String.valueOf(0)))
//                                    {
//                                        uom_status = "Disable";
//                                    }
//                                    else
//                                    {
//                                        uom_status = "Enable";
//                                    }
//
//                                    responseModelStockUOM = new ResponseModelStockUOM(sn,uom_id,uom_name,uom_status);
//                                    data.add(responseModelStockUOM);
//                                    stockUOMAdapterClass.notifyDataSetChanged();
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
//                Toast.makeText(StockUomActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        requestQueue.add(request);
//    }
    private void recycleClickLister() {
        listener = new StockUOMAdapterClass.RecycleViewClickListener(){
            @Override
            public void onClick(View v, int position) {
                String kk = data.get(position).getUom_id();
                String name = data.get(position).getUom_name();
                String status = data.get(position).getUom_status();

                String token = appConfig.getuser_token();
                String userId = appConfig.getuser_id();
                String location = appConfig.getLocation();

                Intent intent = new Intent(getApplicationContext(), Update_StockUom_Activity.class);
                intent.putExtra("id", kk);
                intent.putExtra("token",token);
                intent.putExtra("userId",userId);
                intent.putExtra("location",location);

                intent.putExtra("name", name);
                intent.putExtra("status", status);

                startActivity(intent);
            }
        };
    }
}