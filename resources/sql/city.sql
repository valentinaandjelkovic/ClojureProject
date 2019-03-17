-- name: search-city
-- retrive cities
SELECT *
FROM city
WHERE name LIKE CONCAT('%',:search,'%');


