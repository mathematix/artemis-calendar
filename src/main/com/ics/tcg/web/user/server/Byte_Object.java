package com.ics.tcg.web.user.server;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Byte_Object {

	/*
	 * 
	 */
	public static byte[] ObjectToByte(Object obj) throws IOException {
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ObjectOutputStream outputStream = new ObjectOutputStream(out);
			outputStream.writeObject(obj);
			byte[] bytes = out.toByteArray();
			outputStream.close();
			return bytes;
		} catch (Exception e) {
			System.out.println(" ObjectToBlob ");
			return null;
		}
	}

	/*
	 * 
	 */
	public static Object ByteToObject(byte[] bytes) throws IOException {
		try {
			ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
			ObjectInputStream ois = new ObjectInputStream(bis);
			return ois.readObject();

		} catch (Exception e) {
			System.out.println(" BlobToObject ");
			e.printStackTrace();
			return null;
		}
	}
}
