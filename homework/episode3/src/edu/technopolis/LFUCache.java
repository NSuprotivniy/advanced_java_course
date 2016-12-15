package edu.technopolis;

import java.util.*;

import org.apache.commons.lang.RandomStringUtils;


public class LFUCache<T> implements Iterable<T> {

    class CacheEntry implements Comparable<CacheEntry>
    {
        private T data;
        private int frequency;

        // default constructor
        private CacheEntry()
        {}

        public T getData() {
            return data;
        }
        public void setData(T data) {
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

    private HashMap<Integer, CacheEntry> cacheMap = new LinkedHashMap<Integer, CacheEntry>();


/* LinkedHashMap is used because it has features of both HashMap and LinkedList.
 * Thus, we can get an entry in O(1) and also, we can iterate over it easily.
 * */

    public LFUCache(int initialCapacity)
    {
        this.initialCapacity = initialCapacity;
    }

    public void addCacheEntry(int key, T data)
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
            int entryKeyToBeRemoved = getLFUKey();
            cacheMap.remove(entryKeyToBeRemoved);

            CacheEntry temp = new CacheEntry();
            temp.setData(data);
            temp.setFrequency(0);

            cacheMap.put(key, temp);
        }
    }

    public int getLFUKey()
    {
        int key = 0;
        int minFreq = Integer.MAX_VALUE;

        for(Map.Entry<Integer, CacheEntry> entry : cacheMap.entrySet())
        {
            if(minFreq > entry.getValue().frequency)
            {
                key = entry.getKey();
                minFreq = entry.getValue().frequency;
            }
        }
        return key;
    }

    public T getCacheEntry(int key)
    {
        if(cacheMap.containsKey(key))  // cache hit
        {
            CacheEntry temp = cacheMap.get(key);
            temp.frequency++;
            cacheMap.put(key, temp);
            return temp.data;
        }
        return null; // cache miss
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

    public Iterator<T> iterator() {
        return new LFUCahceIterator();
    }

    private class LFUCahceIterator implements Iterator<T> {

        Iterator<Map.Entry<Integer, CacheEntry>> iterator = cacheMap.entrySet().iterator();

        @Override
        public boolean hasNext() {
            return iterator.hasNext();
        }

        @Override
        public T next() {
            return iterator.next().getValue().data;
        }

    }


    public static void main(String[] args) {
        LFUCache<String> cache = new LFUCache<>(10);

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
            cache.addCacheEntry(key , list.get(key));
            System.out.print(key + " ");
        }
        System.out.println("\n\nCache entry");

        for(String str: cache) {
            System.out.println(str);
        }


    }
}