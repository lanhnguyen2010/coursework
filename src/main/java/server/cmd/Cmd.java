package server.cmd;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import server.service.Server;

public class Cmd {
    public static void main(String[] args) {
        String configPath = args[0];
        //String configPath = "D:\\workspace\\coursework\\src\\main\\resources\\server.properties";
        Properties prop = new Properties();
        FileInputStream in = null;
        try {
            in = new FileInputStream(configPath);
            prop.load(in);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        int port = Integer.parseInt(prop.getProperty("SERVER_PORT"));
        String namenode = prop.getProperty("HDFS_NAMENODE");
        String hdfsUser = prop.getProperty("HDFS_USER");
        String path = prop.getProperty("HDFS_PATH");

        Server server = new Server(port, namenode, hdfsUser, path);
        server.acceptConnection();
    }
}
