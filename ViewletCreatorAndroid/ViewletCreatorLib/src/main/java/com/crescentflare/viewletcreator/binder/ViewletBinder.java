package com.crescentflare.viewletcreator.binder;

import android.view.View;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Viewlet creator binder: default interface
 * An interface which can be implemented to do custom viewlet binding
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public interface ViewletBinder
{
    void onBind(@NotNull String refId, @Nullable View view);
}
