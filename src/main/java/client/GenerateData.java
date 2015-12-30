package client;

import client.impl.ThreadSafeFileWriter;
import java.io.File;
import java.util.Random;
import server.service.DateUtils;

/**
 * Created by lanhnguyen on 12/29/2015.
 */
public class GenerateData {
    public static final String NUMBER = "123456789";
    static Random rnd = new Random();

    public static void main(String[] args) throws Exception {
        String outDir = "D:\\coursework\\data";
        int num = 50000;

        FileWriter writer = new ThreadSafeFileWriter(new File(outDir,
                "data" + System.currentTimeMillis()));
        for (int i = 0; i<=num; i++) {
            String CarID = randomString(2, NUMBER)
                    + randomString(1, "ABCDEFGH")
                    + randomString(4, NUMBER);
            String time = DateUtils.generateDate(2014, 2015);
            String speed = randomString(3, "1234");
            String sensorID = randomString(3, NUMBER);
            String direction = randomString(1, "12");
            if (direction.equals("1")) {
                direction = "in";
            } else {
                direction = "out";
            }
            String record = CarID + "\t" + time + "\t" + speed + "\t" + sensorID + "\t" + direction;
            writer.write(record);
        }

        writer.close();

    }
    private static String randomString(int len, String text) {
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(text.charAt(rnd.nextInt(text.length())));
        return sb.toString();
    }
}
