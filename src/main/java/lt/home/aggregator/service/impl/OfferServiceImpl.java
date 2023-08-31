package lt.home.aggregator.service.impl;

import lombok.RequiredArgsConstructor;
import lt.home.aggregator.entities.OfferEntity;
import lt.home.aggregator.repositories.OfferRepo;
import lt.home.aggregator.service.OfferService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OfferServiceImpl implements OfferService {

    private final OfferRepo offerRepo;

    @Transactional
    @Override
    public OfferEntity save(OfferEntity offerEntity) {
        return offerRepo.save(offerEntity);
    }
}
