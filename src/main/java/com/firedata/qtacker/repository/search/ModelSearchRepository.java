package com.firedata.qtacker.repository.search;

import com.firedata.qtacker.domain.Model;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Model} entity.
 */
public interface ModelSearchRepository extends ElasticsearchRepository<Model, Long> {
}
