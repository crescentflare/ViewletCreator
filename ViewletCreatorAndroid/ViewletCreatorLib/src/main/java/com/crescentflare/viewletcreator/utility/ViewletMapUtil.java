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
@SuppressWarnings({"unused", "WeakerAccess"})
public class ViewletMapUtil
{
    // ---
    // Lookups
    // ---

    private static ViewletColorLookup colorLookup = null;
    private static ViewletDimensionLookup dimensionLookup = null;

    public static void setColorLookup(@Nullable ViewletColorLookup lookup)
    {
        colorLookup = lookup;
    }

    public static void setDimensionLookup(@Nullable ViewletDimensionLookup lookup)
    {
        dimensionLookup = lookup;
    }


    // ---
    // Map conversion
    // ---

    @Nullable
    @SuppressWarnings("unchecked")
    public static Map<String, Object> asStringObjectMap(@Nullable Object object)
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

    public static boolean isMap(@Nullable Object object)
    {
        return object instanceof Map<?, ?>;
    }


    // ---
    // Fetch and convert lists
    // ---

    @NotNull
    @SuppressWarnings("unchecked")
    public static List<Object> optionalObjectList(@Nullable Map<String, Object> map, @NotNull String key)
    {
        if (map != null)
        {
            Object object = map.get(key);
            if (object instanceof List<?>)
            {
                List<?> list = (List<?>)object;
                if (list.size() > 0)
                {
                    return (List<Object>)object;
                }
            }
        }
        return new ArrayList<>();
    }

