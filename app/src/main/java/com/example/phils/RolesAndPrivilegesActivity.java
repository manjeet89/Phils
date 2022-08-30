package com.example.phils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.phils.Adapter.StockCategoryAdapterClass;
import com.example.phils.Admin.StockCategoryActivity;
import com.example.phils.ResponseModels.ResponseModelStockCategory;
import com.example.phils.Shareprefered.AppConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RolesAndPrivilegesActivity extends AppCompatActivity {

    ProgressDialog progressDialog;
    RecyclerView recview;
    SearchView searchView;
    Button btn;

    RolesAndPrivilegesAdapterClass rolesAndPrivilegesAdapterClass;
    List<ResponseMdelRoleAndPrivileged> data;
    ResponseMdelRoleAndPrivileged responseMdelRoleAndPrivileged;

    LinearLayoutManager linearLayoutManager;
    SwipeRefreshLayout swipe;
    TextView location_save;
    AppConfig appConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roles_and_privileges);

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
        rolesAndPrivilegesAdapterClass = new RolesAndPrivilegesAdapterClass(this,data);
        recview.setAdapter(rolesAndPrivilegesAdapterClass);

        fatchdata();
    }

    private void fileList(String newText) {

        List<ResponseMdelRoleAndPrivileged> modelStockCategories = new ArrayList<>();
        for(ResponseMdelRoleAndPrivileged responseMdelRoleAndPrivileged : data)
        {
            if(responseMdelRoleAndPrivileged.getEmp_type_name().toLowerCase(Locale.ROOT).contains(newText.toLowerCase(Locale.ROOT))){
                modelStockCategories.add(responseMdelRoleAndPrivileged);
            }
        }

        if (modelStockCategories.isEmpty()){
            Toast.makeText(this, "No Data found", Toast.LENGTH_SHORT).show();
        }
        else
        {
            rolesAndPrivilegesAdapterClass.setFilteredList(modelStockCategories);
        }
    }

    private void fatchdata() {

        progressDialog = new ProgressDialog(RolesAndPrivilegesActivity.this);
        progressDialog.setMessage("Loading... Please Wait!");
        progressDialog.show();
        StringRequest request = new StringRequest(Request.Method.GET, "https://investment-wizards.com/manjeet/Phils_Stock/Roles&Privileges.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            String status;
                            int stat = 0;
                            int j=0;
                            String cat_group;
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
                                    String emp_type_id = object.getString("emp_type_id");
                                    String emp_type_name = object.getString("emp_type_name");
                                    String emp_type_created_on = object.getString("emp_type_created_on");



                                    responseMdelRoleAndPrivileged = new ResponseMdelRoleAndPrivileged(sn,emp_type_id,emp_type_name,emp_type_created_on);
                                    data.add(responseMdelRoleAndPrivileged);
                                    rolesAndPrivilegesAdapterClass.notifyDataSetChanged();
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
                Toast.makeText(RolesAndPrivilegesActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

}