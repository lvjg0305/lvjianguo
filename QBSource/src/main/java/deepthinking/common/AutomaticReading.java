package deepthinking.common;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import deepthinking.aspect.GetFileFromFTP;


@Component
public class AutomaticReading implements ApplicationRunner{
	private static long initialDelay = 1;
	private static long period = 6000;
	//时间间隔(一天)
//	private static final long PERIOD_DAY = 24 * 60 * 60 * 1000;
	
	private static Logger logger = LoggerFactory.getLogger(AutomaticReading.class);
	
	@Override
    public void run(ApplicationArguments var1) throws Exception{
		startAut();
	}
	public void startAut(){
		//第一种方式，比较科学与安全，注释的第二种感觉不是很好。而且timer不是线程安全的
		ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor(); 
		Runnable runnable = new Runnable() {  
            public void run() {  
            	try {
            		//执行读取FTP文件的方法
            		GetFileFromFTP.findFile();
				} catch (Exception e) {
					logger.error(e.getMessage());
				} 
            }  
        };  
        service.scheduleAtFixedRate(runnable, initialDelay, period, TimeUnit.SECONDS);  
//		Calendar calendar = Calendar.getInstance();
//		calendar.set(Calendar.HOUR_OF_DAY, 1); //凌晨1点
//		calendar.set(Calendar.MINUTE, 0);
//		calendar.set(Calendar.SECOND, 0);
//		Date date=calendar.getTime(); //第一次执行定时任务的时间
//		//如果第一次执行定时任务的时间 小于当前的时间
//		//此时要在 第一次执行定时任务的时间加一天，以便此任务在下个时间点执行。如果不加一天，任务会立即执行。
//		if (date.before(new Date())) {
//			date = this.addDay(date, 1);
//		}
//		Timer timer = new Timer();
//		//安排指定的任务在指定的时间开始进行重复的固定延迟执行。
//		timer.schedule(new TimerTask(){
//			@Override
//			public void run() {
//				try {
//            		
//				} catch (Exception e) {
//					logger.error(e.getMessage());
//				}  
//			}
//		},date,PERIOD_DAY);
	}
//	// 增加或减少天数
//	public Date addDay(Date date, int num) {
//		Calendar startDT = Calendar.getInstance();
//		startDT.setTime(date);
//		startDT.add(Calendar.DAY_OF_MONTH, num);
//		return startDT.getTime();
//	}

}
