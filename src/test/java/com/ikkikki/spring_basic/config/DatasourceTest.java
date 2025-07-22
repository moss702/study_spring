package com.ikkikki.spring_basic.config;

import com.ikkikki.spring_basic.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlInOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Slf4j
@SpringBootTest
public class DatasourceTest {
  @Autowired
  private DataSource dataSource;

  @Autowired
  private JdbcTemplate jdbcTemplate;


  @Test
  public void test() {
    log.info("{}", dataSource);
    log.info("{}", jdbcTemplate);
  }

  @Test
  public void testGetMembers() {
    jdbcTemplate.queryForList("select * from tbl_member").forEach(System.out::println);
  }

  @Test
  public void testIter() {
    List<Integer> list = List.of(2,3,4,1);
    Iterator<Integer> iterator = list.iterator();
    while (iterator.hasNext()) {
      System.out.println(iterator.next());
    }
  }

  @Test
  public void testCursor() throws SQLException { // PL/SQL의 가장 어려운 녀석
    Connection conn = jdbcTemplate.getDataSource().getConnection();
    PreparedStatement ps = conn.prepareStatement("select * from tbl_member");
    // ps.setString(1,"sae");
    ResultSet rs = ps.executeQuery(); // 쿼리수행
    while (rs.next()) { // 커서 하나씩 이동. 행이동
      // rs.getString(1);
      int no = rs.getInt(1);
      // String name = rs.getString(3);
      String name = rs.getString("name"); //칼럼 이름으로도 서치 가능
      log.info("{} {}", no, name);
    }
  }

  @Test
  public void testCallFunction() {
    int result = jdbcTemplate.queryForObject("select add_num(?,?)", int.class, 10, 20);
    // 인자1 : SQL 구문 | 인자2 : 가변인자 * 타입정의한것, ?, ?

    log.info("{}", result);
  }

  @Test
  public void testCallProcedure() {
    SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate);
    call.withCatalogName("pbl").withProcedureName("list_members");

    Map<String, Object> map = call.execute();
    log.info("{}", map);
  }

  @Test
  public void testCallProcedure2() {
    List<Member> members = jdbcTemplate.query("call list_members()", new RowMapper<Member>() {
      @Override
      public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Member.builder()
                .id(rs.getString("id"))
                .name(rs.getString("name"))
                .build();
      } // rowmapper가 단일행 형태라면 람다식으로 변환 가능
    });
    members.forEach(System.out::println);
  }

  @Test
  public void testCallProcedure3() {
    SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate)
            .withCatalogName("pbl").withProcedureName("find_member_by_id")
            .declareParameters(new SqlParameter("v_id", Types.VARCHAR));
    Map<String, Object> map = call.execute("sae");
    log.info("{}", map);
  }

  @Test
  public void testCallProcedure4() {
    SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate)
            .withCatalogName("pbl").withProcedureName("find_name_by_id")
            .declareParameters(new SqlParameter("v_id", Types.VARCHAR), new SqlInOutParameter("m_name", Types.VARCHAR));
    Map<String, Object> map = call.execute(Map.of("v_id", "sae"));
    log.info("{}", map);
    log.info("{}", map.get("m_name"));
  }

  @Test
  public void testCallProcedure5() {
    SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate)
            .withCatalogName("pbl").withProcedureName("add_five")
            .declareParameters(new SqlParameter("num", Types.INTEGER));
    Map<String, Object> map = call.execute(10);
    log.info("{}", map);
  }
}
