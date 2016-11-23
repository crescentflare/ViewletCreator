package com.crescentflare.viewletcreator.utility;

/**
 * Viewlet creator utility: interface for dimension lookup
 * Integrates with ViewletMapUtil to look up custom dimension references
 */
public interface ViewletDimensionLookup
{
    Integer getDimension(String refId);
}
