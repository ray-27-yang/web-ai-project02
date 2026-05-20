# 注解（@）详细讲解笔记（含原文示例）

实现内容清单（将执行的工作）：
- [x] 提取工程中出现的常用注解
- [x] 对每个注解展示项目中的原文代码片段（示例）
- [x] 给出来源、作用、详细用法说明与注意事项（包括常见坑与最佳实践）

说明：下面每个注解项都会先显示一段“原文代码片段”（取自项目），随后给出注解的详细讲解与使用注意点。已去掉“项目中的位置”条目，改为直接展示原文代码以便对照学习。

---

## 目录（按注解名）

- `@SpringBootApplication`
- `@RestController`
- `@RequestMapping`, `@GetMapping`, `@PostMapping`, `@DeleteMapping`, `@PutMapping`
- `@Slf4j`
- `@Autowired`
- `@RequestBody`, `@PathVariable`
- MyBatis 注解：`@Mapper`, `@Select`, `@Insert`, `@Update`, `@Delete`, `@Options`
- `@Service`
- `@Transactional`
- Lombok 注解：`@Data`, `@NoArgsConstructor`, `@AllArgsConstructor`, `@Slf4j`
- `@DateTimeFormat`
- 测试注解：`@Test`, `@SpringBootTest`

---

### `@SpringBootApplication`

示例原文：
```java
@SpringBootApplication
public class TliasWebManagementApplication {
    public static void main(String[] args) {
        SpringApplication.run(TliasWebManagementApplication.class, args);
    }
}
```

来源：org.springframework.boot.autoconfigure.SpringBootApplication

作用与讲解：
- 组合注解，等价于 `@Configuration`、`@EnableAutoConfiguration`、`@ComponentScan`。
- 放在应用主类上用于触发 Spring Boot 的自动配置与组件扫描。

注意事项与最佳实践：
- 主类上通常只需要一个 `@SpringBootApplication`；如果使用多个模块，注意 `@ComponentScan` 的扫描范围可能会影响 Bean 注册。
- 在测试或子模块中需要自定义扫描时，可以通过 `scanBasePackages` 或单独使用 `@EnableAutoConfiguration`/`@ComponentScan` 进行更精细控制。

---

### `@RestController`

示例原文：
```java
@Slf4j
@RequestMapping("/depts")
@RestController
public class DeptController {
    @Autowired
    private DeptService deptService;

    @GetMapping
    public Result list(){
        log.info("查询所有部门顺序");
        List<Dept> deptList = deptService.findAll();
        return Result.success(deptList);
    }
    // ...
}
```

来源：org.springframework.web.bind.annotation.RestController

作用与讲解：
- `@RestController` 是 `@Controller` + `@ResponseBody` 的组合，控制器方法返回的对象会直接序列化为 HTTP 响应体（通常是 JSON）。

注意事项与最佳实践：
- 若需要返回视图（Thymeleaf 等），不要使用 `@RestController`，改用 `@Controller`。
- 对异常处理、统一响应结构（如本项目的 `Result`）建议配合 `@ControllerAdvice` 使用全局异常处理与统一返回格式。

---

### 路由注解：`@RequestMapping`, `@GetMapping`, `@PostMapping`, `@DeleteMapping`, `@PutMapping`

示例原文：
```java
@RequestMapping("/depts")
@RestController
public class DeptController {
    @GetMapping
    public Result list() { ... }

    @DeleteMapping
    public Result delete(Integer id) { ... }

    @PostMapping
    public Result add(@RequestBody Dept dept) { ... }

    @GetMapping("/{id}")
    public Result getInfo(@PathVariable Integer id) { ... }

    @PutMapping
    public Result change(@RequestBody Dept dept) { ... }
}
```

作用与讲解：
- 这些注解用于映射 HTTP 请求到 Controller 的方法上：
  - `@RequestMapping` 可声明类/方法的基础路径和方法（method），
  - `@GetMapping` 等为语义化快捷注解，默认只匹配对应的 HTTP 方法（GET/POST/DELETE/PUT）。

注意事项与最佳实践：
- 方法参数绑定来源多样（路径、查询参数、请求体、表单），要根据场景使用 `@PathVariable`、`@RequestParam`、`@RequestBody`。
- 对于 DELETE/PUT 的请求，浏览器表单默认只支持 GET/POST，前端或测试需设置相应 HTTP 方法或使用隐藏字段/JS 发起请求。

---

### `@Slf4j`（Lombok）

示例原文：
```java
@Slf4j
public class EmpController {
    @Autowired
    private EmpService empService;

    @GetMapping
    public Result page(EmpQueryParam empQueryParam){
        log.info("分页查询:{}",empQueryParam);
        // ...
    }
}
```

来源：lombok.extern.slf4j.Slf4j

作用与讲解：
- Lombok 帮你隐式生成一个名为 `log` 的 `org.slf4j.Logger` 实例，免去手动创建 Logger 的样板代码。

