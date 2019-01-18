package com.crescentflare.viewletcreator.binder;

import android.view.View;

import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * Viewlet creator binder: view map
 * A viewlet binder implementation which contains a map of all referenced views
 */
public class ViewletMapBinder implements ViewletBinder
{
    private Map<String, View> boundViews = new HashMap<>();

    @Override
    public void onBind(String refId, View view)
    {
        if (view != null)
        {
            boundViews.put(refId, view);
        }
    }

    @Nullable
    public View findByReference(String refId)
    {
        return boundViews.get(refId);
    }
}
