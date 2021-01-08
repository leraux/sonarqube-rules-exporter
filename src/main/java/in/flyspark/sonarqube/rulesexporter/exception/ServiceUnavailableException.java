package in.flyspark.sonarqube.rulesexporter.exception;

public class ServiceUnavailableException extends Exception {
	private static final long serialVersionUID = 390021433749907065L;

	public ServiceUnavailableException() {
	}

	public ServiceUnavailableException(String message) {
		super(message);
	}

}
