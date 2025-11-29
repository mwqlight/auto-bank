-- 插入默认用户
INSERT INTO users (username, password, name, role, status) VALUES 
('admin', '$2a$10$8K1p/a0dURXAm7QiTRqNa.E3YPWsKGpbD6vqGTkFn7zaVfz8rUrxC', '管理员', 'ADMIN', 0);

-- 插入测试客户
INSERT INTO customers (customer_number, name, id_card_number, phone, email, address, risk_level, customer_type, status) VALUES 
('CUST000000000001', '张三', '110101199003077532', '13800000001', 'zhangsan@example.com', '北京市朝阳区', 0, 0, 0),
('CUST000000000002', '李四', '110101199003077545', '13800000002', 'lisi@example.com', '上海市浦东新区', 0, 0, 0);

-- 插入测试账户
INSERT INTO accounts (account_number, customer_id, account_type, balance, currency, status) VALUES 
('ACC000000000001', 1, 0, 10000.00, 'CNY', 0),
('ACC000000000002', 2, 0, 5000.00, 'CNY', 0);