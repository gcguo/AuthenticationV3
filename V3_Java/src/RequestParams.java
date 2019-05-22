import java.util.TreeMap;

public class RequestParams {

    byte[] body;
    String url;
    TreeMap<String, String> header;

    public RequestParams(byte[] body, String url, TreeMap<String, String> header) {
        this.body = body;
        this.url = url;
        this.header = header;
    }
}
