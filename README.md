# Sensitive Data Masking for Spring Boot
# Spring Boot 敏感数据自动脱敏工具

[![Maven Central](https://img.shields.io/maven-central/v/io.github.yourname/sensitive-data-masking-spring-boot-starter)](https://search.maven.org/artifact/io.github.yourname/sensitive-data-masking-spring-boot-starter)  
[![License](https://img.shields.io/badge/License-Apache_2.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

> 🌐 **English below** | 中文在上，英文在下  
> ✅ 一行注解，自动隐藏手机号、身份证等敏感信息  
> 🔒 符合 GDPR / 网络安全法 / 个人信息保护要求

---

## 🎯 一句话介绍 / One-Sentence Overview

这是一个 **Spring Boot 插件**，让你在返回 JSON 数据时，**自动把手机号、身份证、邮箱等敏感字段“打码”**，防止泄露用户隐私。  
This is a **Spring Boot starter** that automatically **masks sensitive fields** (like phone numbers, ID cards, emails) in your JSON API responses to protect user privacy.

---

## ❓ 什么是“脱敏”？Why Do We Need This?

想象一下：
- 你的系统返回用户信息：`{"phone": "13800138000", "idCard": "110101199003071234"}`
- 如果这个接口被前端日志记录、被黑客抓包、被测试人员看到……**用户隐私就泄露了！**

✅ **脱敏（Data Masking）** 就是在展示数据时，**把关键部分替换成 `*`**，比如：
- 手机号 → `138****8000`
- 身份证 → `110101**********1234`

这样既不影响功能（比如客服仍能看到后四位），又保护了隐私！

> 💡 这不是加密！这是**展示层的隐藏**，原始数据在数据库里仍是完整的。

---

## 🚀 快速开始 / Quick Start

### 第一步：添加依赖 / Add Dependency

#### Maven
```maven
<dependency>
    <groupId>io.github.yourname</groupId>
    <artifactId>sensitive-data-masking-spring-boot-starter</artifactId>
    <version>1.0.0</version>
</dependency>
 ```

#### Gradle
```gradle
implementation 'io.github.liangalong:sensitive-data-masking-spring-boot-starter:1.0.0'
```  


### 第二步：给字段加注解 / Annotate Your Fields

```java
public class User {
    private String name;

    // 自动脱敏手机号
    @Mask(type = MaskType.MOBILE)
    private String phone;

    // 自动脱敏身份证
    @Mask(type = MaskType.ID_CARD)
    private String idCard;

    // 自定义脱敏：保留前3位 + 后4位
    @Mask(type = MaskType.CUSTOM, customPattern = "3,4")
    private String orderNo; // "ORD123456789" → "ORD*****6789"
}
 ```
### 第三步：正常写 Controller / Write Your REST API

```java
@RestController
public class UserController {
    @GetMapping("/user")
    public User getUser() {
        User u = new User();
        u.setName("张三");
        u.setPhone("13800138000");
        u.setIdCard("110101199003071234");
        u.setOrderNo("ORDER20250101");
        return u;
    }
}
```
#### 返回结果 / Response
```json
{
  "name": "张三",
  "phone": "138****8000",
  "idCard": "110101**********1234",
  "orderNo": "ORD*****0101"
}
```
## ✅ 无需手动处理！框架自动完成脱敏！
#### 你可以在 application.yml 中全局控制是否启用脱敏：
```yaml
sensitive-mask:
  enabled: true  # 默认为 true；设为 false 可临时关闭（如开发环境）
```

## 🛠️ 适合场景 / When to Use

- 开发/测试环境想看完整数据 → 设为 `false`
- 生产环境必须脱敏 → 保持 `true`

## 🧩 支持的脱敏类型 / Supported Mask Types

| 类型 / Type     | 示例输入 / Input          | 输出 / Output               | 说明 / Description                     |
|----------------|--------------------------|----------------------------|----------------------------------------|
| `MOBILE`       | `13800138000`            | `138****8000`              | 中国大陆手机号                         |
| `ID_CARD`      | `110101199003071234`     | `110101**********1234`     | 18位身份证                             |
| `EMAIL`        | `alice@example.com`      | `a***@example.com`         | 邮箱（保留首字母+域名）                |
| `BANK_CARD`    | `6222081234567890`       | `**** **** **** 7890`      | 银行卡号（每4位空格+仅显后4位）        |
| `CUSTOM`       | `ORDER20250101`          | `ORD*****0101`             | 自定义：`customPattern="3,4"` 表示保留前3后4 |

## 🔍 技术原理（小白也能懂） / How It Works (For Beginners)

1. 你用了 `@Mask` 注解 → 框架知道这个字段要“打码”
2. Spring Boot 返回 JSON 时，会调用 Jackson 库序列化对象
3. 我们的插件“劫持”了这个过程，在输出前把值替换成脱敏版本
4. 原始数据不变！数据库、日志、内部调用都还是完整数据，只在 API 响应中脱敏

> 🧠 类比：就像你在朋友圈发照片时，用贴纸盖住车牌号——照片本身没变，只是给别人看的时候做了处理。

## 📦 如何构建项目 / How to Build

```bash
git clone https://github.com/liangalong/sensitive-data-masking.git
cd sensitive-data-masking
mvn clean install
```

生成的 JAR 包可在本地 Maven 仓库使用。

## 🤝 贡献指南 / Contributing

#### 欢迎提交 Issue 或 Pull Request！

- 新增脱敏规则？
- 修复边界 case？
- 改进文档？

#### 请先 Fork 本项目，修改后提交 PR。

## 📜 许可证 / License

#### Apache License 2.0  
#### 详见 [LICENSE](./LICENSE) 文件。

## ✨ 让隐私保护变得简单，从一行注解开始。  
## ✨ Make data privacy simple — with just one annotation.

