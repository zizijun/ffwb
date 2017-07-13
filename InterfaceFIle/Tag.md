#Tag API

### 获取所有的tag
* URL /api/tags
* Method: GET
* 参数:
```
file:excel
```
* 返回:
```
{
  "code": 200,
  "data": [
    {
      "id": 1,
      "content": "基本数据类型",
      "alive": 1
    },
    {
      "id": 2,
      "content": "引用数据类型",
      "alive": 1
    }
  ]
}
```

### 新增tag
* URL /tag/add
* Method: POST
* 参数:
```
{
    "content":"引用数据类型"
}
```
* 返回:
```
{
  "code": 200,
  "data": "添加成功"
}
```