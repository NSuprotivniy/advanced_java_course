package edu.technopolis;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by nsuprotivniy on 15.12.16.
 */
public class LFUCacheTest{

    private LFUCache<String> cache = new LFUCache<>(10);

    @Before
    public void clearCache() {
        cache.clear();
    }

    @Test
    public void addCacheEntry() throws Exception {
        String str1 = "TestString";
        cache.addCacheEntry(1, str1);
        String str2 = cache.getCacheEntry(1);
        Assert.assertTrue("Cache entry should be equal to 'TestString'", str1.compareTo(str2) == 0);
    }

    @Test
    public void getLFUKey() throws Exception {
        String LFU = "LFU";
        String EFU = "EFU";
        String MFU = "MFU";

        cache.addCacheEntry(0, LFU);
        cache.addCacheEntry(1, EFU);
        cache.addCacheEntry(2, MFU);

        for (int i = 0; i < 10; i++) {
            if (i < 2) cache.getCacheEntry(0);
            if (i < 3) cache.getCacheEntry(1);
            if (i < 5) cache.getCacheEntry(2);
        }

        int i = cache.getLFUKey();

        Assert.assertEquals("LFU index should to be 0", i, 0);
    }


    @Test
    public void iterator() throws Exception {
        String[] str_array = new String[10];
        String[] cache_array = new String[10];

        for (int i = 0; i < 10; i++) {
            str_array[i] = Integer.toString(i);
            cache.addCacheEntry(i, str_array[i]);
        }

        int i = 0;
        for (String str: cache) {
            cache_array[i++] = str;
        }

        Assert.assertArrayEquals("Iterator sequence should to be equal to entry sequence",
                cache_array, str_array);
    }

}