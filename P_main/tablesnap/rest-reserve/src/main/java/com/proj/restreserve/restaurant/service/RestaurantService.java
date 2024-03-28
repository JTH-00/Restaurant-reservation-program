package com.proj.restreserve.restaurant.service;

import com.proj.restreserve.restaurant.dto.RestaurantDto;
import com.proj.restreserve.restaurant.dto.SelectRestaurantDto;
import com.proj.restreserve.restaurant.entity.Favorites;
import com.proj.restreserve.restaurant.entity.Restaurant;
import com.proj.restreserve.restaurant.entity.RestaurantImage;
import com.proj.restreserve.restaurant.repository.FavoritesRepository;
import com.proj.restreserve.restaurant.repository.RestaurantImageRepository;
import com.proj.restreserve.restaurant.repository.RestaurantRepository;
import com.proj.restreserve.user.entity.User;
import com.proj.restreserve.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantImageRepository restaurantImageRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    
    private final FavoritesRepository favoritesRepository;

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); // 현재 로그인한 사용자의 인증 정보를 가져옵니다.
        String useremail = authentication.getName();
        return userRepository.findByUseremail(useremail); // 로그인한 사용자의 이메일을 사용하여 사용자 정보를 조회합니다.
    }

    @Transactional
    public Restaurant regist(RestaurantDto restaurantDto, List<MultipartFile> files) {
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
            restaurantDto.setReviewcount(restaurant.getReviewcount());

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

    @Transactional(readOnly = true)
    public Optional<SelectRestaurantDto> findRestaurant(String restaurantid) {//레스토랑 상세페이지 조회
        Restaurant restaurant = this.restaurantRepository.findByRestaurantidAndBanFalse(restaurantid);//밴이 안된 레스토랑 매장 조회
        //DTO변환
        SelectRestaurantDto selectRestaurantDto = modelMapper.map(restaurant, SelectRestaurantDto.class);
        // 이미지 파일들의 링크 가져오기
        List<String> imageLinks = restaurant.getRestaurantimages().stream()
                .map(RestaurantImage::getImagelink)
                .collect(Collectors.toList());
        selectRestaurantDto.setRestaurantimageLinks(imageLinks);
        return Optional.ofNullable(selectRestaurantDto);
    }
    @Transactional(readOnly = true)
    public List<SelectRestaurantDto> ShowRestaurantByreview(){//메인페이지 카테고리별 매장 총 8개 조회
        String list[] = {"족발,보쌈","찜,탕,찌개","치킨","카페,디저트","고기,구이","중식","버거", "돈까스,회,일식","양식","백반,죽,국수","분식","피자","아시안"};
        List<String> categories = Arrays.asList(list);
        Collections.shuffle(categories,new Random()); //셔플 이후 매장 고르기

        List<SelectRestaurantDto> selectRestaurantDtos = new ArrayList<>(); // 랜덤으로 선택된 카테고리 매장들
        for(int i=0; i< Math.min(8, categories.size()); i++) {//8개 이하로 출력
            String selected = categories.get(i);
            List<Restaurant> restaurants = this.restaurantRepository.findManyReviewByCategory(selected); //리뷰 많은 순으로 출력
            Restaurant restaurant;

            if (!restaurants.isEmpty()) {
                restaurant = restaurants.get(0);// 리뷰가 제일 많은 매장 선택
            } else {
                continue;
            }
            SelectRestaurantDto selectRestaurantDto = modelMapper.map(restaurant, SelectRestaurantDto.class);
            selectRestaurantDtos.add(selectRestaurantDto);
        }
        List<List<SelectRestaurantDto>> mainPageRestaurant = new ArrayList<>(); //메인페이지에 출력할 용도
        mainPageRestaurant.add(selectRestaurantDtos);
        return selectRestaurantDtos;
    }
    @Transactional(readOnly = true)
    public List<SelectRestaurantDto> showRestaurantByRandom(){//메인페이지 랜덤 8개 매장 조회
        List<Restaurant> random8Restaurants = this.restaurantRepository.findRandom8Restaurants();//랜덤 8개 조회
        List<SelectRestaurantDto> random8SelectRestaurantDtos = new ArrayList<>(); //Dto변환 후 넣을 리스트

        random8Restaurants.forEach(restaurant -> {//dto변환
            SelectRestaurantDto selectRestaurantDto = modelMapper.map(restaurant, SelectRestaurantDto.class);
            random8SelectRestaurantDtos.add(selectRestaurantDto);
        });
        return random8SelectRestaurantDtos;
    }
    @Transactional(readOnly = true)
    public List<List<SelectRestaurantDto>> showMainPage(){//메인페이지 조회 목록합치기
        List<SelectRestaurantDto> random8Restaurants = showRestaurantByRandom();//랜덤 8개 매장 조회
        List<SelectRestaurantDto> restaurantByReview = ShowRestaurantByreview();//카테고리별 리뷰 많은 순의 매장 8개 조회

        List<List<SelectRestaurantDto>> mainPage = new ArrayList<>(); //하나로 출력하기 위해서 추가로 리스트 선언
        mainPage.add(random8Restaurants);
        mainPage.add(restaurantByReview);

        return mainPage;
    }
}