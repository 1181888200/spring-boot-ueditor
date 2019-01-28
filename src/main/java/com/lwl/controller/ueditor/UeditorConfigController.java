package com.lwl.controller.ueditor;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lwl.ueditor.ActionEnter;


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
