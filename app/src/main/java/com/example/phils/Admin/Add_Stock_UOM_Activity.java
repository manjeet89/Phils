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
import android.util.Log;
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
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Add_Stock_UOM_Activity extends AppCompatActivity {

    EditText uom_name_add;
    TextView stock_size_status;
    ArrayList<String> arrayList1;
    Dialog dialog;
    Button btn;
    TextView location_save;
    AppConfig appConfig;
    ProgressDialog progressDialog;
    ImageView img,profile;


    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), StockUomActivity.class));
    }
    Button logout,location;
    Button btnnotification;

    TextView locationtext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stock_uom);


        uom_name_add = findViewById(R.id.uom_name_add);
        stock_size_status= findViewById(R.id.stock_size_status);
        btn = findViewById(R.id.uom_insert);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Insert();
            }
        });


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
                dialog=new Dialog(Add_Stock_UOM_Activity.this);

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
                        startActivity(new Intent(Add_Stock_UOM_Activity.this,LoginActivity.class));
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



        String access_module = appConfig.getaccess_module().trim();
//        String access_module = getIntent().getStringExtra("access_module");
//
        String text = access_module.toString().replace("[", "").replace("]", "");
        String withoutQuotes_line1 = text.replace("\"", "");
        // Log.d("mekya",withoutQuotes_line1);
        String [] items = withoutQuotes_line1.split("\\s*,\\s*");

        String userlist="",stock="",stockcategorylist="",stocktypelist="",stocksizelist="",stockmakelist="",stockuomlist="",stocklist="";
        String job="",jobcategorylist="",jobsizelist="",joblist="";
        String requisition="",requisitionlist="",receiverlist="";
        for (int i =0;i<items.length;i++) {

            Log.d("itemshar",items[i]);

            //Stock
            if (items[i].equals("user-list")) { userlist = "user-list"; }
            if (items[i].equals("stock")) { stock = "stock"; }
            if (items[i].equals("stock-category-list")) { stockcategorylist = "stock-category-list"; }
            if (items[i].equals("stock-type-list")) { stocktypelist = "stock-type-list"; }
            if (items[i].equals("stock-size-list")) { stocksizelist = "stock-size-list"; }
            if (items[i].equals("stock-make-list")) { stockmakelist = "stock-make-list"; }
            if (items[i].equals("stock-uom-list")) { stockuomlist = "stock-uom-list"; }
            if (items[i].equals("stock-list")) { stocklist = "stock-list"; }

            //job
            if (items[i].equals("job")) { job = "job"; }
            if (items[i].equals("job-category-list")) { jobcategorylist = "job-category-list"; }
            if (items[i].equals("job-size-list")) { jobsizelist = "job-size-list"; }
            if (items[i].equals("job-list")) { joblist = "job-list"; }

            //Requisition
            if (items[i].equals("requisition")) { requisition = "requisition"; }
            if (items[i].equals("requisition-list")) { requisitionlist = "requisition-list"; }
            if (items[i].equals("receiver-list")) { receiverlist = "receiver-list"; }
        }
        Menu menu = navigationView.getMenu();

        //Requisition
        if(requisition.equals("requisition")){
            MenuItem menuItem = menu.findItem(R.id.requisitionnav);
            menuItem.setVisible(true);
        }
        else
        {
            MenuItem menuItem = menu.findItem(R.id.requisitionnav);
            menuItem.setVisible(false);
        }
//
        if(requisitionlist.equals("requisition-list")){
            MenuItem menuItem = menu.findItem(R.id.resqu_list);
            menuItem.setVisible(true);
        }else
        {
            MenuItem menuItem = menu.findItem(R.id.resqu_list);
            menuItem.setVisible(false);
        }

        if(receiverlist.equals("receiver-list")){
            MenuItem menuItem = menu.findItem(R.id.resqu_reviever);
            menuItem.setVisible(true);
        }else
        {
            MenuItem menuItem = menu.findItem(R.id.resqu_reviever);
            menuItem.setVisible(false);
        }



        //Job Portion

        if(job.equals("job")){
            MenuItem menuItem = menu.findItem(R.id.jobnav);
            menuItem.setVisible(true);
        }
        else
        {
            MenuItem menuItem = menu.findItem(R.id.jobnav);
            menuItem.setVisible(false);
        }
