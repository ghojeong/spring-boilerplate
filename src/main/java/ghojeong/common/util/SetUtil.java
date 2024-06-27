package ghojeong.common.util;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class SetUtil {
    private SetUtil() {}

    public static <K, V> void add(Map<K, Set<V>> map, K key, V value) {
        if (map == null || key == null || value == null) {
            return;
        }
        Set<V> set = getOrDefault(map, key);
        set.add(value);
        map.put(key, set);
    }

    public static <K, V> List<V> getOrDefaultList(Map<K, Set<V>> map, K key) {
        if (map == null || key == null) {
            return Collections.emptyList();
        }
        Set<V> set = getOrDefault(map, key);
        return List.copyOf(set);
    }

    private static <K, V> Set<V> getOrDefault(Map<K, Set<V>> map, K key) {
        Set<V> set = map.get(key);
        return set == null
                ? new LinkedHashSet<>()
                : set;
    }
}
