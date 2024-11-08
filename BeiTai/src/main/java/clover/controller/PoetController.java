package clover.controller;

import clover.pojo.Poet;
import clover.service.impl.PoetServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/poets")
public class PoetController {

    @Autowired
    private PoetServiceImpl poetService;

    private static final Logger logger = LoggerFactory.getLogger(PoetController.class);
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePoet(@PathVariable int id) {
        try {
            boolean result = poetService.delete(id);
            if (result) {
                return ResponseEntity.ok(1); // 删除成功
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("存在级联关系的诗人无法删除"); // 存在级联关系
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("删除诗人时发生错误：" + e.getMessage()); // 其他错误
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



    @PostMapping("/add")
    public ResponseEntity<Integer> addPoet(@RequestBody Poet poet) {
        try {
            int result = poetService.insert(poet);
            if (result == 1) {
                return ResponseEntity.ok(1); // 成功返回1
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(0); // 失败返回0
            }
        } catch (Exception e) {
            logger.error("Error inserting poet", e); // 打印异常信息和堆栈跟踪
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(0); // 异常返回0
        }
    }

     // 更新诗人信息
     @PutMapping("/{id}")
     public ResponseEntity<Integer> updatePoet(@PathVariable int id, @RequestBody Poet poet) {
        try {
            poet.setId(id); // 确保更新的是正确的诗人信息
            int result = poetService.update(poet);
            if (result > 0) {
                return ResponseEntity.ok(1); // 成功返回1
            } else {
                return ResponseEntity.badRequest().body(0); // 失败返回0
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(0); // 异常返回0
        }
    }

        // 批量插入诗人
        // 批量插入诗人
        @PostMapping("/batch")
        public ResponseEntity<List<Poet>> addPoets(@RequestBody List<Poet> poets) {
            try {
                poetService.insertPoetsInBatch(poets);
                return ResponseEntity.ok(poets);
            } catch (Exception e) {
                logger.error("Error inserting poets in batch", e); // 打印异常信息和堆栈跟踪
                return ResponseEntity.badRequest().body(null);
            }
        }

        // 批量删除诗人
        @DeleteMapping("/batch")
        public ResponseEntity<Integer> deletePoets(@RequestBody List<Integer> ids) {
            try {
                poetService.deletePoetsInBatch(ids);
                return ResponseEntity.ok(1);
            } catch (Exception e) {
                return ResponseEntity.ok(0);
            }
        }


}