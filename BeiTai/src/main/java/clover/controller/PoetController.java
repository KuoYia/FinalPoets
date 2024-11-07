package clover.controller;

import clover.pojo.Poet;
import clover.service.impl.PoetServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/poets")
public class PoetController {

    @Autowired
    private PoetServiceImpl poetService;
    @GetMapping("/queryPoet/{id}")
    public Poet queryPoetById(@PathVariable int id) {
        System.out.println("模拟查询指定id的诗人");
        Poet poet = new Poet();
        poet.setId(id);
        poet.setName("李白"); // 假设这是诗人的名字
        poet.setDynasty("唐"); // 假设这是诗人的朝代
        poet.setBiography("唐代著名诗人"); // 假设这是诗人的简介
        // 假设诗人的生卒日期，这里使用Java的Date类
        poet.setBirthDate(java.sql.Date.valueOf(LocalDate.of(2004, 1, 1)));
        poet.setDeathDate(java.sql.Date.valueOf(LocalDate.of(0000, 1, 1)));
        return poet;
    }

   //删除诗人
    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> deletePoet(@PathVariable int id) {
        try {
            poetService.delete(id);
            return ResponseEntity.ok(1);
        } catch (Exception e) {
            return ResponseEntity.ok(0);
        }
    }

    //查找诗人
    //http://localhost:8080/api/poets/1
    @GetMapping("/{id}")
    public ResponseEntity<Poet> getPoetById(@PathVariable int id) {
        Poet poet = poetService.findById(id);
        return ResponseEntity.ok(poet);
    }


   //分页查询
    ////http://localhost:8080/api/poets/getPage
    @GetMapping("/getPage")
    public ResponseEntity<List<Poet>> getIssues(@RequestParam(defaultValue = "2") Integer pageNum,
                                                @RequestParam(defaultValue = "5") Integer pageSize) {
        List<Poet> poets = poetService.findPoetsByPage(pageNum, pageSize);
        return ResponseEntity.ok(poets); // 使用 ResponseEntity.ok() 来返回成功的响应
    }

}