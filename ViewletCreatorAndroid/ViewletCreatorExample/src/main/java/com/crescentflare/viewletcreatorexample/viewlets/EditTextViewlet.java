package com.crescentflare.viewletcreatorexample.viewlets;

import android.content.Context;
import android.graphics.Typeface;
import android.text.InputType;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.crescentflare.viewletcreator.ViewletCreator;
import com.crescentflare.viewletcreator.binder.ViewletBinder;
import com.crescentflare.viewletcreator.utility.ViewletMapUtil;

import java.util.Map;

/**
 * Basic viewlet: editable text
 * Creation of an editable text view through parsed JSON
 */
public class EditTextViewlet implements ViewletCreator.Viewlet
{
    @Override
    public View create(Context context)
    {
        return new EditText(context);
    }

    @Override
    public void update(View view, Map<String, Object> attributes, ViewGroup parent, ViewletBinder binder)
    {
        if (view instanceof EditText)
        {
            // Pre-filled text and hint
            EditText editText = (EditText)view;
            editText.setText(ViewViewlet.translatedText(view.getContext(), ViewletMapUtil.optionalString(attributes, "text", "")));
            editText.setHint(ViewViewlet.translatedText(view.getContext(), ViewletMapUtil.optionalString(attributes, "hint", "")));

            // Set input mode
            String inputType = ViewletMapUtil.optionalString(attributes, "inputType", "");
            if (inputType.equals("email"))
            {
                editText.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
            }
            else if (inputType.equals("url"))
            {
                editText.setInputType(InputType.TYPE_TEXT_VARIATION_URI);
            }
            else
            {
                editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
            }

            // Text style
            String typeface = ViewletMapUtil.optionalString(attributes, "typeface", "");
            int defaultSize = (int)(view.getContext().getResources().getDisplayMetrics().scaledDensity * 17);
            editText.setTextSize(TypedValue.COMPLEX_UNIT_PX, ViewletMapUtil.optionalDensityInt(attributes, "textSize", defaultSize));
            if (typeface.equals("bold"))
            {
                editText.setTypeface(null, Typeface.BOLD);
            }
            else if (typeface.equals("italics"))
            {
                editText.setTypeface(null, Typeface.ITALIC);
            }
            else
            {
                editText.setTypeface(Typeface.DEFAULT);
            }
            editText.setTextColor(ViewletMapUtil.optionalColor(attributes, "textColor", 0xFF101010));

            // Standard view attributes
            ViewViewlet.applyDefaultAttributes(view, attributes);
        }
    }

    @Override
    public boolean canRecycle(View view, Map<String, Object> attributes)
    {
        return view instanceof EditText;
    }
}
