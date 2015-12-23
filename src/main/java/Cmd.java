public class Cmd {
    public static void main(String[] args) {
        Server server = new Server(6789);
        server.acceptConnection();
    }
}
