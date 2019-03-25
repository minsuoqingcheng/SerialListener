package cn.edu.nju.lc.data;

import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

public enum DataType {

    NON_STREAM(1, "非流式"),
    STREAM(2, "流式")

    ;
    private int type;
    private String desc;

    private static final Map<Integer, DataType> i2DataType = Stream.of(DataType.values()).collect(toMap(DataType::getType, d -> d));

    DataType(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public int getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    public static DataType getDataType(int type) {
        return i2DataType.get(type);
    }
}