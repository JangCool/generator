<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"  "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${mapperid}">

    <!--
    	※ 경고
		이 select SQL은  Code Generator를 통하여 생성 되었습니다.
     	기본 쿼리 이고 수시로 변경 될 소지가 있기 떄문에 Generator로 변경 하는 것이 아닌 직접 수정은 하지 마십시요.
     	
     	생성일 : ${date}
    -->
    
    <sql id="select-columns">
    	${columns}
    </sql>
    
    <select id="select" parameterType="${dto}" resultType="${dto}">
	${select} 
    </select>
    
    <!--
    	※ 경고
		이 insert SQL은  Code Generator를 통하여 생성 되었습니다.
     	기본 쿼리 이고 수시로 변경 될 소지가 있기 떄문에 Generator로 변경 하는 것이 아닌 직접 수정은 하지 마십시요.
     	
     	생성일 : ${date}
    -->
    <insert id="insert" parameterType="${dto}">
	${insert} 
    </insert>
    
    <!--
    	※ 경고
		이 update SQL은  Code Generator를 통하여 생성 되었습니다.
     	기본 쿼리 이고 수시로 변경 될 소지가 있기 떄문에 Generator로 변경 하는 것이 아닌 직접 수정은 하지 마십시요.
     	
     	생성일 : ${date}
    -->
    <update id="update" parameterType="${dto}">
	${update}   
    </update>
    
    <!--
    	※ 경고
		이 delete SQL은  Code Generator를 통하여 생성 되었습니다.
     	기본 쿼리 이고 수시로 변경 될 소지가 있기 떄문에 Generator로 변경 하는 것이 아닌 직접 수정은 하지 마십시요.
     	
     	생성일 : ${date}
    -->
    <delete id="delete" parameterType="${dto}">
	${delete}  
    </delete>
</mapper>