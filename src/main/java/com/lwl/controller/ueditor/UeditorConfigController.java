package com.lwl.controller.ueditor;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lwl.ueditor.ActionEnter;


/**
 * 读取配置文件的入口，一般显示后台配置有问题，都是这个入口没有找到对应的文件
 * 这个入口要和ueditor.config.js中serverUrl保持一致
 * @author lwl
 * @create 2019年1月28日 下午2:09:57
 * @version 1.0
 */
@Controller
@RequestMapping("/ue")
public class UeditorConfigController {


	@ResponseBody
    @RequestMapping("/controller")
	public String defaultMethod(HttpServletRequest request,HttpServletResponse response) throws JSONException{
			response.setHeader("Content-Type" , "text/html");
			ServletContext application = request.getSession().getServletContext();
			String rootPath = application.getRealPath( "/" );
			String result = new ActionEnter( request, rootPath ).exec();
			return result;
	
	}
	
}
