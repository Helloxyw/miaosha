---
title: Java秒杀系统方案优化 高性能高并发实战
date: 2019-6-16
tag: 秒杀
---





![](https://desk-fd.zol-img.com.cn/t_s1440x900c5/g5/M00/01/0E/ChMkJlbKweiIBt6YAAvmGfwhdgwAALGfwFf6LcAC-Yx733.jpg)





<h2>
  项目安排
</h2>

<h3>1.项目搭建</h3>

* `SpringBoot` 环境搭建
* 集成`Thymeleaf`,`Result`结果封装
* 集成`Mybatis` + `Druid`
* 集成`Jedis` +` Redis`安装 + 通用缓存`Key`封装

<!--more -->

<h3>2.实现登录功能</h3>

* 数据库设计
* 明文密码两次`MD5`处理
* `JSR303`参数校验 + 全局异常处理器
* 分布式`Session`



<h3>3.实现秒杀功能</h3>

* 数据库设计
* 商品列表页
* 商品详情页
* 订单详情页 



<h4>4.JMeter压测</h4>

* `JMeter`入门
* 自定义多变量模拟多用户
* `JMeter`命令行使用
* `SpringBoot`打`War`包



<h3>5.页面优化技术</h3>

* 页面缓存 + `URL`缓存 + 对象缓存
* 页面静态化 ，前后端分离
* 静态资源优化
* `CDN`优化



<h3>6.接口优化</h3>

* `Redis`预减库存减少数据库访问
* 内存标记减少`Redis`访问
* `RabbitMQ`队列缓冲，异步下单，增强用户体验
* `RabbitMQ`安装与`SpringBoot`集成
* 访问`Nginx`水平扩展
* 分库分表（`Mycat`)
* 压测



<h3>7.安全优化</h3>

* 秒杀接口地址隐藏
* 数学公式验证码
* 接口防刷



------

<h2>1.项目搭建</h2>

<h3>集成Redis中的问题和笔记</h3>

<h4>1.Spring注入</h4>

`Spring`通过`DI`（依赖注入）实现`IOC`（控制反转)，常用的注入方式主要有以下三种:

* 构造方法注入
* `setter`方法注入
* 基于注解的注入

<h4>构造方法注入</h4>

在spring的配置文件中注册UserService，将UserDaoJdbc通过constructor-arg标签注入到UserService的**某个**有参数的构造方法

```java
<!-- 注册userService -->
<bean id="userService" class="com.lyu.spring.service.impl.UserService">
	<constructor-arg ref="userDaoJdbc"></constructor-arg>
</bean>
<!-- 注册jdbc实现的dao -->
<bean id="userDaoJdbc" class="com.lyu.spring.dao.impl.UserDaoJdbc"></bean>
```

如果只有一个有参数的构造方法并且参数类型与注入的bean的类型匹配，那就会注入到该构造方法中。

```java
public class UserService implements IUserService {

	private IUserDao userDao;
	
	public UserService(IUserDao userDao) {
		this.userDao = userDao;
	}
	
	public void loginUser() {
		userDao.loginUser();
	}

}
```

```java
@Test
public void testDI() {
	ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
	// 获取bean对象
	UserService userService = ac.getBean(UserService.class, "userService");
	// 模拟用户登录
	userService.loginUser();
}
```

​		

<h4>setter注入</h4>

配置文件如下：

```java
<!-- 注册userService -->
<bean id="userService" class="com.lyu.spring.service.impl.UserService">
	<!-- 写法一 -->
	<!-- <property name="UserDao" ref="userDaoMyBatis"></property> -->
	<!-- 写法二 -->
	<property name="userDao" ref="userDaoMyBatis"></property>
</bean>

<!-- 注册mybatis实现的dao -->
<bean id="userDaoMyBatis" class="com.lyu.spring.dao.impl.UserDaoMyBatis"></bean>
```

**注：上面这两种写法都可以,spring会将name值的每个单词首字母转换成大写，然后再在前面拼接上"set"构成一个方法名,然后去对应的类中查找该方法,通过反射调用,实现注入。**

*切记：name属性值与类中的成员变量名以及set方法的参数名都无关，只与对应的set方法名有关，下面的这种写法是可以运行成功的*

