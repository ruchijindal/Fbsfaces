package com.smp.fbs;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;


public class LoginFilter implements Filter{

  private FilterConfig config = null;
  FilterChain chain;

  public void doFilter(ServletRequest request, ServletResponse response,FilterChain chain)throws IOException, ServletException{
            HttpServletResponse res= (HttpServletResponse)response;
            HttpServletRequest req=(HttpServletRequest)request;
             System.out.println("inside do filter method ------------");
              HttpSession  session=req.getSession(false);
               if(session!=null)
                {
                 String userid = (String)session.getAttribute("userid");
	            if (userid == null)
                    {
	                System.out.println("currentUser null");
	                res.sendRedirect(req.getContextPath());
                    }
                    else
                    {
                     String userrole = (String)session.getAttribute("userrole");
                     String url=new String(req.getRequestURL());
                     String s1=url.substring(url.lastIndexOf("/")+1);
                     url=url.substring(0,url.lastIndexOf("/"));
                     String searchstr=url.substring(url.lastIndexOf("/")+1);

                      System.out.println(searchstr);
                       if(userrole.equals("A")||userrole.equals("a"))
                       {
                         chain.doFilter(request, response);
                       }
                     
                     
                        else
                            chain.doFilter(request, response);
                      }
                        // chain.doFilter(request, response);
                    }
       
              else
                    {
                      res.sendRedirect(req.getContextPath());
                    }

    }
  
  public void destroy()
  {
      this.config = null;

  }
  public void init(FilterConfig config) {
    this.config = config;
  }
}