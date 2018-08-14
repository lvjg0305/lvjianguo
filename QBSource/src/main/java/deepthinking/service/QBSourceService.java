package deepthinking.service;

import deepthinking.domain.TbYwScsjFile;


public interface QBSourceService extends BaseService<TbYwScsjFile,Long>{
	/**
	 * 对素材的总的维护方法，包含新增和修改，主要由于时间差可能造成资料重复录入，
	 * 或者手动抓取的时候设置的时间不可控造成
	 * @param file
	 */
	public int QBsourceAddOrUpdate(TbYwScsjFile file);
}

