package edu.galileo.android.androidchat.lib;

/**
 * EXPLICACION: Esta es una clase que implementa una interfaz que se llama EventBus
 * A su vez, esta clase "envuelve" la librería EventBus de greenrobot.
 * Puede parecer exceso de trabajo pero en realidad la razón de hacerlo de esta forma
 * es que si en algún momento la librería eventBus de greenrobot cambia o se reemplaza,
 * lo único que se debe hacer es cambiar la implementación que se hace en ésta clase
 * pues la interfaz no cambia, por lo tanto el resto de clases sigue teniendo acceso a
 * los mismos métodos.
 *
 * Created by ludwin on 5/11/16.
 */
public class GreenRobotEventBus implements EventBus {

    private de.greenrobot.event.EventBus eventBus;

    private static class SingletonHolder {
        private static final GreenRobotEventBus INSTACE = new GreenRobotEventBus();
    }

    public static GreenRobotEventBus getInstance() {
        return SingletonHolder.INSTACE;
    }

    public GreenRobotEventBus() {
        this.eventBus = de.greenrobot.event.EventBus.getDefault();
    }

    @Override
    public void register(Object subscriber) {
        eventBus.register(subscriber);
    }

    @Override
    public void unregister(Object subscriber) {
        eventBus.unregister(subscriber);
    }

    @Override
    public void post(Object event) {
        eventBus.post(event);
    }
}
