# mqhooks


## Subscribe


    http://localhost:8080/queue/FOO
    http://localhost:8080/topic/FOO

Example:

    curl -v -X POST -H 'Content-Type:application/json' -d "{\"url\":\"http://example.com\"}" http://localhost:8080/queue/TEST

Request:

    POST /queue/TEST HTTP/1.1
    Host: localhost:8080
    Accept: */*
    Content-Type:application/json
    Content-Length: 28

    {"url":"http://example.com"}

Response:

    HTTP/1.1 200 OK
    Content-Type: application/json
    Transfer-Encoding: chunked
    Server: Jetty(7.6.1.v20120215)

    30212


## Unsubscribe

    curl -v -X DELETE http://localhost:8080/queue/TEST/30212


Request:

    DELETE /queue/TEST/30212 HTTP/1.1
    Host: localhost:8080
    Accept: */*

Response:

    HTTP/1.1 204 No Content
    Server: Jetty(7.6.1.v20120215)

List
----

Example:

    curl -v -X GET  http://localhost:8080/queue/TEST

Request:

    GET /queue/TEST HTTP/1.1
    User-Agent: curl/7.19.7 (universal-apple-darwin10.0) libcurl/7.19.7 OpenSSL/0.9.8r zlib/1.2.3
    Host: localhost:8080
    Accept: */*

Response:

    HTTP/1.1 200 OK
    Content-Type: application/json
    Transfer-Encoding: chunked
    Server: Jetty(7.6.1.v20120215)

    {"69209":{"id":"69209","url":"http://example.com"},"87551":{"id":"87551","url":"http://example.com"}}
