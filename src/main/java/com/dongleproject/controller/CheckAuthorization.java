package com.dongleproject.controller;

import com.dongleproject.common.R;
import com.dongleproject.config.CodeCrypt;
import com.dongleproject.config.TimestampCrypt;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Title: CherkToken
 * @Author wyh@qq.com
 * @Package com.dongleproject.controller
 * @Date 2025/11/19 9:13
 * @description:
 */

@RestController
@RequestMapping("/api")
@CrossOrigin
public class CheckAuthorization {

    @GetMapping("/checkAuthorization")
    public R checkAuthorization() {

        String password = "1677FEDE1EDBBA5D53AC0CBAF1AC6E3C";

        String timestamp = "";
        try {
            timestamp = TimestampCrypt.encrypt(System.currentTimeMillis(), password);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Map<String, Object> data = new HashMap<>();
        data.put("result", "权限检查通过！");
        data.put("timestamp", timestamp);

        return R.ok().data("data", data);
    }

    @GetMapping("/getInit")
    public R getInit() {

        String password = "1677FEDE1EDBBA5D53AC0CBAF1AC6E3C";
        String code = "await engine.Engine3D.init({\n" +

                "            canvasConfig: descriptor.canvasConfig,\n" +
                "            beforeRender: descriptor.beforeRender,\n" +
                "            renderLoop: descriptor.renderLoop,\n" +
                "            lateRender: descriptor.lateRender,\n" +
                "            engineSetting: descriptor.engineSetting,\n" +
                "        });";

        String codestamp = "";
        try {
            codestamp = CodeCrypt.encrypt(code, password);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Map<String, Object> data = new HashMap<>();
        data.put("code", codestamp);

        return R.ok().data("data", data);
    }
}
