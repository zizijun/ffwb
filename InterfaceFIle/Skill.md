# Skill

### 技能分析（大类）
* URL /api/skillmodel/{id}
* Method: GET
* 参数:
```
@PathVariable(value = "id")
```
* 返回:
```
{
  "code": 200,
  "data": [
    {
      "name": "j2ee",
      "value": 1.1247426978026978,
      "number": 789,
      "correct": 0.5079781968580704,
      "difficult": 1.178037709996547,
      "average": 1.4259705560474887,
      "sonTagName": [
              "开源框架",
              "设计模式",
              "线程相关",
              "网络"
            ]
    },
    .....
  ]
}
```

### 技能分析（小类）
* URL /api/skillmodel/total/{id}
* Method: GET
* 参数:
```
@PathVariable(value = "id")
```
* 返回:
```
{
  "code": 200,
  "data": [
    {
          "name": "JVM相关",
          "value": 0.6937788742286499,
          "number": 32,
          "correct": 0.8777164440013073,
          "difficult": 0.8285774464417077,
          "average": 1.5056168226198257
        },
        {
          "name": "java的运行",
          "value": 0.0970625851687168,
          "number": 83,
          "correct": 0.14674867154049498,
          "difficult": 0.1754175095603726,
          "average": 0.9905288052797303
        },
        。。。
  ]
}
```

### 随机生成技能
* URL /api/skillmodel/random
* Method: POST
* 参数:
```
无
```
* 返回:
```

```