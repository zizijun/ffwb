# Question

* URL /api/question/upload


* Method: POST

* param

  ```
  {

  “file":"xxx",

  "managerId":"1"

  }

  ```

  ​

* URL /api/questions/all

* Method: GET

* param

  ```
  {
    "pageIndex":1,
    "pageSize":10,
    "sortField":"id",
    "sortOrder":"ASC"
  }
  ```

* URL /api/questions/search

* Method: GET

* param

* ```
  {
    "label":"hah",
    "type":"选择题",
    "pageIndex":8,
    "pageSize":10,
    "sortField":"id",
    "sortOrder":"ASC"
  }
  ```

* URL /api/questions/label(或update、delete)

* Method: PUT

* param

* ```
  [
    {
      "id":5,
      "description":"c++继承机制",
      "solution":"hah",
      "type":"简答题",
      "label":["测试","c++"],
      "optionJson":null
    }
  ]
  ```

* URL /api/questions/add

* Method: POST

* param

* ```
  managerId:1 (这个是直接写在URL后面那种)

  [
    {
      "id":-1,
      "description":"c++继承机制",
      "solution":"hah",
      "type":"简答题",
      "label":["测试","c++"],
      "optionJson":null
    }
  ]
  ```

* ​