import java.io.*;
import java.net.*;

public class RequestUtil {

    public static void request(RequestParams requestParams, CallBack callBack) {
//        new Thread().start();
        try {
            URL url = new URL(requestParams.url);
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("web-proxy.oa.com", 8080));
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection(proxy);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            for (String key : requestParams.header.keySet()) {
                httpURLConnection.setRequestProperty(key, requestParams.header.get(key));
            }
            httpURLConnection.connect();

            OutputStream outputStream = httpURLConnection.getOutputStream();
            outputStream.write(requestParams.body);
            outputStream.flush();
            outputStream.close();

            if (httpURLConnection.getResponseCode() == 200) {
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                String readLine;
                while ((readLine = bufferedReader.readLine()) != null) {
                    stringBuilder.append(readLine);
                    stringBuilder.append("\r\n");
                }
                callBack.onSuccess(stringBuilder.toString());
            }else {
                callBack.onFailure(httpURLConnection.getResponseMessage());
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
