package OMF.utilities;

import OMF.utilities.datahelpers.IdNumberValidator;
import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Calendar.*;

public class DataGenerators {

    private FakeValuesService fakeValuesService;
    private Faker faker;
    private String currentEmail;
    private String currentCellNumber;
    private String firstName;
    private String lastName;
    private String currentFirstName;
    private String currentLastName;

    public DataGenerators() {
        fakeValuesService = new FakeValuesService(new Locale("en-ZA"), new RandomService());
        faker = new Faker(new Locale("en-ZA"));
    }

    private String GenerateSACellNumber(Boolean valid) {
        String saCellNumber;
        if (valid) {
            while (true) {
                saCellNumber = fakeValuesService.regexify("(0)[156789][0-9]{8}");
                Pattern pattern = Pattern.compile("(0)[156789][0-9]{8}");
                Matcher matcher = pattern.matcher(saCellNumber);
                if (matcher.matches()) {
                    break;
                }
            }
            return saCellNumber;
        } else {
            while (true) {
                saCellNumber = fakeValuesService.bothify("##########");
                Pattern pattern = Pattern.compile("(0)[156789][0-9]{8}");
                Matcher matcher = pattern.matcher(saCellNumber);
                if (!matcher.matches()) {
                    break;
                }
            }
            return saCellNumber;
        }
    }

    public static Calendar getCalendar(Date date) {
        Calendar cal = Calendar.getInstance(Locale.US);
        cal.setTime(date);
        return cal;
    }

    /**
     * Generates random SA ID number using regex expression
     * Checks is valid, citizen, older than 18 years old
     * @return 7712315046081
     */
    private String GenerateValidSAIdNumber() {
        IdNumberValidator idNumberValidator = new IdNumberValidator();
        String idNumber;
        while (true) {
            IdNumberValidator.IDNumberDetails idNumberDetails;
            while (true) {
                idNumber = fakeValuesService.regexify(
                        "(((\\d{2}((0[13578]|1[02])(0[1-9]|[13]\\d|3[01])|(0[13456789]|1[012])(0[1-9]|[12]\\d|30)|02(0[1-9]|1\\d|2[0-8])))|([02468][048]|[13579][26])0229))((\\d{4})(\\d{3})|(\\d{7}))");
                idNumberDetails = idNumberValidator.extractInformation(idNumber);
                if (idNumberDetails.isValid() && idNumberDetails.isCitizen()) break;
            }
            Date idBirthday = idNumberDetails.getBirthDate();
            Date dateNow = new Date(System.currentTimeMillis());
            Calendar a = getCalendar(idBirthday);
            Calendar b = getCalendar(dateNow);
            int diff = b.get(YEAR) - a.get(YEAR);
            if (a.get(MONTH) > b.get(MONTH) ||
                    (a.get(MONTH) == b.get(MONTH) && a.get(DATE) > b.get(DATE))) {
                diff--;
            }
            if (diff > 18) break;
        }
        return idNumber;
    }

    public String GenerateRequiredData(String dataTypeRequired) {
        String requiredData = null;
        switch (dataTypeRequired.toUpperCase()) {
            case "GENERATED NAME":
                firstName = faker.name().firstName();
                requiredData = firstName;
                currentFirstName =  requiredData;
                break;
            case "GENERATED - USE NAME":
                requiredData = currentFirstName;
                break;
            case "GENERATED LAST NAME":
                lastName = faker.name().lastName();
                requiredData = lastName;
                currentLastName =  requiredData;
                break;
            case "GENERATED - USE LASTNAME":
                requiredData = currentLastName;
                break;
            case "GENERATED EMAIL":
                firstName = faker.name().firstName();
                currentFirstName = firstName;
                requiredData = firstName.toLowerCase() + faker.number().digits(5) + "@automationshoprite.co.za".toLowerCase();
                currentEmail = requiredData;
                break;
            case "GENERATED EMAIL - USE NAME":
                requiredData = currentFirstName.toLowerCase() + faker.number().digits(5) + "@automationshoprite.co.za".toLowerCase();
                currentEmail = requiredData;
                break;
            case "GENERATED - USE EMAIL":
                requiredData = currentEmail;
                break;
            case "GENERATED CELL NUMBER":
                requiredData = GenerateSACellNumber(true);
                currentCellNumber = requiredData;
                break;
            case "GENERATED - USE CELL NUMBER":
                requiredData = currentCellNumber;
                break;
            case "GENERATED VALID SA ID NUMBER":
                requiredData = GenerateValidSAIdNumber();
                break;
            case "GENERATED NUMBER - 10":
                requiredData = faker.number().digits(10).replace('0', '1');
                break;
        }
        return requiredData;
    }

    public String generateRandomValue(int length) {

        //int length = 10;
        boolean useLetters = false;
        boolean useNumbers = true;
        String generatedValue = RandomStringUtils.random(length, useLetters, useNumbers);
        System.out.println(generatedValue);
        return generatedValue;
    }

    public String generateRandomString(int length) {

        //int length = 10;
        boolean useLetters = true;
        boolean useNumbers = false;
        String generatedString = RandomStringUtils.random(length, useLetters, useNumbers);
        System.out.println(generatedString);
        return generatedString;
    }
}
