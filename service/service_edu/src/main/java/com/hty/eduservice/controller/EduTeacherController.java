package com.hty.eduservice.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hty.eduservice.entity.EduTeacher;
import com.hty.eduservice.entity.vo.TeacherQueryVo;
import com.hty.eduservice.service.EduTeacherService;
import com.hty.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 讲师 前端控制器
 *
 * @author Semineces
 * @since 2020-08-04
 */

@Api(tags = "讲师管理")
@RestController
@RequestMapping("/eduservice/teacher")
public class EduTeacherController {

    @Autowired
    private EduTeacherService eduTeacherService;

    @ApiOperation(value = "查询所有教师")
    @GetMapping("/findAll")
    public R findAllTeacher() {
        //wrapper 为限制条件，为null则全部查询
        List<EduTeacher> list = eduTeacherService.list(null);
        return R.success().data("items", list);
    }

    @ApiOperation(value = "删除教师")
    @DeleteMapping("/delete/{id}")
    public R deleteTeacher(@ApiParam(name = "id", value = "讲师id", required = true) @PathVariable String id) {
        return eduTeacherService.removeById(id) ? R.success() : R.error();
    }

    @ApiOperation(value = "分页查询教师")
    @GetMapping("/pageTeacher/{current}/{size}")
    public R pageTeacher(@PathVariable Long current, @PathVariable Long size) {
        //创建page对象 current代表当前页，size代表一页几个
        Page<EduTeacher> pageTeacher = new Page<>(current, size);
        //调用方法实现分页
        //该方法是底层封装，把分页的所有数据分装到这个Page对象里面
        eduTeacherService.page(pageTeacher, null);
        //获取总数以及，每页的list集合
        long total = pageTeacher.getTotal();
        List<EduTeacher> records = pageTeacher.getRecords();

        Map<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("records", records);
        return R.success().data(map);
    }

    @ApiOperation(value = "条件分页查询教师")
    @PostMapping("/pageTeacherCondition/{current}/{size}")
    public R pageTeacherCondition(@PathVariable Long current, @PathVariable Long size,
                                  @RequestBody(required = false) TeacherQueryVo teacherQueryVo) {
        //创建page对象
        Page<EduTeacher> pageTeacher = new Page<>(current, size);
        //构建wrapper条件
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        //判定条件是否为空，不为空再传
        String name = teacherQueryVo.getName();
        Integer level = teacherQueryVo.getLevel();
        String begin = teacherQueryVo.getBegin();
        String end = teacherQueryVo.getEnd();
        if (!StringUtils.isEmpty(name)) {
            //like 第一个值是数据表中字段 第二个是具体值
            wrapper.like("name", name);
        }
        if (!StringUtils.isEmpty(level)) {
            wrapper.eq("level", level);
        }
        if (!StringUtils.isEmpty(begin)) {
            //ge 大于等于
            wrapper.ge("gmt_create", begin);
        }
        if (!StringUtils.isEmpty(end)) {
            //le 小于等于
            wrapper.le("gmt_create", end);
        }

        //按照添加时间的降序排序
        wrapper.orderByDesc("gmt_create");

        //调用方法实现分页
        eduTeacherService.page(pageTeacher, wrapper);
        long total = pageTeacher.getTotal();
        List<EduTeacher> records = pageTeacher.getRecords();
        Map<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("records", records);

        return R.success().data(map);

    }

    @ApiOperation(value = "添加教师")
    @PostMapping("/addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher) {
        return eduTeacherService.save(eduTeacher) ? R.success() : R.error();
    }

    @ApiOperation(value = "根据id查教师")
    @GetMapping("/getTeacher/{id}")
    public R getTeacherById(@PathVariable String id) {

        /*try {
            int a = 10 / 0;
        } catch (Exception e) {
            //执行自定义异常
            throw new MyException(20001, "执行了自定义异常处理");
        }*/

        EduTeacher eduTeacher = eduTeacherService.getById(id);
        return R.success().data("teacher", eduTeacher);
    }

    @ApiOperation(value = "修改教师")
    @PostMapping("/updateTeacher")
    public R updateTeacher(@RequestBody EduTeacher eduTeacher) {
        return eduTeacherService.updateById(eduTeacher) ? R.success() : R.error();
    }

}

