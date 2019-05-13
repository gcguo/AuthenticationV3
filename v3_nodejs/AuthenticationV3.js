const SECRET_ID = "AKIDz8krbsJ5yKBZQpn74WFkmLPx3EXAMPLE"
const SECRET_KEY = "Gu5t9xGARNpq86cd98joQYCN3EXAMPLE"

const service = "soe"
const host = "soe.tencentcloudapi.com"
const region = ""
const action = "InitOralProcess"
const version = "2018-07-24"
const algorithm = "TC3-HMAC-SHA256"
const timestamp = 1557309332
const dateFormat = new Date(timestamp * 1000);
const date = dateFormat.getFullYear() + "-" + (dateFormat.getMonth() < 10 ? '0' + (dateFormat.getMonth()+1) : (dateFormat.getMonth()+1)) + "-" + (dateFormat.getDate() < 10 ? '0' + dateFormat.getDate() : dateFormat.getDate()) ;
const crypto = require('crypto')

// ************* 步骤 1：拼接规范请求串 *************
const httpRequestMethod = "POST"
const canonicalUri = "/"
const canonicalQueryString = ""
const canonicalHeaders = "content-type:application/json\n" + "host:" + host + "\n"
const signedHeaders = "content-type;host"
const hashedRequestPayload = crypto.createHash('sha256').update("UNSIGNED-PAYLOAD").digest('hex')
const canonicalRequest = httpRequestMethod + "\n" + canonicalUri + "\n" + canonicalQueryString + "\n" + canonicalHeaders + "\n" + signedHeaders + "\n" + hashedRequestPayload
console.log(canonicalRequest)

// ************* 步骤 2：拼接待签名字符串 *************
const credentialScope = date + "/" + service + "/" + "tc3_request"
const hashedCanonicalRequest = crypto.createHash('sha256').update(canonicalRequest).digest('hex')
const stringToSign = algorithm + "\n" + timestamp + "\n" + credentialScope + "\n" + hashedCanonicalRequest
console.log(stringToSign)

// ************* 步骤 3：计算签名 *************
const secretDate = crypto.createHmac('sha256', "TC3" + SECRET_KEY).update(date).digest('hex')
const secretService = crypto.createHmac('sha256', new Buffer(secretDate, 'hex')).update(service).digest('hex')
const secretSigning = crypto.createHmac('sha256', new Buffer(secretService, 'hex')).update("tc3_request").digest('hex')
const signature = crypto.createHmac('sha256', new Buffer(secretSigning, 'hex')).update(stringToSign).digest('hex')
console.log(signature)

// ************* 步骤 4：拼接 Authorization *************
const authorization = algorithm + " " + "Credential=" + SECRET_ID + "/" + credentialScope + ", " + "SignedHeaders=" + signedHeaders + ", " + "Signature=" + signature
console.log(authorization)

const header = {
    "Authorization" : authorization,
    "Host" : host,
    "Content-Type" : "application/json",
    "X-TC-Action" : action,
    "X-TC-Timestamp" : timestamp,
    "X-TC-Version" : version,
    "X-TC-Content-SHA256" : "UNSIGNED-PAYLOAD",
    "X-TC-Region" : region,
}
for (let key in header) {
    console.log(key + ":" + header[key])
}