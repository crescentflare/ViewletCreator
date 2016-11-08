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
    private List<ViewletStyler> registeredStylers = new ArrayList<>();


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

    public static void registerStyler(ViewletStyler styler)
    {
        if (styler != null)
        {
            instance.registeredStylers.add(styler);
        }
    }


    // ---
    // Create and update
    // ---

    public static View create(Context context, String name, Map<String, Object> attributes, ViewGroup addToParent)
    {
        return create(context, name, attributes, addToParent, null);
    }

    public static View create(Context context, String name, Map<String, Object> attributes, ViewGroup parent, ViewletBinder binder)
    {
        Viewlet viewlet = instance.registeredViewlets.get(name);
        if (viewlet != null)
        {
            View view = viewlet.create(context);
            if (view != null)
            {
                for (ViewletStyler styler : instance.registeredStylers)
                {
                    styler.update(view, attributes, parent);
                }
                viewlet.update(view, attributes, parent, binder);
            }
            return view;
        }
        return null;
    }

    public static void inflateOn(View view, String name, Map<String, Object> attributes, ViewGroup parent)
    {
        inflateOn(view, name, attributes, parent, null);
    }

    public static void inflateOn(View view, String name, Map<String, Object> attributes, ViewGroup parent, ViewletBinder binder)
    {
        Map<String, Object> viewletAttributes = ViewletMapUtil.asStringObjectMap(attributes.get(name));
        if (viewletAttributes != null)
        {
            Viewlet viewlet = instance.registeredViewlets.get(name);
            if (viewlet != null)
            {
                for (ViewletStyler styler : instance.registeredStylers)
                {
                    styler.update(view, viewletAttributes, parent);
                }
                viewlet.update(view, viewletAttributes, parent, binder);
            }
        }
    }

    public static boolean canRecycle(View view, Map<String, Object> attributes)
    {
        if (view != null && attributes != null)
        {
            String viewletName = null;
            Viewlet viewlet = null;
            for (String key : attributes.keySet())
            {
                viewlet = instance.registeredViewlets.get(key);
                if (viewlet != null)
                {
                    viewletName = key;
                    break;
                }
            }
            if (viewletName != null)
            {
                Map<String, Object> viewletAttributes = ViewletMapUtil.asStringObjectMap(attributes.get(viewletName));
                if (viewletAttributes != null)
                {
                    return viewlet.canRecycle(view, viewletAttributes);
                }
            }
        }
        return false;
    }

    public static Viewlet findViewletInAttributes(Map<String, Object> attributes)
    {
        for (String key : attributes.keySet())
        {
            Viewlet viewlet = instance.registeredViewlets.get(key);
            if (viewlet != null)
            {
                return viewlet;
            }
        }
        return null;
    }

    public static String findViewletNameInAttributes(Map<String, Object> attributes)
    {
        for (String key : attributes.keySet())
        {
            Viewlet viewlet = instance.registeredViewlets.get(key);
            if (viewlet != null)
            {
                return key;
            }
        }
        return null;
    }


    // ---
    // The viewlet creation and updating interfaces
    // ---

    public interface Viewlet
    {
        View create(Context context);
        void update(View view, Map<String, Object> attributes, ViewGroup parent, ViewletBinder binder);
        boolean canRecycle(View view, Map<String, Object> attributes);
    }

    public interface ViewletStyler
    {
        void update(View view, Map<String, Object> attributes, ViewGroup parent);
    }
}
