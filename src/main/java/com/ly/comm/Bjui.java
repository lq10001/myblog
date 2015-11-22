package com.ly.comm;

import java.util.HashMap;
import java.util.Map;

public class Bjui {
    public static Map<String,String> rtnMap(boolean isOk,String tabId,boolean isClose)
    {
        Map map = new HashMap();
        map.put("statusCode", isOk ? "200":"300");
        map.put("message", isOk ? "操作成功":"操作失败");
        map.put("tabid", tabId);
        map.put("closeCurrent", isClose);
        return map;
    }
}