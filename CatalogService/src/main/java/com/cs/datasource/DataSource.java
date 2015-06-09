package com.cs.datasource;

import org.apache.log4j.Logger;

public abstract class DataSource {
	final static Logger log = Logger.getLogger(DataSource.class);
	private MonitorThread monitorThread;
	
	
	public void setup(){
		connect();
	
		if(monitorThread == null){
			monitorThread = new MonitorThread();
			monitorThread.start();
		}
	}
	
	abstract String name();
	abstract void connect();
	abstract void monitorDataSourceHealth();
	abstract void close();
	
	class MonitorThread extends Thread{
		private static final long MONITOR_THREAD_WAIT_INTERVAL_MS = 30 * 1000;

		public void run(){
			try {
				while(true){
					synchronized (this){
						monitorDataSourceHealth();
						wait(MONITOR_THREAD_WAIT_INTERVAL_MS);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.info("Interrupted. Probably to shut down.:"+e.getMessage());
			}
		}
	}
}
