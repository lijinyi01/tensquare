package com.tensquare.manager.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;

@Component
public class ManagerFilter  extends ZuulFilter {

    @Autowired
    private JwtUtil jwtUtil;
    @Override
    public String filterType() {
        //操作之前过滤pre，post操作之后
        return "pre";
    }

    @Override
    public int filterOrder() {
        //0优先执行过滤器可以很多个
        return 0;
    }

    @Override
    public boolean shouldFilter() {

        //true过滤器开启，false过滤器关闭
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        //过滤器内执行的操作 return任何object放行执行
        // setsendzullRespponse(false)表示不再继续执行
        System.out.println("经过后台过滤器");
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        if(request.getMethod().equals("OPTIONS")){
            return null;
        }

        if (request.getRequestURI().indexOf("login")>0){
            return null;
        }


        String header = request.getHeader("Authorization");
          if(header!=null&&!"".equals(header)){
              if (header.startsWith("Bearer ")){
                  String token=header.substring(7);
                  try{
                      Claims claims = jwtUtil.parseJWT(token);
                      String roles = (String)claims.get("roles");
                      if(roles.equals("admin")){

                          requestContext.addZuulRequestHeader("Anthorization",header);
                          return null;
                      }

                  }catch (Exception e){
                      e.printStackTrace();
                      requestContext.setSendZuulResponse(false);
                  }

              }

          }
        requestContext.setSendZuulResponse(false);
          requestContext.setResponseStatusCode(403);
          requestContext.setResponseBody("权限不足");
          //json类型application、json 在tomcat config web.xml里面找
          requestContext.getResponse().setContentType("text/html;charset=utf-8");
        return null;
    }
}
