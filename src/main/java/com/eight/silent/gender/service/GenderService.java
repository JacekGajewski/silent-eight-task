package com.eight.silent.gender.service;

import java.util.List;

public interface GenderService {

    List<String> getAllNames();

    String getGender(String name, Boolean full);

}
