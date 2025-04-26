to run dev profile:  ./gradlew bootRun --args='--spring.profiles.active=dev'


Swagger url:   http://localhost:8081/swagger-ui/index.html



🔐 1. User Registration
Endpoint: POST /users/register
Request Body:
json

{
  "name": "Alice",
  "email": "alice@example.com",
  "password": "secret123"
}

🔑 2. User Login
Endpoint: POST /users/login
Request Body:
json

{
  "email": "alice@example.com",
  "password": "secret123"
}

🛡️ 3. Calling Secured APIs
All other endpoints (like /users/me) are JWT protected.
How to use the token:
Add this HTTP header to every secured API call:
makefile

Authorization: Bearer <paste_token_here>



👤 4. Get Current User Info
Endpoint: GET /users/me
Headers:
makefile

Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...

