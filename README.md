# ontid-auth-server

* 1. [注册接口](#注册接口)
	* 1.1. [注册ons](#注册ons)
	* 1.2. [获取注册ons交易参数](#获取注册ons交易参数)
	* 1.3. [发送注册hash](#发送注册hash)
	* 1.4. [查询注册是否成功](#查询注册是否成功)
	* 1.5. [根据ontid和主域名获取ons列表](#根据ontid和主域名获取ons列表)
* 2. [登录接口](#登录接口)
	* 2.1. [获取message](#获取message)
	* 2.2. [回调验证](#回调验证)
	* 2.3. [查询登录是否成功](#查询登录是否成功)
* 3. [调用合约接口](#调用合约接口)
	* 3.1. [调用合约](#调用合约)
	* 3.2. [获取交易参数](#获取交易参数)
	* 3.3. [发送交易hash](#发送交易hash)
	* 3.4. [查询交易结果](#查询交易结果)
	

## 注册接口

###  注册ons

```
url：/api/v1/ons/{ons}
method：Get
```

请求：
| Field Name | Type | Description |
|---|---|---|
|ons         |String|需要注册的域名|

响应：

```source-json
{
    "action": "registerDomain",
    "code": 0,
    "msg": "SUCCESS",
    "result": {
        "callback": "http://192.168.3.121:7878/api/v1/ons/invoke",
        "id": "a0308abd-d57e-41fe-9554-5fe6435db2fe",
        "qrcodeUrl": "http://192.168.3.121:7878/api/v1/ons/qrcode/a0308abd-d57e-41fe-9554-5fe6435db2fe"
    },
    "version": "v1"
}
```
| Field Name | Type | Description |
| :-- | :-- | :-- |
| action    | String | 动作标志                      |
| code      | int    | 错误码                        |
| msg       | String | 成功为SUCCESS，失败为错误描述 |
| result    | String | 成功返回记录信息，失败返回""  |
| callback  | String | 回调url地址                   |
| id        | String | 记录的id                      |
| qrcodeUrl | String | 获取交易参数的地址            |
| version   | String | 版本号                        |


###  获取注册ons交易参数

```
url：/api/v1/ons/qrcode/{id}
method：Get
```

请求：
| Field Name | Type | Description |
|---|---|---|
|ons         |String|需要注册的域名|

响应：

```source-json
{
    "action": "invoke",
    "id": "a0308abd-d57e-41fe-9554-5fe6435db2fe",
    "params": {
        "invokeConfig": {
            "gasLimit": 20000,
            "contractHash": "8b344a43204e60750e7ccc8c1b708a67f88f2c43",
            "functions": [
                {
                    "args": [
                        {
                            "name": "arg0-str",
                            "value": "String:abc.on.ont"
                        },
                        {
                            "name": "arg1-str",
                            "value": "Address:%address"
                        },
                        {
                            "name": "arg2-str",
                            "value": "Address:AcdBfqe7SG8xn4wfGrtUbbBDxw2x1e8UKm"
                        },
                        {
                            "name": "arg3-int",
                            "value": 100000000
                        }
                    ],
                    "operation": "transferOng"
                }
            ],
            "payer": "%address",
            "gasPrice": 500
        }
    },
    "version": "v1.0.0"
}
```
| Field Name | Type | Description |
| :-- | :-- | :-- |
| action    | String | 动作标志                      |
| id        | String | 记录的id                      |
| params    | Object | 交易参数                      |
| version   | String | 版本号                        |


###  发送注册hash

```
url：/api/v1/ons/invoke
method：Post
```

请求：
```source-json
{
  "action": "invoke",
  "id": "10ba038e-48da-487b-96e8-8d3b99b6d18a",
  "error": 0,
  "desc": "SUCCESS",
  "result": "tx hash"
}
```
| Field Name | Type | Description |
|---|---|---|
| action    | String | 动作标志                      |
| id        | String | 记录的id                      |
| error     | int    | 错误码                        |
| desc      | String | 成功为SUCCESS，失败为错误描述 |
| result    | String | 交易hash                      |


###  查询注册是否成功

```
url：/api/v1/ons/result/{id}
method：Get
```

请求：
| Field Name | Type | Description |
|---|---|---|
|id          |String|记录的id     |

响应：

```source-json
{
    "action": "registerResult",
    "code": 0,
    "msg": "SUCCESS",
    "result": "1",
    "version": "v1"
}
```
| Field Name | Type | Description |
| :-- | :-- | :-- |
| action    | String | 动作标志                      |
| code      | int    | 错误码                        |
| msg       | String | 成功为SUCCESS，失败为错误描述 |
| result    | String | 成功返回"1"，失败返回"0"      |
| version   | String | 版本号                        |


###  根据ontid和主域名获取ons列表

```
url：/api/v1/ons/list
method：Post
```

请求：
```source-json
{
	"ontid": "did:ont:AGWYQHd4bzyhrbpeYCMsxXYQcJo95VtR5q",
	"domain": "ont.io"
}
```
| Field Name | Type | Description |
|---|---|---|
| ontid    | String | 用户ontid                      |
| domain   | String | 网站主域名                     |

响应：

```source-json
{
    "action": "getOnsList",
    "code": 0,
    "msg": "SUCCESS",
    "result": [
        "test.ont.io",
        "2222.ont.io",
        "1111.ont.io"
    ],
    "version": "v1"
}
```
| Field Name | Type | Description |
| :-- | :-- | :-- |
| action    | String | 动作标志                      |
| code      | int    | 错误码                        |
| msg       | String | 成功为SUCCESS，失败为错误描述 |
| result    | String | 成功返回域名列表，失败返回""  |
| version   | String | 版本号                        |


## 登录接口

###  获取message

```
url：/api/v1/login
method：Get
```

响应：

```source-json
{
    "action": "getMessage",
    "code": 0,
    "msg": "SUCCESS",
    "result": {
        "callback": "http://192.168.3.121:7878/api/v1/login/callback",
        "id": "e1471264-b2d1-45fa-9eb5-1a8ad6ce2b6c",
        "message": "hello 1561537241660"
    },
    "version": "v1"
}
```
| Field Name | Type | Description |
| :-- | :-- | :-- |
| action    | String | 动作标志                      |
| code      | int    | 错误码                        |
| msg       | String | 成功为SUCCESS，失败为错误描述 |
| result    | String | 成功返回记录信息，失败返回""  |
| callback  | String | 回调url地址                   |
| id        | String | 记录的id                      |
| message   | String | 随机消息                      |
| version   | String | 版本号                        |


###  回调验证

```
url：/api/v1/login/callback
method：Post
```

请求：
```source-json
{
	"action": "login",
	"version": "v1.0.0",
	"id": "10ba038e-48da-487b-96e8-8d3b99b6d18a",
	"params": {
		"type": "ontid",
		"user": "did:ont:AGWYQHd4bzyhrbpeYCMsxXYQcJo95VtR5q",
		"domain": "test.ont.io",
		"message": "helloworld",
		"publickey": "0205c8fff4b1d21f4b2ec3b48cf88004e38402933d7e914b2a0eda0de15e73ba61",
		"signature": "01abd7ea9d79c857cd838cabbbaad3efb44a6fc4f5a5ef52ea8461d6c055b8a7cf324d1a58962988709705cefe40df5b26e88af3ca387ec5036ec7f5e6640a1754"
	}
}
```
| Field Name | Type | Description |
|---|---|---|
| action    | String | 动作标志                      |
| version   | String | 版本号                        |
| id        | String | 记录的id                      |
| params    | Object | 回调验证参数                  |
| type      | String | 类型                          |
| user      | String | 用户ontid                     |
| domain    | String | 用户域名                      |
| message   | String | 验签消息                      |
| publickey | String | 公钥                          |
| signature | String | 签名数据                      |

响应：

```source-json
{
    "result": true,
    "action": "login",
    "id": "10ba038e-48da-487b-96e8-8d3b99b6d18a",
    "error": 0,
    "desc": "SUCCESS"
}
```
| Field Name | Type | Description |
| :-- | :-- | :-- |
| action    | String | 动作标志                      |
| error     | int    | 错误码                        |
| desc      | String | 成功为SUCCESS，失败为错误描述 |
| result    | String | 成功返回true，失败返回""      |
| version   | String | 版本号                        |


###  查询登录是否成功

```
url：/api/v1/login/result/{id}
method：Get
```

请求：
| Field Name | Type | Description |
|---|---|---|
|id          |String|记录的id     |

响应：

```source-json
{
    "action": "loginResult",
    "code": 0,
    "msg": "SUCCESS",
    "result": {
        "result": "1",
        "ons": "test.ont.io",
        "ontid": "did:ont:AGWYQHd4bzyhrbpeYCMsxXYQcJo95VtR5q"
    },
    "version": "v1"
}
```
| Field Name | Type | Description |
| :-- | :-- | :-- |
| action    | String | 动作标志                      |
| code      | int    | 错误码                        |
| msg       | String | 成功为SUCCESS，失败为错误描述 |
| result    | String | 成功返回"1"，失败返回"0"      |
| ons       | String | 用户域名                      |
| ontid     | String | 用户ontid                     |
| version   | String | 版本号                        |


## 调用合约接口

###  调用合约

```
url：/api/v1/invoke
method：Get
```

响应：

```source-json
{
    "action": "invokeContract",
    "code": 0,
    "msg": "SUCCESS",
    "result": {
        "callback": "http://192.168.3.121:7878/api/v1/invoke/callback",
        "id": "3a0f9e84-ab2f-4346-8631-1dc9fda279c0",
        "qrcodeUrl": "http://192.168.3.121:7878/api/v1/invoke/qrcode/3a0f9e84-ab2f-4346-8631-1dc9fda279c0"
    },
    "version": "v1"
}
```
| Field Name | Type | Description |
| :-- | :-- | :-- |
| action    | String | 动作标志                      |
| code      | int    | 错误码                        |
| msg       | String | 成功为SUCCESS，失败为错误描述 |
| result    | String | 成功返回记录信息，失败返回""  |
| callback  | String | 回调url地址                   |
| id        | String | 记录的id                      |
| qrcodeUrl | String | 获取交易参数的地址            |
| version   | String | 版本号                        |


###  获取交易参数

```
url：/api/v1/invoke/qrcode/{id}
method：Get
```

请求：
| Field Name | Type | Description |
|---|---|---|
|id          |String|记录的id     |

响应：

```source-json
{
    "action": "invoke",
    "id": "3a0f9e84-ab2f-4346-8631-1dc9fda279c0",
    "params": {
        "invokeConfig": {
            "gasLimit": 20000,
            "contractHash": "8b344a43204e60750e7ccc8c1b708a67f88f2c43",
            "functions": [
                {
                    "args": [
                        {
                            "name": "arg0-str",
                            "value": "String:%domain"
                        },
                        {
                            "name": "arg1-str",
                            "value": "Address:%address"
                        },
                        {
                            "name": "arg2-str",
                            "value": "Address:AcdBfqe7SG8xn4wfGrtUbbBDxw2x1e8UKm"
                        },
                        {
                            "name": "arg3-int",
                            "value": 100000000
                        }
                    ],
                    "operation": "transferOng"
                }
            ],
            "payer": "%address",
            "gasPrice": 500
        }
    },
    "version": "v1.0.0"
}
```
| Field Name | Type | Description |
| :-- | :-- | :-- |
| action    | String | 动作标志                      |
| id        | String | 记录的id                      |
| params    | Object | 交易参数                      |
| version   | String | 版本号                        |


###  发送交易hash

```
url：/api/v1/invoke/callback
method：Post
```

请求：
```source-json
{
  "action": "invoke",
  "id": "10ba038e-48da-487b-96e8-8d3b99b6d18a",
  "error": 0,
  "desc": "SUCCESS",
  "result": "tx hash"
}
```
| Field Name | Type | Description |
|---|---|---|
| action    | String | 动作标志                      |
| id        | String | 记录的id                      |
| error     | int    | 错误码                        |
| desc      | String | 成功为SUCCESS，失败为错误描述 |
| result    | String | 交易hash                      |


###  查询交易结果

```
url：/api/v1/invoke/result/{id}
method：Get
```

请求：
| Field Name | Type | Description |
|---|---|---|
|id          |String|记录的id     |

响应：

```source-json
{
    "action": "getResult",
    "code": 0,
    "msg": "SUCCESS",
    "result": "1",
    "version": "v1"
}
```
| Field Name | Type | Description |
| :-- | :-- | :-- |
| action    | String | 动作标志                      |
| code      | int    | 错误码                        |
| msg       | String | 成功为SUCCESS，失败为错误描述 |
| result    | String | 成功返回"1"，失败返回"0"      |
| version   | String | 版本号                        |