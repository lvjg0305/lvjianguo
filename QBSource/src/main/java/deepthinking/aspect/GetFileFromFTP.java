package deepthinking.aspect;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.logging.log4j.util.PropertiesUtil;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.POIXMLTextExtractor;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.xmlbeans.XmlException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import deepthinking.common.FTPUtil;
import deepthinking.config.FTP_F_Config;
import deepthinking.model.DtsFtpFile;

public class GetFileFromFTP {
	private static FTPClient ftpClient;
	private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static Logger logger = LoggerFactory.getLogger(GetFileFromFTP.class);
	private static Properties prop = new Properties(); 
	private static Date startTime;
	public  static void findFile(Date time){
		ftpClient=FTPUtil.initFtpOpen();
		if(time!=null){//手动触发
			startTime=time;
		}else{//自动触发
			startTime=getpdTime();
		}
		getFile(FTP_F_Config.getDocFile().getDirRoot());
		updateProperties();
	}
	//获取文件的方法
	protected static void getFile(String path){
		// 获得指定目录下的文件夹和文件信息
		try {
			ftpClient.changeWorkingDirectory(path);
			FTPFile[] ftpFiles = ftpClient.listFiles();
			if(ftpFiles.length>0){
				for(FTPFile ftpFile:ftpFiles){
					if (ftpFile.getType() == 1 && !ftpFile.getName().equals(".")
					&& !ftpFile.getName().equals("..")) {//文件夹
						//递归查询
						getFile(path+ftpFile.getName()+ "/");
					} else if (ftpFile.getType() == 0) {
						// 获取ftp文件的最后修改时间
						Date timefile=ftpFile.getTimestamp().getTime();
						if(timefile.getTime()<(startTime.getTime()-10000)){
							break;
						}
						DtsFtpFile dtsFtpFile = new DtsFtpFile();
						// 获取ftp文件的名称
						dtsFtpFile.setFileName(ftpFile.getName());
						dtsFtpFile.setLastTime(timefile);
						dtsFtpFile.setUrl(path+ftpFile.getName());
						readInsFromFile(ftpFile.getName(), dtsFtpFile);
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	//抓取文件内容
	protected static void readInsFromFile(String filePath,DtsFtpFile dtsFtpFile) throws XmlException, OpenXML4JException{
		String buffer =""; 
		File localFile=new File(filePath);
		try {
			OutputStream is = new FileOutputStream(localFile); 
			ftpClient.retrieveFile(new String(filePath.getBytes("gbk"), "ISO-8859-1"), is);
			is.flush();
			is.close();
			//2003
			if (filePath.endsWith(".doc")) {
				InputStream isem = new FileInputStream(new File(localFile.getAbsolutePath()));
				WordExtractor ex = new WordExtractor(isem);
				buffer = ex.getText();
				isem.close();
			} else if (filePath.endsWith("docx")) {
				OPCPackage opcPackage = POIXMLDocument.openPackage(localFile.getAbsolutePath());
				POIXMLTextExtractor extractor = new XWPFWordExtractor(opcPackage);
				buffer = extractor.getText();
				opcPackage.close();
			}
			buffer.trim().replaceAll("(\\r\\n){2,}", "\r\n").replaceAll("(\\n){2,}", "\n");
			//存入数据
			System.out.println(buffer.substring(0, 200));
			System.out.println("--------------");
			System.out.println(buffer.substring(200, 500));
		} catch (Exception e) {
			logger.error(e.getMessage());
		}finally {
			if(localFile.exists()){
				localFile.delete();
			}
		}
	}
	protected static Date getpdTime(){
		String pdtime="1970-01-01 00:00:00";
		Date retTime=null;
		try {
			prop=PropertiesLoaderUtils.loadAllProperties("date.properties");
			pdtime=prop.getProperty("startTime")!=null?prop.getProperty("startTime"):pdtime;
			retTime= formatter.parse(pdtime);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return retTime;
	}
	private static void updateProperties() { 
		 //getResource方法使用了utf-8对路径信息进行了编码，当路径中存在中文和空格时，他会对这些字符进行转换，这样， 
		 //得到的往往不是我们想要的真实路径，在此，调用了URLDecoder的decode方法进行解码，以便得到原始的中文及空格路径。 
		 String filePath = PropertiesUtil.class.getClassLoader().getResource("date.properties").getFile(); 
		 Properties props = null; 
		 BufferedWriter bw = null; 
		 try { 
			 filePath = URLDecoder.decode(filePath,"utf-8"); 
			 props = PropertiesLoaderUtils.loadProperties(new ClassPathResource("date.properties")); 
			 // 写入属性文件 
			 bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath))); 
			 props.clear();// 清空旧的文件 
			 props.setProperty("startTime", formatter.format(new Date())); 
			 props.store(bw, ""); 
		 } catch (IOException e) { 
		 } finally { 
			 try { 
				 bw.close(); 
			 } catch (IOException e) { 
				 e.printStackTrace(); 
			 } 
		 } 
		 }
}





