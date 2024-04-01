package com.proj.restreserve.board.service;

import com.proj.restreserve.board.dto.EventDto;
import com.proj.restreserve.board.dto.NoticeDto;
import com.proj.restreserve.board.entity.Event;
import com.proj.restreserve.board.entity.EventImage;
import com.proj.restreserve.board.entity.Notice;
import com.proj.restreserve.board.entity.NoticeImage;
import com.proj.restreserve.board.repository.EventImageRepository;
import com.proj.restreserve.board.repository.EventRepository;
import com.proj.restreserve.board.repository.NoticeImageRepository;
import com.proj.restreserve.board.repository.NoticeRepository;
import com.proj.restreserve.detailpage.service.FileUpload;
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
    private final NoticeRepository noticeRepository;
    private final NoticeImageRepository noticeImageRepository;
    private final FileUpload fileUpload;

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

        List<EventImage> eventImages = new ArrayList<>();

        // 각 파일에 대한 처리
        for (MultipartFile file : files) {
            // 이미지 파일이 비어있지 않으면 처리
            if (!file.isEmpty()) {
                // 이미지 파일명 생성
                UUID uuid = UUID.randomUUID();
                String fileName = uuid.toString() + "_" + file.getOriginalFilename().lastIndexOf(".");//uuid+확장자명으로 이름지정

                String imageUrl = fileUpload.uploadImageToS3(file,"board/event",fileName);//파일 업로드

                // 가게 이미지 정보 생성
                EventImage eventImage = new EventImage();
                eventImage.setEvent(event);
                eventImage.setImagelink(imageUrl);

                // 이미지 정보 저장
                eventImages.add(eventImage);
            }
        }

        // 가게 정보 저장
        eventRepository.save(event);

        // 가게 이미지 정보 저장
        for (EventImage eventImage : eventImages) {
            eventImage.setEvent(event); // 이미지 정보에 이미지 정보 설정
            eventImageRepository.save(eventImage);
        }

        event.setEventimages(eventImages);
        eventRepository.save(event);//이미지 링크를 출력하기 위해 다시 save

        return event;
    }

    public Notice writenotice(NoticeDto noticeDto, List<MultipartFile> files) {
        //글 정보 저장
        Notice notice = new Notice();
        // 사용자 인증 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String useremail = authentication.getName();

        User user = userRepository.findByUseremail(useremail);

        notice.setTitle(noticeDto.getTitle());
        notice.setContent(noticeDto.getContent());
        notice.setDate(LocalDate.now());
        notice.setUser(user);

        List<NoticeImage> noticeImages = new ArrayList<>();

        // 각 파일에 대한 처리
        for (MultipartFile file : files) {
            // 이미지 파일이 비어있지 않으면 처리
            if (!file.isEmpty()) {
                // 이미지 파일명 생성
                UUID uuid = UUID.randomUUID();
                String fileName = uuid.toString() + "_" + file.getOriginalFilename().lastIndexOf(".");//uuid+확장자명으로 이름지정

                String imageUrl = fileUpload.uploadImageToS3(file,"board/notice",fileName);//파일 업로드
                // 가게 이미지 정보 생성
                NoticeImage noticeImage = new NoticeImage();
                noticeImage.setNotice(notice);
                noticeImage.setImagelink(imageUrl);

                // 이미지 정보 저장
                noticeImages.add(noticeImage);
            }
        }

        // 가게 정보 저장
        noticeRepository.save(notice);

        // 가게 이미지 정보 저장
        for (NoticeImage noticeImage : noticeImages) {
            noticeImage.setNotice(notice); // 이미지 정보에 리뷰 정보 설정
            noticeImageRepository.save(noticeImage);
        }

        notice.setNoticeimages(noticeImages);
        noticeRepository.save(notice);//이미지 링크를 출력하기 위해 다시 save

        return notice;
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

    public List<NoticeDto> noticelist() {
        List<Notice> notices = noticeRepository.findAll();
        return notices.stream().map(notice -> {
            NoticeDto noticeDto = new NoticeDto();
            noticeDto.setNoticeid(notice.getNoticeid());
            noticeDto.setTitle(notice.getTitle());
            noticeDto.setDate(notice.getDate());

            return noticeDto;
        }).collect(Collectors.toList());
    }

    public EventDto eventdetail(String eventid) {
        Event event = eventRepository.findById(eventid)
                .orElseThrow(() -> new IllegalArgumentException("해당 이벤트가 존재하지 않습니다."));

        // EventDto 객체 생성 및 데이터 설정
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

        return eventDto; // 수정된 단일 EventDto 반환

    }

    public NoticeDto noticedetail(String noticeid) {
        Notice notice = noticeRepository.findById(noticeid)
                .orElseThrow(() -> new IllegalArgumentException("해당 공지가 존재하지 않습니다."));

            NoticeDto noticeDto = new NoticeDto();
            noticeDto.setNoticeid(notice.getNoticeid());
            noticeDto.setTitle(notice.getTitle());
            noticeDto.setContent(notice.getContent());
            noticeDto.setDate(notice.getDate());

            // 이미지 파일들의 정보 가져오기
            List<String> imageLinks = notice.getNoticeimages().stream()
                    .map(NoticeImage::getImagelink)
                    .collect(Collectors.toList());
            noticeDto.setNoticeimageLinks(imageLinks);


            return noticeDto;
        }
}
