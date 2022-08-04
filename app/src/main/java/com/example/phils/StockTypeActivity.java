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
import com.example.phils.Adapter.StockTypeAdapterClass;
import com.example.phils.ResponseModels.ResponseModelStockType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class StockTypeActivity extends AppCompatActivity {
    RecyclerView recview;
    SearchView searchView;

    StockTypeAdapterClass stockTypeAdapterClass;
    List<ResponseModelStockType> data;
    ResponseModelStockType responseModelStockType;
    LinearLayoutManager linearLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_type);

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

        stockTypeAdapterClass = new StockTypeAdapterClass(this,data);
        recview.setAdapter(stockTypeAdapterClass);

        fatchdata();
    }

    private void fatchdata() {

        StringRequest request = new StringRequest(Request.Method.GET, "https://investment-wizards.com/manjeet/Phils_Stock/tbl_stock_type.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            String status;
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
                                    String sn = String.valueOf(j);
                                    String category = object.getString("stock_category_id");

                                    String type = object.getString("stock_type_name");

                                    status = object.getString("stock_type_status");
                                    if(status.equals(String.valueOf(0)))
                                    {
                                        status = "Disable";
                                    }
                                    else
                                    {
                                        status = "Enable";
                                    }

                                    responseModelStockType = new ResponseModelStockType(sn,category,type,status);
                                    data.add(responseModelStockType);
                                    stockTypeAdapterClass.notifyDataSetChanged();

                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(StockTypeActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }


    private void fileList(String newText) {

        List<ResponseModelStockType> modelStockCategories = new ArrayList<>();
        for(ResponseModelStockType responseModelStockType : data)
        {
            if(responseModelStockType.getStock_type_name().toLowerCase(Locale.ROOT).contains(newText.toLowerCase(Locale.ROOT))){
                modelStockCategories.add(responseModelStockType);
            }
        }

        if (modelStockCategories.isEmpty()){
            Toast.makeText(this, "No Data found", Toast.LENGTH_SHORT).show();
        }
        else
        {
            stockTypeAdapterClass.setFilteredList(modelStockCategories);
        }
    }

}