package client.impl;

import client.FileWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class ThreadSafeFileWriter implements FileWriter {
    private File _f;

    private Writer _writer;

    public ThreadSafeFileWriter(File f) throws Exception {
        _f = f;

        _writer = new OutputStreamWriter(
                new FileOutputStream(f), "utf-8");
    }

    @Override
    public synchronized void write(String data) {
        try {
            _writer.write(data + "\n");
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public synchronized void close() {
        try {
            _writer.close();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
