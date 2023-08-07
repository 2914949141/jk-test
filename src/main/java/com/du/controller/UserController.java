package com.du.controller;

import com.du.domain.User;
import com.google.common.base.Function;
import lombok.Data;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class UserController {

    @RequestMapping("/")
    public String test() {
        return "Hello Jenkins";
    }
}

