package lt.home.aggregator.utilits;

import lt.home.aggregator.dto.TypeBank;
import lt.home.aggregator.dto.interaction.Request;
import lt.home.aggregator.dto.interaction.RequestToFastBank;
import lt.home.aggregator.dto.interaction.RequestToSolidBank;

import java.util.Objects;
import java.util.regex.Pattern;

public class RequestValidation {
    private static final String PHONE_NUMBER_REGEX_FOR_FAST_BANK = "\\+371[0-9]{8}";
    private static final String PHONE_NUMBER_REGEX_FOR_SOLID_BANK = "\\+[0-9]{11,15}";
    private static final String EMAIL_REGEX = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" +
            "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    public static boolean check(Request request, TypeBank typeBank) {
        if (Objects.nonNull(request)) {
            return switch (typeBank) {
                case FAST -> {
                    RequestToFastBank requestToFastBank = (RequestToFastBank) request;
                    yield patternMatches(PHONE_NUMBER_REGEX_FOR_FAST_BANK, requestToFastBank.getPhoneNumber(), requestToFastBank.getEmail());
                }
                case SOLID -> {
                    RequestToSolidBank requestToSolidBank = (RequestToSolidBank) request;
                    yield patternMatches(PHONE_NUMBER_REGEX_FOR_SOLID_BANK, requestToSolidBank.getPhone(), requestToSolidBank.getEmail());
                }
            };
        } else {
            return false;
        }
    }

    private static boolean patternMatches(String pattern, String phone, String email) {
        final Pattern phonePattern = Pattern.compile(pattern);
        boolean phoneMatches = phonePattern.matcher(phone).matches();
        boolean emailMatches = EMAIL_PATTERN.matcher(email).matches();
        if (!phoneMatches || !emailMatches) {
            return false;
        }
        return true;
    }
}
