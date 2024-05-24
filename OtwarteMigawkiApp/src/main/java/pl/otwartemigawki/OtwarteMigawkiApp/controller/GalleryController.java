package pl.otwartemigawki.OtwarteMigawkiApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.otwartemigawki.OtwarteMigawkiApp.dto.AddGalleryRequestDTO;
import pl.otwartemigawki.OtwarteMigawkiApp.dto.ApiResponseDTO;
import pl.otwartemigawki.OtwarteMigawkiApp.dto.GalleryOverviewDTO;
import pl.otwartemigawki.OtwarteMigawkiApp.model.User;
import pl.otwartemigawki.OtwarteMigawkiApp.model.UserSession;
import pl.otwartemigawki.OtwarteMigawkiApp.service.GalleryService;
import pl.otwartemigawki.OtwarteMigawkiApp.service.JwtService;
import pl.otwartemigawki.OtwarteMigawkiApp.service.UserService;
import pl.otwartemigawki.OtwarteMigawkiApp.service.UserSessionService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/gallery")
public class GalleryController {


    private final GalleryService galleryService;
    private final UserSessionService userSessionService;
    private final UserService userService;
    private final JwtService jwtService;


    @Autowired
    public GalleryController(GalleryService galleryService, UserSessionService userSessionService, UserService userService, JwtService jwtService) {
        this.galleryService = galleryService;
        this.userSessionService = userSessionService;
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponseDTO> addGallery(@ModelAttribute AddGalleryRequestDTO request) {
        try{
            Optional<UserSession> userSession = userSessionService.getUserSessionById(request.getSessionId());
            if(userSession.isPresent())
                galleryService.createGallery(request, userSession.get());
            return ResponseEntity.ok(new ApiResponseDTO("Udało się dodać galerię!", true));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponseDTO("Błąd serwera, nie udało się dodać galerii", false));
        }
    }
    @GetMapping("/get-user-galleries")
    public ResponseEntity<List<GalleryOverviewDTO>> getGalleriesForUser(@CookieValue(name = "jwtToken", defaultValue = "defaultValue") String cookieValue){
        try{
            User user = userService.getUserByEmail(jwtService.extractUserName(cookieValue));
            List<GalleryOverviewDTO> response = galleryService.getAllUserGalleries(user.getId());
            return ResponseEntity.ok(response);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
        }
    }


    @GetMapping("/gallery/{galleryId}")
    public ApiResponseDTO getGalleryById(@PathVariable Integer galleryId) {
        return null;
    }


}
