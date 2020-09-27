package com.tranquyet.data;

import java.io.Serializable;

import com.tranquyet.dictionary.Dictionary;


@SuppressWarnings("serial")
public class DataFile implements Serializable {

	@SuppressWarnings("unused")
	private String openTags = Dictionary.FILE_DATA_OPEN;
	@SuppressWarnings("unused")
	private String closeTags = Dictionary.FILE_DATA_CLOSE;
	public byte[] data;

	public DataFile() {
		data = new byte[Dictionary.MAX_MSG_SIZE];
	}

	public DataFile(int size) {
		data = new byte[size];
	}
}
