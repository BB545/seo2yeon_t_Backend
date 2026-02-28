package com.seo2yeon.students.domain.school.service;

import com.seo2yeon.students.domain.school.client.NeisSchoolClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SchoolService {
    private final NeisSchoolClient neisSchoolClient;

    public String search(String locationName, String keyword) {
        return neisSchoolClient.searchSchool(locationName, keyword);
    }
}
