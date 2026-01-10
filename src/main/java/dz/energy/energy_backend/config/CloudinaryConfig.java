package dz.energy.energy_backend.config;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary() {
        Map<String, String> config = new HashMap<>();

        config.put("cloud_name", "dto3fo5mt");
        config.put("api_key", "156784471748765");
        config.put("api_secret", "6VXe-jSUp6dT9FFBV_6EqBw_8X0");

        return new Cloudinary(config);
    }
}
