package com.zwy.zhxy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zwy.zhxy.pojo.Teacher;
import com.zwy.zhxy.service.TeacherService;
import com.zwy.zhxy.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 赵文渊
 * @version 1.0
 * @date 2022/4/16 1:23
 */
@Api(tags = "教师控制器")
@RestController
@RequestMapping("/sms/teacherController")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;


    //GET
    //	http://localhost:9001/sms/teacherController/getTeachers/1/3
    @ApiOperation("根据老师名称模糊分页查询所有Grade信息")
    @GetMapping("/getTeachers/{pageNo}/{pageSize}")
    public Result getTeachersByPage(@ApiParam("分页查询的当前页码") @PathVariable Integer pageNo,
                                    @ApiParam("分页查询的每页的数量") @PathVariable Integer pageSize,
                                    @ApiParam("分页查询的条件") Teacher teacher) {
        Map<String, Object> map = new LinkedHashMap<>();
        //分页查询
        Page<Teacher> page = new Page<>(pageNo, pageSize);
        //通过 service 查询
        IPage<Teacher> pageRs = teacherService.getClazzByOpr(page, teacher);
        return Result.ok(pageRs);
    }

    //DELETE
    //	http://localhost:9001/sms/teacherController/deleteTeacher
    @ApiOperation("删除一个或多个Teacher")
    @DeleteMapping("/deleteTeacher")
    public Result deleteTeacher(@ApiParam("要删除Teacher的id集合") @RequestBody List<Integer> ids) {
        boolean b = teacherService.removeByIds(ids);
        if (!b) {
            return Result.fail().message("删除失败");
        }
        return Result.ok().message("删除成功");
    }

    //POST
    //	http://localhost:9001/sms/teacherController/saveOrUpdateTeacher
    @PostMapping("/saveOrUpdateTeacher")
    public Result saveOrUpdateTeacher(@ApiParam("JSON格式的Grade对象") @RequestBody Teacher teacher) {
        boolean b = teacherService.saveOrUpdate(teacher);
        if (!b) {
            return Result.fail().message("修改失败");
        }
        return Result.ok().message("修改成功");
    }

}
