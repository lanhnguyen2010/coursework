package server.service;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URI;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import server.error.ServerException;

/**
 * Created by lanhnguyen on 12/23/2015.
 */
public class HDFSWriter {
    public static final String DONE_DIR = "/done";
    public static final String TMP = ".tmp";
    private FileSystem fs;
    private BufferedWriter bw = null;
    private String filePath;
    private long size = 0L;
    private long fileSize;
    private long timeout = 0L;
    private String fileName;


    public HDFSWriter(FileSystem fs) {
        this.fs = fs;
    }

    public HDFSWriter(String namenode, String user, String path, long fileSize) {
        this.fileSize = fileSize;
        try {
            Configuration conf = new Configuration();
            conf.set("fs.hdfs.impl",
                    org.apache.hadoop.hdfs.DistributedFileSystem.class.getName());
            conf.set("fs.file.impl",
                    org.apache.hadoop.fs.LocalFileSystem.class.getName());
            this.fs = FileSystem.get(URI.create(namenode), conf, user);
        } catch (IOException | InterruptedException e) {
            throw new ServerException("Cannot connect to HDFS", e);
        }
    }

    public void create(String path) throws ServerException {
        fileName = "/" + "data_" + System.currentTimeMillis() + TMP;
        filePath = path;
        try {
            System.out.println("Create file " + filePath + fileName);
            bw = new BufferedWriter(new OutputStreamWriter(fs.create(new Path(filePath + fileName))));
        } catch (IOException e) {
            throw new ServerException("Cannot create file in path" + path);
        }

    }

    public synchronized void write(String path, String msg) {
        if (size >= fileSize) {
            try {
                System.out.println("Roll fize " + filePath + fileName);
                bw.close();
                rename();
                bw = null;
                size = 0;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (bw == null) {
            create(path);
        }
        try {
            System.out.println("size : " + size);
            bw.write(msg);
            size += msg.length();
        } catch (IOException e) {
            throw new ServerException("Cannot write data to :" + path);
        }
    }

    public void close() {
        try {
            if (bw != null) {
                bw.close();
            }
            bw = null;
            rename();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void rename() {
        try {
            String donePath = filePath + DONE_DIR;
            if (!fs.exists(new Path(donePath))) {
                fs.mkdirs(new Path(donePath));
            }

            System.out.println("move " + fileName + " to done dir");
            fs.rename(new Path(filePath + fileName), new Path(donePath + fileName.replace(TMP, "")));
        } catch (IOException e) {
            throw new ServerException("Cannot rename file", e);
        }
    }

}
