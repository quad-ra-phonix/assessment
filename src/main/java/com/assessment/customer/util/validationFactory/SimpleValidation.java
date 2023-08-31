package com.assessment.customer.util.validationFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleValidation implements ValidationProcessor{
    @Override
    public Boolean isValidIdNumber(String idNumber) {
        //Regex to check valid id
        String regex = "^(\\d{13})?$";

        Pattern pattern = Pattern.compile(regex);

        if(idNumber == null){
            return false;
        }

        Matcher matcher = pattern.matcher(idNumber);
        return matcher.matches();
    }
}
