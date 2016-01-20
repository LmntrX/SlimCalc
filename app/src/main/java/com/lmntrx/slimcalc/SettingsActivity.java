package com.lmntrx.slimcalc;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    CheckBox vibrateCB;
    CheckBox themeCB;
    Context con;
    Toast toast;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        con=getApplicationContext();

        vibrateCB=(CheckBox)findViewById(R.id.vibrate_cb);
        themeCB=(CheckBox)findViewById(R.id.theme_cb);

        loadPrefs();

        vibrateCB.setOnClickListener(this);
        themeCB.setOnClickListener(this);

        toast=Toast.makeText(this,"",Toast.LENGTH_SHORT);
    }


    public void loadPrefs(){
        loadVibratePrefs();
        loadThemePrefs();
    }

    public boolean loadVibratePrefs() {
        SharedPreferences sp= PreferenceManager.getDefaultSharedPreferences(con);
        boolean cbValue=sp.getBoolean("VIBRATE_CHECKBOX",true);
        vibrateCB.setChecked(cbValue);
        return cbValue;
    }
    public boolean loadThemePrefs() {
        SharedPreferences sp= PreferenceManager.getDefaultSharedPreferences(con);
        boolean cbValue=sp.getBoolean("THEME_CHECKBOX",true);
        themeCB.setChecked(cbValue);
        return cbValue;
    }

    public void saveVibratePrefs(String key, boolean value){
        SharedPreferences sp= PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor edit=sp.edit();
        edit.putBoolean(key,value);
        edit.commit();
    }
    public void saveThemePrefs(String key, boolean value){
        SharedPreferences sp= PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor edit=sp.edit();
        edit.putBoolean(key,value);
        edit.commit();
    }

    @Override
    public void onClick(View v) {
        String id=v.getId()+"";
        if (id.equals((R.id.vibrate_cb)+"")){
            saveVibratePrefs("VIBRATE_CHECKBOX", vibrateCB.isChecked());
            if (vibrateCB.isChecked()){
                toast.setText("Vibration Enabled");
                toast.show();
            }
            else {
                toast.setText("Vibration Disabled");
                toast.show();
            }
        }
        else if (id.equals((R.id.theme_cb)+"")){
            saveThemePrefs("THEME_CHECKBOX", themeCB.isChecked());
            toast.setText("App restart might be required");
            toast.show();
        }


    }
}

