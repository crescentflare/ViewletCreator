package com.crescentflare.viewletcreator.utility;

import android.content.res.ObbInfo;
import android.content.res.Resources;
import android.graphics.Color;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Viewlet creator utility: map access
 * Access values from a dynamic map easily and safely with data conversion
 */
public class ViewletMapUtil
{
    // ---
    // Map conversion
    // ---

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


    // ---
    // Fetch and convert lists
    // ---

    @SuppressWarnings("unchecked")
    public static List<Object> optionalObjectList(Map<String, Object> map, String key)
    {
        if (map != null)
        {
            Object object = map.get(key);
            if (object != null && object instanceof List<?>)
            {
                List<?> list = (List<?>)object;
                if (list.size() > 0)
                {
                    if (list.get(0) instanceof Object)
                    {
                        return (List<Object>)object;
                    }
                }
            }
        }
        return new ArrayList<>();
    }

    @SuppressWarnings("unchecked")
    public static List<String> optionalStringList(Map<String, Object> map, String key)
    {
        if (map != null)
        {
            Object object = map.get(key);
            if (object != null && object instanceof List<?>)
            {
                List<?> list = (List<?>)object;
                if (list.size() > 0)
                {
                    if (list.get(0) instanceof String)
                    {
                        return (List<String>)object;
                    }
                    else
                    {
                        List<String> convertedList = new ArrayList<>();
                        if (list.get(0) instanceof Double)
                        {
                            for (Double item : (List<Double>)object)
                            {
                                convertedList.add("" + item);
                            }
                        }
                        else if (list.get(0) instanceof Float)
                        {
                            for (Float item : (List<Float>)object)
                            {
                                convertedList.add("" + item);
                            }
                        }
                        else if (list.get(0) instanceof Integer)
                        {
                            for (Integer item : (List<Integer>)object)
                            {
                                convertedList.add("" + item);
                            }
                        }
                        else if (list.get(0) instanceof Boolean)
                        {
                            for (Boolean item : (List<Boolean>)object)
                            {
                                convertedList.add(item ? "true" : "false");
                            }
                        }
                        return convertedList;
                    }
                }
            }
        }
        return new ArrayList<>();
    }

    @SuppressWarnings("unchecked")
    public static List<Double> optionalDoubleList(Map<String, Object> map, String key)
    {
        if (map != null)
        {
            Object object = map.get(key);
            if (object != null && object instanceof List<?>)
            {
                List<?> list = (List<?>)object;
                if (list.size() > 0)
                {
                    if (list.get(0) instanceof Double)
                    {
                        return (List<Double>)object;
                    }
                    else
                    {
                        List<Double> convertedList = new ArrayList<>();
                        if (list.get(0) instanceof String)
                        {
                            for (String item : (List<String>)object)
                            {
                                try
                                {
                                    convertedList.add(Double.parseDouble(item));
                                }
                                catch (IllegalArgumentException ignored)
                                {
                                }
                            }
                        }
                        else if (list.get(0) instanceof Float)
                        {
                            for (Float item : (List<Float>)object)
                            {
                                convertedList.add(item.doubleValue());
                            }
                        }
                        else if (list.get(0) instanceof Integer)
                        {
                            for (Integer item : (List<Integer>)object)
                            {
                                convertedList.add(item.doubleValue());
                            }
                        }
                        else if (list.get(0) instanceof Boolean)
                        {
                            for (Boolean item : (List<Boolean>)object)
                            {
                                convertedList.add(item ? 1.0 : 0.0);
                            }
                        }
                        return convertedList;
                    }
                }
            }
        }
        return new ArrayList<>();
    }

    @SuppressWarnings("unchecked")
    public static List<Float> optionalFloatList(Map<String, Object> map, String key)
    {
        if (map != null)
        {
            Object object = map.get(key);
            if (object != null && object instanceof List<?>)
            {
                List<?> list = (List<?>)object;
                if (list.size() > 0)
                {
                    if (list.get(0) instanceof Float)
                    {
                        return (List<Float>)object;
                    }
                    else
                    {
                        List<Float> convertedList = new ArrayList<>();
                        if (list.get(0) instanceof String)
                        {
                            for (String item : (List<String>)object)
                            {
                                try
                                {
                                    convertedList.add(Float.parseFloat(item));
                                }
                                catch (IllegalArgumentException ignored)
                                {
                                }
                            }
                        }
                        else if (list.get(0) instanceof Double)
                        {
                            for (Double item : (List<Double>)object)
                            {
                                convertedList.add(item.floatValue());
                            }
                        }
                        else if (list.get(0) instanceof Integer)
                        {
                            for (Integer item : (List<Integer>)object)
                            {
                                convertedList.add(item.floatValue());
                            }
                        }
                        else if (list.get(0) instanceof Boolean)
                        {
                            for (Boolean item : (List<Boolean>)object)
                            {
                                convertedList.add(item ? 1.0f : 0.0f);
                            }
                        }
                        return convertedList;
                    }
                }
            }
        }
        return new ArrayList<>();
    }

