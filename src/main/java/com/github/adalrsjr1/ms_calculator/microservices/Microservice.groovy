package com.github.adalrsjr1.ms_calculator.microservices

import org.eclipse.jetty.server.Server
import org.eclipse.jetty.servlet.ServletContextHandler
import org.eclipse.jetty.servlet.ServletHolder
import org.glassfish.jersey.server.ResourceConfig
import org.glassfish.jersey.servlet.ServletContainer

class Microservice {
	void init(String _package, int port) {
		ResourceConfig config = new ResourceConfig()
		config.packages(_package)
		ServletHolder servlet = new ServletHolder(new ServletContainer(config))
		
		Server server = new Server(port)
		ServletContextHandler context = new ServletContextHandler(server, "/*")
		context.addServlet(servlet,"/*")
		
		try {
			server.start()
			server.join()
		}
		finally {
			server.destroy()
		}
	}
	
	public static void main(String[] args) {
		Microservice microservice = new Microservice()
		
		String _package = System.getProperty("mspackage");
		int port = Integer.parseInt(System.getProperty("port"));

		microservice.init(_package, port)
		
	}
	
}
