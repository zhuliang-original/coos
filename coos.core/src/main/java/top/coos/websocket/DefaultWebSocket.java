package top.coos.websocket;

import java.io.IOException;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;

public abstract class DefaultWebSocket {

    private Session session;

    protected final void send(String message) {
        session.getAsyncRemote().sendText(message);
    }

    protected abstract void onMessage(String message);

    protected void onClose() {
    }

    protected void onOpen() {
    }

    protected void onError(Throwable error) {
        error.printStackTrace();
    }

    @OnMessage
    public final void onMessage(String message, Session session) throws IOException, InterruptedException {
        this.session = session;
        onMessage(message);
    }

    @OnError
    public final void onError(Session session, Throwable error) {
        this.session = session;
        onError(error);
    }

    @OnOpen
    public final void onOpen(Session session) {
        this.session = session;
        onOpen();
    }

    @OnClose
    public final void onClose(Session session) {
        this.session = session;
        onClose();
    }
}
