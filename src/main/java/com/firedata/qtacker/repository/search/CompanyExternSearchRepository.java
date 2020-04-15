package com.firedata.qtacker.repository.search;

import com.firedata.qtacker.domain.CompanyExtern;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link CompanyExtern} entity.
 */
public interface CompanyExternSearchRepository extends ElasticsearchRepository<CompanyExtern, Long> {
}
