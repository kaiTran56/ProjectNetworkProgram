package com.tranquyet.dictionary;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.tranquyet.data.User;

public class Decode {

	private static Pattern createAccount = Pattern
			.compile(Dictionary.SESSION_OPEN + Dictionary.PEER_NAME_OPEN + ".*" + Dictionary.PEER_NAME_CLOSE
					+ Dictionary.PORT_OPEN + ".*" + Dictionary.PORT_CLOSE + Dictionary.SESSION_CLOSE);

	private static Pattern users = Pattern.compile(Dictionary.SESSION_ACCEPT_OPEN + "(" + Dictionary.PEER_OPEN
			+ Dictionary.PEER_NAME_OPEN + ".+" + Dictionary.PEER_NAME_CLOSE + Dictionary.IP_OPEN + ".+"
			+ Dictionary.IP_CLOSE + Dictionary.PORT_OPEN + "[0-9]+" + Dictionary.PORT_CLOSE + Dictionary.PEER_CLOSE
			+ ")*" + Dictionary.SESSION_ACCEPT_CLOSE);

	private static Pattern request = Pattern.compile(Dictionary.SESSION_KEEP_ALIVE_OPEN + Dictionary.PEER_NAME_OPEN
			+ "[^<>]+" + Dictionary.PEER_NAME_CLOSE + Dictionary.STATUS_OPEN + "(" + Dictionary.SERVER_ONLINE + "|"
			+ Dictionary.SERVER_OFFLINE + ")" + Dictionary.STATUS_CLOSE + Dictionary.SESSION_KEEP_ALIVE_CLOSE);

	private static Pattern message = Pattern.compile(Dictionary.CHAT_MSG_OPEN + ".*" + Dictionary.CHAT_MSG_CLOSE);

	private static Pattern checkNameFile = Pattern.compile(Dictionary.FILE_REQ_OPEN + ".*" + Dictionary.FILE_REQ_CLOSE);

	private static Pattern feedBack = Pattern
			.compile(Dictionary.FILE_REQ_ACK_OPEN + ".*" + Dictionary.FILE_REQ_ACK_CLOSE);

	public static ArrayList<String> getUser(String msg) {
		ArrayList<String> user = new ArrayList<String>();
		if (createAccount.matcher(msg).matches()) {
			Pattern findName = Pattern.compile(Dictionary.PEER_NAME_OPEN + ".*" + Dictionary.PEER_NAME_CLOSE);
			Pattern findPort = Pattern.compile(Dictionary.PORT_OPEN + "[0-9]*" + Dictionary.PORT_CLOSE);
			Matcher find = findName.matcher(msg);
			if (find.find()) {
				String name = find.group(0);
				user.add(name.substring(11, name.length() - 12));
				find = findPort.matcher(msg);
				if (find.find()) {
					String port = find.group(0);
					user.add(port.substring(6, port.length() - 7));
				} else
					return null;
			} else
				return null;
		} else
			return null;
		return user;
	}

	public static ArrayList<User> getAllUser(String msg) {
		ArrayList<User> user = new ArrayList<User>();
		Pattern findPeer = Pattern.compile(Dictionary.PEER_OPEN + Dictionary.PEER_NAME_OPEN + "[^<>]*"
				+ Dictionary.PEER_NAME_CLOSE + Dictionary.IP_OPEN + "[^<>]*" + Dictionary.IP_CLOSE
				+ Dictionary.PORT_OPEN + "[0-9]*" + Dictionary.PORT_CLOSE + Dictionary.PEER_CLOSE);
		Pattern findName = Pattern.compile(Dictionary.PEER_NAME_OPEN + ".*" + Dictionary.PEER_NAME_CLOSE);
		Pattern findPort = Pattern.compile(Dictionary.PORT_OPEN + "[0-9]*" + Dictionary.PORT_CLOSE);
		Pattern findIP = Pattern.compile(Dictionary.IP_OPEN + ".+" + Dictionary.IP_CLOSE);
		if (users.matcher(msg).matches()) {
			Matcher find = findPeer.matcher(msg);
			while (find.find()) {
				String peer = find.group(0);
				String data = "";
				User dataPeer = new User();
				Matcher findInfo = findName.matcher(peer);
				if (findInfo.find()) {
					data = findInfo.group(0);
					dataPeer.setName(data.substring(11, data.length() - 12));
				}
				findInfo = findIP.matcher(peer);
				if (findInfo.find()) {
					data = findInfo.group(0);
					dataPeer.setHost(findInfo.group(0).substring(5, data.length() - 5));
				}
				findInfo = findPort.matcher(peer);
				if (findInfo.find()) {
					data = findInfo.group(0);
					dataPeer.setPort(Integer.parseInt(data.substring(6, data.length() - 7)));
				}
				user.add(dataPeer);
			}
		} else
			return null;
		return user;
	}

	public static ArrayList<User> updatePeerOnline(ArrayList<User> peerList, String msg) {
		Pattern alive = Pattern.compile(Dictionary.STATUS_OPEN + Dictionary.SERVER_ONLINE + Dictionary.STATUS_CLOSE);
		Pattern killUser = Pattern.compile(Dictionary.PEER_NAME_OPEN + "[^<>]*" + Dictionary.PEER_NAME_CLOSE);
		if (request.matcher(msg).matches()) {
			Matcher findState = alive.matcher(msg);
			if (findState.find())
				return peerList;
			findState = killUser.matcher(msg);
			if (findState.find()) {
				String findPeer = findState.group(0);
				int size = peerList.size();
				String name = findPeer.substring(11, findPeer.length() - 12);
				for (int i = 0; i < size; i++)
					if (name.equals(peerList.get(i).getName())) {
						peerList.remove(i);
						break;
					}
			}
		}
		return peerList;
	}

	public static String getMessage(String msg) {
		if (message.matcher(msg).matches()) {
			int begin = Dictionary.CHAT_MSG_OPEN.length();
			int end = msg.length() - Dictionary.CHAT_MSG_CLOSE.length();
			System.out.println(begin + " " + end);
			String message = msg.substring(begin, end);
			return message;
		}
		return null;
	}

	public static String getNameRequestChat(String msg) {
		Pattern checkRequest = Pattern.compile(Dictionary.CHAT_REQ_OPEN + Dictionary.PEER_NAME_OPEN + "[^<>]*"
				+ Dictionary.PEER_NAME_CLOSE + Dictionary.CHAT_REQ_CLOSE);
		if (checkRequest.matcher(msg).matches()) {
			int lenght = msg.length();
			String name = msg.substring((Dictionary.CHAT_REQ_OPEN + Dictionary.PEER_NAME_OPEN).length(),
					lenght - (Dictionary.PEER_NAME_CLOSE + Dictionary.CHAT_REQ_CLOSE).length());
			return name;
		}
		return null;
	}

	public static boolean checkFile(String name) {
		if (checkNameFile.matcher(name).matches())
			return true;
		return false;
	}

	public static boolean checkFeedBack(String msg) {
		if (feedBack.matcher(msg).matches())
			return true;
		return false;
	}
}
