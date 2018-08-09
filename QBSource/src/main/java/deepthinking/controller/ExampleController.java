/********************************************
 * 示例外部API接口
 *
 * @author zwq
 * @create 2018-06-26
 *********************************************/

package deepthinking.controller;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import deepthinking.common.ResultDtoUtil;
import deepthinking.common.exception.ExceptionHandle;
import deepthinking.dto.ResultDto;
import deepthinking.enums.exception.ExampleExceptionEnum;
import deepthinking.model.ExampleDataModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Validated
@CrossOrigin(origins = "*")
@Api(value="00 接口示例",description = "统一响应接口示例")
@RestController
@RequestMapping("/example")
public class ExampleController {
	
	@Autowired
	private ExceptionHandle<ExampleDataModel> exceptionHandle;
	
// 常用参数检测validator	
//	@AssertFalse 校验false
//	@AssertTrue 校验true
//	@DecimalMax(value=,inclusive=) 小于等于value，
//	inclusive=true,是小于等于
//	@DecimalMin(value=,inclusive=) 与上类似
//	@Max(value=) 小于等于value
//	@Min(value=) 大于等于value
//	@NotNull 检查Null
//	@Past 检查日期
//	@Pattern(regex=,flag=) 正则
//	@Size(min=, max=) 字符串，集合，map限制大小
//	@Valid 对po实体类进行校验
	
	
	/**
	 * “查询接口示例”接口
	 * @author zwq
	 * @create 2018-06-26
	 * @return 报文数据
	 **/
	@RequestMapping("")
	@ApiOperation(notes="查询示例",value="查询示例：展示成功、失败和异常三种情况下的报文数据",httpMethod="GET")
	@ApiImplicitParams({ @ApiImplicitParam(paramType = "query", dataType = "String", name = "uname", value = "用户名", required = true)
	,@ApiImplicitParam(paramType = "query", dataType = "String", name = "upwd", value = "密码", required = true)
	,@ApiImplicitParam(paramType = "query", dataType = "int", name = "type", value = "返回内容类别（0:处理成功,1:处理失败,2:系统异常）", required = true)})
	public ResultDto<ExampleDataModel> getResult(String uname,@Size(min=2, max=10)String upwd,@Min(value=0,message = "type必须大于等于0")int type){
		ResultDto<ExampleDataModel> result = ResultDtoUtil.success();
        try {
            if (0==type){
            	// 响应请求成功返回
            	ExampleDataModel exampleData = new ExampleDataModel();
            	exampleData.setUname(uname);
            	exampleData.setUpwd(upwd);
                result =  ResultDtoUtil.success(exampleData);
            }else if (1==type){
            	// 响应请求失败返回
                result =  ResultDtoUtil.error(ExampleExceptionEnum.USER_NOT_FIND);
            }else{
                @SuppressWarnings("unused")
				int i = 1/0;
            }
        }catch (Exception e){
        		// 可以自定义异常
        	   	//result =  exceptionHandle.exceptionGet(new DescribeException("自定义异常",-200));
        	// 系统异常
            result =  exceptionHandle.exceptionGet(e);
        }

        return result;
	}
}
