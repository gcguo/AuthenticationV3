using System;
using System.Collections.Generic;
using System.Globalization;
using System.IO;
using System.Security.Cryptography;
using System.Text;

namespace V3_DotNet
{
    class Program
    {

        static string SECRET_ID = "AKIDz8krbsJ5yKBZQpn74WFkmLPx3EXAMPLE";
        static string SECRET_KEY = "Gu5t9xGARNpq86cd98joQYCN3EXAMPLE";

        static void Main(string[] args)
        {
            string service = "soe";
            string host = "soe.tencentcloudapi.com";
            string region = "";
            string action = "InitOralProcess";
            string version = "2018-07-24";
            string algorithm = "TC3-HMAC-SHA256";
            long timestamp = 1557309332;
            DateTime startTime = TimeZone.CurrentTimeZone.ToLocalTime(new DateTime(1970, 1, 1));
            DateTime dt = startTime.AddSeconds(timestamp);
            string date = dt.ToString("yyyy-MM-dd");

            // ************* 步骤 1：拼接规范请求串 *************
            string httpRequestMethod = "POST";
            string canonicalUri = "/";
            string canonicalQueryString = "";
            string canonicalHeaders = "content-type:application/json\n" + "host:" + host + "\n";
            string signedHeaders = "content-type;host";
            string hashedRequestPayload = GetSHA256HashFromString("UNSIGNED-PAYLOAD");
            string canonicalRequest = httpRequestMethod + "\n" + canonicalUri + "\n" + canonicalQueryString + "\n" + canonicalHeaders + "\n" + signedHeaders + "\n" + hashedRequestPayload;
            Console.WriteLine(canonicalRequest);

            // ************* 步骤 2：拼接待签名字符串 *************
            string credentialScope = date + "/" + service + "/" + "tc3_request";
            string hashedCanonicalRequest = GetSHA256HashFromString(canonicalRequest);
            string stringToSign = algorithm + "\n" + timestamp + "\n" + credentialScope + "\n" + hashedCanonicalRequest;
            Console.WriteLine(stringToSign);

            // ************* 步骤 3：计算签名 *************
            byte[] secretDate = GetSHA256HashBySecret(Encoding.UTF8.GetBytes("TC3" + SECRET_KEY), date);
            byte[] secretService = GetSHA256HashBySecret(secretDate, service);
            byte[] secretSigning = GetSHA256HashBySecret(secretService, "tc3_request");
            string signature = ByteToHexStr(GetSHA256HashBySecret(secretSigning, stringToSign));
            Console.WriteLine(signature);

            // ************* 步骤 4：拼接 Authorization *************
            string authorization = algorithm + " " + "Credential=" + SECRET_ID + "/" + credentialScope + ", " + "SignedHeaders=" + signedHeaders + ", " + "Signature=" + signature;
            Console.WriteLine(authorization);

            Dictionary<string, string> directory = new Dictionary<string, string>();
            directory.Add("Authorization", authorization);
            directory.Add("Host", host);
            directory.Add("Content-Type", "application/json");
            directory.Add("X-TC-Action", action);
            directory.Add("X-TC-Timestamp", timestamp.ToString());
            directory.Add("X-TC-Version", version);
            directory.Add("X-TC-Content-SHA256", "UNSIGNED-PAYLOAD");
            directory.Add("X-TC-Region", region);

            foreach (string key in directory.Keys) {
                Console.WriteLine("key:{0},value:{1}", key, directory[key]);
            }

            Console.ReadLine();
        }

        public static string GetSHA256HashFromString(string strData)
        {
            byte[] bytValue = Encoding.UTF8.GetBytes(strData);
            SHA256 sha256 = new SHA256CryptoServiceProvider();
            byte[] retVal = sha256.ComputeHash(bytValue);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < retVal.Length; i++)
            {
                sb.Append(retVal[i].ToString("x2"));
            }
            return sb.ToString();
        }

        public static byte[] GetSHA256HashBySecret(byte[] secret, string message)
        {
            var encoding = new UTF8Encoding();
            byte[] messageBytes = encoding.GetBytes(message);
            using (var hmacsha256 = new HMACSHA256(secret))
            {
                return hmacsha256.ComputeHash(messageBytes);
            }
        }

        public static string ByteToHexStr(byte[] bytes)
        {
            string returnStr = "";
            if (bytes != null)
            {
                for (int i = 0; i < bytes.Length; i++)
                {
                    returnStr += bytes[i].ToString("x2");
                }
            }
            return returnStr;
        }
    }
}
