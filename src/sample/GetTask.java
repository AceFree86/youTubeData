package sample;

import javafx.concurrent.Task;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class GetTask extends Task<String> {

    private final String authorization;
    private final String cookie;
    private final String referer;
    private final String authuser;
    private final String pageid;
    private final String delegat_context;
    private final String idChannel;

    public GetTask(String authorization, String cookie, String referer,
                   String authuser, String pageid, String delegat_context, String idChannel) {

        this.authorization = authorization;
        this.cookie = cookie;
        this.referer = referer;
        this.authuser = authuser;
        this.pageid = pageid;
        this.delegat_context = delegat_context;
        this.idChannel = idChannel;
    }

    @Override
    protected String call() throws Exception {
        String result;
        URL url = new URL("https://www.googleapis.com/youtube/v3/channels?part=statistics&id="+
                idChannel +"&key=AIzaSyDN-z6TEI7hYpAdti6kaA3TBltt0gMjX7o");
        URLConnection con = url.openConnection();
        con.connect();

        BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
        StringBuilder sb = new StringBuilder();

        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line).append("\n");
        }
        br.close();

        JSONObject json = new JSONObject(sb.toString());
        JSONObject jsonItems = json.getJSONArray("items").getJSONObject(0).getJSONObject("statistics");
        String video = jsonItems.get("videoCount").toString();

        JSONArray jsonData = getYoutubeData(authorization, cookie, referer, authuser, pageid, delegat_context, idChannel);
        result = String.format("%1s-%2s-%3s", getSubscribers(jsonData), getView(jsonData), video);

        updateValue(result);

        return result;
    }

    private JSONArray getYoutubeData(String authorization, String cookie, String referer, String authuser,
                                     String pageid, String delegation_context, String idChannel) throws IOException {

        JSONArray channel= null;

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        cal.add(Calendar.DATE, +2);
        String inclusiveStart = new SimpleDateFormat("yyyyMMdd").format(cal.getTime());
        String exclusiveEnd = new SimpleDateFormat("yyyyMMdd").format(new Date());

        URL url = new URL("https://studio.youtube.com/youtubei/v1/creator/get_channel_dashboard?alt=json&key=AIzaSyBUPetSUmoZL-OhlxA7wSac5XinrygCqMo");
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        httpConn.setRequestMethod("POST");

        httpConn.setRequestProperty("authority", "studio.youtube.com");
        httpConn.setRequestProperty("accept", "*/*");
        httpConn.setRequestProperty("authorization", authorization);
        httpConn.setRequestProperty("content-type", "application/json");
        httpConn.setRequestProperty("cookie", cookie);
        httpConn.setRequestProperty("origin", "https://studio.youtube.com");
        httpConn.setRequestProperty("referer", referer);
        httpConn.setRequestProperty("sec-ch-ua", "\"Chromium\";v=\"106\", \"Google Chrome\";v=\"106\", \"Not;A=Brand\";v=\"99\"");
        httpConn.setRequestProperty("sec-ch-ua-arch", "\"x86\"");
        httpConn.setRequestProperty("sec-ch-ua-bitness", "\"64\"");
        httpConn.setRequestProperty("sec-ch-ua-full-version", "\"106.0.5249.119\"");
        httpConn.setRequestProperty("sec-ch-ua-full-version-list", "\"Chromium\";v=\"106.0.5249.119\", \"Google Chrome\";v=\"106.0.5249.119\", \"Not;A=Brand\";v=\"99.0.0.0\"");
        httpConn.setRequestProperty("sec-ch-ua-mobile", "?0");
        httpConn.setRequestProperty("sec-ch-ua-model", "");
        httpConn.setRequestProperty("sec-ch-ua-platform", "\"Windows\"");
        httpConn.setRequestProperty("sec-ch-ua-platform-version", "\"7.0.0\"");
        httpConn.setRequestProperty("sec-ch-ua-wow64", "?0");
        httpConn.setRequestProperty("sec-fetch-dest", "empty");
        httpConn.setRequestProperty("sec-fetch-mode", "cors");
        httpConn.setRequestProperty("sec-fetch-site", "same-origin");
        httpConn.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/106.0.0.0 Safari/537.36");
        httpConn.setRequestProperty("x-client-data", "CJa2yQEIprbJAQjBtskBCKmdygEIx+PKAQiSocsBCIm8zAEIzLzMAQi+3cwBCMbhzAEIyebMAQj+5swBCNjozAEIj+nMAQjw6swBCOPrzAEInOzMAQ==");
        httpConn.setRequestProperty("x-goog-authuser", authuser);
        httpConn.setRequestProperty("x-goog-pageid", pageid);
        httpConn.setRequestProperty("x-goog-visitor-id", "CgtzRU5JUEhLMXVCUSiKlduaBg%3D%3D");
        httpConn.setRequestProperty("x-origin", "https://studio.youtube.com");
        httpConn.setRequestProperty("x-youtube-client-name", "62");
        httpConn.setRequestProperty("x-youtube-client-version", "1.20221020.04.00");
        httpConn.setRequestProperty("x-youtube-delegation-context", delegation_context);

        httpConn.setDoOutput(true);
        OutputStreamWriter writer = new OutputStreamWriter(httpConn.getOutputStream());
        writer.write("{\"dashboardParams\":{\"channelId\":\"" + idChannel + "\"," +
                "\"factsAnalyticsParams\":{\"nodes\":[{\"key\":\"DASHBOARD_FACT_ANALYTICS_CURRENT\"," +
                "\"value\":{\"query\":{\"dimensions\":[],\"metrics\":[{\"type\":\"VIEWS\"},{\"type\":\"WATCH_TIME\"}," +
                "{\"type\":\"TOTAL_ESTIMATED_EARNINGS\"},{\"type\":\"SUBSCRIBERS_NET_CHANGE\"}]," +
                "\"restricts\":[{\"dimension\":{\"type\":\"USER\"},\"inValues\":[\"" + idChannel + "\"]}]," +
                "\"orders\":[],\"timeRange\":{\"dateIdRange\":{\"inclusiveStart\":" + inclusiveStart + ",\"exclusiveEnd\":" + exclusiveEnd + "}}," +
                "\"currency\":\"USD\",\"returnDataInNewFormat\":true,\"limitedToBatchedData\":false}}}," +
                "{\"key\":\"TOP_VIDEOS\",\"value\":{\"query\":{\"dimensions\":[{\"type\":\"VIDEO\"}]," +
                "\"metrics\":[{\"type\":\"VIEWS\"}],\"restricts\":[{\"dimension\":{\"type\":\"USER\"}," +
                "\"inValues\":[\"" + idChannel + "\"]}],\"orders\":[{\"metric\":{\"type\":\"VIEWS\"}," +
                "\"direction\":\"ANALYTICS_ORDER_DIRECTION_DESC\"}],\"timeRange\":{\"unixTimeRange\":{}},\"limit\":{}," +
                "\"returnDataInNewFormat\":true,\"limitedToBatchedData\":false}}},{\"key\":\"DASHBOARD_FACT_ANALYTICS_LIFETIME_SUBSCRIBERS\"," +
                "\"value\":{\"query\":{\"dimensions\":[],\"metrics\":[{\"type\":\"SUBSCRIBERS_NET_CHANGE\"}]," +
                "\"restricts\":[{\"dimension\":{\"type\":\"USER\"},\"inValues\":[\"" + idChannel + "\"]}]," +
                "\"orders\":[],\"timeRange\":{\"unboundedRange\":{}},\"currency\":\"USD\"," +
                "\"returnDataInNewFormat\":true,\"limitedToBatchedData\":false}}},{\"key\":\"DASHBOARD_FACT_ANALYTICS_TYPICAL\"," +
                "\"value\":{\"getTypicalPerformance\":{\"query\":{\"metrics\":[{\"metric\":{\"type\":\"VIEWS\"}}," +
                "{\"metric\":{\"type\":\"WATCH_TIME\"}},{\"metric\":{\"type\":\"TOTAL_ESTIMATED_EARNINGS\"}}]," +
                "\"externalChannelId\":\"" + idChannel + "\",\"timeRange\":{\"dateIdRange\":{\"inclusiveStart\":" + inclusiveStart + "," +
                "\"exclusiveEnd\":" + exclusiveEnd + "}},\"type\":\"TYPICAL_PERFORMANCE_TYPE_NORMAL\"," +
                "\"entityType\":\"TYPICAL_PERFORMANCE_ENTITY_TYPE_CHANNEL\",\"currency\":\"USD\"}}}}," +
                "{\"key\":\"TOP_VIDEOS_VIDEO\",\"value\":{\"getCreatorVideos\":{\"mask\":{\"videoId\":true,\"title\":true," +
                "\"permissions\":{\"all\":true}}}}}],\"connectors\":[{\"extractorParams\":{\"resultKey\":\"TOP_VIDEOS\"," +
                "\"resultTableExtractorParams\":{\"dimension\":{\"type\":\"VIDEO\"}}}," +
                "\"fillerParams\":{\"targetKey\":\"TOP_VIDEOS_VIDEO\",\"idFillerParams\":{}}}]}," +
                "\"videoSnapshotAnalyticsParams\":{\"nodes\":[{\"key\":\"VIDEO_SNAPSHOT_DATA_QUERY\"," +
                "\"value\":{\"getVideoSnapshotData\":{\"externalChannelId\":\"" + idChannel + "\"," +
                "\"catalystType\":\"CATALYST_ANALYSIS_TYPE_RECENT_VIDEO_PERFORMANCE\",\"showCtr\":true}}}]," +
                "\"connectors\":[]},\"cardProducerTimeout\":\"CARD_PRODUCER_TIMEOUT_SHORT\"}," +
                "\"context\":{\"client\":{\"clientName\":62,\"clientVersion\":\"1.20221020.04.00\"}," +
                "\"request\":{\"returnLogEntry\":true,\"internalExperimentFlags\":[]}," +
                "\"user\":{" + setPageID(pageid) + "\"delegationContext\":{\"externalChannelId\":\"" + idChannel + "\"," +
                "\"roleType\":{\"channelRoleType\":\"CREATOR_CHANNEL_ROLE_TYPE_OWNER\"}}," +
                "\"serializedDelegationContext\":\"" + delegation_context + "\"}}}");
        writer.flush();
        writer.close();
        httpConn.getOutputStream().close();

        InputStream responseStream = httpConn.getResponseCode() / 100 == 2
                ? httpConn.getInputStream()
                : httpConn.getErrorStream();
        Scanner s = new Scanner(responseStream).useDelimiter("\\A");
        String response = s.hasNext() ? s.next() : "";

        JSONObject json = new JSONObject(response);
        JSONArray jsonArray = json.getJSONArray("cards");
        for (int a = 0; a < jsonArray.length(); a++) {
            JSONObject cards = jsonArray.getJSONObject(a);
            if (cards.get("id").equals("facts")) {
                channel = cards.getJSONObject("body")
                        .getJSONObject("basicCard")
                        .getJSONObject("item")
                        .getJSONObject("channelFactsItem")
                        .getJSONObject("channelFactsData")
                        .getJSONArray("results");

            }
        }
        return channel;
    }

    private static String getSubscribers(JSONArray channel) {
        String subscribers="";
        for (int i = 0; i < channel.length(); i++) {
            JSONObject results = channel.getJSONObject(i);
            if (results.get("key").equals("DASHBOARD_FACT_ANALYTICS_LIFETIME_SUBSCRIBERS")) {
                JSONArray jsonRow = results.getJSONObject("value")
                        .getJSONObject("resultTable")
                        .getJSONArray("metricColumns");
                subscribers = jsonRow.getJSONObject(0)
                        .getJSONObject("counts")
                        .getJSONArray("values")
                        .get(0)
                        .toString();
            }
        }
        return subscribers;
    }

    private static String getView(JSONArray channel) {
        String view ="";
        for (int i = 0; i < channel.length(); i++) {
            JSONObject results = channel.getJSONObject(i);
            if (results.get("key").equals("DASHBOARD_FACT_ANALYTICS_CURRENT")) {
                JSONArray jsonRow = results.getJSONObject("value")
                        .getJSONObject("resultTable")
                        .getJSONArray("metricColumns");
                view = jsonRow
                        .getJSONObject(0)
                        .getJSONObject("counts")
                        .getJSONArray("values")
                        .get(0)
                        .toString();
            }
        }
        return view;
    }

    private static String setPageID(String text) {
        String usePageId = "";
        if (text.contains("undefined")) {
            usePageId = "";
        } else {
            usePageId = "\"onBehalfOfUser\":\""+ text +"\",";
        }
        return usePageId;
    }
}
