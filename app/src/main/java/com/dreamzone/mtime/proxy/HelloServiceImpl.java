package com.dreamzone.mtime.proxy;

/**
 * @author bohe
 * @ClassName: HelloServiceImpl
 * @Description:
 * @date 2018/2/3 下午3:25
 */
public class HelloServiceImpl implements HelloService {
    @Override
    public void sayHello(String args) {
        System.out.println("I am HelloServiceImpl says " + args);
    }
}
