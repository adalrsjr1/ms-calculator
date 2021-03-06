package com.github.adalrsjr1.ms_calculator.operations.v1.sub

import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.Context
import javax.ws.rs.core.HttpHeaders
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Path("operation/")
public class Sub {

	private static final Logger log = LoggerFactory.getLogger(Sub.class);

	//"http://localhost:8888/operation/"
	private static final String DESTINATION = System.getProperty("destination")
	
	@GET
	@Path("sub/{a}/{b}")
	@Produces(MediaType.TEXT_PLAIN)
	Response sub(@Context HttpHeaders headers,
				 @PathParam("a") double a,
				 @PathParam("b") double b) {

		String uuid
		int loops
		if(headers.requestHeaders.containsKey("X-UUID")) {
			uuid = headers.requestHeaders["X-UUID"][0]
		}
		else {
			uuid = UUID.randomUUID()
		}
		
		log.info ">>> [sub] uuid: $uuid"
		
		if(headers.requestHeaders.containsKey("X-Loops")) {
			loops = Integer.parseInt(headers.requestHeaders["X-Loops"][0])
		}
		else {
			loops = 0
		}

		URL url = new URL(DESTINATION + "/sum/${a}/${-b}")
		HttpURLConnection connection = (HttpURLConnection) url.openConnection()
		connection.setDoOutput(true)
		connection.setInstanceFollowRedirects(true)
		connection.setRequestMethod("GET")
		connection.setRequestProperty("Content-Type", "text/plain")
		connection.setRequestProperty("X-UUID", uuid)
		connection.setRequestProperty("X-Loops", "$loops")
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(connection.inputStream, "UTF-8"))
		StringBuffer sb = new StringBuffer()
		for (String line; (line = reader.readLine()) != null;) {
			sb.append(line)
		}

		loops = Integer.parseInt(connection.getHeaderField("X-Loops")) + 1
		
		
		int responseCode = connection.getResponseCode()
		connection.disconnect()
		reader.close()
		
		Response.status(responseCode)
				.entity(sb.toString())
				.header("X-UUID", uuid)
				.header("X-Loops", loops)
				.build()
	}

}
