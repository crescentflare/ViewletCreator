package com.crescentflare.viewletcreatorexample.viewlets;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.crescentflare.viewletcreator.ViewletCreator;
import com.crescentflare.viewletcreator.binder.ViewletBinder;
import com.crescentflare.viewletcreator.utility.ViewletMapUtil;

import java.util.List;
import java.util.Map;

/**
 * Basic viewlet: view
 * Creation of a simple view through parsed JSON
 */
public class ViewViewlet implements ViewletCreator.Viewlet
{
    // ---
    // Implementation
    // ---

    @Override
    public View create(Context context)
    {
        return new View(context);
    }

    @Override
    public void update(View view, Map<String, Object> attributes, ViewGroup parent, ViewletBinder binder)
    {
        applyDefaultAttributes(view, attributes);
    }

    @Override
    public boolean canRecycle(View view, Map<String, Object> attributes)
    {
        return false;
    }


    // ---
    // Shared
    // ---

    public static String translatedText(Context context, String value)
    {
        int textIdentifier = context.getResources().getIdentifier(value, "string", context.getPackageName());
        if (textIdentifier > 0)
        {
            return context.getResources().getString(textIdentifier);
        }
        return value;
    }

    public static void applyDefaultAttributes(View view, Map<String, Object> attributes)
    {
        // Background color
        if (!(view instanceof EditText) && !(view instanceof Button))
        {
            view.setBackgroundColor(ViewletMapUtil.optionalColor(attributes, "backgroundColor", 0));
        }

        // Visibility
        String visibility = ViewletMapUtil.optionalString(attributes, "visibility", "");
        if (visibility.equals("gone"))
        {
            view.setVisibility(View.GONE);
        }
        else if (visibility.equals("invisible"))
        {
            view.setVisibility(View.INVISIBLE);
        }
        else
        {
            view.setVisibility(View.VISIBLE);
        }

        // Padding
        if (!(view instanceof EditText))
        {
            List<Integer> paddingList = ViewletMapUtil.optionalDensityIntList(attributes, "padding");
            if (paddingList.size() == 4)
            {
                view.setPadding(paddingList.get(0), paddingList.get(1), paddingList.get(2), paddingList.get(3));
            }
            else
            {
                view.setPadding(
                        ViewletMapUtil.optionalDensityInt(attributes, "paddingLeft", 0),
                        ViewletMapUtil.optionalDensityInt(attributes, "paddingTop", 0),
                        ViewletMapUtil.optionalDensityInt(attributes, "paddingRight", 0),
                        ViewletMapUtil.optionalDensityInt(attributes, "paddingBottom", 0)
                );
            }
        }
    }

    public static void applyLayoutAttributes(View view, Map<String, Object> attributes)
    {
        // Return early if layout parameters are not present
        if (view.getLayoutParams() == null)
        {
            return;
        }

        // Width
        String widthString = ViewletMapUtil.optionalString(attributes, "width", "");
        if (widthString.equals("matchParent"))
        {
            view.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
        }
        else if (widthString.equals("wrapContent"))
        {
            view.getLayoutParams().width = ViewGroup.LayoutParams.WRAP_CONTENT;
        }
        else
        {
            view.getLayoutParams().width = ViewletMapUtil.optionalDensityInt(attributes, "width", ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        // Height
        String heightString = ViewletMapUtil.optionalString(attributes, "height", "");
        if (heightString.equals("matchParent"))
        {
            view.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
        }
        else if (heightString.equals("wrapContent"))
        {
            view.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
        }
        else
        {
            view.getLayoutParams().height = ViewletMapUtil.optionalDensityInt(attributes, "height", ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        // Margin
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams)
        {
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams)view.getLayoutParams();
            List<Integer> marginList = ViewletMapUtil.optionalDensityIntList(attributes, "margin");
            if (marginList.size() == 4)
            {
                marginLayoutParams.setMargins(marginList.get(0), marginList.get(1), marginList.get(2), marginList.get(3));
            }
            else
            {
                marginLayoutParams.setMargins(
                        ViewletMapUtil.optionalDensityInt(attributes, "marginLeft", 0),
                        ViewletMapUtil.optionalDensityInt(attributes, "marginTop", 0),
                        ViewletMapUtil.optionalDensityInt(attributes, "marginRight", 0),
                        ViewletMapUtil.optionalDensityInt(attributes, "marginBottom", 0)
                );
            }
        }
    }
}
