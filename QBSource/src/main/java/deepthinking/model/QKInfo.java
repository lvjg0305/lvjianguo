package deepthinking.model;

import java.util.Date;

//文件信息实体
public class QKInfo {
	 private String _FileName;//文件名称
     private String _BTClass;//标题分类
     private String _EJBT;// 二级标题
     private String _ConText;//正文内容
     private Date _Time;//期刊日期
     private String _Picture;//附图
	public String get_FileName() {
		return _FileName;
	}
	public void set_FileName(String _FileName) {
		this._FileName = _FileName;
	}
	public String get_BTClass() {
		return _BTClass;
	}
	public void set_BTClass(String _BTClass) {
		this._BTClass = _BTClass;
	}
	public String get_EJBT() {
		return _EJBT;
	}
	public void set_EJBT(String _EJBT) {
		this._EJBT = _EJBT;
	}
	public String get_ConText() {
		return _ConText;
	}
	public void set_ConText(String _ConText) {
		this._ConText = _ConText;
	}
	public Date get_Time() {
		return _Time;
	}
	public void set_Time(Date _Time) {
		this._Time = _Time;
	}
	public String get_Picture() {
		return _Picture;
	}
	public void set_Picture(String _Picture) {
		this._Picture = _Picture;
	}
     
     
}
