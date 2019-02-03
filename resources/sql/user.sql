-- name: insert-user!
-- insert user in users
INSERT INTO user (first_name, last_name, username, email, password, admin)
VALUES (:first_name, :last_name, :username, :email, :password, :admin);

-- name: update-user!
-- updates an existing user record
UPDATE user
SET first_name = :first_name, last_name = :last_name, email = :email, password=:password, admin=:admin
WHERE id = :id;

-- name: get-user
-- retrieves a user record given the id
SELECT * FROM user
WHERE id = :id;

-- name: delete-user!
-- :doc deletes a user record given the id
DELETE FROM user
WHERE id = :id;



