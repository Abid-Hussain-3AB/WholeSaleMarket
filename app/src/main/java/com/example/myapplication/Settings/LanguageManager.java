package com.example.myapplication.Settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;

import java.util.Locale;

public class LanguageManager {
    private Context context;
    SharedPreferences sharedPreferences;

    public LanguageManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("Lang",Context.MODE_PRIVATE);
    }
    public void updateResource(String code)
    {
        Locale locale = new Locale(code);
        Locale.setDefault(locale);
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.locale= locale;
        resources.updateConfiguration(configuration,resources.getDisplayMetrics());
        setLang(code);
    }
    public String getLang()
    {
       return sharedPreferences.getString("lang","en");
    }
    public void setLang(String code)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("lang",code);
        editor.commit();
    }
}
