package in.flyspark.sonarqube.rulesexporter.util;

public class Utils {

	private Utils() {
	}

	public static boolean isBlank(String input) {
		return input == null || input.trim().isEmpty();
	}

	public static String makeValidURL(String url) {
		return url.replaceAll("(?<!(http:|https:))[//]+", "/");
	}
}
