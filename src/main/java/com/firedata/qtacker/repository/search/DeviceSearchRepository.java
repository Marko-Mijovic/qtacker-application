package com.firedata.qtacker.repository.search;

import com.firedata.qtacker.domain.Device;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Device} entity.
 */
public interface DeviceSearchRepository extends ElasticsearchRepository<Device, Long> {
}
