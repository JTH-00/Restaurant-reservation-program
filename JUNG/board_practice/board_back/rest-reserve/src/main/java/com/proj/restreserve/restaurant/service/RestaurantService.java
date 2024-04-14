package com.proj.restreserve.restaurant.service;

import com.proj.restreserve.detailpage.service.FileCURD;
import com.proj.restreserve.menu.dto.MenuDto;
import com.proj.restreserve.menu.entity.Menu;
import com.proj.restreserve.menu.entity.MenuImage;
import com.proj.restreserve.menu.repository.MenuImageRepository;
import com.proj.restreserve.menu.repository.MenuRepository;
import com.proj.restreserve.menucategory.entity.MenuCategory;
import com.proj.restreserve.menucategory.repository.MenuCategoryRepository;
import com.proj.restreserve.restaurant.dto.RestaurantDto;
import com.proj.restreserve.restaurant.entity.Favorites;
import com.proj.restreserve.restaurant.entity.Restaurant;
import com.proj.restreserve.restaurant.entity.RestaurantImage;
import com.proj.restreserve.restaurant.repository.FavoritesRepository;
import com.proj.restreserve.restaurant.repository.RestaurantImageRepository;
import com.proj.restreserve.restaurant.repository.RestaurantRepository;
import com.proj.restreserve.user.entity.User;
import com.proj.restreserve.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantImageRepository restaurantImageRepository;
    private final UserRepository userRepository;
    private final MenuCategoryRepository menuCategoryRepository;
    private final MenuRepository menuRepository;
    private final MenuImageRepository menuImageRepository;
    private final FileCURD fileCURD;
    private final String useServiceName = "restaurant";//S3 버킷 폴더명


    private final FavoritesRepository favoritesRepository;

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); // 현재 로그인한 사용자의 인증 정보를 가져옵니다.
        String useremail = authentication.getName();
        return userRepository.findByUseremail(useremail); // 로그인한 사용자의 이메일을 사용하여 사용자 정보를 조회합니다.
    }

    @Transactional
    public Restaurant regist(RestaurantDto restaurantDto, List<MultipartFile> files, List<MenuDto> menuDtos, List<MultipartFile> menuImageFiles) {
        // 가게 정보 저장
        Restaurant restaurant = new Restaurant();
        // 사용자 인증 정보 가져오기
        User user = getCurrentUser();

        // 가게 정보 설정
        restaurant.setTitle(restaurantDto.getTitle());
        restaurant.setCategory(restaurantDto.getCategory());
        restaurant.setCloseddays(restaurantDto.getCloseddays());
        restaurant.setOpentime(restaurantDto.getOpentime());
        restaurant.setClosetime(restaurantDto.getClosetime());
        restaurant.setContent(restaurantDto.getContent());
        restaurant.setPhone(restaurantDto.getPhone());
        restaurant.setStopsales(true);
        restaurant.setCookingtime(restaurantDto.getCookingtime());
        restaurant.setBan(false);
        restaurant.setUser(user);
        restaurant.setVibe(restaurantDto.getVibe());
        restaurant.setAddress(restaurantDto.getAddress());

        //레스토랑 이미지에 set하기위해 save 선언
        restaurantRepository.save(restaurant);

        List<RestaurantImage> restaurantImages = new ArrayList<>();



        // 각 파일에 대한 처리
        if(files!=null){
            for (MultipartFile file : files) {
                // 이미지 파일이 비어있지 않으면 처리
                if (!file.isEmpty()) {
                    // 이미지 파일명 생성
                    UUID uuid = UUID.randomUUID();
                    String fileName = uuid.toString();
                    String imageUrl = fileCURD.uploadImageToS3(file,useServiceName,fileName);//파일 업로드 파일,폴더명,파일일련번호

                    // 가게 이미지 정보 생성
                    RestaurantImage restaurantImage = new RestaurantImage();
                    restaurantImage.setRestaurantimageid(fileName);
                    restaurantImage.setRestaurant(restaurant);
                    restaurantImage.setImagelink(imageUrl);

                    // 이미지 정보 저장
                    restaurantImages.add(restaurantImage);
                    restaurantImageRepository.save(restaurantImage);
                }
            }
        }
        restaurant.setRestaurantimages(restaurantImages);

        // 메뉴 및 메뉴 이미지 처리
        if (menuDtos != null) {
            for (int i = 0; i < menuDtos.size(); i++) {
                MenuDto menuDto = menuDtos.get(i);
                Menu menu = new Menu();
                menu.setName(menuDto.getName());
                menu.setContent(menuDto.getContent());
                menu.setPrice(menuDto.getPrice());
                menu.setRestaurant(restaurant);
                MenuCategory menuCategory = menuCategoryRepository.findById(menuDto.getMenuCategory().getMenucategoryid()).orElse(null);
                menu.setMenuCategory(menuCategory);

                // 메뉴 저장
                menuRepository.save(menu);

                // 메뉴 이미지 처리
                List<MenuImage> menuImages = new ArrayList<>();
                for (MultipartFile imageFile : menuImageFiles) {
                    if (!imageFile.isEmpty() && imageFile.getOriginalFilename().startsWith(i + "_")) { // 파일 이름 규칙 확인
                        UUID uuid = UUID.randomUUID();
                        String fileName = uuid.toString();
                        String imageUrl = fileCURD.uploadImageToS3(imageFile, useServiceName, fileName);
                        MenuImage menuImage = new MenuImage();
                        menuImage.setMenuimageid(fileName);
                        menuImage.setMenuimagelink(imageUrl);
                        menuImageRepository.save(menuImage);
                        menuImages.add(menuImage);
                    }
                }
                if (!menuImages.isEmpty()) {
                    // 첫 번째 이미지를 메뉴 이미지로 설정
                    menu.setMenuimages(menuImages.get(0));
                    menuRepository.save(menu);
                }
            }
        }

        return restaurant;
    }

    public List<RestaurantDto> restaurantAll() {
        List<Restaurant> restaurants = restaurantRepository.findAll();
        return restaurants.stream().map(restaurant -> {
            RestaurantDto restaurantDto = new RestaurantDto();
            restaurantDto.setRestaurantid(restaurant.getRestaurantid());
            restaurantDto.setCategory(restaurant.getCategory());
            restaurantDto.setAddress(restaurant.getAddress());
            restaurantDto.setTitle(restaurant.getTitle());
            restaurantDto.setContent(restaurant.getContent());
            restaurantDto.setOpentime(restaurant.getOpentime());
            restaurantDto.setClosetime(restaurant.getClosetime());

            // 이미지 파일들의 정보 가져오기
            List<String> imageLinks = restaurant.getRestaurantimages().stream()
                    .map(RestaurantImage::getImagelink)
                    .collect(Collectors.toList());
            restaurantDto.setRestaurantimageLinks(imageLinks);

            return restaurantDto;
        }).collect(Collectors.toList());
    }

    public void addFavoriteRestaurant(String restaurantid) {
        User user = getCurrentUser();

        // 사용자와 레스토랑을 찾아옴
        Optional<Restaurant> restaurantOptional = restaurantRepository.findById(restaurantid);
        if (restaurantOptional.isEmpty()) {
            throw new IllegalArgumentException("Restaurant not found");
        }

        Restaurant restaurant = restaurantOptional.get();

        // 이미 사용자가 해당 레스토랑을 좋아하는지 확인
        boolean isAlreadyFavorite = favoritesRepository.existsByUserAndRestaurant(user, restaurant);
        if (isAlreadyFavorite) {
            throw new IllegalStateException("Restaurant already added to favorites");
        }

        // Favorites 엔티티 생성 후 저장
        Favorites favorites = new Favorites();
        favorites.setUser(user);
        favorites.setRestaurant(restaurant);
        favoritesRepository.save(favorites);
    }
}