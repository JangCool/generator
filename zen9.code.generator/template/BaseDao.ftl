package ${package}.base;

import java.util.List;
import org.springframework.stereotype.Repository;
import kr.co.abcmart.zfset.sql.session.SqlSession${sqlsession};
import ${dto};

/**
 * ※ 절대 수정 금지. 해당 파일은 code generator 작동 시 내용을 전부 덮어 씌우게 됩니다. 
 * 
 */
@Repository
public class Base${tableName}Dao extends SqlSession${sqlsession} {
	
    /**
     * 이 select 메소드는 Code Generator를 통하여 생성 되었습니다.
     */
	public List<${tableName}> select(${tableName} ${field}) throws Exception {		
		return getSqlSession${sqlsession}().selectList("${mapperid}.select",${field});
	}
	
    /**
     * 이 select 메소드는 Code Generator를 통하여 생성 되었습니다.
     */
	public int insert(${tableName} ${field}) throws Exception {		
		return getSqlSession${sqlsession}().insert("${mapperid}.insert",${field});
	}
	
    /**
     * 이 select 메소드는 Code Generator를 통하여 생성 되었습니다.
     */
	public int update(${tableName} ${field}) throws Exception {		
		return getSqlSession${sqlsession}().update("${mapperid}.update",${field});
	}
	
	 /**
     * 이 select 메소드는 Code Generator를 통하여 생성 되었습니다.
     */
	public int delete(${tableName} ${field}) throws Exception {		
		return getSqlSession${sqlsession}().delete("${mapperid}.delete",${field});
	}


}
