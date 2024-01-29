//package com.iche.sco.applicationConfig;
//
//import com.cloudinary.Cloudinary;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Configuration
//public class CloudinaryConfig {
//    private final String CLOUD_NAME = "yhegbshd";
//    private final String CLOUD_API_SECRET = "L8gMBYLMnYanoaWKaa7U9VzcUPA";
//    private final String CLOUD_API = "583534981816193";
//
//    @Bean
//    public Cloudinary cloudinary(){
//        Map<String, String> config = new HashMap<>();
//        config.put("cloud_name", CLOUD_NAME);
//        config.put("api_key", CLOUD_API);
//        config.put("api_secret", CLOUD_API_SECRET);
//        return new Cloudinary(config);
//    }
//}
