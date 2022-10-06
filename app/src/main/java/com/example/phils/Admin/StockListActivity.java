package com.example.phils.Admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
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
import com.example.phils.R;
import com.example.phils.ResponseModels.ResponseModelStockList;
import com.example.phils.Shareprefered.AppConfig;
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
    Button logout,location;
    Button btnnotification;
    TextView locationtext;
    Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_list);


        appConfig = new AppConfig(this);
        location_save = findViewById(R.id.location_save);
        String location_save1 = appConfig.getLocation();
        location_save.setText(location_save1);

        locationtext = findViewById(R.id.locationtext);
        locationtext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ProjectLocationActivity.class));
            }
        });
        location_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ProjectLocationActivity.class));

            }
        });

        img = findViewById(R.id.img);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Notification_Activity.class));
            }
        });

//        logout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                appConfig.updateUserLoginStatus(false);
//                startActivity(new Intent(MainActivity.this, LoginActivity.class));
//                finish();
//            }
//        });

        profile = findViewById(R.id.profile);

        //ImageView profile=(ImageView) findViewById(R.id.profile);
        Bitmap mbitmap=((BitmapDrawable) getResources().getDrawable(R.drawable.admin)).getBitmap();
        Bitmap imageRounded=Bitmap.createBitmap(mbitmap.getWidth(), mbitmap.getHeight(), mbitmap.getConfig());
        Canvas canvas=new Canvas(imageRounded);
        Paint mpaint=new Paint();
        mpaint.setAntiAlias(true);
        mpaint.setShader(new BitmapShader(mbitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
        canvas.drawRoundRect((new RectF(0, 0, mbitmap.getWidth(), mbitmap.getHeight())), 100, 100, mpaint); // Round Image Corner 100 100 100 100
        profile.setImageBitmap(imageRounded);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
                //Toast.makeText(MainActivity.this, "desh", Toast.LENGTH_SHORT).show();
                dialog=new Dialog(StockListActivity.this);

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

                ImageView profile  = dialog.findViewById(R.id.profile);
                Bitmap mbitmap=((BitmapDrawable) getResources().getDrawable(R.drawable.admin)).getBitmap();
                Bitmap imageRounded=Bitmap.createBitmap(mbitmap.getWidth(), mbitmap.getHeight(), mbitmap.getConfig());
                Canvas canvas=new Canvas(imageRounded);
                Paint mpaint=new Paint();
                mpaint.setAntiAlias(true);
                mpaint.setShader(new BitmapShader(mbitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
                canvas.drawRoundRect((new RectF(0, 0, mbitmap.getWidth(), mbitmap.getHeight())), 100, 100, mpaint); // Round Image Corner 100 100 100 100
                profile.setImageBitmap(imageRounded);




                Button logout = dialog.findViewById(R.id.logout);
                logout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        appConfig.updateUserLoginStatus(false);
                        startActivity(new Intent(StockListActivity.this,LoginActivity.class));
                        finish();
                    }
                });
                TextView textView = dialog.findViewById(R.id.my_profile);
//                if(1==1)
//                {
//                    textView.setVisibility(View.GONE);
//                }
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


        MaterialToolbar toolbar = findViewById(R.id.topAppbar);
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigation_view);




        if(1==2) {
            Menu menu = navigationView.getMenu();
            MenuItem menuItem = menu.findItem(R.id.ghar);
            menuItem.setVisible(false);
        }





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
                        startActivity(new Intent(getApplicationContext(), StockCategoryActivity.class));
                        break;

                    case R.id.type_stock:
                        startActivity(new Intent(getApplicationContext(), StockTypeActivity.class));
                        break;

                    case R.id.size_stock:
                        startActivity(new Intent(getApplicationContext(), StockSizeActivity.class));
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


                    case R.id.category_job:
                        startActivity(new Intent(getApplicationContext(), Job_Category_Activity.class));
                        break;

                    case R.id.Size_job:
                        startActivity(new Intent(getApplicationContext(), Job_Size_Activity.class));
                        break;

                    case R.id.List_job:
                        startActivity(new Intent(getApplicationContext(), Job_List_Activity.class));
                        break;

                    case R.id.Report_reports:
                        startActivity(new Intent(getApplicationContext(), ReportsActivity.class));
                        break;

                    case R.id.Report_consumption:
                        startActivity(new Intent(getApplicationContext(), ConsumptionActivity.class));
                        break;

                    case R.id.Report_consumption_details:
                        startActivity(new Intent(getApplicationContext(), ConsumptionDetailActivity.class));
                        break;

//                    case R.id.roles:
//                        startActivity(new Intent(getApplicationContext(), RolesAndPrivilegesActivity.class));
//                        break;
//
                    case R.id.resqu_list:
                        startActivity(new Intent(getApplicationContext(), RequisitionListActivity.class));
                        break;
