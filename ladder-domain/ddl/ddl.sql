create table `member`
(
    `id`                   BIGINT AUTO_INCREMENT,
    `nickname`             VARCHAR(50) NOT NULL,
    `profile_url`          VARCHAR(300) DEFAULT NULL,
    `account_connect_type` VARCHAR(30) NOT NULL,
    `created_at`           DATETIME(6)  DEFAULT NULL,
    `updated_at`           DATETIME(6)  DEFAULT NULL,
    PRIMARY KEY (`id`)
);

create table `account`
(
    `id`          bigint auto_increment,
    `member_id`   bigint       not null,
    `social_id`   varchar(30) not null,
    `social_type` varchar(300) not null,
    `created_at`  datetime(6)  null,
    `updated_at`  datetime(6)  null,
     PRIMARY KEY (`id`),
     UNIQUE KEY `uni_account_1` (`social_id`, `social_type`),
     KEY `idx_account_1` (`member_id`)
);

create table `playlist`
(
    `id`         bigint auto_increment,
    `room_id`    bigint       not null,
    `order`    TEXT,
    `current_item_id` bigint null,
    `play_status` varchar(30) null,
    `created_at` datetime(6)  null,
    `updated_at` datetime(6)  null,
     PRIMARY KEY (`id`),
     UNIQUE KEY `uni_playlist_1` (`room_id`)
);

create table `playlist_item`
(
    `id`             bigint auto_increment,
    `playlist_id`    bigint       NOT NULL,
    `video_id`       varchar(300) NOT NULL,
    `title`          varchar(255) null,
    `thumbnail`      varchar(255) null,
    `duration`       int          not null,
    `dominant_color` varchar(255) null,
    `status`         varchar(30) not null,
    `created_at`     datetime(6)  null,
    `updated_at`     datetime(6)  null,
    PRIMARY KEY (`id`),
    KEY `idx_playlist_item_1` (`playlist_id`)
);

create table `room`
(
    `id`             bigint auto_increment,
    `name`           varchar(30) not null,
    `invitation_key` varchar(100) not null,
    `emoji_type`     varchar(30) not null,
    `status`         varchar(30) not null,
    `created_at`     datetime(6)  null,
    `updated_at`     datetime(6)  null,
    PRIMARY KEY (`id`),
    UNIQUE  KEY `uni_room_1` (`invitation_key`)
);

create table `room_member_mapper`
(
    `id`         bigint auto_increment,
    `member_id`  bigint       not null,
    `room_id`    bigint       not null,
    `role`       varchar(30) not null,
    `created_at` datetime(6)  null,
    `updated_at` datetime(6)  null,
    PRIMARY KEY (`id`),
    UNIQUE  KEY `uni_room_member_mapper_1` (`room_id`, `member_id`)
);

create table `mood_suggestion`
(
    `id`             bigint auto_increment,
    `room_id`        bigint       not null,
    `recommender_id` bigint       not null,
    `name`           varchar(30) not null ,
    `created_at`     datetime(6)  null,
    `updated_at`     datetime(6)  null,
    PRIMARY KEY (`id`),
    KEY `idx_mood_suggestion_1` (`room_id`)
);



