package cn.edu.nju.lc.serial;

import gnu.io.SerialPort;

import java.io.IOException;
import java.io.InputStream;

public class SerialListener {

    private SerialPort serialPort;

    public SerialListener(SerialPort serialPort) {
        this.serialPort = serialPort;
    }

    public byte[] receive() {
        InputStream in = null;
        byte[] bytes = null;
        try {
            in = serialPort.getInputStream();
            int buffLength = in.available();        //获取buffer里的数据长度

            while (buffLength != 0) {
                bytes = new byte[buffLength];    //初始化byte数组为buffer中数据的长度
                //noinspection ResultOfMethodCallIgnored
                in.read(bytes);
                buffLength = in.available();
            }
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
        return bytes;
    }


    public void close() {
        serialPort.close();
    }
}
