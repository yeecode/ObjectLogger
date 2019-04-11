[TOC]

# ObjectLogger

[English Introduction](./README.md)

简单易用的对象日志记录系统，支持对象属性变动的记录与查询。

# 1 系统应用

众多系统的运行中需要对各个对象的日志进行记录，这些日志内容包括但不限于：

- 对象零个、一个或者多个属性的变化
- 用户针对对象的操作
- 其他对象状态的转变

这些操作日志被记录下来后，还需要提供相应的查询接口。

该开源项目的目的便在于协助开发者完成对象日志的采集、存储、查询操作。

使用该项目记录后，能达到如下图所示效果：

![实例图片](./pic/page.jpg)

# 2 系统特点

- 该系统与业务系统完全独立，可插拔，不影响主业务流程
- 采用SpringBoot实现，可以独立jar包启动
- 日志的查询操作完全与业务系统解耦，不影响业务系统性能
- 支持多个系统共用
- 业务系统提供日志分析与写入jar包，日志写入简单
- 支持一次记录对象的零个、一个、多个属性变动
- 支持自定义对象变动说明、属性变动说明
- 支持富文本对象的前后对比
- 支持更多对象属性类型的扩展

# 3 系统部署

## 3.1 数据库部分部署

自备数据库。使用该项目的`/database/init_data_table.sql`文件初始化两个数据表。

## 3.2 ObjectLogger系统部署

1. 下载该git项目代码。
2. 根据你的数据库种类、地址、密码等配置`/src/main/resources/application.properties`文件中的下面部分：
```
spring.datasource.driver-class-name ={db_driver}
spring.datasource.url =jdbc:mysql://{your_mysql_address}/{your_database_name}
spring.datasource.username ={your_database_username}
spring.datasource.password ={your_database_password}
```
3. 使用maven打包该项目的jar包。
4. 获取`taeget`目录下的jar包，使用`java -jar ObjectLogger-1.0.1.jar`启动项目。项目默认端口`8080`。

## 3.3 业务系统配置

该部分主要讲解如何配置业务系统来实现将业务系统中的对象变化记录到ObjectLogger中。

### 3.3.1 业务系统添加maven依赖

```
<dependency>
    <groupId>com.github.yeecode.objectLogger</groupId>
    <artifactId>ObjectLoggerClient</artifactId>
    <version>1.0.4</version>
</dependency>
```

该依赖来源于项目[ObjectLoggerClient](https://github.com/yeecode/ObjectLoggerClient)。我们这里只需引用就可以。如果想了解详细原理可前去看看。

### 3.3.2 添加对ObjectLoggerClient中提供的bean的扫描注入。

若业务应用为SpringBoot应用，则在SpringBoot的启动类前添加，如：
```
@SpringBootApplication
@ComponentScan(basePackages={"{your_beans_root}","com.github.yeecode.objectLogger"})
public class MyBootAppApplication {

public static void main(String[] args) {
    SpringApplication.run(MyBootAppApplication.class, args);
}
}
```

若业务应用为Spring应用，则在`applicationContext.xml`增加扫描：
```
<context:component-scan base-package="com.github.yeecode.objectLogger">
</context:component-scan>
```

### 3.3.3 添加对ObjectLoggerClient的配置

在`application.properties`中增加:

```
object.logger.add.log.api=http://{your_ObjectLogger_address}/ObjectLogger/log/add
object.logger.appName={your_app_name}
```

这两个配置项分别是指明了ObjectLogger的部署地址以便于将请求发送到那里、表明了业务应用的应用名以便于区分日志来源。

### 3.3.4 声明一个扩展LocalTypeHandler的类，作为自由扩展的钩子

```
@Service
public class LocalTypeHandlerBean implements LocalTypeHandler {

    @Override
    public ActionItemModel handleLocalType(String subLocalType, String oldValue, String newValue) {
        return null;
    }
}
```

至此，整个系统部署结束。

# 4 系统使用

首先，业务系统在任何需要进行日志记录的类中引入`LogClient`。例如：
```
@Autowired
private LogClient logClient;
```

## 4.1 简单使用

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

## 4.2 对象变动自动记录

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

对象属性的自动对比需要`@LogDescription`注解的支持。对于对象中未加注解的属性会自动跳过对比。

例如，本次示例中的注解配置如下：

```
@LogDescription(name = "TASK")
private String taskName;

@LogDescription(name = "USER",type = AttributeTypeEnum.LOCALTYPE,localType = LocalTypeHandlerBean.USERID)
private Integer userId;

@LogDescription(name = "DESCRIPTION",type = AttributeTypeEnum.TEXT)
private String description;
```

该注解属性介绍如下：

- name:必填，对应写入日志后的`attributeName`值。
- type：为AttributeTypeEnum的值，默认为`AttributeTypeEnum.NORMAL`。
    - AttributeTypeEnum.NORMAL：记录属性的新值和旧值，对比值为null
    - AttributeTypeEnum.TEXT: 用户富文本对比。记录属性值的新值和旧值，并将新旧值转化为纯文本后逐行对比差异，对比值中记录差异
    - AttributeTypeEnum.LOCALTYPE：表明该字段由用户自定义处理，此时localType生效。
- localType：当`type = AttributeTypeEnum.LOCALTYPE`时生效。此时，该字段如果新旧值不一样，则会传递到LocalTypeHandler的实现类中。例如，示例中`userId`字段交由实现类处理，代码如下：

```
@Service
public class LocalTypeHandlerBean implements LocalTypeHandler {

    public static final String USERID = "USERID";

    @Override
    public ActionItemModel handleLocalType(String subLocalType, String oldValue, String newValue) {
        ActionItemModel logActionItemModel = new ActionItemModel();

        if (subLocalType.equals(USERID)) {
            logActionItemModel.setNewValue("USER:" + newValue);
            logActionItemModel.setOldValue("USER:" + oldValue);
            logActionItemModel.setDiffValue("diffValue");
        } else {
            logActionItemModel = null;
        }
        return logActionItemModel;
    }
}
```




