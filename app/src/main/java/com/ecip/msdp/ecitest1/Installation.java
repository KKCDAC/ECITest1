package com.ecip.msdp.ecitest1;

import android.content.ContentUris;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

public class Installation extends AppCompatActivity {

    private static final String TAG = Installation.class.getName();
    static int count = 0; //Installation page should open only once after installation.
    public String id = null;
    public String email = null;
    public String phone = null;
    public String accountName = null;
    public String name = null;
    SharedPreferences pf;
    SharedPreferences pf_ack;
    EditText edit_name, edit_name_father_hus;
    RadioButton radio_button_age, radio_button_Dob;
    Spinner spinner_age_list, spinner_year, spinner_month, spinner_day,
            spinner_gender, spinner_state, spinner_district;
    LinearLayout ll1;
    ArrayAdapter<String> adapter_age_year, adapter_year, adapter_month, adapter_days, adapter_gender, adapter_state, adapter_dist;
    ArrayList<ElectoralStateClass> arrayList_state = new ArrayList<ElectoralStateClass>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_installation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        edit_name = (EditText) findViewById(R.id.edit_name);

        edit_name_father_hus = (EditText) findViewById(R.id.edit_name_father_hus);

        radio_button_age = (RadioButton) findViewById(R.id.age);
        radio_button_Dob = (RadioButton) findViewById(R.id.DoB);

        radio_button_age.setChecked(true);

        spinner_age_list = (Spinner) findViewById(R.id.layout_age);
        spinner_year = (Spinner) findViewById(R.id.year);
        spinner_month = (Spinner) findViewById(R.id.month);
        spinner_day = (Spinner) findViewById(R.id.day);
        spinner_gender = (Spinner) findViewById(R.id.spinner_gender);
        spinner_state = (Spinner) findViewById(R.id.spinner_state);
        spinner_district = (Spinner) findViewById(R.id.spinner_district);
        ll1 = (LinearLayout) findViewById(R.id.layout_DoB);
        ll1.setVisibility(View.GONE);


        Cursor c = getContentResolver().query(ContactsContract.Profile.CONTENT_URI, null, null, null, null);

        int count = c.getCount();


        String[] columnNames = c.getColumnNames();
        boolean b = c.moveToFirst();
        int position = c.getPosition();
        if (count == 1 && position == 0) {
            for (int j = 0; j < columnNames.length; j++) {
                String columnName = columnNames[j];
                String columnValue = c.getString(c.getColumnIndex(columnName));

                //Use the values

              /*  Log.d(TAG,"column_name"+columnName);
                Log.d(TAG,"column_values"+columnValue);*/
            }
            String columnValue = c.getString(c.getColumnIndex("display_name"));
            Log.d(TAG, "column_values" + columnValue);
            edit_name.setText(columnValue);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setTitle("Welcome " + columnValue);
            //URL url = new URL("http://image10.bizrate-images.com/resize?sq=60&uid=2216744464");
            /*Bitmap bmp = BitmapFactory.decodeFile("com.android.contacts/display_photo/9223372034707292161");
            ImageView imageView = (ImageView)findViewById(R.id.image);
            imageView.setImageBitmap(bmp);*/
        }

        c.close();
    /*
        boolean installed = appInstalledOrNot("in.cdac.gist.android.softkeyboard");
        if(installed) {
            //This intent will help you to launch if the package is already installed
            Intent LaunchIntent = getPackageManager()
                    .getLaunchIntentForPackage("in.cdac.gist.android.softkeyboard");
            startActivity(LaunchIntent);

            Toast.makeText(this,"app is already installed",Toast.LENGTH_SHORT ).show();
            //System.out.println("App is already installed on your phone");
        } else {

           *//* Intent goToMarket = new Intent(Intent.ACTION_VIEW)
                    .setData(Uri.parse("market://details?id=com.bt.bms"));
            startActivity(goToMarket);*//*
            Log.d(TAG,"App is not currently installed on your phone");
           // System.out.println("App is not currently installed on your phone");
        }*/

        spinner_year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                int month = spinner_month.getSelectedItemPosition();
                int day = spinner_day.getSelectedItemPosition();
                if (position == 0) {

                    ArrayList<String> arrayList_month = new ArrayList<String>();
                    arrayList_month.add("Month");
                    adapter_month = null;
                    adapter_month = new ArrayAdapter<String>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, arrayList_month);
                    spinner_month.setAdapter(adapter_month);

