

-----------------------现场未更新脚本-------------------------

alter table "Experts" alter  COLUMN  "HFactor"  type VARCHAR(64);

--专家增加排序字段 为了有20位专家总是显示在前20位
ALTER TABLE "Experts" ADD "Px" INT;