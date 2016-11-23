package com.crescentflare.viewletcreator.utility;

import android.view.View;

/**
 * Viewlet creator utility: interface for color lookup
 * Integrates with ViewletMapUtil to look up custom color references
 */
public interface ViewletColorLookup
{
    Integer getColor(String refId);
}
