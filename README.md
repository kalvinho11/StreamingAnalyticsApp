# StreamingAnalyticsApp
The application needs Docker to run it correctly.

Use `docker-compose up -d` to run the project. Once it is up, access to the [RabbitMQ management portal](http://localhost:15672).
The credentials for this page are:
- Username: myuser
- Password: mypassword

Then, in the *data* queue insert this example message with *Content-Type: application/json*:

```json
{
  "version":"1.0.0",
  "datastreams" : [
    {
      "id" : "example",
      "feed" : "feed_1",
      "datapoints":[
        {"at":1431602523123,"value":41},
        {"at":1431602623123,"value":84},
        {"at":1431607623123,"value":41},
        {"at":1431608623123,"value":83},
        {"at":1431609523123,"value":50},
        {"at":1431612623123,"value":98},
        {"at":1431615623123,"value":102},
        {"at":1431618623123,"value":23}
      ]
    },
    {
      "id" : "example1",
      "feed" : "feed_1",
      "datapoints":[
        {"at":1431602523123,"value":41},
        {"at":1431602623123,"value":84},
        {"at":1431607623123,"value":41},
        {"at":1431608623123,"value":83},
        {"at":1431609523123,"value":50},
        {"at":1431612623123,"value":98},
        {"at":1431615623123,"value":102},
        {"at":1431618623123,"value":23}
      ]
    }
  ]
}
```
In order to check the calculated statistics, connect to the localhost Mongo with this connection configuration:

- **database**: analytics
- **username**: appUser
- **password**: password