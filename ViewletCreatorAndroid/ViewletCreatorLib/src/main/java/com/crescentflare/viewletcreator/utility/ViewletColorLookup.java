package com.crescentflare.viewletcreator.utility;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Viewlet creator utility: interface for color lookup
 * Integrates with ViewletMapUtil to look up custom color references
 */
public interface ViewletColorLookup
{
    @Nullable
    Integer getColor(@NotNull String refId);
}