                    ArrayList<String> arrayList_days = new ArrayList<String>();
                    arrayList_days.add("Day");
                    adapter_days = null;
                    adapter_days = new ArrayAdapter<String>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, arrayList_days);
                    spinner_day.setAdapter(adapter_days);
                } else {
                    ArrayList<String> arrayList_month = MethodCollection.get_monthlist();
                    adapter_month = new ArrayAdapter<String>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, arrayList_month);
                    spinner_month.setAdapter(adapter_month);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        spinner_month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                int month = spinner_month.getSelectedItemPosition();
                int day = spinner_day.getSelectedItemPosition();
                if (position == 0) {
                    ArrayList<String> arrayList_days = new ArrayList<String>();
                    arrayList_days.add("Day");
                    adapter_days = null;
                    adapter_days = new ArrayAdapter<String>(Installation.this, R.layout.support_simple_spinner_dropdown_item, arrayList_days);
                    spinner_day.setAdapter(adapter_days);
                } else {
                    ArrayList<String> arrayList_days = MethodCollection.get_daylist(spinner_year.getSelectedItem().toString(), spinner_month.getSelectedItem().toString());
                    adapter_days = null;
                    adapter_days = new ArrayAdapter<String>(Installation.this, R.layout.support_simple_spinner_dropdown_item, arrayList_days);
                    spinner_day.setAdapter(adapter_days);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        spinner_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String statecode = arrayList_state.get(position).getId();
                if (position == 0) {
                    //set spinner for dist
                    ArrayList<String> arrayList_dist = new ArrayList<String>();
                    arrayList_dist.add("Select Constituency from List");
                    adapter_dist = new ArrayAdapter<String>(Installation.this, R.layout.support_simple_spinner_dropdown_item, arrayList_dist);
                    spinner_district.setAdapter(adapter_dist);
                } else {
                    ArrayList<ElectoralDistrictClass> array_list = MethodCollection.get_dist(statecode, getApplicationContext());
                    String[] array_dist = new String[array_list.size()];
                    for (int i = 0; i < array_list.size(); i++) {
                        array_dist[i] = array_list.get(i).getPc();
                    }
                    adapter_dist = new ArrayAdapter<String>(Installation.this, R.layout.support_simple_spinner_dropdown_item, array_dist);
                    spinner_district.setAdapter(adapter_dist);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                if (edit_name.getText().toString().equals("")) {
                    edit_name.setError("Name is required");
                } else {
                    String name = edit_name.getText().toString();
                    String fname = edit_name_father_hus.getText().toString();
                    String age = spinner_age_list.getSelectedItem().toString();
                    String year = spinner_year.getSelectedItem().toString();
                    String month = spinner_month.getSelectedItem().toString();
                    String day = spinner_day.getSelectedItem().toString();
                    String gender = spinner_gender.getSelectedItem().toString();
                    String state = spinner_state.getSelectedItem().toString();
                    String dist = spinner_district.getSelectedItem().toString();

                    int age_int = spinner_age_list.getSelectedItemPosition();
                    int year_int = spinner_year.getSelectedItemPosition();
                    int month_int = spinner_month.getSelectedItemPosition();
                    int day_int = spinner_day.getSelectedItemPosition();
                    int gender_int = spinner_gender.getSelectedItemPosition();
                    int state_int = spinner_state.getSelectedItemPosition();
                    int dist_int = spinner_district.getSelectedItemPosition();

                    boolean age_selected = radio_button_age.isSelected();
                    /*String url=UrlBuilder.electoralsearch_url_builder(name, fname, age, year, month, day, gender, state, dist,
                            age_int, year_int, month_int, day_int, gender_int, state_int, dist_int, age_selected);
                    ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
                    Boolean isInternetPresent = cd.isConnectingToInternet();
                    if(isInternetPresent){
                        *//*Intent myIntent = new Intent(Installation.this, SearchResult.class);
                        myIntent.putExtra("url",url);
                        ElectoralSearch.this.startActivity(myIntent);*//*
                    }*/
                }
            }
        });
        set_spinner();
       /* Bitmap bitmap = BitmapFactory.decodeStream(openPhoto(9223372034707292161L));
        Drawable drawable = new BitmapDrawable(getResources(),bitmap);
        actionBar.setIcon(drawable);*/
    }

    public InputStream openPhoto(long contactId) {
        Uri contactUri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, contactId);
        Uri photoUri = Uri.withAppendedPath(contactUri, ContactsContract.Contacts.Photo.CONTENT_DIRECTORY);
        Cursor cursor = getContentResolver().query(photoUri,
                new String[]{ContactsContract.Contacts.Photo.PHOTO}, null, null, null);
        if (cursor == null) {
            return null;
        }
        try {
            if (cursor.moveToFirst()) {
                byte[] data = cursor.getBlob(0);
                if (data != null) {
                    return new ByteArrayInputStream(data);
                }
            }
        } finally {
            cursor.close();
        }
        return null;
    }
    private boolean appInstalledOrNot(String uri) {
        PackageManager pm = getPackageManager();
        boolean app_installed;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        }
        catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }


        void set_spinner () {
            //set spinner for age
            ArrayList<String> arrayList_age = MethodCollection.get_agelist();
            adapter_age_year = new ArrayAdapter<String>(Installation.this, R.layout.support_simple_spinner_dropdown_item, arrayList_age);
            spinner_age_list.setAdapter(adapter_age_year);

            //set spinner for dob year
            ArrayList<String> arrayList_year = MethodCollection.get_yearlist();
            adapter_year = new ArrayAdapter<String>(Installation.this, R.layout.support_simple_spinner_dropdown_item, arrayList_year);
            spinner_year.setAdapter(adapter_year);

            //set spinner for dob month
            ArrayList<String> arrayList_month = new ArrayList<String>();
            arrayList_month.add("Months");
            adapter_month = new ArrayAdapter<String>(Installation.this, R.layout.support_simple_spinner_dropdown_item, arrayList_month);
            spinner_month.setAdapter(adapter_month);

            //set spinner for dob day
            ArrayList<String> arrayList_days = new ArrayList<String>();
            arrayList_days.add("Days");
            adapter_days = new ArrayAdapter<String>(Installation.this, R.layout.support_simple_spinner_dropdown_item, arrayList_days);
            spinner_day.setAdapter(adapter_days);

            //set spinner for gender
            ArrayList<String> arrayList_gender = MethodCollection.get_gender();
            adapter_gender = new ArrayAdapter<String>(Installation.this, R.layout.support_simple_spinner_dropdown_item, arrayList_gender);
            spinner_gender.setAdapter(adapter_gender);

            //set spinner for state
            arrayList_state = MethodCollection.get_state(Installation.this);
            String[] array_state = new String[arrayList_state.size()];
            for (int i = 0; i < arrayList_state.size(); i++) {
                array_state[i] = arrayList_state.get(i).getName();
            }
            adapter_state = new ArrayAdapter<String>(Installation.this, R.layout.support_simple_spinner_dropdown_item, array_state);
            spinner_state.setAdapter(adapter_state);

            //set spinner for dist
            ArrayList<String> arrayList_dist = new ArrayList<String>();
            arrayList_dist.add("Select Constituency from List");
            adapter_dist = new ArrayAdapter<String>(Installation.this, R.layout.support_simple_spinner_dropdown_item, arrayList_dist);
            spinner_district.setAdapter(adapter_dist);

            pf = getSharedPreferences("address", MODE_PRIVATE);
            pf_ack = getSharedPreferences("USER", MODE_PRIVATE);
            if (pf_ack.getBoolean("acknowledge", false)) {
                String stateString = pf.getString("state","").toUpperCase(); //the value you want the position for
                ArrayAdapter stAdap = (ArrayAdapter) spinner_state.getAdapter(); //cast to an ArrayAdapter
                int stateSpinnerPosition = stAdap.getPosition(stateString);//set the default according to value
                spinner_state.setSelection(stateSpinnerPosition);

                String constituencyString = pf.getString("locality","").toUpperCase();
                ArrayAdapter csAdap = (ArrayAdapter)spinner_district.getAdapter();
                int constituencySpinnerPosition =  csAdap.getPosition(constituencyString);
                spinner_district.setSelection(constituencySpinnerPosition);
            }
        }


        public void onRadioButtonClicked (View view){
            // Is the button now checked?
            boolean checked = ((RadioButton) view).isChecked();

            // Check which radio button was clicked
            switch (view.getId()) {
                case R.id.age:
                    if (checked) {
                        ll1.setVisibility(View.GONE);
                        spinner_age_list.setVisibility(View.VISIBLE);
                    }
                    break;
                case R.id.DoB:
                    if (checked) {
                        ll1.setVisibility(View.VISIBLE);
                        spinner_age_list.setVisibility(View.GONE);
                    }
                    break;
            }
        }
}

