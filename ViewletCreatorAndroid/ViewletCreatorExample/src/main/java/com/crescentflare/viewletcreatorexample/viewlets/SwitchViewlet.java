package com.crescentflare.viewletcreatorexample.viewlets;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.SwitchCompat;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

import com.crescentflare.viewletcreator.ViewletCreator;
import com.crescentflare.viewletcreator.binder.ViewletBinder;
import com.crescentflare.viewletcreator.utility.ViewletMapUtil;

import java.util.Map;

/**
 * Basic viewlet: switch
 * Creation of a switch through parsed JSON
 */
public class SwitchViewlet implements ViewletCreator.Viewlet
{
    @Override
    public View create(Context context)
    {
        return new SwitchCompat(context);
    }

    @Override
    public boolean update(View view, Map<String, Object> attributes, ViewGroup parent, ViewletBinder binder)
    {
        if (view instanceof SwitchCompat)
        {
            // Text
            SwitchCompat switchControl = (SwitchCompat)view;
            switchControl.setText(ViewViewlet.translatedText(view.getContext(), ViewletMapUtil.optionalString(attributes, "text", "")));

            // State
            switchControl.setChecked(ViewletMapUtil.optionalBoolean(attributes, "on", false));

            // Text style
            String typeface = ViewletMapUtil.optionalString(attributes, "typeface", "");
            int defaultSize = (int)(view.getContext().getResources().getDisplayMetrics().scaledDensity * 17);
            switchControl.setTextSize(TypedValue.COMPLEX_UNIT_PX, ViewletMapUtil.optionalDimension(attributes, "textSize", defaultSize));
            if (typeface.equals("bold"))
            {
                switchControl.setTypeface(null, Typeface.BOLD);
            }
            else if (typeface.equals("italics"))
            {
                switchControl.setTypeface(null, Typeface.ITALIC);
            }
            else
            {
                switchControl.setTypeface(Typeface.DEFAULT);
            }
            switchControl.setTextColor(ViewletMapUtil.optionalColor(attributes, "textColor", 0xFF101010));

            // Standard view attributes
            ViewViewlet.applyDefaultAttributes(view, attributes);
            return true;
        }
        return false;
    }

    @Override
    public boolean canRecycle(View view, Map<String, Object> attributes)
    {
        return view instanceof SwitchCompat;
    }
}
