package com.cs.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

/*
 * Memory information is obtained from /proc/meminfo, so only Unix/Linux like system
 * will support this class.
 */
public class SystemMemoryInfo {
	final static Logger log = Logger.getLogger(SystemMemoryInfo.class);

	private static ScheduledExecutorService scheduledExecutorService;

	private static String MEMINFO_FILE = "/proc/meminfo"; 
	private static boolean memCheckEnabled;
	private static long freeMemAmount = 0;
	private static final long LOW_MEM_THRESHOLD = 3L*1024L*1024L; //3 GB
	
	public static void init(int memCheckInterval) {
		File f = new File(MEMINFO_FILE);
		memCheckEnabled = f.exists() && !f.isDirectory();
		if (memCheckEnabled) {

			readMemoryInfoFile();

			log.info(String.format("Scheduled thread to read /proc/meminfo every %d seconds", memCheckInterval));
			scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
			scheduledExecutorService.scheduleAtFixedRate(new MemoryInfoReader(), 0, memCheckInterval, TimeUnit.SECONDS);
		} else {
			log.info("Cannot find /proc/meminfo, memory check will be disabled");
		}
	}

	private static void readMemoryInfoFile() {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(MEMINFO_FILE));

			long sizeMemFree = 0;
			long sizeBuffers = 0;
			long sizeCached = 0;
			long sizeSwapCached = 0;
			int count = 0;
			String line = br.readLine();
			while (line != null) {
				if (line.startsWith("MemFree:") || line.startsWith("Buffers:")
						|| line.startsWith("Cached") || line.startsWith("SwapCached")) {
					int idx1 = line.indexOf(":");
					int idx2 = line.lastIndexOf("kB");
					String strSize = line.substring(idx1+1, idx2-1).trim();

					if (line.startsWith("MemFree:")) {
						sizeMemFree = Long.parseLong(strSize);
					} else if (line.startsWith("Buffers:")) {
						sizeBuffers = Long.parseLong(strSize);
					} else if (line.startsWith("Cached:")) {
						sizeCached = Long.parseLong(strSize);
					} else if (line.startsWith("SwapCached:")) {
						sizeSwapCached = Long.parseLong(strSize);
					}

					//all lines read
					if (++count == 4) {
						break;
					}
				}
				line = br.readLine();
			}

			if (count < 4) {
				log.error("Error: less than 4 rows read from /proc/meminfo for free memory information");
			}

			long sizeTotal = sizeMemFree + sizeBuffers + sizeCached + sizeSwapCached;

			log.info(String.format("Current system free memory is %d kb (MemFree %d, Buffers %d, Cached %d, SwapCached %d)",
					sizeTotal, sizeMemFree, sizeBuffers, sizeCached, sizeSwapCached));

			if (sizeTotal > 0) {
				updateFreeMemAmount(sizeTotal);
			}
		} catch (IOException e) {
			log.error("Exception in reading memory info file", e);
		} finally {
			try {
				if (br != null) {
					br.close();
				}
			} catch (IOException e) {
				log.error("Exception in closing the buffered reader", e);
			}
		}
	}

	private synchronized static void updateFreeMemAmount(long size) {
	    freeMemAmount = size;
	  }
	
	static class MemoryInfoReader implements Runnable {
		public void run() {
			try {
				readMemoryInfoFile();
			} catch (Throwable t) {
				log.error("error calling readMemoryInfoFile", t);
			}
		}
	}
}
