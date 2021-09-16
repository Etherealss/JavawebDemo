package pers.etherealss.demo.utils;

import java.text.MessageFormat;
import java.util.List;

/**
 * 反射拼接sql
 * @author yohoyes
 */
public class DbSqlUtil {

    /**
     * 拼接update中的set字段
     * @param name
     * @param value
     * @param list
     * @return
     */
    public static String setSql(String name, Object value, List<Object> list) {
        list.add(value);
        return name+" = ?";
    }

    /**
     * 拼接where中的and字段
     * @param key
     * @param value
     * @param list
     * @return
     */
    public static String andSql(String key, Object value, List<Object> list) {
        list.add(value);
        String base = "and {0} = ?";
        return MessageFormat.format(base, key);
    }

    /**
     * 拼接插入时的？号
     * @param number
     * @return
     */
    public static String getQuestionForInsert(int number) {
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<number;i++) {
           sb.append(i==number-1 ? "?":"?,");
        }
        return sb.toString();
    }
}
