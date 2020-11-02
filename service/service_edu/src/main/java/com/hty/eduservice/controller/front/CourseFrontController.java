package com.hty.eduservice.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hty.eduservice.client.OrderClient;
import com.hty.eduservice.entity.EduCourse;
import com.hty.eduservice.entity.vo.chapter.ChapterVo;
import com.hty.eduservice.entity.vo.front.CourseFrontInfoVo;
import com.hty.eduservice.entity.vo.front.CourseFrontVo;
import com.hty.eduservice.service.EduChapterService;
import com.hty.eduservice.service.EduCourseService;
import com.hty.utils.JwtUtil;
import com.hty.utils.R;
import com.hty.utils.ordervo.CourseOrderVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * create by Semineces on 2020-08-21
 */
@RestController
@RequestMapping("/eduservice/coursefront")
@CrossOrigin
public class CourseFrontController {

    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduChapterService chapterService;

    @Autowired
    private OrderClient orderClient;

    @ApiOperation("条件查询带分页查询课程 当前是第几页 一页有几个")
    @PostMapping("/getFrontCourseList/{page}/{limit}")
    public R getFrontCourseList(@PathVariable Long page, @PathVariable Long limit,
                                @RequestBody(required = false) CourseFrontVo courseFrontVo) {
        Page<EduCourse> coursePage = new Page<>(page, limit);
        Map<String, Object> map = courseService.getCourseFrontList(coursePage, courseFrontVo);
        //返回分页所有数据
        return R.success().data(map);
    }

    @ApiOperation("课程详情")
    @GetMapping("/getFrontCourseInfo/{courseId}")
    public R getFrontCourseInfo(@PathVariable String courseId, HttpServletRequest request) {
        //根据课程id查询基本信息
        CourseFrontInfoVo courseFrontInfoVo = courseService.getBaseCourseInfo(courseId);
        //根据课程id查章节和小节
        List<ChapterVo> chapterVideoList = chapterService.getChapterVideoByCourseId(courseId);
        //根据课程id和用户id查询当前课程是否已经支付过了，需要调order，nacos
        String memberId = JwtUtil.getMemberIdByJwtToken(request);
        if (StringUtils.isEmpty(memberId)) {
            return R.error().message("未登录，请先登录");
        }
        Boolean isBuy = orderClient.isBuyCourse(courseId, memberId);
        return R.success()
                .data("courseWebVo", courseFrontInfoVo)
                .data("chapterVideoList", chapterVideoList)
                .data("isBuy", isBuy);
    }

    @ApiOperation("根据课程id查询课程信息")
    @PostMapping("/getCourseInfoOrder/{id}")
    public CourseOrderVo getCourseInfoOrder(@PathVariable String id) {
        CourseFrontInfoVo baseCourseInfo = courseService.getBaseCourseInfo(id);
        CourseOrderVo courseOrderVo = new CourseOrderVo();
        BeanUtils.copyProperties(baseCourseInfo, courseOrderVo);
        return courseOrderVo;
    }

}
