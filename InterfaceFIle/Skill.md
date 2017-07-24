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
      "average": 1.4259705560474887
    },
    {
      "name": "j2se",
      "value": 1.8144314222701403,
      "number": 192,
      "correct": 0.6814209279687916,
      "difficult": 2.391922036725086,
      "average": 1.333610563662488
    },
    {
      "name": "html",
      "value": 0.8148027120180783,
      "number": 152,
      "correct": 0.8318366887282798,
      "difficult": 1.2982472863374328,
      "average": 1.4420072151657155
    },
    {
      "name": "css",
      "value": 1.6357266466803553,
      "number": 104,
      "correct": 0.5395701264023228,
      "difficult": 1.714689576882905,
      "average": 1.5135656382138256
    },
    {
      "name": "js",
      "value": 1.4098214914187552,
      "number": 263,
      "correct": 0.33510513177524215,
      "difficult": 1.0450166915355557,
      "average": 1.4445601452107664
    }
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