package com.hty.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * create by Semineces on 2020-08-13
 */
public interface VodService {

    String uploadVideo(MultipartFile file);

    void removeBatch(List<String> videoIdList);
}
