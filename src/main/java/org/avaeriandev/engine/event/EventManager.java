package org.avaeriandev.engine.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventManager {

    private static Map<Class<? extends EngineEvent>, List<EngineListener>> listenerLookup = new HashMap<>();

    public static void registerEvent(Class<? extends EngineEvent> eventClazz) {
        listenerLookup.putIfAbsent(eventClazz, new ArrayList<>());
    }

    public static void registerListener(Class<? extends EngineEvent> eventClazz, EngineListener listener) {
        if(listenerLookup.containsKey(eventClazz)) {
            listenerLookup.get(eventClazz).add(listener);
        } else {
            System.out.println("Event " + eventClazz.getName() + " has not been registered!");
        }
    }

    public static void unregisterListener(Class<? extends EngineEvent> eventClazz, EngineListener listener) {
        if(listenerLookup.containsKey(eventClazz)) {
            listenerLookup.get(eventClazz).remove(listener);
        } else {
            System.out.println("Event " + eventClazz.getName() + " has not been registered!");
        }
    }

    public static void callEvent(EngineEvent event) {
        if(listenerLookup.containsKey(event.getClass())) {
            listenerLookup.get(event.getClass()).forEach(listener -> listener.onEvent(event));
        }
    }

}
