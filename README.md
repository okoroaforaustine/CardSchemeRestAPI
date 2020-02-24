# CardSchemeRestAPI
Token Authentication
Post :http://localhost:8086/User/register

Request
{"username":"austinee",
"password":"12345",
"email":"austine@gmail.com",
"firstname":"austine",
"lastname":"okoroafor"
	
}

Response
{
    "success": "true",
    "payload": {
        "id": 1,
        "username": "austine",
        "password": "$2a$10$ng8AHjaqXEJzJP0XaZx0Muju353Z/fuE6NlM/LBU.wysbvAQbmRI.",
        "firstname": "austine",
        "lastname": "okoroafor",
        "mobile": null,
        "email": "austine@gmail.com",
        "dateOfCreation": null
    }
}
