package org.xorrr.fundsoverview.events;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.google.gwt.thirdparty.guava.common.collect.Maps;

public class EventBus {

    private final Map<EventType, List<EventHandler>> handlerMap = Maps.newHashMap();
    private static EventBus instance = null;

    private EventBus() {}

    public void addHandler(EventType type,
            EventHandler handler) {
        if (!handlerMap.containsKey(type)) 
            handlerMap.put(type, new LinkedList<EventHandler>());
        handlerMap.get(type).add(handler);
    }

    public void fireEvent(EventType type) {
        for (EventHandler handler : handlerMap.get(type)) {
            handler.handleEvent(type);
        }
    }

    public static EventBus getInstance() {
        if (instance == null) {
            instance = new EventBus();
        }
        return instance;
    }

}
