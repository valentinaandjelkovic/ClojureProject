-- name: get-streets-by-city
-- retrive streets
SELECT *
FROM street
WHERE city=:id AND name LIKE CONCAT('%',:search,'%');