/**
 * 
 */
package com.cream.security.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.Session;
import org.springframework.session.SessionRepository;
import org.springframework.session.data.redis.RedisOperationsSessionRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.cream.security.authentication.SSOAuthenticationToken;
import com.cream.security.entity.SysUser;

/**
 * @author cream
 *
 */
@RestController
@RequestMapping("/")
public class TestController {
    
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    
    @GetMapping("/")
    public String index() {
        return "index!!!";
    }

    @GetMapping("/index")
    public String index1() {
        return "index!!!1";
    }

    @GetMapping("/aaa")
    public String aaa() {
        return "aaa!!!1";
    }
    
    @GetMapping("/bbb")
    public String bbb() {
        return "bbb!!!1";
    }
    
    @GetMapping("/test1")
    public String test1() {
        SysUser sysUser1 = new SysUser();
        sysUser1.setId(1L);
        sysUser1.setUsername("cxr1");
        SysUser sysUser2 = new SysUser();
        sysUser2.setId(2L);
        sysUser2.setUsername("cxr2");
        SysUser sysUser3 = new SysUser();
        sysUser3.setId(3L);
        sysUser3.setUsername("cxr3");
        List<SysUser> users = new ArrayList<SysUser>();
        users.add(sysUser1);
        users.add(sysUser2);
        users.add(sysUser3);
        redisTemplate.opsForHash().put("test", "otesto", users);
//        stringRedisTemplate.opsForHash().put("tset", "otseto", users);
        return "test1";
    }
    
    @Resource
    private SessionRepository<Session> sessionRepository;
    
    @Resource
    private RedisOperationsSessionRepository redisOperationsSessionRepository;

    @GetMapping("/test2")
    public String test2(HttpSession mySession, HttpServletRequest request) {
        try {
//          String indexName = userService.getPrincipalNameIndexName(userId);
            // 查询用户的 Session 信息，返回值 key 为 sessionId
            Map<String, ? extends Session> userSessions = redisOperationsSessionRepository.findByIndexNameAndIndexValue(FindByIndexNameSessionRepository.PRINCIPAL_NAME_INDEX_NAME, "admin");
            // 移除用户的 session 信息
            List<String> sessionIds = new ArrayList<>(userSessions.keySet());
            System.out.println(sessionIds.size());
            for (String session : sessionIds) {
                Session s = sessionRepository.findById(session);
                SecurityContext securityContext = (SecurityContextImpl) s.getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
                
                SSOAuthenticationToken result = new SSOAuthenticationToken(securityContext.getAuthentication().getPrincipal(), securityContext.getAuthentication().getCredentials(),
                        "777", null);
                result.setDetails(securityContext.getAuthentication().getDetails());
                System.out.println(((SSOAuthenticationToken)securityContext.getAuthentication()).getCaptcha());
                securityContext.setAuthentication(result);
                
                s.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, securityContext);
                sessionRepository.save(s);
                System.out.println("---");
                System.out.println(((SSOAuthenticationToken)securityContext.getAuthentication()).getCaptcha());
                
            }
            System.out.println("---!!!---");
            SecurityContext securityContext = (SecurityContextImpl) mySession.getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
            SSOAuthenticationToken result = new SSOAuthenticationToken(securityContext.getAuthentication().getPrincipal(), securityContext.getAuthentication().getCredentials(),
                    "555", securityContext.getAuthentication().getAuthorities());
            result.setDetails(securityContext.getAuthentication().getDetails());
            System.out.println(((SSOAuthenticationToken)securityContext.getAuthentication()).getCaptcha());
            securityContext.setAuthentication(result);
            System.out.println("---");
            System.out.println(((SSOAuthenticationToken)securityContext.getAuthentication()).getCaptcha());
            return "test2";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
    
    @GetMapping("/test3")
    public String test3() {
        return "test3";
    }
    
    @GetMapping("/test4")
    public String test4() {
        return "test4";
    }
    
    @Autowired
    private RequestMappingHandlerMapping requestMappingHandlerMapping;
    
    /**
     * 获取当前项目中的全部接口
     * */
    @GetMapping(value = "getAllUrl")
    public String getAllUrl() {
        StringBuilder sb = new StringBuilder();
        sb.append("URL").append("--").append("Class").append("--").append("Function").append('\n');
 
        Map<RequestMappingInfo, HandlerMethod> map = requestMappingHandlerMapping.getHandlerMethods();
        int i=1;
        for (Map.Entry<RequestMappingInfo, HandlerMethod> m : map.entrySet()) {
            RequestMappingInfo info = m.getKey();
            HandlerMethod method = m.getValue();
            sb.append(i+":").append(info.getPatternsCondition()).append("--");
            sb.append(method.getMethod().getDeclaringClass()).append("--");
            sb.append(method.getMethod().getName()).append('\n');
            i++;
        }
 
        System.out.println(sb);
        
        return sb.toString();
 
    }
}
