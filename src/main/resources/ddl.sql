/*
CREATE TABLE member(
    id INT PRIMARY KEY auto_increment,
    member_id VARCHAR(50) NOT NULL UNIQUE KEY,
    member_name VARCHAR(50) NOT NULL,
    working_name VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL,
    member_password VARCHAR(50) NOT NULL,
    member_role ENUM('NORMAL', 'ADMIN') DEFAULT 'NORMAL' NOT NULL
    /*role (normal, admin)*/
);

CREATE TABLE board(
    board_id INT PRIMARY KEY AUTO_INCREMENT,
    board_name VARCHAR(50) NOT NULL,
    category VARCHAR(50) NOT NULL
);

CREATE TABLE post(
    post_id INT PRIMARY KEY auto_increment,
    member_id INT NOT NULL,
    board_id INT NOT NULL,
    title VARCHAR(50) NOT NULL,
    content TEXT,
    creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY(member_id) REFERENCES member(id),
    FOREIGN KEY(board_id) REFERENCES board(board_id)
);

CREATE TABLE reply(
    reply_id INT PRIMARY KEY AUTO_INCREMENT,
    member_id INT NOT NULL,
    post_id INT NOT NULL,
    content TEXT,
    creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY(member_id) REFERENCES member(id),
    FOREIGN KEY(post_id) REFERENCES post(post_id)
);
*/