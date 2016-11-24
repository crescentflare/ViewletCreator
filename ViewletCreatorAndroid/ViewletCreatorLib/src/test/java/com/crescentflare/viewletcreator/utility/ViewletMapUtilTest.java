package com.crescentflare.viewletcreator.utility;

import junit.framework.Assert;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.junit.Assert.*;

/**
 * Utility test: map utility
 */
public class ViewletMapUtilTest
{
    // ---
    // Map conversion
    // ---

    @Test
    public void asStringObjectMap() throws Exception
    {
        Map<String, String> stringMap = new HashMap<>();
        Map<String, Integer> integerMap = new HashMap<>();
        Map<Double, Date> dateMap = new HashMap<>();
        stringMap.put("test", "value");
        integerMap.put("test", 20);
        dateMap.put(0.2, new Date());
        Assert.assertNotNull(ViewletMapUtil.asStringObjectMap(stringMap));
        Assert.assertNotNull(ViewletMapUtil.asStringObjectMap(integerMap));
        Assert.assertNull(ViewletMapUtil.asStringObjectMap(dateMap));
    }

    @Test
    public void isMap() throws Exception
    {
        Assert.assertTrue(ViewletMapUtil.isMap(new HashMap<String, String>()));
        Assert.assertFalse(ViewletMapUtil.isMap(new ArrayList<String>()));
        Assert.assertFalse(ViewletMapUtil.isMap("string"));
    }


    // ---
    // Fetch and convert lists
    // ---

    @Test
    public void optionalObjectList() throws Exception
    {
        Map<String, Object> map = new HashMap<>();
        map.put("list", new ArrayList<>(Arrays.asList("item", "item2")));
        map.put("string", "string");
        Assert.assertEquals(2, ViewletMapUtil.optionalObjectList(map, "list").size());
        Assert.assertEquals(0, ViewletMapUtil.optionalObjectList(map, "string").size());
    }

    @Test
    public void optionalStringList() throws Exception
    {
        Map<String, Object> map = new HashMap<>();
        map.put("strings", new ArrayList<>(Arrays.asList("first", "second")));
        map.put("ints", new ArrayList<>(Arrays.asList(10, 64, 5)));
        Assert.assertEquals(Arrays.asList("first", "second"), ViewletMapUtil.optionalStringList(map, "strings"));
        Assert.assertEquals(Arrays.asList("10", "64", "5"), ViewletMapUtil.optionalStringList(map, "ints"));
    }

    @Test
    public void optionalDoubleList() throws Exception
    {
        Map<String, Object> map = new HashMap<>();
        map.put("strings", new ArrayList<>(Arrays.asList("4.23", "16", "1.2345")));
        map.put("doubles", new ArrayList<>(Arrays.asList(4.2, 12.11)));
        Assert.assertEquals(Arrays.asList(4.23, 16.0, 1.2345), ViewletMapUtil.optionalDoubleList(map, "strings"));
        Assert.assertEquals(Arrays.asList(4.2, 12.11), ViewletMapUtil.optionalDoubleList(map, "doubles"));
    }

    @Test
    public void optionalFloatList() throws Exception
    {
        Map<String, Object> map = new HashMap<>();
        map.put("floats", new ArrayList<>(Arrays.asList(5f, 823.123f, 11f)));
        map.put("ints", new ArrayList<>(Arrays.asList(9, 3214, 41, 22)));
        Assert.assertEquals(Arrays.asList(5f, 823.123f, 11f), ViewletMapUtil.optionalFloatList(map, "floats"));
        Assert.assertEquals(Arrays.asList(9f, 3214f, 41f, 22f), ViewletMapUtil.optionalFloatList(map, "ints"));
    }

    @Test
    public void optionalIntegerList() throws Exception
    {
        Map<String, Object> map = new HashMap<>();
        map.put("ints", new ArrayList<>(Arrays.asList(99, 5126)));
        map.put("strings", new ArrayList<>(Arrays.asList("4.25", "87", "234.8", "11")));
        Assert.assertEquals(Arrays.asList(99, 5126), ViewletMapUtil.optionalIntegerList(map, "ints"));
        Assert.assertEquals(Arrays.asList(4, 87, 234, 11), ViewletMapUtil.optionalIntegerList(map, "strings"));
    }

    @Test
    public void optionalBooleanList() throws Exception
    {
        Map<String, Object> map = new HashMap<>();
        map.put("strings", new ArrayList<>(Arrays.asList("true", "false")));
        map.put("ints", new ArrayList<>(Arrays.asList(0, 5, 1)));
        map.put("booleans", new ArrayList<>(Arrays.asList(false, false, true)));
        Assert.assertEquals(Arrays.asList(true, false), ViewletMapUtil.optionalBooleanList(map, "strings"));
        Assert.assertEquals(Arrays.asList(false, true, true), ViewletMapUtil.optionalBooleanList(map, "ints"));
        Assert.assertEquals(Arrays.asList(false, false, true), ViewletMapUtil.optionalBooleanList(map, "booleans"));
    }
}
