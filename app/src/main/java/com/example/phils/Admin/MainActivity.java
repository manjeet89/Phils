package com.example.phils.Admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
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
import com.example.phils.R;
import com.example.phils.Rought.Demo;
import com.example.phils.Shareprefered.AppConfig;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static long back_pressed;
    TextView settoken;

    TextView location_save;
    Button logout,location;
    Button btnnotification;
    ImageView img,profile;
    TextView locationtext;
    AppConfig appConfig;
    Dialog dialog;
    Button demo;

    ProgressDialog progressDialog;
    static int z=0;

    private static final String CHANNEL_ID = "My Channel";
    private static final int NOTIFICATION_ID = 100;


    @Override
    public void onBackPressed(){
        if (back_pressed + 2000 > System.currentTimeMillis()){
            finishAffinity();
        }
        else{
            Toast.makeText(getBaseContext(), "Press once again to exit",
                    Toast.LENGTH_SHORT).show();
            back_pressed = System.currentTimeMillis();
        }
    }

//    private static final String ONESIGNAL_APP_ID = "19014ed5-455e-422d-a4f9-3206946ab9cc";


    //SwitchMaterial switchmaterial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

//        appConfig = new AppConfig(this);
//
//
//        String ko = appConfig.getRequisition();
//        Log.d("kooooo",ko);
//
//        if(appConfig.getRequisition().equals("true")){
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//            setTheme(R.style.Theme_Dark);
//
//        }
//        else{
//            setTheme(R.style.Theme_Day);
//        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        appConfig = new AppConfig(this);
        progressDialog = new ProgressDialog(this);
        demo = findViewById(R.id.demo);
        demo.setVisibility(View.GONE);
        demo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(), Demo.class));
            }
        });

//        switchmaterial = findViewById(R.id.switchmaterial);
//
//        if(appConfig.getRequisition().equals("true")){
//
//            switchmaterial.setChecked(true);
//
//        }
//        else{
//            switchmaterial.setChecked(false);
//
//        }



        String token = appConfig.getuser_token();
        String location = appConfig.getLocationId();
        String locationName = appConfig.getLocation();
        Log.d("tokennn",token);


//        switchmaterial.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if(b)
//                {
//                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//
//                    appConfig.SaveRequisition("true");
//
//                }
//                else
//                {
//                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//
//                    appConfig.SaveRequisition("false");
//
//                }
//            }
//        });













//        String user_id = getIntent().getStringExtra("user_id");
//        String user_email_id = getIntent().getStringExtra("user_email_id");

        settoken = findViewById(R.id.settoken);

        String user_id = appConfig.getuser_id();
        String user_email_id = appConfig.getuser_email_id();
//        progressDialog.setTitle("Welcome to Home");
//        progressDialog.setMessage("Please Wait!");
//        progressDialog.show();

        String userId = appConfig.getuser_id();
        String user_employee_type = appConfig.getuser_employee_type();


        StringRequest request = new StringRequest(Request.Method.POST, "https://erp.philsengg.com/api/job/location_list",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            int j=0;
                            String stock_category_status;
                            String emp_type_name;

                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("message");
                            //Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                            if(message.equals("Invalid user request")){
                                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                                appConfig.updateUserLoginStatus(false);
                                startActivity(new Intent(MainActivity.this,LoginActivity.class));
                                finish();
                            }
                            else {
                                String access_module = jsonObject.getString("access_module");
                                appConfig.Saveaccess_module(access_module);
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
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(request);


//        // Enable verbose OneSignal logging to debug issues if needed.
//        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);
//
//        // OneSignal Initialization
//        OneSignal.initWithContext(this);
//        OneSignal.setAppId(ONESIGNAL_APP_ID);
//
//        // promptForPushNotifications will show the native Android notification permission prompt.
//        // We recommend removing the following code and instead using an In-App Message to prompt for notification permission (See step 7)
//        OneSignal.promptForPushNotifications();

       // String token = appConfig.getuser_token();


//        String externalUserId = token; // You will supply the external user id to the OneSignal SDK
//
//// Setting External User Id with Callback Available in SDK Version 4.0.0+
//        OneSignal.setExternalUserId(externalUserId, new OneSignal.OSExternalUserIdUpdateCompletionHandler() {
//            @Override
//            public void onSuccess(JSONObject results) {
//                try {
//                    if (results.has("push") && results.getJSONObject("push").has("success")) {
//                        boolean isPushSuccess = results.getJSONObject("push").getBoolean("success");
//                        OneSignal.onesignalLog(OneSignal.LOG_LEVEL.VERBOSE, "Set external user id for push status: " + isPushSuccess);
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//                try {
//                    if (results.has("email") && results.getJSONObject("email").has("success")) {
//                        boolean isEmailSuccess = results.getJSONObject("email").getBoolean("success");
//                        OneSignal.onesignalLog(OneSignal.LOG_LEVEL.VERBOSE, "Set external user id for email status: " + isEmailSuccess);
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                try {
//                    if (results.has("sms") && results.getJSONObject("sms").has("success")) {
//                        boolean isSmsSuccess = results.getJSONObject("sms").getBoolean("success");
//                        OneSignal.onesignalLog(OneSignal.LOG_LEVEL.VERBOSE, "Set external user id for sms status: " + isSmsSuccess);
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(OneSignal.ExternalIdError error) {
//                // The results will contain channel failure statuses
//                // Use this to detect if external_user_id was not set and retry when a better network connection is made
//                OneSignal.onesignalLog(OneSignal.LOG_LEVEL.VERBOSE, "Set external user id done with error: " + error.toString());
//            }
//        });

        location_save = findViewById(R.id.location_save);
        String location_save1 = appConfig.getLocation();
        location_save.setText(location_save1);

        logout = findViewById(R.id.logout);


            locationtext = findViewById(R.id.locationtext);
            locationtext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String emp_name = appConfig.getuser_id();
                    if(emp_name.equals("1")) {
                    startActivity(new Intent(getApplicationContext(), ProjectLocationActivity.class));
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this, "Only Admin Can Change Location", Toast.LENGTH_SHORT).show();

                        //startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    }
                }
            });
            location_save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String emp_name = appConfig.getuser_id();
                        if(emp_name.equals("1")) {
                    startActivity(new Intent(getApplicationContext(), ProjectLocationActivity.class));
                        }
                        else
                        {
                            Toast.makeText(MainActivity.this, "Only Admin Can Change Location", Toast.LENGTH_SHORT).show();
                            //startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }
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
                 dialog=new Dialog(MainActivity.this);

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
                        z=0;
                        appConfig.updateUserLoginStatus(false);
                        startActivity(new Intent(MainActivity.this,LoginActivity.class));
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

//                SwitchMaterial switchMaterial = dialog.findViewById(R.id.switchmaterial);
//
//                 switchMaterial = dialog.findViewById(R.id.switchmaterial);
//
//
//                if(appConfig.getRequisition().equals("true")){
//
//                    switchMaterial.setChecked(true);
//
//                }
//                else{
//                    switchMaterial.setChecked(false);
//
//                }
//                switchMaterial.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                    @Override
//                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                        if(b)
//                {
//                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//
//                    appConfig.SaveRequisition("true");
//
//                }
//                else
//                {
//                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//
//                    appConfig.SaveRequisition("false");
//
//                }
//                    }
//                });

            }
        });

