/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.smp.fbs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Web application lifecycle listener.
 * @author Admin
 */

public class AppListener implements ServletContextListener
{
    Connection con;
    public void contextInitialized(ServletContextEvent sce)
    {
       ServletContext sc=sce.getServletContext();
       String dbuser=sc.getInitParameter("DBUser");
       String dbpassword=sc.getInitParameter("DBPassword");
       String dbdriver=sc.getInitParameter("DBDriver");
       String dburl=sc.getInitParameter("DBURL");
      try
       {
           Class.forName(dbdriver);
           con=DriverManager.getConnection(dburl,dbuser,dbpassword);
           sc.setAttribute("con", con);
           System.out.println(con);

          System.out.println("Connected:");
       }catch(Exception ex)
       {
           System.out.println("Exception:++++++++++++++++"+ex);
       }
    }

    public void contextDestroyed(ServletContextEvent arg)
    {
       try
       {
           if(!con.isClosed())
               con.close();

       }catch(SQLException ex)
       {
           System.out.println("Exception:-----------------------"+ex);
       }
     }
}