```java
public class UserService implements IUserService {

	private IUserDao userDao1;
	
	public void setUserDao(IUserDao userDao1) {
		this.userDao1 = userDao1;
	}
	
	public void loginUser() {
		userDao1.loginUser();
	}

}
```

**还有一点需要注意：如果通过set方法注入属性，那么spring会通过默认的空参构造方法来实例化对象，所以如果在类中写了一个带有参数的构造方法，一定要把空参数的构造方法写上，否则spring没有办法实例化对象，导致报错。**



<h4>基于注解的注入</h4>

在介绍注解注入的方式前，先简单了解`bean`的一个属性`autowire`，`autowire`主要有三个属性值：`constructor`，`byName`，`byType`。

* `constructor`：通过构造方法进行自动注入，`spring`会匹配与构造方法参数类型一致的`bean`进行注入，如果有一个多参数的构造方法，一个只有一个参数的构造方法，在容器中查找到多个匹配多参数构造方法的`bean`，那么`spring`会优先将`bean`注入到多参数的构造方法中。

* `byName`：被注入`bean`的id名必须与set方法后半截匹配，并且id名称的第一个单词首字母必须小写，这一点与手动set注入有点不同。

* `byType`：查找所有的set方法，将符合符合参数类型的bean注入。

---------------------
主要有四种注解可以注册bean，每种注解可以任意使用，只是语义上有所差异：

* `@Component`：可以用于注册所有bean
* `@Repository`：主要用于注册dao层的bean
* `@Controller`：主要用于注册控制层的bean
* `@Service`：主要用于注册服务层的bean

描述依赖关系主要有两种：

* `@Resource`：java的注解，默认以byName的方式去匹配与属性名相同的bean的id，如果没有找到就会以byType的方式查找，如果byType查找到多个的话，使用@Qualifier注解（spring注解）指定某个具体名称的bean。
* `@Autowired`：spring注解，**默认是以byType的方式去匹配类型相同的bean**，如果只匹配到一个，那么就直接注入该bean，无论要注入的 bean 的 name 是什么；如果匹配到多个，就会调用就会调用 **`DefaultListableBeanFactory`** 的 **`determineAutowireCandidate`** 方法来决定具体注入哪个bean

> determineAutowireCandidate 方法的逻辑是：
>
> 1.  先找 Bean 上有@Primary 注解的，有则直接返回 bean 的 name。
> 2. 再找 Bean 上有 @Order，@PriorityOrder 注解的，有则返回 bean 的 name。
> 3. 最后再以名称匹配（ByName）的方式去查找相匹配的 bean

虽然有这么多的注入方式，但是实际上开发的时候自己编写的类一般用注解的方式注册类，用@Autowired描述依赖进行注入，一般实现类也只有一种（jdbc or hibernate or mybatis），除非项目有大的变动，所以@Qualifier标签用的也较少；但是在使用其他组件的API的时候用的是通过xml配置文件来注册类，描述依赖，因为你不能去改人家源码嘛。



<h4>2.</h4>



