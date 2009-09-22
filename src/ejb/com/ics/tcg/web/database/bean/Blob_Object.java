package com.ics.tcg.web.database.bean;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Blob;

import org.hibernate.Hibernate;

public class Blob_Object {

	/*
	 *
	 */
	public Blob ObjectToBlob(Object obj) throws IOException {
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ObjectOutputStream outputStream = new ObjectOutputStream(out);
			outputStream.writeObject(obj);
			byte[] bytes = out.toByteArray();
			outputStream.close();
			return Hibernate.createBlob(bytes);
		} catch (Exception e) {
			// TODO: handle exception

			System.out.println(" ObjectToBlob ");
			return null;
		}
	}

	/*
	 * 
	 */
	public Object BlobToObject(Blob desblob, Object obj) throws IOException {
		try {
			ObjectInputStream in = new ObjectInputStream(desblob
					.getBinaryStream());
			obj = in.readObject();
			in.close();
			return obj;
		} catch (Exception e) {
			// TODO: handle exception

			System.out.println(" BlobToObject ");
			e.printStackTrace();
			return null;
		}
	}
}
