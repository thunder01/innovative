

-----------------------现场未更新脚本-------------------------

alter table "Experts" alter  COLUMN  "HFactor"  type VARCHAR(64);

--专家增加排序字段 为了有20位专家总是显示在前20位
ALTER TABLE "Experts" ADD "Px" INT;
-- 冯晓琳pernr
10064152

orgeh
改成
--50252046  
原本
50261009
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


----关于评论的树查询
WITH RECURSIVE T ("cotent","comentBy","comentAt","approuverNum","title","id","pid", PATH,TYPE, DEPTH)  AS (
    SELECT "cotent","comentBy","comentAt","approuverNum","title","id","pid", "id"||'/' AS PATH,"id" AS TYPE ,1 AS DEPTH
    FROM xacx_information_push
    WHERE "pid"  is null or  pid =''

    UNION ALL

    SELECT  D."cotent",D."comentBy",D."comentAt",D."approuverNum",D."title",D."id",D."pid",T.PATH ||'/'|| D."id",T."id" AS TYPE, T.DEPTH + 1 AS DEPTH
    FROM xacx_information_push D 
    JOIN T ON D."pid" = T."id"
    )
    SELECT "cotent","comentBy","comentAt","approuverNum","title","id","pid", PATH,TYPE, DEPTH FROM T
ORDER BY PATH


---三期未更新
--- 给信息推特创建视图
CREATE MATERIALIZED VIEW v_xacx_information_push AS

(select * from (WITH RECURSIVE T ("cotent","comentBy","comentAt","approuverNum","title","id","pid", PATH,TYPE, DEPTH)  AS (
    SELECT "cotent","comentBy","comentAt","approuverNum","title","id","pid", "id"||'/' AS PATH,"id" AS TYPE ,1 AS DEPTH
    FROM xacx_information_push
    WHERE "pid"  is null or  pid =''

    UNION ALL

    SELECT  D."cotent",D."comentBy",D."comentAt",D."approuverNum",D."title",D."id",D."pid",T.PATH ||'/'|| D."id",T."id" AS TYPE, T.DEPTH + 1 AS DEPTH
    FROM xacx_information_push D 
    JOIN T ON D."pid" = T."id"
    )
    SELECT "cotent","comentBy","comentAt","approuverNum","title","id","pid", PATH,TYPE, DEPTH FROM T
ORDER BY PATH,"comentAt" DESC)e)
