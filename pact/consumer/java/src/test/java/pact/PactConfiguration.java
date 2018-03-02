package pact;

import java.nio.file.Paths;

import static java.lang.String.format;

public class PactConfiguration {

	public static void setOutputLocationInSystemProperties() {
		String pactDirectory = Paths.get("../../pacts").toAbsolutePath().normalize().toString();
		String key = "pact.rootDir";
		System.out.println(format("%s: %s", key, pactDirectory));
		System.getProperties().setProperty(key, pactDirectory);
	}
}
