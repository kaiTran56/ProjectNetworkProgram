package com.tranquyet.data;

public class User {

	private String nameUser = "";
	private String hostUser = "";
	private int portUser = 0;

	public void setUser(String name, String host, int port) {
		nameUser = name;
		hostUser = host;
		portUser = port;
	}

	public void setName(String name) {
		nameUser = name;
	}

	public void setHost(String host) {
		hostUser = host;
	}

	public void setPort(int port) {
		portUser = port;
	}

	public String getName() {
		return nameUser;
	}

	public String getHost() {
		return hostUser;
	}

	public int getPort() {
		return portUser;
	}
}

