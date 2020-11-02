package com.hty.oss.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * create by Semineces on 2020-08-11
 */
public interface OssService {

    String uploadFileAvatar(MultipartFile file);
}
