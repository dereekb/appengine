package com.dereekb.gae.test.server.storage.gcs;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/*
 * Copyright 2013 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import com.google.appengine.tools.cloudstorage.GcsFileOptions;
import com.google.appengine.tools.cloudstorage.GcsFilename;
import com.google.appengine.tools.cloudstorage.GcsInputChannel;
import com.google.appengine.tools.cloudstorage.GcsOutputChannel;
import com.google.appengine.tools.cloudstorage.GcsService;
import com.google.appengine.tools.cloudstorage.GcsServiceFactory;
import com.google.appengine.tools.cloudstorage.RetryParams;
import com.google.appengine.tools.development.testing.LocalBlobstoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.common.io.ByteStreams;

/**
 * A main method to show how to use the GCS client locally.
 *
 */
public class GoogleGcsTest {

	private static class ExampleClass {

		/**
		 * This is the indexService from which all requests are initiated. The retry
		 * and exponential backoff settings are configured here.
		 */
		private final GcsService gcsService = GcsServiceFactory.createGcsService(RetryParams.getDefaultInstance());

		/**
		 * Writes the provided object to the specified file using Java
		 * serialization. One could use this same technique to write many
		 * objects, or with another format such as Json or XML or just a
		 * DataOutputStream.
		 *
		 * Notice at the end closing the ObjectOutputStream is not done in a
		 * finally block. See below for why.
		 */
		private void writeObjectToFile(	GcsFilename fileName,
										Object content) throws IOException {
			GcsOutputChannel outputChannel = this.gcsService.createOrReplace(fileName, GcsFileOptions.getDefaultInstance());
			ObjectOutputStream oout = new ObjectOutputStream(Channels.newOutputStream(outputChannel));
			oout.writeObject(content);
			oout.close();
		}

		/**
		 * Writes the byte array to the specified file. Note that the close at
		 * the end is not in a finally.This is intentional. Because the file
		 * only exists for reading if close is called, if there is an exception
		 * thrown while writing the file won't ever exist. (This way there is no
		 * need to worry about cleaning up partly written files)
		 */
		private void writeToFile(	GcsFilename fileName,
									byte[] content) throws IOException {
			GcsOutputChannel outputChannel = this.gcsService.createOrReplace(fileName, GcsFileOptions.getDefaultInstance());
			outputChannel.write(ByteBuffer.wrap(content));
			outputChannel.close();
		}

		/**
		 * Reads an object from the specified file using Java serialization. One
		 * could use this same technique to read many objects, or with another
		 * format such as Json or XML or just a DataInputStream.
		 *
		 * The final parameter to openPrefetchingReadChannel is a buffer size.
		 * It will attempt to buffer the input by at least this many bytes.
		 * (This must be at least 1kb and less than 10mb) If buffering is
		 * undesirable openReadChannel could be called instead, which is totally
		 * unbuffered.
		 */
		private Object readObjectFromFile(GcsFilename fileName) throws IOException, ClassNotFoundException {
			GcsInputChannel readChannel = this.gcsService.openPrefetchingReadChannel(fileName, 0, 1024 * 1024 * 2);
			ObjectInputStream oin = new ObjectInputStream(Channels.newInputStream(readChannel));
			try {
				return oin.readObject();
			} finally {
				oin.close();
			}
		}

		/**
		 * Reads an object from the specified file using Java serialization. One
		 * could use this same technique to read many objects, or with another
		 * format such as Json or XML or just a DataInputStream.
		 *
		 * The final parameter to openPrefetchingReadChannel is a buffer size.
		 * It will attempt to buffer the input by at least this many bytes.
		 * (This must be at least 1kb and less than 10mb) If buffering is
		 * undesirable openReadChannel could be called instead, which is totally
		 * unbuffered.
		 */
		private byte[] readFromFileWithStream(GcsFilename fileName) throws IOException, ClassNotFoundException {

			GcsInputChannel readChannel = this.gcsService.openPrefetchingReadChannel(fileName, 0, 1024 * 1024 * 1);
			InputStream stream = Channels.newInputStream(readChannel);

			byte[] data = null;

			try {
				data = ByteStreams.toByteArray(stream);
			} finally {
				stream.close();
			}

			return data;
		}

		/**
		 * Reads the contents of an entire file and returns it as a byte array.
		 * This works by first requesting the length, and then fetching the
		 * whole file in a single call. (Because it calls openReadChannel
		 * instead of openPrefetchingReadChannel there is no buffering, and thus
		 * there is no need to wrap the read call in a loop)
		 *
		 * This is really only a good idea for small files. Large files should
		 * be streamed out using the prefetchingReadChannel and processed
		 * incrementally.
		 */
		private byte[] readFromFile(GcsFilename fileName) throws IOException {
			int fileSize = (int) this.gcsService.getMetadata(fileName).getLength();
			ByteBuffer result = ByteBuffer.allocate(fileSize);
			GcsInputChannel readChannel = this.gcsService.openReadChannel(fileName, 0);
			try {
				readChannel.read(result);
			} finally {
				readChannel.close();
			}

			return result.array();
		}
	}

	/**
	 * Use this to make the library run locally as opposed to in a deployed
	 * servlet.
	 */
	private static final LocalServiceTestHelper helper = new LocalServiceTestHelper(
			new LocalBlobstoreServiceTestConfig(), new LocalDatastoreServiceTestConfig());

