package com.svi.advancedjavatraining;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import com.svi.advancedjavatraining.object.City;
import com.svi.advancedjavatraining.object.PopulationData;
import com.svi.advancedjavatraining.object.Province;
import com.svi.advancedjavatraining.utils.PopulationFileWriter;
import com.svi.advancedjavatraining.utils.WebLoader;

public class Main {

	public static void main(String[] args) {
		long startTime = System.nanoTime();

		// TODO WORK AREA

		ExecutorService executorService = Executors.newCachedThreadPool();

		WebLoader webLoader = new WebLoader();

		PopulationFileWriter populationFileWriter = new PopulationFileWriter();

		List<Future<List<PopulationData>>> populationDataList = new ArrayList<>();

		List<Double> populationList = new ArrayList<>();

		double totalPopulation = 0;

		try {

			List<Province> provinces = webLoader.getProvinces();
			List<City> cities = webLoader.getCities();

			for (City city : cities) {
				PopulationGetter checker = new PopulationGetter(city, provinces);
				populationDataList.add(executorService.submit(checker));
			}

			populationDataList.forEach(results_future -> {
				try {
					List<PopulationData> results = results_future.get();

					for (PopulationData populationData : results) {
						populationFileWriter.addPopulationRecord(populationData);
						populationList.add(populationData.getPopulation());
					}
				} catch (InterruptedException | ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
			populationFileWriter.writeToFile();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (Double double1 : populationList) {
			totalPopulation += double1;
		}

		System.out.println(String.format("TOTAL POPULATION: %.0f", totalPopulation));

		// Shutdown the executor service
		executorService.shutdown();

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
