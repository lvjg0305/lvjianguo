/********************************************
 * 文件相关配置类
 *
 * @author zwq
 * @create 2018-06-12
 *********************************************/

package deepthinking.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="attachment")
public class FTP_F_Config {
    /**
     * txt
     */
    private static FTP_F_ConfigItem txtFile;
    /**
     * doc
     */
    private static FTP_F_ConfigItem docFile;
    
	public static FTP_F_ConfigItem getTxtFile() {
		return txtFile;
	}
	public static void setTxtFile(FTP_F_ConfigItem txtFile) {
		FTP_F_Config.txtFile = txtFile;
	}
	public static FTP_F_ConfigItem getDocFile() {
		return docFile;
	}
	public static void setDocFile(FTP_F_ConfigItem docFile) {
		FTP_F_Config.docFile = docFile;
	}
    

}