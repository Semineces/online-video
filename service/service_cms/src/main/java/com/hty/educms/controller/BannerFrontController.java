package com.hty.educms.controller;

import com.hty.educms.entity.CrmBanner;
import com.hty.educms.service.CrmBannerService;
import com.hty.utils.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * create by Semineces on 2020-08-14
 */
@RestController
@RequestMapping("/educms/bannerfront")
@CrossOrigin
public class BannerFrontController {

    @Autowired
    private CrmBannerService crmBannerService;

    @ApiOperation("根据id进行降序排序 显示排序之后的前两条")
    @GetMapping("/getAllBanner")
    public R getAllBanner() {
        List<CrmBanner> list = crmBannerService.selectAllBanner();
        return R.success().data("list", list);
    }
}
