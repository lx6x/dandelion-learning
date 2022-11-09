* 创建用户：create user 'dandelion'@'%' identified by 'dandelion123..';
* 用户授权数据库：grant select,insert,update,delete,create on [数据库名称].* to [用户名称]; *代表整个数据库
* 立即启用修改：flush privileges;
* 取消用户所有数据库（表）的所有权限：revoke all on *.* from tester;
* 删除用户：delete from mysql.user where user='tester';