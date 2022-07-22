package com.vectorx.smartcampus.controller;

import com.vectorx.smartcampus.pojo.*;
import com.vectorx.smartcampus.service.AdminService;
import com.vectorx.smartcampus.service.StudentService;
import com.vectorx.smartcampus.service.TeacherService;
import com.vectorx.smartcampus.util.*;
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
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/sms/system")
public class SystemController
{
    @Autowired
    private AdminService adminService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private TeacherService teacherService;

    @GetMapping("/getVerifyCodeImage")
    public void getVerifyCodeImage(HttpServletRequest request, HttpServletResponse response) {
        BufferedImage verifyCodeImage = CreateVerifyCodeImage.getVerifyCodeImage();
        String verifyCode = new String(CreateVerifyCodeImage.getVerifyCode());
        request.getSession().setAttribute("verifyCode", verifyCode);
        try {
            ImageIO.write(verifyCodeImage, "JPEG", response.getOutputStream());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/login")
    public Result login(@RequestBody LoginForm loginForm, HttpServletRequest request) {
        final String verifyCodeAttr = "verifyCode";
        HttpSession session = request.getSession();
        String verifyCode = (String) session.getAttribute(verifyCodeAttr);
        if (StringUtils.isEmpty(verifyCode)) {
            return Result.fail().message("验证码已失效，请重新输入！");
        }
        String verifyCodeInput = loginForm.getVerifyCode();
        if (StringUtils.isEmpty(verifyCodeInput) || !verifyCodeInput.equalsIgnoreCase(verifyCode)) {
            return Result.fail().message("验证码有误，请重新输入！");
        }
        session.removeAttribute(verifyCodeAttr);

        // 分用户验证
        Map<String, Object> map = new LinkedHashMap<>();
        Integer userType = loginForm.getUserType();
        if (UserType.ADMIN.getUserType().equals(userType)) {
            Admin user = adminService.login(loginForm);
            if (user == null) {
                return Result.fail().message("用户名或密码错误");
            }
            map.put("token", JwtHelper.createToken(user.getId().longValue(), loginForm.getUserType()));
        }
        else if (UserType.STUDENT.getUserType().equals(userType)) {
            Student user = studentService.login(loginForm);
            if (user == null) {
                return Result.fail().message("用户名或密码错误");
            }
            map.put("token", JwtHelper.createToken(user.getId().longValue(), loginForm.getUserType()));
        }
        else if (UserType.TEACHER.getUserType().equals(userType)) {
            Teacher user = teacherService.login(loginForm);
            if (user == null) {
                return Result.fail().message("用户名或密码错误");
            }
            map.put("token", JwtHelper.createToken(user.getId().longValue(), loginForm.getUserType()));
        }
        else {
            return Result.fail().message("查无此用户！");
        }
        return Result.ok(map);
    }

    @GetMapping("/getInfo")
    public Result getInfo(@RequestHeader("token") String token) {
        if (JwtHelper.isExpiration(token)) {
            return Result.build(null, ResultCodeEnum.TOKEN_ERROR);
        }
        Integer userType = JwtHelper.getUserType(token);
        Long userId = JwtHelper.getUserId(token);
        // 分用户验证
        Map<String, Object> map = new LinkedHashMap<>();
        if (UserType.ADMIN.getUserType().equals(userType)) {
            Admin user = adminService.getAdminById(userId);
            map.put("userType", userType);
            map.put("user", user);
        }
        else if (UserType.STUDENT.getUserType().equals(userType)) {
            Student user = studentService.getStudentById(userId);
            map.put("userType", userType);
            map.put("user", user);
        }
        else if (UserType.TEACHER.getUserType().equals(userType)) {
            Teacher user = teacherService.getTeacherById(userId);
            map.put("userType", userType);
            map.put("user", user);
        }
        return Result.ok(map);
    }

    @PostMapping("/headerImgUpload")
    public Result headerImgUpload(@RequestPart("multipartFile") MultipartFile multipartFile) {
        String originalFilename = multipartFile.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        String newFilename = UUID.randomUUID().toString().replace("-", "").concat(suffix);
        try {
            multipartFile.transferTo(
                    new File("D:/workspace-mine/SmartCampus/target/classes/public/upload/".concat(newFilename)));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return Result.ok("upload/".concat(newFilename));
    }

    @PostMapping("/updatePwd/{oldPassword}/{newPassword}")
    public Result updatePwd(@PathVariable("oldPassword") String oldPassword,
            @PathVariable("newPassword") String newPassword, @RequestHeader("token") String token) {
        if (JwtHelper.isExpiration(token)) {
            return Result.fail().message("Token过期，请重新登录！");
        }
        Integer userType = JwtHelper.getUserType(token);
        Long userId = JwtHelper.getUserId(token);
        if (UserType.ADMIN.getUserType().equals(userType)) {
            Admin admin = adminService.getAdminById(userId);
            String nowPassword = admin.getPassword();
            if (StringUtils.isEmpty(oldPassword) || !MD5.encrypt(oldPassword).equals(nowPassword)) {
                return Result.fail().message("原密码不正确！");
            }
            admin.setPassword(MD5.encrypt(newPassword));
            adminService.saveOrUpdate(admin);
        }
        else if (UserType.STUDENT.getUserType().equals(userType)) {
            Student student = studentService.getStudentById(userId);
            String nowPassword = student.getPassword();
            if (StringUtils.isEmpty(oldPassword) || !MD5.encrypt(oldPassword).equals(nowPassword)) {
                return Result.fail().message("原密码不正确！");
            }
            student.setPassword(MD5.encrypt(newPassword));
            studentService.saveOrUpdate(student);
        }
        else if (UserType.TEACHER.getUserType().equals(userType)) {
            Teacher teacher = teacherService.getTeacherById(userId);
            String nowPassword = teacher.getPassword();
            if (StringUtils.isEmpty(oldPassword) || !MD5.encrypt(oldPassword).equals(nowPassword)) {
                return Result.fail().message("原密码不正确！");
            }
            teacher.setPassword(MD5.encrypt(newPassword));
            teacherService.saveOrUpdate(teacher);
        }
        return Result.ok();
    }
}
