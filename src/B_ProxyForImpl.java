import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import common.MyInterface;
import common.MyInterfaceImpl;

/**
 * 例子B：真正为impl生成一个代理
 * 
 * 代理，无非是在被代理的对象执行方法时，加点料。
 */
public class B_ProxyForImpl {
	public static void main(String[] args) {
		MyInterface impl = new MyInterfaceImpl();
		MyHandler handler = new MyHandler(impl);
		MyInterface proxy = (MyInterface) Proxy.newProxyInstance(
				MyInterface.class.getClassLoader(), // classLoader
				new Class[] { MyInterface.class }, //
				handler // 
		);
		if (proxy != null) {
			Object r = proxy.add(1, 2);
			System.out.println("result:"+r);
		}
	}
	
	////////////////////////////////////////////////////////////
	
	private static class MyHandler implements InvocationHandler {

		// 被代理的对象
		private MyInterface realObject;
		
		// 加个构造函数，指定一下被代理的原始对象
		public MyHandler (MyInterface ro) {
			realObject = ro;
		}

		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			System.out.println("hi, when you invoke some method, I can add some log.");
			if (realObject != null) {
				return method.invoke(realObject, args);
			}
			return null;
		}

	}
	
}
