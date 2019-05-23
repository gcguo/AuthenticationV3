import com.google.gson.Gson;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;

public class AuthenticationV3 {

    public static String REF_TEXT = "china";
    public static String AUDIO_DATA = "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";
    public static String SOE_URL = "https://soe.tencentcloudapi.com/";

    public static void main(String[] args) {
        String sessionId = UUID.randomUUID().toString();
        InitOralProcess initOralProcess = new InitOralProcess(sessionId, REF_TEXT, 1, 1, 1.0f);
        initOralProcess.setServerType(InitOralProcess.SERVER_TYPE_ENGLISH);
        System.out.println(new Gson().toJson(initOralProcess));
        RequestUtil.request(new RequestParams(new Gson().toJson(initOralProcess).getBytes(), SOE_URL, getSignature("InitOralProcess")), new CallBack() {
            @Override
            public void onSuccess(String response) {
                System.out.println(response);

                TransmitOralProcess transmitOralProcess = new TransmitOralProcess(1, 1, TransmitOralProcess.VOICE_FILE_MP3, TransmitOralProcess.VOICE_ENCODE_PCM, AUDIO_DATA, sessionId);
                RequestUtil.request(new RequestParams(new Gson().toJson(transmitOralProcess).getBytes(), SOE_URL, getSignature("TransmitOralProcess")), new CallBack() {
                    @Override
                    public void onSuccess(String response) {
                        System.out.println(response);
                    }

                    @Override
                    public void onFailure(String errorMessage) {
                        System.out.println(errorMessage);
                    }
                });
            }

            @Override
            public void onFailure(String errorMessage) {
                System.out.println(errorMessage);
            }
        });
    }

    private final static String SECRET_ID = "AKIDz8krbsJ5yKBZQpn74WFkmLPx3EXAMPLE";
    private final static String SECRET_KEY = "Gu5t9xGARNpq86cd98joQYCN3EXAMPLE";

    public static TreeMap<String, String> getSignature(String initOrTrans) {
        String service = "soe";
        String host = "soe.tencentcloudapi.com";
        String region = "";
        String action = initOrTrans;
        String version = "2018-07-24";
        String algorithm = "TC3-HMAC-SHA256";
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        String date = sdf.format(new Date(Long.valueOf(timestamp + "000")));

        // ************* 步骤 1：拼接规范请求串 *************
        String httpRequestMethod = "POST";
        String canonicalUri = "/";
        String canonicalQueryString = "";
        String canonicalHeaders = "content-type:application/json\n" + "host:" + host + "\n";
        String signedHeaders = "content-type;host";
        String hashedRequestPayload = sign256("UNSIGNED-PAYLOAD");
        String canonicalRequest = httpRequestMethod + "\n" + canonicalUri + "\n" + canonicalQueryString + "\n" + canonicalHeaders + "\n" + signedHeaders + "\n" + hashedRequestPayload;
        System.out.println(canonicalRequest);

        // ************* 步骤 2：拼接待签名字符串 *************
        String credentialScope = date + "/" + service + "/" + "tc3_request";
        String hashedCanonicalRequest = sign256(canonicalRequest);
        String stringToSign = algorithm + "\n" + timestamp + "\n" + credentialScope + "\n" + hashedCanonicalRequest;
        System.out.println(stringToSign);

        // ************* 步骤 3：计算签名 *************
        byte[] secretDate = sign256WithKey(("TC3" + SECRET_KEY).getBytes(), date);
        byte[] secretService = sign256WithKey(secretDate, service);
        byte[] secretSigning = sign256WithKey(secretService, "tc3_request");
        String signature = bytesToHexString(sign256WithKey(secretSigning, stringToSign));
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

    public static byte[] sign256WithKey(byte[] key, String msg) {
        Mac mac;
        byte[] result = null;
        try {
            mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec(key, mac.getAlgorithm());
            mac.init(secretKeySpec);
            result = mac.doFinal(msg.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String sign256(String message) {
        byte[] signBytes = null;
        try {
            signBytes = MessageDigest.getInstance("SHA-256").digest(message.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return bytesToHexString(signBytes);
    }

    public static String bytesToHexString(byte[] bytes) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() < 2)
                stringBuffer.append(0);
            stringBuffer.append(hex);
        }
        return stringBuffer.toString().toLowerCase();
    }
}
