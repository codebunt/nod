# nod
Test Auth implementation for codecraft

This uses play! framework and mongodb for its persistence.

Setup :
1) Clone this repo
2) point application to an existing mongodb instance by editing conf/application.conf
3) Add host entry for halageri.com to 127.0.0.1 (Not sure if its needed but I had a hard time getting this up on localhost)
3) ./activator "run 80"   [ will run the application in dev mode on port 80]

Test pages :

http://halageri.com/assets/signin.html

http://halageri.com/assets/index.html  (signup)

Controller : 
app/controllers/AuthController.java

Routes defined at : 
conf/routes

e.g : 
signup using fb :
GET http://halageri.com/signup/facebook?access_token=<token-from-fb>

returns userid and token

linking / unlinking a fb account :
GET http://halageri.com/user/<user-id>/link/facebook?access_token=<token-from-fb>
GET http://halageri.com/user/<user-id>/unlink/facebook?access_token=<token-from-fb>

signup using email :
POST http://halageri.com/signup/local
{
	"email" : <email>,
	"password" : <pwd>,
	"name" : <full-name>,
	"phone" : <phone>
}
returns userid and token





