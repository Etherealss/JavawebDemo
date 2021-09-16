package pers.etherealss.demo.utils;

import pers.etherealss.demo.core.repository.annotation.DbField;

import java.lang.reflect.Field;
import java.util.*;

/**
 * 反射工具类
 * @author yohoyes
 */
public class ReflectUtil {

    /**
     * 通过反射获取pojo类的含有注解的字段
     * @param po
     * @param <T>
     * @return
     * @throws IllegalAccessException 抛出非法权限异常
     */
    public static <T> Map<String, Object> getParams(T po) throws IllegalAccessException {
        Map<String, Object> map = new HashMap<>(5);
        return getFieldsMap4Insert(po, map);
    }

    /**
     * 通过反射获取pojo类的含有注解的方法
     * 包含父类
     * @param object
     * @param <T>
     * @return
     */
    public static <T> Field[] getAllFields(T object) {
        Class<?> clazz = object.getClass();
        List<Field> fieldList = new ArrayList<>();
        while (clazz != null) {
            fieldList.addAll(new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())));
            clazz = clazz.getSuperclass();
        }
        Field[] fields = new Field[fieldList.size()];
        fieldList.toArray(fields);
        return fields;
    }

    /**
     * 获取插入时需要的简直对列表
     * @param po
     * @param <T>
     * @return
     * @throws IllegalAccessException
     */
    public static <T> Map<String, Object> getParamForInsert(T po) throws IllegalAccessException {
        Map<String, Object> map = new HashMap<String, Object>(16);
        return getFieldsMap4Insert(po, map);
    }

    /**
     * 获取update操作时需要的键值对参数列表，就是 set xxx = xxx 那些
     * @param po
     * @param <T>
     * @return
     * @throws IllegalAccessException
     */
    public static <T> Map<String, Object> getParamForUpdate(T po) throws IllegalAccessException {
        Map<String, Object> map = new HashMap<String, Object>(8);
        return getFieldsMap4Update(po, map);
    }


    /**
     * 拼接修改操作的sql语句片段
     * @param po
     * @param list
     * @param <T>
     * @return
     */
    public static <T> String getSqlFragment(T po, List<Object> list) throws IllegalAccessException {
        // 拼接update操作的sql 形如 set xxx = aaa , xxx = bbb
        StringBuilder sb = new StringBuilder(" set ");
        // 获取update的参数
        Map<String, Object> paramForUpdate = getParamForUpdate(po);
        // 遍历Map
        Iterator<Map.Entry<String, Object>> iterator = paramForUpdate.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> cur = iterator.next();
            sb.append(DbSqlUtil.setSql(cur.getKey(), cur.getValue(), list));
            if (iterator.hasNext()) {
                sb.append(" , ");
            }
        }
        return sb.toString();
    }

    /**
     * 拼接插入时的sql
     * @param po
     * @param list
     * @param <T>
     * @return
     */
    public static <T> String getSqlForInsert(T po, List<Object> list) {
        StringBuilder sb = new StringBuilder();
        try {
            Map<String, Object> paraForUpdate = getParamForInsert(po);
            Set<Map.Entry<String, Object>> set = paraForUpdate.entrySet();
            Iterator<Map.Entry<String, Object>> iterator = set.iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Object> cur = iterator.next();
                list.add(cur.getValue());
                sb.append(cur.getKey());
                if (iterator.hasNext()) {
                    sb.append(" , ");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * 获取实体类字段，用于insert操作
     * @param po 实体类
     * @param map
     * @param <T>
     * @return
     * @throws IllegalAccessException
     */
    private static <T> Map<String, Object> getFieldsMap4Insert(T po, Map<String, Object> map) throws IllegalAccessException {
        Class<?> clazz = po.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            DbField dbField = field.getAnnotation(DbField.class);
            if (dbField != null) {
                getFieldsMap(field, dbField, po, clazz, map, !dbField.insertIgnore());
            }
        }
        return map;
    }

    private static <T> Map<String, Object> getFieldsMap4Update(T po, Map<String, Object> map) throws IllegalAccessException {
        Class<?> clazz = po.getClass();
        for (Field f : clazz.getDeclaredFields()) {
            f.setAccessible(true);
            DbField dbField = f.getAnnotation(DbField.class);
            if (dbField != null) {
                getFieldsMap(f, dbField, po, clazz, map, dbField.update());
            }
        }
        return map;
    }

    private static <T> void getFieldsMap(Field f, DbField dbField, T po,
                                         Class<?> clazz, Map<String, Object> map,
                                         boolean condition) throws IllegalAccessException {
        Object value = f.get(po);
        if (value != null) {
            if (dbField != null && condition) {
                map.put(dbField.value(), value);
            }
        }
    }
}
