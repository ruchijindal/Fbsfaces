package com.smp.filter;

import com.smp.managedbean.BrokerPaymentBean;
import com.smp.managedbean.LoginBean;
import java.io.*;
import javax.ejb.EJB;
import javax.servlet.*;
import javax.servlet.http.*;

public class LoginFilter implements Filter {

    @EJB
    BrokerPaymentBean brokerPaymentBean;

    private FilterConfig config = null;
    FilterChain chain;

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletResponse res = (HttpServletResponse) response;
        HttpServletRequest req = (HttpServletRequest) request;
        //System.out.println("inside do filter method ------------");
        HttpSession session = req.getSession(false);

        if (session != null) {
             String userid = LoginBean.fbsLogin.getUserId();
//            if (LoginBean.fbsLogin.getCompanyId() != null) {
//                int companyId = LoginBean.fbsLogin.getCompanyId();
//                //System.out.println("COMPANY ID++++++++ " + companyId);
//            }
            res.setHeader("Cache-Control", "no-cache"); //Forces caches to obtain a new copy of the page from the origin server
            res.setHeader("Cache-Control", "no-store"); //Directs caches not to store the page under any circumstance
            res.setDateHeader("Expires", 0); //Causes the proxy cache to see the page as "stale"
            res.setHeader("Pragma", "no-cache");

           
             System.out.println("USERID++++++++ " + userid);
             System.out.println("redirect path *** "+req.getContextPath());
            if (userid == null) {

                res.sendRedirect(req.getContextPath()+"/faces/index.xhtml");
            } else {
                 //brokerPaymentBean.populate();
                chain.doFilter(request, response);
            }
            //else {
//                String userrole = (String)LoginBean.fbsLogin.getUserRole();
//                String url = new String(req.getRequestURL());
//                String page = url.substring(url.lastIndexOf("/") + 1);
//
//                url = url.substring(0, url.lastIndexOf("/"));
//
//                String searchstr = url.substring(url.lastIndexOf("/") + 1);
//
//
//               // System.out.println(searchstr);
//                if (userrole.equals("9")) {
//                    chain.doFilter(request, response);
//                } else if (userrole.equals("general")) {
//                    if (searchstr.equals("admin") || searchstr.equals("manager") || searchstr.equals("consumer") || searchstr.equals("reports") || searchstr.equals("charts") || page.equals("rate.jsp")) {
//                        res.sendRedirect(req.getContextPath() + "/404.jsp");
//                    } else {
//                        chain.doFilter(request, response);
//                    }
//                } else if (userrole.equals("consumer")) {
//                    if (searchstr.equals("admin") || searchstr.equals("manager") || searchstr.equals("reports") || searchstr.equals("charts")) {
//                        res.sendRedirect(req.getContextPath() + "/404.jsp");
//                    } else {
//                        chain.doFilter(request, response);
//                    }
//
//                } else if (userrole.equals("manager")) {
//
//                    if (searchstr.equals("admin") || searchstr.equals("consumer") || searchstr.equals("reports")) {
//                        res.sendRedirect(req.getContextPath() + "/404.jsp");
//                    } else {
//                        chain.doFilter(request, response);
//                    }
//                }
//                // chain.doFilter(request, response);
//            }


        } else {
            System.out.println("redirect path  "+req.getContextPath());
            res.sendRedirect(req.getContextPath()+"/faces/index.xhtml");
        }


    }

    public void destroy() {
        this.config = null;


    }

    public void init(FilterConfig config) {
        this.config = config;
    }
}
