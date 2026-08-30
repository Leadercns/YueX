# ⚠️ 项目免责声明

> **本开源项目仅供学习、研究及技术交流使用。**

## 🛠 项目配置

| 项目 | 版本 |
|------|------|
| **JDK** | 21 |
| **Java** | 21 |

---

## 🔒 安全警告

**请务必阅读并遵守以下条款：**

1. **生产环境禁止直接使用**  
   本项目代码仅为演示和教学目的编写，**未经任何安全加固**，直接部署到公网或生产环境存在严重安全隐患。

2. **密码必须加密**  
   若您计划将本项目用于任何实际场景，**必须**对用户密码进行加密存储（如使用 BCrypt、Argon2 等安全哈希算法），本项目当前采用明文存储，**极不安全**。

3. **必须自行实现 JWT 鉴权**  
   本项目使用的 Token 仅为简易随机字符串，**不具备生产级安全性**。正式使用时，请务必集成 JWT（JSON Web Token）或 OAuth2 等成熟鉴权方案。

4. **自行承担风险**  
   因使用本项目（包括但不限于二次开发、部署、传播）导致的任何数据泄露、系统入侵、法律纠纷等后果，**均由使用者自行承担，与原作者无关**。

---

## 🔧 二次开发说明

- ✅ 欢迎 Fork、Clone、修改、再分发
- ✅ 鼓励学习其中的代码逻辑和设计思路
- ❌ 请勿将本项目代码直接用于商业项目
- ❌ 请勿移除或篡改本免责声明

---

## 📄 许可证

本项目采用 [MIT License](LICENSE)，您几乎可以对本项目做任何操作，但原作者不提供任何形式的担保或责任。

---

**最后强调：代码随便改，出事自己扛。🙏**




# ⚠️ Project Disclaimer

> **This open-source project is intended for learning, research, and technical exchange purposes only.**

## 🛠 Project Configuration

| Item | Version |
|------|---------|
| **JDK** | 21 |
| **Java** | 21 |

---

## 🔒 Security Warning

**Please read and comply with the following terms:**

1. **DO NOT use this project directly in production.**  
   This code is written for demonstration and educational purposes only. It has **not undergone any security hardening**. Deploying it to a public network or production environment poses serious security risks.

2. **Passwords MUST be encrypted.**  
   If you plan to use this project in any real-world scenario, you **must** implement secure password hashing (e.g., BCrypt, Argon2, or similar). This project currently stores passwords in plain text – **this is highly insecure**.

3. **You MUST implement proper JWT authentication.**  
   The token mechanism used in this project is a simple random string and **is not production‑grade**. For official use, please integrate JWT (JSON Web Token) or OAuth2, along with appropriate security measures.

4. **You bear all risks.**  
   Any consequences arising from the use of this project – including but not limited to data breaches, system intrusions, or legal disputes – are **solely your responsibility**. The original author assumes no liability.

---

## 🔧 Modification & Redistribution

- ✅ You are welcome to fork, clone, modify, and redistribute.
- ✅ Learning from the code structure and logic is encouraged.
- ❌ Do not use this code directly in commercial projects without proper security hardening.
- ❌ Do not remove or alter this disclaimer.

---

## 📄 License

This project is licensed under the [MIT License](LICENSE). You are free to do almost anything with it, but the author provides no warranty or liability of any kind.

---

**Final reminder: Feel free to modify the code, but you are solely responsible for your own modifications and usage. 🙏**
