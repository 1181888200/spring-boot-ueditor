# spring-boot-ueditor
springboot结合百度编辑器demo

修改如下配置文件

1. application.yml 配置文件中的【端口】和【项目名称】

2. ueditor.config.json 文件中的对应的访问前缀信息【imageUrlPrefix】【scrawlUrlPrefix】【snapscreenUrlPrefix】【videoUrlPrefix】
         这个里面的imagePathFormat默认情况不需要修改，如果变动，请UeditorResourceController 类跟着变动

3. /spring-boot-ueditor/src/main/resources/static/ueditor/ueditor.config.js
   3.1 根据【项目名称】修改对应的UEDITOR_HOME_URL
   3.2 修改 window.UEDITOR_CONFIG --> 中的serverUrl 属性，即UeditorConfigController控制器
   
4. /spring-boot-ueditor/src/main/java/com/lwl/ueditor/EduitorConfig.java
	修改类中属性对应的值即可	