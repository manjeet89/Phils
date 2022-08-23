package com.example.phils;

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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.phils.Adapter.StockUOMAdapterClass;
import com.example.phils.ResponseModels.ResponseModelStockUOM;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class StockUomActivity extends AppCompatActivity {
    ProgressDialog progressDialog;

    RecyclerView recview;
    SearchView searchView;

    Button uom;
    StockUOMAdapterClass stockUOMAdapterClass;
    List<ResponseModelStockUOM> data;
    ResponseModelStockUOM responseModelStockUOM;
    LinearLayoutManager linearLayoutManager;



    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_uom);

        recview = findViewById(R.id.recview);
        searchView = findViewById(R.id.search);
        searchView.clearFocus();

        uom = findViewById(R.id.uom);
        uom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Add_Stock_UOM_Activity.class));
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
                        startActivity(new Intent(getApplicationContext(),UserActivity.class));
                        break;

                    case R.id.category_stock:
                        startActivity(new Intent(getApplicationContext(),StockCategoryActivity.class));
                        break;

                    case R.id.type_stock:
                        startActivity(new Intent(getApplicationContext(),StockTypeActivity.class));
                        break;

                    case R.id.size_stock:
                        startActivity(new Intent(getApplicationContext(),StockSizeActivity.class));
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

        linearLayoutManager = new LinearLayoutManager(this);
        recview.setLayoutManager(linearLayoutManager);

        data = new ArrayList<>();
        stockUOMAdapterClass = new StockUOMAdapterClass(this,data);
        recview.setAdapter(stockUOMAdapterClass);

        fatchdata();
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
        progressDialog.setIcon(R.drawable.ic_baseline_autorenew_24);
        progressDialog.show();
        StringRequest request = new StringRequest(Request.Method.GET, "https://investment-wizards.com/manjeet/Phils_Stock/tbl_stock_uom.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            String uom_status;
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
//                                    String sn = object.getString("stock_category_id");
                                    String uom_id = String.valueOf(j);
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

                                    responseModelStockUOM = new ResponseModelStockUOM(uom_id,uom_name,uom_status);
                                    data.add(responseModelStockUOM);
                                    stockUOMAdapterClass.notifyDataSetChanged();
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
                Toast.makeText(StockUomActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
}