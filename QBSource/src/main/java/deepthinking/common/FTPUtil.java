package deepthinking.common;

import java.io.IOException;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTPClient;

import deepthinking.config.FTPconetCof;

public class FTPUtil {
	private static FTPClient ftpClient;
	public static FTPClient initFtpOpen(){
		if(ftpClient!=null){
			return ftpClient;
		}else{
			//连接FTP
			ftpClient=new FTPClient();
			try {
				ftpClient.connect(FTPconetCof.getUrl());
				ftpClient.login(FTPconetCof.getUserName(),FTPconetCof.getPassWord());
				ftpClient.setControlEncoding("utf8");
				ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			} catch (SocketException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return ftpClient;
	}
}
