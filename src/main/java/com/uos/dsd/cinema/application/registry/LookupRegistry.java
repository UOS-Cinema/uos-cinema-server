package com.uos.dsd.cinema.application.registry;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.function.Function;

public abstract class LookupRegistry<T> {

    private final Map<String, T> registryMap = new HashMap<>();

    public void register(List<T> values, Function<T, String> keyMapper) {

        registryMap.clear();
        values.forEach(value -> registryMap.put(keyMapper.apply(value), value));
    }

    public T get(String key) {

        T result = registryMap.get(key);
        if (result == null) {
            throw notFoundException();
        }
        return result;
    }

    public List<T> getAll() {

        return new ArrayList<>(registryMap.values());
    }

    protected abstract RuntimeException notFoundException();
}

