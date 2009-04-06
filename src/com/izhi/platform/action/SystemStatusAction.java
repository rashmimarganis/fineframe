package com.izhi.platform.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.management.MBeanServer;
import javax.management.MBeanServerNotification;
import javax.management.Notification;
import javax.management.NotificationListener;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.manager.Constants;
import org.apache.catalina.manager.StatusTransformer;
import org.apache.catalina.util.ServerInfo;
import org.apache.catalina.util.StringManager;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.tomcat.util.modeler.Registry;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

@Service
@SuppressWarnings("unchecked")
@Namespace("/system")
public class SystemStatusAction extends BaseAction implements NotificationListener,InitializingBean,ApplicationContextAware  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private int debug = 0;


    /**
     * MBean server.
     */
    protected MBeanServer mBeanServer = null;


    /**
     * Vector of protocol handlers object names.
     */
   
	protected Vector protocolHandlers = new Vector();


    /**
     * Vector of thread pools object names.
     */
    protected Vector threadPools = new Vector();


    /**
     * Vector of request processors object names.
     */
    protected Vector requestProcessors = new Vector();


    /**
     * Vector of global request processors object names.
     */
    protected Vector globalRequestProcessors = new Vector();
    
    private Map<String,Object> serverInfo=new HashMap<String,Object>();
    private ApplicationContext ctx;

    /**
     * The string manager for this package.
     */
    protected static StringManager sm =
        StringManager.getManager(Constants.Package);
    @Action(value="status")
    public String execute(){
    	/*// mode is flag for HTML or XML output
    	HttpServletRequest request=this.getRequest();
    	HttpServletResponse response=this.getResponse();
        int mode = 0;
        // if ?XML=true, set the mode to XML
        if (request.getParameter("XML") != null 
            && request.getParameter("XML").equals("true")) {
            mode = 1;
        }
        StatusTransformer.setContentType(response, mode);

        PrintWriter writer=null;
		try {
			writer = response.getWriter();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

        boolean completeStatus = false;
        if ((request.getPathInfo() != null) 
            && (request.getPathInfo().equals("/all"))) {
            completeStatus = true;
        }
        // use StatusTransformer to output status
        StatusTransformer.writeHeader(writer,mode);

        // Body Header Section
        Object[] args = new Object[2];
        args[0] = request.getContextPath();
        if (completeStatus) {
            args[1] = sm.getString("statusServlet.complete");
        } else {
            args[1] = sm.getString("statusServlet.title");
        }
        // use StatusTransformer to output status
        StatusTransformer.writeBody(writer,args,mode);

        // Manager Section
        args = new Object[9];
        args[0] = sm.getString("htmlManagerServlet.manager");
        args[1] = response.encodeURL(request.getContextPath() + "/html/list");
        args[2] = sm.getString("htmlManagerServlet.list");
        args[3] = response.encodeURL
            (request.getContextPath() + "/" +
             sm.getString("htmlManagerServlet.helpHtmlManagerFile"));
        args[4] = sm.getString("htmlManagerServlet.helpHtmlManager");
        args[5] = response.encodeURL
            (request.getContextPath() + "/" +
             sm.getString("htmlManagerServlet.helpManagerFile"));
        args[6] = sm.getString("htmlManagerServlet.helpManager");
        if (completeStatus) {
            args[7] = response.encodeURL
                (request.getContextPath() + "/status");
            args[8] = sm.getString("statusServlet.title");
        } else {
            args[7] = response.encodeURL
                (request.getContextPath() + "/status/all");
            args[8] = sm.getString("statusServlet.complete");
        }
        // use StatusTransformer to output status
        StatusTransformer.writeManager(writer,args,mode);

        // Server Header Section
        args = new Object[7];
        args[0] = sm.getString("htmlManagerServlet.serverTitle");
        args[1] = sm.getString("htmlManagerServlet.serverVersion");
        args[2] = sm.getString("htmlManagerServlet.serverJVMVersion");
        args[3] = sm.getString("htmlManagerServlet.serverJVMVendor");
        args[4] = sm.getString("htmlManagerServlet.serverOSName");
        args[5] = sm.getString("htmlManagerServlet.serverOSVersion");
        args[6] = sm.getString("htmlManagerServlet.serverOSArch");
        // use StatusTransformer to output status
        StatusTransformer.writePageHeading(writer,args,mode);
        
        // Server Row Section
        args = new Object[6];
        args[0] = ServerInfo.getServerInfo();
        args[1] = System.getProperty("java.runtime.version");
        args[2] = System.getProperty("java.vm.vendor");
        args[3] = System.getProperty("os.name");
        args[4] = System.getProperty("os.version");
        args[5] = System.getProperty("os.arch");
        
        
        // use StatusTransformer to output status
        StatusTransformer.writeServerInfo(writer, args, mode);

        try {

            // Display operating system statistics using APR if available
            StatusTransformer.writeOSState(writer,mode);

            // Display virtual machine statistics
            StatusTransformer.writeVMState(writer,mode);

            Enumeration enumeration = threadPools.elements();
            while (enumeration.hasMoreElements()) {
                ObjectName objectName = (ObjectName) enumeration.nextElement();
                String name = objectName.getKeyProperty("name");
                // use StatusTransformer to output status
                StatusTransformer.writeConnectorState
                    (writer, objectName,
                     name, mBeanServer, globalRequestProcessors,
                     requestProcessors, mode);
            }

            if ((request.getPathInfo() != null) 
                && (request.getPathInfo().equals("/all"))) {
                // Note: Retrieving the full status is much slower
                // use StatusTransformer to output status
                StatusTransformer.writeDetailedState
                    (writer, mBeanServer, mode);
            }

        } catch (Exception e) {
            
        }*/

        // use StatusTransformer to output status
//        StatusTransformer.writeFooter(writer, mode);
    	
    	Date startDate=new Date(ctx.getStartupDate());
    	serverInfo.put("startDate", startDate);
        serverInfo.put("serverVersion", ServerInfo.getServerInfo());
        serverInfo.put("serverNumber", ServerInfo.getServerNumber());
        serverInfo.put("serverBuild", ServerInfo.getServerBuilt());
        serverInfo.put("freeMemory",  StatusTransformer.formatSize(Runtime.getRuntime().freeMemory(),true));
        serverInfo.put("totalMemory", StatusTransformer.formatSize(Runtime.getRuntime().totalMemory(),true));
        serverInfo.put("maxMemory", StatusTransformer.formatSize(Runtime.getRuntime().maxMemory(),true));
        serverInfo.put("javaRuntimeVersion",System.getProperty("java.runtime.version"));
        serverInfo.put("javaVmVersion",System.getProperty("java.vm.version"));
        serverInfo.put("javaVmVendor",System.getProperty("java.vm.vendor"));
        serverInfo.put("osName",System.getProperty("os.name"));
        serverInfo.put("osVersion",System.getProperty("os.version"));
        serverInfo.put("osArch",System.getProperty("os.arch"));
    	return SUCCESS;
    }
    
    
	@Override
	public void handleNotification(Notification notification, Object handback) {
		if (notification instanceof MBeanServerNotification) {
            ObjectName objectName = 
                ((MBeanServerNotification) notification).getMBeanName();
            if (notification.getType().equals
                (MBeanServerNotification.REGISTRATION_NOTIFICATION)) {
                String type = objectName.getKeyProperty("type");
                if (type != null) {
                    if (type.equals("ProtocolHandler")) {
                        protocolHandlers.addElement(objectName);
                    } else if (type.equals("ThreadPool")) {
                        threadPools.addElement(objectName);
                    } else if (type.equals("GlobalRequestProcessor")) {
                        globalRequestProcessors.addElement(objectName);
                    } else if (type.equals("RequestProcessor")) {
                        requestProcessors.addElement(objectName);
                    }
                }
            } else if (notification.getType().equals
                       (MBeanServerNotification.UNREGISTRATION_NOTIFICATION)) {
                String type = objectName.getKeyProperty("type");
                if (type != null) {
                    if (type.equals("ProtocolHandler")) {
                        protocolHandlers.removeElement(objectName);
                    } else if (type.equals("ThreadPool")) {
                        threadPools.removeElement(objectName);
                    } else if (type.equals("GlobalRequestProcessor")) {
                        globalRequestProcessors.removeElement(objectName);
                    } else if (type.equals("RequestProcessor")) {
                        requestProcessors.removeElement(objectName);
                    }
                }
                String j2eeType = objectName.getKeyProperty("j2eeType");
                if (j2eeType != null) {
                    
                }
            }
        }
		
	}
	@Override
	public void afterPropertiesSet() throws Exception {
		mBeanServer = Registry.getRegistry(null, null).getMBeanServer();

        // Set our properties from the initialization parameters
        String value = null;

        try {

            // Query protocol handlers
            String onStr = "*:type=ProtocolHandler,*";
            ObjectName objectName = new ObjectName(onStr);
            Set set = mBeanServer.queryMBeans(objectName, null);
            Iterator iterator = set.iterator();
            while (iterator.hasNext()) {
                ObjectInstance oi = (ObjectInstance) iterator.next();
                protocolHandlers.addElement(oi.getObjectName());
            }

            // Query Thread Pools
            onStr = "*:type=ThreadPool,*";
            objectName = new ObjectName(onStr);
            set = mBeanServer.queryMBeans(objectName, null);
            iterator = set.iterator();
            while (iterator.hasNext()) {
                ObjectInstance oi = (ObjectInstance) iterator.next();
                threadPools.addElement(oi.getObjectName());
            }

            // Query Global Request Processors
            onStr = "*:type=GlobalRequestProcessor,*";
            objectName = new ObjectName(onStr);
            set = mBeanServer.queryMBeans(objectName, null);
            iterator = set.iterator();
            while (iterator.hasNext()) {
                ObjectInstance oi = (ObjectInstance) iterator.next();
                globalRequestProcessors.addElement(oi.getObjectName());
            }

            // Query Request Processors
            onStr = "*:type=RequestProcessor,*";
            objectName = new ObjectName(onStr);
            set = mBeanServer.queryMBeans(objectName, null);
            iterator = set.iterator();
            while (iterator.hasNext()) {
                ObjectInstance oi = (ObjectInstance) iterator.next();
                requestProcessors.addElement(oi.getObjectName());
            }

            // Register with MBean server
            onStr = "JMImplementation:type=MBeanServerDelegate";
            objectName = new ObjectName(onStr);
            mBeanServer.addNotificationListener(objectName, this, null, null);

        } catch (Exception e) {
            e.printStackTrace();
        }
		
	}
	public int getDebug() {
		return debug;
	}
	public void setDebug(int debug) {
		this.debug = debug;
	}


	public Map<String, Object> getServerInfo() {
		return serverInfo;
	}


	@Override
	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		this.ctx=arg0;
		
	}
	
}
