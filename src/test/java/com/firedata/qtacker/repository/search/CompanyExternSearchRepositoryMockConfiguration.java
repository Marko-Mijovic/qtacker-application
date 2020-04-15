package com.firedata.qtacker.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link CompanyExternSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class CompanyExternSearchRepositoryMockConfiguration {

    @MockBean
    private CompanyExternSearchRepository mockCompanyExternSearchRepository;

}
