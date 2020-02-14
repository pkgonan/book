# Book Search Service Study

## How to use ?
* Clone project


        git clone https://github.com/pkgonan/book.git


* Initialize


        docker-compose up -d


* How to test?


        Refer to https directories.
        
        https://github.com/pkgonan/book/tree/master/https


## Feature
* Member (Join, Login)
* Book Search
* My Search History
* Popular Search Keyword


## Open Source
* spring-boot-starter-web
    * For using servlet (JDBC is Blocking I/O)
* spring-boot-starter-webflux
    * For using non-blocking I/O (Book search)
* spring-boot-starter-data-jpa
    * For using JPA
* spring-boot-starter-data-redis-reactive
    * For using Reactive Redis
* spring-boot-starter-security
    * For using password encoding
* lombok
    * For using easy development
* h2
    * For using database easily