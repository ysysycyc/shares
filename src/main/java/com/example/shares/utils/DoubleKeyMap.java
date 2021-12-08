package com.example.shares.utils;

import com.google.common.collect.Maps;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author lijiawei
 */
public class DoubleKeyMap<K1, K2, V> {

    private final Map<K2, V> key2Map = Maps.newHashMap();
    private final Map<K1, K2> keyRelationMap = Maps.newHashMap();

    public void put(K1 k1, K2 k2, V v) {
        keyRelationMap.put(k1, k2);
        key2Map.put(k2, v);
    }

    public V getKey1(K1 k1) {
        K2 k2 = keyRelationMap.get(k1);
        return key2Map.get(k2);
    }

    public V getKey2(K2 k2) {
        return key2Map.get(k2);
    }

    public V get(Object o) {
        if (key2Map.containsKey(o)) {
            return getKey2((K2) o);
        }

        K2 k2 = keyRelationMap.get((K1) o);

        return key2Map.get(k2);
    }

    public void putAll(List<V> vList, Function<V, K1> f1, Function<V, K2> f2) {
        vList.forEach(v -> put(f1.apply(v), f2.apply(v), v));
    }

    public Collection<V> values() {
        return key2Map.values();
    }

    public void clear() {
        keyRelationMap.clear();
        key2Map.clear();
    }

    @Override
    public String toString() {
        return keyRelationMap.keySet().stream().map(k1 -> {
            K2 k2 = keyRelationMap.get(k1);
            return "k1=" + k1 + ",k2=" + k2 + ",value=" + key2Map.get(k2);
        }).collect(Collectors.joining(";"));
    }

    public static void main(String[] args) {

        DoubleKeyMap<Integer, String, Object> doubleKeyMap = new DoubleKeyMap<>();
        doubleKeyMap.put(1, "2", "1");
        doubleKeyMap.put(2, "1", "2");
        System.out.println(doubleKeyMap.get("1"));
    }

}
