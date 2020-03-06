package com.example.demo.config.utils;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.example.demo.config.enums.BaseErrorEnum;
import com.example.demo.myPostBar.user.entity.TUser;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JWTUtils {
	  /**
     * 公共密钥
     */
    public static final String SECRET = "ykhzs";


    /**
     * 创建token
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String createToken(TUser user,long ttlMillis) throws UnsupportedEncodingException {
    	 SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        //签发时间
        Date date = new Date();
        long nowMillis = System.currentTimeMillis();
        Map<String, Object> claims = new HashMap<>();
        claims.put("uid", user.getId());
        claims.put("name", user.getName());
        claims.put("avatar", user.getAvatar());
        // 下面就是在为payload添加各种标准声明和私有声明了
        JwtBuilder builder = Jwts.builder()             // 这里其实就是new一个JwtBuilder，设置jwt的body
                            .setId(user.getId())                  // 设置jti(JWT ID)：是JWT的唯一标识，根据业务需要，这个可以设置为一个不重复的值，主要用来作为一次性token,从而回避重放攻击。
        					.setClaims(claims)
                            .setIssuedAt(date)    	       // iat: jwt的签发时间
                            .setSubject(user.getId())        // sub(Subject)：代表这个JWT的主体，即它的所有人，这个是一个json格式的字符串，可以存放什么userid，roldid之类的，作为什么用户的唯一标志。
                            .signWith(signatureAlgorithm, SECRET); // 设置签名使用的签名算法和签名使用的秘钥
        
        // 设置过期时间
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }
        return builder.compact();
    }


    /**
     * 解密
     * @param token
     * @return
     * @throws UnsupportedEncodingException
     */
    public static Claims verifyToken(String token) throws UnsupportedEncodingException {

    	// SecretKey key = generalKey();  //签名秘钥，和生成的签名的秘钥一模一样
         try {
        	 Claims claims = Jwts.parser()               //得到DefaultJwtParser
        			 .setSigningKey(SECRET)                 //设置签名的秘钥
        			 .parseClaimsJws(token).getBody();     //设置需要解析的jwt
        	 		return claims;
		} catch (Exception e) {
			 throw  new UnsupportedEncodingException(BaseErrorEnum.ERR_JWT_TOKEN_EXPRISE.getErrorMessage());
		}finally {
		}
    }
}
