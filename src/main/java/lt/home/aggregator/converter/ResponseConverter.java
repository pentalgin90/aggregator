package lt.home.aggregator.converter;

import lt.home.aggregator.configuration.MapStructConfig;
import lt.home.aggregator.dto.interaction.Response;
import lt.home.aggregator.entities.ResponseEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapStructConfig.class, uses = OfferConverter.class)
public interface ResponseConverter {
    @Mapping(target = "request", ignore = true)
    @Mapping(target = "offer", source = "offerEntity")
    Response entityToDto(ResponseEntity response);
    @Mapping(target = "customerEntity", ignore = true)
    @Mapping(target = "offerEntity", source = "offer")
    ResponseEntity dtoToEntity(Response response);
}
