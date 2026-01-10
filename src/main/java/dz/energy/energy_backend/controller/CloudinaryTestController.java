package dz.energy.energy_backend.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/test")
@CrossOrigin("*")
public class CloudinaryTestController {

    private final Cloudinary cloudinary;

    public CloudinaryTestController(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    @PostMapping("/upload")
    public Map upload(@RequestParam("image") MultipartFile image) throws Exception {

        return cloudinary.uploader().upload(
                image.getBytes(),
                ObjectUtils.emptyMap()
        );
    }
}
