package com.svi.advancedjavatraining;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import com.svi.advancedjavatraining.object.City;
import com.svi.advancedjavatraining.object.CityInfo;
import com.svi.advancedjavatraining.object.PopulationData;
import com.svi.advancedjavatraining.object.Province;
import com.svi.advancedjavatraining.utils.JSONFileReader;

public class Checker implements Callable<List<PopulationData>> {
    private City city;
    private List<Province> provinces;

    public Checker(City city, List<Province> provinces) {
        this.city = city;
        this.provinces = provinces;
    }

    @Override
    public List<PopulationData> call() throws Exception {
        // TODO Auto-generated method stub
        List<PopulationData> populationDataList = new ArrayList<PopulationData>();

                String provinceKey = city.getProvince(); // Assuming province key matches city's province
                for (Province province : provinces) {
                    if (provinceKey.equals(province.getKey())) {
                        // Load population data
                        JSONFileReader jsonFileReader = new JSONFileReader(province.getName(), city.getName());
                        CityInfo cityInfo = jsonFileReader.getData();
                        // Handle null population data (default to 0)
                        double population = (cityInfo != null) ? cityInfo.getPopulation() : 0;
                        populationDataList.add(new PopulationData(province.getName(), city.getName(), population));
                        break;
                    }
                }
            
        return populationDataList;
    }

}