

-----------------------现场未更新脚本-------------------------

alter table "Experts" alter  COLUMN  "HFactor"  type VARCHAR(64);

--专家增加排序字段 为了有20位专家总是显示在前20位
ALTER TABLE "Experts" ADD "Px" INT;
-- 冯晓琳pernr
10064152

orgeh
改成
--50252046  
--原本
50261009

--健康研究院
50142933
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

---创建收藏记录

create or REPLACE VIEW COLLECT_LIST	 as

SELECT  push."id" pushid,push."cotent",push."title",push."comentBy",u.username comentByC,collection."collectBy",collection."collectAt",'xxttsc' type1
		FROM "xacx_information_push"  push  
		  join xacx_information_push_collection collection 
		 	on push."id" = collection."comentId" 
			left join xacx_user u on push."comentBy" = u.pernr 
		
	 UNION
    

   select tech.id pushid,tech.cotent cotent,tech.title title,tech."createBy" comentBy,tu.username comentByC, techcoll."collectBy" collectBy,techcoll."collectAt" collectAt,'information' type1
		from xacx_tech_information_collection techcoll 
		 join xacx_tech_information tech on techcoll."informationId" = tech."id"
     left join xacx_user tu on tu.pernr = tech."createBy" 

  union 

   select sec."id" pushid,sec.cotent cotent,sec.title title,sec."createBy" comentBy,su.username comentByC ,tesec."collectBy" collectBy,tesec."collectAt" collectAt,'sections' type1 from xacx_tech_sections_collection tesec
    join xacx_tech_sections sec on sec."id" = tesec."sectionId"
    left join xacx_user su on su.pernr = sec."createBy"
