package sample;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class GetName {

    public String getName(String id) throws IOException {
        String result;

        URL url = new URL("https://www.googleapis.com/youtube/v3/channels?part=snippet&" +
                "id="+ id +"&key= ");

        URLConnection connect = url.openConnection();
        connect.connect();

        BufferedReader br = new BufferedReader(new InputStreamReader(connect.getInputStream()));
        StringBuilder sb = new StringBuilder();

        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line).append("\n");
        }
        br.close();

        JSONObject json = new JSONObject(sb.toString());
        JSONObject jsonItems = json.getJSONArray("items")
                .getJSONObject(0)
                .getJSONObject("snippet")
                .getJSONObject("localized");
        result = jsonItems.get("title").toString();

        return result;
    }
}
