package net.acesinc.data.json.generator.log;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;


import static net.acesinc.data.json.generator.log.MqttLogger.*;

class MqttLoggerTest {

    @Test
    void getMqttClient(String access_token) {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("broker.server", "wss://cloud.smartplatform.io");
        map.put("broker.port", 443);
        map.put("clientId", "simulatore-birex");
        map.put("username", "admin@birex.it@birex");
        map.put("password", access_token);//"eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJqOXFtS05hdXV5bzBYTExVa2VBR1RzTDdkWXhta2xlSEZPYXRUOGVnZTdJIn0.eyJleHAiOjE2NDgyOTIyMDQsImlhdCI6MTY0Njk5NjIwNCwianRpIjoiYzdiN2I1YjUtYTRlYi00OWIyLThjZWMtZGMwNDg2OTliNTA2IiwiaXNzIjoiaHR0cHM6Ly9pZHAuc21hcnRwbGF0Zm9ybS5pby9hdXRoL3JlYWxtcy9zcCIsImF1ZCI6ImFjY291bnQiLCJzdWIiOiIwZWI3MmI5OS0wZDBmLTQ2NGMtYjE1OS0zNWU4Y2RkZTJhODYiLCJ0eXAiOiJCZWFyZXIiLCJhenAiOiJnYXRld2F5LWJpcmV4Iiwic2Vzc2lvbl9zdGF0ZSI6ImNhYWU5MTJhLTFiNjgtNGM4Mi04YWUzLTQ4ZjZjYTI1YzY3YyIsImFjciI6IjEiLCJyZWFsbV9hY2Nlc3MiOnsicm9sZXMiOlsib2ZmbGluZV9hY2Nlc3MiLCJiaXJleCIsInVtYV9hdXRob3JpemF0aW9uIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwidmlldy1wcm9maWxlIl19fSwic2NvcGUiOiIiLCJjbGllbnRJZCI6ImdhdGV3YXktYmlyZXgiLCJjbGllbnRIb3N0IjoiOTMuNDEuMzMuMTgxIiwicHJlZmVycmVkX3VzZXJuYW1lIjoic2VydmljZS1hY2NvdW50LWdhdGV3YXktYmlyZXgiLCJjbGllbnRBZGRyZXNzIjoiOTMuNDEuMzMuMTgxIn0.RsyVSQCajyFAmxu7TG0ceqg_6UfxXUFiV3pNR2OiFWnvifbikBzN6uV1lRYRcC6IOHxSordhQ7PhpViVXPt1_zgpCj4APxWxrqCYEzyBRtREqtgLh3AJT_xonV8SGnePSPto4wk-VjM5whBywX4t4ayv18rr08xGwofzGQIa-QSGxFueGiyZfFfhwLaXMfLAGqxbLRJ2ZURgelk4hdB5mAmDoBzp1w8mKNPcBNiJux6tRwtrNNtG66dYbzNYYwbIpKzdnoSk4ZC88qdTxjqwsIe6bD-EYe5D8Rpvbx3VmZ6QE_Z1CfbuvH0ynoV2TDQlIb4VFMl_-qcXHQWwJPTnJA");
        map.put("topic", "/birex/test/atest");
        map.put("qos", 0);

        try {
            MqttLogger mqttLogger = new MqttLogger(queue, map);
            int i = 2;
            while (true){
                i--;
                mqttLogger.logEvent("{\"test\":1}", new HashMap<>());
                Thread.sleep(5000);
                System.out.println(queue);
                if (i < 0)
                    break;

            }
        } catch (MqttException | InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Test
    void testToken(){
        /*
        curl --location --request POST 'https://idp.smartplatform.io/auth/realms/sp/protocol/openid-connect/token' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'client_id=gateway-birex' \
--data-urlencode 'client_secret=06031641-a3d5-4d37-be64-32b1030b8594' \
--data-urlencode 'grant_type=client_credentials'

         */

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        //headers.add("Authorization", "Basic " + encodedCredentials);
        //ResponseEntity<String> response = null;
        RestTemplate restTemplate = new RestTemplate();



        MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
        map.add("client_id", "gateway-birex");
        map.add("client_secret", "06031641-a3d5-4d37-be64-32b1030b8594");
        map.add("grant_type", "client_credentials");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
        String access_token_url = "https://idp.smartplatform.io/auth/realms/sp/protocol/openid-connect/token";

        ResponseEntity<String> response = restTemplate.postForEntity( access_token_url, request , String.class );

//
//        HttpEntity<String> request = new HttpEntity<String>(headers);
//
//        access_token_url += "?client_id=gateway-birex";
//        access_token_url += "&client_secret=06031641-a3d5-4d37-be64-32b1030b8594";
//        access_token_url += "&grant_type=client_credentials";
//        response = restTemplate..exchange(access_token_url, HttpMethod.POST, request, String.class);


        System.out.println("Access Token Response ---------" + response.getBody());
        JSONParser parser = new JSONParser();
        try {
            JSONObject jsonObject = (JSONObject) parser.parse(response.getBody());
            System.out.println("jsonObject ---------" + jsonObject);
            if(jsonObject.keySet().contains("access_token")) {
                String access_token = (String) jsonObject.get("access_token");
                System.out.println("access_token ---------" + access_token);
                getMqttClient(access_token);
            }


        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}