# exam接口

### 创建试卷

- URL /api/exam/create

- Method: POST

- 参数

  ```
  RequestParam: name,totalTime,mode,userId,label
  ```

- 返回：

  ```json
  {
    "code":200,
    "data":{
      "id":1,
      "name":"试卷1",
      "totalTime":60,
      "mode":1,
      "costTime":-1,
      "totalScore":-1,
      "grade": -1,
      "userId": 1,
      "alive": 1
    }
  }
  ```

### 组卷

* URL /api/exam/generate

* Method: POST

* 参数：

  ```json
  RequestParam: examId,questionId(可选，如果mode为1返回所有题目，如果mode为2，根据questionId返回所请求题目)
  ```

* 返回：

  ```json
  {
   "code": 200,
   "data": {
     "list": [
       {
         "id": 7,
         "description": "下列哪一个是合法的标识符：",
         "solution": "d",
         "type": "选择题",
         "label": "java",
         "tags": [
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
         ],
         "option": {
           "A": "12class",
           "B": "+viod",
           "C": "-5",
           "D": "_black"
         }
       }
       ...
     ]
   }
  }
  ```

