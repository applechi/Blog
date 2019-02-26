package com.apple.controller.admin;

import com.apple.model.Blog;
import com.apple.model.BlogType;
import com.apple.model.Blogger;
import com.apple.model.Link;
import com.apple.service.BlogService;
import com.apple.service.BlogTypeService;
import com.apple.service.BloggerService;
import com.apple.service.LinkService;
import com.apple.util.ResponseUtil;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 管理员系统Controller层
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/admin/system")
public class SystemAdminController {

	@Resource
	private BloggerService bloggerService;

	@Resource
	private LinkService linkService;

	@Resource
	private BlogTypeService blogTypeService;

	@Resource
	private BlogService blogService;

	/**
	 * 刷新系统缓存
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/refreshSystem")
	public String refreshSystem(HttpServletRequest request,HttpServletResponse response)throws Exception{
		ServletContext application=RequestContextUtils.getWebApplicationContext(request).getServletContext();

		Blogger blogger =bloggerService.find(); // 获取博主信息
		blogger.setPassword(null);
		application.setAttribute("blogger", blogger);

		List<Link> linkList =linkService.list(null); // 查询所有的友情链接信息
		application.setAttribute("linkList", linkList);

		List<BlogType> blogTypeCountList =blogTypeService.countList(); // 查询博客类别以及博客的数量
		application.setAttribute("blogTypeCountList", blogTypeCountList);

		List<Blog> blogCountList =blogService.countList(); // 根据日期分组查询博客
		application.setAttribute("blogCountList", blogCountList);

		JSONObject result=new JSONObject();
		result.put("success", true);
		ResponseUtil.write(response, result);
		return null;
	}


}
