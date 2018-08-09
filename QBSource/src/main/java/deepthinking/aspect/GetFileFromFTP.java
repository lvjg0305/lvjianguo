package deepthinking.aspect;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import deepthinking.common.FTPUtil;
import deepthinking.config.FTP_F_Config;
import deepthinking.model.DtsFtpFile;

public class GetFileFromFTP {
	private static FTPClient ftpClient;
	private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//	private static List<DtsFtpFile> listFile = new ArrayList<DtsFtpFile>();
//	private static List<String> listurl=new ArrayList<String>();
	private static Logger logger = LoggerFactory.getLogger(GetFileFromFTP.class);
	public  static void findFile(){
		ftpClient=FTPUtil.initFtpOpen();
//		//变化路径，判定是否有文件
//		getFile(FTP_F_Config.getDocFile().getDirRoot());
//		//获取所有的已读文件
//		List<String> listFileold=new ArrayList<>();
//		//排除已读文件
//		listurl.removeAll(listFileold);
//		//读取所有的文件
//		for(String url:listurl){
//			
//		}
	}
	//获取文件的方法
	protected static void getFile(String path){
		// 获得指定目录下的文件夹和文件信息
		try {
			ftpClient.changeWorkingDirectory(path);
			FTPFile[] ftpFiles = ftpClient.listFiles();
			for(FTPFile ftpFile:ftpFiles){
				DtsFtpFile dtsFtpFile = new DtsFtpFile();
				// 获取ftp文件的名称
				dtsFtpFile.setFileName(ftpFile.getName());
				// 获取ftp文件的最后修改时间
				dtsFtpFile.setLastTime(formatter.parse(formatter.format(ftpFile.getTimestamp().getTime())));
				if (ftpFile.getType() == 1 && !ftpFile.getName().equals(".")
				&& !ftpFile.getName().equals("..")) {//文件夹
					// 获取ftp文件的当前路径
					dtsFtpFile.setUrl(path + "/" + ftpFile.getName());
					//递归查询
					getFile(path + "/" + ftpFile.getName()+ "/");
				} else if (ftpFile.getType() == 0) {
					dtsFtpFile.setUrl(path + "/"+ ftpFile.getName());
//					listFile.add(dtsFtpFile);
					readInsFromFile(path + "/"+ ftpFile.getName(), dtsFtpFile);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	//抓取文件内容
	public static void readInsFromFile(String filePath,DtsFtpFile dtsFtpFile) throws IOException{
		InputStream ins=ftpClient.retrieveFileStream(filePath);
		BufferedReader reader = new BufferedReader(new InputStreamReader(ins, "UTF-8"));
		String line; 
		StringBuffer buffer = new StringBuffer(); 
		while((line=reader.readLine())!=null){
			buffer.append(line.trim());
		}
		//存入数据
		
	}
}





