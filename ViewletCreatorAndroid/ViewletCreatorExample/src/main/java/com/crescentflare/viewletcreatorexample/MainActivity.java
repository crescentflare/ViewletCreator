package com.crescentflare.viewletcreatorexample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.crescentflare.viewletcreator.ViewletCreator;
import com.crescentflare.viewletcreator.ViewletLoader;
import com.crescentflare.viewletcreator.utility.ViewletMapUtil;

import java.util.Map;

/**
 * The main activity shows a small layout example, explanation and buttons to show other layout examples
 */
public class MainActivity extends AppCompatActivity
{
    // ---
    // Initialization
    // ---

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // Set view
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Testing the viewlet
        Map<String, Object> attributes = ViewletLoader.loadAttributes(this, R.raw.layout_main);
        View createdView = ViewletCreator.create(this, "textView", ViewletMapUtil.asStringObjectMap(attributes.get("textView")), null);
        if (createdView != null)
        {
            ((ViewGroup)findViewById(R.id.activity_main_container)).addView(createdView);
        }
    }
}
