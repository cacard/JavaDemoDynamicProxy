import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * һ����ʵ��
 * 
 */
public class A_FirstLook {
	public static void main(String[] args) {

		// 1������һ���ӿ�ʵ��impl
		MyInterface impl = new MyInterfaceImpl();

		// 2������impl�Ĵ�������ΪɶҪ�ô����㶮�ġ�

		// 3����������֮ǰ����Ҫһ��InvocationHandler��
		// ��ʵ�������ط�������Ȼ����ݷ��������κ�����Ĳ�����
		// �����Щ��־�ˣ��۸������ˣ���ͨ������Ҫ����ԭʼ����impl��Ӧ�ķ����ġ�
		MyHandler handler = new MyHandler();

		// 4������һ������instance
		// �������ݽӿھ��ܴ�����һ���ýӿڵ�ʵ�֣���Ϊ�������
		// ��Ȼ�ǽӿڵ�ʵ�֣�����ʵ���������ϱ���������ʲô����ʵ�֣�������ô����ʵ�֣��������InvocationHandler������ơ�
		MyInterface proxy = (MyInterface) Proxy.newProxyInstance(
				MyInterface.class.getClassLoader(), // classLoader
				new Class[] { MyInterface.class }, //
				handler // 
		);

		// 5��ִ�д���instance���е���
		// �ڱ����У����ɵ�proxy���ӿ�������ʵ��implûɶ��ϵ��
		if (proxy != null) {
			Object r = proxy.add(1, 2);
			System.out.println("result:"+r);
		}
	}

	/////////////////////////////////////////////////////////////////////////

	private static class MyHandler implements InvocationHandler {

		/**
		 * �������壺
		 * ....proxy������proxy-instance
		 * ....method���ⲿ����proxy-instance�ķ���
		 * ....args���ⲿ���÷�����Ӧ�Ĳ���
		 * 
		 * ע�����
		 * ....����ٴε���proxy�ķ�������ô���ܻ�����ݹ顣
		 */
		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			
			/**
			 * --------------------------------------------------
			 * �������
			 * proxy
			 *   ��Ϊ�գ����Ǵ�����proxy-instance��
			 *   �� instanceof MyInterface
			 *   Class�ǣ�class com.sun.proxy.$Proxy
			 * 
			 * method
			 *   ��proxy-instance�ϵ���ʲô������ͻ����Ǹ�method
			 */
			
			
			String paramsMsg = "";
			if (proxy == null) {
				paramsMsg += "proxy:null";
			} else {
				paramsMsg += "proxy:not null, getClass:" + proxy.getClass() + "," + (proxy instanceof MyInterface);

			}
			paramsMsg += "|method:"+method.getName()+"|";
			if (args == null || args.length == 0) {
				paramsMsg += "args:null empty";
			} else {
				paramsMsg += args.toString();
			}
			System.out.println(paramsMsg);

			return 111;
		}

	}
}
