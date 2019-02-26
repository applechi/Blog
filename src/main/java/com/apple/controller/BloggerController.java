package com.apple.controller;

import com.apple.model.Blogger;
import com.apple.service.BloggerService;
import com.apple.util.CryptographyUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/blogger")
public class BloggerController {

	@Resource
	private BloggerService bloggerService;

	@RequestMapping("/login")
	public String login(Blogger blogger, HttpServletRequest request){
		Subject subject= SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(blogger.getUserName(), CryptographyUtil.md5(blogger.getPassword(), "java1234"));

		try {
			subject.login(token); // 登录验证
			return "redirect:/admin/main.jsp";
		} catch (AuthenticationException e) {
			e.printStackTrace();
			//如果账号密码错误，回显填写的账号密码，并提示信息！
			request.setAttribute("blogger", blogger);
			request.setAttribute("errorInfo", "用户名或者密码错误！");
			return "login";
		}

	}

	/**
	 * 关于博主
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/aboutMe")
	public ModelAndView aboutMe()throws Exception{
		ModelAndView mav=new ModelAndView();
		mav.addObject("pageTitle", "关于博主_java开源博客系统");
		mav.addObject("mainPage", "foreground/blogger/info.jsp");
		mav.setViewName("mainTemp");
		return mav;
	}
}
