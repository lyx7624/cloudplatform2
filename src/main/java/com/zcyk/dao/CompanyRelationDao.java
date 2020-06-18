package com.zcyk.dao;

import com.zcyk.entity.CompanyRelation;
import org.apache.ibatis.annotations.*;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 公司关系表(CompanyRelation)表数据库访问层
 *
 * @author makejava
 * @since 2020-05-29 17:02:45
 */
public interface CompanyRelationDao extends Mapper<CompanyRelation> {


    /*查找企业所有关系 company_id or relation_id 1正在的关系 2需要处理的审核请求 3审核不通过 4离开 5撤销*/
    @Select("select cr.*,c.code company_code,c.address company_address,c.name relation from company_relation cr " +
            "inner join company c on c.id = cr.relation_id " +
            "where cr.company_id=#{company_id}  and cr.status =#{status} and c.name like '%${search}%'")
    @Results(value = {
            @Result(property = "applicant_type",column = "applicant_type"),
            @Result(property = "relation",column = "relation_id",javaType = String.class,one = @One(select = "com.zcyk.dao.CompanyDao.getCompanyName")),
            @Result(property = "applicant",column = "applicant_id",javaType = String.class,one = @One(select = "com.zcyk.dao.UserDao.getNameById")),
            @Result(property = "audit",column = "audit_id",javaType = String.class,one = @One(select = "com.zcyk.dao.UserDao.getNameById")),
            @Result(property = "company_users",column = "relation_id",javaType = Integer.class,one = @One(select = "com.zcyk.dao.UserCompanyStationDao.gatAllUserCount")),
            @Result(property = "company_projects",column = "relation_id",javaType = Integer.class,one = @One(select = "com.zcyk.dao.CompanyProjectDao.getCompanyProjectCount"))
           /* @Result(property = "area_code",column = "area_code"),
            @Result(property = "city",column = "area_code",javaType = String.class,one = @One(select = "com.zcyk.dao.CountyDao.getHigher")),
            @Result(property = "province",column = "city",javaType = String.class,one = @One(select = "com.zcyk.dao.CountyDao.getHigher"))*/
    })
    List<CompanyRelation> selectByCompanyStatusType(@Param("company_id") String id, @Param("search") String search, @Param("status") Integer status);


    /*
    * 2.需要审核的企业
    * */
    /*查找企业所有关系 1正在的关系 2需要处理的审核请求 3审核不通过 4离开 5撤销*/
    @Select("select cr.*,c.code company_code,c.address company_address,c.name company_name from company_relation cr " +
            "inner join company c on c.id = cr.company_id " +
            "where cr.relation_id=#{relation_id} and cr.status =#{status} and c.name like '%${search}%'")
    @Results(value = {
            @Result(property = "applicant_type",column = "applicant_type"),
            @Result(property = "relation",column = "relation_id",javaType = String.class,one = @One(select = "com.zcyk.dao.CompanyDao.getCompanyName")),
            @Result(property = "applicant",column = "applicant_id",javaType = String.class,one = @One(select = "com.zcyk.dao.UserDao.getNameById")),
            @Result(property = "audit",column = "audit_id",javaType = String.class,one = @One(select = "com.zcyk.dao.UserDao.getNameById")),
            @Result(property = "company_users",column = "relation_id",javaType = Integer.class,one = @One(select = "com.zcyk.dao.UserCompanyStationDao.gatAllUserCount")),
            @Result(property = "company_projects",column = "relation_id",javaType = Integer.class,one = @One(select = "com.zcyk.dao.CompanyProjectDao.getCompanyProjectCount"))
           /* @Result(property = "area_code",column = "area_code"),
            @Result(property = "city",column = "area_code",javaType = String.class,one = @One(select = "com.zcyk.dao.CountyDao.getHigher")),
            @Result(property = "province",column = "city",javaType = String.class,one = @One(select = "com.zcyk.dao.CountyDao.getHigher"))*/
    })
    List<CompanyRelation> byRelation(@Param("relation_id") String relation_id, @Param("search") String search,@Param("status") int status);




    /*查找企业所有关系*/
    @Select("select cr.*,cr.relation_id,c.code company_code,c.address company_address from company_relation cr " +
            "inner join company c on c.id = cr.relation_id " +
            "where cr.company_id=#{company_id}  and c.name like '%${search}%' and cr.status = 1")
    @Results(value = {
            @Result(property = "applicant_type",column = "applicant_type"),
            @Result(property = "relation_id",column = "relation_id"),
            @Result(property = "company_name",column = "relation_id",javaType = String.class,one = @One(select = "com.zcyk.dao.CompanyDao.getCompanyName")),
            @Result(property = "relation",column = "relation_id",javaType = String.class,one = @One(select = "com.zcyk.dao.CompanyDao.getCompanyName")),
            @Result(property = "applicant",column = "applicant_id",javaType = String.class,one = @One(select = "com.zcyk.dao.UserDao.getNameById")),
            @Result(property = "audit",column = "audit_id",javaType = String.class,one = @One(select = "com.zcyk.dao.UserDao.getNameById")),
            @Result(property = "company_users",column = "relation_id",javaType = Integer.class,one = @One(select = "com.zcyk.dao.UserCompanyStationDao.gatAllUserCount")),
            @Result(property = "company_projects",column = "relation_id",javaType = Integer.class,one = @One(select = "com.zcyk.dao.CompanyProjectDao.getCompanyProjectCount"))
           /* @Result(property = "area_code",column = "area_code"),
            @Result(property = "city",column = "area_code",javaType = String.class,one = @One(select = "com.zcyk.dao.CountyDao.getHigher")),
            @Result(property = "province",column = "city",javaType = String.class,one = @One(select = "com.zcyk.dao.CountyDao.getHigher"))*/
    })
    List<CompanyRelation> selectByCompany(@Param("company_id") String company_id, @Param("search") String search);

