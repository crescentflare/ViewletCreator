package com.crescentflare.viewletcreatorexample.viewlets;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.crescentflare.viewletcreator.ViewletCreator;
import com.crescentflare.viewletcreator.binder.ViewletBinder;
import com.crescentflare.viewletcreator.utility.ViewletMapUtil;

import java.util.List;
import java.util.Map;

/**
 * Container viewlet: linear layout
 * Creation of a linear layout through parsed JSON
 */
public class LinearLayoutViewlet implements ViewletCreator.Viewlet
{
    @Override
    public View create(Context context)
    {
        return new LinearLayout(context);
    }

    @Override
    public void update(View view, Map<String, Object> attributes, ViewGroup parent, ViewletBinder binder)
    {
        if (view instanceof LinearLayout)
        {
            // First remove all children
            LinearLayout container = (LinearLayout)view;
            container.removeAllViews();

            // Add children
            List<Object> children = ViewletMapUtil.optionalObjectList(attributes, "children");
            for (Object child : children)
            {
                Map<String, Object> viewAttributes = ViewletMapUtil.asStringObjectMap(child);
                View createdView = ViewletCreator.create(view.getContext(), viewAttributes, container);
                if (createdView != null)
                {
                    container.addView(createdView);
                    ViewViewlet.applyLayoutAttributes(createdView, viewAttributes);
                    if (binder != null)
                    {
                        String refId = ViewletMapUtil.optionalString(viewAttributes, "refId", null);
                        if (refId != null)
                        {
                            binder.onBind(refId, createdView);
                        }
                    }
                }
            }

            // Standard view attributes
            ViewViewlet.applyDefaultAttributes(view, attributes);
        }
    }

    @Override
    public boolean canRecycle(View view, Map<String, Object> attributes)
    {
        return view instanceof LinearLayout;
    }
}
