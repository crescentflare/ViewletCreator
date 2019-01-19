package com.crescentflare.viewletcreator.binder;

import android.view.View;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Viewlet creator binder: view annotations
 * A viewlet binder implementation which assigns views to annotated fields in the given class
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class ViewletAnnotationBinder implements ViewletBinder
{
    private Object assignToObject;
    private Map<String, Field> annotatedFields = new HashMap<>();

    public ViewletAnnotationBinder(@NotNull Object assignToObject)
    {
        this.assignToObject = assignToObject;
        for (Field field : assignToObject.getClass().getDeclaredFields())
        {
            for (Annotation annotation : field.getDeclaredAnnotations())
            {
                if (annotation instanceof ViewletRef)
                {
                    annotatedFields.put(((ViewletRef)annotation).value(), field);
                }
            }
        }
    }

    @Override
    public void onBind(@NotNull String refId, @Nullable View view)
    {
        if (view != null)
        {
            Field assignField = annotatedFields.get(refId);
            if (assignField != null)
            {
                try
                {
                    assignField.setAccessible(true);
                    assignField.set(assignToObject, view);
                }
                catch (IllegalAccessException ignored)
                {
                }
            }
        }
    }
}
