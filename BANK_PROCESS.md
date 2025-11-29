# 银行核心业务流程指南

本文档为客户提供了银行系统核心业务流程的可视化操作指南，帮助客户更好地理解和使用银行服务。

## 银行核心业务流程图

```mermaid
graph TD
    A[客户] --> B{登录系统}
    B -->|新用户| C[注册账户]
    B -->|已有账户| D[进入主界面]
    C --> D
    
    D --> E[主控制台]
    E --> F[客户管理]
    E --> G[账户管理]
    E --> H[交易管理]
    
    F --> F1[查看客户信息]
    F --> F2[更新个人信息]
    
    G --> G1[创建新账户]
    G --> G2[查看账户列表]
    G --> G3[查看账户详情]
    G --> G4[账户操作]
    
    G4 --> G41[存款]
    G4 --> G42[取款]
    G4 --> G43[转账]
    
    H --> H1[查看交易记录]
    H --> H2[查看交易详情]
    
    G1 --> G11[选择客户]
    G1 --> G12[选择账户类型]
    G1 --> G13[确认开户]
    G13 --> G2
    
    G41 --> G411[选择账户]
    G41 --> G412[输入存款金额]
    G41 --> G413[确认存款]
    G413 --> G2
    
    G42 --> G421[选择账户]
    G42 --> G422[输入取款金额]
    G42 --> G423[确认取款]
    G423 --> G2
    
    G43 --> G431[选择转出账户]
    G43 --> G432[选择转入账户]
    G43 --> G433[输入转账金额]
    G43 --> G434[确认转账]
    G434 --> H1
    
    style A fill:#e1f5fe
    style B fill:#f3e5f5
    style C fill:#fce4ec
    style D fill:#e8f5e8
    style E fill:#fff3e0
    style F fill:#e0f2f1
    style G fill:#e0f2f1
    style H fill:#e0f2f1
    style F1 fill:#b2dfdb
    style F2 fill:#b2dfdb
    style G1 fill:#b2dfdb
    style G2 fill:#b2dfdb
    style G3 fill:#b2dfdb
    style G4 fill:#b2dfdb
    style G41 fill:#80cbc4
    style G42 fill:#80cbc4
    style G43 fill:#80cbc4
    style H1 fill:#b2dfdb
    style H2 fill:#b2dfdb
```

## 详细操作说明

### 1. 登录/注册流程
- **新用户**: 首次使用系统需要进行注册，填写基本信息创建账户
- **已有账户用户**: 直接使用用户名和密码登录系统

### 2. 客户管理
- **查看客户信息**: 查看个人基本信息，包括姓名、身份证号、联系方式等
- **更新个人信息**: 修改个人联系信息，如电话号码、邮箱地址等

### 3. 账户管理
- **创建新账户**: 
  1. 选择已有的客户信息
  2. 选择账户类型（储蓄账户、支票账户、定期账户等）
  3. 确认开户，系统将生成唯一的账户号码
  
- **查看账户列表**: 显示所有账户的概览信息，包括账户号码、类型、余额等
- **查看账户详情**: 查看单个账户的详细信息和状态
- **账户操作**:
  - **存款**: 向指定账户存入资金
  - **取款**: 从指定账户提取资金
  - **转账**: 在不同账户之间转移资金

### 4. 交易管理
- **查看交易记录**: 浏览账户的所有交易历史
- **查看交易详情**: 查看单笔交易的详细信息，包括交易时间、金额、类型等

## 颜色说明
- <span style="background-color:#e1f5fe;padding:2px 5px;">蓝色</span>: 客户起点
- <span style="background-color:#f3e5f5;padding:2px 5px;">紫色</span>: 登录决策点
- <span style="background-color:#fce4ec;padding:2px 5px;">粉色</span>: 注册流程
- <span style="background-color:#e8f5e8;padding:2px 5px;">绿色</span>: 主界面
- <span style="background-color:#fff3e0;padding:2px 5px;">橙色</span>: 主控制台
- <span style="background-color:#e0f2f1;padding:2px 5px;">青色</span>: 主要功能模块
- <span style="background-color:#b2dfdb;padding:2px 5px;">浅青色</span>: 子功能
- <span style="background-color:#80cbc4;padding:2px 5px;">深青色</span>: 具体操作

通过以上流程图和说明，客户可以清晰地了解银行系统的操作流程，便于快速上手使用各项银行服务。