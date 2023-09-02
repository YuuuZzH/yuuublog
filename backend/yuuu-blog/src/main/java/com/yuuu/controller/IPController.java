package com.yuuu.controller;

import com.yuuu.domain.ResponseResult;
import com.yuuu.utils.IPUtil;
import com.yuuu.utils.IpToAddressUtil;
import org.springframework.mobile.device.Device;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: yuuu
 * Description:
 * Email: ***REMOVED***
 * CreatedTime: 2023-08-22 15:55:15:55
 */
@RestController
public class IPController {
    @PostMapping("/ip")
    public ResponseResult login(HttpServletRequest request,Device device){

        String ipAddress = IPUtil.getIpAddr(request);
        String cityInfo = IpToAddressUtil.getCityInfo(ipAddress);
        Map<String,String> map = new HashMap<>();
        map.put("ip",ipAddress);
        map.put("address",cityInfo);
        String devName = "";
        if (device.isMobile()) {
            devName = "手机";
        } else if (device.isTablet()) {
            devName = "平板";
        } else if(device.isNormal()){
            devName = "PC";
        }else {
            devName = "其他";
        }
        map.put("dev", devName);
        return new ResponseResult().ok(map);
    }

}