	@BeforeEach
	public void setUp() {
		helper.setUp();
	}

	@AfterEach
	public void tearDown() {
		helper.tearDown();
	}

	/**
	 * Writes a map to GCS and then reads it back printing the result to
	 * standard out. Then does the same for a byte array. (You may wish to
	 * suppress stderr as there is a lot of noise)
	 */
	@Test
	public void testObjectAccess() throws IOException, ClassNotFoundException {
		ExampleClass example = new ExampleClass();

		for (int i = 0; i < 500; i += 1) {
			try {
				/** Write and read back a map */
				GcsFilename filename = new GcsFilename("MyBucket", "foo");
				Map<String, String> mapContent = new HashMap<String, String>();
				mapContent.put("foo", "bar");

				example.writeObjectToFile(filename, mapContent);

			} finally {

			}
		}
	}

	/**
	 * Writes a map to GCS and then reads it back printing the result to
	 * standard out. Then does the same for a byte array. (You may wish to
	 * suppress stderr as there is a lot of noise)
	 */
	@Test
	public void testByteAccess() throws IOException, ClassNotFoundException {
		ExampleClass example = new ExampleClass();

		for (int i = 0; i < 500; i += 1) {
			try {
				/** Write and read back a map */
				GcsFilename filename = new GcsFilename("MyBucket", "foo");

				/** Write and read back a byteArray */
				byte[] byteContent = new byte[] { 1, 2, 3, 4, 5 };

				example.writeToFile(filename, byteContent);

			} finally {

			}
		}
	}

	/**
	 * Writes a map to GCS and then reads it back printing the result to
	 * standard out. Then does the same for a byte array. (You may wish to
	 * suppress stderr as there is a lot of noise)
	 */
	@Test
	@Disabled
	public void testByteObjectTimes() throws IOException, ClassNotFoundException {
		ExampleClass example = new ExampleClass();

		for (int i = 0; i < 2; i += 1) {
			try {
				/** Write and read back a map */
				GcsFilename filename = new GcsFilename("MyBucket", "foo");
				Byte[] bytes = new Byte[1024 * 1024 * 20];

				example.writeObjectToFile(filename, bytes);

				Object reading = example.readObjectFromFile(filename);

				System.out.println(reading.toString());

			} catch (RuntimeException e) {
				e.printStackTrace();
			} finally {

			}
		}
	}

	/**
	 * Writes a map to GCS and then reads it back printing the result to
	 * standard out. Then does the same for a byte array. (You may wish to
	 * suppress stderr as there is a lot of noise)
	 */
	@Test
	@Disabled
	public void testByteTimes() throws IOException, ClassNotFoundException {
		ExampleClass example = new ExampleClass();

		for (int i = 0; i < 20; i += 1) {
			try {
				/** Write and read back a map */
				GcsFilename filename = new GcsFilename("MyBucket", "foo");
				byte[] bytes = new byte[1024 * 1024];

				example.writeToFile(filename, bytes);
				byte[] reading = example.readFromFile(filename);

				System.out.println(reading.toString());

			} finally {

			}
		}
	}

	/**
	 * Writes a map to GCS and then reads it back printing the result to
	 * standard out. Then does the same for a byte array. (You may wish to
	 * suppress stderr as there is a lot of noise)
	 */
	@Test
	@Disabled
	public void testByteStreamTimes() throws IOException, ClassNotFoundException {
		ExampleClass example = new ExampleClass();

		for (int i = 0; i < 20; i += 1) {
			try {
				/** Write and read back a map */
				GcsFilename filename = new GcsFilename("MyBucket", "foo");
				byte[] bytes = new byte[1024 * 1024];

				example.writeToFile(filename, bytes);
				byte[] reading = example.readFromFileWithStream(filename);

				System.out.println(reading.toString());

			} finally {

			}
		}
	}

	/**
	 * Writes a map to GCS and then reads it back printing the result to
	 * standard out. Then does the same for a byte array. (You may wish to
	 * suppress stderr as there is a lot of noise)
	 */
	@Disabled
	@Test
	public void testBigByteTimes() throws IOException, ClassNotFoundException {
		ExampleClass example = new ExampleClass();

		for (int i = 0; i < 20; i += 1) {
			try {
				/** Write and read back a map */
				GcsFilename filename = new GcsFilename("MyBucket", "foo");
				byte[] bytes = new byte[1024 * 1024 * 20];

				example.writeToFile(filename, bytes);
				byte[] reading = example.readFromFile(filename);

				System.out.println(reading.toString());

			} finally {

			}
		}
	}

	/**
	 * Writes a map to GCS and then reads it back printing the result to
	 * standard out. Then does the same for a byte array. (You may wish to
	 * suppress stderr as there is a lot of noise)
	 */
	@Disabled
	@Test
	public void testBigByteStreamTimes() throws IOException, ClassNotFoundException {
		ExampleClass example = new ExampleClass();

		for (int i = 0; i < 20; i += 1) {
			try {
				/** Write and read back a map */
				GcsFilename filename = new GcsFilename("MyBucket", "foo");
				byte[] bytes = new byte[1024 * 1024 * 20];

				example.writeToFile(filename, bytes);
				byte[] reading = example.readFromFileWithStream(filename);

				System.out.println(reading.toString());

			} finally {

			}
		}
	}

}