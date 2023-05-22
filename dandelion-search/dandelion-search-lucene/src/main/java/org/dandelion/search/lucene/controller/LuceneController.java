package org.dandelion.search.lucene.controller;

import org.dandelion.search.lucene.model.SearchEntity;
import org.dandelion.search.lucene.service.LuceneService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @date 2023/5/22
 */
@Controller
public class LuceneController {
    @Resource
    private LuceneService luceneService;

    @RequestMapping("/search")
    public String search() {
        return "search";
    }

    @GetMapping("/list")
    @ResponseBody
    public List<SearchEntity> list(@RequestParam("key") String key, @RequestParam("num") Integer num) {
        return luceneService.search(key, num);
    }
}
