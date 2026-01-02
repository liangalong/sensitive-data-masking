package com.example.sensitivemask.integration;

import com.example.sensitivemask.annotation.Mask;
import com.example.sensitivemask.enums.MaskType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.Data;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class SensitiveMaskIntegrationTest {

    @Data
    public static class User {
        public String name;

        @Mask(type = MaskType.MOBILE)
        public String phone;

        @Mask(type = MaskType.EMAIL)
        public String email;

        // Jackson 需要无参构造（即使只序列化也建议加上）
        public User() {}

        public User(String name, String phone, String email) {
            this.name = name;
            this.phone = phone;
            this.email = email;
        }

    }

    private JacksonTester<User> json; // 移除 @Autowired 注解

    // 前置方法：手动初始化 JacksonTester
    @BeforeEach
    void setUp() {
        // 初始化 JacksonTester，传入 ObjectMapper（Spring 上下文的 ObjectMapper 或默认实例）
        JacksonTester.initFields(this, new ObjectMapper());
    }

    @Test
    public void shouldMaskSensitiveFieldsInJson() throws Exception {
        // 1. 准备测试数据：创建一个包含真实敏感信息的User对象（模拟业务中的真实用户数据）
        // 姓名：Alice，手机号：13800138000，邮箱：alice@test.com
        User testUser = new User("Alice", "13800138000", "alice@test.com");

        // 控制台打印：原始用户数据（脱敏前），让你看到初始值
        System.out.println("===== 脱敏前的原始用户数据 =====");
        System.out.println("姓名：" + testUser.name);
        System.out.println("原始手机号：" + testUser.phone);
        System.out.println("原始邮箱：" + testUser.email);
        System.out.println("=================================\n");

        // 2. 核心操作：将User对象序列化为JSON字符串（这一步会触发我们自定义的脱敏序列化器）
        // json.write(对象)：将Java对象转换为JSON格式数据
        // .getJson()：获取转换后的JSON字符串内容
        String convertedJsonContent = json.write(testUser).getJson();

        // 控制台打印：序列化后的完整JSON内容（脱敏后），直观看到脱敏结果
        System.out.println("===== 脱敏后的完整JSON内容 =====");
        System.out.println("JSON字符串：" + convertedJsonContent);
        System.out.println("=================================\n");

        // 3. 结果验证：断言JSON字符串中包含预期的脱敏后内容（验证脱敏功能是否生效）
        // assertThat(实际结果).contains(预期内容)：判断实际结果中是否包含指定的预期内容
        System.out.println("===== 开始验证脱敏结果是否符合预期 =====");
        // 验证1：手机号是否被正确脱敏（13800138000 → 138****8000）
        assertThat(convertedJsonContent).contains("138****8000");
        System.out.println("手机号脱敏验证通过：13800138000 → 138****8000");

        // 验证2：邮箱是否被正确脱敏（alice@test.com → a***@test.com）
        assertThat(convertedJsonContent).contains("a***@test.com");
        System.out.println("邮箱脱敏验证通过：alice@test.com → a***@test.com");

        // 验证3：普通姓名字段是否保持不变（未加脱敏注解，应该还是原始值Alice）
        assertThat(convertedJsonContent).contains("Alice");
        System.out.println("姓名字段验证通过：未脱敏，保持原始值 Alice");
        System.out.println("=================================\n");

        // 最终控制台提示：测试全部通过
        System.out.println("所有测试验证均通过，脱敏功能正常生效！");
    }


    void manualInputAndMaskResult() {
        Scanner scanner = new Scanner(System.in);
        User user = new User();

        System.out.print("请输入姓名：");
        user.setName(scanner.nextLine());
        System.out.println("-----------------------------");

        System.out.print("请输入需要脱敏的手机号（11位数字，例如13800138000）：");
        user.setPhone(scanner.nextLine());
        System.out.println("-----------------------------");

        System.out.print("请输入需要脱敏的邮箱（例如alice@test.com）：");
        user.setEmail(scanner.nextLine());
        System.out.println("-----------------------------");

        // 关键：创建并配置 ObjectMapper
        ObjectMapper mapper = JsonMapper.builder()
                .build();
        // 必须注册你的脱敏逻辑！
        // 假设你有一个自定义 Module，比如 SensitiveDataMaskingModule
        // mapper.registerModule(new SensitiveDataMaskingModule());

        // 如果你的脱敏是通过 @JsonComponent 实现的，
        // 且该组件是一个 JsonSerializer，你需要手动注册它：
        // 例如：
        // SimpleModule module = new SimpleModule();
        // module.addSerializer(String.class, new YourMaskingSerializer());
        // mapper.registerModule(module);

        try {
            String json = mapper.writeValueAsString(user); // ← 这里才会触发脱敏！
            System.out.println("===== 脱敏后的完整JSON内容 =====");
            System.out.println(json);
        } catch (Exception e) {
            System.err.println("序列化失败: " + e.getMessage());
            e.printStackTrace();
        }

        scanner.close();
        System.out.println("手动输入脱敏测试完成！");
    }

    public static void main(String[] args) {
        new SensitiveMaskIntegrationTest().manualInputAndMaskResult();
    }
}