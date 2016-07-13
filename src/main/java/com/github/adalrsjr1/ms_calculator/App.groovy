package com.github.adalrsjr1.ms_calculator;

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors;

import org.eclipse.jetty.server.Server
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer
import org.slf4j.Logger
import org.slf4j.LoggerFactory


/**
 * Hello world!
 *
 */
public class App 
{
	private static final Logger logger = LoggerFactory.getLogger(App.class);
	
	static ExecutorService tPool = Executors.newCachedThreadPool()
	
    public static void main( String[] args )
    {
		println  InetAddress.getLocalHost().getHostName()
		
		tPool.execute({
			ResourceConfig config = new ResourceConfig()
			config.packages("com.github.adalrsjr1.ms_calculator.operations.v1")
			ServletHolder servlet = new ServletHolder(new ServletContainer(config))
			
			Server server = new Server(8888)
			ServletContextHandler context = new ServletContextHandler(server, "/*")
			context.addServlet(servlet,"/*")
			
			try {
				server.start()
				server.join()
			}
			finally {
				server.destroy()
			}
		})
		
		tPool.execute({
			ResourceConfig config = new ResourceConfig()
			config.packages("com.github.adalrsjr1.ms_calculator.operations.v1")
			ServletHolder servlet = new ServletHolder(new ServletContainer(config))
			
			Server server = new Server(8889)
			ServletContextHandler context = new ServletContextHandler(server, "/*")
			context.addServlet(servlet,"/*")
			
			try {
				server.start()
				server.join()
			}
			finally {
				server.destroy()
			}
		})
    }
}
