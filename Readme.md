Your task is to create a simple API for registering and fetching a user from a database using a language of your choice.



Your API should be able to be called using Postman or an equivalent API platform locally. 
Your API should include endpoints for both creating and fetching the user with the appropriate security.


POST /user

```
{
  "firstName": "Michael",
  "lastName": "Knight",
  "email": "info@saqaya.com",
  "marketingConsent": false
}
```

Response


```
{
  "id": "553ae7da92f5505a92bbb8c9d47be76ab9f65bc2",
  "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c"
}
```

```
GET /user/553ae7da92f5505a92bbb8c9d47be76ab9f65bc2
```

```
{
  "id": "553ae7da92f5505a92bbb8c9d47be76ab9f65bc2",
  "firstName": "Michael",
  "lastName": "Knight",
  "marketingConsent": false
}
```

Recommendations


The “id” should be a programmatically generated SHA1 hash of the email address, salted with the following “450d0b0db2bcf4adde5032eca1a7c416e560cf44” string. 
The “accessToken” should be a programmatically generated unique JWT Token.
The GET endpoint should use “id” and "accessToken" to return the user.
The GET endpoint should omit the user “email” property if “marketingConsent” is false.
Save the user into a local database.
Include any of your own recommendations.
Please use any code structure that you feel is appropriate with the appropriate directory structures.
Please add comments in your code frequently.
