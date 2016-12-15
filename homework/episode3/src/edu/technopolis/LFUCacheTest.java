package edu.technopolis;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by nsuprotivniy on 15.12.16.
 */
public class LFUCacheTest{

    private LFUCache<Integer, String> cache = new LFUCache<>(10);

    @Before
    public void clearCache() {
        cache.clear();
    }

    @Test
    public void addTest() throws Exception {
        String str1 = "TestString";
        cache.add(1, str1);
        String str2 = cache.get(1);
        Assert.assertTrue("Cache entry should be equal to 'TestString'", str1.compareTo(str2) == 0);
    }

    @Test
    public void getLFUKey() throws Exception {
        String LFU = "LFU";
        String EFU = "EFU";
        String MFU = "MFU";

        cache.add(0, LFU);
        cache.add(1, EFU);
        cache.add(2, MFU);

        for (int i = 0; i < 10; i++) {
            if (i < 2) cache.get(0);
            if (i < 3) cache.get(1);
            if (i < 5) cache.get(2);
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
            cache.add(i, str_array[i]);
        }

        int i = 0;
        for (String str: cache) {
            cache_array[i++] = str;
        }

        Assert.assertArrayEquals("Iterator sequence should to be equal to entry sequence",
                cache_array, str_array);
    }

    @Test
    public void LFUReplacementTest() {

        String LFU = "LFU";


        for (int i = 0; i < 5; i++) {
            cache.add(i, Integer.toString(i + 2));
            for (int j = 0; j < 15; j++) {
                cache.get(i);
            }
        }

        cache.add(5, LFU);
        for (int i = 0; i < 10; i++) {
            cache.get(5);
        }

        for (int i = 6; i < 10; i++) {
            cache.add(i, Integer.toString(i + 2));
            for (int j = 0; j < 20; j++) {
                cache.get(i);
            }
        }

        String str = "new string";

        cache.add(10, str);

        LFU = cache.get(5);

        Assert.assertEquals("LFU should be null", LFU, null);


    }

}