[![WX20190519-182215-2x.png](https://i.postimg.cc/Pr1YV84q/WX20190519-182215-2x.png)](https://postimg.cc/tYCsYJ80)





<h3>2.实现登录</h3>

<h4>1.两次MD5</h4>

1. 用户端：PASS = MD5（明文 + 固定Salt)
2. 服务端：PASS = MD5（用户输入 + 随机Salt）



<h4>2.数据库三大范式</h4>

* 1NF：要求有主键，并且要求每一个字段原子性不可再分

> 1NF是对属性的**原子性**，要求属性具有原子性，不可再分解；
>
> 表：字段1、 字段2(字段2.1、字段2.2)、字段3 ......
>
> 如学生（学号，姓名，性别，出生年月日），如果认为最后一列还可以再分成（出生年，出生月，出生日），它就不是一范式了，否则就是。



* 2NF：要求所有非主键字段完全依赖主键，不能产生部分依赖

> 2NF是对记录的**惟一性**，要求记录有惟一标识，即实体的惟一性，即不存在部分依赖；
>
> 表：学号、课程号、姓名、学分;
>
> 这个表明显说明了两个事务:学生信息, 课程信息;由于非主键字段必须依赖主键，这里**学分依赖课程号**，**姓名依赖与学号**，所以不符合二范式。
>
> **可能会存在问题：**
>
> - `数据冗余:`，每条记录都含有相同信息；
> - `删除异常：`删除所有学生成绩，就把课程信息全删除了；
> - `插入异常：`学生未选课，无法记录进数据库；
> - `更新异常：`调整课程学分，所有行都调整。
>
> **正确做法:** 
> 学生：`Student`(学号, 姓名)； 
> 课程：`Course`(课程号, 学分)； 
> 选课关系：`StudentCourse`(学号, 课程号, 成绩)。



* 3NF：所有非主键字段和主键字段之间不能传递依赖

> 3NF是对字段的**冗余性**，要求任何字段不能由其他字段派生出来，它要求字段没有冗余，即不存在传递依赖；
>
> 表: 学号, 姓名, 年龄, 学院名称, 学院电话
>
> 因为存在**依赖传递**: (学号) → (学生)→(所在学院) → (学院电话) 。
>
> **可能会存在问题：**
>
> - `数据冗余:`有重复值；
> - `更新异常：`有重复的冗余信息，修改时需要同时修改多条记录，否则会出现**数据不一致的情况** 。
>
> **正确做法：**
>
> 学生：(学号, 姓名, 年龄, 所在学院)；
>
> 学院：(学院, 电话)。

<h4>反范式</h4>

**一般说来，数据库只需满足第三范式（3NF）就行了。**

> 没有冗余的数据库设计可以做到。但是，没有冗余的数据库未必是最好的数据库，有时为了提高运行效率，就必须降低范式标准，适当保留冗余数据。具体做法是：在概念数据模型设计时遵守第三范式，降低范式标准的工作放到物理数据模型设计时考虑。降低范式就是增加字段，允许冗余，**达到以空间换时间的目的**。
>
> 〖例〗：有一张存放商品的基本表，如表1所示。“金额”这个字段的存在，表明该表的设计不满足第三范式，因为“金额”可以由“单价”乘以“数量”得到，说明“金额”是冗余字段。但是，增加“金额”这个冗余字段，可以**提高查询统计的速度**，这就是以**空间换时间**的作法。

<h4>范式化设计和反范式化设计的优缺点</h4>

**范式化**

**优点**

* 可以尽可能的减少数据冗余
* 数据表更新快，体积小

**缺点**

* 对于查询需要对多个表进行关联，导致性能低
* 更难进行索引优化



**反范式化**

**优点**

* 可以减少表的关联
* 可以更好进行索引优化

**缺点**

* 存在数据冗余及数据维护异常
* 对数据的修改需要更多的成本





<h3>五、页面优化</h3>

<h4>页面缓存</h4>

1. 取缓存
2. 手动渲染模板
3. 结果输出



<h4>静态资源优化</h4>

1. JS/CSS压缩，减少流量
2. 多个JS/CSS组合，减少连接数
3. CDN就近访问



<h3>六、秒杀接口优化</h3>

**思路：减少数据库访问 **

1. 系统初始化，把商品库存数量加载到Redis
2. 收到请求，Redis预减库存，库存不足，直接返回，否则进入3
3. 请求入队，立即返回排队中
4. 请求出队，生成订单，减少库存
5. 客户端轮询，是否秒杀成功



**1.`SpringBoot `集成`RabbitMQ`**

1. 添加依赖`spring-boot-starter-amqp`
2. 创建消息接受者
3. 创建消息发送者



<h3>7.安全优化</h3>

- 秒杀接口地址隐藏
- 数学公式验证码
- 接口防刷



**1.秒杀接口地址隐藏**

**思路：秒杀开始之前，先去请求接口获取秒杀地址**

1. 接口改造，带上PathVariable参数
2. 添加生成地址的接口
3. 秒杀收到请求，先验证PathVariable



**2.数学公式验证码**

**思路：点击秒杀之前，先输入验证码，分散用户的请求**

1. 添加生成验证码的接口
2. 在获取秒杀路径的时候，验证验证码
3. ScriptEngine使用 



**3.接口限流**

把用户访问次数写入缓存，并加上有效时间。（可以用拦截器减少对业务代码的侵入）







