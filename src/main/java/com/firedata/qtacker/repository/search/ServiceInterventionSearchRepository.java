package com.firedata.qtacker.repository.search;

import com.firedata.qtacker.domain.ServiceIntervention;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link ServiceIntervention} entity.
 */
public interface ServiceInterventionSearchRepository extends ElasticsearchRepository<ServiceIntervention, Long> {
}
