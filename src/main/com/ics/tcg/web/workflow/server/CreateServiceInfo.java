package com.ics.tcg.web.workflow.server;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.ics.tcg.web.workflow.client.service.InputInfo;
import com.ics.tcg.web.workflow.client.service.MethodInfo;
import com.ics.tcg.web.workflow.client.service.OutputInfo;
import com.ics.tcg.web.workflow.client.service.ParamInfo;
import com.ics.tcg.web.workflow.client.service.ServiceInfo;

/**
 * Package the service with the type {@link ServiceInfo}. Service name and
 * methods are packaged. Input and output also packaged in the method.
 * 
 * @author Administrator
 * 
 */
@SuppressWarnings("unchecked")
public class CreateServiceInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -250832281492227032L;

	/** Service info to be packaged. */
	private ServiceInfo serviceInfo;
	/** The service name. */
	private String serviceName;
	/** The class path of the virtual service. */
	private String serviceClassPath;

	public CreateServiceInfo(String serviceName) {
		this.serviceName = serviceName;
		serviceClassPath = new String();
		serviceInfo = new ServiceInfo();
	}

	/**
	 * Get the virtual service's class path.
	 * 
	 * @return
	 */
	public String getClassPath() {
		ServiceConfigureXmlParse xmlParse = new ServiceConfigureXmlParse();
		try {
			xmlParse.parse();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return xmlParse.findServiceClassPath(serviceName);
	}

	/**
	 * Package the service.
	 * 
	 * @throws ClassNotFoundException
	 * @throws ClassNotFoundException
	 */
	public void parseClass() throws ClassNotFoundException {
		serviceClassPath = getClassPath();
		Class cl = Class.forName(serviceClassPath);
		serviceInfo.setServiceName(cl.getSimpleName());

		createMethodInfo(cl);
	}

	/**
	 * Package the method info.
	 * 
	 * @param cl
	 */
	public void createMethodInfo(Class cl) {
		Method[] methods = cl.getDeclaredMethods();
		MethodInfo methodInfo;

		for (Method method : methods) {
			methodInfo = new MethodInfo();
			methodInfo.setMethodName(method.getName());

			createInputInfo(method, methodInfo);
			createOutputInfo(method, methodInfo);

			serviceInfo.getMethodInfoArray().add(methodInfo);
		}
	}

	/**
	 * Package the method's input info.
	 * 
	 * @param method
	 * @param methodInfo
	 */
	public void createInputInfo(Method method, MethodInfo methodInfo) {
		InputInfo inputInfo = new InputInfo();
		ParamInfo paramInfo;
		Class[] cls = method.getParameterTypes();

		for (Class c : cls) {
			paramInfo = new ParamInfo();

			// parameter is primitive
			if (c.isPrimitive() || c.getSimpleName().equals("String")
					|| c.getSimpleName().equals("Integer")
					|| c.getSimpleName().equals("Float")
					|| c.getSimpleName().equals("Double")
					|| c.getSimpleName().equals("Boolean")
					|| c.getSimpleName().equals("Date")) {
				paramInfo.setPrimitive(true);
				paramInfo.setArray(false);
				paramInfo.setParamTypeName(c.getSimpleName());
			}

			// usually not primitive
			else {
				// is array type, not happen yet
				if (c.isArray()) {
					// System.out.println("1 " + c.getSimpleName());
					Class type = c.getComponentType();
					paramInfo.setArray(true);
					paramInfo.setParamTypeName(type.getSimpleName());

					// primitive array
					if (type.isPrimitive()
							|| type.getSimpleName().equals("String")
							|| type.getSimpleName().equals("Integer")
							|| type.getSimpleName().equals("Float")
							|| type.getSimpleName().equals("Double")
							|| type.getSimpleName().equals("Boolean")
							|| type.getSimpleName().equals("Date")) {
						paramInfo.setPrimitive(true);
					}

					// array of complex type
					else {
						paramInfo.setPrimitive(false);
						paramInfo.setParamFullTypeName(type.getName());

						createParamInfo(type, paramInfo);
					}
				}

				// complex type, request or request list
				else {
					// System.out.println(c.getSimpleName());
					paramInfo.setPrimitive(false);
					paramInfo.setArray(false);
					paramInfo.setParamTypeName(c.getSimpleName());
					paramInfo.setParamFullTypeName(c.getName());

					createParamInfo(c, paramInfo);
				}

			}

			inputInfo.getParamInfosArray().add(paramInfo);
			methodInfo.setInputInfo(inputInfo);
		}
	}

	/**
	 * Package the method's output info.
	 * 
	 * @param method
	 * @param methodInfo
	 */
	public void createOutputInfo(Method method, MethodInfo methodInfo) {
		OutputInfo outputInfo = new OutputInfo();
		ParamInfo paramInfo = new ParamInfo();
		Class type = method.getReturnType();

		if (type.isPrimitive() || type.getSimpleName().equals("String")
				|| type.getSimpleName().equals("Integer")
				|| type.getSimpleName().equals("Float")
				|| type.getSimpleName().equals("Double")
				|| type.getSimpleName().equals("Boolean")
				|| type.getSimpleName().equals("Date")) {
			paramInfo.setArray(false);
			paramInfo.setPrimitive(true);
			paramInfo.setParamTypeName(type.getSimpleName());
		}

		else {
			if (type.isArray()) {
				Class arrayType = type.getComponentType();
				paramInfo.setArray(true);
				paramInfo.setParamTypeName(arrayType.getSimpleName());

				if (arrayType.isPrimitive()
						|| arrayType.getSimpleName().equals("String")
						|| arrayType.getSimpleName().equals("Integer")
						|| arrayType.getSimpleName().equals("Float")
						|| arrayType.getSimpleName().equals("Double")
						|| arrayType.getSimpleName().equals("Boolean")
						|| arrayType.getSimpleName().equals("Date")) {
					paramInfo.setPrimitive(true);
				}

				else {
					paramInfo.setPrimitive(false);
					paramInfo.setParamFullTypeName(type.getName());

					createParamInfo(arrayType, paramInfo);
				}
			}

			else {
				paramInfo.setPrimitive(false);
				paramInfo.setArray(false);
				paramInfo.setParamTypeName(type.getSimpleName());
				paramInfo.setParamFullTypeName(type.getName());

				createParamInfo(type, paramInfo);
			}
		}

		outputInfo.setParamInfo(paramInfo);
		methodInfo.setOutputInfo(outputInfo);
	}

	/**
	 * Create the nested paraminfos that is used. Actually this is usually
	 * called because of the parameter and the return type defined in the
	 * javadescription package.
	 * 
	 * @param c
	 * @param paramInfo
	 */
	public void createParamInfo(Class c, ParamInfo paramInfo) {
		ParamInfo tempParamInfo;
		Field[] fields = c.getDeclaredFields();

		for (Field f : fields) {
			if (f.getName().equals("serialVersionUID")) {
				continue;
			}

			tempParamInfo = new ParamInfo();
			Class type = f.getType();

			tempParamInfo.setParamName(f.getName());

			// is primitive, like price request
			if (type.isPrimitive() || type.getSimpleName().equals("String")
					|| type.getSimpleName().equals("Integer")
					|| type.getSimpleName().equals("Float")
					|| type.getSimpleName().equals("Double")
					|| type.getSimpleName().equals("Boolean")
					|| type.getSimpleName().equals("Date")) {
				tempParamInfo.setPrimitive(true);
				tempParamInfo.setParamTypeName(type.getSimpleName());
				// set enum
				if (type.getSimpleName().equals("String")) {
					if (f.getName().equals("weatherState")) {
						tempParamInfo.setParamEnums("Sunny,Cloudy,Rainy");
					} else if (f.getName().equals("windDirection")) {
						tempParamInfo.setParamEnums("North,South,West,East");
					} else if (f.getName().equals("windPower")) {
						tempParamInfo.setParamEnums("Strong,Weak");
					} else if (f.getName().equals("ticketKind")
							&& c.getSimpleName().equals(
									"BookTrainTicketResponseInfo")) {
						tempParamInfo.setParamEnums("Seat,Sleeper,Standing");
					} else if (f.getName().equals("ticketKind")
							&& c.getSimpleName().equals(
									"BookAirTicketResponseInfo")) {
						tempParamInfo.setParamEnums("First Class,Second Class");
					}
					// else if(f.getName().equals("ticketKind")
					// && c.getSimpleName().equals("BookBusTicketResponseInfo"))
					// {
					// tempParamInfo.setParamEnums("First Class,Second Class");
					// }
				}
			}

			// not primitive
			else {
				Object o = null;
				try {
					o = type.newInstance();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
				// info[] may be returned and requested
				if (o instanceof ArrayList) {
					// System.out.println(type.getSimpleName());
					String name = f.getGenericType().toString();
					String innerFUllType = name.substring(
							name.indexOf('<') + 1, name.indexOf('>'));
					Class arrayType = null;
					try {
						arrayType = Class.forName(innerFUllType);
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
					// System.out.println(innerFUllType);
					// String innerType =
					// innerFUllType.substring(innerFUllType.lastIndexOf('.') +
					// 1);

					tempParamInfo.setArray(true);
					// paramInfo.setArray(true);
					tempParamInfo.setParamTypeName(arrayType.getSimpleName());

					// array of primitive, not happen
					if (arrayType.isPrimitive()
							|| arrayType.getSimpleName().equals("String")
							|| arrayType.getSimpleName().equals("Integer")
							|| arrayType.getSimpleName().equals("Float")
							|| arrayType.getSimpleName().equals("Double")
							|| arrayType.getSimpleName().equals("Boolean")
							|| arrayType.getSimpleName().equals("Date")) {
						tempParamInfo.setPrimitive(true);
					}

					// info[]
					else {
						tempParamInfo.setPrimitive(false);
						tempParamInfo.setParamFullTypeName(arrayType.getName());

						createParamInfo(arrayType, tempParamInfo);
					}
				}

				else {
					// System.out.println(type.getSimpleName());
					tempParamInfo.setPrimitive(false);
					tempParamInfo.setArray(false);
					tempParamInfo.setParamTypeName(type.getSimpleName());
					tempParamInfo.setParamFullTypeName(type.getName());

					createParamInfo(type, tempParamInfo);
				}
			}

			paramInfo.getFieldInfoArray().add(tempParamInfo);
		}
	}

	public ServiceInfo getServiceInfo() {
		return serviceInfo;
	}

	public void setServiceInfo(ServiceInfo serviceInfo) {
		this.serviceInfo = serviceInfo;
	}
}
