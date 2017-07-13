# Question

### 上传试题 试题存在excel之中
* URL /api/question/upload?managerId=1
* Method: POST
* 参数:
```
file:excel
```
* 返回:
```
{
  "code": 200,
  "data": "成功上传6题目"
}
```

### 获取所有的试题(分页操作)
 * URL /api/questions/all
 * Method: GET
 * 参数:
 ```
 RequestParam：pageIndex，pageSize，sortField，sortOrder 可以不写有默认值
 ```
 * 返回:
 ```
 {
  "code": 200,
  "data": {
    "totalCount": 6,
    "totalPage": 1,
    "list": [
      {
              "id": 7,
              "description": "下列哪一个是合法的标识符：",
              "solution": "d",
              "type": "选择题",
              "label": "java",
              "tagIds": [
                1,
                2
              ],
              "optionJson": {
                "A": "12class",
                "B": "+viod",
                "C": "-5",
                "D": "_black"
              }
            }
      ...
    ],
    "pageSize": 10,
    "pageIndex": 0
  }
}
```

### 根据条件搜索问题
* URL /api/questions/search
* Method: GET
* 参数:
```
RequestParam：label,type //搜索条件
RequestParam：pageIndex，pageSize，sortField，sortOrder 可以不写有默认值
```
* 返回:
```
{
  "code": 200,
  "data": {
    "totalCount": 6,
    "totalPage": 1,
    "list": [
      {
        "id": 1,
        "description": "下列哪一个是合法的标识符：",
        "solution": "d",
        "type": "选择题",
        "label": null,
        "tagIds": null,
        "optionJson": {
          "A": "12class",
          "B": "+viod",
          "C": "-5",
          "D": "_black"
        }
      }
      ...
    ],
    "pageSize": 10,
    "pageIndex": 0
  }
}
```

### 问题更新
* URL /api/questions/update
* Method: PUT
* 参数:
```
[
    {
        "id": 1,
        "description": "下列哪一个是合法的标识符：",
        "solution": "d",
        "type": "选择题",
        "label": "java",
        "tagIds": [1,2],
        "optionJson": {
          "A": "12class",
          "B": "+viod",
          "C": "-5",
          "D": "_black"
        }
      }
    ]
```
* 返回:
```
{
  "code": 200,
  "data": "成功更新1个问题,失败0次"
}
```

### 问题新增
* URL /api/questions/add
* Method: PUT
* 参数:
```
RequestParam:managerId
[
    {
        "description": "下列哪一个是合法的标识符：",
        "solution": "d",
        "type": "选择题",
        "label": "java",
        "tagIds": [1,2],
        "optionJson": {
          "A": "12class",
          "B": "+viod",
          "C": "-5",
          "D": "_black"
        }
      }
    ]
```
* 返回:
```
{
  "code": 200,
  "data": "成功新增1个问题,失败0次"
}
```

### 问题删除
* URL /api/questions/delete
* Method: PUT
* 参数:
```
[
    {
        "id": 1,
        "description": "下列哪一个是合法的标识符：",
        "solution": "d",
        "type": "选择题",
        "label": "java",
        "tagIds": [1,2],
        "optionJson": {
          "A": "12class",
          "B": "+viod",
          "C": "-5",
          "D": "_black"
        }
      }
    ]
```
* 返回:
```
{
  "code": 200,
  "data": "成功删除1个问题,失败0次"
}
```