//
        if(jobcategorylist.equals("job-category-list")){
            MenuItem menuItem = menu.findItem(R.id.category_job);
            menuItem.setVisible(true);
        }else
        {
            MenuItem menuItem = menu.findItem(R.id.category_job);
            menuItem.setVisible(false);
        }

        if(jobsizelist.equals("job-size-list")){
            MenuItem menuItem = menu.findItem(R.id.Size_job);
            menuItem.setVisible(true);
        }else
        {
            MenuItem menuItem = menu.findItem(R.id.Size_job);
            menuItem.setVisible(false);
        }

        if(joblist.equals("job-list")){
            MenuItem menuItem = menu.findItem(R.id.List_job);
            menuItem.setVisible(true);
        }else
        {
            MenuItem menuItem = menu.findItem(R.id.List_job);
            menuItem.setVisible(false);
        }


        //Stock portion
        if (userlist.equals("user-list")) {
            MenuItem menuItem = menu.findItem(R.id.user);
            menuItem.setVisible(true);
        }
        else
        {
            MenuItem menuItem = menu.findItem(R.id.user);
            menuItem.setVisible(false);
        }

        if (stock.equals("stock")) {
            MenuItem menuItem = menu.findItem(R.id.stockidnav);
            menuItem.setVisible(true);
        }
        else
        {
            MenuItem menuItem = menu.findItem(R.id.stockidnav);
            menuItem.setVisible(false);
        }

        if(stockcategorylist.equals("stock-category-list")){
            MenuItem menuItem = menu.findItem(R.id.category_stock);
            menuItem.setVisible(true);
        }else
        {
            MenuItem menuItem = menu.findItem(R.id.category_stock);
            menuItem.setVisible(false);
        }

        if(stocktypelist.equals("stock-type-list")){
            MenuItem menuItem = menu.findItem(R.id.type_stock);
            menuItem.setVisible(true);
        }else
        {
            MenuItem menuItem = menu.findItem(R.id.type_stock);
            menuItem.setVisible(false);
        }

        if(stocksizelist.equals("stock-size-list")){
            MenuItem menuItem = menu.findItem(R.id.size_stock);
            menuItem.setVisible(true);
        }else
        {
            MenuItem menuItem = menu.findItem(R.id.size_stock);
            menuItem.setVisible(false);
        }

        if(stockmakelist.equals("stock-make-list")){
            MenuItem menuItem = menu.findItem(R.id.make_stock);
            menuItem.setVisible(true);
        }else
        {
            MenuItem menuItem = menu.findItem(R.id.make_stock);
            menuItem.setVisible(false);
        }

        if(stockuomlist.equals("stock-uom-list")){
            MenuItem menuItem = menu.findItem(R.id.umo_stock);
            menuItem.setVisible(true);
        }else
        {
            MenuItem menuItem = menu.findItem(R.id.umo_stock);
            menuItem.setVisible(false);
        }

        if(stocklist.equals("stock-list")){
            MenuItem menuItem = menu.findItem(R.id.list_stock);
            menuItem.setVisible(true);
        }else
        {
            MenuItem menuItem = menu.findItem(R.id.list_stock);
            menuItem.setVisible(false);
        }






        if(1==2) {
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


        arrayList1=new ArrayList<>();
        arrayList1.add("Enable");
        arrayList1.add("Disable");

        stock_size_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Initialize dialog
                dialog=new Dialog(Add_Stock_UOM_Activity.this);

                // set custom dialog
                dialog.setContentView(R.layout.dialog_searchable_spinner_status);

                // set custom height and width
                dialog.getWindow().setLayout(650,800);

                // set transparent background
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                // show dialog
                dialog.show();

                // Initialize and assign variable
                EditText editText1=dialog.findViewById(R.id.edit_text1);
                ListView listViewstatus=dialog.findViewById(R.id.list_view_status);

                ArrayAdapter<String> adapter=new ArrayAdapter<>(Add_Stock_UOM_Activity.this, android.R.layout.simple_list_item_1,arrayList1);
                listViewstatus.setAdapter(adapter);
                editText1.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        adapter.getFilter().filter(charSequence);

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });
                listViewstatus.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        stock_size_status.setText(adapter.getItem(i));

                        // Dismiss dialog
                        dialog.dismiss();
                    }
                });

            }
        });
        // end Status spinner
    }
    private void Insert() {

        appConfig = new AppConfig(this);

        String e1 =  uom_name_add.getText().toString().toUpperCase(Locale.ROOT).trim();
        String e3 = stock_size_status.getText().toString().trim();
        if(TextUtils.isEmpty(e1))
        {
            uom_name_add.setError("Enter uom Name");
            Toast.makeText(Add_Stock_UOM_Activity.this, "Enter UOM Name", Toast.LENGTH_SHORT).show();
            return;
        }

        else if(TextUtils.isEmpty(e3))
        {
            Toast.makeText(Add_Stock_UOM_Activity.this, "Please Select your Status", Toast.LENGTH_SHORT).show();
            return;
        }
        else
        {

            progressDialog = new ProgressDialog(Add_Stock_UOM_Activity.this);
            progressDialog.setTitle("Stock Make");
            progressDialog.setMessage("Loading... Please Wait!");
            progressDialog.show();

            if(e3.equals("Enable"))
            {
                e3 = "1";
            }
            else
            {
                e3 = "0";
            }

            String e4 =  e1.toUpperCase(Locale.ROOT);
            String e6 = e3;



            String token = getIntent().getStringExtra("token");
            String userId = getIntent().getStringExtra("userId");
            String location = getIntent().getStringExtra("location");
            String user_employee_type = appConfig.getuser_employee_type();

            StringRequest request = new StringRequest(Request.Method.POST, "https://erp.philsengg.com/api/stock/add_stock_uom",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(response);
                                String message = jsonObject.getString("message");

                                Toast.makeText(Add_Stock_UOM_Activity.this, message, Toast.LENGTH_SHORT).show();

                                startActivity(new Intent(getApplicationContext(),StockUomActivity.class));
                                progressDialog.dismiss();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            //Toast.makeText(Add_Stock_Category_Activity.this, response, Toast.LENGTH_SHORT).show();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Add_Stock_UOM_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

                }
            })
            {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap headers = new HashMap();
                    headers.put("Usertoken",token);
                    headers.put("Userid", userId);
                    headers.put("Projectlocationid", location);
                    headers.put("Useremployeetype", user_employee_type);

                    return headers;
                }

                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<String,String>();
                    params.put("uom_name",e4);
                    params.put("uom_status",e6);
                    return  params;
                }


            };
            RequestQueue  requestQueue = Volley.newRequestQueue(Add_Stock_UOM_Activity.this);
            requestQueue.add(request);

//            StringRequest request = new StringRequest(Request.Method.POST, "https://investment-wizards.com/manjeet/Phils_Stock/insert_category/add_stock_uom.php",
//                    new Response.Listener<String>() {
//                        @Override
//                        public void onResponse(String response) {
//                            Toast.makeText(Add_Stock_UOM_Activity.this, response, Toast.LENGTH_SHORT).show();
//                        }
//                    }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    Toast.makeText(Add_Stock_UOM_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
//
//                }
//            })
//            {
//                @Nullable
//                @Override
//                protected Map<String, String> getParams() throws AuthFailureError {
//                    Map<String,String> params = new HashMap<String,String>();
//                    params.put("uom_name",e4);
//                    params.put("uom_status",e6);
//                    return  params;
//                }
//            };
//            RequestQueue requestQueue = Volley.newRequestQueue(Add_Stock_UOM_Activity.this);
//            requestQueue.add(request);

        }
    }
}