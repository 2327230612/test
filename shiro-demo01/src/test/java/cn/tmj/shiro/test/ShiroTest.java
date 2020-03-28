package cn.tmj.shiro.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

public class ShiroTest {

	@Test
	public void testName() throws Exception {

		// 1.读取shiro.ini 配置文件，创建出Shiro核心对象安全管理器SecurityManager
		IniSecurityManagerFactory iniSecurityManagerFactory = new IniSecurityManagerFactory("classpath:shiro.ini");
		SecurityManager securityManager = iniSecurityManagerFactory.createInstance();

		// 2.将安全设置到当前线程环境中
		SecurityUtils.setSecurityManager(securityManager);

		// 3.获取主体对象Subject
		Subject subject = SecurityUtils.getSubject();

		// 4.创建Token令牌，封装身份凭证(账号密码)
		AuthenticationToken token = new UsernamePasswordToken("tmj", "123");

		// 判断是否认证
//		boolean authenticated = subject.isAuthenticated();
//		System.out.println("authenticated :" + authenticated);

		// 没有认证时候开始认证
		if (!subject.isAuthenticated()) {

			try {
				subject.login(token);
			} catch (UnknownAccountException e) {
				System.out.println("亲，账号不存在!");
			} catch (IncorrectCredentialsException e) {
				System.out.println("亲，密码错误!");
			}

		}

		boolean authenticated = subject.isAuthenticated();
		System.out.println("authenticated :" + authenticated);
		
		
		//获取认证的身份
		Object principal = subject.getPrincipal();
		System.out.println(principal);
		// 退出认证
		subject.logout();

		authenticated = subject.isAuthenticated();
		System.out.println("authenticated :" + authenticated);
		
		//获取认证的身份
		principal = subject.getPrincipal();
		System.out.println(principal);

	}
}
