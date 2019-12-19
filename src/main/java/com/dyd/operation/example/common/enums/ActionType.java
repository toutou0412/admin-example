package com.dyd.operation.example.common.enums;

import java.util.Map;
import java.util.TreeMap;

/**
 * @author 林金汉
 * @ClassName: ActionType
 * @Description:
 * @date 2019/12/12 13:25
 */
public enum ActionType {

    ADD(0, "新增"),
    INFO(1, "查看"),
    EDIT(2, "修改"),
    ;

    private Integer key;
    private String value;

    ActionType(Integer key, String value) {
        this.key = key;
        this.value = value;
    }

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static boolean containKey(Integer key) {
        ActionType[] valueList = ActionType.values();
        if (valueList != null && valueList.length > 0) {
            for (ActionType type : valueList) {
                if (type.getKey().equals(key)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 返回map
     */
    public static Map<Integer, String> getValues() {
        Map<Integer, String> values = new TreeMap<Integer, String>();
        ActionType[] valueList = ActionType.values();
        if (valueList != null && valueList.length > 0) {
            for (ActionType type : valueList) {
                values.put(type.getKey(), type.getValue());
            }
        }
        return values;
    }

}
