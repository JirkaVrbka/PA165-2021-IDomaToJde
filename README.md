# PA165 2021

## Portal for providing and consuming online services

The main goal is to create an online portal where customers can seek for or provide a service to others.

The current and main focus is on activities practiced online e.g.: yoga instructor offering online yoga classes.

The service keeps records of customers and their:

- Necessary personal details.
- Services they provide / consume.
- A timetable to ease time management.
- Rating / review information.

Customers must register into our system.
The system acts as a platform, the payment process is *not our responsibility!*

## UI
For UI we use Vue3 js. 

To start UI for this project, you have to:
 * Install npm
 * npm install
 * npm install vue@next
 * npm install -g @vue/cli
 * npm install vue bootstrap bootstrap-vue
 * npm install vue-cookies --save
 * npm install vuex --save
 * npm install js-cookie --save

 To run UI you have to:
  * Open terminal
  * Go to 'ui\src\frontend'
  * execute command 'npm run serve'
  * In browser open 'http://localhost:3000/'

## Rest

The REST API documentation is built using Swagger2 and is available at `/swagger` (redirect)\
To obtain a token you can log in via the `AuthenticationController` with the predefined admin account:
> Username: root
> 
> Passsword: 12345

Then supply the returned token to all calls that need it. It currently stays valid for one day.

### Permission System

Just having the token is not enough, you have to have permission to access the resource
- Category -> no restriction except `delete` for admins only
- Offer -> being an owner or being subscribed to an `Offer` allows different interaction, `getAll` has no restrictions,
  otherwise you have to be authenticated
- User -> you have to be authenticated or be registered to one `Offer/s` of the `User` you want to contact
- Timetable -> `move` is `Offer` owner only, otherwise mostly being authenticated and owner
- TimetableChatMessage -> being an owner of the message or being related to the `TimetableEntry` (owner/`Offer` author)

## Reference project
https://github.com/fi-muni/PA165/tree/master/eshop-persistence

## Project development process
 - To create a change in main project, create a branch from master (and name it correctly)
 - Switch to your branch
 - Make changes in code (comment regularly)
 - Push changes into your branch
 - To merge into master, create pull request (from your branch into master)
 - Add other participants as reviewers 
 - (Fix comments from reviewers)
 - When (and only when) all reviews gives the pull request an approval, merge into master

> Updated at 23.05.2021
