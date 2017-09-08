package com.crescentflare.viewletcreator;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.crescentflare.viewletcreator.binder.ViewletBinder;
import com.crescentflare.viewletcreator.utility.ViewletMapUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Viewlet creator: manages viewlets
 * The main interface to register and create new view components
 */
public class ViewletCreator
{
    // ---
    // Singleton instance
    // ---

    private static ViewletCreator instance = new ViewletCreator();


    // ---
    // Registered viewlet list
    // ---

    private Map<String, Viewlet> registeredViewlets = new HashMap<>();
    private Map<String, Map<String, Map<String, Object>>> registeredStyles = new HashMap<>();


    // ---
    // Initialization
    // ---

    private ViewletCreator()
    {
    }


    // ---
    // Registration
    // ---

    public static void registerViewlet(String name, Viewlet viewlet)
    {
        if (name != null)
        {
            if (viewlet != null)
            {
                instance.registeredViewlets.put(name, viewlet);
            }
            else
            {
                instance.registeredViewlets.remove(name);
            }
        }
    }

    public static void registerStyle(String viewletName, String styleName, Map<String, Object> styleAttributes)
    {
        if (viewletName != null && styleName != null)
        {
            // First register that viewlet has styles (if not present)
            Map<String, Map<String, Object>> viewletStyles = instance.registeredStyles.get(viewletName);
            if (viewletStyles == null)
            {
                viewletStyles = new HashMap<>();
                instance.registeredStyles.put(viewletName, viewletStyles);
            }

            // Register or remove style
            if (styleAttributes != null)
            {
                viewletStyles.put(styleName, styleAttributes);
            }
            else
            {
                viewletStyles.remove(styleName);
            }
        }
    }

    public static List<String> registeredViewletNames()
    {
        return new ArrayList<>(instance.registeredViewlets.keySet());
    }


    // ---
    // Create and update
    // ---

    public static View create(Context context, Map<String, Object> attributes, ViewGroup parent)
    {
        return create(context, attributes, parent, null);
    }

    public static View create(Context context, Map<String, Object> attributes, ViewGroup parent, ViewletBinder binder)
    {
        String viewletName = findViewletNameInAttributes(attributes);
        if (viewletName != null)
        {
            Viewlet viewlet = instance.registeredViewlets.get(viewletName);
            if (viewlet != null)
            {
                View view = viewlet.create(context);
                if (view != null)
                {
                    Map<String, Object> mergedAttributes = mergeAttributes(attributes, attributesForStyle(viewletName, ViewletMapUtil.optionalString(attributes, "viewletStyle", null)));
                    viewlet.update(view, mergedAttributes, parent, binder);
                }
                return view;
            }
        }
        return null;
    }

    public static boolean inflateOn(View view, Map<String, Object> attributes, ViewGroup parent)
    {
        return inflateOn(view, attributes, parent, null);
    }

    public static boolean inflateOn(View view, Map<String, Object> attributes, ViewGroup parent, ViewletBinder binder)
    {
        String viewletName = findViewletNameInAttributes(attributes);
        if (viewletName != null)
        {
            Viewlet viewlet = instance.registeredViewlets.get(viewletName);
            if (viewlet != null)
            {
                Map<String, Object> mergedAttributes = mergeAttributes(attributes, attributesForStyle(viewletName, ViewletMapUtil.optionalString(attributes, "viewletStyle", null)));
                return viewlet.update(view, mergedAttributes, parent, binder);
            }
        }
        return false;
    }

    public static boolean canRecycle(View view, Map<String, Object> attributes)
    {
        if (view != null && attributes != null)
        {
            Viewlet viewlet = findViewletInAttributes(attributes);
            if (viewlet != null)
            {
                return viewlet.canRecycle(view, attributes);
            }
        }
        return false;
    }

    public static Viewlet findViewletInAttributes(Map<String, Object> attributes)
    {
        String viewletName = ViewletMapUtil.optionalString(attributes, "viewlet", null);
        if (viewletName != null)
        {
            return instance.registeredViewlets.get(viewletName);
        }
        return null;
    }

    public static String findViewletNameInAttributes(Map<String, Object> attributes)
    {
        return ViewletMapUtil.optionalString(attributes, "viewlet", null);
    }

    private static Map<String, Object> attributesForStyle(String viewletName, String styleName)
    {
        if (viewletName != null)
        {
            Map<String, Map<String, Object>> viewletStyles = instance.registeredStyles.get(viewletName);
            if (viewletStyles != null)
            {
                if (styleName != null && styleName.equals("default"))
                {
                    return viewletStyles.get(styleName);
                }
                return mergeAttributes(viewletStyles.get(styleName), viewletStyles.get("default"));
            }
        }
        return null;
    }

    private static Map<String, Object> mergeAttributes(Map<String, Object> givenAttributes, Map<String, Object> fallbackAttributes)
    {
        // Just return one of the attributes if the other is null
        if (fallbackAttributes == null)
        {
            return givenAttributes;
        }
        else if (givenAttributes == null)
        {
            return fallbackAttributes;
        }

        // Merge and return without modifying the originals
        Map<String, Object> mergedAttributes = new HashMap<>();
        for (String key : givenAttributes.keySet())
        {
            mergedAttributes.put(key, givenAttributes.get(key));
        }
        for (String key : fallbackAttributes.keySet())
        {
            if (!mergedAttributes.containsKey(key))
            {
                mergedAttributes.put(key, fallbackAttributes.get(key));
            }
        }
        return mergedAttributes;
    }


    // ---
    // Sub-viewlet utilities
    // ---

    public static Map<String, Object> attributesForSubViewlet(Object subViewletItem)
    {
        Map<String, Object> attributes = ViewletMapUtil.asStringObjectMap(subViewletItem);
        if (attributes != null)
        {
            String viewletName = findViewletNameInAttributes(attributes);
            if (viewletName != null)
            {
                return mergeAttributes(attributes, attributesForStyle(viewletName, ViewletMapUtil.optionalString(attributes, "viewletStyle", null)));
            }
        }
        return null;
    }

    public static List<Map<String, Object>> attributesForSubViewletList(Object subViewletItemList)
    {
        List<Map<String, Object>> viewletItemList = new ArrayList<>();
        if (subViewletItemList != null && subViewletItemList instanceof List<?>)
        {
            List<?> itemList = (List<?>) subViewletItemList;
            for (Object item : itemList)
            {
                Map<String, Object> attributes = ViewletMapUtil.asStringObjectMap(item);
                if (attributes != null)
                {
                    String viewletName = findViewletNameInAttributes(attributes);
                    if (viewletName != null)
                    {
                        viewletItemList.add(mergeAttributes(attributes, attributesForStyle(viewletName, ViewletMapUtil.optionalString(attributes, "viewletStyle", null))));
                    }
                }
            }
        }
        return viewletItemList;
    }


    // ---
    // The viewlet creation and updating interface
    // ---

    public interface Viewlet
    {
        View create(Context context);
        boolean update(View view, Map<String, Object> attributes, ViewGroup parent, ViewletBinder binder);
        boolean canRecycle(View view, Map<String, Object> attributes);
    }
}
