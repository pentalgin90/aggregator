package lt.home.aggregator.converter;

import lt.home.aggregator.configuration.MapStructConfig;
import lt.home.aggregator.dto.Customer;
import lt.home.aggregator.entities.CustomerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapStructConfig.class, uses = ResponseConverter.class)
public interface CustomerConverter {

    @Mapping(target = "responseList", source = "responseEntityList")
    Customer entityToDto(CustomerEntity entity);

    @Mapping(target = "dateCreated", ignore = true)
    @Mapping(target = "responseEntityList", source = "responseList")
    CustomerEntity dtoToEntity(Customer customer);
}
