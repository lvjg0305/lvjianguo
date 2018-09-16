package deepthinking.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import deepthinking.model.QKInfo;
import deepthinking.model.SubTitle;

public class RxWordUtil {
	private static List<Integer> listRightMarkerIndex = new ArrayList<Integer>();//】的索引
    private static List<Integer> listleftMarkerIndex = new ArrayList<Integer>();//【的索引
    private static List<String> listCategory = new ArrayList<String>();//分类
    private static List<SubTitle> listSubTitle = new ArrayList<SubTitle>();//二级标题
    private static List<String> listSubContent = new ArrayList<String>();//二级标题下的段落
    private static String firstTile=null;
    
    //获取每一个【 和 】的位置
    public static void findzkh(String wordStr){
    	int s=wordStr.indexOf("【");
        while(s!=-1){
        	listleftMarkerIndex.add(s);
        	s=wordStr.indexOf("【",s+1);
        }
        int e=wordStr.indexOf("】");
        while(e!=-1){
        	listRightMarkerIndex.add(e);
        	e=wordStr.indexOf("】",e+1);
        }
    }
    //获取分类
    public static void GetCategory(String wordStr){
    	if (listleftMarkerIndex.size() == listRightMarkerIndex.size())
        {
            for (int i = 0; i < listRightMarkerIndex.size(); i++){
            	String flclass=wordStr.substring(listleftMarkerIndex.get(i)+1, listRightMarkerIndex.get(i));
            	listCategory.add(flclass);
            }
        }
    }
    //获取二级标题
    public static void GetSubTitle(String wordStr){
    	for(int i=0;i<listRightMarkerIndex.size();i++){
    		//获取题目
    		String title=null;
    		if(i+1==listleftMarkerIndex.size()){
    			int index = wordStr.indexOf("\r", listRightMarkerIndex.get(i) + 3);
    			title=wordStr.substring(listRightMarkerIndex.get(i)+2,index);
    		}else{
    			title=wordStr.substring(listRightMarkerIndex.get(i)+2,listleftMarkerIndex.get(i+1)-1);
    		}
    		title.trim();
    		if(title.contains("\n")||title.contains("\r")){//多个标题
    			String[] titles=(title.split("\n").length==1?title.split("\r"):title.split("\n"));
    			for(int j=0;j<titles.length;j++){
    				String str=titles[j];
    				if(!str.equals("")){
    					if(str.contains(",")){
    						str=str.replace(",", "");
    					}
    					if(firstTile==null){
    						firstTile=str;
    					}
    					SubTitle st=new SubTitle();
    					st.setContent(str);
    					st.setCategory(listCategory.get(i));
    					//获取在正文中的位置 
    					int indextle=0;
    					if(i==0){
    						indextle=wordStr.indexOf(str,listRightMarkerIndex.get(listRightMarkerIndex.size()-1)+3);
    					}else{
    						indextle=wordStr.indexOf(str,listRightMarkerIndex.get(listRightMarkerIndex.size()-1)+firstTile.length());
    					}
    					st.setIndex(indextle);
    					listSubTitle.add(st);
    				}
    			}
    		}
    	}
//    	//寻找第一个】后的\r
//        int index = wordStr.indexOf("\r", listRightMarkerIndex.get(0) + 3);
//        //寻找第一个子对象的内容
//        String firstTitle = wordStr.substring(listRightMarkerIndex.get(0)+ 2, listleftMarkerIndex.get(0));
//        //获取第一个子对象的在正文中的位置 
//        int firstTitleIndex = wordStr.indexOf(firstTitle, listRightMarkerIndex.get(listRightMarkerIndex.size()-1));
//        int lastIndex = wordStr.indexOf(firstTitle, listRightMarkerIndex.get(listRightMarkerIndex.size() - 1) + 2);
//        String str = wordStr.substring(listRightMarkerIndex.get(0)+ 2, lastIndex - listRightMarkerIndex.get(0)- 2);
//        String[] tagers = str.split("\r");
//
//        int categoryIndex = 0;
//        for(String t:tagers){
//        	if (t.contains("【")){
//        		categoryIndex++;
//        	}else{
//        		if (t!=null&&!t.equals("")){
//        			listSubTitle.add(new SubTitle(listCategory.get(categoryIndex), t, wordStr.lastIndexOf(t)));
//        		}
//        	}
//        }
        
    }
    //获取二级子标题下的具体内容
    public static void GetSubContent(String wordStr){
    	String content = null;
        for (int i = 0; i < listSubTitle.size(); i++)
        {
            int startIndex = listSubTitle.get(i).getIndex() + listSubTitle.get(i).getContent().length();
            int contentLength = -1;
            if (i < listSubTitle.size() - 1)
            {
                contentLength = listSubTitle.get(i+1).getIndex() - startIndex;
            }
            else
            {
                contentLength = wordStr.length() - startIndex;
            }

            content = wordStr.substring(startIndex, contentLength);

            listSubContent.add(content);
        }
    }
    //从字符串中识别出附图 输出图名
    public static String GetAttFigures(String str){
    	String AllGra = null;
    	String regStr = "附图\\d{0,3}.+";
    	Pattern pattern = Pattern.compile(regStr);  
		Matcher matcher = pattern.matcher(str);  
		while (matcher.find()) { 
			AllGra=matcher.group();
		}
        return AllGra;
    }
    //获取报文
    public static List<QKInfo> GetQKInfo(String wordStr){
    	List<QKInfo> list=new ArrayList<QKInfo>();
    	findzkh(wordStr);//获取下标
    	GetCategory(wordStr);//获取分类
    	GetSubTitle(wordStr);//获取二级标题
    	GetSubContent(wordStr);//获取内容
    	String contentWithPic = null;
    	//获取附件
        for (int i = 0; i < listSubContent.size(); i++){
        	QKInfo qk=new QKInfo();
        	contentWithPic = listSubContent.get(i);
        	qk.set_BTClass(listCategory.get(i));
        	qk.set_EJBT(listSubTitle.get(i).getCategory());
        	qk.set_ConText(listSubContent.get(i));
            String picadd = GetAttFigures(contentWithPic);
            qk.set_Picture(picadd);
            list.add(qk);
        }
        System.out.println(list.toString());
        return list;
    }
}



















