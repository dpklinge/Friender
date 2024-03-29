CREATE TABLE app_user (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid (),
    display_name TEXT NOT NULL,
    password TEXT NOT NULL,
    email TEXT NOT NULL,
    phone_number TEXT NOT NULL,
    gender TEXT NOT NULL,
    age INT NOT NULL
);

CREATE TABLE user_location (
    id UUID PRIMARY KEY REFERENCES app_user,
    home_location GEOGRAPHY,
    current_location GEOGRAPHY
);