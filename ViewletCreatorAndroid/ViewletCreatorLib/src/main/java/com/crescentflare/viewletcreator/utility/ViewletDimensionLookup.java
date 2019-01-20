package com.crescentflare.viewletcreator.utility;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Viewlet creator utility: interface for dimension lookup
 * Integrates with ViewletMapUtil to look up custom dimension references
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public interface ViewletDimensionLookup
{
    @Nullable
    Integer getDimension(@NotNull String refId);
}
