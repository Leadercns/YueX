# YueX - 轻量级多级权限 API 管理平台

> **单租户 · 三层角色 · 邀请注册 · IP 安全验证**


## 📖 项目介绍

### 这个东西是什么？

**YueX** 是一个基于 **Spring Boot 4.1.1 + JDK 21** 构建的**轻量级多级权限 API 管理平台**，采用 **三层角色体系**：

```
👑 管理员（Admin）
    │  管理开发者账号、生成邀请码、审核注册
    ▼
🧑‍💻 开发者（Developer）
    │  拥有自己的 API 空间，管理下属用户
    ▼
👤 终端用户（User）
    │  通过 API Key 调用开发者提供的接口
```

**简单说：管理员管开发者，开发者管自己的用户。**

### 它能干什么？

| 角色 | 已实现功能 |
|------|-----------|
| **👑 管理员** | 登录、注册（需邀请码）、Token 自动续期（24h）、账号封禁检测 |
| **🧑‍💻 开发者** | 登录（含 IP 变化安全问题验证）、注册（独立）、账号封禁检测 |
| **👤 用户** | 登录/注册、获取用户列表、获取指定用户信息、封禁/解封、签到 |

### 核心功能

- ✅ **邀请码注册制**（防止恶意注册）
- ✅ **三层角色隔离**（管理员 → 开发者 → 用户）
- ✅ **Token 认证**（登录后 24 小时有效，自动续期）
- ✅ **开发者 IP 安全验证**（陌生 IP 登录需回答安全问题）
- ✅ **账号状态管理**（封禁/启用）
- ✅ **独立 ID 生成**（管理员ID、开发者ID、用户ID）
- ✅ **用户完整 CRUD + 签到**（开发者管理自己的用户）
- ✅ **重置开发者 ID**（开发者可重新生成身份标识）

### 适用场景

- 🚀 小型 SaaS 平台的用户管理模块
- 🔐 需要"开发者-用户"分层的 API 服务平台
- 🎓 学习 Spring Boot 多角色权限控制
- 🧩 快速搭建内部 API 管理后台

### 技术栈

| 技术 | 版本 |
|------|------|
| Java | 21 |
| Spring Boot | 4.1.1 |
| MyBatis | 4.0.1 |
| MySQL | 8.0+ |
| Maven | 3.9+ |
| Lombok | 最新 |

### 项目定位

- **架构模式**：单租户（Singleton Tenant）
- **角色体系**：三层（管理员 → 开发者 → 用户）
- **部署形态**：单体应用（Monolithic）


## 🚀 项目部署

### 环境要求

- JDK 21+
- MySQL 8.0+
- Maven 3.9+
- Git

### 第一步：克隆项目

```bash
git clone https://github.com/Leadercns/YueX.git
cd YueX
```

### 第二步：创建数据库

```sql
CREATE DATABASE yuex DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

### 第三步：修改配置文件

打开 `src/main/resources/application.yml`：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/yuex?useSSL=false&serverTimezone=Asia/Shanghai
    username: root
    password: 你的数据库密码
    driver-class-name: com.mysql.cj.jdbc.Driver
```

### 第四步：建表

SQL文件我会放在项目SQL文件夹里面的，直接导入就行。

### 第五步：插入初始管理员

```sql
INSERT INTO admins (username, password, State, ICode, ICodeS) 
VALUES ('admin', '123456', 0, 'INITCODE', 0);
```

### 第六步：打包启动

```bash
mvn clean package -DskipTests
java -jar target/YueX-*.jar
```

