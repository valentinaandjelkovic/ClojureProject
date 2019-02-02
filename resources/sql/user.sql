-- name: insert-user!
-- insert user in users
INSERT INTO users (first_name, last_name, username, email, password, admin)
VALUES (:first_name, :last_name, :username, :email, :password, :admin);

-- name: update-user!
-- updates an existing user record
UPDATE users
SET first_name = :first_name, last_name = :last_name, email = :email, password=:password, admin=:admin
WHERE id = :id;

-- name: get-user
-- retrieves a user record given the id
SELECT * FROM users
WHERE id = :id;

-- name: delete-user!
-- :doc deletes a user record given the id
DELETE FROM users
WHERE id = :id;
