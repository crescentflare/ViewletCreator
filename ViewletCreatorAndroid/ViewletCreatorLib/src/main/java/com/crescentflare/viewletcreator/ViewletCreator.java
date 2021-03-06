package com.crescentflare.viewletcreator;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.crescentflare.viewletcreator.binder.ViewletBinder;
import com.crescentflare.viewletcreator.utility.ViewletMapUtil;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Viewlet creator: manages viewlets
 * The main interface to register and create new view components
 */
@SuppressWarnings({"unused", "WeakerAccess"})
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
    private List<String> mergeSubAttributes = new ArrayList<>();
    private List<String> excludeAttributes = new ArrayList<>();


    // ---
    // Initialization
    // ---

    private ViewletCreator()
    {
    }


    // ---
    // Registration
    // ---

    public static void registerViewlet(@Nullable String name, @Nullable Viewlet viewlet)
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

    public static void registerStyle(@Nullable String viewletName, @Nullable String styleName, @Nullable Map<String, Object> styleAttributes)
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

    @NotNull
    public static List<String> registeredViewletNames()
    {
        return new ArrayList<>(instance.registeredViewlets.keySet());
    }

    public static void setMergeSubAttributes(List<String> attributeNames)
    {
        instance.mergeSubAttributes = attributeNames;
    }

    public static void setExcludeAttributes(List<String> attributeNames)
    {
        instance.excludeAttributes = attributeNames;
    }


    // ---
    // Create and update
    // ---

    @Nullable
    public static View create(@NotNull Context context, @Nullable Map<String, Object> attributes, @Nullable ViewGroup parent)
    {
        return create(context, attributes, parent, null);
    }

    @Nullable
    public static View create(@NotNull Context context, @Nullable Map<String, Object> attributes, @Nullable ViewGroup parent, @Nullable ViewletBinder binder)
    {
        String viewletName = findViewletNameInAttributes(attributes);
        if (viewletName != null)
        {
            Viewlet viewlet = instance.registeredViewlets.get(viewletName);
            if (viewlet != null)
            {
                View view = viewlet.create(context);
                Map<String, Object> mergedAttributes = processAttributes(attributes, attributesForStyle(viewletName, ViewletMapUtil.optionalString(attributes, "viewletStyle", null)), instance.mergeSubAttributes, instance.excludeAttributes);
                viewlet.update(view, mergedAttributes, parent, binder);
                return view;
            }
        }
        return null;
    }

    public static boolean inflateOn(@NotNull View view, @Nullable Map<String, Object> attributes, @Nullable ViewGroup parent)
    {
        return inflateOn(view, attributes, parent, null);
    }

    public static boolean inflateOn(@NotNull View view, @Nullable Map<String, Object> attributes, @Nullable ViewGroup parent, @Nullable ViewletBinder binder)
    {
        String viewletName = findViewletNameInAttributes(attributes);
        if (viewletName != null)
        {
            Viewlet viewlet = instance.registeredViewlets.get(viewletName);
            if (viewlet != null)
            {
                Map<String, Object> mergedAttributes = processAttributes(attributes, attributesForStyle(viewletName, ViewletMapUtil.optionalString(attributes, "viewletStyle", null)), instance.mergeSubAttributes, instance.excludeAttributes);
                return viewlet.update(view, mergedAttributes, parent, binder);
            }
        }
        return false;
    }

    public static boolean canRecycle(@Nullable View view, @Nullable Map<String, Object> attributes)
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

    @Nullable
    public static Viewlet findViewletInAttributes(@Nullable Map<String, Object> attributes)
    {
        String viewletName = ViewletMapUtil.optionalString(attributes, "viewlet", null);
        if (viewletName != null)
        {
            return instance.registeredViewlets.get(viewletName);
        }
        return null;
    }

    @Nullable
    public static String findViewletNameInAttributes(@Nullable Map<String, Object> attributes)
    {
        return ViewletMapUtil.optionalString(attributes, "viewlet", null);
    }


    // ---
    // Sub-viewlet utilities
    // ---

    @Nullable
    public static Map<String, Object> attributesForSubViewlet(@Nullable Object subViewletItem)
    {
        Map<String, Object> attributes = ViewletMapUtil.asStringObjectMap(subViewletItem);
        if (attributes != null)
        {
            String viewletName = findViewletNameInAttributes(attributes);
            if (viewletName != null)
            {
                return processAttributes(attributes, attributesForStyle(viewletName, ViewletMapUtil.optionalString(attributes, "viewletStyle", null)), instance.mergeSubAttributes, instance.excludeAttributes);
            }
        }
        return null;
    }

    public static List<Map<String, Object>> attributesForSubViewletList(@Nullable Object subViewletItemList)
    {
        List<Map<String, Object>> viewletItemList = new ArrayList<>();
        if (subViewletItemList instanceof List<?>)
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
                        viewletItemList.add(processAttributes(attributes, attributesForStyle(viewletName, ViewletMapUtil.optionalString(attributes, "viewletStyle", null)), instance.mergeSubAttributes, instance.excludeAttributes));
                    }
                }
            }
        }
        return viewletItemList;
    }


    // ---
    // Attribute processing
    // ---

    @Nullable
    private static Map<String, Object> attributesForStyle(@Nullable String viewletName, @Nullable String styleName)
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

    @NotNull
    private static Map<String, Object> processAttributes(@Nullable Map<String, Object> givenAttributes, @Nullable Map<String, Object> fallbackAttributes, List<String> mergeSubAttributes, List<String> excludeAttributes)
    {
        Map<String, Object> result = mergeAttributes(givenAttributes, fallbackAttributes);
        for (String mergeSubAttribute : mergeSubAttributes)
        {
            Map<String, Object> item = ViewletMapUtil.asStringObjectMap(result.get(mergeSubAttribute));
            if (item != null)
            {
                result = mergeAttributes(item, result);
            }
        }
        for (String excludeAttribute : excludeAttributes)
        {
            result.remove(excludeAttribute);
        }
        return result;
    }

    @NotNull
    private static Map<String, Object> mergeAttributes(@Nullable Map<String, Object> givenAttributes, @Nullable Map<String, Object> fallbackAttributes)
    {
        // Just return one of the attributes if the other is null
        if (fallbackAttributes == null)
        {
            return givenAttributes != null ? givenAttributes : new HashMap<String, Object>();
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
    // The viewlet creation and updating interface
    // ---

    public interface Viewlet
    {
        @NotNull
        View create(@NotNull Context context);

        boolean update(@NotNull View view, @NotNull Map<String, Object> attributes, @Nullable ViewGroup parent, @Nullable ViewletBinder binder);
        boolean canRecycle(@NotNull View view, @NotNull Map<String, Object> attributes);
    }
}
