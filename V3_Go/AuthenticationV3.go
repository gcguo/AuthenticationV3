package main

import (
	"crypto/hmac"
	"crypto/sha256"
	"encoding/hex"
	"fmt"
	"time"
)

var SECRET_ID = "AKIDz8krbsJ5yKBZQpn74WFkmLPx3EXAMPLE";
var SECRET_KEY = "Gu5t9xGARNpq86cd98joQYCN3EXAMPLE";

func main() {
	var service = "soe"
	var host = "soe.tencentcloudapi.com"
	var region = ""
	var action = "InitOralProcess"
	var version = "2018-07-24"
	var algorithm = "TC3-HMAC-SHA256"
	var timestamp = "1557309332"
	timeFormat := time.Unix(1557309332, 0)
	var date = timeFormat.Format("2006-01-02")

	fmt.Println("test", service, host, region, action, version, algorithm, timestamp, date)

	// ************* 步骤 1：拼接规范请求串 *************
	var httpRequestMethod = "POST"
	var canonicalUri = "/"
	var canonicalQueryString = ""
	var canonicalHeaders = "content-type:application/json\n" + "host:" + host + "\n"
	var signedHeaders = "content-type;host"
	var hashedRequestPayload = Sha256("UNSIGNED-PAYLOAD")
	var canonicalRequest = httpRequestMethod + "\n" + canonicalUri + "\n" + canonicalQueryString + "\n" + canonicalHeaders + "\n" + signedHeaders + "\n" + hashedRequestPayload
	fmt.Println(canonicalRequest)

	// ************* 步骤 2：拼接待签名字符串 *************
	var credentialScope = date + "/" + service + "/" + "tc3_request"
	var hashedCanonicalRequest = Sha256(canonicalRequest)
	var stringToSign = algorithm + "\n" + timestamp + "\n" + credentialScope + "\n" + hashedCanonicalRequest
	fmt.Println(stringToSign)

	// ************* 步骤 3：计算签名 *************
	var secretDate = ComputeHmacSha256([]byte("TC3" + SECRET_KEY), date)
	var secretService = ComputeHmacSha256(secretDate, service)
	var secretSigning = ComputeHmacSha256(secretService, "tc3_request")
	var signature = hex.EncodeToString(ComputeHmacSha256(secretSigning, stringToSign))
	fmt.Println(signature)

	// ************* 步骤 4：拼接 Authorization *************
	var authorization = algorithm + " " + "Credential=" + SECRET_ID + "/" + credentialScope + ", " + "SignedHeaders=" + signedHeaders + ", " + "Signature=" + signature
	fmt.Println(authorization)

	header := make(map[string]string)
	header["Authorization"] = authorization
	header["Host"] = host
	header["Content-Type"] = "application/json"
	header["X-TC-Action"] = action
	header["X-TC-Timestamp"] = string(timestamp)
	header["X-TC-Version"] = version
	header["X-TC-Content-SHA256"] = "UNSIGNED-PAYLOAD"
	header["X-TC-Region"] = region
	for value := range header {
		fmt.Println(value)
	}
}

func Sha256(message string) string {
	HmacSha256 := sha256.New()
	HmacSha256.Write([]byte(message))
	return hex.EncodeToString(HmacSha256.Sum(nil))
}

func ComputeHmacSha256(secret []byte, message string) []byte {
	h := hmac.New(sha256.New, secret)
	h.Write([]byte(message))
	return h.Sum(nil)
}