//        btnnotification = findViewById(R.id.btn_notification);
//        btnnotification.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String message = "This is Example notification";
//                Intent intent = new Intent(getApplicationContext(),Notification_Activity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                intent.putExtra("Message",message);
//                PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),1,intent,PendingIntent.FLAG_UPDATE_CURRENT);
//
//                NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(),CHANNEL_ID);
//                builder.setSmallIcon(R.drawable.ic_baseline_message_24);
//                builder.setContentTitle("Phils ERP");
//                builder.setContentText(message);
//                builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
//                builder.setContentIntent(pendingIntent);
//                builder.setAutoCancel(true);
//
//                NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
//                notificationManagerCompat.notify(NOTIFICATION_ID,builder.build());
//
//
//            }
//        });

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










       // }


//        if(1==2) {
//            Menu menu = navigationView.getMenu();
//            MenuItem menuItem = menu.findItem(R.id.ghar);
//            menuItem.setVisible(false);
//        }





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
                        Toast.makeText(MainActivity.this, "Home", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.user:
                            startActivity(new Intent(getApplicationContext(), UserActivity.class));
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

    }

//    private void Grenerate() {
//
//        String user_id = appConfig.getuser_id();
//        String user_email_id = appConfig.getuser_email_id();
//
//        FirebaseMessaging.getInstance().getToken()
//                .addOnCompleteListener(new OnCompleteListener<String>() {
//                    @Override
//                    public void onComplete(@NonNull Task<String> task) {
//                        if (!task.isSuccessful()) {
//                            Log.d("Fetching", String.valueOf(task.getException()));
//                            return;
//                        }
//                        // Get new FCM registration token
//                        String firebasetoken = task.getResult();
//                        settoken.setText(firebasetoken);
//
//
////                        Log.d("chalns",settoken.getText().toString());
//                        // Log and toast
////                        String msg = getString(R.string.msg_token_fmt, token);
////                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
//
//
//                    }
//                });
//        String firetoken = "settoken.getText().toString()";
//        Log.d("chalns",firetoken);
//
//        StringRequest request = new StringRequest(Request.Method.POST, "https://erp.philsengg.com/api/login/update_firebase_user_token",
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        JSONObject jsonObject = null;
//                        try {
//                            jsonObject = new JSONObject(response);
//                            String message = jsonObject.getString("message");
//                            Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
//                            //startActivity(new Intent(getApplicationContext(),MainActivity.class));
//                            //progressDialog.dismiss();
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                        //Toast.makeText(Add_Stock_Category_Activity.this, response, Toast.LENGTH_SHORT).show();
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
//
//            }
//        }) {
//
//            @Nullable
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("user_id", user_id);
//                params.put("user_email_id", user_email_id);
//                params.put("firebase_user_token", firetoken);
//
//                return params;
//            }
//
//
//        };
//        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
//        requestQueue.add(request);
//
//    }





}