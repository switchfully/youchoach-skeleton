create table DATABASE_FILE
(
    file_name varchar(255) not null,
    original_file_name varchar(255) not null,
    file_content bytea,
    primary key (file_name)
)
