package com.proj.restreserve.restaurant.service;

import com.proj.restreserve.restaurant.dto.RestaurantDto;
import com.proj.restreserve.restaurant.entity.Restaurant;
import com.proj.restreserve.restaurant.entity.RestaurantImage;
import com.proj.restreserve.restaurant.repository.RestaurantImageRepository;
import com.proj.restreserve.restaurant.repository.RestaurantRepository;
import com.proj.restreserve.review.entity.ReviewImage;
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
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantImageRepository restaurantImageRepository;
    private final UserRepository userRepository;

    @Transactional
    public Restaurant regist(RestaurantDto restaurantDto, List<MultipartFile> files) {
        // 가게 정보 저장
        Restaurant restaurant = new Restaurant();
        // 사용자 인증 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String useremail = authentication.getName();

        User user = userRepository.findByUseremail(useremail);

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

        // 가게 이미지 업로드 경로 설정
        String projectPath = System.getProperty("user.dir")+ File.separator+"JUNG"+ File.separator+"board_practice"+ File.separator +"board_back"+ File.separator + "rest-reserve" + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "static" +  File.separator + "files";

        List<RestaurantImage> restaurantImages = new ArrayList<>();

        // 각 파일에 대한 처리
        for (MultipartFile file : files) {
            // 이미지 파일이 비어있지 않으면 처리
            if (!file.isEmpty()) {
                try {
                    // 이미지 파일명 생성
                    UUID uuid = UUID.randomUUID();
                    String fileName = uuid.toString() + "_" + file.getOriginalFilename();

                    String restaurantImageId = uuid.toString();
                    File saveFile = new File(projectPath, fileName);

                    // 파일 저장
                    // 랜덤 식별자와 파일명 지정(중복 방지)
                    file.transferTo(saveFile);

                    // 가게 이미지 정보 생성
                    RestaurantImage restaurantImage = new RestaurantImage();
                    restaurantImage.setRestaurant(restaurant);
                    restaurantImage.setImagelink("images/" + fileName);

                    // 이미지 정보 저장
                    restaurantImages.add(restaurantImage);
                } catch (IOException e) {
                    throw new RuntimeException("이미지 업로드 중 오류 발생: " + e.getMessage());
                }
            }
        }

        // 가게 정보 저장
        restaurant = restaurantRepository.save(restaurant);

        // 가게 이미지 정보 저장
        for (RestaurantImage restaurantImage : restaurantImages) {
            restaurantImage.setRestaurant(restaurant); // 이미지 정보에 리뷰 정보 설정
            restaurantImageRepository.save(restaurantImage);
        }
        restaurant.setRestaurantimages(restaurantImages);

        return restaurant;
    }

    public List<RestaurantDto> findRestaurant() {
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
}



