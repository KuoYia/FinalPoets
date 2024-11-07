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
    //删除诗人
    @Delete("DELETE FROM poets WHERE id = #{id}")
    void delete(int id);

    // 分页查询所有诗人
    /*@Select("SELECT * FROM poets")
    List<Poet> findAllPoets(@Param("page") PageHelper.Page<?> page);*/
    @Select("SELECT * FROM poets")
    List<Poet> findAllPoets();

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

    // 根据诗人ID查询其所有诗句
    @Select("SELECT id, title, content, translation, authorId, poemTypeId FROM poems WHERE authorId = #{poetId}")
    List<Poem> selectPoemsByPoetId(@Param("poetId") int poetId);

    // 查询作者被收藏的次数
    @Select("SELECT COUNT(*) " +
            "FROM collections " +
            "WHERE resource_id IN " +
            "(SELECT id FROM poems WHERE authorId = #{id})")
    int countCollectionsByAuthorId(int id);



}



