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
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.example.phils.Spinner.ReportManagerSpinner;
import com.example.phils.Spinner.UserEmployeeListSpinner;
import com.example.phils.Spinner.UserLocationSpinner;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class UpdateUserActivity extends AppCompatActivity {
    TextView emailtext,usernametext,passwordtext,selectstatus,userlocation,emppost,reportmanager;
    TextView setemployetype,setgender,setlocation,setemployetypespinner,setreportmanager;
    EditText email,usernameuser,passworduser,username,usercontact,empid;
    RadioGroup radioGroup,radiogroupgender;
    Button button;

    ArrayList<String> arrayList1;
    RequestQueue requestQueue;
    Dialog dialog;
    AppConfig appConfig;

    ArrayList<UserLocationSpinner> Userlocation = new ArrayList<>();
    ArrayAdapter<UserLocationSpinner> UserLocationAdapter;

    ArrayList<UserEmployeeListSpinner> UserEmployeeList = new ArrayList<>();
    ArrayAdapter<UserEmployeeListSpinner> UserEmployeeAdapter;

    ArrayList<ReportManagerSpinner> ReportList = new ArrayList<>();
    ArrayAdapter<ReportManagerSpinner> ReportAdapter;

    Button logout,location;
    Button btnnotification;
    ImageView img,profile;
    TextView locationtext,location_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);

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
                dialog=new Dialog(UpdateUserActivity.this);

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
                        startActivity(new Intent(UpdateUserActivity.this,LoginActivity.class));
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
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
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

        button = findViewById(R.id.button1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Insert();
            }
        });

        emppost = findViewById(R.id.emppost) ;
        userlocation = findViewById(R.id.userlocation);
        selectstatus = findViewById(R.id.selectstatus);
        reportmanager = findViewById(R.id.reportmanager);

        emailtext = findViewById(R.id.emailtext);
        usernametext = findViewById(R.id.usernametext);
        passwordtext = findViewById(R.id.passwordtext);
        username = findViewById(R.id.username);
        usercontact = findViewById(R.id.usercontact);
        empid = findViewById(R.id.empid);

        radioGroup = findViewById(R.id.radia_id1);
        radiogroupgender = findViewById(R.id.radiogroupgender);

        setgender = findViewById(R.id.setgender);
        setemployetype = findViewById(R.id.setemployetype);
        setlocation = findViewById(R.id.setlocation);
        setemployetypespinner = findViewById(R.id.setemployetypespinner);
        setreportmanager = findViewById(R.id.setreportmanager);

        email = findViewById(R.id.email);
        usernameuser = findViewById(R.id.usernameuser);
        passworduser = findViewById(R.id.passworduser);


//        RadioButton radioButton = radioGroup.findViewById(R.id.parmannet);
//        radioButton.setChecked(true);
//        setemployetype.setText("1");

