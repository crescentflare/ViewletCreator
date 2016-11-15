package com.crescentflare.viewletcreatorexample;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.crescentflare.viewletcreator.ViewletCreator;
import com.crescentflare.viewletcreatorexample.viewlets.TextViewViewlet;

/**
 * An easy way to handle settings for the viewlet example
 */
public class Settings
{
    // ---
    // Prefences keys
    // ---

    private static final String KEY_SERVER_ENABLED = "PREF_SERVER_ENABLED";
    private static final String KEY_AUTO_REFRESH = "PREF_AUTO_REFRESH";
    private static final String KEY_SERVER_ADDRESS = "PREF_SERVER_ADDRESS";


    // ---
    // Members
    // ---

    private boolean serverEnabled = false;
    private boolean autoRefresh = false;
    private String serverAddress = "";
    private boolean settingsLoaded = false;


    // ---
    // Singleton instance
    // ---

    public static Settings instance = new Settings();

    private Settings()
    {
    }


    // ---
    // Settings access
    // ---

    public boolean isServerEnabled()
    {
        loadSettingsIfNeeded();
        return serverEnabled;
    }

    public void setServerEnabled(boolean serverEnabled)
    {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(ExampleApplication.context).edit();
        this.serverEnabled = serverEnabled;
        editor.putBoolean(KEY_SERVER_ENABLED, serverEnabled);
        editor.apply();
    }

    public boolean isAutoRefresh()
    {
        loadSettingsIfNeeded();
        return autoRefresh;
    }

    public void setAutoRefresh(boolean autoRefresh)
    {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(ExampleApplication.context).edit();
        this.autoRefresh = autoRefresh;
        editor.putBoolean(KEY_AUTO_REFRESH, autoRefresh);
        editor.apply();
    }

    public String getServerAddress()
    {
        loadSettingsIfNeeded();
        return serverAddress;
    }

    public void setServerAddress(String serverAddress)
    {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(ExampleApplication.context).edit();
        this.serverAddress = serverAddress;
        editor.putString(KEY_SERVER_ADDRESS, serverAddress);
        editor.apply();
    }


    // ---
    // Helper
    // ---

    public void loadSettingsIfNeeded()
    {
        if (!settingsLoaded)
        {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ExampleApplication.context);
            serverEnabled = preferences.getBoolean(KEY_SERVER_ENABLED, false);
            autoRefresh = preferences.getBoolean(KEY_AUTO_REFRESH, false);
            serverAddress = preferences.getString(KEY_SERVER_ADDRESS, "http://127.0.0.1:2233");
            settingsLoaded = true;
        }
    }
}
