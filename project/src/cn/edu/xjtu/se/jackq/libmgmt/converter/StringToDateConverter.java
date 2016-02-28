package cn.edu.xjtu.se.jackq.libmgmt.converter;

import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StringToDateConverter implements Converter<String, Date> {
    private String pattern = "yyyy-MM-dd";

    @Override
    public Date convert(String s) {
        // Null Date
        if (null == s || s.isEmpty()) {
            return null;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        dateFormat.setLenient(false);
        try {
            return dateFormat.parse(s);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid Date Format in header");
        }
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }
}
