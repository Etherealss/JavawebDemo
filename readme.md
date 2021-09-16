## Service层数据库自动回滚

涉及：代理模式
相关代码：core.transaction中的两个类
- ServiceTransactionProxy：代理模式的实现类
- ProxyFactory：生产代理对象的工厂

使用：ServiceProxyFactory：获取受过代理的Service对象

Spring框架就有这个功能，可以手敲出来

## 统一格式的API信息包

相关代码：pojo.vo中的ApiMsg、common.enums的ApiInfo
- ApiMsg用于向前端传递格式统一的json数据
- ApiInfo用于表示后端的运行情况

## 获取参数、返回数据

相关代码：utils中的GetParamUtil、ResponseUtil

## Controller请求映射
还没搞定