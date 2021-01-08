package in.flyspark.sonarqube.rulesexporter.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import in.flyspark.sonarqube.rulesexporter.exception.ServiceUnavailableException;

public class RequestMaker {
	private static final Logger logger = LoggerFactory.getLogger(RequestMaker.class.getSimpleName());

	private RequestMaker() {
	}

	private static final String USER_AGENT = "Mozilla/5.0";

	public static String makeServiceCall(String url) throws ServiceUnavailableException {
		try {
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("User-Agent", USER_AGENT);
			int responseCode = con.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String inputLine;
				StringBuilder response = new StringBuilder();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
				return response.toString();
			} else {
				logger.debug("Unable to get response");
				throw new ServiceUnavailableException("Service Unavailable : " + url);
			}
		} catch (IOException e) {
			logger.error("makeServiceCall : ", e);
		}
		return "";
	}

}
