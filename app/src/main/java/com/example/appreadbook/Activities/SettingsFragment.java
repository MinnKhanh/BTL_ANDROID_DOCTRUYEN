package com.example.appreadbook.Activities;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.preference.ListPreference;

import com.example.appreadbook.Listener.bahomlistener;
import com.example.appreadbook.Model.Info;
import com.example.appreadbook.Model.PreferenceManager;
import com.example.appreadbook.R;

import java.util.List;

@SuppressLint("ValidFragment")
public class SettingsFragment extends PreferenceFragment {
    PreferenceManager preferenceManager;
    bahomlistener settingsActivity;
    SettingsFragment(bahomlistener settingsActivity){
        this.settingsActivity=settingsActivity;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferencesetting);
        Preference myPref = (Preference) findPreference("dark");
        Preference exit = (Preference) findPreference("exit");
        Preference lis =findPreference("coll");
        preferenceManager=new PreferenceManager((Context) this.settingsActivity);
        myPref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {

                Info.darkmode=preference.shouldDisableDependents();
                Log.d("Tag2", String.valueOf(Info.darkmode));
                return true;
            }
        });
        exit.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
               settingsActivity.OnClikchap();
                return false;

            }
        });
        Preference cash = (Preference) findPreference("cash");
        cash.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
               preferenceManager.clear();
                return false;
            }
        });
        Preference listPreference= (Preference) findPreference("coll");
        listPreference.setSummary(Info.numbercol+" column");
listPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
    @Override
    public boolean onPreferenceChange(Preference preference, Object o) {
Log.d("hiencol",o.toString());
        Info.numbercol=Integer.valueOf(o.toString());
        listPreference.setSummary(o.toString()+" column");
        return true;
    }
});

    }

}