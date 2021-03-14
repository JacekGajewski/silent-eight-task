package com.eight.silent.gender.controller;

import com.eight.silent.gender.service.GenderService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("genders")
@AllArgsConstructor
public class GenderController {

    private final GenderService genderService;

    @GetMapping
    public List<String> getAllNames() {
        return genderService.getAllNames();
    }

    @GetMapping("/{name}")
    public String checkGender(@PathVariable String name, @RequestParam(required = false) Boolean fullName) {
        return genderService.getGender(name, fullName);
    }
}
