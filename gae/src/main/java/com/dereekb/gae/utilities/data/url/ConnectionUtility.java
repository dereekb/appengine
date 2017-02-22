package com.dereekb.gae.utilities.data.url;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLConnection;

import com.dereekb.gae.utilities.json.JsonUtility;
import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;

/**
 *
 *
 * @author dereekb
 *
 */
public class ConnectionUtility {

	public static JsonElement readJsonFromConnection(URLConnection connection) throws JsonSyntaxException, IOException {
		InputStream inputStream = connection.getInputStream();
		return readJsonFromInputStream(inputStream);
	}

	public static String readStringFromConnection(URLConnection connection) throws IOException {
		InputStream inputStream = connection.getInputStream();
		return readStringFromInputStream(inputStream);
	}

	public static JsonElement readJsonFromInputStream(InputStream inputStream) throws JsonSyntaxException, IOException {
		String json = readStringFromInputStream(inputStream);
		return JsonUtility.parseJson(json);
	}

	public static String readStringFromInputStream(InputStream inputStream) throws IOException {
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

		BufferedReader bufferReader = new BufferedReader(inputStreamReader);
		StringBuffer json = new StringBuffer();
		String line;

		while ((line = bufferReader.readLine()) != null) {
			json.append(line);
		}

		bufferReader.close();

		return json.toString();
	}

}
