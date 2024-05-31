package pl.otwartemigawki.OtwarteMigawkiApp.dto;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link pl.otwartemigawki.OtwarteMigawkiApp.model.SessionType}
 */
@Value
public class SessionTypeDTO implements Serializable {
    Integer sessionTypeId;
    String sessionTypeName;
    String description;
    Integer price;
    String coverPhotoPath;
    Boolean disabled;


}