package com.bootcamp.demo.util.serviceLocator;

import java.util.HashMap;
import java.util.Map;

public class ServiceLocator {
    private static final Map<Class<?>, Object> services = new HashMap<>();

    public static <T extends Service> void register(Class<T> clazz, T service) {
        services.put(clazz, service);
    }
    public static <T extends Service> void unregister(Class<T> clazz) {
        services.remove(clazz);
    }

    public static <T extends Service> T get(Class<T> clazz) {
        return clazz.cast(services.get(clazz));
    }

    public static void clear() {
        services.clear();
    }
}
