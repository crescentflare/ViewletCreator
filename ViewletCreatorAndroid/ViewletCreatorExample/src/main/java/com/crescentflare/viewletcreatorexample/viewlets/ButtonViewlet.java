package com.crescentflare.viewletcreatorexample.viewlets;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.SwitchCompat;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.crescentflare.viewletcreator.ViewletCreator;
import com.crescentflare.viewletcreator.binder.ViewletBinder;
import com.crescentflare.viewletcreator.utility.ViewletMapUtil;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

/**
 * Basic viewlet: button
 * Creation of a button through parsed JSON
 */
public class ButtonViewlet implements ViewletCreator.Viewlet
{
    @NotNull
    @Override
    public View create(@NotNull Context context)
    {
        return new Button(context);
    }

    @Override
    public boolean update(@NotNull View view, @Nullable Map<String, Object> attributes, @Nullable ViewGroup parent, @Nullable ViewletBinder binder)
    {
        if (view instanceof Button)
        {
            // Text
            Button button = (Button)view;
            button.setText(ViewViewlet.translatedText(view.getContext(), ViewletMapUtil.optionalString(attributes, "text", "")));

            // Text style
            String typeface = ViewletMapUtil.optionalString(attributes, "typeface", "");
            int defaultSize = (int)(view.getContext().getResources().getDisplayMetrics().scaledDensity * 17);
            button.setTextSize(TypedValue.COMPLEX_UNIT_PX, ViewletMapUtil.optionalDimension(attributes, "textSize", defaultSize));
            if (typeface.equals("bold"))
            {
                button.setTypeface(null, Typeface.BOLD);
            }
            else if (typeface.equals("italics"))
            {
                button.setTypeface(null, Typeface.ITALIC);
            }
            else
            {
                button.setTypeface(Typeface.DEFAULT);
            }
            button.setTextColor(ViewletMapUtil.optionalColor(attributes, "textColor", 0xFF101010));

            // Other properties
            String textAlignment = ViewletMapUtil.optionalString(attributes, "textAlignment", "");
            button.setMaxLines(ViewletMapUtil.optionalInteger(attributes, "maxLines", Integer.MAX_VALUE));
            if (textAlignment.equals("center"))
            {
                button.setGravity(Gravity.CENTER_HORIZONTAL);
            }
            else if (textAlignment.equals("right"))
            {
                button.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
            }
            else
            {
                button.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
            }

            // Standard view attributes
            ViewViewlet.applyDefaultAttributes(view, attributes);
            return true;
        }
        return false;
    }

    @Override
    public boolean canRecycle(@NotNull View view, @Nullable Map<String, Object> attributes)
    {
        return view instanceof Button && !(view instanceof SwitchCompat);
    }
}
