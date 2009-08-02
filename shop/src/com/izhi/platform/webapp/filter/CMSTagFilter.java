package com.izhi.platform.webapp.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.mortbay.log.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.izhi.cms.tag.BlockTag;
import com.izhi.cms.tag.TagDirective;

import freemarker.template.TemplateDirectiveModel;

/**
 * Servlet Filter implementation class CMSTagFilter
 */
public class CMSTagFilter implements Filter {
	private static TagDirective tag=null;
	private static BlockTag block=null;
	private final Logger log=LoggerFactory.getLogger(CMSTagFilter.class);
	
    /**
     * Default constructor. 
     */
    public CMSTagFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req=(HttpServletRequest)request;
		//HttpServletResponse res=(HttpServletResponse)response;
		/*try {
			TemplateDirectiveModel a=(TemplateDirectiveModel)Class.forName("com.izhi.cms.tag.TagDirective").newInstance();
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		//req.setAttribute("tag", tag);
		//req.setAttribute("block", block);
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		log.info("加载模板自定义标签。。。。");
		
		tag=new TagDirective();
		block=new BlockTag();
	}

}
