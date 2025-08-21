package com.example.demo.hotel.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private String address;

    @Column(name = "ranks")
    private Integer rank;

    private float rating;

    @Builder.Default
    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<RoomType> roomTypes = new ArrayList<>();

    public void addRoomType(RoomType roomType) {
        this.roomTypes.add(roomType);
        roomType.setHotel(this);
    }

    public void removeRoomType(RoomType roomType) {
        this.roomTypes.remove(roomType);
        roomType.setHotel(null);
    }

    public RoomType findRoomType(Long roomTypeId) {
        return this.roomTypes.stream().filter(r -> r.getId().equals(roomTypeId)).findFirst().orElse(null);
    }


    public static Hotel create(String name, Integer rank, float rating, String address, String description) {
        return Hotel.builder()
                .name(name)
                .description(description)
                .address(address)
                .rank(rank)
                .rating(rating)
                .build();
    }
}
