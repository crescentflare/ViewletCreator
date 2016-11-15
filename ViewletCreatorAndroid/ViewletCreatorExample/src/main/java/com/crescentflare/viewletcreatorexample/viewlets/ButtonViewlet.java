package com.crescentflare.viewletcreatorexample.viewlets;

import android.content.Context;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.crescentflare.viewletcreator.ViewletCreator;
import com.crescentflare.viewletcreator.binder.ViewletBinder;
import com.crescentflare.viewletcreator.utility.ViewletMapUtil;

import java.util.Map;

/**
 * Basic viewlet: button
 * Creation of a button through parsed JSON
 */
public class ButtonViewlet implements ViewletCreator.Viewlet
{
    @Override
    public View create(Context context)
    {
        return new Button(context);
    }

    @Override
    public void update(View view, Map<String, Object> attributes, ViewGroup parent, ViewletBinder binder)
    {
        if (view instanceof Button)
        {
            // Text
            Button button = (Button)view;
            String text = ViewletMapUtil.optionalString(attributes, "text", "");
            int textIdentifier = view.getContext().getResources().getIdentifier(text, "string", view.getContext().getPackageName());
            if (textIdentifier > 0)
            {
                text = view.getContext().getResources().getString(textIdentifier);
            }
            button.setText(text);

            // Text style
            String typeface = ViewletMapUtil.optionalString(attributes, "typeface", "");
            int defaultSize = (int)(view.getContext().getResources().getDisplayMetrics().scaledDensity * 17);
            button.setTextSize(TypedValue.COMPLEX_UNIT_PX, ViewletMapUtil.optionalDensityInt(attributes, "textSize", defaultSize));
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
        }
    }

    @Override
    public boolean canRecycle(View view, Map<String, Object> attributes)
    {
        return view instanceof Button;
    }
}
