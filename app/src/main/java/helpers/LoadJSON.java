package helpers;

import android.os.AsyncTask;

import com.weekendjack.weekendjack.JsonDisplayActivity;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by aidy121 on 2018-10-08.
 */

public class LoadJSON {

    private static final String serverURL = "http://192.168.1.63:80/";

    public static void downloadJSON(final JsonDisplayActivity jda, final String urlWebService, final String[] typeNames, final String[] typeValues) {

        class DownloadJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    if (s != null) {
                        JSONArray jsonArray = new JSONArray(s);
                        jda.handleJson(jsonArray);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL(serverURL + urlWebService);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    if (typeNames.length > 0) {
                        conn.setDoOutput(true);
                        OutputStream out = new BufferedOutputStream(conn.getOutputStream());
                        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
                        String toPrint = "";
                        for (int i = 0; i < typeNames.length; i++) {
                            toPrint += URLEncoder.encode(typeNames[i], "UTF-8") + "=" + URLEncoder.encode(typeValues[i], "UTF-8");
                            if (i != typeNames.length - 1) {
                                toPrint += "&";
                            }
                        }
                        writer.write(toPrint);
                        writer.flush();
                        writer.close();
                        out.close();
                    }
                    conn.connect();
                    StringBuilder sb = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }
                    return sb.toString().trim();
                } catch (Exception e) {
                    if (urlWebService.equals("load_service_types.php")) {
                        return "[{\"id\":\"0\",\"service_name\":\"Plumbing\"},{\"id\":\"1\",\"service_name\":\"Electrician\"}]";
                    } else if (urlWebService.equals("search_database.php")) {
                        return "[{\"id\":\"1\",\"type_id\":\"0\",\"post_date\":\"2018-09-02 22:13:25\",\"days_available\":\"06\",\"user_id\":\"0\",\"title\":\"Test #2\",\"description\":\"Desp Test2\"},{\"id\":\"2\",\"type_id\":\"0\",\"post_date\":\"2018-09-02 22:13:25\",\"days_available\":\"6\",\"user_id\":\"0\",\"title\":\"Test #3\",\"description\":\"Desp Test3\"}]";
                    }
                    e.printStackTrace();
                    return null; // Connection failed
                }
            }
        }
        DownloadJSON getJSON = new DownloadJSON();
        getJSON.execute();
    }

}
