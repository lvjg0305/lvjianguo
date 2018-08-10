package deepthinking.common;

import java.io.IOException;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import deepthinking.config.FTPconetCof;

public class FTPUtil {
	private static FTPClient ftpClient;
	private static Logger logger = LoggerFactory.getLogger(FTPUtil.class);
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
				ftpClient.enterLocalPassiveMode();
			} catch (SocketException e) {
				logger.error(e.getMessage());
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
		}
		return ftpClient;
	}
}
