# AuthenticationV3
## 智聆口语V3签名示例
参考官网：https://cloud.tencent.com/document/product/884/30657
示例代码：https://github.com/gcguo/AuthenticationV3
生成内容加在请求头，智聆口语需要有大量数据提交，建议使用POST， Content-Type: application/json

##### 注释：
- 示例内SECRET_ID和SECRET_KEY需要替换成已购买账号。
- SECRET_ID和SECRET_KEY为敏感内容，不建议放在终端源码内，鉴权前三步建议放在服务器内相对安全
- action和version为接口固定参数，在官网文档中查询并替换，此处以InitOralProcess示例。
- 支持临时签名，直接传入signature和timestamp即可，Date 为 UTC 标准时间的日期，取值需要和公共参数 X-TC-Timestamp 换算的 UTC 标准时间日期一致。

### java
- 需要引入org.apache.commons.codec包(Android)
### python
- 引入hashlib, hmac
