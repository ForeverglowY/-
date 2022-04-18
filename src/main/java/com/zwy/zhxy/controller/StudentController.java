package com.zwy.zhxy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zwy.zhxy.pojo.Clazz;
import com.zwy.zhxy.pojo.Student;
import com.zwy.zhxy.service.StudentService;
import com.zwy.zhxy.util.MD5;
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
@Api(tags = "学生控制器")
@RestController
@RequestMapping("/sms/studentController")
public class StudentController {

    @Autowired
    private StudentService studentService;

    //GET
    //	http://localhost:9001/sms/studentController/getStudentByOpr/1/3
    @ApiOperation("根据学生名称和班级名称模糊分页查询所有Student信息")
    @GetMapping("/getStudentByOpr/{pageNo}/{pageSize}")
    public Result getStudents(@ApiParam("分页查询的当前页码") @PathVariable Integer pageNo,
                              @ApiParam("分页查询的每页的数量") @PathVariable Integer pageSize,
                              @ApiParam("分页查询的查询条件") Student student) {
        Map<String, Object> map = new LinkedHashMap<>();
        //分页查询
        Page<Student> page = new Page<>(pageNo, pageSize);
        //通过 service 查询
        IPage<Student> pageRs = studentService.getClazzByOpr(page, student);
        return Result.ok(pageRs);
    }

    //POST
    //http://localhost:9001/sms/studentController/addOrUpdateStudent
    @ApiOperation("保存或者修改学生信息")
    @PostMapping("/addOrUpdateStudent")
    public Result addOrUpdateStudent(@ApiParam("JSON格式的学生信息") @RequestBody Student student) {

        Integer id = student.getId();
        if (null == id || 0 == id) {
            student.setPassword(MD5.encrypt(student.getPassword()));
        }
        boolean b = studentService.saveOrUpdate(student);
        if (b) {
            return Result.ok().message("操作成功");
        }
        return Result.fail().message("操作失败");
    }

    //DELETE
    //	http://localhost:9001/sms/studentController/delStudentById
    @ApiOperation("删除一个或多个Student")
    @DeleteMapping("/delStudentById")
    public Result delStudentById(@RequestBody List<Integer> ids) {
        boolean b = studentService.removeByIds(ids);
        if (b) {
            return Result.ok().message("删除成功");
        }
        return Result.fail().message("删除失败");
    }
}
