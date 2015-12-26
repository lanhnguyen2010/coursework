//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.OutputStream;
//import java.util.Properties;
//import org.apache.hadoop.conf.Configuration;
//import org.apache.hadoop.fs.FileStatus;
//import org.apache.hadoop.fs.FileUtil;
//import org.apache.hadoop.fs.Path;
//import org.apache.hadoop.hdfs.DistributedFileSystem;
//import org.apache.hadoop.hdfs.MiniDFSCluster;
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.junit.Test;
//import server.cmd.Cmd;
//import server.service.HDFSWriter;
//
//import static org.junit.Assert.*;
//
//
//public class ServerTest extends BaseTest {
//    private static final String CONFIG_PATH = "D:\\workspace\\coursework\\src\\main\\resources\\config.properties";
//
//    private static MiniDFSCluster hdfsCluster;
//
//    @BeforeClass
//    public static void setUp() throws IOException {
//        File baseDir = new File("./target/hdfs/" + "a").getAbsoluteFile();
//        FileUtil.fullyDelete(baseDir);
//        Configuration conf = new Configuration();
//        conf.set(MiniDFSCluster.HDFS_MINIDFS_BASEDIR, baseDir.getAbsolutePath());
//        MiniDFSCluster.Builder builder = new MiniDFSCluster.Builder(conf);
//        hdfsCluster = builder.build();
//        hdfsURI = hdfsCluster.getNameNode().getHostAndPort();
//        System.out.println("hdfs: //" + hdfsURI);
//        writeConfig("hdfs://" + hdfsURI.replace("\\",""));
//
//    }
//
//    @Before
//    public void start() {
//        String[] args = new String[1];
//        args[0] = CONFIG_PATH;
//        //Cmd.main(args);
//    }
//
//    @Test
//    public void testListen() throws Exception {
//        HDFSWriter hdfsWriter = new HDFSWriter(hdfsCluster.getFileSystem());
//        hdfsWriter.write("/user/c/b/c/a/a.txt", "abc");
//        DistributedFileSystem dfs = hdfsCluster.getFileSystem();
//        FileStatus[] files = dfs.listStatus(new Path("/user"));
//        for (FileStatus file : files) {
//            System.out.println(file.getPath());
//        }
//
//    }
//
//    @Test
//    public void testDoSomething() throws Exception {
//
//    }
//
//    @Test
//    public void testRespondse() throws Exception {
//
//    }
//
//
//
//    private static void writeConfig(String namenode) {
//        Properties prop = new Properties();
//        OutputStream out = null;
//        try {
//            out = new FileOutputStream(CONFIG_PATH);
//            prop.setProperty("HDFS_NAMENODE", namenode);
//            prop.setProperty("HDFS_USER", "root");
//            prop.setProperty("SERVER_PORT", "6789");
//            prop.setProperty("HDFS_PATH", "/user/test");
//            prop.store(out, "CONFIG");
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                out.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//
//    }
//}