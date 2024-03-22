package com.proj.restreserve.board.service;

import com.proj.restreserve.board.dto.EventDto;
import com.proj.restreserve.board.entity.Event;
import com.proj.restreserve.board.entity.EventImage;
import com.proj.restreserve.board.repository.EventImageRepository;
import com.proj.restreserve.board.repository.EventRepository;
import com.proj.restreserve.restaurant.dto.RestaurantDto;
import com.proj.restreserve.restaurant.entity.Restaurant;
import com.proj.restreserve.restaurant.entity.RestaurantImage;
import com.proj.restreserve.user.entity.User;
import com.proj.restreserve.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final EventImageRepository eventImageRepository;

    public Event writeevent(EventDto eventDto, List<MultipartFile> files) {
        //글 정보 저장
        Event event = new Event();
        // 사용자 인증 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String useremail = authentication.getName();

        User user = userRepository.findByUseremail(useremail);

        event.setTitle(eventDto.getTitle());
        event.setContent(eventDto.getContent());
        event.setDate(LocalDate.now());
        event.setEventstart(eventDto.getEventstart());
        event.setEventend(eventDto.getEventend());
        event.setEventstatus(false);
        event.setUser(user);
        // 가게 이미지 업로드 경로 설정
        String projectPath = System.getProperty("user.dir") + File.separator + "JUNG" + File.separator + "board_practice" + File.separator + "board_back" + File.separator + "rest-reserve" + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "static" + File.separator + "eventfiles";

        List<EventImage> eventImages = new ArrayList<>();

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
                    EventImage eventImage = new EventImage();
                    eventImage.setEvent(event);
                    eventImage.setImagelink("images/" + fileName);

                    // 이미지 정보 저장
                    eventImages.add(eventImage);
                } catch (IOException e) {
                    throw new RuntimeException("이미지 업로드 중 오류 발생: " + e.getMessage());
                }
            }
        }

        // 가게 정보 저장
        event = eventRepository.save(event);

        // 가게 이미지 정보 저장
        for (EventImage eventImage : eventImages) {
            eventImage.setEvent(event); // 이미지 정보에 리뷰 정보 설정
            eventImageRepository.save(eventImage);
        }
        event.setEventimages(eventImages);

        return event;
    }

    public List<EventDto> eventlist() {
        List<Event> events = eventRepository.findAll();
        return events.stream().map(event -> {
            EventDto eventDto = new EventDto();
            eventDto.setEventid(event.getEventid());
            eventDto.setTitle(event.getTitle());
            eventDto.setDate(event.getDate());

            return eventDto;
        }).collect(Collectors.toList());
    }

    public List<EventDto> eventdetail(String eventid) {
        List<Event> events = eventRepository.findByEventid(eventid);
        return events.stream().map(event -> {
            EventDto eventDto = new EventDto();
            eventDto.setEventid(event.getEventid());
            eventDto.setTitle(event.getTitle());
            eventDto.setContent(event.getContent());
            eventDto.setDate(event.getDate());
            eventDto.setEventstart(event.getEventstart());
            eventDto.setEventend(event.getEventend());


            // 이미지 파일들의 정보 가져오기
            List<String> imageLinks = event.getEventimages().stream()
                    .map(EventImage::getImagelink)
                    .collect(Collectors.toList());
            eventDto.setEventimageLinks(imageLinks);


            return eventDto;
        }).collect(Collectors.toList());
    }
}
