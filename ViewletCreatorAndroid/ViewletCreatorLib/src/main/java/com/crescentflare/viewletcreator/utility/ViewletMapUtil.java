package com.crescentflare.viewletcreator.utility;

import android.content.res.Resources;
import android.graphics.Color;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

/**
 * Viewlet creator utility: map access
 * Access values from a dynamic map easily and safely with data conversion
 */
public class ViewletMapUtil
{
    // ---
    // Lookups
    // ---

    private static ViewletColorLookup colorLookup = null;
    private static ViewletDimensionLookup dimensionLookup = null;

    public static void setColorLookup(ViewletColorLookup lookup)
    {
        colorLookup = lookup;
    }

    public static void setDimensionLookup(ViewletDimensionLookup lookup)
    {
        dimensionLookup = lookup;
    }


    // ---
    // Map conversion
    // ---

    @Nullable
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
        return object instanceof Map<?, ?>;
    }


    // ---
    // Fetch and convert lists
    // ---

    @NotNull
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

    @NotNull
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
                    List<String> convertedList = new ArrayList<>();
                    for (Object item : list)
                    {
                        String result = objectToString(item);
                        if (result != null)
                        {
                            convertedList.add(result);
                        }
                    }
                    return convertedList;
                }
            }
        }
        return new ArrayList<>();
    }

    @NotNull
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
                    List<Double> convertedList = new ArrayList<>();
                    for (Object item : list)
                    {
                        Double result = objectToDouble(item);
                        if (result != null)
                        {
                            convertedList.add(result);
                        }
                    }
                    return convertedList;
                }
            }
        }
        return new ArrayList<>();
    }

    @NotNull
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
                    List<Float> convertedList = new ArrayList<>();
                    for (Object item : list)
                    {
                        Float result = objectToFloat(item);
                        if (result != null)
                        {
                            convertedList.add(result);
                        }
                    }
                    return convertedList;
                }
            }
        }
        return new ArrayList<>();
    }

    @NotNull
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
                    List<Integer> convertedList = new ArrayList<>();
                    for (Object item : list)
                    {
                        Integer result = objectToInteger(item);
                        if (result != null)
                        {
                            convertedList.add(result);
                        }
                    }
                    return convertedList;
                }
            }
        }
        return new ArrayList<>();
    }

    @NotNull
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
                    List<Boolean> convertedList = new ArrayList<>();
                    for (Object item : list)
                    {
                        Boolean result = objectToBoolean(item);
                        if (result != null)
                        {
                            convertedList.add(result);
                        }
                    }
                    return convertedList;
                }
            }
        }
        return new ArrayList<>();
    }


    // ---
    // Fetch and convert view-related values
    // ---

    @NotNull
    public static List<Integer> optionalColorList(Map<String, Object> map, String key)
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
                    for (Object item : list)
                    {
                        Integer result = objectToColor(item);
                        if (result != null)
                        {
                            convertedList.add(result);
                        }
                    }
                    return convertedList;
                }
            }
        }
        return new ArrayList<>();
    }

    @NotNull
    public static List<Integer> optionalDimensionList(Map<String, Object> map, String key)
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
                    for (Object item : list)
                    {
                        Integer result = objectToDimension(item);
                        if (result != null)
                        {
                            convertedList.add(result);
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
        if (map != null)
        {
            Integer result = objectToColor(map.get(key));
            if (result != null)
            {
                return result;
            }
        }
        return defaultValue;
    }

    public static int optionalDimension(Map<String, Object> map, String key, int defaultValue)
    {
        if (map != null)
        {
            Integer result = objectToDimension(map.get(key));
            if (result != null)
            {
                return result;
            }
        }
        return defaultValue;
    }


    // ---
    // Fetch and convert basic values
    // ---

    @Nullable
    public static Date optionalDate(Map<String, Object> map, String key, Date defaultValue)
    {
        if (map != null)
        {
            Object object = map.get(key);
            if (object instanceof String)
            {
                String stringDate = (String)object;
                List<String> formatterList = new ArrayList<>(Arrays.asList(
                        "yyyy-MM-dd'T'HH:mm:ss'Z'",
                        "yyyy-MM-dd'T'HH:mm:ssX",
                        "yyyy-MM-dd'T'HH:mm:ssZ",
                        "yyyy-MM-dd'T'HH:mm:ss",
                        "yyyy-MM-dd"
                ));
                for (String formatter : formatterList)
                {
                    try
                    {
                        DateFormat dateFormatter = new SimpleDateFormat(formatter, Locale.US);
                        if (formatter.endsWith("'Z'") || formatter.endsWith("Z") || formatter.endsWith("X"))
                        {
                            dateFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
                        }
                        return dateFormatter.parse(stringDate);
                    }
                    catch (Exception ignored)
                    {
                    }
                }
            }
        }
        return defaultValue;
    }

    @Nullable
    public static String optionalString(Map<String, Object> map, String key, String defaultValue)
    {
        if (map != null)
        {
            String result = objectToString(map.get(key));
            if (result != null)
            {
                return result;
            }
        }
        return defaultValue;
    }

    public static double optionalDouble(Map<String, Object> map, String key, double defaultValue)
    {
        if (map != null)
        {
            Double result = objectToDouble(map.get(key));
            if (result != null)
            {
                return result;
            }
        }
        return defaultValue;
    }

    public static float optionalFloat(Map<String, Object> map, String key, float defaultValue)
    {
        if (map != null)
        {
            Float result = objectToFloat(map.get(key));
            if (result != null)
            {
                return result;
            }
        }
        return defaultValue;
    }

    public static int optionalInteger(Map<String, Object> map, String key, int defaultValue)
    {
        if (map != null)
        {
            Integer result = objectToInteger(map.get(key));
            if (result != null)
            {
                return result;
            }
        }
        return defaultValue;
    }

    public static boolean optionalBoolean(Map<String, Object> map, String key, boolean defaultValue)
    {
        if (map != null)
        {
            Boolean result = objectToBoolean(map.get(key));
            if (result != null)
            {
                return result;
            }
        }
        return defaultValue;
    }


    // ---
    // Internal object conversion
    // ---

    @Nullable
    private static Integer objectToDimension(Object object)
    {
        if (object instanceof String)
        {
            String densityString = (String)object;
            float density;
            if (densityString.startsWith("$"))
            {
                if (dimensionLookup != null)
                {
                    Integer foundDimension = dimensionLookup.getDimension(densityString.substring(1));
                    if (foundDimension != null)
                    {
                        return foundDimension;
                    }
                }
                return null;
            }
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
            else if (densityString.endsWith("wp"))
            {
                densityString = densityString.substring(0, densityString.length() - 2);
                density = (float)Resources.getSystem().getDisplayMetrics().widthPixels / 100;
            }
            else if (densityString.endsWith("hp"))
            {
                densityString = densityString.substring(0, densityString.length() - 2);
                density = (float)Resources.getSystem().getDisplayMetrics().heightPixels / 100;
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
        return null;
    }

    @Nullable
    private static Integer objectToColor(Object object)
    {
        if (object instanceof String)
        {
            String colorString = (String)object;
            if (colorString.startsWith("$"))
            {
                if (colorLookup != null)
                {
                    Integer foundColor = colorLookup.getColor(colorString.substring(1));
                    if (foundColor != null)
                    {
                        return foundColor;
                    }
                }
                return null;
            }
            if (colorString.length() > 0 && colorString.charAt(0) != '#')
            {
                colorString = "#" + colorString;
            }
            try
            {
                return Color.parseColor(colorString);
            }
            catch (IllegalArgumentException ignored)
            {
            }
        }
        return null;
    }

    @Nullable
    private static String objectToString(Object object)
    {
        if (object instanceof String)
        {
            return (String)object;
        }
        else if (object instanceof Double || object instanceof Float || object instanceof Integer || object instanceof Boolean)
        {
            return object.toString();
        }
        return null;
    }

    @Nullable
    private static Double objectToDouble(Object object)
    {
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
            return ((Float)object).doubleValue();
        }
        else if (object instanceof Integer)
        {
            return ((Integer)object).doubleValue();
        }
        else if (object instanceof Boolean)
        {
            return ((Boolean)object) ? 1.0 : 0.0;
        }
        return null;
    }

    @Nullable
    private static Float objectToFloat(Object object)
    {
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
            return ((Integer)object).floatValue();
        }
        else if (object instanceof Boolean)
        {
            return ((Boolean)object) ? 1.0f : 0.0f;
        }
        return null;
    }

    @Nullable
    private static Integer objectToInteger(Object object)
    {
        if (object instanceof String)
        {
            try
            {
                return Integer.parseInt((String)object);
            }
            catch (IllegalArgumentException ignored)
            {
            }
            try
            {
                Double result = Double.parseDouble((String)object);
                return result.intValue();
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
        return null;
    }

    @Nullable
    private static Boolean objectToBoolean(Object object)
    {
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
        return null;
    }
}
