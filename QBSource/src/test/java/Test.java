import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {
	@org.junit.Test
	public void test1(){
		String str="ab【c】def【g】hijkl【m】nopq【rs】tuv【wx】yz";
		List list=new ArrayList();
		int a=str.indexOf("【");
		while(a!=-1){
			list.add(a);
			a=str.indexOf("【",a+1);
		}
		System.out.println(list.toString());
	}
	@org.junit.Test
	public void test2(){
		String url = "附图1：周边海域外（对象23）军舰船活动示意图;附图2：对象2及周边海域外军舰船活动示意图"
				+ "4月19日，对象55对对象";
		String regex = "附图\\d{0,3}.+";
		String result = "";  
		Pattern pattern = Pattern.compile(regex);  
		Matcher matcher = pattern.matcher(url);  
		while (matcher.find()) {  
//		      result = matcher.group(1);
			System.out.println(matcher.group());
		} 
//		for(int i=0;i<matcher.groupCount();i++){
//			System.out.println(matcher.group(i));
//		}
//		System.out.println(matcher.groupCount());
	}
}
