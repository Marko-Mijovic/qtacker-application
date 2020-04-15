package com.firedata.qtacker.repository.search;

import com.firedata.qtacker.domain.LogUser;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link LogUser} entity.
 */
public interface LogUserSearchRepository extends ElasticsearchRepository<LogUser, Long> {
}
