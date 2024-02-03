package org.dilan.salinda.sonarqubedataextractor.util;


import org.dilan.salinda.sonarqubedataextractor.dto.PagingDTO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UtilsTests {


    @MockBean
    private PagingDTO PagingDTO;

    @Test
    void findMaxPagesTest() {
        assertThat(PagingDTO).isNotNull();
    }

}
