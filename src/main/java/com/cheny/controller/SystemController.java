package com.cheny.controller;

import com.aliyuncs.exceptions.ClientException;
import com.cheny.pojo.Admin;
import com.cheny.pojo.LoginForm;
import com.cheny.pojo.Student;
import com.cheny.pojo.Teacher;
import com.cheny.service.AdminService;
import com.cheny.service.StudentService;
import com.cheny.service.TeacherService;
import com.cheny.util.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@Api(tags = "系统控制器")
@RestController
@RequestMapping("/sms/system")
public class SystemController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private TeacherService teacherService;

    /**
     * 修改密码
     * @param password
     *      原密码
     * @param newPassword
     *      新密码
     * @param token
     *      通过token指令获取账号类型和账号id
     * @return
     *      返回结果
     */
    @PostMapping("/updatePwd/{password}/{newPassword}")
    public Result updatePwd(@PathVariable String password,
                            @PathVariable String newPassword,
                            @RequestHeader("token") String token) {
        boolean expiration = JwtHelper.isExpiration(token);
        //判断token是否过期
        if (expiration) {
            return Result.fail().message("token失效，请重新登录后修改密码");
        }
        //从token中解析出用户id和用户的类型
        Long userId = JwtHelper.getUserId(token);
        Integer userType = JwtHelper.getUserType(token);
        if (userType == 1) {
            //根据id获取账号
            Admin admin = adminService.getById(userId);
            //如果输入的旧密码不正确，则返回
            if (!admin.getPassword().equals(MD5.encrypt(password))) {
                return Result.fail().message("原密码有误！");
            }
            admin.setPassword(MD5.encrypt(newPassword));
            //将数据库中旧的账号进行更新
            adminService.saveOrUpdate(admin);
        } else if (userType == 2) {
            Student student = studentService.getById(userId);
            //如果输入的旧密码不正确，则返回
            if (!student.getPassword().equals(MD5.encrypt(password))) {
                return Result.fail().message("原密码有误！");
            }
            student.setPassword(MD5.encrypt(newPassword));
            //将数据库中旧的账号进行更新
            studentService.saveOrUpdate(student);
        } else if (userType == 3) {
            Teacher teacher = teacherService.getById(userId);
            //如果输入的旧密码不正确，则返回
            if (!teacher.getPassword().equals(MD5.encrypt(password))) {
                return Result.fail().message("原密码有误！");
            }
            teacher.setPassword(MD5.encrypt(newPassword));
            //将数据库中旧的账号进行更新
            teacherService.saveOrUpdate(teacher);
        }
        return Result.ok();
    }

    @ApiOperation("文件上传统一入口")
    @PostMapping("/headerImgUpload")
    public Result headerImgUpload(@RequestPart("multipartFile") MultipartFile multipartFile) throws ClientException, IOException {
        //本地存储方式
        /*String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        String originalFilename = multipartFile.getOriginalFilename();
        int i = originalFilename.lastIndexOf(".");
        String newFileName = uuid.concat(originalFilename.substring(i));
        //保存文件
        String portraitPath =
                "D:/workspace/IDEAProjects/myCode/zhxy/myZhxy/target/classes/public/upload/".concat(newFileName);
        multipartFile.transferTo(new File(portraitPath));
        //响应图片的路径
        String path = "upload/".concat(newFileName);*/

        //阿里云OSS存储
        String url = AliOSSUtil.upload(multipartFile);
        return Result.ok(url);
    }

    /**
     * 根据携带的token获取对应的信息
     */
    @GetMapping("/getInfo")
    public Result getInfoByToken(@RequestHeader("token") String token) {
        boolean expiration = JwtHelper.isExpiration(token);
        //判断token是否过期
        if (expiration) {
            return Result.build(null, ResultCodeEnum.TOKEN_ERROR);
        }
        //从token中解析出用户id和用户的类型
        Long userId = JwtHelper.getUserId(token);
        Integer userType = JwtHelper.getUserType(token);

        Map<String, Object> map = new LinkedHashMap<>();
        if (userType == 1) {
            Admin admin = adminService.getById(userId);
            map.put("userType", userType);
            map.put("user", admin);
        } else if (userType == 2) {
            Student student = studentService.getById(userId);
            map.put("userType", userType);
            map.put("user", student);
        } else if (userType == 3) {
            Teacher teacher = teacherService.getById(userId);
            map.put("userType", userType);
            map.put("user", teacher);
        }
        return Result.ok(map);
    }

    /**
     * 登录验证
     */
    @PostMapping("/login")
    public Result login(@RequestBody LoginForm loginForm, HttpServletRequest request) {
        //验证码校验
        HttpSession session = request.getSession();
        String sessionVerifiCode = (String) session.getAttribute("verifiCode");
        String loginVerifCode = loginForm.getVerifiCode();
        if ("".equals(sessionVerifiCode) || null == sessionVerifiCode) {
            return Result.fail().message("验证码失效，请刷新后重试");
        }
        if (!sessionVerifiCode.equalsIgnoreCase(loginVerifCode)) {
            return Result.fail().message("验证码错误，请重新输入");
        }
        //从session域中移除现有验证码
        session.removeAttribute("verifiCode");
        //分用户类型校验
        Integer userType = loginForm.getUserType();
        //准备一个map用户存放响应的数据
        HashMap<String, Object> map = new LinkedHashMap<>();

        if (userType == 1) {
            //管理员账号类型
            Admin admin = adminService.selectAdminByNameAndPassword(loginForm.getUsername(),
                    MD5.encrypt(loginForm.getPassword()));
            if (admin != null) {
                //用户的类型和用户id转换成一个密文，以token的名称向客户端反馈
                String token = JwtHelper.createToken(admin.getId().longValue(), userType);
                map.put("token", token);
                return Result.ok(map);
            }
        } else if (userType == 2) {
            //学生账号类型
            Student student = studentService.selectAdminByNameAndPassword(loginForm.getUsername(),
                    MD5.encrypt(loginForm.getPassword()));
            if (student != null) {
                //用户的类型和用户id转换成一个密文，以token的名称向客户端反馈
                String token = JwtHelper.createToken(student.getId().longValue(), userType);
                map.put("token", token);
                return Result.ok(map);
            }
        } else if (userType == 3) {
                //老师账号类型
                Teacher teacher = teacherService.selectAdminByNameAndPassword(loginForm.getUsername(),
                        MD5.encrypt(loginForm.getPassword()));
                if (teacher != null) {
                    //用户的类型和用户id转换成一个密文，以token的名称向客户端反馈
                    String token = JwtHelper.createToken(teacher.getId().longValue(), userType);
                    map.put("token", token);
                    return Result.ok(map);
                }
        }
        return Result.fail().message("用户名或密码错误");
    }

    /**
     * 获取验证码
     */
    @GetMapping("/getVerifiCodeImage")
    public void getVerifiCodeImage(HttpServletRequest request, HttpServletResponse response) {
        //获取图片
        BufferedImage verifiCodeImage = CreateVerifiCodeImage.getVerifiCodeImage();
        //获取图片上的验证码
        String verifiCode = new String(CreateVerifiCodeImage.getVerifiCode());
        //将验证码文本放入session域，为下一次验证做准备
        HttpSession session = request.getSession();
        session.setAttribute("verifiCode", verifiCode);
        //将验证码图片响应给浏览器
        try {
            ImageIO.write(verifiCodeImage, "JPEG", response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
