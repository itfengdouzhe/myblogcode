### 1.项目架构及技术描述

------

#### 1.1.本项目用到的技术和框架

- 项目构建：Maven
- web框架：Springboot
- 数据库ORM：Mybatis
- 数据库连接池： HikariCP
- 分页插件：PageHelper
- 数据库：MySql
- 缓存：Redis
- 前端模板：Thymeleaf
- 文章展示：Editor.md

#### 1.2.本项目中的关键点

- 采用Springboot开发，数据库使用连接池加orm框架的模式，对于系统的关键业务使用Redis缓存，加快相应速度。
- 整体系统采用门户网站+后台管理+用户个人中心的方式搭建，门户网站展示博客内容以及博主介绍，后台管理用于编辑文章，查看反馈，管理评论留言。
- 使用华为云obs进行静态资源存储，以及CDN全站加速。

#### 1.3.开发环境

| 工具     | 名称                  |
| -------- | --------------------- |
| 开发工具 | IDEA                  |
| 语言     | JDK1.8、Html、css、js |
| 数据库   | MySQL5.6              |
| ORM      | Mybatis               |
| 安全框架 | SpringSecurity        |
| 缓存     | Redis                 |
| 项目构建 | Maven                 |
| 运行环境 | 华为云Centos          |

#### 1.4.结构设计

![image-20231114145632768](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20231114145632768.png)

![e936cec553b5b745407e0cf84c12088](C:\Users\Administrator\Desktop\材料\e936cec553b5b745407e0cf84c12088.png)

### 2.项目内容

#### 2.1.页面展示

![首页_01](C:\Users\Administrator\Desktop\材料\首页_01.png)

#### 2.2.博客分类

![博客分类_02](C:\Users\Administrator\Desktop\材料\博客分类_02.png)

#### 2.3.面试辅导

![面试辅导_03](C:\Users\Administrator\Desktop\材料\面试辅导_03.png)

#### 2.4.私教带徒

![私教带徒_04](C:\Users\Administrator\Desktop\材料\私教带徒_04.png)

#### 2.5.文章编辑

![文章编辑](C:\Users\Administrator\Desktop\材料\文章编辑.png)

#### 2.6.后台管理

![5a2986678b5f218ad26118c5fdbe9df](D:\wechart\WeChat Files\wxid_iajw48j8m3yu12\FileStorage\Temp\5a2986678b5f218ad26118c5fdbe9df.png)

### 3.功能描述

#### 3.1.主页

- 博客汇总，以列表形式展示文章，并附上文章作者、发布日期、分类情况以及文章简要
- 能够以分类形式查看文章
- 能够以时间列表方式归档文章
- 可实现通过标签查找所有相关文章
- 个人介绍、联系方式
- 博客网站更新记录
- 友链链接 

#### 3.2后台管理

- 网站仪表盘，记录网站访客量情况
- 文章管理

1. 分页展示文章信息
2. 可对文章进行再编辑以及删除文章

- 发布文章

1. 使用markdown编辑器，支持插入代码，插入图片等功能
2. 文章可选择分类和标签，以及转载文章支持链接原作者文章

- 分类管理，支持增加、删除、修改分类

-  友情链接

1. 支持增加友情链接
2. 支持删除友情链接

- 反馈信息管理，可查看用户反馈信息

#### 3.3.安装部署

- 支持-jar方式
- 使用springboot自带方式打包

### 4.项目业务设计

#### 4.1.发表文章流程

![image-20231114150912682](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20231114150912682.png)

#### 4.2.登录流程

![image-20231114150931573](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20231114150931573.png)

#### 4.3.用户个人资料修改流程

![image-20231114150951820](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20231114150951820.png)

#### 4.4.打包、部署和运行

- 本项目采用Springboot的maven插件进行打包，打包结果：**.jar
- 部署方式：使用 nohup java -jar **.jar >**.log 2>&1 &的方式，后台启动项目，并在该路径下生成运行日志。

#### 4.5.数据库设计

**用户表：user**

| 名称           | 类型    | 长度 | 主键  | 非空  | 描述         |
| -------------- | ------- | ---- | ----- | ----- | ------------ |
| id             | int     | 11   | true  | true  | 主键、自增   |
| phone          | varchar | 255  | false | true  | 手机号       |
| username       | varchar | 255  | false | true  | 用户名       |
| password       | varchar | 255  | false | true  | 密码         |
| gender         | char    | 50   | false | true  | 性别         |
| trueName       | varchar | 255  | false | false | 姓名         |
| birthday       | char    | 100  | false | false | 生日         |
| email          | varchar | 255  | false | false | 邮箱         |
| personalBrief  | varchar | 255  | false | false | 个人简介     |
| avatarImgUrl   | varchar | 255  | false | true  | 头像url      |
| recentlyLanded | varchar | 255  | false | false | 最近登录时间 |

