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

    public static View create(Context context, Map<String, Object> attributes, ViewGroup addToParent)
    {
        return create(context, attributes, addToParent, null);
    }

    public static View create(Context context, Map<String, Object> attributes, ViewGroup parent, ViewletBinder binder)
    {
        Viewlet viewlet = findViewletInAttributes(attributes);
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

    public static void inflateOn(View view, Map<String, Object> attributes, ViewGroup parent)
    {
        inflateOn(view, attributes, parent, null);
    }

    public static void inflateOn(View view, Map<String, Object> attributes, ViewGroup parent, ViewletBinder binder)
    {
        Viewlet viewlet = findViewletInAttributes(attributes);
        if (viewlet != null)
        {
            for (ViewletStyler styler : instance.registeredStylers)
            {
                styler.update(view, attributes, parent);
            }
            viewlet.update(view, attributes, parent, binder);
        }
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
