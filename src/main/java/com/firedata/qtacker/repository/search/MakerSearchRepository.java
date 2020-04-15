package com.firedata.qtacker.repository.search;

import com.firedata.qtacker.domain.Maker;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Maker} entity.
 */
public interface MakerSearchRepository extends ElasticsearchRepository<Maker, Long> {
}
