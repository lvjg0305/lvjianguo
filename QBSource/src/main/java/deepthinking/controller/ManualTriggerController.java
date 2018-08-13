/********************************************
 * API接口
 *
 * @author lvjainguo
 * @create 2018-08-11
 *********************************************/

package deepthinking.controller;

import java.text.SimpleDateFormat;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import deepthinking.aspect.GetFileFromFTP;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Validated
@CrossOrigin(origins = "*")
@Api(value="手动触发素材抓取")
@RestController
public class ManualTriggerController {
	/**
	 * 
	 * @param time
	 * @return
	 */
	@RequestMapping("/manualTrigger.do")
	@ApiOperation(notes="手动触发",value="利用传入的时间一次性抓取",httpMethod="POST")
	@ApiImplicitParams({ @ApiImplicitParam(paramType = "query", dataType = "Date", name = "time", value = "开始时间", required = true)})
	public int ManualTrigger(String time){
		int i=1;
		if(time==null||"".equals(time)){
			return i;
		}
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			GetFileFromFTP.findFile(formatter.parse(time));
		} catch (Exception e) {
			i=0;
		}
        return i;
	}
}
