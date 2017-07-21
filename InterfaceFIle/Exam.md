# exam接口

### 创建试卷

- URL /api/exam/create

- Method: POST

- 参数 

  ```json
  {
  	"name" : "exam3",
	""totalScore" : 20,
  	"user" : {
  		"id":1
  	}
  }
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
      "startTime":"",
      "endTime":"",
      "alive": 1
    }
  }
  ```

### 组卷

* URL /api/exam/generate

* Method: POST

* 参数：

  ```json
  {
    "id" : 3
  }

  ```

* 返回：

  ```json
  {
   "code": 200,
   "data": {
     "id":1,
     "name":"试卷1",
     "totalTime":60,
     "mode":1,
     "costTime":-1,
     "totalScore":-1,
     "grade": -1,
     "userId": 1,
     "startTime":"",
     "endTime":"",
     "alive": 1,
     "questions": [
       {
         "id": 7,   // 这条属性在本接口中是无效的
         "description": "下列哪一个是合法的标识符：",
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
         "optionJson": {
           "A": "12class",
           "B": "+viod",
           "C": "-5",
           "D": "_black"
         },
         "answerId":1    // 记录answer的id，用于添加解答
       }
       ...
     ]
   }
  }
  ```




### 获取所有考试

* URL /api/exam/all

* Method: GET

* 参数

  ```
  RequestParam: userId
  ```

* 返回：

  ```json
  {
    "code":200,
    "data":[
        {
          "id":1,
          "name":"试卷1",
          "totalTime":60,
          "mode":1,
          "costTime":-1,
          "totalScore":-1,
          "grade": -1,
          "userId": 1,
  	      "startTime":"2017-7-16 10:00:00",
  	      "endTime":"2017-7-16 10:28:58",
          "alive": 1
        },
        ...
      ]
  }
  ```

### 答题

* URL /api/anwser/add

* Method: POST

* 参数

  ``` json
  [
    {
        "id":7,
        "answer":"A,B"
    },
    {
        "id":8,
        "answer":"A"
    }
    ...
    ]
  ```

* 返回

  ```json
  {
    "code":200,
    "data":{
        "id":1,
        "userId":1,
        "examId":1,
        "questionId":7,
        "answer":"A,B",
        "isRight":false,
        "alive":1
      }
  }
  ```

### 结束考试

* URL /api/exam/finish

* Method: POST

* 参数

  ```json
  {
    "id":1,
    "name":"试卷1",
    "costTime":55,
    "userId":1
  }
  ```

* 返回

  ```json
  {
    "code":200,
    "data":"success"
  }
  ```

### 查看考试结果

* URL: /api/exam/result

* Method: GET

* 参数

  ```j
  RequestParam: examId,userId
  ```

* 返回

  ```json
  {
    "code":200,
    "data":{
      "id":1,
      "name":"试卷1",
      "totalTime":60,
      "mode":1,
      "costTime":55,
      "totalScore":100,
      "grade": 90,
      "userId": 1,
      "alive": 1,
      "resultList":[
        {
          "id":1,
          "answer":"A,B",
          "isRight":true,
          "alive":1
        },
        ...
      ]
    }
  }
  ```

### 查看考题解析

* URL /api/question/analysis

* Method: GET

* 参数

  ```
  RequestParam:answerId
  ```

* 返回

  ```
  {
    "code":200,
    "data":{
      "answer":"这题不会",
      [
      {
      "id":1,
      "question":{}//具体可以不用管
      "user":{}//具体可以忽略
      "content":"jvm是java虚拟机",
      "createdTime":"2017-08-08",
      "alive:1
      }
      {
      "id":2,
      "question":{}//具体可以不用管
      "user":{}//具体可以忽略
      "content":"jvm是java虚拟机",
      "createdTime":"2017-08-08",
      "alive:1
      }
      ]
    }
  }
  ```



    ​