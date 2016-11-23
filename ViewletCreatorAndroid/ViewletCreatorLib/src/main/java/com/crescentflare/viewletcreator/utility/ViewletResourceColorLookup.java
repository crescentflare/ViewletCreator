package com.crescentflare.viewletcreator.utility;

import android.content.Context;
import android.view.View;

/**
 * Viewlet creator utility: color lookup through resource ID
 * A color lookup implementation fetching colors through the app color resources defined in XML
 */
public class ViewletResourceColorLookup implements ViewletColorLookup
{
    private Context resourceContext;

    public ViewletResourceColorLookup(Context resourceContext)
    {
        this.resourceContext = resourceContext;
    }

    @Override
    public Integer getColor(String refId)
    {
        int identifier = resourceContext.getResources().getIdentifier(refId, "color", resourceContext.getPackageName());
        if (identifier > 0)
        {
            return resourceContext.getResources().getColor(identifier);
        }
        return null;
    }
}
