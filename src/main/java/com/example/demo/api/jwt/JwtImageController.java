package com.example.demo.api.jwt;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.config.common.BaseController;
import com.example.demo.config.common.WebResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(BaseController.JWT_URL+"/upload")
@Api(description = "图片相关接口 ",tags="图片接口  ")
public class JwtImageController extends BaseController{

	@Value("${uploadImageUrl}")
	private String uploadImageUrl;
	
	@ApiOperation(value = "图片上传", notes = "")
	@PostMapping("/upload")
	public WebResult upload(@RequestParam("file") MultipartFile file,HttpServletResponse response) throws UnsupportedEncodingException {
		 FileOutputStream out = null;
	        try {
	            byte[] bytes = file.getBytes();
	            String path = uploadImageUrl+file.getOriginalFilename();
	            File newFile = new File(path);
	            out = new FileOutputStream(newFile);
	            out.write(bytes);
	            out.flush();
	            System.out.println("成功保存至本地");
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                out.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	        return insertSuccess(file.getOriginalFilename());
	        //ImageUtils.getImage(file.getOriginalFilename(), response);
	}
}
