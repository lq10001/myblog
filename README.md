#我的Blog系统

## 公众号：全栈程序员之路
![输入图片说明](https://mp.weixin.qq.com/mp/qrcode?scene=10000004&size=102&__biz=MzIzMTE0NTE5Mg==&mid=2651421365&idx=1&sn=ec2b310f1888fca3560ba504af0077f5&send_time= "在这里输入图片标题")

## 预览图
1. 主页
![输入图片说明](https://git.oschina.net/uploads/images/2017/0715/213428_084682a7_3018.png "在这里输入图片标题")
2.详情
![输入图片说明](https://git.oschina.net/uploads/images/2017/0715/213440_8f75213e_3018.png "在这里输入图片标题")
3 后台编辑
![输入图片说明](https://git.oschina.net/uploads/images/2017/0715/213449_d2f82bf4_3018.png "在这里输入图片标题")
4 Github
![输入图片说明](https://git.oschina.net/uploads/images/2017/0715/213502_7a90baf1_3018.png "在这里输入图片标题")
5. gitbook
![输入图片说明](https://git.oschina.net/uploads/images/2017/0715/213511_ed5b59a2_3018.png "在这里输入图片标题")
6. 最后来张关于我
![输入图片说明](https://git.oschina.net/uploads/images/2017/0715/213523_13edc174_3018.png "在这里输入图片标题")

## 描述
1. 这是个人Blog系统，用于记录工作，学习中的点点滴滴。
2. 使用Java开发，技术架构为Nutz + Avalon + Editor.md + Mysql
3. 文件的存储方式为，md + html源文件，html源文件存入数据库中。
4. 

## 体验地址
1. http://115.28.2.33:8080/blog/
2. https://www.gitbook.com/

## 产品定位
1. 公司/个人博客系统

## 产品解决的痛点
使用Github来管理博客文章的版本问题

## 产品特色
1. 通过Github来管理博客文章版本，提交Github时，设置webhook回调，自动同步到博客系统和Gitbook中
2. 博客系统审核文章，发布到博客上。
3. 文章使用Markdown方式编辑，
4. 文章解析后，存入Mysql 和 缓存系统，提高访问速度和并发量。
5. Gitbook同步生成pdf/epub等书籍，
6. Markdown能导入到百度阅读等平台，直接发售。

## 产品潜在客户
1. 企业
2. 个人

## 产品下一步计划
1. 自媒体产品(如虎嗅)

## 技术架构
1. Java：Nutz框架
2. 缓存：EhCache，
3. 静态模板框架：Beetl
4. 后台UI框架：B-JUI
5. 数据库：MySql
6. 后台服务器：阿里云centos6
7. MD编辑器：editor.md
8. 前端UI：bootstrap