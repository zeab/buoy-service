# buoy-service
A barebones akka-http service to allow ingress paths to be exercised for testing and debugging

[![Build Status](https://travis-ci.org/zeab/buoy-service.svg?branch=master)](https://travis-ci.org/zeab/buoy-service)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/b07eeae174e040ba8717f45c43ed8b2e)](https://www.codacy.com/app/zeab/buoy-service?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=zeab/buoy-service&amp;utm_campaign=Badge_Grade)

#### Docker:
```docker
docker run -p 8080:8080 zeab/buoyservice
```

#### Http Endpoints:
```http
get /ingress
post /ingress
put /ingress
delete /ingress
```

#### Logging
Times and connections are all spit out to the console 
