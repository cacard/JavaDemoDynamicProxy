import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 一个简单实现
 * 
 */
public class A_FirstLook {
	public static void main(String[] args) {

		// 1，创建一个接口实现impl
		MyInterface impl = new MyInterfaceImpl();

		// 2，创建impl的代理。至于为啥要用代理，你懂的。

		// 3，创建代理之前，需要一个InvocationHandler，
		// 其实就是拦截方法名，然后根据方法名做任何你想的操作：
		// 比如加些日志了，篡改数据了，但通常还是要调用原始对象impl相应的方法的。
		MyHandler handler = new MyHandler();

		// 4，创建一个代理instance
		// 仅仅根据接口就能创建出一个该接口的实现，作为代理对象。
		// 虽然是接口的实现，但其实各个方法肯本不可能有什么具体实现，至于怎么具体实现，你可以在InvocationHandler里面控制。
		MyInterface proxy = (MyInterface) Proxy.newProxyInstance(
				MyInterface.class.getClassLoader(), // classLoader
				new Class[] { MyInterface.class }, //
				handler // 
		);

		// 5，执行代理instance进行调用
		// 在本例中，生成的proxy跟接口真正的实现impl没啥关系。
		if (proxy != null) {
			Object r = proxy.add(1, 2);
			System.out.println("result:"+r);
		}
	}

	/////////////////////////////////////////////////////////////////////////

	private static class MyHandler implements InvocationHandler {

		/**
		 * 参数含义：
		 * ....proxy，就是proxy-instance
		 * ....method，外部调用proxy-instance的方法
		 * ....args，外部调用方法相应的参数
		 * 
		 * 注意事项：
		 * ....如果再次调用proxy的方法，那么可能会引起递归。
		 */
		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			
			/**
			 * --------------------------------------------------
			 * 参数情况
			 * proxy
			 *   不为空，就是创建的proxy-instance。
			 *   是 instanceof MyInterface
			 *   Class是：class com.sun.proxy.$Proxy
			 * 
			 * method
			 *   在proxy-instance上调用什么，这里就会是那个method
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
