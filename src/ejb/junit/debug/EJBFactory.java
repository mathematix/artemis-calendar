package junit.debug;

import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;

public class EJBFactory {

	public static Object getEJB(String jndipath) {
		try {
			Properties props = new Properties();

			props.setProperty("java.naming.factory.initial",
					"org.jnp.interfaces.NamingContextFactory");
			props.setProperty("java.naming.provider.url", "localhost:1099");
			props.setProperty("java.naming.factory.url.pkgs",
					"org.jboss.naming:org.jnp.interfaces");
			/*
			 * props.setProperty("java.naming.factory.initial",
			 * "com.sun.enterprise.naming.SerialInitContextFactory");
			 * props.setProperty("java.naming.factory.url.pkgs",
			 * "com.sun.enterprise.naming");
			 * props.setProperty("java.naming.provider.url", "localhost:3700");
			 * props.setProperty("java.naming.factory.state",
			 * "com.sun.corba.ee.impl.presentation.rmi.JNDIStateFactoryImpl");
			 */
			InitialContext ctx = new InitialContext(props);
			return ctx.lookup(jndipath);
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return null;
	}
}
