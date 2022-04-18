package com.zwy.zhxy.controller;

import com.zwy.zhxy.pojo.Admin;
import com.zwy.zhxy.pojo.LoginForm;
import com.zwy.zhxy.pojo.Student;
import com.zwy.zhxy.pojo.Teacher;
import com.zwy.zhxy.service.AdminService;
import com.zwy.zhxy.service.StudentService;
import com.zwy.zhxy.service.TeacherService;
import com.zwy.zhxy.util.*;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author 赵文渊
 * @version 1.0
 * @date 2022/4/16 1:26
 */
@RestController
@RequestMapping("/sms/system")
public class SystemController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private TeacherService teacherService;

    //GET
    //	http://localhost:9001/sms/system/getVerifiCodeImage
    /**
     * 获取验证码
     */
    @GetMapping("/getVerifiCodeImage")
    public void getVerifiCodeImage(HttpServletRequest request, HttpServletResponse response) {
        //获取图片
        BufferedImage image = CreateVerifyCodeImage.getverifyCodeImage();
        //获取图片上的验证码
        String verifyCode = new String(CreateVerifyCodeImage.getverifyCode());
        //将验证码文本放入 session 域
        HttpSession session = request.getSession();
        session.setAttribute("verifiCode", verifyCode);
        //将验证码图片相应给浏览器
        try {
            ImageIO.write(image, "JPEG", response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 登录验证
     */
    @PostMapping("/login")
    public Result login(@RequestBody LoginForm loginForm, HttpServletRequest request) {
        // 验证码是否有效
        HttpSession session = request.getSession();
        String sessionVerifyCode = (String) session.getAttribute("verifiCode");
        String loginVerifyCode = loginForm.getVerifiCode();
        if (StringUtils.isEmpty(sessionVerifyCode)) {
            return Result.fail().message("验证码失效，请刷新后重试");
        }
        if (!sessionVerifyCode.equalsIgnoreCase(loginVerifyCode)) {
            return Result.fail().message("验证码有误，请刷新后重试");
        }
        //从 session 域中移除现有验证码
        session.removeAttribute("verifiCode");
        //准备一个 map，存放用户响应数据
        Map<String, Object> map = new LinkedHashMap<>();
        //分用户类型进行校验
        Integer userType = loginForm.getUserType();
        switch (userType) {
            //管理员
            case 1:
                try {
                    Admin admin = adminService.login(loginForm);
                    if (null != admin) {
                        //用户类型和用户id转换为一个密文，以token的名称向客户端返回
                        map.put("token", JwtHelper.createToken(admin.getId().longValue(), 1));
                    } else {
                        throw new RuntimeException("用户名或密码有误");
                    }
                    return Result.ok(map);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    return Result.fail().message(e.getMessage());
                }
            //学生
            case 2:
                try {
                    Student student = studentService.login(loginForm);
                    if (null != student) {
                        //用户类型和用户id转换为一个密文，以token的名称向客户端返回
                        map.put("token", JwtHelper.createToken(student.getId().longValue(), 2));
                    } else {
                        throw new RuntimeException("用户名或密码有误");
                    }
                    return Result.ok(map);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    return Result.fail().message(e.getMessage());
                }

            //教师
            case 3:
                try {
                    Teacher teacher = teacherService.login(loginForm);
                    if (null != teacher) {
                        //用户类型和用户id转换为一个密文，以token的名称向客户端返回
                        map.put("token", JwtHelper.createToken(teacher.getId().longValue(), 3));
                    } else {
                        throw new RuntimeException("用户名或密码有误");
                    }
                    return Result.ok(map);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    return Result.fail().message(e.getMessage());
                }
        }
        return Result.fail().message("查无此用户");
    }

    /**
     *
     */
    @GetMapping("/getInfo")
    public Result getInfoByToken(@RequestHeader("token") String token) {
        //验证 token 是否过期
        boolean isExpiration = JwtHelper.isExpiration(token);
        if (isExpiration) {
            return Result.build(null, ResultCodeEnum.TOKEN_ERROR);
        }
        //从 token 中解析出用户 id
        Long userId = JwtHelper.getUserId(token);
        Integer userType = JwtHelper.getUserType(token);
        Map<String, Object> map = new LinkedHashMap<>();
        switch (userType) {
            case 1:
                Admin admin = adminService.getById(userId);
                map.put("userType", 1);
                map.put("user", admin);
                break;
            case 2:
                Student student = studentService.getById(userId);
                map.put("userType", 2);
                map.put("user", student);
                break;
            case 3:
                Teacher teacher = teacherService.getById(userId);
                map.put("userType", 3);
                map.put("user", teacher);
                break;
        }
        return Result.ok(map);
    }

    //POST/sms/system/headerImgUpload
    @ApiOperation("处理上传的头像图片")
    @PostMapping("/headerImgUpload")
    public Result<Object> headerImgUpload(@ApiParam("上传的头像图片") @RequestPart MultipartFile multipartFile,
                                          HttpServletRequest request) {
        String originalFilename = multipartFile.getOriginalFilename();
        String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        //文件后缀
        int i = originalFilename.lastIndexOf(".");
        String filename = uuid.concat(originalFilename.substring(i));
        //保存文件
        String portrait = "D:/ideaprojects/javaweb/zhxy/target/classes/public/upload/".concat(filename);
        try {
            multipartFile.transferTo(new File(portrait));
        } catch (IOException e) {
            e.printStackTrace();
            return Result.fail();
        }
        //响应图片路径 upload/...
        String path = "upload/".concat(filename);
        return Result.ok(path);
    }

    //POST
    //	http://localhost:9001/sms/system/updatePwd/123456（原密码）/qwerty（新密码）
    @PostMapping("/updatePwd/{oldPassword}/{newPassword}")
    public Result updatePwd(@PathVariable String oldPassword,
                            @PathVariable String newPassword,
                            @RequestHeader("token") String token) {
        Long userId = JwtHelper.getUserId(token);
        String userName = JwtHelper.getUserName(token);
        Integer userType = JwtHelper.getUserType(token);
        switch (userType) {
            case 1:
                Admin admin = adminService.getById(userId);
                String password = admin.getPassword();
                if (!password.equals(MD5.encrypt(oldPassword))) {
                    return Result.fail().message("原密码错误！！！");
                }
                admin.setPassword(MD5.encrypt(newPassword));
                boolean b = adminService.updateById(admin);
                if (!b) {
                    return Result.fail().message("操作失败！");
                }
                return Result.ok().message("操作成功！");
            case 2:
                Student student = studentService.getById(userId);
                String studentPassword = student.getPassword();
                if (!studentPassword.equals(MD5.encrypt(oldPassword))) {
                    return Result.fail().message("原密码错误！！！");
                }
                student.setPassword(MD5.encrypt(newPassword));
                boolean b2 = studentService.updateById(student);
                if (!b2) {
                    return Result.fail().message("操作失败！");
                }
                return Result.ok().message("操作成功！");

        }
        return Result.fail().message("出现错误");
    }



}
