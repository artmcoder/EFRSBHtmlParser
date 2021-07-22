package ru.yakunin.efrsbhtmlparser.controller;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ControllerUtils {
    public static List<String> getAllTowns() {
        List<String> towns = new ArrayList<>();
        String jsonFile = getJsonFile();
        try {
            JSONParser parser = new JSONParser();
            JSONArray townsInJson = (JSONArray) parser.parse(jsonFile);
            for (int i = 0; i < townsInJson.size(); i++) {
                JSONObject townInJson = (JSONObject) townsInJson.get(i);
                towns.add((String) townInJson.get("city"));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Collections.sort(towns);
        return towns;
    }

    public static List<String> getAllRegions() {
        List<String> regions = new ArrayList<>();
        String jsonFile = getJsonFile();
        try {
            JSONParser parser = new JSONParser();
            JSONArray regionsInJson = (JSONArray) parser.parse(jsonFile);
            for (int i = 0; i < regionsInJson.size(); i++) {
                JSONObject regionInJson = (JSONObject) regionsInJson.get(i);
                String region = (String) regionInJson.get("region");
                if (!regions.contains(region)) regions.add(region);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Collections.sort(regions);
        return regions;
    }

    private static String getJsonFile() {
        StringBuilder builder = new StringBuilder();
        try {
            List<String> lines = Files.readAllLines(Paths
                    .get("data/regions_cities.json"));
            lines.forEach(line -> builder.append(line + "\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }


}
