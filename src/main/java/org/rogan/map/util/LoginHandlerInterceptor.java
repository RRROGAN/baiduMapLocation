package org.rogan.map.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoginHandlerInterceptor extends HandlerInterceptorAdapter
{
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
    throws Exception
  {
    Object user = request.getSession().getAttribute("user");
    if (user == null)
    {
      response.sendRedirect(request.getContextPath() + "/��¼ҳ·��...");
      return false;
    }
    return true;
  }
}