//
                    case R.id.resqu_reviever:
                        startActivity(new Intent(getApplicationContext(), RequisitionReciverList.class));
                        break;



                    default:
                        return true;
                }
                return true;
            }
        });



        recview = findViewById(R.id.recview);
        searchView = findViewById(R.id.search);
        searchView.clearFocus();



        stock_add_button = findViewById(R.id.stock_add);
        stock_add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String token = appConfig.getuser_token();
                String userId = appConfig.getuser_id();
                String location = appConfig.getLocationId();

                Intent intent = new Intent(getApplicationContext(), Add_Stock_List_Activity.class);
                intent.putExtra("token",token);
                intent.putExtra("userId",userId);
                intent.putExtra("location",location);

                startActivity(intent);
                finish();
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
        String location = appConfig.getLocationId();

        StringRequest request = new StringRequest(Request.Method.POST, "https://mployis.com/staging/api/stock/stock_list",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            int j=0;
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
                                if(stock_size_name.equals("null"))
                                {
                                    stock_size_name = " ";
                                }
                                String make_name = object.getString("make_name");
                                if(make_name.equals("null"))
                                {
                                    make_name = " ";
                                }
                                String uom_name = object.getString("uom_name");
                                String stock_category_name = object.getString("stock_category_name");
                                String assign_quantity = object.getString("assign_quantity");
                                if(assign_quantity.equals("null"))
                                {
                                    assign_quantity = " - ";
                                }

                                responseModelStockList = new ResponseModelStockList(sn,stock_id,stock_location_id,stock_category_id,stock_type_id,
                                        stock_size_id,stock_batch_number,stock_invoice_number,stock_distributor_name,stock_make_id,
                                        stock_uom_id,safety_stock,stock_quantity,stock_lot,stock_price,is_stock_transfer,stock_status,stock_type_name,
                                        stock_size_name,make_name,uom_name,stock_category_name,assign_quantity);
                                data.add(responseModelStockList);
                                stockListAdapterClass.notifyDataSetChanged();
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
                String assign_quantity = data.get(position).getAssign_quantity();


                String token = appConfig.getuser_token();
                String userId = appConfig.getuser_id();
                String location = appConfig.getLocationId();

                Dialog  dialog=new Dialog(StockListActivity.this);

                // set custom dialog
                dialog.setContentView(R.layout.custom_stock_list_button);

                // set custom height and width
                dialog.getWindow().setLayout(650,750);

                // set transparent background
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                // show dialog
                dialog.show();
                // Toast.makeText(Notification_Activity.this, "dfn", Toast.LENGTH_SHORT).show();

                Button edit = dialog.findViewById(R.id.stocklistedit);
                Button transfer = dialog.findViewById(R.id.stockTransfer);
                Button delete = dialog.findViewById(R.id.Stockdelete);



                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        progressDialog.setMessage("Please Wait!");
                        StringRequest request = new StringRequest(Request.Method.POST, "https://mployis.com/staging/api/stock/stock_delete",
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        JSONObject jsonObject = null;
                                        try {
                                            jsonObject = new JSONObject(response);
                                            String message = jsonObject.getString("message");
                                            progressDialog.dismiss();
                                            Toast.makeText(StockListActivity.this, message, Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(getApplicationContext(),StockListActivity.class));


                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        //Toast.makeText(Add_Stock_Category_Activity.this, response, Toast.LENGTH_SHORT).show();
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
                            }

                            @Nullable
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String,String> params = new HashMap<String,String>();
                                params.put("stock_id",kk);
                                return  params;
                            }


                        };
                        RequestQueue  requestQueue = Volley.newRequestQueue(StockListActivity.this);
                        requestQueue.add(request);
                    }
                });
                transfer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), TransfarStockListActivity.class);
                        intent.putExtra("id", kk);
                        intent.putExtra("Stock_category_name", Stock_category_name);
                        intent.putExtra("Stock_type_name",Stock_type_name );
                        intent.putExtra("Stock_size_name", Stock_size_name);
                        intent.putExtra("stock_batch_number", stock_batch_number);
                        intent.putExtra("stock_invoice_number", stock_invoice_number);
                        intent.putExtra("stock_distributor_name", stock_distributor_name);
                        intent.putExtra("Make_name", Make_name);
                        intent.putExtra("Uom_name", Uom_name);
                        intent.putExtra("safety_stock", safety_stock);
                        intent.putExtra("stock_quantity", stock_quantity);

                        intent.putExtra("stock_lot", stock_lot);
                        intent.putExtra("stock_price", stock_price);
                        intent.putExtra("assign_quantity",assign_quantity);


                        intent.putExtra("token",token);
                        intent.putExtra("userId",userId);
                        intent.putExtra("location",location);

                        startActivity(intent);
                    }
                });
                edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
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
                });





            }
        };
    }
}