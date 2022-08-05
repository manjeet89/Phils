package com.example.phils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.phils.Adapter.StockUOMAdapterClass;
import com.example.phils.ResponseModels.ResponseModelStockUOM;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class StockUomActivity extends AppCompatActivity {
    RecyclerView recview;
    SearchView searchView;

    StockUOMAdapterClass stockUOMAdapterClass;
    List<ResponseModelStockUOM> data;
    ResponseModelStockUOM responseModelStockUOM;
    LinearLayoutManager linearLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_uom);

        recview = findViewById(R.id.recview);
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