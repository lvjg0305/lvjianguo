///********************************************
// * 基础数据Service层接口的实现
// *
// * @author zwq
// * @create 2018-06-06
// *********************************************/
//
//package deepthinking.service.impl;
//
//import java.lang.reflect.InvocationTargetException;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.springframework.stereotype.Service;
//
//import com.github.pagehelper.PageInfo;
//
//import deepthinking.domain.TCszdZdqz;
//import deepthinking.model.DictionaryModel;
//import deepthinking.service.BaseDataService;
//
//@Service("baseDataService")
//public class BaseDataServiceImpl extends BaseServiceImpl<TCszdZdqz,Long> implements BaseDataService{
//
//
//	/**
//	 * 获取所有字典的数据模型
//	 */
//    public Map<String,Map<Long,DictionaryModel>> getAllDics() 
//    		throws IllegalAccessException, InvocationTargetException, NoSuchMethodException{
//    	 Map<String,Map<Long,DictionaryModel>> dicMap = null;
//		 
//    	 List<DictionaryModel> dicModels = this.selectAllModelList(".selectAllDics",null);
//		 
//		 if(null!=dicModels){
//			 dicMap = new HashMap<String,Map<Long,DictionaryModel>>();
//			 for(DictionaryModel dicModel :dicModels){ 
//				 String dicKey = dicModel.getDicTypeId()+"*"+dicModel.getDicTypeName();
//				 Long dicValueKey = dicModel.getDicValueId();
//				 
//				 if(dicMap.containsKey(dicKey)){
//					 if(dicMap.get(dicKey).containsKey(dicValueKey)){
//						 dicMap.get(dicKey).replace(dicValueKey, dicModel);
//					 }else{
//						 dicMap.get(dicKey).put(dicValueKey, dicModel);
//					 }
//				 }else{
//					 Map<Long,DictionaryModel> dicValueMap = new HashMap<Long,DictionaryModel>();
//					 dicValueMap.put(dicValueKey, dicModel);
//					 dicMap.put(dicKey, dicValueMap);
//				 }
//			 }
//		 }
//		 
//		 return dicMap;
//    }
//
//	@Override
//	public PageInfo<TCszdZdqz> pageFind(int pageNum, int pageSize, Object parameter)
//			throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/************
//	 * 根据字典分类名称，查询字典值列表
//	 * @throws NoSuchMethodException 
//	 * @throws InvocationTargetException 
//	 * @throws IllegalAccessException 
//	 */
//	@Override
//	public List<DictionaryModel> getAllDicsByTypeName(String dicTypeName)
//			throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
//		HashMap<String,String> reqParams = new HashMap<>();
//		reqParams.put("ZDFLMC", dicTypeName);
//		return this.selectAllModelList(".selectAllDicsByTypeName",reqParams);
//	}
//
//    
//}
