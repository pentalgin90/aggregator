package lt.home.aggregator.converter;

import lt.home.aggregator.configuration.MapStructConfig;
import lt.home.aggregator.dto.Customer;
import lt.home.aggregator.dto.interaction.RequestToFastBank;
import lt.home.aggregator.dto.interaction.RequestToSolidBank;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapStructConfig.class)
public interface RequestConverter {
    @Mapping(target = "monthlyIncomeAmount", source = "monthlyIncome")
    @Mapping(target = "monthlyCreditLiabilities", source = "monthlyExpenses")
    @Mapping(target = "agreeToDataSharing", source = "agreeToBeScored")
    RequestToFastBank makeRequestToFastBank(Customer customer);

    @Mapping(target = "phone", source = "phoneNumber")
    @Mapping(target = "maritalStatus", source = "marriedStatus")
    RequestToSolidBank makeRequestToSolidBank(Customer customer);
}
