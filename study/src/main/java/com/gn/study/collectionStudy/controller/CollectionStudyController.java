package com.gn.study.collectionStudy.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author guanning
 */
@RestController
@RequestMapping("/collection")
public class CollectionStudyController {

    @RequestMapping("test")
    public String test(){
        return "helo world";
    }
}
