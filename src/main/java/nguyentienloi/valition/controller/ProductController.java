package nguyentienloi.valition.controller;

import jakarta.validation.Valid;
import nguyentienloi.valition.models.Product;
import nguyentienloi.valition.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import static java.io.File.separator;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static java.util.UUID.randomUUID;


@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("product", new Product());
        return "products/create";
    }

    @Value("${upload.path}")
    private String uploadPath;

    @PostMapping("/create")
    public String create(@Valid Product newProduct, @RequestParam MultipartFile imageProduct, BindingResult result,
                         Model model) {
        if (result.hasErrors()) {
            model.addAttribute("product", newProduct);
            return "products/create";
        }

        if (imageProduct != null && imageProduct.getSize() > 0) {
            try {
                File saveFile = new ClassPathResource("static/images").getFile();
                String newImageFile = randomUUID() + ".png";
                java.nio.file.Path path = Paths.get(saveFile.getAbsolutePath() + separator + newImageFile);
                Files.copy(imageProduct.getInputStream(), path, REPLACE_EXISTING);
                newProduct.setImage(newImageFile);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        productService.add(newProduct);
        return "redirect:/products";
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("listproduct", productService.GetAll());
        return "products/index";
    }


}
