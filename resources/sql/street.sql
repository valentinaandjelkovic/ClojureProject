-- name: search-street
-- search streets
SELECT *
FROM street
WHERE city=:id AND name LIKE CONCAT('%',:search,'%');

-- name: get-all
-- retrive streets
SELECT s.id, s.name, s.community, c.name as city
FROM street s join city c ON (s.city=c.id);

-- name: update-street!
-- update street name and commmunity
UPDATE street
SET name=:name, community=:community
WHERE id=:id;

-- name: delete-street!
-- delete street by id
DELETE FROM street
WHERE id=:id;

-- name: find-by-id
-- retrive street by id
SELECT *
FROM street
WHERE id=:id;