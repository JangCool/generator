package ${package};

import org.springframework.stereotype.Repository;
import kr.co.abcmart.zfset.sql.session.SqlSessionMaster;
import ${dto};

@Repository
public class ${tableName}Dao extends SqlSessionMaster {
	
    /**
     * 이 select 메소드는 Code Generator를 통하여 생성 되었습니다.
     *
     * @date ${date}
     */
	public List<${tableName}> select(${tableName} ${field}) throws Exception {		
		return getSqlSessionMaster().selectList("${package}.${tableName}.select",${field});
	}
	
    /**
     * 이 select 메소드는 Code Generator를 통하여 생성 되었습니다.
     *
     * @date ${date}
     */
	public int insert(${tableName} ${field}) throws Exception {		
		return getSqlSessionMaster().insert("${package}.${tableName}.insert",${field});
	}
	
    /**
     * 이 select 메소드는 Code Generator를 통하여 생성 되었습니다.
     *
     * @date ${date}
     */
	public int update(${tableName} ${field}) throws Exception {		
		return getSqlSessionMaster().update("${package}.${tableName}.update",${field});
	}
	
	 /**
     * 이 select 메소드는 Code Generator를 통하여 생성 되었습니다.
     *
     * @date ${date}
     */
	public int delete(${tableName} ${field}) throws Exception {		
		return getSqlSessionMaster().delete("${package}.${tableName}.delete",${field});
	}


}
