package clover.service.impl;

import clover.pojo.Poem;
import clover.pojo.Poet;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PoetServiceImpl {
    Poet findById(int id);
    int insert(Poet poet);
    int update(Poet poet);
    boolean delete(int id);
    void insertPoetsInBatch(List<Poet> poets);
    void deletePoetsInBatch(List<Integer> ids);

    List<Poet> findAllPoets(int page, int size);
    public List<Poet> findPoetsByPage(Integer pageNum, Integer pageSize);

    List<Poet> findPoetWithPoemsById(int id);

    List<Poem> selectPoemsByPoetId(int poetId);
}