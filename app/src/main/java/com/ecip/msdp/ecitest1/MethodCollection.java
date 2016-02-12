package com.ecip.msdp.ecitest1;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

/**
 * Created by msdg on 12-02-2016.
 */
public class MethodCollection {
    Context context;

    public MethodCollection(Context context){
        this.context=context;
    }

    public boolean isConnectingToInternet(){
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null)
        {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }

        }
        return false;
    }

    public static ArrayList<String> get_agelist(){
        String first="Select Age from List";
        ArrayList<String> abc= new ArrayList<String>();
        abc.add(first);
        for(int i=18;i<=120;i++){
            abc.add(""+i);
        }
        return abc;
    }

    public static ArrayList<String> get_yearlist(){
        String first="Year";
        Calendar now = Calendar.getInstance();   // Gets the current date and time
        int year = now.get(Calendar.YEAR);
        ArrayList<String> abc= new ArrayList<String>();
        abc.add(first);
        for(int i=(year-18);i>=(year-120);i--){
            abc.add(""+i);
        }
        return abc;
    }

    public static ArrayList<String> get_monthlist(){
        String first[]={"Month","Jan","Feb","March","April","May","June","July","Aug","Sep","Oct","Nov","Dec"};
        ArrayList<String> abc= new ArrayList<>(Arrays.asList(first));
        return abc;
    }

    public static ArrayList<String> get_gender(){
        String first[]={"Select Gender from List","Male","Female","Others"};
        ArrayList<String> abc= new ArrayList<>(Arrays.asList(first));
        return abc;
    }

    public static ArrayList<ElectoralStateClass> get_state(Context _context){
        ArrayList<ElectoralStateClass> abc=json_to_arraylist_state(readfile("json_files_elctoralsearch/state.json",_context));
        return abc;
    }

    public static ArrayList<ElectoralDistrictClass> get_dist(String statecode,Context _context){
        ArrayList<ElectoralDistrictClass> abc=json_to_arraylist_dist(readfile("json_files_elctoralsearch/"+statecode+".json",_context));
        return abc;
    }

    public static String readfile(String filepath,Context _context){
        String json = null;
        try {
            InputStream is = _context.getAssets().open(filepath);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
    public static ArrayList<ElectoralStateClass> json_to_arraylist_state(String json){
        ArrayList<ElectoralStateClass> state_array=new ArrayList<ElectoralStateClass>();
        try {
            JSONObject obj = new JSONObject(json);
            JSONArray m_jArry = obj.getJSONArray("response");
            for (int i = 0; i < m_jArry.length(); i++) {
                JSONObject jo_inside = m_jArry.getJSONObject(i);
                //Log.d("Details-->", jo_inside.getString("formule"));
                String id = jo_inside.getString("id");
                String state = jo_inside.getString("state");

                ElectoralStateClass ed=new ElectoralStateClass(id,state);
                state_array.add(ed);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return state_array;
    }

    public static ArrayList<ElectoralDistrictClass> json_to_arraylist_dist(String json){
        ArrayList<ElectoralDistrictClass> dist_array=new ArrayList<ElectoralDistrictClass>();
        try {
            JSONObject obj = new JSONObject(json);
            JSONObject m_response = obj.getJSONObject("response");
            String state_name=m_response.getString("state");
            JSONArray distarray=m_response.getJSONArray("docs");
            for (int i = 0; i < distarray.length(); i++) {
                JSONObject jo_inside = distarray.getJSONObject(i);
                //Log.d("Details-->", jo_inside.getString("formule"));
                String id = jo_inside.getString("id");
                String dist = jo_inside.getString("dist");
                String pc = jo_inside.getString("pc");
                String show = jo_inside.getString("show");

                ElectoralDistrictClass edc=new ElectoralDistrictClass(id,dist,pc,show);
                dist_array.add(edc);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dist_array;
    }


    public static ArrayList<String> get_daylist(String year,String month){

        String first="Day";
        ArrayList<String> abc= new ArrayList<>();
        abc.add(first);
        int int_year=Integer.parseInt(year);
        boolean flag_leap=false;
        if(int_year%4==0)
        {
            if(int_year%100==0)
            {
                if(int_year%400==0)
                {
                    flag_leap=true;
                }
                else{
                    flag_leap=false;
                }
            }
            else{
                flag_leap=true;
            }
        }
        else
        {
            flag_leap=false;
        }
        int n=0;
        if(month.equals("Jan")||month.equals("March")||month.equals("May")||month.equals("July")||month.equals("Aug")||month.equals("Oct")||month.equals("Dec"))
        {
            n=31;
        }
        else if(month.equals("April")||month.equals("June")||month.equals("Sep")||month.equals("Nov"))
        {
            n=30;
        }
        else if(month.equals("Feb")&& flag_leap==false)
        {
            n=28;
        }
        else
        {
            n=29;
        }

        for(int i=1;i<=n;i++)
        {
            abc.add(""+i);
        }
        return abc;
    }
}
