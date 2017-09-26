

-----------------------现场未更新脚本-------------------------

alter table "Experts" alter  COLUMN  "HFactor"  type VARCHAR(64);

--专家增加排序字段 为了有20位专家总是显示在前20位
ALTER TABLE "Experts" ADD "Px" INT;

--增加一个视图，方便查询sid
CREATE MATERIALIZED VIEW v_xacx_user_org AS
select * from (WITH RECURSIVE T ("orgeh","stext", PATH, DEPTH)  AS (
    SELECT "orgeh","stext", "stext"||'/' AS PATH, 1 AS DEPTH
    FROM xacx_user_organization
    WHERE "up_orgeh"  is null

    UNION ALL

    SELECT  D."orgeh",D."stext" ,T.PATH ||'/'|| D."stext", T.DEPTH + 1 AS DEPTH
    FROM xacx_user_organization D
    JOIN T ON D."up_orgeh" = T."orgeh"
    )
    SELECT "orgeh","stext", PATH, DEPTH FROM T
ORDER BY PATH) e1


--
select u.pernr,u.orgeh,e.PATH from xacx_user u left join  v_xacx_user_org e on u.orgeh = e.orgeh where pernr = '10046490'
