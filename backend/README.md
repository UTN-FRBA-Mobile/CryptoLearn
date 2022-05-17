## Backend

### Sign Up

```bash
curl --request POST \
  --url http://localhost:8080/sign_up \
  --header 'Content-Type: application/json' \
  --data '{
	"email": "admin1@gmail.com",
	"password": "pepe1111"
}'

Response: 201
```

### Login

```bash
curl --request POST \
  --url http://localhost:8080/log_in \
  --header 'Content-Type: application/json' \
  --data '{
	"email": "admin1@gmail.com",
	"password": "pepe1111"
}'

Response: 200 + JWT | 403
```

### Levels

```bash
curl --request GET \
  --url http://localhost:8080/levels \
  --header 'Authorization: Bearer <JWT_TOKEN>'

Response:
[{
    "answer": 0,
    "name": "Capitulo 1",
    "options": [
        "Un gusto de helado",
        "Una criptomoneda"
    ],
    "question": "Que es el bitcoin?",
    "state": "completado",
    "url": "http://localhost:8080/levels/1/1"
},…]
}
```

```bash
curl --request GET \
  --url http://localhost:8080/levels/<chapter>/<question> \
  --header 'Authorization: Bearer <JWT_TOKEN>'

Example:
curl --request GET \
  --url http://localhost:8080/levels/1/1 \
  --header 'Authorization: Bearer <JWT_TOKEN>'
Response: <Html>…</Html>
```
