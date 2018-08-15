package deepthinking.service.impl;
/********************************************
 * 服务接口的实现
 *
 * @author lvjianguo
 * @create 2018-08-10
 *********************************************/

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;

import deepthinking.dao.mapper.TbYwScsjFileMapper;
import deepthinking.domain.TbYwScsjFile;
import deepthinking.domain.TbYwScsjFileCriteria;
import deepthinking.service.QBSourceService;
@Service("qBSourceService")
public class QBSourceServiceImpl extends BaseServiceImpl<TbYwScsjFile,Long> implements QBSourceService{
	@Autowired
	private TbYwScsjFileMapper mapper;
	@Override
	public PageInfo<TbYwScsjFile> pageFind(int pageNum, int pageSize, Object parameter)
			throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int QBsourceAddOrUpdate(TbYwScsjFile file) {
		int i=0;
		//判定是否已经有录入的数据(根据时间、名称、路径)
		TbYwScsjFileCriteria fileCriteria=new TbYwScsjFileCriteria();
		fileCriteria.createCriteria().andBmEqualTo(file.getBm());
		fileCriteria.createCriteria().andScljEqualTo(file.getSclj());
		fileCriteria.createCriteria().andRksjEqualTo(file.getRksj());
		List<TbYwScsjFile> list=new ArrayList<TbYwScsjFile>();
		list=mapper.selectByExample(fileCriteria);
		if(list.size()>0){//有数据，修改数据
			String id=list.get(0).getScid();
			file.setScid(id);
			i=this.updateByPrimaryKey(file);
		}else{//没有数据，添加
			i=this.insert(file);
		}
		return i;
	}

}
