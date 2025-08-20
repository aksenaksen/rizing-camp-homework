package com.example.demo.hotel.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer roomNum;

    private Long hotelId;
    
    private Long roomTypeId;

    @Builder.Default
    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Vacancy> vacancies = new ArrayList<>();

    public boolean isAvailable(LocalDate date){
        return vacancies.stream().anyMatch(vacancy ->
                vacancy.getDate().equals(date)
                && vacancy.isAvailable());
    }

    // 연관관계 편의 메서드
    public void addVacancy(Vacancy vacancy) {
        this.vacancies.add(vacancy);
        vacancy.setRoom(this);
    }

    public void removeVacancy(Vacancy vacancy) {
        this.vacancies.remove(vacancy);
        vacancy.setRoom(null);
    }

    public static Room create(Integer roomNum, Long hotelId, Long roomTypeId) {
        return Room.builder()
                .roomNum(roomNum)
                .hotelId(hotelId)
                .roomTypeId(roomTypeId)
                .build();
    }
}
