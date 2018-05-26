# Api Documentation
## Summary
The Toggler service relies on a ReST API to manipulate toggles. 

This section provides information regarding how to make requests to the API and the necessary parameters and payload.

## Requests

### Get all toggles

#### Request
`GET /api/toggle`

##### Request Payload
None

#### Response `OK`
##### Response Payload
```json
{
    "feature": [
        {
            "identifier":"12342:1.32",
            "group":"12342",
            "strategy":"org.ff4j.strategy.BlackListStrategy,
            grantedClients=service_1",
            "active":true
        }
    ],
    "service":"",
    "timestamp":1527342607,
    "uuid":"fd7705ec-016f-41f0-ad9a-47c2ce37bf55"
}
```

### Post a toggle

#### Request
`POST /api/toggle`
##### Request Payload
```json
{
    "toggle":"12342",
    "version":"1.32",
    "active":true ,
    "blacklist":"service_1"
}
```
#### Response `CREATED`
##### Response Payload
None



### Update The Toggle List

#### Request
`PUT /api/toggle`
##### Request Payload
None
#### Response `SERVICE_UNAVAILABLE`
##### Response Payload
None

### Delete The Toggle List
#### Request
`DELETE /api/toggle`
##### Request Payload
None
#### Response `SERVICE_UNAVAILABLE`
##### Response Payload
None

### Get a toggle with a given version
#### Request
`GET /api/toggle/TOGGLE_ID/version/VERSION_ID`

* TOGGLE_ID: The name of the toggle
* VERSION_ID: The Toggle's version

##### Request Payload
None

#### Response `OK`
##### Response Payload
```json
{
    "feature": [
        {
            "identifier":"12342:1.32",
            "group":"12342",
            "strategy":"org.ff4j.strategy.BlackListStrategy,
            grantedClients=service_1",
            "active":true
        }
    ],
    "service":"",
    "timestamp":1527342607,
    "uuid":"fd7705ec-016f-41f0-ad9a-47c2ce37bf55"
}
```

### Insert toggle
#### Request
`POST /api/toggle/TOGGLE_ID/version/VERSION_ID`

* TOGGLE_ID: The name of the toggle
* VERSION_ID: The Toggle's version
##### Request Payload
None
#### Response `SERVICE_UNAVAILABLE`
##### Response Payload
None

### Delete toggle
#### Request
`DELETE /api/toggle/TOGGLE_ID/version/VERSION_ID`

* TOGGLE_ID: The name of the toggle
* VERSION_ID: The Toggle's version
##### Request Payload
None
#### Response `NO_CONTENT`
If it exists
##### Response Payload
None
#### Response `NOT_FOUND`
If it does not exist
##### Response Payload
None

### Update toggle
#### Request
`PUT /api/toggle/TOGGLE_ID/version/VERSION_ID`

* TOGGLE_ID: The name of the toggle
* VERSION_ID: The Toggle's version
##### Request Payload
None
#### Response `SERVICE_UNAVAILABLE`
##### Response Payload
None

### Check whether or not a service is blacklisted
#### Request
`GET /api/toggle/TOGGLE_ID/version/VERSION_ID/service/SERVICE_ID`

* TOGGLE_ID: The name of the toggle
* VERSION_ID: The Toggle's version
* SERVICE_ID: The service's id

##### Request Payload
None

#### Response `OK`
##### Response Payload
```json
{
    "identifier":"12342:1.32",
    "active":false,
    "service":"service_1",
    "timestamp":1527344709,
    "uuid":"0329499a-868b-4235-a80f-ee83487269e0"
}
```
#### Response `NOT_FOUND`
##### Response Payload
None

### Blacklist Service
#### Request
`POST /api/toggle/TOGGLE_ID/version/VERSION_ID/service/SERVICE_ID`

* TOGGLE_ID: The name of the toggle
* VERSION_ID: The Toggle's version
* SERVICE_ID: The Service's name.
##### Request Payload
None
#### Response `SERVICE_UNAVAILABLE`
##### Response Payload
None

### Remove service from blacklist
#### Request
`DELETE /api/toggle/TOGGLE_ID/version/VERSION_ID/service/SERVICE_ID`

* TOGGLE_ID: The name of the toggle
* VERSION_ID: The Toggle's version
* SERVICE_ID: The Service's name.
##### Request Payload
None
#### Response `SERVICE_UNAVAILABLE`
##### Response Payload
None

### Edit the service Blacklist status
#### Request
`PUT /api/service/SERVICE_ID`

* TOGGLE_ID: The name of the toggle
* VERSION_ID: The Toggle's version
* SERVICE_ID: The Service's name.
##### Request Payload
None
#### Response `SERVICE_UNAVAILABLE`
##### Response Payload
None

### Get all toggles That have a service blacklisted

#### Request
`GET /service/SERVICE_ID`
* SERVICE_ID: The Service's name.
##### Request Payload
None

#### Response `OK`
##### Response Payload
```json
{
    "feature": [
        {
            "identifier":"12342:1.32",
            "group":"12342",
            "strategy":"org.ff4j.strategy.BlackListStrategy,
            grantedClients=service_1",
            "active":true
        }
    ],
    "service":"",
    "timestamp":1527342607,
    "uuid":"fd7705ec-016f-41f0-ad9a-47c2ce37bf55"
}
```
