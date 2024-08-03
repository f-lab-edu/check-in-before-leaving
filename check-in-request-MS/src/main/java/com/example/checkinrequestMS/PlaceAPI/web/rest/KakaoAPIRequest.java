package com.example.checkinrequestMS.PlaceAPI.web.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Component
public class KakaoAPIRequest {

    private static String apiKey = "a0dc4e7625b15b5b4cef4e0a028119b3";

    public static StringBuilder getStoreInfo(String query, double x, double y, int radius){
        try {
            String apiURL = "https://dapi.kakao.com/v2/local/search/keyword.json"
                    + "?page=1&size=15&sort=accuracy"
                    + "&query=" + URLEncoder.encode("맛집", "UTF-8")
                    + "&x=" + x
                    + "&y=" + y
                    + "&radius=" + radius;

            HttpURLConnection con = connection(apiURL);
            StringBuilder response = bufferConnection(con);
            return response;
            //jsonParse(response);
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }catch (MalformedURLException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    private static HttpURLConnection  connection(String apiURL) throws IOException, MalformedURLException {
        URL url = new URL(apiURL);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Authorization", "KakaoAK " + apiKey);
        return con;

    }
    private static StringBuilder bufferConnection(HttpURLConnection con) throws IOException {
        int responseCode = con.getResponseCode();
        BufferedReader br;
        if (responseCode == 200) {
            br = new BufferedReader(new InputStreamReader(con.getInputStream()));
        } else {
            br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
        }

        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = br.readLine()) != null) {
            response.append(inputLine);
        }
        br.close();

        return response;

    }


}
