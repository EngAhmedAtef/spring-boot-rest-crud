create table public.instructor
(
    id           serial
        primary key,
    first_name   varchar(50) not null,
    last_name    varchar(50) not null,
    email        varchar(50) not null,
    phone_number varchar(50) not null,
    title        varchar(50) not null
);

alter table public.instructor
    owner to postgres;

create table public.courses
(
    id            uuid default uuid_generate_v4() not null
        primary key,
    name          varchar(100)                    not null,
    start_date    timestamp                       not null,
    end_date      timestamp                       not null,
    course_level  course_level                    not null,
    is_started    boolean                         not null,
    instructor_id integer                         not null
        references public.instructor
            on delete cascade
);

alter table public.courses
    owner to postgres;

create index courses_name_index
    on public.courses (name);

create table public.students
(
    id           uuid default uuid_generate_v4() not null
        primary key,
    first_name   varchar(100)                    not null,
    last_name    varchar(100)                    not null,
    age          integer                         not null,
    gender       gender                          not null,
    email        varchar(100)                    not null,
    phone_number varchar(20)                     not null,
    national_id  varchar(20)                     not null
);

alter table public.students
    owner to postgres;

create table public.relations
(
    student_id uuid not null
        references public.students
            on delete cascade,
    course_id  uuid not null
        references public.courses
            on delete cascade,
    primary key (student_id, course_id)
);

alter table public.relations
    owner to postgres;

create table public.instructor_details
(
    id              uuid default uuid_generate_v4() not null
        primary key,
    youtube_channel varchar,
    hobbies         text[],
    instructor_id   integer                         not null
        references public.instructor
            on delete cascade
);

alter table public.instructor_details
    owner to postgres;