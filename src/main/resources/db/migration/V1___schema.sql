CREATE TABLE app_user (
    id UUID PRIMARY KEY,
    display_name TEXT NOT NULL,
    password TEXT NOT NULL,
    email TEXT NOT NULL,
    phone_number TEXT NOT NULL,
    gender TEXT NOT NULL,
    age INT NOT NULL
)