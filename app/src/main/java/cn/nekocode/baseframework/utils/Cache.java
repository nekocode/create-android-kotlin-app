package cn.nekocode.baseframework.utils;

import android.content.Context;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;

import cn.nekocode.baseframework.AppContext;

/**
 * Created by Nekocode on 2014/11/24 0024.
 */
public class Cache {
    private static final String MEMCACHE_PREFIX = "memcache_";
    private static HashMap<String, Object> map = new HashMap<>();
    private static Kryo kryo = new Kryo();

    public static void save(String fileName, Object obj) {
        try {
            FileOutputStream fo = AppContext.get().openFileOutput(fileName, Context.MODE_PRIVATE);
            Output output = new Output(fo);

            kryo.writeObject(output, obj);
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static <E>E load(String fileName, Class<E> c) {
        E rlt = null;
        try {
            FileInputStream fi = AppContext.get().openFileInput(fileName);
            Input input = new Input(fi);
            rlt = kryo.readObject(input, c);
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rlt;
    }

    public static void delete(String fileName) {
        AppContext.get().deleteFile(fileName);
    }

    public static void deleteAll() {
        File toDelete = new File("/data/data/" + AppContext.get().getPackageName() + "/files");

        if (toDelete != null && toDelete.exists() && toDelete.isDirectory()) {
            for (File item : toDelete.listFiles()) {
                item.delete();
            }
        }
    }

    public static <T>T getMemCache(String key, Class<T> classType) {
        key = MEMCACHE_PREFIX + key;

        T cache = (T) map.get(key);
        if(cache == null) {
            cache = load(key, classType);
            if(cache != null)
                map.put(key, cache);
        }
        return cache;
    }

    public static void putMemCache(String key, Object object) {
        key = MEMCACHE_PREFIX + key;

        map.put(key, object);
    }

    public static void saveMemCache(String key, Object object) {
        key = MEMCACHE_PREFIX + key;

        map.put(key, object);
        if(object != null)
            save(key, object);
    }

    public static void saveMemCache(String key) {
        key = MEMCACHE_PREFIX + key;

        if(map.containsKey(key)) {
            save(key, map.get(key));
        }
    }

    public static void removeMemCache(String key) {
        key = MEMCACHE_PREFIX + key;

        map.remove(key);
        delete(key);
    }

    public static void removeAllMemCache() {
        map.clear();

        File toDelete = new File("/data/data/" + AppContext.get().getPackageName() + "/files");
        if (toDelete != null && toDelete.exists() && toDelete.isDirectory()) {
            for (File item : toDelete.listFiles()) {
                if(item.getName().contains(MEMCACHE_PREFIX))
                    item.delete();
            }
        }
    }
}
