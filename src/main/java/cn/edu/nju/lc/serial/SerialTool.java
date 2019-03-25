package cn.edu.nju.lc.serial;


import gnu.io.*;

import java.util.Enumeration;
import java.util.HashSet;

public abstract class SerialTool {

    private static String experimentPortFeature = "cu.usbmodem";


    /**
     * 打开串口
     * @param portName 端口名
     * @param baudrate 比特率
     * @return
     */
    public static SerialPort openPort(String portName, int baudrate) {
        //通过端口名识别端口
        CommPortIdentifier portIdentifier;
        try {
            portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
            //打开端口，并给端口名字和一个timeout（打开操作的超时时间）
            CommPort commPort = portIdentifier.open(portName, 2000);
            //判断是不是串口
            if (commPort instanceof SerialPort) {
                SerialPort serialPort = (SerialPort) commPort;
                try {
                    //设置一下串口的波特率等参数
                    serialPort.setSerialPortParams(baudrate, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
                } catch (UnsupportedCommOperationException e) {
                    System.out.println(e.getMessage());
                }
                return serialPort;
            }
            else {
                //不是串口
                System.out.println();
            }
        } catch (NoSuchPortException e) {
            System.out.println("NoSuchPortException" + e.getMessage());
        } catch (PortInUseException e) {
            System.out.println("PortInUseException" + e.getMessage());
        }
        return null;
    }



    public static String getExperimentPortName() {
        CommPortIdentifier.getPortIdentifiers();
        @SuppressWarnings("unchecked")
        Enumeration<CommPortIdentifier> portEnum = CommPortIdentifier.getPortIdentifiers();
        while (portEnum.hasMoreElements()) {
            CommPortIdentifier portIdentifier = portEnum.nextElement();
            String name = portIdentifier.getName();
            if (name != null && name.contains(experimentPortFeature)) {
                return name;
            }
        }
        throw new RuntimeException("get find the experiment port");
    }




}

