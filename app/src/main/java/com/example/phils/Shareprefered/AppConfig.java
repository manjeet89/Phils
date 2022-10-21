package com.example.phils.Shareprefered;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.phils.R;

public class AppConfig {
    private Context context;
    private SharedPreferences sharedPreferences;

    public AppConfig(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(context.getString(R.string.pref_file_key),Context.MODE_PRIVATE);
    }

    public boolean isUserLogin()
    {
        return sharedPreferences.getBoolean(context.getString(R.string.pref_is_user_login),false);
    }

    public void updateUserLoginStatus(boolean status)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(context.getString(R.string.pref_is_user_login),status);
        editor.apply();
    }
//    public void saveNameOfUser(String name)
//    {
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString(context.getString(R.string.pref_name_of_user),name);
//        editor.apply();
//    }
//    public String getNameOfUser()
//    {
//        return sharedPreferences.getString(context.getString(R.string.pref_name_of_user),"Unknown");
//    }
//
//    public void saveIdOfUser(String id)
//    {
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString(context.getString(R.string.pref_id_of_user),id);
//        editor.apply();
//    }
//    public String getIdOfUser()
//    {
//        return sharedPreferences.getString(context.getString(R.string.pref_id_of_user),"same");
//    }
//
//    public void saveUserName(String name)
//    {
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString(context.getString(R.string.save_userName),name);
//        editor.apply();
//    }
//    public String getUserName()
//    {
//        return sharedPreferences.getString(context.getString(R.string.save_userName),"Admin");
//    }


    public void SaveLocation(String location)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.save_location),location);
        editor.apply();
    }
    public String getLocation()
    {
        return sharedPreferences.getString(context.getString(R.string.save_location),"padgha");
    }

    public void SaveLocationId(String locationId)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.save_locationid),locationId);
        editor.apply();
    }
    public String getLocationId()
    {
        return sharedPreferences.getString(context.getString(R.string.save_locationid),"1");
    }










    public void Saveuser_id(String user_id)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.user_id),user_id);
        editor.apply();
    }
    public String getuser_id()
    {
        return sharedPreferences.getString(context.getString(R.string.user_id),"id");
    }

    public void Saveuser_full_name(String user_full_name)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.user_full_name),user_full_name);
        editor.apply();
    }
    public String getuser_full_name()
    {
        return sharedPreferences.getString(context.getString(R.string.user_full_name),"name");
    }

    public void Saveuser_email_id(String user_email_id)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.user_email_id),user_email_id);
        editor.apply();
    }
    public String getuser_email_id()
    {
        return sharedPreferences.getString(context.getString(R.string.user_email_id),"user_email_id");
    }

    public void Saveuser_employee_type(String user_employee_type)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.user_employee_type),user_employee_type);
        editor.apply();
    }
    public String getuser_employee_type()
    {
        return sharedPreferences.getString(context.getString(R.string.user_employee_type),"user_employee_type");
    }

    public void Saveemployee_type(String employee_type)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.employee_type),employee_type);
        editor.apply();
    }
    public String getemployee_type()
    {
        return sharedPreferences.getString(context.getString(R.string.employee_type),"employee_type");
    }

    public void Saveemp_type_name(String emp_type_name)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.emp_type_name),emp_type_name);
        editor.apply();
    }
    public String getemp_type_name()
    {
        return sharedPreferences.getString(context.getString(R.string.emp_type_name),"emp_type_name");
    }

    public void Saveemp_type_id(String emp_type_id)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.emp_type_id),emp_type_id);
        editor.apply();
    }
    public String getemp_type_id()
    {
        return sharedPreferences.getString(context.getString(R.string.emp_type_id),"emp_type_id");
    }

    public void Saveuser_token(String user_token)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.user_token),user_token);
        editor.apply();
    }
    public String getuser_token()
    {
        return sharedPreferences.getString(context.getString(R.string.user_token),"user_token");
    }

    public void Saveuser_name(String user_name)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.user_name),user_name);
        editor.apply();
    }
    public String getuser_name()
    {
        return sharedPreferences.getString(context.getString(R.string.user_name),"Admin");
    }



    public void Saveaccess_module(String access_module)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.access_module),access_module);
        editor.apply();
    }
    public String getaccess_module()
    {
        return sharedPreferences.getString(context.getString(R.string.access_module),"access_module");
    }









    //Requisition approved name

    public void SaveRequisition(String Requisition)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.Requisitionsave),Requisition);
        editor.apply();
    }
    public String getRequisition()
    {
        return sharedPreferences.getString(context.getString(R.string.Requisitionsave),"false");
    }

    public void SaveManagerName(String Requisition)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.managername),Requisition);
        editor.apply();
    }
    public String getManagerName()
    {
        return sharedPreferences.getString(context.getString(R.string.managername),"false");
    }



}
