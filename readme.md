## Service层数据库自动回滚

涉及：代理模式、反射

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

涉及到JSON

## Controller请求映射

原理参考的是SpringMVC框架。

**涉及**：单例模式、注解、反射、一点点泛型。

可以实现请求自动绑定到Controller的方法上。

因为Servlet最大的问题就是一个Servlet只能写少量的方法，而且它们的请求路径都是固定的。不够灵活，这样子就会导致项目太大的时候，会有很多的Servlet类，此外，类之间共享数据也是个问题。

本项目的请求绑定的原理是通过注解和反射收集Controller的类信息和方法信息，并通过DispatcherController统一接收所有的请求。如果是Controller的请求，则通过遍历Controller信息，找到匹配请求路径的Controller方法，并通过反射调用。

反射要求我们清楚被反射的方法的参数，由于涉及到类型转换之类的问题，我就没有做太复杂，而是统一将参数解析后存在JSONObject对象中。

反射还有一个条件，那就是获取被反射的对象实例。由于Controller一般都是单例的，所以写了一个BeanFactory用来创造Controller单例对象，将类名首字母小写后作为key，实例作为value，保存在HashMap中。在遍历Controller信息时，就可以通过类名来获取。

这一功能还需要处理不同HTTP请求的问题，如果是GET请求，那么其参数在url中，需要解析url获取。如果是以content-type = "application/json"的方式传输，则可以通过json获取，如果是以表单格式传输，则可以同或req.getParameter()方法获取。但如果是multipart/form-data格式，则可能为文件上传等类型的请求，这种时候可以直接交予原生Servlet处理。

同理，如果是需要访问前端页面等请求，也会经过DispatcherController类的处理。如果判断其实请求前端页面，则直接交予原生Servlet请求即可。

这个功能还可以改进，例如：

1.   需要检查是否存在两个完全一致的Controller请求路径。因为寻找可执行的Controller方法是按顺序遍历的，找到了就直接返回，不继续向后检查。如果请求路径重复很容易出错。
2.   此外还可以完成参数绑定功能，根据方法参数的不同传参。其实现思路和Controller绑定请求差不多。
3.   如果是请求静态资源，也应该跳过这种请求，交予原生Servlet处理。

这个功能比较复杂，如果看不懂可以略过。而且这个功能还没进过测试，很多东西都可能出错，简单来说就是个坑。可以参考一下它的实现原理，学习一下其中涉及到的一些Java知识，如果在项目中直接运用，可能会导致很多bug。