package server.service;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URI;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.ContentSummary;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import server.error.ServerException;

/**
 * Created by lanhnguyen on 12/23/2015.
 */
public class HDFSWriter {
    private FileSystem fs;
    private BufferedWriter bw = null;
    private String filePath;
    private long size = 0L;


    public HDFSWriter(FileSystem fs) {
        this.fs = fs;
    }

    public HDFSWriter(String namenode, String user, String path) {
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
        filePath = path + "/" + "data_" + System.currentTimeMillis();
        try {
            System.out.println("Create file " + filePath);
            bw = new BufferedWriter(new OutputStreamWriter(fs.create(new Path(filePath))));
        } catch (IOException e) {
            throw new ServerException("Cannot create file in path" + path);
        }

    }

    public synchronized void write(String path, String msg) {
        if (size >= 1000) {
            try {
                System.out.println("Roll fize " + filePath);
                bw.close();
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
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
