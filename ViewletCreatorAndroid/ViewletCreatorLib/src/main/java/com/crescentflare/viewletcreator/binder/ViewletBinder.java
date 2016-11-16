package com.crescentflare.viewletcreator.binder;

import android.view.View;

/**
 * Viewlet creator binder: default interface
 * An interface which can be implemented to do custom viewlet binding
 */
public interface ViewletBinder
{
    void onBind(String refId, View view);
}
