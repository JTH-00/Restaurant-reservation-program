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
import com.proj.restreserve.detailpage.service.FileCURD;
import com.proj.restreserve.restaurant.dto.RestaurantDto;
import com.proj.restreserve.restaurant.entity.Restaurant;
import com.proj.restreserve.restaurant.entity.RestaurantImage;
import com.proj.restreserve.user.entity.User;
import com.proj.restreserve.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
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
    private final ModelMapper modelMapper;
    private final FileCURD fileCURD;
    private final List<String> useServiceName = Arrays.asList("board/notice","board/event");
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); // 현재 로그인한 사용자의 인증 정보를 가져옵니다.
        String useremail = authentication.getName();
        return userRepository.findByUseremail(useremail); // 로그인한 사용자의 이메일을 사용하여 사용자 정보를 조회합니다.
    }
    public Event writeevent(EventDto eventDto, List<MultipartFile> files) {
        //글 정보 저장
        Event event = new Event();
        // 사용자 인증 정보 가져오기
        User user = getCurrentUser();

        event.setTitle(eventDto.getTitle());
        event.setContent(eventDto.getContent());
        event.setDate(LocalDate.now());
        event.setEventstart(eventDto.getEventstart());
        event.setEventend(eventDto.getEventend());
        event.setEventstatus(false);
        event.setUser(user);
        eventRepository.save(event);

        List<EventImage> eventImages = new ArrayList<>();
        if (files != null) {
            // 각 파일에 대한 처리
            for (MultipartFile file : files) {
                // 이미지 파일이 비어있지 않으면 처리
                if (!file.isEmpty()) {
                    // 이미지 파일명 생성
                    UUID uuid = UUID.randomUUID();
                    String fileName = uuid.toString() + "_" + file.getOriginalFilename().lastIndexOf(".");//uuid+확장자명으로 이름지정

                    String imageUrl = fileCURD.uploadImageToS3(file,useServiceName.get(1),fileName);//파일 업로드

                    // 가게 이미지 정보 생성
                    EventImage eventImage = new EventImage();
                    eventImage.setEventimageid(fileName);
                    eventImage.setEvent(event);
                    eventImage.setImagelink(imageUrl);

                    // 이미지 정보 저장
                    eventImages.add(eventImage);
                }
            }
        }
        event.setEventimages(eventImages);
        return event;
    }

    public Notice writenotice(NoticeDto noticeDto, List<MultipartFile> files) {
        //글 정보 저장
        Notice notice = new Notice();
        // 사용자 인증 정보 가져오기
        User user = getCurrentUser();

        notice.setTitle(noticeDto.getTitle());
        notice.setContent(noticeDto.getContent());
        notice.setDate(LocalDate.now());
        notice.setUser(user);

        List<NoticeImage> noticeImages = new ArrayList<>();
        if (files != null) {
            // 각 파일에 대한 처리
            for (MultipartFile file : files) {
                // 이미지 파일이 비어있지 않으면 처리
                if (!file.isEmpty()) {
                    // 이미지 파일명 생성
                    UUID uuid = UUID.randomUUID();
                    String fileName = uuid.toString();

                    String imageUrl = fileCURD.uploadImageToS3(file,useServiceName.get(0),fileName);//파일 업로드
                    // 가게 이미지 정보 생성
                    NoticeImage noticeImage = new NoticeImage();
                    noticeImage.setNoticeimageid(fileName);
                    noticeImage.setNotice(notice);
                    noticeImage.setImagelink(imageUrl);

                    // 이미지 정보 저장
                    noticeImages.add(noticeImage);
                    noticeImageRepository.save(noticeImage);
                }
            }
        }
        notice.setNoticeimages(noticeImages);

        return notice;
    }
    @Transactional
    public EventDto modifyEvent(String eventid,EventDto eventDto, List<MultipartFile> files,List<String> deleteImageLinks) {
        // 이벤트 정보 불러오기
        Event event = eventRepository.getReferenceById(eventid);
        if(!event.getUser().equals(getCurrentUser())){
            throw new RuntimeException("올바른 접근이 아닙니다");
        }
        //수정된 이벤트 내용
        event.setTitle(eventDto.getTitle());
        event.setContent(eventDto.getContent());
        event.setEventstart(eventDto.getEventstart());
        event.setEventend(eventDto.getEventend());

        if (deleteImageLinks != null){
            for (String deleteImageLink : deleteImageLinks) {
                //링크의 맨마지막 일련번호를 가져옴
                int imageidNum = deleteImageLink.lastIndexOf("/");
                String imageid = deleteImageLink.substring(imageidNum + 1);

                fileCURD.deleteFile(useServiceName.get(1),imageid);
                //orphanRemoval = true가 되어있어 리뷰 엔티티의 연결을 끊어 고아 객체를 삭제
                event.getEventimages().removeIf(c -> c.getEventimageid().equals(imageid));
            }
        }
        if(files!=null){
            // 각 파일에 대한 처리
            for (MultipartFile file : files) {
                // 이미지 파일이 비어있지 않으면 처리
                if (!file.isEmpty()) {
                    // 이미지 파일명 생성
                    UUID uuid = UUID.randomUUID();
                    String fileName = uuid.toString();
                    String imageUrl = fileCURD.uploadImageToS3(file, useServiceName.get(1), fileName);//파일 업로드

                    // 공지사항 이미지 정보 생성
                    EventImage eventImage = new EventImage();
                    eventImage.setImagelink(imageUrl);
                    eventImage.setEventimageid(fileName);
                    eventImage.setEvent(event);
                    event.getEventimages().add(eventImage);

                    eventImageRepository.save(eventImage);
                }
            }

        }
        EventDto eventDto1 = this.modelMapper.map(event,EventDto.class);
        List<String> imagelink = event.getEventimages().stream()
                .map(EventImage::getImagelink)
                .collect(Collectors.toList());
        eventDto1.setEventimageLinks(imagelink);
        return eventDto1;
    }
    @Transactional
    public NoticeDto modifyNotice(String noticeid,NoticeDto noticeDto, List<MultipartFile> files,List<String> deleteImageLinks) {
        // 공지사항 정보 불러오기
        Notice notice = noticeRepository.getReferenceById(noticeid);
        if(!notice.getUser().equals(getCurrentUser())){
            throw new RuntimeException("올바른 접근이 아닙니다");
        }
        //수정된 공지사항 내용
        notice.setTitle(noticeDto.getTitle());
        notice.setContent(noticeDto.getContent());

        if (deleteImageLinks != null){
            for (String deleteImageLink : deleteImageLinks) {
                //링크의 맨마지막 일련번호를 가져옴
                int imageidNum = deleteImageLink.lastIndexOf("/");
                String imageid = deleteImageLink.substring(imageidNum + 1);

                fileCURD.deleteFile(useServiceName.get(0),imageid);
                //orphanRemoval = true가 되어있어 리뷰 엔티티의 연결을 끊어 고아 객체를 삭제
                notice.getNoticeimages().removeIf(c -> c.getNoticeimageid().equals(imageid));
            }
        }
        if(files!=null){
        // 각 파일에 대한 처리
            for (MultipartFile file : files) {
                // 이미지 파일이 비어있지 않으면 처리
                if (!file.isEmpty()) {
                    // 이미지 파일명 생성
                    UUID uuid = UUID.randomUUID();
                    String fileName = uuid.toString();
                    String imageUrl = fileCURD.uploadImageToS3(file, useServiceName.get(0), fileName);//파일 업로드

                    // 공지사항 이미지 정보 생성
                    NoticeImage noticeImage = new NoticeImage();
                    noticeImage.setImagelink(imageUrl);
                    noticeImage.setNoticeimageid(fileName);
                    noticeImage.setNotice(notice);
                    notice.getNoticeimages().add(noticeImage);

                    noticeImageRepository.save(noticeImage);
                }
            }

        }
        NoticeDto noticeDto1 = this.modelMapper.map(notice,NoticeDto.class);
        List<String> imagelink = notice.getNoticeimages().stream()
                .map(NoticeImage::getImagelink)
                .collect(Collectors.toList());
        noticeDto1.setNoticeimageLinks(imagelink);
        return noticeDto1;
    }
    @Transactional
    public void deleteEvent(String eventid){
        if(!eventRepository.getReferenceById(eventid).getUser().equals(getCurrentUser())){
            throw new RuntimeException("올바른 접근이 아닙니다");
        }
        List<EventImage> eventImages = eventImageRepository.findByEvent_Eventid(eventid); //이벤트과 연관된 이미지들

        for(EventImage eventImage: eventImages){//S3 업로드된 이미지들 삭제
            eventImageRepository.delete(eventImage);//이벤트 이미지 db에서 삭제
            fileCURD.deleteFile(useServiceName.get(1),eventImage.getEventimageid());
        }
        noticeRepository.deleteById(eventid);//이벤트 삭제
    }
    @Transactional
    public void deleteNotice(String noticetid){
        if(!noticeRepository.getReferenceById(noticetid).getUser().equals(getCurrentUser())){
            throw new RuntimeException("올바른 접근이 아닙니다");
        }
        List<NoticeImage> noticeImages = noticeImageRepository.findByNotice_Noticeid(noticetid); //공지사항과 연관된 이미지들

        for(NoticeImage noticeImage: noticeImages){//S3 업로드된 이미지들 삭제
            noticeImageRepository.delete(noticeImage);//공지사항 이미지 db에서 삭제
            fileCURD.deleteFile(useServiceName.get(0),noticeImage.getNoticeimageid());
        }
        noticeRepository.deleteById(noticetid);//공지사항 삭제
    }

    public Page<EventDto> eventlist(int page, int pagesize) {
        Pageable pageable = PageRequest.of(page,pagesize);
        Page<Event> events = eventRepository.findAll(pageable);
        return events.map(event -> {
            EventDto eventDto = new EventDto();
            eventDto.setEventid(event.getEventid());
            eventDto.setTitle(event.getTitle());
            eventDto.setDate(event.getDate());

            return eventDto;
        });
    }

    public Page<NoticeDto> noticelist(int page, int pagesize) {
        Pageable pageable = PageRequest.of(page,pagesize);
        Page<Notice> notices = noticeRepository.findAll(pageable);
        return notices.map(notice -> {
            NoticeDto noticeDto = new NoticeDto();
            noticeDto.setNoticeid(notice.getNoticeid());
            noticeDto.setTitle(notice.getTitle());
            noticeDto.setDate(notice.getDate());

            return noticeDto;
        });
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
