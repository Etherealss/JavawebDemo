package pers.etherealss.demo.infrastructure.utils;

import pers.etherealss.demo.infrastructure.core.repository.annotation.DbField;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 根据传入的键值对实例化pojo
 * @author hoyoyes
 */
public class MapUtil {

    /**
     * 单对象
     * @param po
     * @param list
     * @param map
     * @param <T>
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static <T> T modelMapper(T po, Field[] list, Map<String, Object> map) throws IllegalAccessException, InstantiationException {
        T o = (T) po.getClass().newInstance();
        for (Field f : list) {
            DbField dbField = f.getAnnotation(DbField.class);
            if (dbField != null && map.containsKey(dbField.value())) {
                f.setAccessible(true);
                f.set(o, map.get(dbField.value()));
            }
        }
        return o;
    }

    /**
     * 多对象列表
     * 多个键值对列表
     * @param po
     * @param f
     * @param list
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> List<T> modelMapperForList(
            T po, Field[] f, List<Map<String, Object>> list)
            throws InstantiationException, IllegalAccessException {

        Iterator<Map<String, Object>> iterator = list.iterator();
        List<T> res = new ArrayList<T>();
        while (iterator.hasNext()) {
            T t = modelMapper(po, f, iterator.next());
            res.add(t);
        }
        return res;
    }
}
