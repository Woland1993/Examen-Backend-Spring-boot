package dominio;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Imagen {
private int id;
private String nombre;
private String base64;
private String created_at;
private String updated_at;

}
