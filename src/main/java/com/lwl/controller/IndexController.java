package com.lwl.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 测试页面
 * @author lwl
 * @create 2019年1月28日 下午2:07:35
 * @version 1.0
 */
@Controller
@RequestMapping("/index")
public class IndexController {

	
	@RequestMapping("")
	public String index() {
		return "index";
	}
	
}
