package com.example.phils;

import androidx.appcompat.app.AppCompatActivity;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class StockCategoryActivity extends AppCompatActivity {
    RecyclerView recview;

    StockCategoryAdapterClass stockCategoryAdapterClass;
    List<ResponseModelStockCategory> data;
    ResponseModelStockCategory responseModelStockCategory;
    LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_category);
        recview = findViewById(R.id.recview);
        linearLayoutManager = new LinearLayoutManager(this);
        recview.setLayoutManager(linearLayoutManager);
        data = new ArrayList<>();
        stockCategoryAdapterClass = new StockCategoryAdapterClass(this,data);
        recview.setAdapter(stockCategoryAdapterClass);

        fatchdata();
    }

    private void fatchdata() {
        StringRequest request = new StringRequest(Request.Method.GET, "https://investment-wizards.com/manjeet/Phils_Stock/tbl_stock_category.php",
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
                                    String category = object.getString("stock_category_name");
                                    String cat_group = object.getString("emp_type_name");
                                     status = object.getString("stock_category_status");
                                    if(status.equals(String.valueOf(0)))
                                    {
                                        status = "Disable";
                                    }
                                    else
                                    {
                                        status = "Enable";
                                    }

                                    responseModelStockCategory = new ResponseModelStockCategory(sn,category,status,cat_group);
                                    data.add(responseModelStockCategory);
                                    stockCategoryAdapterClass.notifyDataSetChanged();

                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(StockCategoryActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
}