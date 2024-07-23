create table if not exists vacancy (
    id integer auto_increment PRIMARY KEY,
    slug varchar(255) not null unique,
    company_name varchar(255) not null,
    title varchar(255) not null,
    description text,
    remote boolean,
    url varchar(255),
    tags varchar(50) array,
    job_types varchar(50) array,
    location varchar(255),
    created_at bigint not null
);