<div align="left">
<img src="./pic/ObjectLogger.png" height="80px" alt="ObjectLogger" >
</div>

# ObjectLogger
![language](https://img.shields.io/badge/language-java-green.svg)
![version](https://img.shields.io/badge/mvn-1.0.0-blue.svg?style=flat)
[![codebeat badge](https://codebeat.co/badges/94beca78-0817-4a27-9544-326afe35339f)](https://codebeat.co/projects/github-com-yeecode-objectlogger-master)
![license](https://img.shields.io/badge/license-Apache-brightgreen.svg)

强大且易用的Java对象日志记录系统，支持对象属性变动的记录与查询。

---

[English Introduction](./README.md)

---

# 1 系统简介

ObjectLogger是一套强大且易用的对象日志记录系统。它能够将任意对象的变动日志记录下来，并支持查询。可以应用在用户操作日志记录、对象属性变更记录等诸多场景中。

![实例图片](./pic/web.gif)

该系统具有以下特点：

- 一站整合：系统支持日志的记录与查询，开发者只需再开发前端界面即可使用。
- 完全独立：与业务系统无耦合，可插拔使用，不影响主业务流程。
- 应用共享：系统可以同时供多个业务系统使用，互不影响。
- 简单易用：服务端直接jar包启动；业务系统有官方Maven插件支持。
- 自动解析：能自动解析对象的属性变化，并支持富文本的前后对比。
- 便于扩展：支持自定义对象变动说明、属性变动说明。支持更多对象属性类型的扩展。

# 2 快速上手

## 2.1 创建数据库

使用该项目的`/server/database/init_data_table.sql`文件初始化两个数据表。

## 2.2 启动Server

下载该项目下最新的Server服务jar包，地址为`/server/target/ObjectLogger-*.jar`。

启动下载的jar包。

```
java -jar ObjectLogger-*.jar --spring.datasource.driver-class-name={db_driver} --spring.datasource.url=jdbc:{db}://{db_address}/{db_name} --spring.datasource.username={db_username} --spring.datasource.password={db_password}
```

上述命令中的用户配置项说明如下：

- `db_driver`:数据库驱动。如果使用MySql数据库则为`com.mysql.jdbc.Driver`;如果使用SqlServer数据库则为`com.microsoft.sqlserver.jdbc.SQLServerDriver`。
- `db`:数据库类型。如果使用MySql数据库则为`mysql`;如果使用SqlServer数据库则为`sqlserver`。
- `db_address`:数据库连接地址。如果数据库在本机则为`127.0.0.1`。
- `db_name`:数据库名，该数据库中需包含上一步初始化的两个数据表。
- `db_username`:数据库登录用户名。
- `db_password`:数据库登录密码。

启动jar包后，系统默认的服务地址为：

```
http://127.0.0.1:8080/ObjectLogger/
```

访问上述地址可以看到下面的欢迎界面：

![系统首页](./pic/100.jpg)

至此，ObjectLogger系统已经搭建结束，可以接受业务系统的日志写入和查询操作。

# 3 业务系统接入

该部分讲解如何配置业务系统来将业务系统中的对象变化记录到ObjectLogger中。

## 3.1 引入依赖包

在pom中增加下面的依赖：

```
<dependency>
    <groupId>com.github.yeecode.objectLogger</groupId>
    <artifactId>ObjectLoggerClient</artifactId>
    <version>{最新版本}</version>
</dependency>
```

## 3.2 添加对ObjectLoggerClient中bean的自动注入

### 3.2.1 对于SpringBoot应用

在SpringBoot的启动类前添加`@ComponentScan`注解，并在`basePackages`中增加ObjectLoggerClient的包地址：`com.github.yeecode.objectLoggerClient`，如：

```
@SpringBootApplication
@ComponentScan(basePackages={"{your_beans_root}","com.github.yeecode.objectLogger"})
public class MyBootAppApplication {
public static void main(String[] args) {
    // 省略其他代码
  }
}
```
### 3.2.2 对于Spring应用

在`applicationContext.xml`增加对ObjectLoggerClient包地址的扫描：

```
<context:component-scan base-package="com.github.yeecode.objectLoggerClient">
</context:component-scan>
```

## 3.3 完成配置

在`application.properties`中增加:

```
object.logger.add.log.api=http://{ObjectLogger_address}/ObjectLogger/log/add
object.logger.appName={your_app_name}
object.logger.autoLog=true
```

- `ObjectLogger_address`:属性指向上一步的ObjectLogger的部署地址，例如：`127.0.0.1:8080`
- `your_app_name`:指当前业务系统的应用名。以便于区分日志来源，实现同时支持多个业务系统
- `object.logger.autoLog`:是否对对象的所有属性进行变更日志记录

至此，业务系统的配置完成。已经实现了和ObjectLogger的Server端的对接。

# 4 日志查询

系统运行后，可以通过`/ObjectLogger/log/query`查询系统中记录的日志，并通过传入参数对日志进行过滤。

![实例图片](./pic/api.gif)

通过这里，我们可以查询下一步中写入的日志。

# 5 日志写入

业务系统在任何需要进行日志记录的类中引入`LogClient`。例如：

```
@Autowired
private LogClient logClient;
```

## 5.1 简单使用

直接将对象的零个、一个、多个属性变化放入`actionItemModelList`中发出即可。`actionItemModelList`置为`null`则表示此次对象无需要记录的属性变动。例如，业务应用中调用：

```
logClient.sendLogForItems("TaskModel",5,"actor name","addTask","add Task","via web page","some comments",null);
```

在ObjectLogger中使用如下查询条件：
```
http://{your_ObjectLogger_address}/ObjectLogger/log/query?appName=myBootApp&objectName=TaskModel&objectId=5
```

查询到日志：
```
{
  "respMsg": "成功",
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

## 5.2 对象变动自动记录

该功能可以自动完成新老对象的对比，并根据对比结果，将多个属性变动一起写入日志系统中。使用时，要确保传入的新老对象属于同一个类。

例如，业务系统这样调用：

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

则我们可以使用下面查询条件：

```
http://{your_ObjectLogger_address}/ObjectLogger/log/query?appName=myBootApp&objectName=TaskModel&objectId=9
```

查询到如下结果：

```
{
  "respMsg": "成功",
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
          "diffValue": "第6行变化:<br/>&nbsp;&nbsp;&nbsp; -： <del> the 3th line </del> <br/>&nbsp;&nbsp; +： <u> the last line </u> <br/>"
        }
      ]
    }
  ],
  "respCode": "1000"
}
```

# 6 对象属性过滤

有些对象的属性的变动不需要进行日志记录，例如`updateTime`、`hashCode`等。ObjectLogger支持对对象的属性进行过滤，只追踪我们感兴趣的属性。

并且，对于每个属性我们可以更改其记录到ObjectLogger系统中的具体方式，例如修改命名等。

要想启用这个功能，首先将配置中的`object.logger.autoLog`改为`false`。

```
object.logger.autoLog=false
```

然后在需要进行变化日志记录的属性上增加`@LogTag`注解。凡是没有增加该注解的属性在日志记录时会被自动跳过。

例如，注解配置如下则`id`字段的变动将被忽略。

```
private Integer id;

@LogTag(name = "TaskName")
private String taskName;

@LogTag(name = "UserId", extendedType = "userIdType")
private int userId;

@LogTag(name = "Description", builtinType = BuiltinTypeHandler.TEXT)
private String description;
```

该注解属性介绍如下：

- name:必填，对应写入日志后的`attributeName`值。
- builtinType：ObjectLogger的内置类型，为BuiltinTypeHandler的值。默认为`BuiltinTypeHandler.NORMAL`。
    - BuiltinTypeHandler.NORMAL：记录属性的新值和旧值，对比值为null
    - BuiltinTypeHandler.TEXT: 用户富文本对比。记录属性值的新值和旧值，并将新旧值转化为纯文本后逐行对比差异，对比值中记录差异
- extendedType：扩展属性类型。使用ObjcetLogger时，用户可以扩展某些字段的处理方式。

# 7 属性处理扩展

很多情况下，用户希望能够自主决定某些对象属性的处理方式。例如，对于例子中`Task`对象的`userId`属性，用户可能想将其转化为姓名后存入日志系统，从而使得日志系统与`userId`完全解耦。

ObjectLogger完全支持这种情况，可以让用户自主决定某些属性的日志记录方式。要想实现这种功能，首先在需要进行扩展处理的属性上为`@LogTag`的`extendedType`属性赋予一个字符串值。例如：

```
@LogTag(name = "UserId", extendedType = "userIdType")
    private int userId;
```

然后在业务系统中声明一个Bean继承BaseExtendedTypeHandler，作为自由扩展的钩子。代码如下:

```
@Service
public class ExtendedTypeHandler implements BaseExtendedTypeHandler {
    @Override
    public BaseActionItemModel handleAttributeChange(String attributeName, String logTagName, Object oldValue, Object newValue) {
        return null;
    }
}
```

接下来，当ObjectLogger处理到该属性时，会将该属性的相关信息传入到扩展Bean的`handleAttributeChange`方法中，然后用户可以自行处理。传入的四个参数解释如下：

- `extendedType`：扩展类型值，即`@LogTag`注解的`extendedType`值。本示例中为`userIdType`。
- `attributeName`：属性名。本示例中为`userId`。
- `logTagName`：`@LogTag`注解的`name`值，可能为null。本示例中为`UserId`。
- `oldValue`：该属性的旧值。
- `newValue`：该属性的新值。

例如我们可以采用如下的方式处理`userIdType`属性：

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

- 1.0.0：实现基本功能
- 2.0.0：重新组织代码结构
- 2.0.1：日志写入支持多线程
- 2.2.0：增加全局对象属性变动自动记录功能
- TODO：增加对继承属性的自动记录功能
- TODO：增加对象深度镜像功能