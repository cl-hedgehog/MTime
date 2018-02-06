package com.dreamzone.mtime.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author bohe
 * @ClassName: HelloServiceProxy
 * @Description:
 * @date 2018/2/3 下午3:10
 */
public class HelloServiceProxy implements InvocationHandler{

    private Object target;

    /**
     * 绑定对象并返回一个代理占位
     * @param target 真实对象
     * @param interfaces
     * @return  代理对象，占位
     */
    public Object bind(Object target, Class[] interfaces){
        this.target = target;
        return  Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("I am jdk dynamic proxy");
        Object result = null;
        System.out.println("I am going to say hello");
        result = method.invoke(this.target, args);
        System.out.println("I had said hello");
        return result;
    }
}
