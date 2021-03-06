package com.sp.fc.user.service.helper;

import com.sp.fc.user.domain.School;
import com.sp.fc.user.repository.SchoolRepository;
import com.sp.fc.user.repository.UserRepository;
import com.sp.fc.user.service.SchoolService;
import com.sp.fc.user.service.UserSecurityService;
import com.sp.fc.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

public class WithUserTest { // 중복되는 테스트 설정들을 상위 헬퍼 객체로 모아두기

    @Autowired
    protected SchoolRepository schoolRepository;
    @Autowired
    protected UserRepository userRepository;

    protected SchoolService schoolService;
    protected UserService userService;
    protected UserSecurityService userSecurityService;

    protected SchoolTestHelper schoolTestHelper;
    protected UserTestHelper userTestHelper;
    protected School school;

    private boolean prepared;

    protected void prepareUserServices (){ // @BeforeEach에서 호출하여 미리 전처리 작업
        if(prepared) return;
        prepared = true;

        this.schoolRepository.deleteAll();
        this.userRepository.deleteAll();
        this.schoolService = new SchoolService(schoolRepository);
        this.userService = new UserService(schoolRepository, userRepository);
        this.userSecurityService = new UserSecurityService(userRepository);

        this.userTestHelper = new UserTestHelper(userService, NoOpPasswordEncoder.getInstance());
        this.schoolTestHelper = new SchoolTestHelper(schoolService);
        this.school = this.schoolTestHelper.createSchool("테스트 학교", "서울");
    }

}
