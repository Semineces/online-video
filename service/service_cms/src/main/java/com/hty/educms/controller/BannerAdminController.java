package com.hty.educms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hty.educms.entity.CrmBanner;
import com.hty.educms.service.CrmBannerService;
import com.hty.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author Semineces
 * @since 2020-08-14
 */
@RestController
@RequestMapping("/educms/banneradmin")
@CrossOrigin
public class BannerAdminController {

    @Autowired
    private CrmBannerService crmBannerService;

    @ApiOperation("分页查询banner")
    @GetMapping("/pageBanner/{page}/{limit}")
    public R pageBanner(@PathVariable Long page, @PathVariable Long limit) {
        Page<CrmBanner> pageBanner = new Page<>(page, limit);
        crmBannerService.page(pageBanner, null);
        return R.success().data("items", pageBanner.getRecords()).data("total", pageBanner.getTotal());
    }

    @ApiOperation("添加banner")
    @PostMapping("/addBanner")
    public R addBanner(@RequestBody CrmBanner crmBanner) {
        crmBannerService.save(crmBanner);
        return R.success();
    }

    @ApiOperation("修改banner")
    @PostMapping("/update")
    public R update(@RequestBody CrmBanner crmBanner) {
        crmBannerService.updateById(crmBanner);
        return R.success();
    }

    @ApiOperation("删除banner")
    @DeleteMapping("/remove/{id}")
    public R remove(@PathVariable String id) {
        crmBannerService.removeById(id);
        return R.success();
    }

    @ApiOperation("根据id查询banner")
    @GetMapping("/get/{id}")
    public R get(@PathVariable String id) {
        CrmBanner crmBanner = crmBannerService.getById(id);
        return R.success().data("item", crmBanner);
    }

}