### 第七步：测试接口
### 管理员接口（/admin）
| 功能 | 方法 | 接口 | 参数 | 说明 |
|------|------|------|------|------|
| 登录 | POST | `/admin/Login` | `username`, `password` | 返回 token |
| 注册 | POST | `/admin/Register` | `username`, `password`, `code` | 需要邀请码 |
### 开发者接口（/）
| 功能 | 方法 | 接口 | 参数 | 说明 |
|------|------|------|------|------|
| 登录 | POST | `/Login` | `username`, `password`, `Security_answer`（可选） | IP变化时需回答问题 |
| 注册 | POST | `/Register` | `username`, `password`, `answer` | 独立注册，无需邀请码 |
### 用户接口（/api）
| 功能 | 方法 | 接口 | 参数 | 说明 |
|------|------|------|------|------|
| 用户登录 | POST | `/api/Login` | `username`, `password` | 返回用户 token |
| 用户注册 | POST | `/api/Register` | `username`, `password`, `dev_id` | 需提供开发者ID |
| 获取用户列表 | GET | `/api/Users` | 无（需登录） | 当前开发者下的所有用户 |
| 获取指定用户 | GET | `/api/User/{userid}` | 路径参数 | 查看某个用户详情 |
| 封禁/解封用户 | PUT | `/api/User/State` | `userid`, `state` | 修改用户状态 |
| 用户签到 | POST | `/api/User/Sign` | 无（需登录） | 签到记录/积分 |
| 重置开发者ID | PUT | `/api/Developer/ResetId` | 无（需登录） | 重新生成开发者自己的ID |


### 接口示例

```bash
# 管理员登录
curl -X POST http://localhost:8080/admin/Login \
  -d "username=admin&password=123456"

# 管理员注册（需要邀请码）
curl -X POST http://localhost:8080/admin/Register \
  -d "username=test&password=123456&code=INITCODE"

# 开发者注册
curl -X POST http://localhost:8080/Register \
  -d "username=dev01&password=123456&answer=我的宠物名字"

# 开发者登录
curl -X POST http://localhost:8080/Login \
  -d "username=dev01&password=123456"

# 用户登录
curl -X POST http://localhost:8080/api/Login \
  -d "username=user1&password=123456"

# 获取用户列表（需携带用户token）
curl -X GET http://localhost:8080/api/Users \
  -H "token: 你的用户token"
```


## 📂 项目结构

```
src/main/java/cn/levaer/
├── Controller/
│   ├── AdminController.java      # 管理员接口
│   ├── DeveController.java       # 开发者接口
│   └── UserController.java       # 用户接口
├── Service/
│   ├── AdminService.java         # 管理员服务接口
│   ├── DeveService.java          # 开发者服务接口
│   ├── UserService.java          # 用户服务接口
│   └── Impl/
│       ├── AdminServiceImpl.java # 管理员服务实现
│       ├── DeveServiceImpl.java  # 开发者服务实现
│       └── UserServiceImpl.java  # 用户服务实现
├── Mapper/
│   ├── AdminMapper.java          # 管理员数据访问
│   ├── DeveMapper.java           # 开发者数据访问
│   └── UserMapper.java           # 用户数据访问
└── Tool/
    └── Result.java               # 统一响应结果
```


## 📄 免责声明

> **本开源项目仅供学习、研究及技术交流使用。**

### ⚠️ 安全警告

1. **生产环境禁止直接使用** — 本项目未经安全加固，直接部署存在严重安全隐患。

2. **密码必须加密** — 当前为明文存储，正式使用请集成 BCrypt 等加密方案。

3. **必须自行实现 JWT 鉴权** — 当前 Token 为简易随机串，生产环境请替换为 JWT。

4. **使用者自行承担风险** — 因使用本项目导致的任何后果，与原作者无关。

### 📝 二开说明

- ✅ 欢迎 Fork、Clone、修改、再分发
- ❌ 请勿将本项目代码直接用于商业项目（未经安全加固）
- ❌ 请勿移除或篡改本免责声明

### 📄 许可证

本项目采用 [MIT License](LICENSE)，原作者不提供任何形式的担保或责任。


**最后强调：代码随便改，出事自己扛。🙏**

---

**如果这个项目对你有帮助，欢迎点个 Star ⭐ 支持一下！**
