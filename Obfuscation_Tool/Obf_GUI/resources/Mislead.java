package TOBEREPLACED;

import java.sql.Timestamp;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Mislead implements Runnable{
	private AtomicInteger counter = new AtomicInteger(0);
	private ConcurrentLinkedQueue<String> clq;
	private Thread thread;
	private static Mislead Instance;
	private boolean isWaiting = true;
	
	public static Mislead getMisleadInstance(){
		if (Instance == null){
			Instance = new Mislead();
		}
		
		return Instance;
	}
	
	private Mislead(){
		clq = new ConcurrentLinkedQueue<String>();
		thread = new Thread(this);
		thread.setDaemon(true);
		thread.start();
	}
	
	@Override
	public void run(){
		while(true){
			if (clq.size() == 0 || clq.peek() == null){
				try {
					this.wait();
					isWaiting = true;
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				continue;
			}
			
			String status = clq.poll();
			System.out.println(status);
		}
	}
	
	
	public void addStatus(){
		counter.getAndAdd(1);
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		clq.add(ts.toString() + "\t\t" + "Class Status Satisfied.");
		if (this.isWaiting){
			isWaiting = false;
			this.notify();
		}
	}
}
