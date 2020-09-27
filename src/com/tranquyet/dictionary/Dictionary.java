package com.tranquyet.dictionary;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Dictionary {

	public static int IN_VALID = -1;

	public static int MAX_MSG_SIZE = 1024000; 

	public static String SESSION_OPEN = "hello_KAi";
	public static String SESSION_CLOSE = "hello_KAi_Quyet";
	public static String PEER_NAME_OPEN = "<PEER_NAME>";
	public static String PEER_NAME_CLOSE = "</PEER_NAME>";
	public static String PORT_OPEN = "<PORT>";
	public static String PORT_CLOSE = "</PORT>";
	public static String SESSION_KEEP_ALIVE_OPEN = "hello_wrold";
	public static String SESSION_KEEP_ALIVE_CLOSE = "hello_world!";
	public static String STATUS_OPEN = "<STATUS>";
	public static String STATUS_CLOSE = "</STATUS>";
	public static String SESSION_DENY = "<SESSION_DENY />";
	public static String SESSION_ACCEPT_OPEN = "<SESSION_ACCEPT>";
	public static String SESSION_ACCEPT_CLOSE = "</SESSION_ACCEPT>";
	public static String CHAT_REQ_OPEN = "<CHAT_REQ>";
	public static String CHAT_REQ_CLOSE = "</CHAT_REQ>";
	public static String IP_OPEN = "<IP>";
	public static String IP_CLOSE = "</IP>";
	public static String CHAT_DENY = "<CHAT_DENY />";
	public static String CHAT_ACCEPT = "<CHAT_ACCEPT />";
	public static String CHAT_MSG_OPEN = "<CHAT_MSG>";
	public static String CHAT_MSG_CLOSE = "</CHAT_MSG>";
	public static String PEER_OPEN = "<PEER>";
	public static String PEER_CLOSE = "</PEER>";
	public static String FILE_REQ_OPEN = "<FILE_REQ>";
	public static String FILE_REQ_CLOSE = "</FILE_REQ>";
	public static String FILE_REQ_NOACK = "<FILE_REQ_NOACK />";
	public static String FILE_REQ_ACK_OPEN = "<FILE_REQ_ACK>";
	public static String FILE_REQ_ACK_CLOSE = "</FILE_REQ_ACK>";
	public static String FILE_DATA_BEGIN = "<FILE_DATA_BEGIN />";
	public static String FILE_DATA_OPEN = "<FILE_DATA>";
	public static String FILE_DATA_CLOSE = "</FILE_DATA>";
	public static String FILE_DATA_END = "<FILE_DATA_END />";
	public static String CHAT_CLOSE = "<CHAT_CLOSE />";

	public static String SERVER_ONLINE = "Running........";
	public static String SERVER_OFFLINE = ".......Stop!";

	public static int show(JFrame frame, String msg, boolean type) {
		if (type)
			return JOptionPane.showConfirmDialog(frame, msg, null, JOptionPane.YES_NO_OPTION);
		JOptionPane.showMessageDialog(frame, msg);
		return IN_VALID;
	}
}