    @SuppressWarnings("unchecked")
    public static List<Integer> optionalIntegerList(Map<String, Object> map, String key)
    {
        if (map != null)
        {
            Object object = map.get(key);
            if (object != null && object instanceof List<?>)
            {
                List<?> list = (List<?>)object;
                if (list.size() > 0)
                {
                    if (list.get(0) instanceof Integer)
                    {
                        return (List<Integer>)object;
                    }
                    else
                    {
                        List<Integer> convertedList = new ArrayList<>();
                        if (list.get(0) instanceof String)
                        {
                            for (String item : (List<String>)object)
                            {
                                try
                                {
                                    convertedList.add(Integer.parseInt(item));
                                }
                                catch (IllegalArgumentException ignored)
                                {
                                }
                            }
                        }
                        else if (list.get(0) instanceof Double)
                        {
                            for (Double item : (List<Double>)object)
                            {
                                convertedList.add(item.intValue());
                            }
                        }
                        else if (list.get(0) instanceof Float)
                        {
                            for (Float item : (List<Float>)object)
                            {
                                convertedList.add(item.intValue());
                            }
                        }
                        else if (list.get(0) instanceof Boolean)
                        {
                            for (Boolean item : (List<Boolean>)object)
                            {
                                convertedList.add(item ? 1 : 0);
                            }
                        }
                        return convertedList;
                    }
                }
            }
        }
        return new ArrayList<>();
    }

    @SuppressWarnings("unchecked")
    public static List<Boolean> optionalBooleanList(Map<String, Object> map, String key)
    {
        if (map != null)
        {
            Object object = map.get(key);
            if (object != null && object instanceof List<?>)
            {
                List<?> list = (List<?>)object;
                if (list.size() > 0)
                {
                    if (list.get(0) instanceof Boolean)
                    {
                        return (List<Boolean>)object;
                    }
                    else
                    {
                        List<Boolean> convertedList = new ArrayList<>();
                        if (list.get(0) instanceof String)
                        {
                            for (String item : (List<String>)object)
                            {
                                try
                                {
                                    convertedList.add(Boolean.parseBoolean(item));
                                }
                                catch (IllegalArgumentException ignored)
                                {
                                }
                            }
                        }
                        else if (list.get(0) instanceof Double)
                        {
                            for (Double item : (List<Double>)object)
                            {
                                convertedList.add(item > 0);
                            }
                        }
                        else if (list.get(0) instanceof Float)
                        {
                            for (Float item : (List<Float>)object)
                            {
                                convertedList.add(item > 0);
                            }
                        }
                        else if (list.get(0) instanceof Integer)
                        {
                            for (Integer item : (List<Integer>)object)
                            {
                                convertedList.add(item > 0);
                            }
                        }
                        return convertedList;
                    }
                }
            }
        }
        return new ArrayList<>();
    }


    // ---
    // Fetch and convert view-related values
    // ---

    @SuppressWarnings("unchecked")
    public static List<Integer> optionalDensityIntList(Map<String, Object> map, String key)
    {
        if (map != null)
        {
            Object object = map.get(key);
            if (object != null && object instanceof List<?>)
            {
                List<?> list = (List<?>)object;
                if (list.size() > 0)
                {
                    List<Integer> convertedList = new ArrayList<>();
                    if (list.get(0) instanceof String)
                    {
                        for (String item : (List<String>)object)
                        {
                            String densityString = item;
                            float density;
                            if (densityString.endsWith("sp"))
                            {
                                densityString = densityString.substring(0, densityString.length() - 2);
                                density = Resources.getSystem().getDisplayMetrics().scaledDensity;
                            }
                            else if (densityString.endsWith("dp"))
                            {
                                densityString = densityString.substring(0, densityString.length() - 2);
                                density = Resources.getSystem().getDisplayMetrics().density;
                            }
                            else if (densityString.endsWith("px"))
                            {
                                densityString = densityString.substring(0, densityString.length() - 2);
                                density = 1;
                            }
                            else
                            {
                                density = Resources.getSystem().getDisplayMetrics().density;
                            }
                            try
                            {
                                convertedList.add((int)(Double.parseDouble(densityString) * density));
                            }
                            catch (IllegalArgumentException ignored)
                            {
                            }
                        }
                    }
                    else if (list.get(0) instanceof Double)
                    {
                        for (Double item : (List<Double>)object)
                        {
                            convertedList.add((int)(item * Resources.getSystem().getDisplayMetrics().density));
                        }
                    }
                    else if (list.get(0) instanceof Float)
                    {
                        for (Float item : (List<Float>)object)
                        {
                            convertedList.add((int)(item * Resources.getSystem().getDisplayMetrics().density));
                        }
                    }
                    else if (list.get(0) instanceof Integer)
                    {
                        for (Integer item : (List<Integer>)object)
                        {
                            convertedList.add((int)(item * Resources.getSystem().getDisplayMetrics().density));
                        }
                    }
                    return convertedList;
                }
            }
        }
        return new ArrayList<>();
    }

