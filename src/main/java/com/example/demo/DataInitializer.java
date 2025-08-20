package com.example.demo;

import com.example.demo.hotel.infra.RoomRepository;
import com.example.demo.hotel.domain.*;
import com.example.demo.hotel.infra.*;
import com.example.demo.user.application.UserService;
import com.example.demo.user.presentation.UserCreateRequestDto;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer {
    private final UserService userService;
    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;

    // User 기본 데이터
    @PostConstruct
    @Transactional
    public void initUsers() {
        userService.save(new UserCreateRequestDto("Jone Doe", "JohnD@gmail.com", "010-1234-1234"));
        userService.save(new UserCreateRequestDto("Jane Doe", "JaneD@gmail.com", "010-5678-5678"));
        userService.save(new UserCreateRequestDto("John Q.", "JohnQ@gmail.com", "010-1234-5678"));
    }

    // Hotel 기본 데이터
    @PostConstruct
    @Transactional
    public void initHotels() {
        // 서울신라호텔 (5성급)
        Hotel shilla = Hotel.create("서울신라호텔", 5, 9.0f, "서울 중구 동호로 249",
                "서울 중심부에 위치한 서울신라호텔은 2019년부터 2024년까지 6년 연속 포브스 트래블 가이드에서 5성 호텔로 선정되었습니다.");
        
        // 신라호텔 룸타입 생성
        RoomType shillaPresidential = createRoomType("프레지덴셜 스위트", new BigDecimal("800000"), new BigDecimal("600000"), 4, "최고급 스위트룸");
        RoomType shillaExecutive = createRoomType("이그제큐티브 디럭스", new BigDecimal("400000"), new BigDecimal("300000"), 2, "비즈니스 디럭스룸");
        RoomType shillaStandard = createRoomType("스탠다드 디럭스", new BigDecimal("250000"), new BigDecimal("200000"), 2, "편안한 디럭스룸");
        
        shilla.addRoomType(shillaPresidential);
        shilla.addRoomType(shillaExecutive);
        shilla.addRoomType(shillaStandard);
        
        // 호텔 저장 (룸타입 포함)
        Hotel savedShilla = hotelRepository.save(shilla);
        
        // 신라호텔 룸 생성
        createRoomsWithVacancy(savedShilla.getId(), shillaPresidential.getId(), List.of(2001, 2002), LocalDate.now());
        createRoomsWithVacancy(savedShilla.getId(), shillaExecutive.getId(), List.of(1501, 1502, 1503, 1504), LocalDate.now());
        createRoomsWithVacancy(savedShilla.getId(), shillaStandard.getId(), List.of(1001, 1002, 1003, 1004, 1005, 1006), LocalDate.now());
        
        // 호텔 그레이스리 서울 (4성급)
        Hotel gracery = Hotel.create("호텔 그레이스리 서울", 4, 8.8f, "서울 중구 세종대로12길 12",
                "명동 남대문 지역에 위치한 호텔 그레이스리 서울은 인기 관광지에서 가까운 거리에 있습니다.");
        
        // 그레이스리 룸타입 생성
        RoomType graceryDeluxe = createRoomType("디럭스 트윈", new BigDecimal("180000"), new BigDecimal("150000"), 2, "모던한 트윈룸");
        RoomType graceryStandard = createRoomType("스탠다드 더블", new BigDecimal("140000"), new BigDecimal("120000"), 2, "깔끔한 더블룸");
        RoomType gracerySingle = createRoomType("스탠다드 싱글", new BigDecimal("100000"), new BigDecimal("80000"), 1, "편리한 싱글룸");
        
        gracery.addRoomType(graceryDeluxe);
        gracery.addRoomType(graceryStandard);
        gracery.addRoomType(gracerySingle);
        
        Hotel savedGracery = hotelRepository.save(gracery);
        
        // 그레이스리 룸 생성
        createRoomsWithVacancy(savedGracery.getId(), graceryDeluxe.getId(), List.of(301, 302, 303), LocalDate.now());
        createRoomsWithVacancy(savedGracery.getId(), graceryStandard.getId(), List.of(201, 202, 203, 204, 205), LocalDate.now());
        createRoomsWithVacancy(savedGracery.getId(), gracerySingle.getId(), List.of(101, 102, 103, 104), LocalDate.now());
        
        // 롯데시티호텔 마포 (3성급)
        Hotel lotte = Hotel.create("롯데시티호텔 마포", 3, 8.6f, "서울 마포구 마포대로 109",
                "롯데시티호텔 마포는 서울에 투숙하는 국내외 출장객 및 관광객을 위한 프리미엄 비즈니스 호텔입니다.");
        
        // 롯데 룸타입 생성
        RoomType lotteDeluxe = createRoomType("디럭스 더블", new BigDecimal("120000"), new BigDecimal("100000"), 2, "비즈니스 더블룸");
        RoomType lotteStandard = createRoomType("스탠다드 더블", new BigDecimal("90000"), new BigDecimal("75000"), 2, "실용적인 더블룸");
        
        lotte.addRoomType(lotteDeluxe);
        lotte.addRoomType(lotteStandard);
        
        Hotel savedLotte = hotelRepository.save(lotte);
        
        // 롯데 룸 생성
        createRoomsWithVacancy(savedLotte.getId(), lotteDeluxe.getId(), List.of(501, 502, 503), LocalDate.now());
        createRoomsWithVacancy(savedLotte.getId(), lotteStandard.getId(), List.of(401, 402, 403, 404, 405, 406), LocalDate.now());
    }
    
    private RoomType createRoomType(String name, BigDecimal onPrice, BigDecimal offPrice, Integer capacity, String description) {
        return RoomType.builder()
                .name(name)
                .onPrice(onPrice)
                .offPrice(offPrice)
                .capacity(capacity)
                .description(description)
                .build();
    }
    
    private void createRoomsWithVacancy(Long hotelId, Long roomTypeId, List<Integer> roomNumbers, LocalDate startDate) {
        for (Integer roomNum : roomNumbers) {
            Room room = Room.create(roomNum, hotelId, roomTypeId);
            
            // 3일간의 빈 방 데이터 생성
            for (int i = 0; i < 3; i++) {
                LocalDate date = startDate.plusDays(i);
                Vacancy vacancy = Vacancy.create(date, true);
                room.addVacancy(vacancy);
            }
            
            // vacancy가 추가된 room 저장
            roomRepository.save(room);
        }
    }
}
