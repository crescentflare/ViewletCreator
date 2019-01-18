package com.crescentflare.viewletcreator.utility;

import android.content.Context;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Viewlet creator utility: dimension lookup through resource ID
 * A dimension lookup implementation fetching dimensions through the app dimension resources defined in XML
 */
public class ViewletResourceDimensionLookup implements ViewletDimensionLookup
{
    private Context resourceContext;

    public ViewletResourceDimensionLookup(@NotNull Context resourceContext)
    {
        this.resourceContext = resourceContext;
    }

    @Nullable
    @Override
    public Integer getDimension(@NotNull String refId)
    {
        int identifier = resourceContext.getResources().getIdentifier(refId, "dimen", resourceContext.getPackageName());
        if (identifier > 0)
        {
            return resourceContext.getResources().getDimensionPixelSize(identifier);
        }
        return null;
    }
}
