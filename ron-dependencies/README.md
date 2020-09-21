# ron-dependencies

统一 Maven 依赖管理。

## 使用说明

### 安装该依赖

#### 1. 本地使用

导入项目源码，如有需要，执行 `mvn clean install`，构建项目并安装到本地仓库。

> 请勿在本地修改相关 `<version>`

#### 2. 私有仓库使用

后续将直接发布在私有仓库，由专门的人维护。

```
# 发布到私有仓库
mvn clean deploy
```

### 引用依赖

新建项目代码时，在 `pom.xml` 中添加如下依赖：

```
<parent>
    <groupId>io.ron</groupId>
    <artifactId>ron-dependencies</artifactId>
    <version>1.0-SNAPSHOT</version>
    <relativePath/> <!-- lookup parent from repository -->
</parent>
```

对于该基础依赖中已经包含的 jar，可以直接通过 `<groupId>` 与 `<artifactId>` 进行定位，版本已统一管理，不需要加 `<version>`。

### 查看具体组件的依赖版本

```
mvn help:effective-pom -Doutput=effective-pom
```