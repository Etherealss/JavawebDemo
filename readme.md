## Service 层数据库自动回滚

涉及：代理模式、反射

相关代码：core.transaction 中的两个类
- ServiceTransactionProxy：代理模式的实现类
- ProxyFactory：生产代理对象的工厂

使用：ServiceProxyFactory：获取受过代理的 Service 对象

Spring 框架就有这个功能，可以手敲出来

## 统一格式的 API 信息包

相关代码：pojo.vo 中的 ApiMsg、common.enums 的 ApiInfo
- ApiMsg 用于向前端传递格式统一的 json 数据
- ApiInfo 用于表示后端的运行情况

## 获取参数、返回数据

相关代码：utils 中的 GetParamUtil、ResponseUtil

涉及到 JSON

## Controller 请求映射

原理参考的是 SpringMVC 框架。

**涉及**：单例模式、注解、反射、一点点泛型。

可以实现请求自动绑定到 Controller 的方法上。

因为 Servlet 最大的问题就是一个 Servlet 只能写少量的方法，而且它们的请求路径都是固定的。不够灵活，这样子就会导致项目太大的时候，会有很多的 Servlet 类，此外，类之间共享数据也是个问题。

本项目的请求绑定的原理是通过注解和反射收集 Controller 的类信息和方法信息，并通过 DispatcherController 统一接收所有的请求。如果是 Controller 的请求，则通过遍历 Controller 信息，找到匹配请求路径的 Controller 方法，并通过反射调用。

反射要求我们清楚被反射的方法的参数，由于涉及到类型转换之类的问题，我就没有做太复杂，而是统一将参数解析后存在 JSONObject 对象中。

反射还有一个条件，那就是获取被反射的对象实例。由于 Controller 一般都是单例的，所以写了一个 BeanFactory 用来创造 Controller 单例对象，将类名首字母小写后作为 key，实例作为 value，保存在 HashMap 中。在遍历 Controller 信息时，就可以通过类名来获取。

这一功能还需要处理不同 HTTP 请求的问题，如果是 GET 请求，那么其参数在 url 中，需要解析 url 获取。如果是以 `content-type = "application/json"` 的方式传输，则可以通过 json 获取，如果是以表单格式传输，则可以同或 `req.getParameter()` 方法获取。但如果是 `multipart/form-data` 格式，则可能为文件上传等类型的请求，这种时候可以直接交予原生 Servlet 处理。

同理，如果是需要访问前端页面等请求，也会经过 DispatcherController 类的处理。如果判断其实请求前端页面，则直接交予原生 Servlet 请求即可。

这个功能还可以改进，例如：

1. 需要检查是否存在两个完全一致的 Controller 请求路径。因为寻找可执行的 Controller 方法是按顺序遍历的，找到了就直接返回，不继续向后检查。如果请求路径重复很容易出错。开发人员可能一不小心就写了两个一模一样的请求路径，但他自己忘了。
2. **参数绑定**功能，现在 Controller 的方法参数是固定的，但可以实现根据方法参数的不同传参。其实现思路和 Controller 绑定请求差不多。
3. **返回值处理**功能，可以统一 Controller 返回值（例如统一为 ApiMsg 对象）。例如 UserController 返回了 User 对象，则将其包装成 `ApiMsg<User>` 对象
4. **Controller 异常处理**功能。可以在 DispatcherServlet 里拦截 Controller 传上来的所有异常，并根据需要进行处理。例如自定义 NotFoundException，然后当捕获该异常时，执行对应的代码。如果设计该功能能够灵活扩展不同的异常是重点。
5. 如果是请求**静态资源**，也应该跳过这种请求，交予原生 Servlet 处理。我的代码中还未涉及静态资源的处理，只是如果没有匹配的 Controller 接口才会通过原生 Servlet 处理。

> 以上这些都是 SpringMVC 或 SpringBoot 框架支持的功能。

这个功能相对“复杂”，如果看不懂可以略过。而且这个功能还没经过完整测试，很多东西都可能出错，简单来说就是个坑。所以可以参考一下它的实现原理，学习一下其中涉及到的一些 Java 知识，**如果在项目中直接复制运用，可能会导致很多 bug，或者是导致扩展性很差（如在添加新功能时收到原有代码的限制，导致无法扩展新功能），这都是很严重的问题**。建议在参考之后取其精华即可。

同时这个功能是模仿 SpringMVC 框架的，其实现相比于框架而言，简单粗暴了许多，如果未来想要更好地实现它，可以参考框架源码