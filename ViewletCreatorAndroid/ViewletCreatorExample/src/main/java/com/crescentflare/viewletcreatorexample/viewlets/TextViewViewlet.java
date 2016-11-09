package com.crescentflare.viewletcreatorexample.viewlets;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.crescentflare.viewletcreator.ViewletCreator;
import com.crescentflare.viewletcreator.binder.ViewletBinder;
import com.crescentflare.viewletcreator.utility.ViewletMapUtil;
import com.crescentflare.viewletcreatorexample.R;

import java.util.Map;

/**
 * Basic viewlet: text view
 * Creation of a text view through parsed JSON
 */
public class TextViewViewlet implements ViewletCreator.Viewlet
{
    @Override
    public View create(Context context)
    {
        return new TextView(context);
    }

    @Override
    public void update(View view, Map<String, Object> attributes, ViewGroup parent, ViewletBinder binder)
    {
        if (view instanceof TextView)
        {
            TextView textView = (TextView)view;
            textView.setText(ViewletMapUtil.optionalString(attributes, "text", ""));
        }
    }

    @Override
    public boolean canRecycle(View view, Map<String, Object> attributes)
    {
        return view instanceof TextView;
    }
}
