package nguyentienloi.valition.models;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private int id;
    @NotBlank(message = "Tên sản phẩm không được bỏ trống")
    private String name;
    @Length(min = 0, max = 50,message = "Tên hình ảnh không quá 50 ký tự" )
    private String image;
    @NotNull(message = "Giá sản phẩm không được để trống")
    @Min(value = 1, message = "Giá sản phẩm không được nhỏ hơn q")
    @Max(value = 999999999, message = "Giá sản phẩm không được lớn hơn 999999999")
    private long price;
}
