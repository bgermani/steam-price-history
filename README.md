# Steam price history

## What it is
 
Simple Java application that schedules a daily pull of Steam game prices specified by the user and keeps a history of them.

## Endpoints

```
GET /gamelist - get all price history for all games inserted
```

```
GET /gamelist/update - manually trigger a price data update
```

```
GET /game/{gameId} -  get full Steam info for gameId

```

```
POST /game/{gameId} - insert gameId to db

```

## To-do

- Implement Redis
- Implement ES
- Reformulate DB structure using a PriceToGame table for better data quality
- Endpoints to be used with frontend