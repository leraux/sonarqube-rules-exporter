package in.flyspark.sonarqube.rulesexporter.rest;

import in.flyspark.sonarqube.rulesexporter.exception.ServiceUnavailableException;

public class RestServices {

	private RestServices() {
	}

	public static final String getComponents(final String url) throws ServiceUnavailableException {
		return RequestMaker.makeServiceCall(url);
	}
}
