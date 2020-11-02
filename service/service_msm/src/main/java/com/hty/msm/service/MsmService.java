package com.hty.msm.service;

import java.util.Map;

/**
 * create by Semineces on 2020-08-18
 */
public interface MsmService {

    boolean send(Map<String, Object> param, String phone);
}
