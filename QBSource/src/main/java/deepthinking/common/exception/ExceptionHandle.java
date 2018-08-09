/********************************************
 * 统一异常处理类
 *
 * @author zwq
 * @create 2018-06-26
 *********************************************/

package deepthinking.common.exception;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import deepthinking.common.ResultDtoUtil;
import deepthinking.dto.ResultDto;
import deepthinking.enums.exception.IllegalArgumentExceptionEnum;
import deepthinking.enums.exception.UnknowExceptionEnum;
import deepthinking.model.IllegalArgumentModel;


@ControllerAdvice
public class ExceptionHandle<T> {
	private static Logger logger = LoggerFactory.getLogger(ExceptionHandle.class);

    /**
     * 判断错误是否是已定义的已知错误，不是则由未知错误代替，同时记录在log中
     * @param e
     * @return
     */
	@SuppressWarnings("unchecked")
	@ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResultDto<T> exceptionGet(Exception e){
        if(e instanceof DescribeException){
            DescribeException myException = (DescribeException) e;
            return ResultDtoUtil.error(myException.getCode(),myException.getMessage());
        }
        if(e instanceof ConstraintViolationException){
        	List<IllegalArgumentModel> illegalArgumentModels = new ArrayList<>();
        	for(ConstraintViolation<?> error:((ConstraintViolationException)e).getConstraintViolations()){
        		IllegalArgumentModel illegalArgumentModel = new IllegalArgumentModel();
        		illegalArgumentModel.setParam(error.getPropertyPath().toString());
        		illegalArgumentModel.setErrMsg(error.getMessage());
        		
        		illegalArgumentModels.add(illegalArgumentModel);
        	}
        	
        	return (ResultDto<T>)ResultDtoUtil.error(IllegalArgumentExceptionEnum.IllegalArgument_ERROR,illegalArgumentModels);
        	
        	
        }

        logger.error("【系统异常】{}",e);
        return ResultDtoUtil.error(UnknowExceptionEnum.UNKNOW_ERROR);
    }
}
