create table user_account
(
    id         bigint                  not null
        primary key,
    username   varchar(255)            not null,
    password   varchar(255)            not null,
    email      varchar(255)            not null,
    created_at datetime                null,
    update_at  datetime                null,
    role_id    varchar(255)            not null,
    avatar_url varchar(255) default '' null,
    is_deleted tinyint      default 0  not null,
    constraint email
        unique (email),
    constraint role_id
        unique (role_id),
    constraint username
        unique (username)
);

create table social_followers
(
    id           bigint            not null
        primary key,
    follower_id  bigint            not null,
    following_id bigint            not null,
    follow_time  datetime          null,
    status       tinyint default 0 null,
    constraint social_followers_ibfk_1
        foreign key (follower_id) references user_account (id),
    constraint social_followers_ibfk_2
        foreign key (following_id) references user_account (id)
);

create index follower_id
    on social_followers (follower_id);

create index following_id
    on social_followers (following_id);

create table user_roles
(
    id   varchar(255)           not null,
    role enum ('admin', 'user') null,
    constraint user_roles_ibfk_1
        foreign key (id) references user_account (role_id)
);

create index id
    on user_roles (id);

create table video_videos
(
    id              bigint            not null
        primary key,
    title           varchar(255)      null,
    description     varchar(1000)     null,
    created_at      datetime          null,
    updated_at      datetime          null,
    file_path       varchar(255)      null,
    author_id       bigint            not null,
    click_count     int     default 0 null,
    recommend_count int     default 0 null,
    comment_count   int     default 0 null,
    is_deleted      tinyint default 0 null,
    constraint video_videos_ibfk_1
        foreign key (author_id) references user_account (id)
);

create table likes_video
(
    id       bigint not null
        primary key,
    user_id  bigint not null,
    video_id bigint not null,
    constraint likes_video_ibfk_1
        foreign key (user_id) references user_account (id),
    constraint likes_video_ibfk_2
        foreign key (video_id) references video_videos (id)
);

create index user_id
    on likes_video (user_id);

create index video_id
    on likes_video (video_id);

create table video_comment
(
    id              bigint                 not null
        primary key,
    video_id        bigint                 not null,
    parent_id       bigint  default (-(1)) null,
    content         varchar(1000)          null,
    recommend_count int     default 0      null,
    created_by      bigint                 not null,
    created_at      datetime               null,
    updated_at      datetime               null,
    is_deleted      tinyint default 0      null,
    constraint video_comment_ibfk_1
        foreign key (video_id) references video_videos (id),
    constraint video_comment_ibfk_2
        foreign key (created_by) references user_account (id)
);

create table likes_comment
(
    id         bigint not null
        primary key,
    user_id    bigint not null,
    comment_id bigint not null,
    constraint likes_comment_user_account_id_fk
        foreign key (user_id) references user_account (id),
    constraint likes_comment_video_comment_id_fk
        foreign key (comment_id) references video_comment (id)
);

create index created_by
    on video_comment (created_by);

create index video_id
    on video_comment (video_id);

create index author_id
    on video_videos (author_id);


