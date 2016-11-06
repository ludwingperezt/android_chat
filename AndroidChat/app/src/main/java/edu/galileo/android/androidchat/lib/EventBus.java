package edu.galileo.android.androidchat.lib;

/**
 * Created by ludwin on 5/11/16.
 */
public interface EventBus {
    void register(Object subscriber);
    void unregister(Object subscriber);
    void post(Object event);
}
