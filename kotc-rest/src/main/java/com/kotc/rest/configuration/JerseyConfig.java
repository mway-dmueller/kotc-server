package com.kotc.rest.configuration;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import com.kotc.rest.resource.BasicResource;

@Component
@ApplicationPath("/kotc/rest")
public class JerseyConfig extends ResourceConfig {

	public JerseyConfig() {
		packages(BasicResource.PACKAGE_NAME);
	}
}
