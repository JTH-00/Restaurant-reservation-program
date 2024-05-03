    package com.proj.restreserve.user.entity;

    import jakarta.persistence.*;
    import lombok.Data;

    @Entity
    @Data
    @Table(name = "businessnumber")
    public class BusinessNumber {
        @Id
        @Column(name = "businessid")
        private String businessid;

        @Column(name = "imagelink",nullable = false)
        private String imagelink;
    }
