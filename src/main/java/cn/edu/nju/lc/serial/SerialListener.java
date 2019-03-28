package cn.edu.nju.lc.serial;

import gnu.io.SerialPort;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class SerialListener {

    private SerialPort serialPort;

    public SerialListener(SerialPort serialPort) {
        this.serialPort = serialPort;
    }

    public String receive() {
        InputStream in = null;
        try {
            in = serialPort.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
            return bufferedReader.readLine();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch(IOException e) {
                System.out.println(e.getMessage());
            }

        }
        return null;
    }


    public void close() {
        serialPort.close();
    }
}
