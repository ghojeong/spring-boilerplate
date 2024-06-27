package ghojeong.common.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public final class ListUtil {
    private ListUtil() {}

    public static <K, V> void add(Map<K, List<V>> map, K key, V value) {
        if (map == null || key == null || value == null) {
            return;
        }
        List<V> list = getOrDefault(map, key);
        list.add(value);
        map.put(key, list);
    }

    public static <K, V> List<V> getOrDefault(Map<K, List<V>> map, K key) {
        List<V> list = map.get(key);
        return list == null
                ? new ArrayList<>()
                : list;
    }

    public static <V> List<V> getOrEmptyList(List<V> list) {
        return list == null
                ? Collections.emptyList()
                : list;
    }

    public static <T, R> List<R> mapOrEmptyList(
            List<T> list, Function<T, R> func
    ) {
        return getOrEmptyList(list).stream()
                .map(func).toList();
    }

    public static <T, R> List<R> mapWithIndex(List<T> list, BiFunction<Integer, T, R> func) {
        AtomicInteger index = new AtomicInteger();
        return getOrEmptyList(list).stream().map(
                it -> func.apply(index.getAndIncrement(), it)
        ).toList();
    }

    public static <T, K> Map<K, List<T>> groupingBy(
            List<T> list,
            Function<? super T, ? extends K> classifier
    ) {
        return list.stream().filter(
                it -> classifier.apply(it) != null
        ).collect(Collectors.groupingBy(classifier));
    }

    public static <T, K, A, D> Map<K, D> groupingBy(
            List<T> list,
            Function<? super T, ? extends K> classifier,
            Collector<? super T, A, D> downstream
    ) {
        return list.stream().filter(
                it -> classifier.apply(it) != null
        ).collect(Collectors.groupingBy(classifier, downstream));
    }
}
