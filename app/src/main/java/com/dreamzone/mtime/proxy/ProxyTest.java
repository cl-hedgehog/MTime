package com.dreamzone.mtime.proxy;

/**
 * @author bohe
 * @ClassName: ProxyTest
 * @Description:
 * @date 2018/2/3 下午3:22
 */
public class ProxyTest {


    public static void main(String[] args){
        HelloServiceProxy proxy = new HelloServiceProxy();
        HelloService service = new HelloServiceImpl();
        service = (HelloService) proxy.bind(service, new Class[] {HelloService.class});
        service.sayHello("hi boohee");
    }
}
