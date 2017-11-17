# coffee-recipe
a simple service that stores and provides commands for a jura coffee maker

## prerequisites
you'll need an mqtt broker and a mariadb. you can either use services in your
network environment or you can spin them up on your host via the provided docker-compose
file.

## configuration
this is pretty much a vanilla spring boot service. configure 

## running in a demo scenario
first you might want to reset your demo recipes
```
curl http://localhost/recipe/reset
```

afterwards you can create the demo recipes (small/big coffee as well as single/double espresso):
```
curl http://localhost/recipe/init
```

you can get all stored recipes via calling:
```
curl http://localhost/recipe
```

also you need to register the token (the value stored in your RFID chip) with a certain recipe
```
curl -X POST -H 'Content-Type: application/json' -d '{"token":"1234","recipeId":"7"}' http://localhost:8080/token
```

now we are ready to trigger the coffee machine with a token:
```
curl -X POST -H 'Content-Type: application/json' -d '{"token":"1234","machineId":"a020a600f704"}' http://localhost:8080/make
```

in the real setup the last request is sent from the Arduino RFID scanner.


## remarks
persistence is still under development, expect to delete your db
schema on a regular basis
