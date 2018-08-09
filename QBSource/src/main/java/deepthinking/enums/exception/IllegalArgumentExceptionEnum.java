/********************************************
 * 参数异常枚举值
 *
 * @author zwq
 * @create 2018-07-06
 *********************************************/

package deepthinking.enums.exception;

import deepthinking.common.exception.IExceptionEnum;

public enum IllegalArgumentExceptionEnum implements IExceptionEnum{
	IllegalArgument_ERROR(-2,"参数校验错误");

    private Integer code;
    private String msg;

    IllegalArgumentExceptionEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
