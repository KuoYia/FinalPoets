package clover.service.impl;

import clover.pojo.Poem;
import clover.pojo.Poet;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PoetServiceImpl {
    Poet findById(int id);
    List<Poet> findAll();
    Poet insert(Poet poet);
    void update(Poet poet);
    boolean delete(int id);
    int getLastId();
    int countCollectionsByAuthorId(int id);
    List<Poet> findByMultipleConditions(String name, String dynasty, String biography);
    void insertPoetsInBatch(List<Poet> poets);
    void deletePoetsInBatch(List<Integer> ids);
    List<Poet> findPoetWithPoemsById(int id);
    List<Poem> selectPoemsByPoetId(int poetId);
    List<Poem> selectCollectionsByUserId(int userId);
    List<Poet> findPoetAndUsersWhoCollectedPoet(int id);
}