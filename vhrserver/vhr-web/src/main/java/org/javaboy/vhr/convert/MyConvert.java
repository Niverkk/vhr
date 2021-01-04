package org.javaboy.vhr.convert;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author JKXAING on 2020/8/24
 */
@Component
public class MyConvert implements Converter<String, Date> {
    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
    
    @Override
    public Date convert(String s) {
        try {
            return sf.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
