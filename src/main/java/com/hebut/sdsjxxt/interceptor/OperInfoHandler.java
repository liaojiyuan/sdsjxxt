package com.hebut.sdsjxxt.interceptor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

/**
 * 用于拦截mybaits接口，在执行业务数据存入数据库前，设置操作信息，包括创建时间、最后修改时间 OperInfoHandler
 * 
 *
 */
public class OperInfoHandler implements MethodInterceptor {

	Logger log = Logger.getLogger(getClass());

	/**
	 * 反射调用
	 */
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		String methodName = invocation.getMethod().getName();
		Object[] obj = invocation.getArguments();
		// 以insert开头插入，但是过滤掉insertOrUpdate函数
		if (StringUtils.startsWith(methodName, "insert") && !StringUtils.endsWithIgnoreCase(methodName, "Update")) {
			setInsertOperInfo(obj[0]);
		} // 以update开头插入，但是insertOrUpdate函数也是update类型
		else if (StringUtils.startsWith(methodName, "update") || "insertOrUpdate".equals(methodName)) {
			setUpdateOperInfo(obj[0]);
		}
		return invocation.proceed();
	}

	/**
	 * 针对insert操作
	 * 
	 * @param obj
	 *            业务对象实例
	 */
	private void setInsertOperInfo(Object obj) {
		setValue(obj, "setCreateTime", Date.class, new Date());
		setValue(obj, "setUpdateTime", Date.class, new Date());
	}

	/**
	 * 针对update操作
	 * 
	 * @param obj
	 *            业务对象实例
	 */
	private void setUpdateOperInfo(Object obj) {
		setValue(obj, "setUpdateTime", Date.class, new Date());

	}

	/**
	 * 反射赋值
	 * 
	 * @param obj
	 * @param methodName
	 * @param class1
	 * @param value
	 */
	private void setValue(Object obj, String methodName, Class<? extends Object> class1, Object value) {
		Class<? extends Object> clazz = obj.getClass();
		Method method;
		try {
			method = clazz.getMethod(methodName, class1);
			if (null != method) {
				method.invoke(obj, value);
			}
		} catch (NoSuchMethodException e) {
			log.debug(clazz.getName() + "不存在需要赋值的方法");
		} catch (SecurityException e) {
			log.debug(clazz.getName() + "存在安全问题，赋值失败");
		} catch (IllegalAccessException e) {
			log.debug(clazz.getName() + "非法访问异常");
		} catch (IllegalArgumentException e) {
			log.debug(clazz.getName() + "无效参数");
		} catch (InvocationTargetException e) {
			log.debug(clazz.getName() + "调用目标异常");
		}
	}

}
