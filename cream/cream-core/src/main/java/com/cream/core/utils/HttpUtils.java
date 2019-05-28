/**
 * 
 */
package com.cream.core.utils;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author cream
 *
 */
public class HttpUtils {
    
    public static final String XML_HTTP_REQUEST = "XMLHttpRequest";
    
    public static final String X_REQUESTED_WITH = "X-Requested-With";

    /**
     * 获取当前请求ip
     * @param request
     * @return
     */
    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 判断是否为ajax请求
     * @param request
     * @return
     */
    public static boolean isAjaxRequest(HttpServletRequest request) {
        return request.getHeader("accept").indexOf("application/json") > -1
                || XML_HTTP_REQUEST.equals(request.getHeader(X_REQUESTED_WITH));
    }
    
    /**
     * 输出json
     * @param response
     * @param jsonStr
     * @throws IOException
     */
    public static void writeJson(HttpServletResponse response, String jsonStr) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.println(jsonStr);
        out.flush();
        out.close();
    }
}
