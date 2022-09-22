package pers.etherealss.demo.infrastructure.utils;

import com.alibaba.fastjson.JSONObject;
import pers.etherealss.demo.pojo.vo.ApiMsg;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author wtk
 * @description 向前端发送响应数据
 */
public class ResponseUtil {
    /**
     * @param response
     * @param msg
     * @param <T>
     * @throws IOException
     */
    public static <T> void send(HttpServletResponse response, ApiMsg<T> msg) throws IOException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg", msg);
        //发送给客户端
        response.getWriter().write(jsonObject.toJSONString());
    }

    /**
     * @param response
     * @param jsonObject
     * @throws IOException
     */
    public static void send(HttpServletResponse response, JSONObject jsonObject) throws IOException {
        response.getWriter().write(jsonObject.toJSONString());
    }

    /**
     * @param response
     * @param jsonString
     * @throws IOException
     */
    public static void send(HttpServletResponse response, String jsonString) throws IOException {
        response.getWriter().write(jsonString);
    }

}
