import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import common.MyInterface;
import common.MyInterfaceImpl;

/**
 * ����B������Ϊimpl����һ������
 * 
 * �����޷����ڱ�����Ķ���ִ�з���ʱ���ӵ��ϡ�
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

		// ������Ķ���
		private MyInterface realObject;
		
		// �Ӹ����캯����ָ��һ�±������ԭʼ����
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
