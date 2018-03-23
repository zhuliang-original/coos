package top.coos.web.websocket;


public abstract class WebSocketListener {

    public abstract String getName();

    protected String name = getName();

    private CoreWebSocket socket;

    public WebSocketListener() {

    }

    public final void init(CoreWebSocket socket) {
        this.socket = socket;

    }

    protected abstract void onMessage(String content);

    public void send(String content) {
        socket.send(name, content);
    }

}
