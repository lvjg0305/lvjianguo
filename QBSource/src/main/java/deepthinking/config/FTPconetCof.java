package deepthinking.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="ftp")
public class FTPconetCof {
	private static String url;
	private static int port;
	private static String userName;
	private static String passWord;
	public static String getUrl() {
		return url;
	}
	public static void setUrl(String url) {
		FTPconetCof.url = url;
	}
	public static int getPort() {
		return port;
	}
	public static void setPort(int port) {
		FTPconetCof.port = port;
	}
	public static String getUserName() {
		return userName;
	}
	public static void setUserName(String userName) {
		FTPconetCof.userName = userName;
	}
	public static String getPassWord() {
		return passWord;
	}
	public static void setPassWord(String passWord) {
		FTPconetCof.passWord = passWord;
	}
	
	
}
