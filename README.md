# XShop 小米商城电子购物平台

Java Web 课程设计项目，基于 JSP + Servlet + JavaBean + JDBC + DAO + MVC 架构。

## 项目结构

```
xshop-jsp/                   # Java 后端（Maven WAR 项目）
  ├── pom.xml
  ├── sql/xiaomishop.sql     # 数据库建表脚本
  └── src/main/
      ├── java/com/xshop/
      │   ├── dao/           # 数据访问层
      │   ├── model/         # JavaBean
      │   ├── service/       # 业务逻辑层
      │   ├── util/          # 工具类（DBUtil、MD5、Token）
      │   └── web/           # Servlet + Filter
      └── webapp/
          ├── admin/         # JSP 后台管理页面
          ├── css/           # 样式文件
          ├── images/        # 图片资源
          └── WEB-INF/web.xml

javaweb-xshop-master/        # Vue.js 商城前端
  └── javaweb-xshop-master/
      ├── src/
      │   ├── views/         # 页面组件
      │   ├── router/        # 路由配置
      │   └── util/          # API 工具
      └── vue.config.js
```

## 环境要求

- JDK 17+
- Maven 3
- Tomcat 9
- MySQL 8
- Node.js（可选，仅需修改前端时）

## 快速开始

### 1. 克隆

```bash
git clone https://github.com/0714cxy/java-xiaomishop.git
cd java-xiaomishop
```

### 2. 导入数据库

打开 MySQL，执行 SQL 脚本创建数据库和表：

```bash
mysql -u root -p < xshop-jsp/sql/xiaomishop.sql
```

### 3. 修改数据库配置（可选）

编辑 `xshop-jsp/src/main/java/com/xshop/util/DBUtil.java`，将数据库密码改为你自己的 MySQL 密码：

```java
dataSource.setPassword("你的密码");
```

### 4. 编译部署后端

```bash
cd xshop-jsp
mvn clean package
```

将生成的 `target/xshop.war` 复制到 Tomcat 的 `webapps` 目录下，启动 Tomcat。

### 5. 构建前端（可选）

如需修改前端代码，重新构建：

```bash
cd ../javaweb-xshop-master/javaweb-xshop-master
npm install
npm run build
```

将 `dist/` 下所有文件复制到 Tomcat 的 `webapps/xshop/` 目录（覆盖已有文件）。

> 注：WAR 包已包含编译后的前端文件，不修改前端代码可跳过此步。

### 6. 访问

- **商城首页**：`http://localhost:8080/xshop/`
- **后台管理**：`http://localhost:8080/xshop/admin/login`

## 默认账号

- 管理员：admin / 123
- 测试用户：test / 123456

## 技术栈

| 模块 | 技术 |
|------|------|
| 商城前端 | Vue.js + Element UI |
| 后台管理 | JSP + Layui |
| 控制层 | Servlet |
| 业务层 | JavaBean + Service |
| 数据层 | DAO + JDBC + Druid |
| 数据库 | MySQL 8 |
| 密码加密 | MD5 |
| 连接池 | Druid |
