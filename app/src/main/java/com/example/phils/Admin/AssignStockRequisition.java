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
import com.example.phils.Adapter.AssignRequisitionAdapterClass;
import com.example.phils.Spinner.GrinderWelderListSpinner;
import com.example.phils.R;
import com.example.phils.ResponseModels.ResponseModelAssignRequisition;
import com.example.phils.Shareprefered.AppConfig;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AssignStockRequisition extends AppCompatActivity {

    TextView category,type,size,quantity,reqQuantity,changestats;

    TextView setreq_id,setreciverid,assignrecivedby,assignquantity,setmaxlot;
    AppConfig appConfig;

    ProgressDialog progressDialog;
    RecyclerView recview;
    SearchView searchView;
    LinearLayoutManager linearLayoutManager;
    List<ResponseModelAssignRequisition> data;
    ResponseModelAssignRequisition responseModelAssignRequisition;
    AssignRequisitionAdapterClass assignRequisitionAdapterClass;
    private AssignRequisitionAdapterClass.RecycleViewClickListener listener;
    private AssignRequisitionAdapterClass.ItemClickListener  itemClickListener;

    ArrayList<GrinderWelderListSpinner> grinderWelderListSpinners = new ArrayList<>();
    ArrayAdapter<GrinderWelderListSpinner> grinderWelderListSpinnerArrayAdapter;

    TextView location_save;
    Button logout,location;
    Button btnnotification;
    ImageView img,profile;
    TextView locationtext;
    Dialog dialog;

    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_stock_requisition);




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
                dialog=new Dialog(AssignStockRequisition.this);

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
                        startActivity(new Intent(AssignStockRequisition.this,LoginActivity.class));
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


        String id = getIntent().getStringExtra("id");
        String changecategory = getIntent().getStringExtra("stockcategory");
        String changetype = getIntent().getStringExtra("stocktype");
        String changesize = getIntent().getStringExtra("stocksize");
        String changequantity = getIntent().getStringExtra("quantityAssign");
        String changereqquantity = getIntent().getStringExtra("quantityreq");
        String changesatusprint = getIntent().getStringExtra("managerstatus");


        category = findViewById(R.id.category);
        type = findViewById(R.id.type);
        size = findViewById(R.id.size);
        quantity = findViewById(R.id.quantity);
        reqQuantity = findViewById(R.id.reqQuantity);
        changestats = findViewById(R.id.changestats);

        btn = findViewById( R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressDialog.setMessage("Please Wait");
                progressDialog.show();

               String s =  setreq_id.getText().toString();
               String p = setreciverid.getText().toString();
               String ass = quantity.getText().toString();
               String max = reqQuantity.getText().toString();
               String quantity = assignquantity.getText().toString();

               int Lot = Integer.parseInt(max) - Integer.parseInt(changequantity);
                //Toast.makeText(AssignStockRequisition.this, String.valueOf(Lot), Toast.LENGTH_SHORT).show();

                if (TextUtils.isEmpty(s)) {
                    setreq_id.setError("Please Select Available Stock");
                    Toast.makeText(AssignStockRequisition.this, "Please Select Available Stock", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(p)) {
                    setreciverid.setError("Please Select User");
                    Toast.makeText(AssignStockRequisition.this, "Please Select User", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(TextUtils.isEmpty(ass)){
                    setreciverid.setError("Please Enter Quantity");
                    Toast.makeText(AssignStockRequisition.this, "Please Enter Quantity", Toast.LENGTH_SHORT).show();

                }
                else {
                       if (Lot >= Integer.parseInt(quantity)) {
                        String token = appConfig.getuser_token();
                        String userId = appConfig.getuser_id();
                        String location = appConfig.getLocationId();
                        String user_employee_type = appConfig.getuser_employee_type();

                        //Toast.makeText(AssignStockRequisition.this, s+"/"+id+"/"+p+"/"+ass, Toast.LENGTH_SHORT).show();
                        StringRequest request = new StringRequest(Request.Method.POST, "https://erp.philsengg.com/api/requisition/requistion_assign_stock",
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        JSONObject jsonObject = null;
                                        try {
                                            jsonObject = new JSONObject(response);
                                            String message = jsonObject.getString("message");

                                            Toast.makeText(AssignStockRequisition.this, message, Toast.LENGTH_SHORT).show();

                                            startActivity(new Intent(getApplicationContext(), RequisitionListActivity.class));
                                            progressDialog.dismiss();

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        //Toast.makeText(Add_Stock_Category_Activity.this, response, Toast.LENGTH_SHORT).show();
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(AssignStockRequisition.this, error.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        }) {
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
                                Map<String, String> params = new HashMap<String, String>();
                                params.put("req_id", id);
                                params.put("assign_stock_id", s);
                                params.put("req_quantity", quantity);
                                params.put("receiver_id", p);

                                return params;
                            }


                        };
                        RequestQueue requestQueue = Volley.newRequestQueue(AssignStockRequisition.this);
                        requestQueue.add(request);
                    } else {
                        Toast.makeText(AssignStockRequisition.this, "The quantity field must contain a number less than or equal to  " + String.valueOf(Lot), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }

            }
        });
        setreq_id = findViewById(R.id.setreq_id);
        setreciverid = findViewById(R.id.setreciverid);
        setmaxlot = findViewById(R.id.setmaxlot);

        category.setText(changecategory);
        type.setText(changetype);
        size.setText(changesize);
        quantity.setText(changequantity);
        reqQuantity.setText(changereqquantity);

        recview = findViewById(R.id.recview);

        assignrecivedby = findViewById(R.id.assignrecivedby);
        assignquantity = findViewById(R.id.assignquantity);

//        searchView = findViewById(R.id.search);
//        searchView.clearFocus();
//
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//               // fileList(newText);
//                return true;
//            }
//        });

        fatchdata();
        recycleClickLister();
        linearLayoutManager = new LinearLayoutManager(this);
        data = new ArrayList<>();

        itemClickListener = new AssignRequisitionAdapterClass.ItemClickListener() {
            @Override
            public void onClick(String s) {
                recview.post(new Runnable() {
                    @Override
                    public void run() {
                        assignRequisitionAdapterClass.notifyDataSetChanged();;
                    }
                });
//                Toast
//                        .makeText(getApplicationContext(),
//                                "Selected : " + s,
//                                Toast.LENGTH_SHORT)
//                        .show();
            }
        };
        recview.setLayoutManager(linearLayoutManager);

        assignRequisitionAdapterClass = new AssignRequisitionAdapterClass(listener,itemClickListener,data);
        recview.setAdapter(assignRequisitionAdapterClass);
    }

    private void recycleClickLister() {
        setreq_id.setText("");
        listener  = new AssignRequisitionAdapterClass.RecycleViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                String  s = data.get(position).getStock_id();
                String Max  = data.get(position).getStock_lot();
                setmaxlot.setText(Max);
                setreq_id.setText(s);

                //Toast.makeText(AssignStockRequisition.this, s+Max, Toast.LENGTH_SHORT).show();

            }
        };
    }

    private void fatchdata() {
        progressDialog = new ProgressDialog(AssignStockRequisition.this);
        progressDialog.setMessage("Loading... Please Wait!");
        progressDialog.show();

        String token = appConfig.getuser_token();
        String userId = appConfig.getuser_id();
        String location = appConfig.getLocationId();
        String useremptype = appConfig.getuser_employee_type();
        String user_employee_type = appConfig.getuser_employee_type();

        String req_id = getIntent().getStringExtra("id");
        //Toast.makeText(this, token+"/"+userId+"/"+location+"/"+useremptype+"/"+req_id, Toast.LENGTH_SHORT).show();

        StringRequest request = new StringRequest(Request.Method.POST, "https://erp.philsengg.com/api/requisition/requistion_avaiable_stock",
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
                                Toast.makeText(AssignStockRequisition.this, message, Toast.LENGTH_SHORT).show();
                                appConfig.updateUserLoginStatus(false);
                                startActivity(new Intent(AssignStockRequisition.this, LoginActivity.class));
                                finish();
                            }
                            else {

                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    j++;
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String sn = String.valueOf(j);
                                    String stock_id = object.getString("stock_id");
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
                                    String make_name = object.getString("make_name");
                                    String uom_name = object.getString("uom_name");

                                    String stock_category_name = object.getString("stock_category_name");
                                    String assign_quantity = object.getString("assign_quantity");

                                    if(stock_quantity.equals("0")) {

                                    }else{
                                        Double sum=0.0;
                                        if(assign_quantity.equals("null")) {
                                            int k = 0;
                                            int p = 0;
                                             sum = Double.valueOf(stock_quantity) - 0;
                                        }
                                        else
                                        {
                                            sum = Double.valueOf(stock_quantity) - Double.valueOf(assign_quantity);
                                        }
//
                                        Double q = sum;
//
//                                        Toast.makeText(AssignStockRequisition.this, String.valueOf(p), Toast.LENGTH_SHORT).show();
                                        String AvailableStock = String.valueOf(q);
//
                                        if(sum == 0.0){

                                        }else {
                                            responseModelAssignRequisition = new ResponseModelAssignRequisition(AvailableStock, stock_id, stock_location_id, stock_category_id, stock_type_id, stock_size_id,
                                                    stock_batch_number, stock_invoice_number, stock_distributor_name, stock_make_id, stock_uom_id, safety_stock,
                                                    stock_quantity, stock_lot, stock_price, is_stock_transfer, stock_status, stock_type_name, stock_size_name, make_name,
                                                    uom_name, stock_category_name, assign_quantity);
                                            data.add(responseModelAssignRequisition);
                                            assignRequisitionAdapterClass.notifyDataSetChanged();
                                            progressDialog.dismiss();
                                        }
                                    }


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
                Toast.makeText(AssignStockRequisition.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
                //return super.getHeaders();
            }

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                params.put("req_id", req_id);
                return  params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(AssignStockRequisition.this);
        requestQueue.add(request);


        StringRequest request12 = new StringRequest(Request.Method.POST, "https://erp.philsengg.com/api/job/job_welder_grinder_list",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            //String message = jsonObject.getString("message");

                            JSONArray jsonArray = jsonObject.getJSONArray("data");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                String user_id = object.getString("user_id");
                                String user_full_name = object.getString("user_full_name");
                                String user_employee_id = object.getString("user_employee_id");

                                String name = user_full_name +" - "+user_employee_id;

                                grinderWelderListSpinners.add(new GrinderWelderListSpinner(user_id,name));
                                grinderWelderListSpinnerArrayAdapter = new ArrayAdapter<GrinderWelderListSpinner>(AssignStockRequisition.this,
                                        android.R.layout.simple_spinner_dropdown_item,grinderWelderListSpinners);
                                grinderWelderListSpinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                            }
                            }
                        catch(JSONException e){
                                e.printStackTrace();
                            }
                        }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AssignStockRequisition.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
                //return super.getHeaders();
            }
        };

        RequestQueue requestQueue12 = Volley.newRequestQueue(AssignStockRequisition.this);
        requestQueue12.add(request12);

        assignrecivedby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Initialize dialog
             Dialog   dialog = new Dialog(AssignStockRequisition.this);

                // set custom dialog
                dialog.setContentView(R.layout.dialog_searchable_spinner_stock_type);

                // set custom height and width
                dialog.getWindow().setLayout(950, 1100);

                // set transparent background
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                // show dialog
                dialog.show();

                // Initialize and assign variable
                EditText editText = dialog.findViewById(R.id.edit_text);
                ListView listView = dialog.findViewById(R.id.list_view);

                listView.setAdapter(grinderWelderListSpinnerArrayAdapter);
                editText.addTextChangedListener(new TextWatcher() {
                                                    @Override
                                                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                                    }

                                                    @Override
                                                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                                        grinderWelderListSpinnerArrayAdapter.getFilter().filter(charSequence);
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

                        GrinderWelderListSpinner sp =(GrinderWelderListSpinner)parent.getItemAtPosition(position);
                        assignrecivedby.setText(sp.user_full_name);
                        setreciverid.setText(sp.user_id);
                        dialog.dismiss();
                    }
                });
            }
        });

    }
}