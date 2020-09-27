package com.tranquyet.server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import com.tranquyet.data.User;
import com.tranquyet.dictionary.Decode;
import com.tranquyet.dictionary.Dictionary;

public class ServerCore {

	private ArrayList<User> dataPeer = null;
	private ServerSocket server;
	private Socket connection;
	private ObjectOutputStream obOutputClient;
	private ObjectInputStream obInputStream;
	public boolean isStop = false, isExit = false;

	// Intial server socket
	public ServerCore(int port) throws Exception {
		server = new ServerSocket(port);
		dataPeer = new ArrayList<User>();
		(new WaitForConnect()).start();
	}

	// show status of state
	private String sendSessionAccept() throws Exception {
		String msg = Dictionary.SESSION_ACCEPT_OPEN;
		int size = dataPeer.size();
		for (int i = 0; i < size; i++) {
			User peer = dataPeer.get(i);
			msg += Dictionary.PEER_OPEN;
			msg += Dictionary.PEER_NAME_OPEN;
			msg += peer.getName();
			msg += Dictionary.PEER_NAME_CLOSE;
			msg += Dictionary.IP_OPEN;
			msg += peer.getHost();
			msg += Dictionary.IP_CLOSE;
			msg += Dictionary.PORT_OPEN;
			msg += peer.getPort();
			msg += Dictionary.PORT_CLOSE;
			msg += Dictionary.PEER_CLOSE;
		}
		msg += Dictionary.SESSION_ACCEPT_CLOSE;
		return msg;
	}

	// close server
	public void stopserver() throws Exception {
		isStop = true;
		server.close();
		connection.close();
	}

	// client connect to server
	private boolean waitForConnection() throws Exception {
		connection = server.accept();
		obInputStream = new ObjectInputStream(connection.getInputStream());
		String msg = (String) obInputStream.readObject();
		ArrayList<String> getData = Decode.getUser(msg);
		ServerGui.updateMessage(msg);
		if (getData != null) {
			if (!isExsistName(getData.get(0))) {
				saveNewPeer(getData.get(0), connection.getInetAddress().toString(), Integer.parseInt(getData.get(1)));
				ServerGui.updateMessage(getData.get(0));
				ServerGui.updateNumberClient();
			} else
				return false;
		} else {
			int size = dataPeer.size();

			Decode.updatePeerOnline(dataPeer, msg);
			if (size != dataPeer.size()) {
				isExit = true;
				ServerGui.decreaseNumberClient();
			}
		}
		return true;
	}

	private void saveNewPeer(String user, String ip, int port) throws Exception {
		User newPeer = new User();
		if (dataPeer.size() == 0)
			dataPeer = new ArrayList<User>();
		newPeer.setUser(user, ip, port);
		dataPeer.add(newPeer);
	}

	private boolean isExsistName(String name) throws Exception {
		if (dataPeer == null)
			return false;
		int size = dataPeer.size();
		for (int i = 0; i < size; i++) {
			User peer = dataPeer.get(i);
			if (peer.getName().equals(name))
				return true;
		}
		return false;
	}

	public class WaitForConnect extends Thread {

		@Override
		public void run() {
			super.run();
			try {
				while (!isStop) {
					if (waitForConnection()) {
						if (isExit) {
							isExit = false;
						} else {
							obOutputClient = new ObjectOutputStream(connection.getOutputStream());
							obOutputClient.writeObject(sendSessionAccept());
							obOutputClient.flush();
							obOutputClient.close();
						}
					} else {
						obOutputClient = new ObjectOutputStream(connection.getOutputStream());
						obOutputClient.writeObject(Dictionary.SESSION_DENY);
						obOutputClient.flush();
						obOutputClient.close();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
