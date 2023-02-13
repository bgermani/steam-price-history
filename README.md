# Steam price history

## What it is

Pulls Steam game prices specified by the user daily and keeps a history of them.

## Endpoints

```
GET /gamelist - get all price history for all games
```

```
GET /gamelist/update - manually trigger a price data update
```

```
GET /game/{gameId} -  get all price data for specified gameId
```

```
POST /game/{gameId} - insert gameId to database
```