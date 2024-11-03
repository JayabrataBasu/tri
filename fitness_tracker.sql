/*This is merely the set of sql queries you must input to get the app working, please edit names and ids as per need, the list of queries can be expanded further as per need*/

-- Create a new database
CREATE DATABASE fitness_tracker;

-- Use the newly created database
USE fitness_tracker;

-- Create a table for users
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    email VARCHAR(100)
);

-- Insert a new user into the users table
INSERT INTO users (name, email) VALUES ('John Doe', 'john.doe@example.com');

-- Query all users from the users table
SELECT * FROM users;

-- Update a user's information (example)
UPDATE users SET name = 'Jane Doe', email = 'jane.doe@example.com' WHERE id = 1;

-- Delete a user from the users table (example)
DELETE FROM users WHERE id = 1;

-- Create a table for food choices
CREATE TABLE food_choices (
    id INT AUTO_INCREMENT PRIMARY KEY,
    food_name VARCHAR(100),
    calories INT,
    category VARCHAR(50),
    protein DECIMAL(5,2),
    carbs DECIMAL(5,2),
    fats DECIMAL(5,2)
);

-- Insert a new food item into the food_choices table
INSERT INTO food_choices (food_name, calories, category, protein, carbs, fats) 
VALUES ('Apple', 95, 'Snacks', 0.5, 25.0, 0.3);

-- Query all food choices
SELECT * FROM food_choices;

-- Update a food item's information (example)
UPDATE food_choices SET calories = 100 WHERE id = 1;

-- Delete a food item from the food_choices table (example)
DELETE FROM food_choices WHERE id = 1;
