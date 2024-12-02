CREATE SCHEMA IF NOT EXISTS sns;
USE sns;

CREATE TABLE IF NOT EXISTS sns.post (
    post_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    contents TEXT,
    owner_id VARCHAR(255),
    owner_email VARCHAR(255),
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE IF NOT EXISTS sns.like_member_info(
    post_id BIGINT,
    member_id VARCHAR(255),
    member_email VARCHAR(255)
);