//        RadioButton radioButton1 = radiogroupgender.findViewById(R.id.male);
//        radioButton1.setChecked(true);
//        setgender.setText("1");


        String EmpType = getIntent().getStringExtra("employee_type");
        if(EmpType.equals("Permanent"))
        {
            RadioButton radioButton = radioGroup.findViewById(R.id.parmannet);
            radioButton.setChecked(true);
            setemployetype.setText("1");
        }
        else
        {
            RadioButton radioButton = radioGroup.findViewById(R.id.tempory);
            radioButton.setChecked(true);
            setemployetype.setText("0");
        }
        String Name = getIntent().getStringExtra("user_full_name");
        username.setText(Name);
        String Password = getIntent().getStringExtra("user_password");
        String Username = getIntent().getStringExtra("user_name");
        usernameuser.setText(Username);
        String Gender = getIntent().getStringExtra("gender");
        if(Gender.equals("1"))
        {
            RadioButton radioButton1 = radiogroupgender.findViewById(R.id.male);
            radioButton1.setChecked(true);
            setgender.setText("1");
        }
        else
        {
            RadioButton radioButton1 = radiogroupgender.findViewById(R.id.female);
            radioButton1.setChecked(true);
            setgender.setText("0");
        }
        String EmpId = getIntent().getStringExtra("user_employee_id");
        empid.setText(EmpId);
        String Contact = getIntent().getStringExtra("user_mobile_number");
        usercontact.setText(Contact);
        String Email = getIntent().getStringExtra("user_email_id");
        email.setText(Email);
        String EmpPost = getIntent().getStringExtra("user_employee_type");
        setemployetypespinner.setText(EmpPost);
        String ReportManager = getIntent().getStringExtra("reporting_manager");
        setreportmanager.setText(ReportManager);
        String st = getIntent().getStringExtra("user_status");
        selectstatus.setText(st);
        Toast.makeText(this, EmpPost+ReportManager, Toast.LENGTH_SHORT).show();




        radiogroupgender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selectid = radioGroup.getCheckedRadioButtonId();
                if(selectid == -1)
                {
                    Toast.makeText(UpdateUserActivity.this, "No One Selected", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    RadioButton radioButton = radioGroup.findViewById(selectid);
                    if(radioButton.getText().equals("Male"))
                    {
                        setgender.setText("");
                        setgender.setText("1");
                        Toast.makeText(UpdateUserActivity.this, setgender.getText(), Toast.LENGTH_SHORT).show();
                    }
                    else{
                        setgender.setText("");
                        setgender.setText("0");
                        Toast.makeText(UpdateUserActivity.this, setgender.getText(), Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton button = findViewById(i);
                int selectId = radioGroup.getCheckedRadioButtonId();
                if(selectId == -1)
                {
                    Toast.makeText(UpdateUserActivity.this, "No one Selected", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    RadioButton radioButton = radioGroup.findViewById(selectId);
                    if(radioButton.getText().equals("Permanent")) {
                        emailtext.setVisibility(View.VISIBLE);
                        email.setVisibility(View.VISIBLE);

                        usernametext.setVisibility(View.VISIBLE);
                        usernameuser.setVisibility(View.VISIBLE);

                        passwordtext.setVisibility(View.VISIBLE);
                        passworduser.setVisibility(View.VISIBLE);

                        setemployetype.setText("");
                        setemployetype.setText("1");
                        Toast.makeText(UpdateUserActivity.this, setemployetype.getText(), Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        setemployetype.setText("");
                        setemployetype.setText("0");
                        Toast.makeText(UpdateUserActivity.this, setemployetype.getText(), Toast.LENGTH_SHORT).show();

                        emailtext.setVisibility(View.GONE);
                        email.setVisibility(View.GONE);

                        usernametext.setVisibility(View.GONE);
                        usernameuser.setVisibility(View.GONE);

                        passwordtext.setVisibility(View.GONE);
                        passworduser.setVisibility(View.GONE);
                    }
                }
            }
        });

        arrayList1=new ArrayList<>();
        arrayList1.add("Enabled");
        arrayList1.add("Disabled");

        selectstatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Initialize dialog
                dialog=new Dialog(UpdateUserActivity.this);

                // set custom dialog
                dialog.setContentView(R.layout.dialog_searchable_spinner_status);

                // set custom height and width
                dialog.getWindow().setLayout(750,1100);

                // set transparent background
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                // show dialog
                dialog.show();

                // Initialize and assign variable
                EditText editText1=dialog.findViewById(R.id.edit_text1);
                ListView listViewstatus=dialog.findViewById(R.id.list_view_status);

                ArrayAdapter<String> adapter=new ArrayAdapter<>(UpdateUserActivity.this, android.R.layout.simple_list_item_1,arrayList1);
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
                        selectstatus.setText(adapter.getItem(i));
                        // Dismiss dialog
                        dialog.dismiss();
                    }
                });

            }
        });

        String token = appConfig.getuser_token();
        String userId = appConfig.getuser_id();
        String location = appConfig.getLocationId();

        StringRequest request = new StringRequest(Request.Method.POST, "https://mployis.com/staging/api/job/location_list",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("message");
                            if(message.equals("Invalid user request")){
                                Toast.makeText(UpdateUserActivity.this, message, Toast.LENGTH_SHORT).show();
                                appConfig.updateUserLoginStatus(false);
                                startActivity(new Intent(UpdateUserActivity.this, LoginActivity.class));
                                finish();
                            }
                            else {
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);

                                    String location_id = object.getString("location_id");
                                    String location_name = object.getString("location_name");

                                    Userlocation.add(new UserLocationSpinner(location_id,location_name));
                                    UserLocationAdapter = new ArrayAdapter<UserLocationSpinner>(UpdateUserActivity.this,
                                            android.R.layout.simple_spinner_dropdown_item,Userlocation);
                                    UserLocationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
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
                Toast.makeText(UpdateUserActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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

        RequestQueue requestQueue = Volley.newRequestQueue(UpdateUserActivity.this);
        requestQueue.add(request);

        userlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Initialize dialog
                dialog=new Dialog(UpdateUserActivity.this);

                // set custom dialog
                dialog.setContentView(R.layout.dialog_searchable_spinner_status);

                // set custom height and width
                dialog.getWindow().setLayout(750,1100);

                // set transparent background
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                // show dialog
                dialog.show();

                // Initialize and assign variable
                EditText editText1=dialog.findViewById(R.id.edit_text1);
                ListView listViewstatus=dialog.findViewById(R.id.list_view_status);

                listViewstatus.setAdapter(UserLocationAdapter);
                editText1.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        UserLocationAdapter.getFilter().filter(charSequence);

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });
                listViewstatus.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        UserLocationSpinner sp = (UserLocationSpinner) adapterView.getItemAtPosition(i);
                        userlocation.setText(sp.location_name);
                        setlocation.setText(sp.location_id);
                        dialog.dismiss();
                        Toast.makeText(UpdateUserActivity.this, sp.location_id, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        //Toast.makeText(this, userId+location+token, Toast.LENGTH_SHORT).show();

        StringRequest request1 = new StringRequest(Request.Method.POST, "https://mployis.com/staging/api/user/user_employee_type_list",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("message");
                            if(message.equals("Invalid user request")){
                                Toast.makeText(UpdateUserActivity.this, message, Toast.LENGTH_SHORT).show();
                                appConfig.updateUserLoginStatus(false);
                                startActivity(new Intent(UpdateUserActivity.this, LoginActivity.class));
                                finish();
                            }
                            else {
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);

                                    String emp_type_id = object.getString("emp_type_id");
                                    String emp_type_name = object.getString("emp_type_name");
                                    if(emp_type_id.equals(EmpPost))
                                        emppost.setText(emp_type_name);
                                    UserEmployeeList.add(new UserEmployeeListSpinner(emp_type_id,emp_type_name));
                                    UserEmployeeAdapter = new ArrayAdapter<UserEmployeeListSpinner>(UpdateUserActivity.this,
                                            android.R.layout.simple_spinner_dropdown_item,UserEmployeeList);
                                    UserEmployeeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
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
                Toast.makeText(UpdateUserActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

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
                params.put("project_location_id", location);
                params.put("user_id", userId);
                return  params;
            }

        };
        RequestQueue  requestQueue1 = Volley.newRequestQueue(UpdateUserActivity.this);
        requestQueue1.add(request1);

        emppost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Initialize dialog
                dialog=new Dialog(UpdateUserActivity.this);

                // set custom dialog
                dialog.setContentView(R.layout.dialog_searchable_spinner_status);

                // set custom height and width
                dialog.getWindow().setLayout(750,1100);

                // set transparent background
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                // show dialog
                dialog.show();

                // Initialize and assign variable
                EditText editText1=dialog.findViewById(R.id.edit_text1);
                ListView listViewstatus=dialog.findViewById(R.id.list_view_status);

                listViewstatus.setAdapter(UserEmployeeAdapter);
                editText1.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        UserEmployeeAdapter.getFilter().filter(charSequence);

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });
                listViewstatus.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        UserEmployeeListSpinner sp = (UserEmployeeListSpinner) adapterView.getItemAtPosition(i);
                        emppost.setText(sp.emp_type_name);
                        setemployetypespinner.setText(sp.emp_type_id);
                        dialog.dismiss();
                        Toast.makeText(UpdateUserActivity.this, sp.emp_type_id, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

//        Toast.makeText(this, userId+location+token, Toast.LENGTH_SHORT).show();
        StringRequest request2 = new StringRequest(Request.Method.POST, "https://mployis.com/staging/api/user/reporting_manager_list",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("message");
                            if(message.equals("Invalid user request")){
                                Toast.makeText(UpdateUserActivity.this, message, Toast.LENGTH_SHORT).show();
                                appConfig.updateUserLoginStatus(false);
                                startActivity(new Intent(UpdateUserActivity.this, LoginActivity.class));
                                finish();
                            }
                            else {
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);

                                    String user_id = object.getString("user_id");
                                    String user_full_name = object.getString("user_full_name");
                                    if(user_id.equals(ReportManager))
                                        reportmanager.setText(user_full_name);

                                    ReportList.add(new ReportManagerSpinner(user_id,user_full_name));
                                    ReportAdapter = new ArrayAdapter<>(UpdateUserActivity.this,
                                            android.R.layout.simple_spinner_dropdown_item,ReportList);
                                    ReportAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                                    UserEmployeeList.add(new UserEmployeeListSpinner(emp_type_id,emp_type_name));
//                                    UserEmployeeAdapter = new ArrayAdapter<UserEmployeeListSpinner>(UpdateUserActivity.this,
//                                            android.R.layout.simple_spinner_dropdown_item,UserEmployeeList);
//                                    UserEmployeeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
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
                Toast.makeText(UpdateUserActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

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
                params.put("project_location_id", location);
                params.put("user_id", userId);
                return  params;
            }

        };
        RequestQueue  requestQueue2 = Volley.newRequestQueue(UpdateUserActivity.this);
        requestQueue2.add(request2);

        reportmanager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Initialize dialog
                dialog=new Dialog(UpdateUserActivity.this);

                // set custom dialog
                dialog.setContentView(R.layout.dialog_searchable_spinner_status);

                // set custom height and width
                dialog.getWindow().setLayout(750,1100);

                // set transparent background
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                // show dialog
                dialog.show();

                // Initialize and assign variable
                EditText editText1=dialog.findViewById(R.id.edit_text1);
                ListView listViewstatus=dialog.findViewById(R.id.list_view_status);

                listViewstatus.setAdapter(ReportAdapter);
                editText1.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        ReportAdapter.getFilter().filter(charSequence);

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });
                listViewstatus.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        ReportManagerSpinner sp = (ReportManagerSpinner) adapterView.getItemAtPosition(i);
                        reportmanager.setText(sp.user_full_name);
                        setreportmanager.setText(sp.user_id);
                        dialog.dismiss();
                        Toast.makeText(UpdateUserActivity.this, sp.user_id, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    private void Insert() {
        String EmpType = setemployetype.getText().toString();
        String Name = username.getText().toString().toUpperCase(Locale.ROOT);

        String Gender = setgender.getText().toString();
        String Email = email.getText().toString();
        String Contact = usercontact.getText().toString();
        String EmpId = empid.getText().toString();
        String Username = username.getText().toString();
        String Password = passworduser.getText().toString();
        String EmpPost = setemployetypespinner.getText().toString();
        String ReportManager = setreportmanager.getText().toString();
        String Locationuser = setlocation.getText().toString();
        String Status = selectstatus.getText().toString();

        if (TextUtils.isEmpty(Name)) {
            username.setError("Please Enter Name");
            Toast.makeText(UpdateUserActivity.this, "Please Enter Name", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(Contact)) {
            usercontact.setError("Please EnterNumber");
            Toast.makeText(UpdateUserActivity.this, "Please Enter Number", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(EmpId)) {
            empid.setError("Please Enter Employee Id");
            Toast.makeText(UpdateUserActivity.this, "Please Enter Employee Id", Toast.LENGTH_SHORT).show();
            return;
        }else if (TextUtils.isEmpty(EmpPost)) {
            Toast.makeText(UpdateUserActivity.this, "Please Select Employee Post", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(ReportManager)) {
            Toast.makeText(UpdateUserActivity.this, "Please Select Report Manager", Toast.LENGTH_SHORT).show();
            return;
        }else if (TextUtils.isEmpty(Locationuser)) {
            Toast.makeText(UpdateUserActivity.this, "Please Select Location", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(Status)) {
            Toast.makeText(UpdateUserActivity.this, "Please Select Status", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            ProgressDialog progressDialog = new ProgressDialog(UpdateUserActivity.this);
            progressDialog.setMessage("Loading... Please Wait!");
            progressDialog.show();

            if (Status.equals("Enabled")) {
                Status = "1";
            } else {
                Status = "0";
            }
            String st = Status;
            //Toast.makeText(this, st+email+Gender+Username+Password, Toast.LENGTH_SHORT).show();
            String token = appConfig.getuser_token();
            String userId = appConfig.getuser_id();
            String location = appConfig.getLocationId();
            String id = getIntent().getStringExtra("id");

            StringRequest request = new StringRequest(Request.Method.POST, "https://mployis.com/staging/api/user/add_user",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(response);
                                String message = jsonObject.getString("message");

//                                if(message.equals("Job size created successfully")) {
//                                    Toast.makeText(Add_Job_List_Activity.this, message, Toast.LENGTH_SHORT).show();
//                                    startActivity(new Intent(getApplicationContext(), Job_List_Activity.class));
//                                }
//                                else
                                Toast.makeText(UpdateUserActivity.this, message, Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), UserActivity.class));
                                progressDialog.dismiss();
                                //Toast.makeText(UpdateUserActivity.this, message, Toast.LENGTH_SHORT).show();

                                // }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            //Toast.makeText(Add_Stock_Category_Activity.this, response, Toast.LENGTH_SHORT).show();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(UpdateUserActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap headers = new HashMap();
                    headers.put("user_token", token);
                    headers.put("user_id", userId);
                    headers.put("project_location_id", location);

                    return headers;
                }

                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("user_id", id);
                    params.put("employee_type", EmpType);
                    params.put("user_full_name", Name);
                    params.put("user_password", Password);
                    params.put("user_name", Username);
                    params.put("gender", Gender);
                    params.put("user_employee_id", EmpId);
                    params.put("user_mobile_number", Contact);
                    params.put("user_email_id", Email);
                    params.put("user_employee_type", EmpPost);
                    params.put("reporting_manager", ReportManager);
                    params.put("user_status", st);
                    return params;
                }

            };
            RequestQueue requestQueue = Volley.newRequestQueue(UpdateUserActivity.this);
            requestQueue.add(request);


        }

    }
}
