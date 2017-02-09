package com.dereekb.gae.utilities.data.url;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLConnection;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

/**
 *
 *
 * @author dereekb
 *
 */
public class ConnectionUtility {

	private static final JsonParser parser = new JsonParser();

	public static JsonElement readJsonFromConnection(URLConnection connection) throws JsonSyntaxException, IOException {
		String json = readStringFromConnection(connection);
		return parser.parse(json);
	}

	public static String readStringFromConnection(URLConnection connection) throws IOException {
		InputStream inputStream = connection.getInputStream();
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
