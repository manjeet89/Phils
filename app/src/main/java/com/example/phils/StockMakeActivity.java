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
import com.example.phils.Adapter.StockMakeAdapterClass;
import com.example.phils.ResponseModels.ResponseModelStockMake;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class StockMakeActivity extends AppCompatActivity {
    RecyclerView recview;
    SearchView searchView;

    StockMakeAdapterClass stockMakeAdapterClass;
    List<ResponseModelStockMake> data;
    ResponseModelStockMake responseModelStockMake;
    LinearLayoutManager linearLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_make);

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

        stockMakeAdapterClass = new StockMakeAdapterClass(this,data);
        recview.setAdapter(stockMakeAdapterClass);

        fatchdata();
    }

    private void fileList(String newText) {

        List<ResponseModelStockMake> modelStockCategories = new ArrayList<>();
        for(ResponseModelStockMake responseModelStockMake : data)
        {
            if(responseModelStockMake.getMake_name().toLowerCase(Locale.ROOT).contains(newText.toLowerCase(Locale.ROOT))){
                modelStockCategories.add(responseModelStockMake);
            }
        }

        if (modelStockCategories.isEmpty()){
            Toast.makeText(this, "No Data found", Toast.LENGTH_SHORT).show();
        }
        else
        {
            stockMakeAdapterClass.setFilteredList(modelStockCategories);
        }
    }

    private void fatchdata() {

        StringRequest request = new StringRequest(Request.Method.GET, "https://investment-wizards.com/manjeet/Phils_Stock/tbl_stock_make.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            String make_status;
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
                                    String make_id = String.valueOf(j);
                                    String make_name = object.getString("make_name");

                                    make_status = object.getString("make_status");
                                    if(make_status.equals(String.valueOf(0)))
                                    {
                                        make_status = "Disable";
                                    }
                                    else
                                    {
                                        make_status = "Enable";
                                    }

                                    responseModelStockMake = new ResponseModelStockMake(make_id,make_name,make_status);
                                    data.add(responseModelStockMake);
                                    stockMakeAdapterClass.notifyDataSetChanged();

                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(StockMakeActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
}