    public static int optionalColor(Map<String, Object> map, String key, int defaultValue)
    {
        String result = optionalString(map, key, null);
        if (result != null)
        {
            if (result.length() > 0 && result.charAt(0) != '#')
            {
                result = "#" + result;
            }
            try
            {
                return Color.parseColor(result);
            }
            catch (IllegalArgumentException ignored)
            {
            }
        }
        return defaultValue;
    }

    public static int optionalDensityInt(Map<String, Object> map, String key, int defaultValue)
    {
        if (map != null)
        {
            Object object = map.get(key);
            if (object instanceof String)
            {
                String densityString = (String)object;
                float density;
                if (densityString.endsWith("sp"))
                {
                    densityString = densityString.substring(0, densityString.length() - 2);
                    density = Resources.getSystem().getDisplayMetrics().scaledDensity;
                }
                else if (densityString.endsWith("dp"))
                {
                    densityString = densityString.substring(0, densityString.length() - 2);
                    density = Resources.getSystem().getDisplayMetrics().density;
                }
                else if (densityString.endsWith("px"))
                {
                    densityString = densityString.substring(0, densityString.length() - 2);
                    density = 1;
                }
                else
                {
                    density = Resources.getSystem().getDisplayMetrics().density;
                }
                try
                {
                    return (int)(Double.parseDouble(densityString) * density);
                }
                catch (IllegalArgumentException ignored)
                {
                }
            }
            else if (object instanceof Double)
            {
                return (int)((Double)object * Resources.getSystem().getDisplayMetrics().density);
            }
            else if (object instanceof Float)
            {
                return (int)((Float)object * Resources.getSystem().getDisplayMetrics().density);
            }
            else if (object instanceof Integer)
            {
                return (int)((Integer)object * Resources.getSystem().getDisplayMetrics().density);
            }
        }
        return defaultValue;
    }


    // ---
    // Fetch and convert basic values
    // ---

    public static String optionalString(Map<String, Object> map, String key, String defaultValue)
    {
        if (map != null)
        {
            Object object = map.get(key);
            if (object instanceof String)
            {
                return (String)object;
            }
            else if (object instanceof Double || object instanceof Float || object instanceof Integer || object instanceof Boolean)
            {
                return object.toString();
            }
        }
        return defaultValue;
    }

    public static double optionalDouble(Map<String, Object> map, String key, double defaultValue)
    {
        if (map != null)
        {
            Object object = map.get(key);
            if (object instanceof String)
            {
                try
                {
                    return Double.parseDouble((String)object);
                }
                catch (IllegalArgumentException ignored)
                {
                }
            }
            else if (object instanceof Double)
            {
                return (Double)object;
            }
            else if (object instanceof Float)
            {
                return (Float)object;
            }
            else if (object instanceof Integer)
            {
                return (Integer)object;
            }
            else if (object instanceof Boolean)
            {
                return ((Boolean)object) ? 1.0 : 0.0;
            }
        }
        return defaultValue;
    }

    public static float optionalFloat(Map<String, Object> map, String key, float defaultValue)
    {
        if (map != null)
        {
            Object object = map.get(key);
            if (object instanceof String)
            {
                try
                {
                    return Float.parseFloat((String)object);
                }
                catch (IllegalArgumentException ignored)
                {
                }
            }
            else if (object instanceof Double)
            {
                return ((Double)object).floatValue();
            }
            else if (object instanceof Float)
            {
                return (Float)object;
            }
            else if (object instanceof Integer)
            {
                return (Integer)object;
            }
            else if (object instanceof Boolean)
            {
                return ((Boolean)object) ? 1.0f : 0.0f;
            }
        }
        return defaultValue;
    }

    public static int optionalInteger(Map<String, Object> map, String key, int defaultValue)
    {
        if (map != null)
        {
            Object object = map.get(key);
            if (object instanceof String)
            {
                try
                {
                    return Integer.parseInt((String)object);
                }
                catch (IllegalArgumentException ignored)
                {
                }
            }
            else if (object instanceof Double)
            {
                return ((Double)object).intValue();
            }
            else if (object instanceof Float)
            {
                return ((Float)object).intValue();
            }
            else if (object instanceof Integer)
            {
                return (Integer)object;
            }
            else if (object instanceof Boolean)
            {
                return ((Boolean)object) ? 1 : 0;
            }
        }
        return defaultValue;
    }

    public static boolean optionalBoolean(Map<String, Object> map, String key, boolean defaultValue)
    {
        if (map != null)
        {
            Object object = map.get(key);
            if (object instanceof String)
            {
                try
                {
                    return Boolean.parseBoolean((String)object);
                }
                catch (IllegalArgumentException ignored)
                {
                }
            }
            else if (object instanceof Double)
            {
                return (Double)object > 0;
            }
            else if (object instanceof Float)
            {
                return (Float)object > 0;
            }
            else if (object instanceof Integer)
            {
                return (Integer)object > 0;
            }
            else if (object instanceof Boolean)
            {
                return (Boolean)object;
            }
        }
        return defaultValue;
    }
}
