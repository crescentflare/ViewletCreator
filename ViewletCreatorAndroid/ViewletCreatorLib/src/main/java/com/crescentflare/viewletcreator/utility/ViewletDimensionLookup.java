package com.crescentflare.viewletcreator.utility;

import javax.annotation.Nullable;

/**
 * Viewlet creator utility: interface for dimension lookup
 * Integrates with ViewletMapUtil to look up custom dimension references
 */
public interface ViewletDimensionLookup
{
    @Nullable
    Integer getDimension(String refId);
}
