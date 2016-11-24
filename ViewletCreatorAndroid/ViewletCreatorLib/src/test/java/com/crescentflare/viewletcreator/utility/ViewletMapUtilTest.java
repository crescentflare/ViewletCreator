package com.crescentflare.viewletcreator.utility;

import junit.framework.Assert;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Utility test: map utility
 */
public class ViewletMapUtilTest
{
    @Test
    public void asStringObjectMap() throws Exception
    {
        Map<String, String> stringMap = new HashMap<>();
        Map<String, Integer> integerMap = new HashMap<>();
        Map<Integer, Date> dateMap = new HashMap<>();
        stringMap.put("test", "value");
        integerMap.put("test", 20);
        dateMap.put(0, new Date());
        Assert.assertNotNull(ViewletMapUtil.asStringObjectMap(stringMap));
        Assert.assertNotNull(ViewletMapUtil.asStringObjectMap(integerMap));
        Assert.assertNull(ViewletMapUtil.asStringObjectMap(dateMap));
    }

    @Test
    public void isMap() throws Exception
    {
        Assert.assertTrue(ViewletMapUtil.isMap(new HashMap<String, String>()));
        Assert.assertFalse(ViewletMapUtil.isMap(new ArrayList<String>()));
        Assert.assertFalse(ViewletMapUtil.isMap(new String()));
    }
}
