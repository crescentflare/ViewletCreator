package com.crescentflare.viewletcreator.utility;

import android.content.res.Resources;
import android.graphics.Color;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Viewlet creator utility: map access
 * Access values from a dynamic map easily and safely with data conversion
 */
public class ViewletMapUtil
{
    @SuppressWarnings("unchecked")
    public static Map<String, Object> asStringObjectMap(Object object)
    {
        if (isMap(object))
        {
            Map<?, ?> map = (Map<?, ?>)object;
            if (map.keySet().size() > 0)
            {
                Object firstKey = map.keySet().iterator().next();
                if (!(firstKey instanceof String))
                {
                    return null;
                }
            }
            return (Map<String, Object>)object;
        }
        return null;
    }

    public static boolean isMap(Object object)
    {
        return object != null && object instanceof Map<?, ?>;
    }
}
