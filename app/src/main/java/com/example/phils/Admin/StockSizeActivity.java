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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.phils.Adapter.StockSizeAdapterClass;
import com.example.phils.Demo;
import com.example.phils.R;
import com.example.phils.ResponseModels.ResponseModelStockSize;
import com.example.phils.Shareprefered.AppConfig;
import com.example.phils.UserActivity;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class StockSizeActivity extends AppCompatActivity {
    ProgressDialog progressDialog;

    RecyclerView recview;
    SearchView searchView;

    StockSizeAdapterClass stockSizeAdapterClass;
    List<ResponseModelStockSize> data;
    ResponseModelStockSize responseModelStockSize;
    LinearLayoutManager linearLayoutManager;
    Button button;
    TextView location_save;
    AppConfig appConfig;

    private StockSizeAdapterClass.RecycleViewClickListener listener;

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_size);

        appConfig = new AppConfig(this);
        location_save = findViewById(R.id.location_save);
        String location_save1 = appConfig.getLocation();
        location_save.setText(location_save1);

        recview = findViewById(R.id.recview);
        searchView = findViewById(R.id.search);
        searchView.clearFocus();
        button = findViewById(R.id.add_size);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Add_Stock_Size_Activity.class));
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
                        startActivity(new Intent(getApplicationContext(), StockCategoryActivity.class));
                        break;

                    case R.id.type_stock:
                        startActivity(new Intent(getApplicationContext(), StockTypeActivity.class));
                        break;

                    case R.id.size_stock:
                        startActivity(new Intent(getApplicationContext(),StockSizeActivity.class));
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
        stockSizeAdapterClass = new StockSizeAdapterClass(listener,data);
        recview.setAdapter(stockSizeAdapterClass);


    }

    private void fileList(String newText) {

        List<ResponseModelStockSize> modelStockCategories = new ArrayList<>();
        for(ResponseModelStockSize responseModelStockSize : data)
        {
            if(responseModelStockSize.getStock_size_name().toLowerCase(Locale.ROOT).contains(newText.toLowerCase(Locale.ROOT))){
                modelStockCategories.add(responseModelStockSize);
            }
        }

        if (modelStockCategories.isEmpty()){
            Toast.makeText(this, "No Data found", Toast.LENGTH_SHORT).show();
        }
        else
        {
            stockSizeAdapterClass.setFilteredList(modelStockCategories);
        }
    }


    private void fatchdata() {
        progressDialog = new ProgressDialog(StockSizeActivity.this);
        progressDialog.setTitle("Stock Size");
        progressDialog.setMessage("Loading... Please Wait!");
        progressDialog.setIcon(R.drawable.ic_baseline_autorenew_24);
        progressDialog.show();
        StringRequest request = new StringRequest(Request.Method.GET, "https://investment-wizards.com/manjeet/Phils_Stock/tbl_stock_size.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            String stock_size_status;
                            int stat = 0;
                            int j=0;
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            if(success.equals("1"))
                            {
                                for(int i=0;i<jsonArray.length();i++)
                                {
                                    j++;
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String sn = String.valueOf(j);
                                    String stock_size_id = object.getString("stock_size_id");
                                    String stock_category_name = object.getString("stock_category_name");
                                    String stock_type_name = object.getString("stock_type_name");
                                    String stock_size_name = object.getString("stock_size_name");
                                    stock_size_status = object.getString("stock_size_status");

                                    if(stock_size_status.equals(String.valueOf(0)))
                                    {
                                        stock_size_status = "Disable";
                                    }
                                    else
                                    {
                                        stock_size_status = "Enable";
                                    }

                                    responseModelStockSize = new ResponseModelStockSize(sn,stock_category_name,stock_type_name,stock_size_name,stock_size_status,stock_size_id);
                                    data.add(responseModelStockSize);
                                    stockSizeAdapterClass.notifyDataSetChanged();
                                    progressDialog.dismiss();

                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(StockSizeActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    private void recycleClickLister() {
        listener = new StockSizeAdapterClass.RecycleViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                String kk = data.get(position).getStock_size_id();
                Intent intent = new Intent(getApplicationContext(), Demo.class);
                intent.putExtra("username",kk);
                startActivity(intent);
                //Toast.makeText(UserActivity.this, kk, Toast.LENGTH_SHORT).show();
            }
        };
    }

}