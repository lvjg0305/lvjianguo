package deepthinking.common;

import java.util.ArrayList;
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
    					str=str.replaceAll("\r|\n|\\s*", "");
    					SubTitle st=new SubTitle();
    					st.setContent(str);
    					st.setCategory(listCategory.get(i));
    					//获取在正文中的位置 
    					int indextle=0;
    					if(i==listRightMarkerIndex.size()-1){
    						indextle=wordStr.indexOf(str,listRightMarkerIndex.get(listRightMarkerIndex.size()-1)+str.length());
    					}else{
    						indextle=wordStr.indexOf(str,listRightMarkerIndex.get(listRightMarkerIndex.size()-1)+3);
    					}
    					st.setIndex(indextle);
    					listSubTitle.add(st);
    				}
    			}
    		}
    	}
    }
    //获取二级子标题下的具体内容
    public static void GetSubContent(String wordStr){
    	String content = null;
        for (int i = 0; i < listSubTitle.size(); i++)
        {
            int startIndex = listSubTitle.get(i).getIndex() + listSubTitle.get(i).getContent().length();
            int endtIndex = -1;
            if (i < listSubTitle.size() - 1){
            	endtIndex = listSubTitle.get(i+1).getIndex();
            } else{
            	endtIndex = wordStr.length();
            }
            content = wordStr.substring(startIndex, endtIndex);
            content=content.trim();
            content=content.replaceAll(" ", "");
            content=content.replaceAll("\r|\n|\\s*", "");
            content=content.replaceAll("\\-[0-9]\\-","");
            listSubTitle.get(i).setSourceText(content);
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
        return AllGra+".jpg";
    }
    //获取报文主方法
    public static List<QKInfo> GetQKInfo(String wordStr){
    	List<QKInfo> list=new ArrayList<QKInfo>();
    	findzkh(wordStr);//获取下标
    	GetCategory(wordStr);//获取分类
    	GetSubTitle(wordStr);//获取二级标题
    	GetSubContent(wordStr);//获取内容
    	String contentWithPic = null;
    	//获取附件
        for (int i = 0; i < listSubTitle.size(); i++){
        	QKInfo qk=new QKInfo();
        	contentWithPic = listSubTitle.get(i).getSourceText();
        	qk.set_BTClass(listSubTitle.get(i).getCategory());
        	qk.set_EJBT(listSubTitle.get(i).getContent());
        	qk.set_ConText(listSubTitle.get(i).getSourceText());
            String picadd = GetAttFigures(contentWithPic);
            qk.set_Picture(picadd);
            list.add(qk);
        }
        System.out.println(list.toString());
        return list;
    }
}



















