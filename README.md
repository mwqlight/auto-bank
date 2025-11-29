# auto-bank
Auto bank for banks.

## 项目概述
开发一个现代化、安全可靠的**智能银行全流程业务系统**，采用**SpringBoot 3.2 + Vue3 + TypeScript**技术栈，实现从客户开户到复杂金融交易的全业务闭环，满足银行级安全与性能要求，界面设计需体现金融专业性与现代化体验。

## 核心业务流程

为了帮助客户更好地理解和使用银行系统，我们提供了详细的[银行核心业务流程指南](BANK_PROCESS.md)，其中包括：

- 客户注册与登录流程
- 客户信息管理
- 账户创建与管理
- 存款、取款、转账等交易操作
- 交易记录查询

通过可视化流程图和详细说明，客户可以快速掌握银行系统的各项功能和操作方法。

## 技术架构

### 前端架构 (Vue3 + TypeScript)
- **框架**：Vue3 Composition API + TypeScript
- **状态管理**：Pinia (模块化设计)
- **路由**：Vue Router (动态路由+权限控制)
- **UI组件库**：基于Element Plus深度定制
- **可视化**：ECharts (数据大屏)
- **构建工具**：Vite (生产环境优化)

### 后端架构 (SpringBoot 3.2)
- **核心框架**：Spring Boot 3.2 + Spring Security
- **持久层**：Spring Data JPA
- **缓存**：Redis
- **安全框架**：Spring Security + JWT
- **文档**：SpringDoc OpenAPI 3.0

## 快速开始

### 后端服务启动
```bash
cd backend
mvn spring-boot:run
```

### 前端服务启动
```bash
cd frontend
npm install
npm run dev
```

访问 http://localhost:3000 使用银行系统。

## 安全特性
- JWT Token认证
- 密码加密存储
- 转账安全验证
- 操作日志记录

## 界面设计
- 响应式设计，支持多种设备
- 现代化的UI界面
- 清晰的数据展示
- 直观的操作流程
