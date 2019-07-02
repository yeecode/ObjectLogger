<div align="left">
<img src="./pic/ObjectLogger.png" height="80px" alt="ObjectLogger" >
</div>

# ObjectLogger
![language](https://img.shields.io/badge/language-java-green.svg)
![version](https://img.shields.io/badge/mvn-1.0.0-blue.svg?style=flat)
[![codebeat badge](https://codebeat.co/badges/94beca78-0817-4a27-9544-326afe35339f)](https://codebeat.co/projects/github-com-yeecode-objectlogger-master)
![license](https://img.shields.io/badge/license-Apache-brightgreen.svg)

The powerful and easy-to-use object log system, supports writing and querying of object attribute changes.

---

[中文说明](./README_CN.md)

---

# 1 Introduction

ObjectLogger is powerful and easy-to-use object log system, which supports writing and querying of object attribute changes.

It can be used in many scenarios, such as user operation log record, object attribute change record and so on.

![demo](./pic/web.gif)

The system has the following characteristics:

- It supports logging and query, and developers only need to redevelop the web page before using.
- It is not coupled with business systems.Pluggable use, without affecting the main business process.
- It can be used by multiple business systems at the same time without affecting each other.
- It can be started directly using the jar package; the business system is supported by the official Maven plug-in.
- It can automatically parse the attribute changes of objects and support the comparison of rich text.
- It supports extension of more object attribute types.

# 2 Quick Start

## 2.1 Create Data Tables

Use `/server/database/init_data_table.sql` to init two data tables.

## 2.2 Start Server

Download the new target jar file from `/server/target/ObjectLogger-*.jar`. 

Start the jar with the following statement:

```
java -jar ObjectLogger-*.jar --spring.datasource.driver-class-name={db_driver} --spring.datasource.url=jdbc:{db}://{db_address}/{db_name} --spring.datasource.username={db_username} --spring.datasource.password={db_password}
```

The above configuration items are described below:

- `db_driver`:Database driver. `com.mysql.jdbc.Driver` for MySQL database; `com.microsoft.sqlserver.jdbc.SQLServerDriver` for SqlServer database. 
- `db`:DataBase driver. `mysql`  for MySQL database ;`sqlserver`  for SqlServer database. 
- `db_address`:Database address. If the database is native, `127.0.0.1`. 
- `db_name`:Database name.. 
- `db_username`:User name used to log in to the database.
- `db_password`:Password used to log in to the database.

After starting the jar package, the default service address of the system is:

```
http://127.0.0.1:8080/ObjectLogger/
```

Visit the above address to see the following welcome interface:

![welcome interface](./pic/100.jpg)

The ObjectLogger system has been built.

# 3 Access Service System

This section explains how to configure the business system to record object changes in the business system into ObjectLogger.

## 3.1 Add Dependency

Add dependency package in POM file：

```
<dependency>
    <groupId>com.github.yeecode.objectLogger</groupId>
    <artifactId>ObjectLoggerClient</artifactId>
    <version>{last_version}</version>
</dependency>
```

## 3.2 Scan Beans in ObjectLoggerClient

### 3.2.1 SpringBoot

Add `@ComponentScan` and add ``com.github.yeecode.objectLoggerClient` in `basePackages`：

```
@SpringBootApplication
@ComponentScan(basePackages={"{your_beans_root}","com.github.yeecode.objectLogger"})
public class MyBootAppApplication {
public static void main(String[] args) {
    // Eliminate other code
  }
}
```
### 3.2.2 Spring

Add the following code to `applicationContext.xml` file:

```
<context:component-scan base-package="com.github.yeecode.objectLoggerClient">
</context:component-scan>
```

## 3.3 Configuration

Add the following code to `application.properties`:

```
object.logger.add.log.api=http://{ObjectLogger_address}/ObjectLogger/log/add
object.logger.appName={your_app_name}
object.logger.autoLog=true
```

- `ObjectLogger_address`: The deployment address of the ObjectLogger in the previous step, such as: `127.0.0.1:8080`
- `your_app_name`:The application name of the current business system. In order to differentiate log sources and support multiple business systems at the same time
- `object.logger.autoLog`:Whether to automatically record all attributes of an object

At this point, the configuration of the business system is completed.

# 4 Query Logs


The logs recorded in the system can be queried by `ObjectLogger/log/query', and the logs can be filtered by passing in parameters.

![demo](./pic/api.gif)


# 5 Insert Logs

The business system introduces `LogClient` in any class that requires logging:

```
@Autowired
private LogClient logClient;
```

## 5.1 Simple Use

Just put the zero, one or more attributes of the object into `actionItemModelList` and call `sendLogForItems` method. For example, a business application calls:

```
logClient.sendLogForItems("TaskModel",5,"actor name","addTask","add Task","via web page","some comments",null);
```

Query form ObjectLogger：
```
http://{your_ObjectLogger_address}/ObjectLogger/log/query?appName=myBootApp&objectName=TaskModel&objectId=5
```

Results：
```
{
  "respMsg": "SUCCESS",
  "respData": [
    {
      "id": 16,
      "appName": "myBootApp",
      "objectName": "TaskModel",
      "objectId": 5,
      "actor": "actor name",
      "action": "addTask",
      "actionName": "add Task",
      "extraWords": "via web page",
      "comment": "some comments",
      "actionTime": "2019-04-10T10:56:15.000+0000",
      "actionItemModelList": []
    }
  ],
  "respCode": "1000"
}
```

## 5.2 Automatic Recording of Object Attributes

This function can automatically complete the comparison between old and new objects, and insert multiple attribute changes into the log system together. When used, ensure that the old and new objects belong to the same class.

For example:

```
TaskModel oldTaskModel = new TaskModel();
oldTaskModel.setId(9);
oldTaskModel.setTaskName("oldName");
oldTaskModel.setUserId(3);
oldTaskModel.setDescription("\t<p>the first line</p>\n" +
        "\t<p>the second line</p>\n" +
        "\t<p>the 3th line</p>");

TaskModel newTaskModel = new TaskModel();
newTaskModel.setId(9);
newTaskModel.setTaskName("newName");
newTaskModel.setUserId(5);
newTaskModel.setDescription("\t<p>the first line</p>\n" +
        "\t<p>the second line</p>\n" +
        "\t<p>the last line</p>");

logClient.sendLogForObject(9,"actor name","editTask","edit Task","via app",
"some comments",oldTaskModel,newTaskModel);
```

Query form ObjectLogger：

```
http://{your_ObjectLogger_address}/ObjectLogger/log/query?appName=myBootApp&objectName=TaskModel&objectId=9
```

Results：

```
{
  "respMsg": "SUCCESS",
  "respData": [
    {
      "id": 15,
      "appName": "myBootApp",
      "objectName": "TaskModel",
      "objectId": 9,
      "actor": "actor name",
      "action": "editTask",
      "actionName": "edit Task",
      "extraWords": "via app",
      "comment": "some comments",
      "actionTime": "2019-04-10T10:56:17.000+0000",
      "actionItemModelList": [
        {
          "id": 18,
          "actionId": 15,
          "attributeType": "NORMAL",
          "attribute": "taskName",
          "attributeName": "TASK",
          "oldValue": "oldName",
          "newValue": "newName",
          "diffValue": null
        },
        {
          "id": 19,
          "actionId": 15,
          "attributeType": "USERID",
          "attribute": "userId",
          "attributeName": "USER",
          "oldValue": "USER:3",
          "newValue": "USER:5",
          "diffValue": "diffValue"
        },
        {
          "id": 20,
          "actionId": 15,
          "attributeType": "TEXT",
          "attribute": "description",
          "attributeName": "DESCRIPTION",
          "oldValue": "\"\\t<p>the first line</p>\\n\\t<p>the second line</p>\\n\\t<p>the 3th line</p>\"",
          "newValue": "\"\\t<p>the first line</p>\\n\\t<p>the second line</p>\\n\\t<p>the last line</p>\"",
          "diffValue": "Line 6:<br/>&nbsp;&nbsp;&nbsp; -： <del> the 3th line </del> <br/>&nbsp;&nbsp; +： <u> the last line </u> <br/>"
        }
      ]
    }
  ],
  "respCode": "1000"
}
```

# 6 Object Attribute Filtering

Some object attributes do not need to be logged, such as `updateTime', `hashCode', etc. ObjectLogger supports filtering attributes of objects, tracking only attributes that we are interested in.

And for each attribute, we can change the way it is recorded in the ObjectLogger system, such as changing the name.

To enable this function, first change the `object. logger. autoLog` in the configuration to `false'.

```
object.logger.autoLog=false
```

Then, the `@LogTag` annotation should be added to the attributes that need to be logged for change. Attributes without the annotation will be automatically skipped when logging.

For example, if the annotation configuration is as follows, `id` field changes will be ignored.

```
private Integer id;

@LogTag(name = "TaskName")
private String taskName;

@LogTag(name = "UserId", extendedType = "userIdType")
private int userId;

@LogTag(name = "Description", builtinType = BuiltinTypeHandler.TEXT)
private String description;
```

- name:The attribute name to display.
- builtinType：Built-in type of ObjectLogger, which form the BuiltinTypeHandler enum. The default value is `BuiltinTypeHandler.NORMAL`.
    - BuiltinTypeHandler.NORMAL：Record the new and old values.
    - BuiltinTypeHandler.TEXT: Line-by-line comparison of rich text differences.
- extendedType：Extend attribute types. Users can extend the processing of certain fields.

# 7 Extended Processing Attribute

In many cases, users want to be able to decide how to handle certain object attributes independently. For example, users may want to convert the `userId` attribute of the `Task` object into a name and store it in the log system, thus completely decoupling the log system from `userId'.

ObjectLogger fully supports this scenario, allowing users to decide how to log certain attributes independently. To achieve this function, first assign a string value to the `extendedType` attribute of `@LogTag` that needs to be extended. For example:

```
@LogTag(name = "UserId", extendedType = "userIdType")
    private int userId;
```

And new a Bean implements BaseExtendedTypeHandler in business system:

```
@Service
public class ExtendedTypeHandler implements BaseExtendedTypeHandler {
    @Override
    public BaseActionItemModel handleAttributeChange(String attributeName, String logTagName, Object oldValue, Object newValue) {
        return null;
    }
}
```

When ObjectLogger processes this property, it passes information about the property into the `handleAttributeChange'method of the extended bean. The four parameters introduced are explained as follows:

- `extendedType`：Extended Type.In this example, `userIdType`. 
- `attributeName`：Attribute Name. In this example,`userId`. 
- `logTagName`：Name value of `@LogTag`， may be null. In this example,`UserId`. 
- `oldValue`：Old value of the attribute. 
- `newValue`：New value of the attribute. 

For example, we can deal with the `userIdType` attribute in the following way:

```
public BaseActionItemModel handleAttributeChange(String extendedType, String attributeName, String logTagName, Object oldValue, Object newValue) {
    BaseActionItemModel baseActionItemModel = new BaseActionItemModel();
    if (extendedType.equals("userIdType")) {
        baseActionItemModel.setOldValue("USER_" + oldValue);
        baseActionItemModel.setNewValue("USER_" + newValue);
        baseActionItemModel.setDiffValue(oldValue + "->" + newValue);
    }
    return baseActionItemModel;
}
```

## 8 ReleaseNotes

1.0.0：Initialize the system
2.0.0：Optimized system structure
2.0.1：Made the system support multi-threading
2.2.0：Added automatic recording function of global object attribute change
TODO：Added automatic recording for inherited attributes
TODO：Added object deep copy function













