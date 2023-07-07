package sample;

public class ChatClient {
    public static String username;
    private int port;
    private String hostname;


    public ChatClient(String hostname, int port)
    {
        this.hostname = hostname;
        this.port = port;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getUsername()
    {
        return this.username;
    }

}