    @NotNull
    public static List<String> optionalStringList(@Nullable Map<String, Object> map, @NotNull String key)
    {
        if (map != null)
        {
            Object object = map.get(key);
            if (object instanceof List<?>)
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
    public static List<Double> optionalDoubleList(@Nullable Map<String, Object> map, @NotNull String key)
    {
        if (map != null)
        {
            Object object = map.get(key);
            if (object instanceof List<?>)
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
    public static List<Float> optionalFloatList(@Nullable Map<String, Object> map, @NotNull String key)
    {
        if (map != null)
        {
            Object object = map.get(key);
            if (object instanceof List<?>)
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
    public static List<Integer> optionalIntegerList(@Nullable Map<String, Object> map, @NotNull String key)
    {
        if (map != null)
        {
            Object object = map.get(key);
            if (object instanceof List<?>)
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
    public static List<Boolean> optionalBooleanList(@Nullable Map<String, Object> map, @NotNull String key)
    {
        if (map != null)
        {
            Object object = map.get(key);
            if (object instanceof List<?>)
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
    public static List<Integer> optionalColorList(@Nullable Map<String, Object> map, @NotNull String key)
    {
        if (map != null)
        {
            Object object = map.get(key);
            if (object instanceof List<?>)
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
    public static List<Integer> optionalDimensionList(@Nullable Map<String, Object> map, @NotNull String key)
    {
        if (map != null)
        {
            Object object = map.get(key);
            if (object instanceof List<?>)
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

    public static int optionalColor(@Nullable Map<String, Object> map, @NotNull String key, int defaultValue)
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

    public static int optionalDimension(@Nullable Map<String, Object> map, @NotNull String key, int defaultValue)
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
    public static Date optionalDate(@Nullable Map<String, Object> map, @NotNull String key, @Nullable Date defaultValue)
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
    public static String optionalString(@Nullable Map<String, Object> map, @NotNull String key, @Nullable String defaultValue)
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

    public static double optionalDouble(@Nullable Map<String, Object> map, @NotNull String key, double defaultValue)
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

    public static float optionalFloat(@Nullable Map<String, Object> map, @NotNull String key, float defaultValue)
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

    public static int optionalInteger(@Nullable Map<String, Object> map, @NotNull String key, int defaultValue)
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

    public static boolean optionalBoolean(@Nullable Map<String, Object> map, @NotNull String key, boolean defaultValue)
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
    private static Integer objectToDimension(@Nullable Object object)
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
    private static Integer objectToColor(@Nullable Object object)
    {
        if (object instanceof String)
        {
            // Handle lookup
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

            // Handle formatted color string
            if (colorString.startsWith("h") || colorString.startsWith("H"))
            {
                // Obtain color components for possible hue, saturation, value (brightness) / luminousity and alpha
                int colorComponents[] = { 0, 100, 100, 100, 100 };
                int inColorComponent = 0;
                boolean useLuminousity = false;
                for (int i = 0; i < colorString.length(); i++)
                {
                    char character = colorString.charAt(i);
                    if (character >= '0' && character <= '9')
                    {
                        if (inColorComponent < colorComponents.length)
                        {
                            colorComponents[inColorComponent] = colorComponents[inColorComponent] * 10 + character - '0';
                        }
                    }
                    else
                    {
                        switch (character)
                        {
                            case 'H':
                            case 'h':
                                inColorComponent = 0;
                                break;
                            case 'S':
                            case 's':
                                inColorComponent = 1;
                                break;
                            case 'V':
                            case 'v':
                                inColorComponent = 2;
                                useLuminousity = false;
                                break;
                            case 'L':
                            case 'l':
                                inColorComponent = 3;
                                useLuminousity = true;
                                break;
                            case 'A':
                            case 'a':
                                inColorComponent = 4;
                                break;
                            default:
                                inColorComponent = 9999;
                        }
                        if (inColorComponent < colorComponents.length)
                        {
                            colorComponents[inColorComponent] = 0;
                        }
                    }
                }

                // When in HSL (luminousity) color space, convert to HSV first
                float saturation = colorComponents[1] / 100.0f;
                float brightness = colorComponents[2] / 100.0f;
                if (useLuminousity)
                {
                    float luminousity = colorComponents[3] / 100.0f;
                    float x = saturation * Math.min(1 - luminousity, luminousity);
                    saturation = 2 * x / Math.max(luminousity + x, 0.000000001f);
                    brightness = luminousity + x;
                }

                // No saturation
                float hue = colorComponents[0];
                int alpha = colorComponents[4] * 255 / 100;
                int intBrightness = (int)(brightness * 255);
                if (saturation == 0)
                {
                    return (alpha << 24) | (intBrightness << 16) | (intBrightness << 8) | intBrightness;
                }

                // Calculate intermediate values
                float angle = (hue >= 360 ? 0 : hue);
                float sector = angle / 60;
                float factorial = sector - (float)Math.floor(sector);
                int p = (int)(brightness * (1 - saturation) * 255);
                int q = (int)(brightness * (1 - (saturation * factorial)) * 255);
                int t = (int)(brightness * (1 - (saturation * (1 - factorial))) * 255);

                // Convert to color
                switch((int)Math.floor(sector))
                {
                    case 0:
                        return (alpha << 24) | (intBrightness << 16) | (t << 8) | p;
                    case 1:
                        return (alpha << 24) | (q << 16) | (intBrightness << 8) | p;
                    case 2:
                        return (alpha << 24) | (p << 16) | (intBrightness << 8) | t;
                    case 3:
                        return (alpha << 24) | (p << 16) | (q << 8) | intBrightness;
                    case 4:
                        return (alpha << 24) | (t << 16) | (p << 8) | intBrightness;
                    default:
                        return (alpha << 24) | (intBrightness << 16) | (p << 8) | q;
                }
            }
            else
            {
                // Prepare conversion
                int characterCount = colorString.length();
                int startChar = 0;
                if (colorString.startsWith("#"))
                {
                    startChar = 1;
                    characterCount--;
                }

                // Continue conversion, only allow formats with 3/4 characters (short RGB or ARGB) and 6/8 characters (normal RGB or ARGB)
                if (characterCount == 3 || characterCount == 4 || characterCount == 6 || characterCount == 8)
                {
                    try
                    {
                        long rgbValue = Long.parseLong(colorString.substring(startChar), 16);
                        if (characterCount <= 4)
                        {
                            int alpha = 0xf;
                            int red = (int)((rgbValue & 0xf00) >> 8);
                            int green = (int)((rgbValue & 0xf0) >> 4);
                            int blue = (int)(rgbValue & 0xf);
                            if (characterCount == 4)
                            {
                                alpha = (int)((rgbValue & 0xf000) >> 12);
                            }
                            return ((alpha * 255 / 15) << 24) | ((red * 255 / 15) << 16) | ((green * 255 / 15) << 8) | (blue * 255 / 15);
                        }
                        else
                        {
                            if (characterCount == 6)
                            {
                                rgbValue |= 0xff000000;
                            }
                            return (int)rgbValue;
                        }
                    }
                    catch (Exception ignored)
                    {
                    }
                }
            }
        }
        return null;
    }

    @Nullable
    private static String objectToString(@Nullable Object object)
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
    private static Double objectToDouble(@Nullable Object object)
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
    private static Float objectToFloat(@Nullable Object object)
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
    private static Integer objectToInteger(@Nullable Object object)
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
    private static Boolean objectToBoolean(@Nullable Object object)
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
