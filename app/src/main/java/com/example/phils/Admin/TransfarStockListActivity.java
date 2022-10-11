package com.example.phils.Admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

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
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.phils.R;
import com.example.phils.Shareprefered.AppConfig;
import com.example.phils.Spinner.StockTransferlocation;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TransfarStockListActivity extends AppCompatActivity {

    TextView categorylist,typelist,sizelist,batchlist,invoicelist,distributorlist,makelist,uomlist,safetylist,quantitylist,lotsize,pricelist,assignquantitylist,
            quantityinsert,
            changestats,
            setlocation;
    EditText assignquanity;

    Button button;
    AppConfig appConfig;
    ArrayList<StockTransferlocation> locationstatus = new ArrayList<>();
    ArrayAdapter<StockTransferlocation> stockTransferlocationArrayAdapter;

    Dialog dialog;
    ProgressDialog progressDialog;


    TextView location_save;
    Button logout,location;
    Button btnnotification;
    ImageView img,profile;
    TextView locationtext;


    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(),StockListActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfar_stock_list);




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
                dialog=new Dialog(TransfarStockListActivity.this);

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
                        startActivity(new Intent(TransfarStockListActivity.this,LoginActivity.class));
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


        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Please Wait");
        progressDialog.show();


        categorylist = findViewById(R.id.categorylist);
        typelist = findViewById(R.id.typelist);
        sizelist = findViewById(R.id.sizelist);
        batchlist = findViewById(R.id.batchlist);
        invoicelist = findViewById(R.id.invoicelist);
        distributorlist = findViewById(R.id.distributorlist);
        makelist = findViewById(R.id.makelist);
        uomlist = findViewById(R.id.uomlist);
        safetylist = findViewById(R.id.safetylist);
        quantitylist = findViewById(R.id.quantitylist);
        lotsize = findViewById(R.id.lotsize);
        pricelist = findViewById(R.id.pricelist);
        assignquantitylist = findViewById(R.id.assignquantitylist);

        quantityinsert = findViewById(R.id.quantityinsert);

        assignquanity = findViewById(R.id.assignquanity);

        changestats = findViewById(R.id.changestats);

        setlocation = findViewById(R.id.setlocation);

        button = findViewById(R.id.btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String quantity = assignquanity.getText().toString();
                String location = setlocation.getText().toString();
                if (TextUtils.isEmpty(quantity)) {
                    assignquanity.setError("Please Enter Quantity");
                    Toast.makeText(TransfarStockListActivity.this, "Please Enter Quantity", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(location)) {
                    setlocation.setError("Please Select location");
                    Toast.makeText(TransfarStockListActivity.this, "Please Select location", Toast.LENGTH_SHORT).show();
                    return;
                }
                else
                {

                    InsertData();
                }
            }
        });

        String stockId = getIntent().getStringExtra("id");
        String Stock_category_name = getIntent().getStringExtra("Stock_category_name");
        String Stock_type_name = getIntent().getStringExtra("Stock_type_name");
        String Stock_size_name = getIntent().getStringExtra("Stock_size_name");

        String stock_batch_number = getIntent().getStringExtra("stock_batch_number");
        String Make_name = getIntent().getStringExtra("Make_name");
        String Uom_name = getIntent().getStringExtra("Uom_name");
        String assign_quantity = getIntent().getStringExtra("assign_quantity");

        categorylist.setText(Stock_category_name);
        typelist.setText(Stock_type_name);
        sizelist.setText(Stock_size_name);
        batchlist.setText(stock_batch_number);
        makelist.setText(Make_name);
        uomlist.setText(Uom_name);
        assignquantitylist.setText(assign_quantity);

        String locationId = appConfig.getLocationId();
        setlocation.setText(locationId);
        String locationName = appConfig.getLocation();
        changestats.setText(locationName);

        String token = getIntent().getStringExtra("token");
        String userId = getIntent().getStringExtra("userId");
        String location = getIntent().getStringExtra("location");
        String user_employee_type = appConfig.getuser_employee_type();

        StringRequest request = new StringRequest(Request.Method.POST, "https://mployis.com/staging/api/stock/transfer_stock_details",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            String stock_category_status;
                            String emp_type_name;

                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("message");

                            //Toast.makeText(TransfarStockListActivity.this, message, Toast.LENGTH_SHORT).show();
                            if(message.equals("Invalid user request")){
                                Toast.makeText(TransfarStockListActivity.this, message, Toast.LENGTH_SHORT).show();
                                appConfig.updateUserLoginStatus(false);
                                startActivity(new Intent(TransfarStockListActivity.this, LoginActivity.class));
                                finish();
                            }
                            else {

                                String data = jsonObject.getString("data");
                                JSONObject jsonObject1 = new JSONObject(data);

                                String stock_invoice_number = jsonObject1.getString("stock_invoice_number");
                                String stock_distributor_name = jsonObject1.getString("stock_distributor_name");
                                String safety_stock = jsonObject1.getString("safety_stock");
                                String stock_quantity = jsonObject1.getString("stock_quantity");
                                String stock_lot = jsonObject1.getString("stock_lot");
                                String stock_price = jsonObject1.getString("stock_price");

                                invoicelist.setText(stock_invoice_number);
                                distributorlist.setText(stock_distributor_name);
                                safetylist.setText(safety_stock);
                                quantitylist.setText(stock_quantity);
                                lotsize.setText(stock_lot);
                                pricelist.setText(stock_price);

                                quantityinsert.setText(stock_quantity);

                                //progressDialog.dismiss();
                            }

                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(TransfarStockListActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap headers = new HashMap();
                headers.put("user_token",token);
                headers.put("user_id", userId);
                headers.put("project_location_id", location);
                headers.put("user_employee_type", user_employee_type);

                return headers;
            }

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                params.put("stock_id",stockId);
                return  params;
            }


        };
        RequestQueue requestQueue = Volley.newRequestQueue(TransfarStockListActivity.this);
        requestQueue.add(request);

        StringRequest request1 = new StringRequest(Request.Method.POST, "https://mployis.com/staging/api/stock/stock_transfer_location",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            int j=0;
                            String stock_category_status;
                            String emp_type_name;

                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("message");

                            if(message.equals("Invalid user request")){
                                Toast.makeText(TransfarStockListActivity.this, message, Toast.LENGTH_SHORT).show();
                                appConfig.updateUserLoginStatus(false);
                                startActivity(new Intent(TransfarStockListActivity.this,LoginActivity.class));
                                finish();
                            }
                            else {

                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    j++;
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String location_id = object.getString("location_id");
                                    String location_name = object.getString("location_name");

                                    locationstatus.add(new StockTransferlocation(location_id,location_name));
                                    stockTransferlocationArrayAdapter = new ArrayAdapter<StockTransferlocation>(TransfarStockListActivity.this,
                                            android.R.layout.simple_spinner_dropdown_item,locationstatus);
                                    stockTransferlocationArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    progressDialog.dismiss();

                                }
                            }

                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(TransfarStockListActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap headers = new HashMap();
                headers.put("user_token",token);
                headers.put("user_id", userId);
                headers.put("project_location_id", location);
                headers.put("user_employee_type", user_employee_type);

                return headers;
                //return super.getHeaders();
            }
        };

        RequestQueue requestQueue1 = Volley.newRequestQueue(TransfarStockListActivity.this);
        requestQueue1.add(request1);

        changestats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new Dialog(TransfarStockListActivity.this);

                // set custom dialog
                dialog.setContentView(R.layout.dialog_searchable_spinner_stock_type);

                // set custom height and width
                dialog.getWindow().setLayout(950, 1000);

                // set transparent background
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                // show dialog
                dialog.show();

                // Initialize and assign variable
                EditText editText = dialog.findViewById(R.id.edit_text);
                ListView listView = dialog.findViewById(R.id.list_view);

                listView.setAdapter(stockTransferlocationArrayAdapter);
                editText.addTextChangedListener(new TextWatcher() {
                                                    @Override
                                                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                                    }

                                                    @Override
                                                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                                        stockTransferlocationArrayAdapter.getFilter().filter(charSequence);
                                                    }

                                                    @Override
                                                    public void afterTextChanged(Editable editable) {

                                                    }

                                                }

                );


                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        // when item selected from list
                        // set selected item on textView

                        //  select_type.setText(spinnerArrayAdapter.getItem(position));

                        StockTransferlocation spn = (StockTransferlocation) parent.getItemAtPosition(position);
                        changestats.setText(spn.location_name);
                        setlocation.setText(spn.location_id);
                        //Toast.makeText(Add_Stock_Type_Activity.this, spn.stock_category_id, Toast.LENGTH_SHORT).show();

                        dialog.dismiss();
                    }
                });
            }
        });

    }

    private void InsertData() {


         String checkQuantity = quantityinsert.getText().toString();
         //if(checkQuantity.equals(assignquanity.getText().toString())) {
             if(Integer.parseInt(checkQuantity) >= Integer.parseInt(assignquanity.getText().toString()))
             {

            // }
             progressDialog = new ProgressDialog(this);
             progressDialog.setMessage("Please Wait");
             progressDialog.show();

             String token = getIntent().getStringExtra("token");
             String userId = getIntent().getStringExtra("userId");
             String location = getIntent().getStringExtra("location");
             String id = getIntent().getStringExtra("id");
                 String user_employee_type = appConfig.getuser_employee_type();

             String quantity = assignquanity.getText().toString();
             String locationid = setlocation.getText().toString();
             //if(Integer.parseInt(quantity) >=Integer.parseInt(quantityinsert.getText().toString()))


             StringRequest request = new StringRequest(Request.Method.POST, "https://mployis.com/staging/api/stock/transfer_stock",
                     new Response.Listener<String>() {
                         @Override
                         public void onResponse(String response) {
                             JSONObject jsonObject = null;
                             try {
                                 jsonObject = new JSONObject(response);
                                 String message = jsonObject.getString("message");

                                 Toast.makeText(TransfarStockListActivity.this, message, Toast.LENGTH_SHORT).show();

                                 startActivity(new Intent(getApplicationContext(), StockListActivity.class));
                                 progressDialog.dismiss();

                             } catch (JSONException e) {
                                 e.printStackTrace();
                             }
                             //Toast.makeText(Add_Stock_Category_Activity.this, response, Toast.LENGTH_SHORT).show();
                         }
                     }, new Response.ErrorListener() {
                 @Override
                 public void onErrorResponse(VolleyError error) {
                     Toast.makeText(TransfarStockListActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

                 }
             }) {
                 @Override
                 public Map<String, String> getHeaders() throws AuthFailureError {
                     HashMap headers = new HashMap();
                     headers.put("user_token", token);
                     headers.put("user_id", userId);
                     headers.put("project_location_id", location);
                     headers.put("user_employee_type", user_employee_type);

                     return headers;
                 }

                 @Nullable
                 @Override
                 protected Map<String, String> getParams() throws AuthFailureError {
                     Map<String, String> params = new HashMap<String, String>();
                     params.put("stock_location_id", locationid);
                     params.put("stock_quantity", quantity);
                     params.put("stock_id", id);
                     return params;
                 }


             };
             RequestQueue requestQueue = Volley.newRequestQueue(TransfarStockListActivity.this);
             requestQueue.add(request);
         }
         else
         {
             Toast.makeText(this, "The Stock quantity field must contain a number less than or equal to"+checkQuantity+".", Toast.LENGTH_SHORT).show();
         }
    }
}