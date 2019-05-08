import java.io.*;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.TreeMap;

public class HttpUtil {

    public static void request(final TreeMap<String, String> header, final byte[] params) {
        new Thread(() -> {
            try {
                URL url = new URL(String.format("https://%s/", header.get("Host")));
                Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("web-proxy.oa.com", 8080));
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection(proxy);
                httpURLConnection.setReadTimeout(3000);
                httpURLConnection.setConnectTimeout(3000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);
                for (String key : header.keySet()) {
                    httpURLConnection.setRequestProperty(key, header.get(key));
                    System.out.println(key + ":" + header.get(key));
                }
                httpURLConnection.connect();

                OutputStream outputStream = httpURLConnection.getOutputStream();
                System.out.println(new String(params));
                outputStream.write(params);
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder responseString = new StringBuilder();
                String readLine;
                while ((readLine = bufferedReader.readLine()) != null) {
                    responseString.append(readLine);
                    responseString.append("\r\n");
                }
                bufferedReader.close();

                String jsonString = responseString.toString();
                System.out.println(jsonString);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
