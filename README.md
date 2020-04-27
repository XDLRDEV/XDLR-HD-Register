
## Home Page ##

Now that the web site is running, visit 

    http://localhost:8080/index

to commit user infomation.

## RESTful Web Service ##

    http://localhost:8080/rest/greeting

It will respond with a JSON representation of a greeting, as the following listing shows:

    {"id":1,"content":"Hello, World!"}

You can customize the greeting with an optional name parameter in the query string, as the following listing shows:

    http://localhost:8080/rest/greeting?name=User

## Access User Data ##

The following curl command register a user:

```
$ curl localhost:8080/demo/user/register -d idNo=620525186905125907 -d name=First -d phone=18364308360 -d company=xdlr -d companyAddress=1919 -d toHubei=false -d toWuhan=false -d isFever=false -d isNewCoronavirus=false
```

The reply should be as follows:

    Saved

The following curl command edit a user:

    $ curl localhost:8080/demo/user/edit -d idNo=620525186905125907 -d name=First -d phone=18364308360 -d company=xdlr -d companyAddress=1919 -d toHubei=false -d toWuhan=false -d isFever=false -d isNewCoronavirus=false

The following command shows all the users:
    
    $ curl 'localhost:8080/demo/user/all'

The following command shows one user:

    $ curl 'localhost:8080/demo/user?idNo=620525186905125907'

The reply should be as follows:

    {"id":1,"idNo":"620525186905125907","name":"zhangsan","phone":"18364308360","company":"xdlr","companyAddress":"1919","toHubei":false,"toWuhan":false,"registerTime":"2020-02-04T19:53:54.000+0800","temperatureRecords":[],"newCoronavirus":false,"fever":false}

The following command record user temperature:

    $ curl localhost:8080/demo/user/temperature/record -d idNo=620525186905125907 -d temperature=37

## Create A New Database ##

To create a new database, run the following commands at the mysql prompt:

    mysql> create database db_example; -- Creates the new database
    mysql> create user 'springuser'@'%' identified by 'ThePassword'; -- Creates the user
    mysql> create user 'lspringuser'@'localhost' identified by 'ThePassword'; -- Creates the user can be accessed from localhost
    mysql> grant all on db_example.* to 'springuser'@'%'; -- Gives all privileges to the new user on the newly created database

## MariaDB数据库字符编码 ##

1.编辑文件/etc/my.cnf, 在[mysqld]标签下添加内容如下:

```
[mysqld]
init_connect='SET collation_connection = utf8_unicode_ci'
init_connect='SET NAMES utf8'
character-set-server=utf8
collation-server=utf8_unicode_ci
skip-character-set-client-handshake
```

2.编辑文件/etc/my.cnf.d/client.cnf, 在[client]中添加内容如下:

```
[client]
default-character-set=utf8
```

3.编辑文件/etc/my.cnf.d/mysql-clients.cnf, 在[mysql]中添加内容如下:

```
[mysql]
default-character-set=utf8
```

4.重启mariadb

```undefined
systemctl restart mariadb
```

5.进入MariaDB查看字符集

```ruby
MariaDB [(none)]> show variables like "%character%";
```

## Make Database Security ##

Make Some Security Changes [https://spring.io/guides/gs/accessing-data-mysql/](https://spring.io/guides/gs/accessing-data-mysql/)

    mysql> revoke all on db_example.* from 'springuser'@'localhost';
    
    mysql> grant select, insert, delete, update on db_example.* to 'springuser'@'localhost';

## Build an executable JAR ##

If you use Gradle, you can run the application by using 

    ./gradlew bootRun

Alternatively, you can build the JAR file by using 

    ./gradlew build 

and then run the JAR file, as follows:

    java -jar build/libs/gs-accessing-data-mysql-0.1.0.jar
    nohup java -jar build/libs/xdlr-hd-register-0.0.1-SNAPSHOT.jar & -- Run on background

view process, port

    netstat -tunlp

## Production access ##

    注册用户
    $ curl ip:8085/data/user/register -d idNo=620525186905125907 -d name=First -d phone=18364308360 -d company=xdlr -d companyAddress=1919 -d toHubei=false -d toWuhan=false -d isFever=false -d isNewCoronavirus=false
    
    提交一次体温测试，可以多次提交
    $ curl ip:8085/data/user/temperature/record -d idNo=620525186905125907 -d temperature=37
    
    查看所有用户信息
    $ curl 'http://ip:8085/data/user/all'
    
    查看某个用户信息
    $ curl 'http://ip:8085/data/user?idNo=620525186905125907'

## Logging ##

Retain the access log files 7 days before they are removed.

Location of the app log file:

    my-app/logs/access_log.app.log

Location of the server tomcat log:

```
my-tomcat/logs
```

## Reference ##

https://spring.io/guides/gs/serving-web-content/

https://spring.io/guides/gs/rest-service/

https://spring.io/guides/gs/accessing-data-mysql/

https://spring.io/guides/gs/accessing-data-rest/

https://spring.io/guides/gs/consuming-rest-jquery/

https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#preface

https://docs.spring.io/spring-boot/docs/current/reference/html/appendix-application-properties.html

https://docs.oracle.com/cd/E17952_01/mysql-8.0-en/index.html

## 其它 ##

QRCode+html2canvas 实现本地下载带边框的二维码
https://blog.csdn.net/Max1992/article/details/88874142

正确关闭spring boot
https://stackoverflow.com/questions/26547532/how-to-shutdown-a-spring-boot-application-in-a-correct-way