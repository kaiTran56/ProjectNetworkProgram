package com.tranquyet.dictionary;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Encode {

	private static Pattern checkMessage = Pattern.compile("[^<>]*[<>]");

	public static String getCreateAccount(String name, String port) {
		return Dictionary.SESSION_OPEN + Dictionary.PEER_NAME_OPEN + name + Dictionary.PEER_NAME_CLOSE
				+ Dictionary.PORT_OPEN + port + Dictionary.PORT_CLOSE + Dictionary.SESSION_CLOSE;
	}

	public static String sendRequest(String name) {
		return Dictionary.SESSION_KEEP_ALIVE_OPEN + Dictionary.PEER_NAME_OPEN + name + Dictionary.PEER_NAME_CLOSE
				+ Dictionary.STATUS_OPEN + Dictionary.SERVER_ONLINE + Dictionary.STATUS_CLOSE
				+ Dictionary.SESSION_KEEP_ALIVE_CLOSE;
	}

	public static String sendMessage(String message) {

		Matcher findMessage = checkMessage.matcher(message);
		String result = "";
		while (findMessage.find()) {
			String subMessage = findMessage.group(0);
			System.out.println("subMessage: " + subMessage);
			int begin = subMessage.length();
			char nextChar = message.charAt(subMessage.length() - 1);
			System.out.println("nextChar: " + nextChar);
			result += subMessage; // + nextChar
			subMessage = message.substring(begin, message.length());
			message = subMessage;
			findMessage = checkMessage.matcher(message);
		}
		result += message;

		return Dictionary.CHAT_MSG_OPEN + result + Dictionary.CHAT_MSG_CLOSE;
	}

	public static String sendRequestChat(String name) {
		return Dictionary.CHAT_REQ_OPEN + Dictionary.PEER_NAME_OPEN + name + Dictionary.PEER_NAME_CLOSE
				+ Dictionary.CHAT_REQ_CLOSE;
	}

	public static String sendFile(String name) {
		return Dictionary.FILE_REQ_OPEN + name + Dictionary.FILE_REQ_CLOSE;
	}

	public static String exit(String name) {
		return Dictionary.SESSION_KEEP_ALIVE_OPEN + Dictionary.PEER_NAME_OPEN + name + Dictionary.PEER_NAME_CLOSE
				+ Dictionary.STATUS_OPEN + Dictionary.SERVER_OFFLINE + Dictionary.STATUS_CLOSE
				+ Dictionary.SESSION_KEEP_ALIVE_CLOSE;
	}
}