    @Select("SELECT t.*,t.company_id as company_id2,c1.name as company_name,c2.name as relation_name,c1.code as company_code,c2.code as relation_code,c1.address as company_address,c2.address as relation_address from" +
            " (SELECT * FROM company_relation WHERE STATUS=1 AND company_id=#{company_id}" +
            " UNION ALL " +
            " SELECT * FROM company_relation WHERE STATUS=1 AND relation_id=#{company_id}) as t" +
            " left join"+
            " company c1 on c1.id=t.company_id" +
            " left join" +
            " company c2 on c2.id=t.relation_id"
            )
    @Results(value = {
            // @Result(property = "company_name",column = "company_id",javaType = String.class,one = @One(select = "com.zcyk.dao.CompanyDao.getCompanyName")),
            // @Result(property = "relation_name",column = "relation_id",javaType = String.class,one = @One(select = "com.zcyk.dao.CompanyDao.getCompanyName")),
            @Result(property = "applicant",column = "applicant_id",javaType = String.class,one = @One(select = "com.zcyk.dao.UserDao.getNameById")),
            @Result(property = "audit",column = "audit_id",javaType = String.class,one = @One(select = "com.zcyk.dao.UserDao.getNameById")),
            @Result(property = "company_users",column = "company_id2",javaType = Integer.class,one = @One(select = "com.zcyk.dao.UserCompanyStationDao.gatAllUserCount")),
            @Result(property = "company_projects",column = "company_id2",javaType = Integer.class,one = @One(select = "com.zcyk.dao.CompanyProjectDao.getCompanyProjectCount"))
            })
    List<CompanyRelation> selectNoCheck(@Param("company_id") String company_id,@Param("search") String search);

    @Select("SELECT t.*,t.company_id as company_id2,c1.name as company_name,c2.name as relation_name,c1.code as company_code,c2.code as relation_code,c1.address as company_address,c2.address as relation_address from" +
            " (SELECT * FROM company_relation WHERE flow_status in (0,5) AND company_id=#{company_id}" +
            " UNION ALL " +
            " SELECT * FROM company_relation WHERE flow_status in (0,5) AND relation_id=#{company_id}) as t" +
            " left join"+
            " company c1 on c1.id=t.company_id" +
            " left join" +
            " company c2 on c2.id=t.relation_id"
            )
    @Results(value = { //@Result(property = "company_name",column = "company_id",javaType = String.class,one = @One(select = "com.zcyk.dao.CompanyDao.getCompanyName")),
            //@Result(property = "relation_name",column = "company_id",javaType = String.class,one = @One(select = "com.zcyk.dao.CompanyDao.getCompanyName")),
            @Result(property = "applicant",column = "applicant_id",javaType = String.class,
                    one = @One(select = "com.zcyk.dao.UserDao.getNameById")),
            @Result(property = "audit",column = "audit_id",javaType = String.class,one = @One(select = "com.zcyk.dao.UserDao.getNameById")),
            @Result(property = "company_users",column = "company_id",javaType = Integer.class,one = @One(select = "com.zcyk.dao.UserCompanyStationDao.gatAllUserCount")),
            @Result(property = "company_projects",column = "company_id",javaType = Integer.class,one = @One(select = "com.zcyk.dao.CompanyProjectDao.getCompanyProjectCount"))})
    List<CompanyRelation> selectCheck(@Param("company_id") String company_id,@Param("search") String search);

    @Select("SELECT t.* ,t.company_id as company_id2,c1.name as company_name,c2.name as relation_name,c1.code as company_code,c2.code as relation_code,c1.address as company_address,c2.address as relation_address from" +
            " (SELECT * FROM company_relation WHERE flow_status not in (0,5) AND company_id=#{company_id}" +
            " UNION ALL " +
            " SELECT * FROM company_relation WHERE flow_status not in (0,5) AND relation_id=#{company_id}) as t" +
            " left join"+
            " company c1 on c1.id=t.company_id" +
            " left join" +
            " company c2 on c2.id=t.relation_id"
            )
    @Results(value = { //@Result(property = "company_name",column = "company_id",javaType = String.class,one = @One(select = "com.zcyk.dao.CompanyDao.getCompanyName")),
            //@Result(property = "relation_name",column = "company_id",javaType = String.class,one = @One(select = "com.zcyk.dao.CompanyDao.getCompanyName")),
            @Result(property = "applicant",column = "applicant_id",javaType = String.class,one = @One(select = "com.zcyk.dao.UserDao.getNameById")),
            @Result(property = "audit",column = "audit_id",javaType = String.class,one = @One(select = "com.zcyk.dao.UserDao.getNameById")),
            @Result(property = "company_users",column = "company_id",javaType = Integer.class,one = @One(select = "com.zcyk.dao.UserCompanyStationDao.gatAllUserCount")),
            @Result(property = "company_projects",column = "company_id",javaType = Integer.class,one = @One(select = "com.zcyk.dao.CompanyProjectDao.getCompanyProjectCount"))})
    List<CompanyRelation> selectChecked(@Param("company_id") String company_id,@Param("search") String search);


    @Select("select * from company_relation where status = 1 and company_id=#{company_id} and relation_id = #{relation_id}" +
            " union all" +
            " select * from company_relation where status =1 and relation_id=#{company_id} and company_id=#{relation_id}")
    List<CompanyRelation>relatedCompany(@Param("company_id") String company_id,@Param("relation_id") String relation_id);
}