**文章表：article**

| 名称              | 类型     | 长度 | 主键  | 非空  | 描述         |
| ----------------- | -------- | ---- | ----- | ----- | ------------ |
| id                | int      | 11   | true  | true  | 主键，自增   |
| articleId         | bigint   | 20   | false | true  | 作者         |
| originalAuthor    | varchar  | 255  | false | true  | 文章原作者   |
| articleTitle      | varchar  | 255  | false | true  | 文章标题     |
| articleContent    | longtext | 0    | false | true  | 文章内容     |
| articleTags       | varchar  | 255  | false | true  | 文章标签     |
| articleType       | varchar  | 255  | false | true  | 文章类型     |
| articleCategories | varchar  | 255  | false | true  | 文章分类     |
| publishDate       | varchar  | 255  | false | true  | 发布文章日期 |
| updateDate        | varchar  | 255  | false | true  | 更新文章日期 |
| articleUrl        | varchar  | 255  | false | true  | 文章url      |
| articleTabloid    | int      | 255  | false | true  | 文章摘要     |
| likes             | int      | 11   | false | true  | 文章喜欢数   |
| lastArticleId     | bigint   | 20   | false | false | 上一篇文章id |
| nextArticleId     | bigint   | 20   | false | false | 下一篇文章id |

**评论记录表：comment_record**

| 名称           | 类型   | 长度 | 主键  | 非空 | 描述       |
| -------------- | ------ | ---- | ----- | ---- | ---------- |
| id             | bigint | 20   | true  | true | 主键，自增 |
| pId            | bigint | 20   | false | true | 文章原作者 |
| answererId     | int    | 11   | false | true | 评论者id   |
| respondentId   | int    | 11   | false | true | 评论日期   |
| likes          | int    | 11   | false | true | 评论点赞数 |
| commentContent | text   | 0    | false | true | 评论内容   |

### 5.开发流程

#### 5.1.数据库CRUD

- controller层中编写前端接口，接收前端参数
- service层中编写所需业务接口，供controller层调用
- 实现service层中的接口，并注入mapper层中的sql接口
- 采用Mybatis的JavaConfig方式编写Sql语句。由于并没有使用Mybatis的逆向功能，需要自己手写所有sql语句
- 关于事务的实现，在启动类中开启事务，并在service层需要实现事务的业务接口上使用@Transactional注解，还是十分方便的
- 本项目开发并不是很难，只是在业务的实现上比较复杂
- 页面与展示
- 作为一名后端开发，对于css的功力有所欠缺，这里我从百度下载对应的UI，极大的减少了页面的开发难度.
- 前端页面与后端的交互主要是在controller包中，并使用Thymeleaf渲染页面。
- 自定义异常处理页面，通过重写WebMvcConfigurerAdapter实现自动跳转到404、403页面

#### 5.2.其他功能

- 使用lazyload插件实现页面图片懒加载
- 后台实时记录当天访客量，便于了解博客日常访问量
- 分析访问量最多的数据，主要在于文章访问部分，将文章放入redis缓存。每次编辑完文章后，更新缓存
- 使用华为云互联网中间件的业务实时监控服务，对于网站性能的了解以及优化有很大的帮助

#### 5.3.网站建设

- 服务器选用的是华为云centos7
- 域名是华为云上购买的域名
- 网站备案以及公安机关备案，后者备案时间较短但是那个备案网站经常挂掉，所以公安机关备案还得看运气。而网站备案时间就比较长了，按照华为云的流程走大概1个月左右时间，需要上传个人身份信息以及邮寄个人资料过去。
- 网站配置了安全证书，可实现https访问以及自动从http跳转到https。

### 6.开发中遇到的难点

- 要实现在一个页面进行权限验证，如果验证不成功会跳转到登录界面，并且登录成功后还要返回到之前界面，这里由于对SpringSecurity内部原理的不了解，所以我这里采用的方法是利用请求头和响应头存储url，并在登录成功后的页面出跳转到响应头中存储的url处。
- 上传头像处使用上传头像至华为云的OBS对象存储中，由于上传问题并没有返回上传成功后的图片url地址，于是只好设置OBS的Bucket为公共读权限，然后当上传成功后手动拼接图片url并存入数据库。
- 项目中最大的难点还是莫过于页面css的设计，但是使用了妹子UI后极大的解决了这个问题，只需修改少量css就能实现自己所需要的样式。

![e936cec553b5b745407e0cf84c12088](C:\Users\Administrator\Desktop\材料\e936cec553b5b745407e0cf84c12088.png)