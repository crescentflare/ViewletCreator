package com.crescentflare.viewletcreator.utility;

import android.view.View;

import javax.annotation.Nullable;

/**
 * Viewlet creator utility: interface for color lookup
 * Integrates with ViewletMapUtil to look up custom color references
 */
public interface ViewletColorLookup
{
    @Nullable
    Integer getColor(String refId);
}