注意事项与最佳实践：
- 确保项目引入 Lombok 并在 IDE 中启用注解处理器；否则会出现编辑器报红。
- 日志参数化写法（`log.info("msg {}", arg)`）优于字符串拼接，能避免在日志级别被禁用时造成不必要的字符串拼接开销。

---

### `@Autowired`

示例原文（字段注入）：
```java
@RestController
public class DeptController {
    @Autowired
    private DeptService deptService;
    // ...
}
```

示例原文（构造器注入）：
```java
public EmpServiceImpl(EmpMapper empMapper, EmpExprMapper empExprMapper) {
    this.empExprMapper = empExprMapper;
    this.empMapper = empMapper;
}
```

来源：org.springframework.beans.factory.annotation.Autowired

作用与讲解：
- 注入 Spring 管理的 Bean。Spring 会按类型（默认）或按名称（结合 `@Qualifier`）注入依赖。

注意事项与最佳实践：
- 优先使用构造器注入（便于單元測試和不可变设计），字段注入适用于快速示例或框架代码。
- 当注入多个实现时使用 `@Qualifier` 或 `@Primary` 明确选择。

---

### `@RequestBody` / `@PathVariable`

示例原文：
```java
@PostMapping
public Result add(@RequestBody Dept dept){
    log.info("新增部门{}",dept);
    deptService.add(dept);
    return Result.success();
}

@GetMapping("/{id}")
public Result getInfo(@PathVariable Integer id){
    log.info("根据id查询部门{}",id);
    Dept dept = deptService.getById(id);
    return Result.success(dept);
}
```

作用与讲解：
- `@RequestBody`：将请求体（通常是 JSON）反序列化为 Java 对象，需配置合适的 HttpMessageConverter（Spring Boot 默认支持 Jackson）。
- `@PathVariable`：从 URL 路径中提取占位符并绑定到方法参数。

注意事项与最佳实践：
- `@RequestBody` 用于整体验证时配合 `@Valid` 使用，并在 ControllerAdvice 中统一处理 MethodArgumentNotValidException。
- 注意请求 Content-Type（如 `application/json`）必须和服务器端期望匹配。

---

### `@RequestParam`

示例原文（假设场景）：
```java
@GetMapping("/search")
public Result search(@RequestParam(name = "q", required = false) String keyword,
                     @RequestParam(defaultValue = "1") int page) {
    // 根据 keyword 搜索，page 为分页页码
}
```

来源：org.springframework.web.bind.annotation.RequestParam

作用与讲解：
- 将 HTTP 请求的查询参数或表单参数绑定到方法参数上。常用于 GET 请求的查询字符串，例如 `/emps?name=张三&page=2`。
- 常用属性：
  - `name` / `value`：参数名
  - `required`：是否必须，默认 true
  - `defaultValue`：当参数缺失时使用的默认值（设置后 `required` 将被忽略）

注意事项与最佳实践：
- 对于可选参数应该设置 `required = false` 或 `defaultValue`，以避免参数缺失造成 400 错误。
- 对于复杂对象或大量参数，考虑使用封装的 DTO（例如 `EmpQueryParam`）将参数绑定为对象，便于维护与验证。


### MyBatis 注解：`@Mapper`, `@Select`, `@Insert`, `@Update`, `@Delete`, `@Options`

示例原文（`DeptMapper`）：
```java
//自动创建一个实现类，并且交给IOC容器处理
@Mapper
public interface DeptMapper {
    @Select("select id, name, create_time, update_time from dept order by update_time desc")
    List<Dept> findAll();

    @Delete("delete from dept where id = #{id}")
    void deleteById(Integer id);

    @Insert("insert into dept(name,create_time,update_time) values (#{name},#{createTime},#{updateTime})")
    void add(Dept dept);

    @Select("select id, name, create_time, update_time from dept where id = #{id}")
    Dept getById(Integer id);

    @Update("update dept set name = #{name},update_time = #{updateTime} where id = #{id}")
    void update(Dept dept);
}
```

示例原文（`EmpMapper` 的插入）：
```java
@Options(useGeneratedKeys = true,keyProperty = "id")
@Insert("insert into emp(username, name, gender, phone, job, salary, image, entry_date, dept_id, create_time, update_time)" +
        "values (#{username},#{name},#{gender},#{phone},#{job},#{salary},#{image},#{entryDate},#{deptId},#{createTime},#{updateTime});")
void insert(Emp emp);
```

来源：org.apache.ibatis.annotations.*

作用与讲解：
- `@Mapper`：标识接口为 MyBatis Mapper，MyBatis（或 Spring Boot 的 MyBatis Starter）会创建代理并注册为 Bean。
- `@Select/@Insert/@Update/@Delete`：在接口上直接写 SQL 语句，适用于简单、固定的 SQL。
- `@Options(useGeneratedKeys=true, keyProperty="id")`：插入后把数据库自动生成的主键设置回传入实体的 `id` 属性。

