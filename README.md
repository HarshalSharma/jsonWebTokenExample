## JSON WEB TOKEN EXAMPLE

This is a simple example application for verifying JSON Web Signatures, this spring app uses PS256 algorithm to generate and verify tokens.

### APIs:

***
#### 1. GETTING THE PUBLIC KEY:

```bash
curl -X GET \
  'localhost:8080/jwts/publicKey' \
  -H 'Accept: */*'
```

#### 2. GET REQUEST BODY AS JWS SIGNED:
```bash
curl -X POST \
  'localhost:8080/jwts/getHashedJWS' \
  -H 'Accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
    "subject":"Test",
    "customHeader": {
        "name":"Some Name 1"
    }
}'
```

available body options:
1. **subject** - quick variable to set subject without customClaims, setting this will over-ride the customClaims Subject key.
2. **customHeader** - json object to add custom key-pair headers.
3. **customClaims** - json object to add Claims as key-pair, for example, "sub" : "Subject Name", 
4. **payload** - json object to set payload, either customClaim or payload must be set.

#### 3. Check If json is tampered / signature is valid.

Pass the JWS in body:

```bash
curl -X POST \
  'localhost:8080/jwts/isTampered' \
  -H 'Accept: */*' \
  -H 'Content-Type: application/json' \
  -d 'eyJuYW1lIjoiSGFyc2hhbCIsImFsZyI6IlBTMjU2In0.eyJzdWIiOiJUZXN0In0.Aaq0zByj9ORiP7NZHJWp0kYqJKkow85ofrbgwWojUv_fVcOuULx4JDoCAMjoDv7xKCx7x2ibxhkeEL7NNQb-4sZ94-lK5IKHtaQfLjvqrTUNHrJHSBXLaBBI1U9wIrg_33oQvk9sCPpTZrsg2aYoehs6Vr_QhSW4rIb74uSiFT_gJ-M_Tszw9QLHOHElo4D655g6J8VbCesCnSUlN-sWsUhat-Gt2FVB4vM6AJXDf5_dHuTKsj3puf49er3svmJkkeq1oEBfc4_j4u0eawJZggp9UezvYI6GimZjTFkI1AlwSBLLZ2Ww3AJYQSf1d1j7F72zOfcUQIFVItIafynvuA'
```

---
That's all folks!