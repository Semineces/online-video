package com.hty.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * create by Semineces on 2020-08-17
 */
public class JwtUtil {

    public static final long EXPIRE = 1000 * 60 * 60 * 24; //token过期时间
    public static final String APP_SECRET = "ukc8BDbRigUDaY6pZFfWus2jZWLPHO"; //秘钥，随便生成的，实际开发按照公司生成

    /**
     * 生成token字符串 其中两个参数可以换，但是要保证唯一性
     *
     * @param id       用户id
     * @param nickname 用户昵称
     * @return
     */
    public static String getJwtToken(String id, String nickname) {
        //构建jwt字符串
        String JwtToken = Jwts.builder()
                //设置jwt的头
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                //设置项目名字
                .setSubject("online-user")
                //设置过期时间
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
                //设置token主体，有多个就加上多行
                .claim("id", id)
                .claim("nickname", nickname)
                //签名hash，根据秘钥以及什么方式去生成jwt字符串
                .signWith(SignatureAlgorithm.HS256, APP_SECRET)
                .compact();

        return JwtToken;
    }

    /**
     * 判断token是否存在与有效
     *
     * @param jwtToken
     * @return
     */
    public static boolean checkToken(String jwtToken) {
        if (StringUtils.isEmpty(jwtToken)) return false;
        try {
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 判断token是否存在与有效
     *
     * @param request
     * @return
     */
    public static boolean checkToken(HttpServletRequest request) {
        try {
            //得到request中的token
            String jwtToken = request.getHeader("token");
            //判断是否为空
            if (StringUtils.isEmpty(jwtToken)) return false;
            //校验token
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 根据token获取用户id
     *
     * @param request
     * @return
     */
    public static String getMemberIdByJwtToken(HttpServletRequest request) {
        String jwtToken = request.getHeader("token");
        if (StringUtils.isEmpty(jwtToken)) return "";
        //解析jwt
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        //得到jwt字符串的主体
        Claims claims = claimsJws.getBody();
        //返回用户id
        return (String) claims.get("id");
    }
}
