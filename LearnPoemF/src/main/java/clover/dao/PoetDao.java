package clover.dao;


import clover.pojo.Poem;
import clover.pojo.Poet;
import org.apache.ibatis.annotations.*;
import org.springframework.context.annotation.Primary;

import java.util.List;


@Mapper
@Primary
public interface PoetDao {
    //查询功能
    @Select("SELECT * FROM poets WHERE id = #{id}")
    Poet findById(int id);

    //插入，新增诗人
    @Insert("INSERT INTO poets(name, birthDate, deathDate, dynasty, biography) " +
            "VALUES(#{name}, #{b2irthDate}, #{deathDate}, #{dynasty}, #{biography})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Poet poet);

    //更改
    @Update("UPDATE poets SET name = #{name}, birthDate = #{birthDate}, " +
            "deathDate = #{deathDate}, dynasty = #{dynasty}, biography = #{biography} WHERE id = #{id}")
    void update(Poet poet);

    @Delete("DELETE FROM poets WHERE id = #{id}")
    void delete(int id);
    // 批量插入
    @Insert("<script>" +
            "INSERT INTO poets (name, birthDate, deathDate, dynasty, biography) VALUES " +
            "<foreach collection='list' item='poet' separator=','>" +
            "(#{poet.name}, #{poet.birthDate}, #{poet.deathDate}, #{poet.dynasty}, #{poet.biography})" +
            "</foreach>" +
            "</script>")
    void insertPoetsInBatch(@Param("list") List<Poet> poets);

    // 批量删除
    @Delete("<script>" +
            "DELETE FROM poets " +
            "WHERE id IN " +
            "<foreach collection='ids' item='id' open='(' separator=',' close=')'>" +
            "#{id}" +
            "</foreach>" +
            "</script>")
    void deletePoetsInBatch(@Param("ids") List<Integer> ids);


    // 获取诗人表中最后一个诗人的ID
    @Select("SELECT id FROM poets ORDER BY id DESC LIMIT 1")
    int getLastId();

    @Select("SELECT * FROM poets")
    List<Poet> findAll();

    // 查询作者被收藏的次数
    @Select("SELECT COUNT(*) " +
            "FROM collections " +
            "WHERE resource_id IN " +
            "(SELECT id FROM poems WHERE authorId = #{id})")
    int countCollectionsByAuthorId(int id);


    // 模糊查询三个条件三选一
    @Select("<script>" +
            "SELECT * FROM poets " +
            "WHERE 1=1 " +
            "<if test='name != null and name.trim() != \"\"'>" +
            "AND name LIKE CONCAT('%', #{name}, '%') " +
            "</if>" +
            "<if test='dynasty != null and dynasty.trim() != \"\"'>" +
            "AND dynasty LIKE CONCAT('%', #{dynasty}, '%') " +
            "</if>" +
            "<if test='biography != null and biography.trim() != \"\"'>" +
            "AND biography LIKE CONCAT('%', #{biography}, '%') " +
            "</if>" +
            "</script>")
    List<Poet> findByMultipleConditions(@Param("name") String name,
                                        @Param("dynasty") String dynasty,
                                        @Param("biography") String biography);


    // 多表连接查询，查询诗人和诗句
    //返回poet主体加上诗句的list
    @Select("SELECT " +
            "p.id AS poet_id, " +
            "p.name AS poet_name, " +
            "p.birthDate AS poet_birthDate, " +
            "p.deathDate AS poet_deathDate, " +
            "p.dynasty AS poet_dynasty, " +
            "p.biography AS poet_biography, " +

            "po.id AS poem_id, " +
            "po.title AS poem_title, " +
            "po.content AS poem_content, " +
            "po.translation AS poem_translation, " +

            "po.authorId AS poem_authorId, " +
            "po.poemTypeId AS poem_poemTypeId " +

            "FROM poets p " +
            "LEFT JOIN poems po ON p.id = po.authorId " +
            "WHERE p.id = #{id}")
// 使用@Results注解来指定查询结果如何映射到Java对象的属性上
    @Results(value = {
            // 映射单个属性
            @Result(property = "id", column = "poet_id"),
            @Result(property = "name", column = "poet_name"),
            @Result(property = "birthDate", column = "poet_birthDate"),
            @Result(property = "deathDate", column = "poet_deathDate"),
            @Result(property = "dynasty", column = "poet_dynasty"),
            @Result(property = "biography", column = "poet_biography"),

            // 映射一个集合属性，表示一对多的关系
            // 这里映射的是Poet对象中的poems属性，它是一个包含多个Poem对象的列表
            @Result(property = "poems", column = "poet_id",
                    many = @Many(select = "com.clover.mapper.PoetMapper.selectPoemsByPoetId"))
    })
    List<Poet> findPoetWithPoemsById(@Param("id") int id);


    // 根据诗人ID查询其所有诗句
    @Select("SELECT id, title, content, translation, authorId, poemTypeId FROM poems WHERE authorId = #{poetId}")
    List<Poem> selectPoemsByPoetId(@Param("poetId") int poetId);


    // 根据用户 ID 查询用户收藏的诗人列表
    @Select("SELECT * FROM poems WHERE id IN " +
            "(SELECT resource_id FROM collections WHERE user_id = #{userId})")
    List<Poem> selectCollectionsByUserId(@Param("userId") int userId);


    // 查询诗人信息和收藏该诗人的用户列表
    @Select("SELECT p.*, u.* " +
            "FROM poets p " +
            "JOIN collections co ON p.id = co.resource_id " +
            "JOIN users u ON co.user_id = u.id " +
            "WHERE p.id = #{poetId}")
    @Results(value = {
            @Result(property = "poet.id", column = "poet_id"),
            @Result(property = "poet.name", column = "poet_name"),
            @Result(property = "birthDate", column = "poet_birthDate"),
            @Result(property = "deathDate", column = "poet_deathDate"),
            @Result(property = "dynasty", column = "poet_dynasty"),
            @Result(property = "biography", column = "poet_biography"),
            // 其他诗人属性的映射

            @Result(property = "usersWhoCollectedPoet", column = "user_id",
                    many = @Many(select = "com.clover.mapper.UserMapper.selectUserById"))
    })
    // Mapper接口
    List<Poet> findPoetAndUsersWhoCollectedPoet(@Param("id") int id);


}



