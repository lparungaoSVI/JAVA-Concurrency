package com.svi.advancedjavatraining;

import java.util.concurrent.TimeUnit;

public class Main {

	public static void main(String[] args) {
		long startTime = System.nanoTime();
		
		// TODO WORK AREA
		// TODO END WORK AREA
		
		long endTime = System.nanoTime();
		long totalTime = endTime - startTime;
		long millis = TimeUnit.NANOSECONDS.toMillis(totalTime);

		long seconds = TimeUnit.NANOSECONDS.toSeconds(totalTime);

		long minutes = TimeUnit.NANOSECONDS.toMinutes(totalTime);

		long hours = TimeUnit.NANOSECONDS.toHours(totalTime);

		String time = String.format("%d:%02d:%02d.%03d", hours, minutes, seconds, millis);
		System.out.println(time);

	}

}
