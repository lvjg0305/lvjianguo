/********************************************
 * 服务端启动主类
 *
 * @author zwq
 * @create 2018-06-02 22:23
 *********************************************/

package deepthinking;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;


@MapperScan("deepthinking.dao.mapper")
@SpringBootApplication
@ServletComponentScan
@EnableAutoConfiguration(exclude = {MultipartAutoConfiguration.class})
public class ServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class);
	}

}