注意事项与最佳实践：
- 复杂或动态 SQL（含条件、分页、关联查询）建议放到 XML 中以便维护（本项目的 `EmpMapper.xml` 用于复杂的 list 查询）。
- 使用 `useGeneratedKeys` 时要确认数据库与驱动支持返回生成主键（MySQL 常见），对于 Oracle 可能需要其他方式（如 selectKey）。

---

### `@Service`

示例原文：
```java
@Service
public class DeptServiceImpl implements DeptService {
    @Autowired
    private DeptMapper deptMapper;
    // ...
}
```

来源：org.springframework.stereotype.Service

作用与讲解：
- 标注业务类，使其成为 Spring 管理的组件（Bean），语义上表示业务逻辑层。

注意事项与最佳实践：
- 将事务注解放在 Service 层通常比放在 DAO 层更合适，因为 Service 层可以组合多个 DAO 操作来形成业务事务边界。

---

### `@Transactional`

示例原文：
```java
@Transactional(rollbackFor = {Exception.class})//事务管理
@Override
public void save(Emp emp) {
    try {
        // 保存员工基本信息与经历
    } finally {
        // 记录日志
    }
}
```

以及日志示例：
```java
@Transactional(propagation = Propagation.REQUIRES_NEW)//在一个新的事务中运行
@Override
public void insertLog(EmpLog empLog) {
    empLogMapper.insert(empLog);
}
```

来源：org.springframework.transaction.annotation.Transactional

作用与讲解：
- 声明方法的事务边界：在方法执行期间开启事务，成功提交，异常时回滚（默认仅回滚 RuntimeException）。
- `propagation` 可控制事务传播行为（如 `REQUIRED`, `REQUIRES_NEW` 等）。

注意事项与最佳实践：
- 默认只对运行时异常回滚，若需对 checked exception 回滚，请用 `rollbackFor` 指定。
- `REQUIRES_NEW` 会开启一个新的事务并挂起当前事务，常用于写入审计日志以保证日志不受业务回滚影响。
- 事务注解基于代理实现（AOP），同类内部方法相互调用不会触发代理，导致注解失效；必要时改为同类对外调用或使用 AspectJ。

---

### Lombok 注解：`@Data`, `@NoArgsConstructor`, `@AllArgsConstructor`, `@Slf4j`

示例原文：
```java
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dept {
    private Integer id;
    private String name;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
```

来源：lombok.*

作用与讲解：
- `@Data` 自动生成 getter/setter、toString、equals、hashCode。
- `@NoArgsConstructor` 与 `@AllArgsConstructor` 分别生成无参构造与全参构造。
- `@Slf4j` 生成 `log` 对象（参见上文）。

注意事项与最佳实践：
- 在使用 Lombok 时确保 IDE 与构建工具都支持注解处理器，否则可能出现编译/IDE 报红；Lombok 会隐藏类的真实源代码，调试时留意生成的方法。

---

### `@DateTimeFormat`

示例原文：
```java
@DateTimeFormat(pattern = "yyyy-MM-dd")
LocalDate begin;

@DateTimeFormat(pattern = "yyyy-MM-dd")
LocalDate end;
```

来源：org.springframework.format.annotation.DateTimeFormat

作用与讲解：
- 用于将请求中的字符串参数解析为 java.time 类型（如 `LocalDate`）时指定格式，常用于 GET 查询参数绑定。

注意事项与最佳实践：
- 该注解用于请求参数绑定（URL 或表单），对 JSON 请求体中日期的解析应使用 Jackson 的配置（例如 `@JsonFormat` 或配置 ObjectMapper）。

---

### 测试注解：`@Test`, `@SpringBootTest`

示例原文：
```java
@SpringBootTest
class TliasWebManagementApplicationTests {
    @Test
    void contextLoads() {
    }
}
```

以及普通测试方法：
```java
@Test
public void testLog(){
    log.debug("开始计算...");
    // ...
}
```

来源：JUnit（`org.junit.jupiter.api.Test`）与 Spring Boot 测试（`org.springframework.boot.test.context.SpringBootTest`）

作用与讲解：
- `@Test` 标注单元/集成测试方法。
- `@SpringBootTest` 启动完整的 Spring 应用上下文，适用于集成测试。

注意事项与最佳实践：
- `@SpringBootTest` 启动上下文开销较大，单元测试建议使用 Mockito 等轻量方式或 `@WebMvcTest`/`@DataJpaTest` 等切片测试以提高速度。

---

## 结语与下一步

我已将注解的原文示例与详细讲解整合到本文件中。如果你希望我做进一步加工，可以选择：

- 将每个注解项加入官方文档链接和常见陷阱清单（我可添加链接与示例）
- 生成按“学习优先级”排序的 7 天学习计划
- 在源码中自动插入简短注释以提示用法（我可以生成 patch）

回复你想要的扩展，我将继续执行并保存到项目中。
