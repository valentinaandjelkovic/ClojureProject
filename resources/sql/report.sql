-- name: get-all
-- retrives all reports
SELECT r.id, u.username AS USER, s.name AS street, c.name AS city, rt.name AS TYPE, r.date, r.description
FROM report r JOIN report_type rt ON (r.type=rt.id) JOIN USER u ON (u.id=r.user) JOIN street s ON (s.id=r.street) JOIN city c ON (s.city=c.id)
ORDER BY r.date DESC;

-- name: get-all-types
-- retrive all report type
SELECT *
FROM report_type
ORDER BY name;