package lt.home.aggregator.converter;

import lt.home.aggregator.configuration.MapStructConfig;
import lt.home.aggregator.dto.interaction.Offer;
import lt.home.aggregator.entities.OfferEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapStructConfig.class)
public interface OfferConverter {
    Offer entityToDto(OfferEntity offerEntity);
    @Mapping(target = "offerId", ignore = true)
    @Mapping(target = "responseEntity", ignore = true)
    OfferEntity dtoToEntity(Offer offer);
}
