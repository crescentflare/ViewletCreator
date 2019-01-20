package com.crescentflare.viewletcreatorexample.viewlets;

import android.content.Context;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.crescentflare.viewletcreator.ViewletCreator;
import com.crescentflare.viewletcreator.binder.ViewletBinder;
import com.crescentflare.viewletcreator.utility.ViewletMapUtil;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

/**
 * Basic viewlet: text view
 * Creation of a text view through parsed JSON
 */
public class TextViewViewlet implements ViewletCreator.Viewlet
{
    @NotNull
    @Override
    public View create(@NotNull Context context)
    {
        return new TextView(context);
    }

    @Override
    public boolean update(@NotNull View view, @NotNull Map<String, Object> attributes, @Nullable ViewGroup parent, @Nullable ViewletBinder binder)
    {
        if (view instanceof TextView)
        {
            // Text
            TextView textView = (TextView)view;
            textView.setText(ViewViewlet.translatedText(view.getContext(), ViewletMapUtil.optionalString(attributes, "text", "")));

            // Text style
            String typeface = ViewletMapUtil.optionalString(attributes, "typeface", "");
            int defaultSize = (int)(view.getContext().getResources().getDisplayMetrics().scaledDensity * 17);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, ViewletMapUtil.optionalDimension(attributes, "textSize", defaultSize));
            if (typeface.equals("bold"))
            {
                textView.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
            }
            else if (typeface.equals("italics"))
            {
                textView.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.ITALIC));
            }
            else
            {
                textView.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
            }
            textView.setTextColor(ViewletMapUtil.optionalColor(attributes, "textColor", 0xFF101010));

            // Other properties
            String textAlignment = ViewletMapUtil.optionalString(attributes, "textAlignment", "");
            textView.setMaxLines(ViewletMapUtil.optionalInteger(attributes, "maxLines", Integer.MAX_VALUE));
            if (textAlignment.equals("center"))
            {
                textView.setGravity(Gravity.CENTER);
            }
            else if (textAlignment.equals("right"))
            {
                textView.setGravity(Gravity.RIGHT);
            }
            else
            {
                textView.setGravity(Gravity.LEFT);
            }

            // Standard view attributes
            ViewViewlet.applyDefaultAttributes(view, attributes);
            return true;
        }
        return false;
    }

    @Override
    public boolean canRecycle(@NotNull View view, @NotNull Map<String, Object> attributes)
    {
        return view instanceof TextView && !(view instanceof Button) && !(view instanceof EditText);
    }
}
