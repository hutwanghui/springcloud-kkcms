package com.kk.api.controller;

import com.kk.api.service.GraphQLService;
import graphql.ExecutionResult;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

/**
 * Created by msi- on 2018/2/8.
 */
@RestController
@RequestMapping("/rest")
public class ApiController {

    @Autowired
    private GraphQLService graphQLService;

    @PostMapping(value = "/post/query")
    public ResponseEntity<Object> query(@RequestBody String query) {
        ExecutionResult result = graphQLService.getGraphQL().execute(query);
        return new ResponseEntity<Object>(result, HttpStatus.OK);
    }

    @PostMapping(value = "/post/mutation")
    public ResponseEntity<Object> mutation(@RequestBody String mutation) {
        ExecutionResult result = graphQLService.getGraphQL().execute(mutation);
        return new ResponseEntity<Object>(result, HttpStatus.OK);
    }

    @GetMapping(value = "/jwt")
    public Object getCurrentJwt(Authentication authentication, HttpServletRequest httpServletRequest) throws UnsupportedEncodingException {
        String header = httpServletRequest.getHeader("Authorization");
        String token = StringUtils.substringAfter(header, "beraer ");
        //解析jwt，并验证其中的密钥，这个密钥是在jwt配置中指定好的
        //签名和验证前面的时候分别是springframework.security.oauth2.provider和jwtio，而spring默认是用UTF-8签名的，所以验证的时候也要用UTF-8格式
        Claims claims = Jwts.parser().setSigningKey("hutwanghui".getBytes("UTF-8")).parseClaimsJws(token).getBody();
        System.out.print("\n获取令牌信息：" + claims.get("user_name"));
        return authentication;
    }



}
