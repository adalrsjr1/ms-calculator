package com.github.adalrsjr1.ms_calculator.util

import javax.ws.rs.core.HttpHeaders
import javax.ws.rs.core.MultivaluedMap;
import org.glassfish.jersey.server.ContainerRequest

class Util {
	static String addHeaderAtt(ContainerRequest headers, String key, String value) {
		String v
		if(headers.requestHeaders.containsKey(key)) {
			v = headers.requestHeaders[key][0]
		}
		else {
			v = value
		}
		
		return v
	} 
	
	static String getHeaderAtt(ContainerRequest headers, String key) {
		String v
		if(headers.requestHeaders.containsKey(key)) {
			v = headers.requestHeaders[key][0]
		}
		else {
			v = null
		}
		
		return v
	}
}
