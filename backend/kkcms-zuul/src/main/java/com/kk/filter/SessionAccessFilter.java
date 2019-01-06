package com.kk.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.util.HTTPRequestUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by msi- on 2018/01/20
 */
public class SessionAccessFilter extends ZuulFilter {
    private final Logger log = LoggerFactory.getLogger(SessionAccessFilter.class);


    @Resource
    private TokenStore redisTokenStore;

    /**
     * 前置过滤器
     * pre：可以在请求被路由之前调用
     * route：在路由请求时候被调用
     * post：在route和error过滤器之后被调用
     * error：处理请求时发生错误时被调用
     *
     * @return
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * 优先级为0，数字越大，优先级越低
     * 通过int值来定义过滤器的执行顺序
     *
     * @return
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 是否执行该过滤器，此处为true，说明需要过滤
     * 返回一个boolean类型来判断该过滤器是否要执行，所以通过此函数可实现过滤器的开关。
     *
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 过滤器的具体逻辑。
     * 使用RequestContext共享状态进行过滤器间的协调，他是一个Map结构
     *
     * @return
     */
    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        //获取当前请求路径
        log.info("send {} request to {}", request.getMethod(), request.getRequestURL().toString());
        if (StringUtils.contains(request.getRequestURL().toString(), "kkcms-gate/oauthcode")) {
            passZullPorxy(ctx);
            return null;
        }
        if (StringUtils.contains(request.getRequestURL().toString(), "kkcms-gate/code")) {
            passZullPorxy(ctx);
            return null;
        }
        if (StringUtils.contains(request.getRequestURL().toString(), "kkcms-gate/authentication/form")) {
            passZullPorxy(ctx);
            return null;
        }
        if (StringUtils.contains(request.getRequestURL().toString(), "kkcms-movie-recommend/movie/checkIfFavorite")) {
            passZullPorxy(ctx);
            return null;
        }
        if (HTTPRequestUtils.getInstance().getHeaderValue("Authorization") == null) {
            log.warn("access token is empty!!!");
            // 禁止对该请求进行路由
            nopassZullProxy(ctx);
            return null;
        }
        String auth = request.getHeader("Authorization");
        log.info("Header中携带的认证头信息为:" + auth);
        if (auth.split(" ")[0].equals("Basic")) {
            passZullPorxy(ctx);
            return null;
        }
        String accessToken = auth.split(" ")[1];
        log.info("请求中携带的Token为:" + accessToken);
        if (redisTokenStore.readAccessToken(accessToken) != null) {
            log.info("access token is ok!!!");
            passZullPorxy(ctx);
        } else {
            log.info("access token is not correct!!!");
            nopassZullProxy(ctx);
        }
        return null;
    }


    public void passZullPorxy(RequestContext ctx) {
        ctx.setSendZuulResponse(true);// 对该请求进行路由
        ctx.setResponseStatusCode(200);
        ctx.set("isSuccess", true);// 设值，让下一个Filter看到上一个Filter的状态
    }

    public void nopassZullProxy(RequestContext ctx) {
        ctx.setSendZuulResponse(false);
        ctx.setResponseStatusCode(401);
        //ctx.setResponseBody("{\"result\":\"token is not correct!\"}");// 返回错误内容
        ctx.set("isSuccess", false);
    }

}
