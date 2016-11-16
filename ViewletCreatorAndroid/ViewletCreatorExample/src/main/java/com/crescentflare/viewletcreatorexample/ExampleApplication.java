package com.crescentflare.viewletcreatorexample;

import android.app.Application;
import android.content.Context;

import com.crescentflare.viewletcreator.ViewletCreator;
import com.crescentflare.viewletcreatorexample.viewlets.ButtonViewlet;
import com.crescentflare.viewletcreatorexample.viewlets.EditTextViewlet;
import com.crescentflare.viewletcreatorexample.viewlets.LinearLayoutViewlet;
import com.crescentflare.viewletcreatorexample.viewlets.SwitchViewlet;
import com.crescentflare.viewletcreatorexample.viewlets.TextViewViewlet;
import com.crescentflare.viewletcreatorexample.viewlets.ViewViewlet;

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
        ViewletCreator.registerViewlet("linearLayout", new LinearLayoutViewlet());
        ViewletCreator.registerViewlet("textView", new TextViewViewlet());
        ViewletCreator.registerViewlet("editText", new EditTextViewlet());
        ViewletCreator.registerViewlet("switch", new SwitchViewlet());
        ViewletCreator.registerViewlet("button", new ButtonViewlet());
        ViewletCreator.registerViewlet("view", new ViewViewlet());
    }
}
