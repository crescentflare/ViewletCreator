package com.crescentflare.viewletcreator.binder;

import android.view.View;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * Viewlet creator binder: view map
 * A viewlet binder implementation which contains a map of all referenced views
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class ViewletMapBinder implements ViewletBinder
{
    private Map<String, View> boundViews = new HashMap<>();

    @Override
    public void onBind(@NotNull String refId, @Nullable View view)
    {
        if (view != null)
        {
            boundViews.put(refId, view);
        }
    }

    @Nullable
    public View findByReference(@NotNull String refId)
    {
        return boundViews.get(refId);
    }
}
