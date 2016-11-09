package com.crescentflare.viewletcreatorexample;

import android.app.Application;
import android.content.Context;

import com.crescentflare.viewletcreator.ViewletCreator;
import com.crescentflare.viewletcreatorexample.viewlets.TextViewViewlet;

/**
 * The singleton application context (containing the other singletons in the app)
 */
public class ExampleApplication extends Application
{
    // ---
    // Global context member
    // ---

    public static Context context = null;


    // ---
    // Initialization
    // ---

    @Override
    public void onCreate()
    {
        super.onCreate();
        context = this;
        registerViewlets();
    }

    public void registerViewlets()
    {
        ViewletCreator.registerViewlet("textView", new TextViewViewlet());
    }
}
