package edu.technopolis;

import java.util.*;

import org.apache.commons.lang.RandomStringUtils;

/*
   Класс реализует LFU кэш.
 */

public class LFUCache<K, V> implements Iterable<V> {

    // Класс содержит значение одного элемента кэша и частоту его использования.
    class CacheEntry implements Comparable<CacheEntry>
    {
        private V data;
        private int frequency;


        public V getData() {
            return data;
        }
        public void setData(V data) {
            this.data = data;
        }

        public int getFrequency() {
            return frequency;
        }
        public void setFrequency(int frequency) {
            this.frequency = frequency;
        }

        @Override
        public int compareTo(CacheEntry e) {
            if (e.frequency == this.frequency) return  0;
            else if (e.frequency <  this.frequency) return -1;
            else return  1;
        }
    }

    private int initialCapacity = 10;

    // Используется LinkedHashMap, который позволяет итерироваться по структуре для поиска LFU.
    private HashMap<K, CacheEntry> cacheMap = new LinkedHashMap<K, CacheEntry>();

    public LFUCache(int initialCapacity)
    {
        this.initialCapacity = initialCapacity;
    }

    public void add(K key, V data)
    {

        if(!isFull())
        {
            CacheEntry temp = new CacheEntry();
            temp.setData(data);
            temp.setFrequency(0);

            cacheMap.put(key, temp);
        }
        else
        {
            K entryKeyToBeRemoved = getLFUKey();
            cacheMap.remove(entryKeyToBeRemoved);

            CacheEntry temp = new CacheEntry();
            temp.setData(data);
            temp.setFrequency(0);

            cacheMap.put(key, temp);
        }
    }

    public K getLFUKey()
    {
        K key = null;
        int minFreq = Integer.MAX_VALUE;

        for(Map.Entry<K, CacheEntry> entry : cacheMap.entrySet())
        {
            if(minFreq > entry.getValue().frequency)
            {
                key = entry.getKey();
                minFreq = entry.getValue().frequency;
            }
        }
        return key;
    }

    public V get(K key)
    {
        if(cacheMap.containsKey(key))
        {
            CacheEntry temp = cacheMap.get(key);
            temp.frequency++;
            cacheMap.put(key, temp);
            return temp.data;
        }
        return null;
    }

    public void clear() {
        cacheMap.clear();
    }

    public boolean isFull()
    {
        if(cacheMap.size() == initialCapacity)
            return true;

        return false;
    }

    public Iterator<V> iterator() {
        return new LFUCahceIterator();
    }

    private class LFUCahceIterator implements Iterator<V> {

        Iterator<Map.Entry<K, CacheEntry>> iterator = cacheMap.entrySet().iterator();

        @Override
        public boolean hasNext() {
            return iterator.hasNext();
        }

        @Override
        public V next() {
            return iterator.next().getValue().data;
        }

    }


    public static void main(String[] args) {
        LFUCache<Integer, String> cache = new LFUCache<>(10);

        List<String> list = new ArrayList<>();
        RandomStringUtils randomString = new RandomStringUtils();
        for (int i = 0; i < 20; i++)
        {
            String str = randomString.randomAlphabetic(20);
            list.add(str);
            System.out.println(i + " " + str);
        }

        System.out.println("\nCache filling");
        Random randInt = new Random();
        for (int i = 0; i < 50; i++) {
            int key = randInt.nextInt(20);
            cache.add(key , list.get(key));
            System.out.print(key + " ");
        }
        System.out.println("\n\nCache entry");

        for(String str: cache) {
            System.out.println(str);
        }


    }
}