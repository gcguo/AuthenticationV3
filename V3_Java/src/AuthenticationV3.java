import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.TreeMap;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

public class AuthenticationV3 {

    public static void main(String[] args) {
        TreeMap<String, String> header = null;
        try {
            header = getSignature();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (null != header) {
            for (String key : header.keySet()) {
                System.out.println(key + ":" + header.get(key));
            }
        }
    }

    private final static String SECRET_ID = "AKIDz8krbsJ5yKBZQpn74WFkmLPx3EXAMPLE";
    private final static String SECRET_KEY = "Gu5t9xGARNpq86cd98joQYCN3EXAMPLE";

    public static TreeMap<String, String> getSignature() throws Exception {
        String service = "soe";
        String host = "soe.tencentcloudapi.com";
        String region = "";
        String action = "InitOralProcess";
        String version = "2018-07-24";
        String algorithm = "TC3-HMAC-SHA256";
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        String date = sdf.format(new Date(Long.valueOf(timestamp + "000")));

        // ************* 步骤 1：拼接规范请求串 *************
        String httpRequestMethod = "POST";
        String canonicalUri = "/";
//        String canonicalQueryString = "Limit=10&Offset=0";
        String canonicalQueryString = "";
        String canonicalHeaders = "content-type:application/json\n" + "host:" + host + "\n";
        String signedHeaders = "content-type;host";
        String hashedRequestPayload = new String(Hex.encodeHex(DigestUtils.sha256("UNSIGNED-PAYLOAD")));
        String canonicalRequest = httpRequestMethod + "\n" + canonicalUri + "\n" + canonicalQueryString + "\n" + canonicalHeaders + "\n" + signedHeaders + "\n" + hashedRequestPayload;
        System.out.println(canonicalRequest);

        // ************* 步骤 2：拼接待签名字符串 *************
        String credentialScope = date + "/" + service + "/" + "tc3_request";
        String hashedCanonicalRequest = new String(Hex.encodeHex(DigestUtils.sha256(canonicalRequest)));
        String stringToSign = algorithm + "\n" + timestamp + "\n" + credentialScope + "\n" + hashedCanonicalRequest;
        System.out.println(stringToSign);

        // ************* 步骤 3：计算签名 *************
        byte[] secretDate = sign256(("TC3" + SECRET_KEY).getBytes("UTF-8"), date);
        byte[] secretService = sign256(secretDate, service);
        byte[] secretSigning = sign256(secretService, "tc3_request");
        String signature = new String(Hex.encodeHex(sign256(secretSigning, stringToSign))).toLowerCase();
        System.out.println(signature);

        // ************* 步骤 4：拼接 Authorization *************
        String authorization = algorithm + " " + "Credential=" + SECRET_ID + "/" + credentialScope + ", " + "SignedHeaders=" + signedHeaders + ", " + "Signature=" + signature;
        System.out.println(authorization);

        TreeMap<String, String> headers = new TreeMap<>();
        headers.put("Authorization", authorization);
        headers.put("Host", host);
        headers.put("Content-Type", "application/json");
        headers.put("X-TC-Action", action);
        headers.put("X-TC-Timestamp", String.format(Locale.getDefault(),"%s", timestamp));
        headers.put("X-TC-Version", version);
        headers.put("X-TC-Content-SHA256", "UNSIGNED-PAYLOAD");
        headers.put("X-TC-Region", region);
        return headers;
    }

    public static byte[] sign256(byte[] key, String msg) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, mac.getAlgorithm());
        mac.init(secretKeySpec);
        return mac.doFinal(msg.getBytes("UTF-8"));
    }
}
