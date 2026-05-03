package com.example.school_website.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dmpqllxc5",
                "api_key", "972765935229522",
                "api_secret", "XF5YIX93F1XplBn0CS4e_3gjbi4",
                "secure", true
        ));
    }
}
